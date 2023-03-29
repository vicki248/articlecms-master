package com.juaracoding.websitearticle.service;/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author Vicki M a.k.a. Vicki Mantovani
Java Developer
Created on 07/03/2023 20:22
@Last Modified 07/03/2023 20:22
Version 1.1
*/

import com.juaracoding.websitearticle.configuration.OtherConfig;
import com.juaracoding.websitearticle.handler.ResponseHandler;
import com.juaracoding.websitearticle.model.Article;
import com.juaracoding.websitearticle.repo.ArticleRepo;
import com.juaracoding.websitearticle.utils.ConstantMessage;
import com.juaracoding.websitearticle.utils.LoggingFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@Service
@Transactional
public class ArticleService {

    private ArticleRepo articleRepo;
    private String [] strExceptionArr = new String[2];

    @Autowired
    public ArticleService(ArticleRepo articleRepo) {
        strExceptionArr[0] = "ArticleService";
        this.articleRepo = articleRepo;
    }

    public List<Article> getArticlesToShowInGrid() {
        return articleRepo.findFirst9ByIsShowOrderByCreatedDateDesc((byte) 1);
    }

    public List<Article> getArticlesToShowInBanner() {
        return articleRepo.findFirst5ByIsShowOrderByCreatedDateDesc((byte) 1);
    }

//    public Article getArtikelBySlug(String slug) {
//        return articleRepo.findBySlug(slug);
//    }

    public Map<String, Object> saveArticle(Article article, WebRequest request) {
        Object strUserIdz = request.getAttribute("USR_ID",1);
        try {
            if(strUserIdz==null)
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_INVALID,
                        HttpStatus.NOT_ACCEPTABLE,null,"FV02001",request);
            }
            article.setCreatedBy(Integer.parseInt(strUserIdz.toString()));
            article.setCreatedDate(new Date());
            articleRepo.save(article);
        } catch (Exception e) {
            strExceptionArr[1] = "saveArticle(Article article, WebRequest request) --- LINE 58";
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_SAVE_FAILED,
                    HttpStatus.BAD_REQUEST, null, "FE02001", request);
        }
        return new ResponseHandler().generateModelAttribut(ConstantMessage.SUCCESS_SAVE,
                HttpStatus.CREATED, null, null, request);
    }

    public Article getArticleById(long id) {
        Optional<Article> optional = articleRepo.findById(id);
        Article article = null;
        if (optional.isPresent()) {
            article = optional.get();
        } else {
            throw new RuntimeException(" Article not found for id :: " + id);
        }
        return article;
    }

    public Map<String, Object> updateArticle(Article article, WebRequest request) {
        Object strUserIdz = request.getAttribute("USR_ID",1);
        try {
            if(strUserIdz==null)
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_INVALID,
                        HttpStatus.NOT_ACCEPTABLE,null,"FV02001",request);
            }
            article.setModifiedBy(Integer.parseInt(strUserIdz.toString()));
            article.setModifiedDate(new Date());
            articleRepo.save(article);
        } catch (Exception e) {
            strExceptionArr[1] = "updateArticle(Article article, WebRequest request) --- LINE 90";
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_SAVE_FAILED,
                    HttpStatus.BAD_REQUEST, null, "FE02001", request);
        }
        return new ResponseHandler().generateModelAttribut(ConstantMessage.SUCCESS_SAVE,
                HttpStatus.CREATED, null, null, request);
    }

    public Page<Article> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.articleRepo.findAll(pageable);
    }

}
