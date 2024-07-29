package com.ecom.repository;

import com.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByIsActiveTrue();

	//Using JPQL (Recommended)
	@Query(value = "select p from Product p where p.category=:category and p.isActive=true")
	List<Product> findByIsActiveTrue(@Param("category") String category);

	//TODO
	// Note:-Using Native SQL(you can't use p in Native Query)
	//	@Query(value = "select * from Product where category = :category and is_active = true", nativeQuery = true)
	//	List<Product> findByIsActiveTrue(@Param("category") String category);


}
