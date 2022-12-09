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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_bill", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "bill_id"))
    private Set<Bill> bills;

    private double price;

    private double weight;

    private String description;

    private String imageName;

}//create table mapping trong db
