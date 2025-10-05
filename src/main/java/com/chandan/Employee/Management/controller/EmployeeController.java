package com.chandan.Employee.Management.controller;

import com.chandan.Employee.Management.model.Employee;
import com.chandan.Employee.Management.model.PurchasedItem;
import com.chandan.Employee.Management.repository.EmployeeRepository;
import com.chandan.Employee.Management.repository.PurchasedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepo;
    @Autowired private PurchasedItemRepository purchasedRepo;
    @Autowired private RestTemplate restTemplate;

    @GetMapping
    public List<Employee> getAll() { return employeeRepo.findAll(); }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee e) { return employeeRepo.save(e); }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) { employeeRepo.deleteById(id); }

    @GetMapping("/{id}/purchases")
    public List<PurchasedItem> getPurchases(@PathVariable Long id) {
        return purchasedRepo.findByEmployeeId(id);
    }

    // Example endpoint to purchase an item from Store microservice
    @PostMapping("/{id}/purchase")
    public String purchase(@PathVariable Long id, @RequestParam String type, @RequestParam String name) {
        String url = "http://localhost:8081/purchase/type=" + type + "/name=" + name + "/employee/" + id;
        String response = restTemplate.postForObject(url, null, String.class);

        PurchasedItem item = new PurchasedItem();
        item.setItemName(name);
        item.setItemType(type);
        item.setEmployeeId(id);
        purchasedRepo.save(item);

        return response;
    }
}

