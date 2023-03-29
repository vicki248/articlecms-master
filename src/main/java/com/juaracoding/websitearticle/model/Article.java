package com.juaracoding.websitearticle.model;/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author Vicki M a.k.a. Vicki Mantovani
Java Developer
Created on 07/03/2023 20:22
@Last Modified 07/03/2023 20:22
Version 1.1
*/

import com.juaracoding.websitearticle.utils.ConstantMessage;
import org.apache.xmlbeans.impl.soap.Text;
import org.springframework.web.servlet.tags.form.TextareaTag;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.Date;

@Entity
@Table(name = "MstArticle")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDArticle")
    private Long idArticle;

    @NotEmpty(message = ConstantMessage.WARNING_TITLEARTICLE_IS_EMPTY)
    @NotNull(message = ConstantMessage.WARNING_TITLEARTICLE_IS_NULL)
    @Column(name = "TitleArticle")
    private String titleArticle;

    @Column(name = "Slug")
    private String slug;

    @NotEmpty(message = ConstantMessage.WARNING_IMGARTICLE_IS_EMPTY)
    @NotNull(message = ConstantMessage.WARNING_IMGARTICLE_IS_NULL)
    @Column(name = "ImageArticle")
    private String imageArticle;

    @NotEmpty(message = ConstantMessage.WARNING_BODYRTICLE_IS_EMPTY)
    @NotNull(message = ConstantMessage.WARNING_BODYARTICLE_IS_NULL)
    @Column(name = "BodyArticle", columnDefinition="TEXT")
    private String bodyArticle;

    /*
       start audit trails
    */
    @Column(name ="CreatedDate" , nullable = false)
    private Date createdDate = new Date();

    @Column(name = "CreatedBy", nullable = false)
    private Integer createdBy;

    @Column(name = "ModifiedDate")
    private Date modifiedDate;
    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

    @Column(name = "IsShow", nullable = false)
    private Byte isShow = 1;
    /*
        end audit trails
     */

    @ManyToOne
    @JoinColumn(name = "IDCategoryArticle")
    private CategoryArticle categoryArticle;

    public CategoryArticle getCategoryArticle() {
        return categoryArticle;
    }

    public void setCategoryArticle(CategoryArticle categoryArticle) {
        this.categoryArticle = categoryArticle;
    }

    public Long getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Long idArticle) {
        this.idArticle = idArticle;
    }

    public String getTitleArticle() {
        return titleArticle;
    }

    public void setTitleArticle(String titleArticle) {
        this.titleArticle = titleArticle;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImageArticle() {
        return imageArticle;
    }

    public void setImageArticle(String imageArticle) {
        this.imageArticle = imageArticle;
    }

    public String getBodyArticle() {
        return bodyArticle;
    }

    public void setBodyArticle(String bodyArticle) {
        this.bodyArticle = bodyArticle;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Byte getIsShow() {
        return isShow;
    }

    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }
}
