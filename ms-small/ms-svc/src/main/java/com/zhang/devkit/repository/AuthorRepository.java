package com.zhang.devkit.repository;

import com.zhang.devkit.domain.Author;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Author entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
}
