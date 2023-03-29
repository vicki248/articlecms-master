package com.juaracoding.websitearticle.controller;

import cn.apiclub.captcha.Captcha;
import com.juaracoding.websitearticle.model.Userz;
import com.juaracoding.websitearticle.service.ArticleService;
import com.juaracoding.websitearticle.utils.CaptchaUtils;
import com.juaracoding.websitearticle.utils.MappingAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    private ArticleService articleService;
    private MappingAttribute mappingAttribute = new MappingAttribute();
    private Map<String,Object> objectMapper = new HashMap<String,Object>();

    @Autowired
    public MainController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String homepage(Model model, WebRequest request)
    {
        model.addAttribute("articles", articleService.getArticlesToShowInGrid());

        model.addAttribute("articlesbanner", articleService.getArticlesToShowInBanner());
        return "home";
    }


}
