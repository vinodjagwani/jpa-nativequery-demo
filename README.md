# jpa-nativequery-demo

Minimal Spring Boot demo showing how to return typed DTOs from a Spring Data JPA `@NativeQuery` using `@SqlResultSetMapping` + `@ConstructorResult`, instead of raw `Object[]` rows.

## Stack

- Java 25
- Spring Boot 4.0.3
- Spring Data JPA (`@NativeQuery`, added in 3.4)
- Gradle 9.1.0 (Kotlin DSL)
- H2 (in-memory)
- Spotless (Google Java Format) + Checkstyle + JaCoCo

## Structure

```
src/main/java/com/example/demo/
├── DemoApplication.java          # main class + demo seed data (CommandLineRunner)
├── entity/
│   ├── Employee.java             # @SqlResultSetMapping("EmployeeDeptMapping") lives here
│   └── Department.java
├── dto/
│   └── EmployeeDeptView.java     # record: id, name, deptName
├── repository/
│   ├── EmployeeRepository.java   # @NativeQuery + sqlResultSetMapping
│   └── DepartmentRepository.java
└── controller/
    └── EmployeeController.java   # GET /employees/by-dept?dept=...
```

## Run

```bash
./gradlew bootRun
```

Then:

```bash
curl "http://localhost:8080/employees/by-dept?dept=Engineering"
```

Seeded on startup: Alice + Bob in Engineering, Carol in Sales.

## Test

```bash
./gradlew check
```

Runs compile, `test`, `spotlessCheck`, `checkstyleMain`/`checkstyleTest`, and `jacocoTestReport`. Also available as `./gradlew format` (auto-fix formatting) and `./gradlew lint` (check only).

## The pattern

`EmployeeRepository.findByDepartment()` runs a native SQL join and maps the result to `EmployeeDeptView` (a record) via a `@SqlResultSetMapping` declared on `Employee`. See [article.md](article.md) for the full write-up, including two gotchas hit while building this:

1. Hibernate auto-flushes before native queries by default (when no query spaces are registered) — you usually don't need a manual `.flush()`.
2. `@DataJpaTest` loads the `@SpringBootApplication` class as config, so a `CommandLineRunner` bean declared there runs inside test contexts too unless guarded (`@Profile("!test")` here, paired with `@ActiveProfiles("test")` on the test).
