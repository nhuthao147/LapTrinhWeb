package com.edu.laptrinhweb.nhom4.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY)
    private Set<Bill_Product> bill_productList;

    private double price;

    private double weight;

    private String description;

    private String imageName;

    private Long quantity;
}//create table mapping trong db
