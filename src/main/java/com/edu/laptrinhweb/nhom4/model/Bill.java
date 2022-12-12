package com.edu.laptrinhweb.nhom4.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String address1;
    private String address2;
    private String zip;
    private String city;
    private String phone;
    private String email;
    private String addition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bill_product", joinColumns = @JoinColumn(name = "bill_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Collection<Product> products = new ArrayList<>();

    private double total;


}