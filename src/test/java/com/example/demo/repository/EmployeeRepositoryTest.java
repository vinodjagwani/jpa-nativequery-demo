package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.dto.EmployeeDeptView;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

  @Autowired private EmployeeRepository employeeRepository;

  @Autowired private DepartmentRepository departmentRepository;

  @Test
  @DisplayName("findByDepartment maps native query result to typed EmployeeDeptView")
  void testFindByDepartmentMapsNativeQueryResultToTypedDto() {
    seedEmployees();

    final List<EmployeeDeptView> result = employeeRepository.findByDepartment("Engineering");

    assertThat(result).hasSize(2);
    assertThat(result).extracting(EmployeeDeptView::name).containsExactlyInAnyOrder("Alice", "Bob");
    assertThat(result).allMatch(view -> view.deptName().equals("Engineering"));
    assertThat(result).allMatch(view -> view.id() != null);
  }

  private void seedEmployees() {
    final Department engineering = departmentRepository.save(new Department("Engineering"));
    final Department sales = departmentRepository.save(new Department("Sales"));
    employeeRepository.save(new Employee("Alice", engineering));
    employeeRepository.save(new Employee("Bob", engineering));
    employeeRepository.save(new Employee("Carol", sales));
    employeeRepository.flush();
  }
}
