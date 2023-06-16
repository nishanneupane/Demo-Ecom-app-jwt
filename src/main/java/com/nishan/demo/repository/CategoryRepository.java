package com.nishan.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nishan.demo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
