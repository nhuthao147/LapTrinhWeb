package com.edu.laptrinhweb.nhom4.model;

import javax.persistence.*;

@Entity
@Table(name = "bill_product")
@IdClass(BillProductId.class)
public class Bill_Product {

    @Id
    @ManyToOne
    @JoinColumn(name = "bill_id", referencedColumnName = "id")
    private Bill bill;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "quantity")
    private Long quantity;

}
