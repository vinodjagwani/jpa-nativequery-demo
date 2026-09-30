package com.example.demo.controller;

import com.example.demo.dto.EmployeeDeptView;
import com.example.demo.repository.EmployeeRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeRepository employeeRepository;

  public EmployeeController(final EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @GetMapping("/by-dept")
  public List<EmployeeDeptView> byDept(@RequestParam final String dept) {
    return employeeRepository.findByDepartment(dept);
  }
}
