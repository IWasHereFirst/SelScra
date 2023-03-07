package com.example.selscra.repositories;

import com.example.selscra.dto_lidl.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LidlSubCategoryRepository extends JpaRepository<SubCategory, Long> {
}
