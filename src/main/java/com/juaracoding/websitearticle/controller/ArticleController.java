package com.juaracoding.websitearticle.controller;/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author Vicki M a.k.a. Vicki Mantovani
Java Developer
Created on 07/03/2023 20:22
@Last Modified 07/03/2023 20:22
Version 1.1
*/

import com.juaracoding.websitearticle.configuration.OtherConfig;
import com.juaracoding.websitearticle.model.Article;
import com.juaracoding.websitearticle.service.ArticleService;
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
@RequestMapping("/api/article")
public class ArticleController {
    private ArticleService articleService;
    private CategoryArticleService categoryArticleService;
    private MappingAttribute mappingAttribute = new MappingAttribute();
    private Map<String,Object> objectMapper = new HashMap<String,Object>();
    @Autowired
    public ArticleController(ArticleService articleService, CategoryArticleService categoryArticleService) {
        this.articleService = articleService;
        this.categoryArticleService = categoryArticleService;
    }

    // display list of article
    @GetMapping("/show")
    public String showArticles(Model model, WebRequest request) {
        if(OtherConfig.getFlagSessionValidation().equals("y"))
        {
            mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
            if(request.getAttribute("USR_ID",1)==null){
                return "redirect:/api/check/logout";
            }
        }
        return findPaginated(1, "idArticle", "asc", model,request);
    }

    @GetMapping("/showNewArticleForm")
    public String showNewArticleForm(Model model, WebRequest request) {
        mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
        if(request.getAttribute("USR_ID",1)==null){
            return "redirect:/api/check/logout";
        }

        // create model attribute to bind form data
        Article article = new Article();
        model.addAttribute("article", article);
        model.addAttribute("listCategory", categoryArticleService.findAllCategory());
        return "article/create_article";
    }

    @PostMapping("/saveArticle")
    public String saveArticle(@ModelAttribute("article") Article article,Model model, WebRequest request) {
        if(request.getAttribute("USR_ID",1)==null){
            mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
            return "redirect:/api/check/logout";
        }
        // save article to database
        objectMapper = articleService.saveArticle(article, request);
        if(objectMapper.get("message").toString().equals(ConstantMessage.ERROR_FLOW_INVALID))//AUTO LOGOUT JIKA ADA PESAN INI
        {
            return "redirect:/api/check/logout";
        }
        return "redirect:/api/article/show";
    }

    @GetMapping("/update/{id}")
    public String editArticleForm(@PathVariable("id") Long Id, Model model, WebRequest request) {
        if(OtherConfig.getFlagSessionValidation().equals("y")) {
            mappingAttribute.setAttribute(model, objectMapper, request);//untuk set session
            if (request.getAttribute("USR_ID", 1) == null) {
                return "redirect:/api/check/logout";
            }
        }
        model.addAttribute("article", articleService.getArticleById(Id));
        model.addAttribute("listCategory", categoryArticleService.findAllCategory());
        return "article/update_article";
    }

    @PostMapping("/update/{id}")
    public String updateArticle(@PathVariable("id") Long id,
                                @ModelAttribute("article") Article article,
                                Model model, WebRequest request) {
        if(OtherConfig.getFlagSessionValidation().equals("y")) {
            mappingAttribute.setAttribute(model, objectMapper, request);//untuk set session
            if (request.getAttribute("USR_ID", 1) == null) {
                return "redirect:/api/check/logout";
            }
        }
        // get article from database by id
        Article existingArticle = articleService.getArticleById(id);
        existingArticle.setIdArticle(id);
        existingArticle.setTitleArticle(article.getTitleArticle());
        existingArticle.setSlug(article.getSlug());
        existingArticle.setImageArticle(article.getImageArticle());
        existingArticle.setBodyArticle(article.getBodyArticle());
        existingArticle.setIsShow(article.getIsShow());
        existingArticle.setCategoryArticle(article.getCategoryArticle());

        // save updated article object
        articleService.updateArticle(existingArticle, request);
        return "redirect:/api/article/show";
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

        Page<Article> page = articleService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Article> listArticles = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listArticles", listArticles);
        return "article/article";
    }
}
