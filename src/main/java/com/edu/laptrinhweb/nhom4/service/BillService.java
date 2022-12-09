package com.edu.laptrinhweb.nhom4.service;

import com.edu.laptrinhweb.nhom4.model.Bill;
import com.edu.laptrinhweb.nhom4.model.Category;
import com.edu.laptrinhweb.nhom4.repository.BillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface BillService {

    public List<Bill> getAllBill();

    public void updateBill(Bill bill);

    public void removeBillById(Long id);

    public Optional<Bill> getBillById(Long id);
}
