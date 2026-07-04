package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.NotificationTemplateEntity;
import ix.portal.npg.repository.NotificationTemplateRepository;
import ix.portal.npg.service.criteria.NotificationTemplateCriteria;
import ix.portal.npg.service.dto.NotificationTemplateDTO;
import ix.portal.npg.service.mapper.NotificationTemplateMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NotificationTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "notificationTemplate" })class NotificationTemplateResourceIT {

    private static final String DEFAULT_TEMPLATE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/notification-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NotificationTemplateRepository notificationTemplateRepository;

    @Autowired
    private NotificationTemplateMapper notificationTemplateMapper;

    @Autowired
    private EntityManager em;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc restNotificationTemplateMockMvc;

    private NotificationTemplateEntity notificationTemplateEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationTemplateEntity createEntity(EntityManager em) {
        NotificationTemplateEntity notificationTemplateEntity = new NotificationTemplateEntity()
            .templateCode(DEFAULT_TEMPLATE_CODE)
            .language(DEFAULT_LANGUAGE)
            .content(DEFAULT_CONTENT)
            .type(DEFAULT_TYPE);
        return notificationTemplateEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationTemplateEntity createUpdatedEntity(EntityManager em) {
        NotificationTemplateEntity notificationTemplateEntity = new NotificationTemplateEntity()
            .templateCode(UPDATED_TEMPLATE_CODE)
            .language(UPDATED_LANGUAGE)
            .content(UPDATED_CONTENT)
            .type(UPDATED_TYPE);
        return notificationTemplateEntity;
    }

    @BeforeEach
    public void initTest() {
        jdbcTemplate.update("delete from TBL_NOTIFICATION_TEMPLATE");
        em.clear();

        notificationTemplateEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createNotificationTemplate() throws Exception {
        int databaseSizeBeforeCreate = notificationTemplateRepository.findAll().size();
        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplateEntity);
        restNotificationTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationTemplateEntity testNotificationTemplate = notificationTemplateList.get(notificationTemplateList.size() - 1);
        assertThat(testNotificationTemplate.getTemplateCode()).isEqualTo(DEFAULT_TEMPLATE_CODE);
        assertThat(testNotificationTemplate.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testNotificationTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testNotificationTemplate.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createNotificationTemplateWithExistingId() throws Exception {
        // Create the NotificationTemplate with an existing ID
        notificationTemplateEntity.setId(1L);
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplateEntity);

        int databaseSizeBeforeCreate = notificationTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNotificationTemplates() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList
        restNotificationTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationTemplateEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateCode").value(hasItem(DEFAULT_TEMPLATE_CODE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));
    }

    @Test
    @Transactional
    void getNotificationTemplate() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get the notificationTemplate
        restNotificationTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, notificationTemplateEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationTemplateEntity.getId().intValue()))
            .andExpect(jsonPath("$.templateCode").value(DEFAULT_TEMPLATE_CODE))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE));
    }

    @Test
    @Transactional
    void getNotificationTemplatesByIdFiltering() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        Long id = notificationTemplateEntity.getId();

        defaultNotificationTemplateShouldBeFound("id.equals=" + id);
        defaultNotificationTemplateShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationTemplateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationTemplateShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationTemplateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationTemplateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTemplateCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where templateCode equals to DEFAULT_TEMPLATE_CODE
        defaultNotificationTemplateShouldBeFound("templateCode.equals=" + DEFAULT_TEMPLATE_CODE);

        // Get all the notificationTemplateList where templateCode equals to UPDATED_TEMPLATE_CODE
        defaultNotificationTemplateShouldNotBeFound("templateCode.equals=" + UPDATED_TEMPLATE_CODE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTemplateCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where templateCode not equals to DEFAULT_TEMPLATE_CODE
        defaultNotificationTemplateShouldNotBeFound("templateCode.notEquals=" + DEFAULT_TEMPLATE_CODE);

        // Get all the notificationTemplateList where templateCode not equals to UPDATED_TEMPLATE_CODE
        defaultNotificationTemplateShouldBeFound("templateCode.notEquals=" + UPDATED_TEMPLATE_CODE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTemplateCodeIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where templateCode in DEFAULT_TEMPLATE_CODE or UPDATED_TEMPLATE_CODE
        defaultNotificationTemplateShouldBeFound("templateCode.in=" + DEFAULT_TEMPLATE_CODE + "," + UPDATED_TEMPLATE_CODE);

        // Get all the notificationTemplateList where templateCode equals to UPDATED_TEMPLATE_CODE
        defaultNotificationTemplateShouldNotBeFound("templateCode.in=" + UPDATED_TEMPLATE_CODE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTemplateCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where templateCode is not null
        defaultNotificationTemplateShouldBeFound("templateCode.specified=true");

        // Get all the notificationTemplateList where templateCode is null
        defaultNotificationTemplateShouldNotBeFound("templateCode.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTemplateCodeContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where templateCode contains DEFAULT_TEMPLATE_CODE
        defaultNotificationTemplateShouldBeFound("templateCode.contains=" + DEFAULT_TEMPLATE_CODE);

        // Get all the notificationTemplateList where templateCode contains UPDATED_TEMPLATE_CODE
        defaultNotificationTemplateShouldNotBeFound("templateCode.contains=" + UPDATED_TEMPLATE_CODE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTemplateCodeNotContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where templateCode does not contain DEFAULT_TEMPLATE_CODE
        defaultNotificationTemplateShouldNotBeFound("templateCode.doesNotContain=" + DEFAULT_TEMPLATE_CODE);

        // Get all the notificationTemplateList where templateCode does not contain UPDATED_TEMPLATE_CODE
        defaultNotificationTemplateShouldBeFound("templateCode.doesNotContain=" + UPDATED_TEMPLATE_CODE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByLanguageIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where language equals to DEFAULT_LANGUAGE
        defaultNotificationTemplateShouldBeFound("language.equals=" + DEFAULT_LANGUAGE);

        // Get all the notificationTemplateList where language equals to UPDATED_LANGUAGE
        defaultNotificationTemplateShouldNotBeFound("language.equals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByLanguageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where language not equals to DEFAULT_LANGUAGE
        defaultNotificationTemplateShouldNotBeFound("language.notEquals=" + DEFAULT_LANGUAGE);

        // Get all the notificationTemplateList where language not equals to UPDATED_LANGUAGE
        defaultNotificationTemplateShouldBeFound("language.notEquals=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByLanguageIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where language in DEFAULT_LANGUAGE or UPDATED_LANGUAGE
        defaultNotificationTemplateShouldBeFound("language.in=" + DEFAULT_LANGUAGE + "," + UPDATED_LANGUAGE);

        // Get all the notificationTemplateList where language equals to UPDATED_LANGUAGE
        defaultNotificationTemplateShouldNotBeFound("language.in=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByLanguageIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where language is not null
        defaultNotificationTemplateShouldBeFound("language.specified=true");

        // Get all the notificationTemplateList where language is null
        defaultNotificationTemplateShouldNotBeFound("language.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByLanguageContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where language contains DEFAULT_LANGUAGE
        defaultNotificationTemplateShouldBeFound("language.contains=" + DEFAULT_LANGUAGE);

        // Get all the notificationTemplateList where language contains UPDATED_LANGUAGE
        defaultNotificationTemplateShouldNotBeFound("language.contains=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByLanguageNotContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where language does not contain DEFAULT_LANGUAGE
        defaultNotificationTemplateShouldNotBeFound("language.doesNotContain=" + DEFAULT_LANGUAGE);

        // Get all the notificationTemplateList where language does not contain UPDATED_LANGUAGE
        defaultNotificationTemplateShouldBeFound("language.doesNotContain=" + UPDATED_LANGUAGE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where content equals to DEFAULT_CONTENT
        defaultNotificationTemplateShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the notificationTemplateList where content equals to UPDATED_CONTENT
        defaultNotificationTemplateShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where content not equals to DEFAULT_CONTENT
        defaultNotificationTemplateShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);

        // Get all the notificationTemplateList where content not equals to UPDATED_CONTENT
        defaultNotificationTemplateShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByContentIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultNotificationTemplateShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the notificationTemplateList where content equals to UPDATED_CONTENT
        defaultNotificationTemplateShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where content is not null
        defaultNotificationTemplateShouldBeFound("content.specified=true");

        // Get all the notificationTemplateList where content is null
        defaultNotificationTemplateShouldNotBeFound("content.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByContentContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where content contains DEFAULT_CONTENT
        defaultNotificationTemplateShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the notificationTemplateList where content contains UPDATED_CONTENT
        defaultNotificationTemplateShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByContentNotContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where content does not contain DEFAULT_CONTENT
        defaultNotificationTemplateShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the notificationTemplateList where content does not contain UPDATED_CONTENT
        defaultNotificationTemplateShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where type equals to DEFAULT_TYPE
        defaultNotificationTemplateShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the notificationTemplateList where type equals to UPDATED_TYPE
        defaultNotificationTemplateShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where type not equals to DEFAULT_TYPE
        defaultNotificationTemplateShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the notificationTemplateList where type not equals to UPDATED_TYPE
        defaultNotificationTemplateShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultNotificationTemplateShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the notificationTemplateList where type equals to UPDATED_TYPE
        defaultNotificationTemplateShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where type is not null
        defaultNotificationTemplateShouldBeFound("type.specified=true");

        // Get all the notificationTemplateList where type is null
        defaultNotificationTemplateShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where type contains DEFAULT_TYPE
        defaultNotificationTemplateShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the notificationTemplateList where type contains UPDATED_TYPE
        defaultNotificationTemplateShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllNotificationTemplatesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        // Get all the notificationTemplateList where type does not contain DEFAULT_TYPE
        defaultNotificationTemplateShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the notificationTemplateList where type does not contain UPDATED_TYPE
        defaultNotificationTemplateShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationTemplateShouldBeFound(String filter) throws Exception {
        restNotificationTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationTemplateEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateCode").value(hasItem(DEFAULT_TEMPLATE_CODE)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)));

        // Check, that the count call also returns 1
        restNotificationTemplateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationTemplateShouldNotBeFound(String filter) throws Exception {
        restNotificationTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationTemplateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNotificationTemplate() throws Exception {
        // Get the notificationTemplate
        restNotificationTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNotificationTemplate() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();

        // Update the notificationTemplate
        NotificationTemplateEntity updatedNotificationTemplateEntity = notificationTemplateRepository
            .findById(notificationTemplateEntity.getId())
            .get();
        // Disconnect from session so that the updates on updatedNotificationTemplateEntity are not directly saved in db
        em.detach(updatedNotificationTemplateEntity);
        updatedNotificationTemplateEntity
            .templateCode(UPDATED_TEMPLATE_CODE)
            .language(UPDATED_LANGUAGE)
            .content(UPDATED_CONTENT)
            .type(UPDATED_TYPE);
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(updatedNotificationTemplateEntity);

        restNotificationTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
        NotificationTemplateEntity testNotificationTemplate = notificationTemplateList.get(notificationTemplateList.size() - 1);
        assertThat(testNotificationTemplate.getTemplateCode()).isEqualTo(UPDATED_TEMPLATE_CODE);
        assertThat(testNotificationTemplate.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testNotificationTemplate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNotificationTemplate.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplateEntity.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplateEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, notificationTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplateEntity.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplateEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplateEntity.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplateEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNotificationTemplateWithPatch() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();

        // Update the notificationTemplate using partial update
        NotificationTemplateEntity partialUpdatedNotificationTemplateEntity = new NotificationTemplateEntity();
        partialUpdatedNotificationTemplateEntity.setId(notificationTemplateEntity.getId());

        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationTemplateEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationTemplateEntity))
            )
            .andExpect(status().isOk());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
        NotificationTemplateEntity testNotificationTemplate = notificationTemplateList.get(notificationTemplateList.size() - 1);
        assertThat(testNotificationTemplate.getTemplateCode()).isEqualTo(DEFAULT_TEMPLATE_CODE);
        assertThat(testNotificationTemplate.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testNotificationTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testNotificationTemplate.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateNotificationTemplateWithPatch() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();

        // Update the notificationTemplate using partial update
        NotificationTemplateEntity partialUpdatedNotificationTemplateEntity = new NotificationTemplateEntity();
        partialUpdatedNotificationTemplateEntity.setId(notificationTemplateEntity.getId());

        partialUpdatedNotificationTemplateEntity
            .templateCode(UPDATED_TEMPLATE_CODE)
            .language(UPDATED_LANGUAGE)
            .content(UPDATED_CONTENT)
            .type(UPDATED_TYPE);

        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNotificationTemplateEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNotificationTemplateEntity))
            )
            .andExpect(status().isOk());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
        NotificationTemplateEntity testNotificationTemplate = notificationTemplateList.get(notificationTemplateList.size() - 1);
        assertThat(testNotificationTemplate.getTemplateCode()).isEqualTo(UPDATED_TEMPLATE_CODE);
        assertThat(testNotificationTemplate.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testNotificationTemplate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNotificationTemplate.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplateEntity.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplateEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, notificationTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplateEntity.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplateEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNotificationTemplate() throws Exception {
        int databaseSizeBeforeUpdate = notificationTemplateRepository.findAll().size();
        notificationTemplateEntity.setId(count.incrementAndGet());

        // Create the NotificationTemplate
        NotificationTemplateDTO notificationTemplateDTO = notificationTemplateMapper.toDto(notificationTemplateEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNotificationTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(notificationTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NotificationTemplate in the database
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNotificationTemplate() throws Exception {
        // Initialize the database
        notificationTemplateRepository.saveAndFlush(notificationTemplateEntity);

        int databaseSizeBeforeDelete = notificationTemplateRepository.findAll().size();

        // Delete the notificationTemplate
        restNotificationTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, notificationTemplateEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationTemplateEntity> notificationTemplateList = notificationTemplateRepository.findAll();
        assertThat(notificationTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
