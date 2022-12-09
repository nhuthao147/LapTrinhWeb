package com.edu.laptrinhweb.nhom4.global;

import com.edu.laptrinhweb.nhom4.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    //tao bien toan cuc
    public static List<Product> cart;

    static {
        cart = new ArrayList<>();
    }

}
