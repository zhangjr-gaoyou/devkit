package com.zhang.devkit.service.impl;

import com.zhang.devkit.service.AuthorService;
import com.zhang.devkit.domain.Author;
import com.zhang.devkit.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Author}.
 */
@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {
        log.debug("Request to save Author : {}", author);
        return authorRepository.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Author> findAll(Pageable pageable) {
        log.debug("Request to get all Authors");
        return authorRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findOne(Long id) {
        log.debug("Request to get Author : {}", id);
        return authorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Author : {}", id);
        authorRepository.deleteById(id);
    }
}
