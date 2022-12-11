package com.edu.laptrinhweb.nhom4.service.impl;

import com.edu.laptrinhweb.nhom4.model.Bill;
import com.edu.laptrinhweb.nhom4.repository.BillRepository;
import com.edu.laptrinhweb.nhom4.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
}