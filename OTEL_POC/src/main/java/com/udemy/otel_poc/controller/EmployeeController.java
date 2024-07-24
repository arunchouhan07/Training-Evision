package com.udemy.otel_poc.controller;

import com.udemy.otel_poc.model.Employee;
import com.udemy.otel_poc.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;



@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    Logger logger= LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository repository;

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        logger.info("Employee created");
        return repository.save(employee);
    }
    @GetMapping("/{id}")
    public Optional<Employee> findById(@PathVariable String id) {
        logger.info("Employee found by id {}", id);
        return repository.findById(id);
    }

    @GetMapping
    public Iterable<Employee> findAll() {
        logger.info("Employee found");
        return repository.findAll();
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        employee.setId(id);
        logger.info("Employee updated with id {}", id);
        return repository.save(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        logger.info("Employee deleted with id {}", id);
        repository.deleteById(id);
    }
}