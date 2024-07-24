package com.udemy.otel_poc.repository;

import com.udemy.otel_poc.model.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {
}
