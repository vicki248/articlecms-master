package com.juaracoding.websitearticle.controller;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 3/8/2023 7:54 PM
@Last Modified 3/8/2023 7:54 PM
Version 1.0
*/


import com.juaracoding.websitearticle.configuration.OtherConfig;
import com.juaracoding.websitearticle.model.CategoryArticle;
import com.juaracoding.websitearticle.service.CategoryArticleService;
import com.juaracoding.websitearticle.utils.ConstantMessage;
import com.juaracoding.websitearticle.utils.MappingAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/api/category")
public class CategoryArticleController {

    private CategoryArticleService categoryArticleService;

    private MappingAttribute mappingAttribute = new MappingAttribute();

    private Map<String,Object> objectMapper = new HashMap<String,Object>();

    @Autowired
    public void categoryArticleService(CategoryArticleService categoryArticleService) {
        this.categoryArticleService = categoryArticleService;
    }

    // display list of category
    @GetMapping("/show")
    public String showCategories(Model model, WebRequest request) {
        if(OtherConfig.getFlagSessionValidation().equals("y"))
        {
            mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
            if(request.getAttribute("USR_ID",1)==null){
                return "redirect:/api/check/logout";
            }
        }
        return findPaginated(1, "idCategoryArticle", "asc", model,request);
    }

    @GetMapping("/showNewCategoryForm")
    public String showNewCategoryForm(Model model, WebRequest request) {
        mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
        if(request.getAttribute("USR_ID",1)==null){
            return "redirect:/api/check/logout";
        }

        // create model attribute to bind form data
        CategoryArticle categoryArticle = new CategoryArticle();
        model.addAttribute("categoryArticle", categoryArticle);
        return "category/create_category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("categoryArticle") CategoryArticle categoryArticle,Model model, WebRequest request) {
        if(request.getAttribute("USR_ID",1)==null){
            mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
            return "redirect:/api/check/logout";
        }
        // save category to database
        objectMapper = categoryArticleService.saveCategory(categoryArticle, request);
        if(objectMapper.get("message").toString().equals(ConstantMessage.ERROR_FLOW_INVALID))//AUTO LOGOUT JIKA ADA PESAN INI
        {
            return "redirect:/api/check/logout";
        }
        return "redirect:/api/category/show";
    }

    @GetMapping("/update/{id}")
    public String editCategoryForm(@PathVariable("id") Long Id, Model model, WebRequest request) {
        if(OtherConfig.getFlagSessionValidation().equals("y")) {
            mappingAttribute.setAttribute(model, objectMapper, request);//untuk set session
            if (request.getAttribute("USR_ID", 1) == null) {
                return "redirect:/api/check/logout";
            }
        }
        model.addAttribute("categoryArticle", categoryArticleService.getCategoryById(Id));
        return "category/update_category";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable("id") Long id,
                                @ModelAttribute("categoryArticle") CategoryArticle categoryArticle,
                                Model model, WebRequest request) {
        if(OtherConfig.getFlagSessionValidation().equals("y")) {
            mappingAttribute.setAttribute(model, objectMapper, request);//untuk set session
            if (request.getAttribute("USR_ID", 1) == null) {
                return "redirect:/api/check/logout";
            }
        }
        // get category from database by id
        CategoryArticle existingCategory = categoryArticleService.getCategoryById(id);
        existingCategory.setIdCategoryArticle(id);
        existingCategory.setNameCategoryArticle(categoryArticle.getNameCategoryArticle());
        existingCategory.setStrDescCategoryArticle(categoryArticle.getStrDescCategoryArticle());

        // save updated article object
        categoryArticleService.updateCategory(existingCategory, request);
        return "redirect:/api/category/show";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model,WebRequest request) {
        mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
        if(request.getAttribute("USR_ID",1)==null){
            return "redirect:/api/check/logout";
        }

        int pageSize = 5;

        Page<CategoryArticle> page = categoryArticleService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<CategoryArticle> listCategoryArticles = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listCategoryArticles", listCategoryArticles);
        return "category/category";
    }
}
