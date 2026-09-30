package com.example.demo;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class DemoApplication {

  public static void main(final String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  @Profile("!test")
  CommandLineRunner seed(
      final EmployeeRepository employeeRepository,
      final DepartmentRepository departmentRepository) {
    return args -> {
      final Department engineering = departmentRepository.save(new Department("Engineering"));
      final Department sales = departmentRepository.save(new Department("Sales"));
      employeeRepository.save(new Employee("Alice", engineering));
      employeeRepository.save(new Employee("Bob", engineering));
      employeeRepository.save(new Employee("Carol", sales));
    };
  }
}
