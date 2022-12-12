package com.edu.laptrinhweb.nhom4.service.impl;

import com.edu.laptrinhweb.nhom4.model.Bill;
import com.edu.laptrinhweb.nhom4.model.Product;
import com.edu.laptrinhweb.nhom4.repository.BillRepository;
import com.edu.laptrinhweb.nhom4.service.BillService;
import com.edu.laptrinhweb.nhom4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BillServiceImpl implements BillService {
    @Autowired
    BillRepository billRepository;

    @Override
    public List<Bill> getAllBill() {
        return billRepository.findAll();
    }//findAll

    @Override
    public void updateBill(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public void removeBillById(Long id) {
        billRepository.deleteById(id);
    }

    @Override
    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }//search theo id



    @Autowired
    ProductService productService;

    @Override
    public Bill saveSill(Bill bill, List<Product> cart) {
        Bill newBill = new Bill();
        newBill.setEmail(bill.getEmail());
        newBill.setUser(bill.getUser());
        newBill.setTotal(bill.getTotal());
        newBill.setFirstName(bill.getFirstName());
        newBill.setLastName(bill.getLastName());
        newBill.setAddition(bill.getAddition());
        newBill.setCity(bill.getCity());
        newBill.setAddress1(bill.getAddress1());
        newBill.setAddress2(bill.getAddress2());
        newBill.setPhone(bill.getPhone());

        newBill.getProducts().addAll(cart.stream().map(p -> {
            Product pp = productService.getProductById(p.getId()).get();
            pp.getBills().add(newBill);
            return pp;
        }).collect(Collectors.toList()));
        return billRepository.save(newBill);
    }
}