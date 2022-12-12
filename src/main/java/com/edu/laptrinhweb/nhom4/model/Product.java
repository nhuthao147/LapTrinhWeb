package com.edu.laptrinhweb.nhom4.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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

    private double price;

    private double weight;

    private String description;

    private String imageName;

    private Long quantity;
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private Collection<Bill> bills = new ArrayList<>();
}//create table mapping trong db
