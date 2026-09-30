package com.example.demo.repository;

import com.example.demo.dto.EmployeeDeptView;
import com.example.demo.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @NativeQuery(
      value =
          """
                    SELECT e.id AS id, e.name AS name, d.dept_name AS deptName
                    FROM employee e
                    JOIN department d ON e.dept_id = d.id
                    WHERE d.dept_name = :dept
                    """,
      sqlResultSetMapping = "EmployeeDeptMapping")
  List<EmployeeDeptView> findByDepartment(@Param("dept") String dept);
}
