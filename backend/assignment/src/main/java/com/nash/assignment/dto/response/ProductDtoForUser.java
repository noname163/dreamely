package com.nash.assignment.dto.response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.nash.assignment.constant.ProductStatus;

// @Builder
public class ProductDtoForUser {
    private long id;
    @NotBlank(message = "Name Cannot Empty")
    private String name;
    @Min(value = 1, message = "Price Cannot Empty")
    private String price;

    private ProductStatus status;

    private String description;

    @NotBlank(message = "Categories Cannot Empty")
    private String categories;

    private String image;
    private List<ImageProductDto> images = new ArrayList<>();



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImageProductDto> getImages() {
        return images;
    }

    public void setImages(List<ImageProductDto> images) {
        this.images = images;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    
}
