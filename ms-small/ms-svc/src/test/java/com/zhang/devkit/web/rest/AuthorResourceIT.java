package com.zhang.devkit.web.rest;

import com.zhang.devkit.MssdemoApp;
import com.zhang.devkit.domain.Author;
import com.zhang.devkit.repository.AuthorRepository;
import com.zhang.devkit.service.AuthorService;
import com.zhang.devkit.service.dto.AuthorCriteria;
import com.zhang.devkit.service.AuthorQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AuthorResource} REST controller.
 */
@SpringBootTest(classes = MssdemoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AuthorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorQueryService authorQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthorMockMvc;

    private Author author;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Author createEntity(EntityManager em) {
        Author author = new Author()
            .name(DEFAULT_NAME)
            .birthDate(DEFAULT_BIRTH_DATE);
        return author;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Author createUpdatedEntity(EntityManager em) {
        Author author = new Author()
            .name(UPDATED_NAME)
            .birthDate(UPDATED_BIRTH_DATE);
        return author;
    }

    @BeforeEach
    public void initTest() {
        author = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuthor() throws Exception {
        int databaseSizeBeforeCreate = authorRepository.findAll().size();
        // Create the Author
        restAuthorMockMvc.perform(post("/api/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isCreated());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate + 1);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuthor.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void createAuthorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = authorRepository.findAll().size();

        // Create the Author with an existing ID
        author.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorMockMvc.perform(post("/api/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAuthors() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList
        restAuthorMockMvc.perform(get("/api/authors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(author.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get the author
        restAuthorMockMvc.perform(get("/api/authors/{id}", author.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(author.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()));
    }


    @Test
    @Transactional
    public void getAuthorsByIdFiltering() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        Long id = author.getId();

        defaultAuthorShouldBeFound("id.equals=" + id);
        defaultAuthorShouldNotBeFound("id.notEquals=" + id);

        defaultAuthorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuthorShouldNotBeFound("id.greaterThan=" + id);

        defaultAuthorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuthorShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAuthorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name equals to DEFAULT_NAME
        defaultAuthorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the authorList where name equals to UPDATED_NAME
        defaultAuthorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthorsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name not equals to DEFAULT_NAME
        defaultAuthorShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the authorList where name not equals to UPDATED_NAME
        defaultAuthorShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAuthorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the authorList where name equals to UPDATED_NAME
        defaultAuthorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name is not null
        defaultAuthorShouldBeFound("name.specified=true");

        // Get all the authorList where name is null
        defaultAuthorShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllAuthorsByNameContainsSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name contains DEFAULT_NAME
        defaultAuthorShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the authorList where name contains UPDATED_NAME
        defaultAuthorShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllAuthorsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where name does not contain DEFAULT_NAME
        defaultAuthorShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the authorList where name does not contain UPDATED_NAME
        defaultAuthorShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllAuthorsByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultAuthorShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the authorList where birthDate equals to UPDATED_BIRTH_DATE
        defaultAuthorShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllAuthorsByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultAuthorShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the authorList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultAuthorShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllAuthorsByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultAuthorShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the authorList where birthDate equals to UPDATED_BIRTH_DATE
        defaultAuthorShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllAuthorsByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where birthDate is not null
        defaultAuthorShouldBeFound("birthDate.specified=true");

        // Get all the authorList where birthDate is null
        defaultAuthorShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAuthorsByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultAuthorShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the authorList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultAuthorShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllAuthorsByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultAuthorShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the authorList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultAuthorShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllAuthorsByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultAuthorShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the authorList where birthDate is less than UPDATED_BIRTH_DATE
        defaultAuthorShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllAuthorsByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultAuthorShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the authorList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultAuthorShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuthorShouldBeFound(String filter) throws Exception {
        restAuthorMockMvc.perform(get("/api/authors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(author.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())));

        // Check, that the count call also returns 1
        restAuthorMockMvc.perform(get("/api/authors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuthorShouldNotBeFound(String filter) throws Exception {
        restAuthorMockMvc.perform(get("/api/authors?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuthorMockMvc.perform(get("/api/authors/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAuthor() throws Exception {
        // Get the author
        restAuthorMockMvc.perform(get("/api/authors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuthor() throws Exception {
        // Initialize the database
        authorService.save(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author
        Author updatedAuthor = authorRepository.findById(author.getId()).get();
        // Disconnect from session so that the updates on updatedAuthor are not directly saved in db
        em.detach(updatedAuthor);
        updatedAuthor
            .name(UPDATED_NAME)
            .birthDate(UPDATED_BIRTH_DATE);

        restAuthorMockMvc.perform(put("/api/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuthor)))
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuthor.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorMockMvc.perform(put("/api/authors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(author)))
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAuthor() throws Exception {
        // Initialize the database
        authorService.save(author);

        int databaseSizeBeforeDelete = authorRepository.findAll().size();

        // Delete the author
        restAuthorMockMvc.perform(delete("/api/authors/{id}", author.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
