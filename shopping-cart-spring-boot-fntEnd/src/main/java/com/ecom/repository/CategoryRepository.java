package com.ecom.repository;

import com.ecom.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("select c from Category c")
    public List<Category> findAllCategory();

    @Query(value= "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM Category WHERE Category.name=:name", nativeQuery = true)
    public Integer countByCategoryName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "insert into Category(id, name, image_name, is_active) values(:id,:name,:imageName,:isActive)", nativeQuery = true)
    public int save(@Param("id") int id,@Param("name") String name,@Param("imageName") String imageName,@Param("isActive") boolean isActive );

    @Transactional
    @Modifying
    @Query(value = "update Category c set c.name=:name, c.image_name=:imageName, c.is_active=:isActive where c.id=:id",nativeQuery = true)
    public int updateCategory(@Param("id") int id,@Param("name") String name,@Param("imageName") String imageName,@Param("isActive") boolean isActive );

    public List<Category> findByIsActiveTrue();
}
