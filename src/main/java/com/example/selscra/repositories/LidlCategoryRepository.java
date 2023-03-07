package com.example.selscra.repositories;

import com.example.selscra.dto_lidl.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LidlCategoryRepository extends JpaRepository<Category, Long> {
}
