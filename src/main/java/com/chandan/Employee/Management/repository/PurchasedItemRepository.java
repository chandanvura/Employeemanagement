package com.chandan.Employee.Management.repository;

import com.chandan.Employee.Management.model.PurchasedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchasedItemRepository extends JpaRepository<PurchasedItem, Long> {
    List<PurchasedItem> findByEmployeeId(Long employeeId);
}
