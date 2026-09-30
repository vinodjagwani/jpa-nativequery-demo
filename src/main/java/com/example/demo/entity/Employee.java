package com.example.demo.entity;

import com.example.demo.dto.EmployeeDeptView;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
@SqlResultSetMapping(
    name = "EmployeeDeptMapping",
    classes =
        @ConstructorResult(
            targetClass = EmployeeDeptView.class,
            columns = {
              @ColumnResult(name = "id", type = Long.class),
              @ColumnResult(name = "name", type = String.class),
              @ColumnResult(name = "deptName", type = String.class)
            }))
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "dept_id")
  private Department department;

  protected Employee() {}

  public Employee(final String name, final Department department) {
    this.name = name;
    this.department = department;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Department getDepartment() {
    return department;
  }
}
