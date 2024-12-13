package com.java.project.FinFlow.DataRepositories;

import com.java.project.FinFlow.Tables.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {

    @Query("SELECT category_name FROM Categories WHERE id = :cid")
    public Optional<String> getCategoryById(@Param("cid") Long cid);

    @Query("SELECT id FROM Categories WHERE category_name = :cname")
    public Optional<Long> getIdByCategoryName(@Param("cname") String cname);

}
