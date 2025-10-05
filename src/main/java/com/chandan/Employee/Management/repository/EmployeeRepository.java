package com.chandan.Employee.Management.repository;

import com.chandan.Employee.Management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
