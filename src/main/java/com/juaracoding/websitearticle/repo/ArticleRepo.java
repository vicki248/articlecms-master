package com.juaracoding.websitearticle.repo;/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author Vicki M a.k.a. Vicki Mantovani
Java Developer
Created on 07/03/2023 20:22
@Last Modified 07/03/2023 20:22
Version 1.1
*/

import com.juaracoding.websitearticle.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article,Long> {
    List<Article> findFirst9ByIsShowOrderByCreatedDateDesc(byte isShow);

    List<Article> findFirst5ByIsShowOrderByCreatedDateDesc(byte isShow);

//    Article findBySlug(String slug);

}

