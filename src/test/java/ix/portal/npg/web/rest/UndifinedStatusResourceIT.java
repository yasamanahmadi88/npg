/*
package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.UndifinedStatusEntity;
import ix.portal.npg.repository.UndifinedStatusRepository;
import ix.portal.npg.service.criteria.UndifinedStatusCriteria;
import ix.portal.npg.service.dto.UndifinedStatusDTO;
import ix.portal.npg.service.mapper.UndifinedStatusMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

*/
/**
 * Integration tests for the {@link UndifinedStatusResource} REST controller.
 *//*

@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "undifinedStatus" })
class UndifinedStatusResourceIT {

    private static final String DEFAULT_POR_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_POR_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_POR_TECH_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_POR_TECH_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_POR_ERR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POR_ERR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_RSP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POR_RSP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_POR_REQUEST_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSERT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSERT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/undifined-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UndifinedStatusRepository undifinedStatusRepository;

    @Autowired
    private UndifinedStatusMapper undifinedStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUndifinedStatusMockMvc;

    private UndifinedStatusEntity undifinedStatusEntity;

    */
/**
 * Create an entity for this test.
 *
 * This is a static method, as tests for other entities might also need it,
 * if they test an entity which requires the current entity.
 *//*

    public static UndifinedStatusEntity createEntity(EntityManager em) {
        UndifinedStatusEntity undifinedStatusEntity = new UndifinedStatusEntity()
            .porStatus(DEFAULT_POR_STATUS)
            .porTechStatus(DEFAULT_POR_TECH_STATUS)
            .porErrCode(DEFAULT_POR_ERR_CODE)
            .porRspCode(DEFAULT_POR_RSP_CODE)
            .porRequestId(DEFAULT_POR_REQUEST_ID)
            .insertDate(DEFAULT_INSERT_DATE);
        return undifinedStatusEntity;
    }

    */
/**
 * Create an updated entity for this test.
 *
 * This is a static method, as tests for other entities might also need it,
 * if they test an entity which requires the current entity.
 *//*

    public static UndifinedStatusEntity createUpdatedEntity(EntityManager em) {
        UndifinedStatusEntity undifinedStatusEntity = new UndifinedStatusEntity()
            .porStatus(UPDATED_POR_STATUS)
            .porTechStatus(UPDATED_POR_TECH_STATUS)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .porRequestId(UPDATED_POR_REQUEST_ID)
            .insertDate(UPDATED_INSERT_DATE);
        return undifinedStatusEntity;
    }

    @BeforeEach
    public void initTest() {
undifinedStatusEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createUndifinedStatus() throws Exception {
        int databaseSizeBeforeCreate = undifinedStatusRepository.findAll().size();
        // Create the UndifinedStatus
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(undifinedStatusEntity);
        restUndifinedStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeCreate + 1);
        UndifinedStatusEntity testUndifinedStatus = undifinedStatusList.get(undifinedStatusList.size() - 1);
        assertThat(testUndifinedStatus.getPorStatus()).isEqualTo(DEFAULT_POR_STATUS);
        assertThat(testUndifinedStatus.getPorTechStatus()).isEqualTo(DEFAULT_POR_TECH_STATUS);
        assertThat(testUndifinedStatus.getPorErrCode()).isEqualTo(DEFAULT_POR_ERR_CODE);
        assertThat(testUndifinedStatus.getPorRspCode()).isEqualTo(DEFAULT_POR_RSP_CODE);
        assertThat(testUndifinedStatus.getPorRequestId()).isEqualTo(DEFAULT_POR_REQUEST_ID);
        assertThat(testUndifinedStatus.getInsertDate()).isEqualTo(DEFAULT_INSERT_DATE);
    }

    @Test
    @Transactional
    void createUndifinedStatusWithExistingId() throws Exception {
        // Create the UndifinedStatus with an existing ID
        undifinedStatusEntity.setId(1L);
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(undifinedStatusEntity);

        int databaseSizeBeforeCreate = undifinedStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUndifinedStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInsertDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = undifinedStatusRepository.findAll().size();
        // set the field null
        undifinedStatusEntity.setInsertDate(null);

        // Create the UndifinedStatus, which fails.
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(undifinedStatusEntity);

        restUndifinedStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUndifinedStatuses() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList
        restUndifinedStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(undifinedStatusEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].porStatus").value(hasItem(DEFAULT_POR_STATUS)))
            .andExpect(jsonPath("$.[*].porTechStatus").value(hasItem(DEFAULT_POR_TECH_STATUS)))
            .andExpect(jsonPath("$.[*].porErrCode").value(hasItem(DEFAULT_POR_ERR_CODE)))
            .andExpect(jsonPath("$.[*].porRspCode").value(hasItem(DEFAULT_POR_RSP_CODE)))
            .andExpect(jsonPath("$.[*].porRequestId").value(hasItem(DEFAULT_POR_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].insertDate").value(hasItem(DEFAULT_INSERT_DATE.toString())));
    }

    @Test
    @Transactional
    void getUndifinedStatus() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get the undifinedStatus
        restUndifinedStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, undifinedStatusEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(undifinedStatusEntity.getId().intValue()))
            .andExpect(jsonPath("$.porStatus").value(DEFAULT_POR_STATUS))
            .andExpect(jsonPath("$.porTechStatus").value(DEFAULT_POR_TECH_STATUS))
            .andExpect(jsonPath("$.porErrCode").value(DEFAULT_POR_ERR_CODE))
            .andExpect(jsonPath("$.porRspCode").value(DEFAULT_POR_RSP_CODE))
            .andExpect(jsonPath("$.porRequestId").value(DEFAULT_POR_REQUEST_ID))
            .andExpect(jsonPath("$.insertDate").value(DEFAULT_INSERT_DATE.toString()));
    }

    @Test
    @Transactional
    void getUndifinedStatusesByIdFiltering() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        Long id = undifinedStatusEntity.getId();

        defaultUndifinedStatusShouldBeFound("id.equals=" + id);
        defaultUndifinedStatusShouldNotBeFound("id.notEquals=" + id);

        defaultUndifinedStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUndifinedStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultUndifinedStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUndifinedStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porStatus equals to DEFAULT_POR_STATUS
        defaultUndifinedStatusShouldBeFound("porStatus.equals=" + DEFAULT_POR_STATUS);

        // Get all the undifinedStatusList where porStatus equals to UPDATED_POR_STATUS
        defaultUndifinedStatusShouldNotBeFound("porStatus.equals=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porStatus not equals to DEFAULT_POR_STATUS
        defaultUndifinedStatusShouldNotBeFound("porStatus.notEquals=" + DEFAULT_POR_STATUS);

        // Get all the undifinedStatusList where porStatus not equals to UPDATED_POR_STATUS
        defaultUndifinedStatusShouldBeFound("porStatus.notEquals=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorStatusIsInShouldWork() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porStatus in DEFAULT_POR_STATUS or UPDATED_POR_STATUS
        defaultUndifinedStatusShouldBeFound("porStatus.in=" + DEFAULT_POR_STATUS + "," + UPDATED_POR_STATUS);

        // Get all the undifinedStatusList where porStatus equals to UPDATED_POR_STATUS
        defaultUndifinedStatusShouldNotBeFound("porStatus.in=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porStatus is not null
        defaultUndifinedStatusShouldBeFound("porStatus.specified=true");

        // Get all the undifinedStatusList where porStatus is null
        defaultUndifinedStatusShouldNotBeFound("porStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorStatusContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porStatus contains DEFAULT_POR_STATUS
        defaultUndifinedStatusShouldBeFound("porStatus.contains=" + DEFAULT_POR_STATUS);

        // Get all the undifinedStatusList where porStatus contains UPDATED_POR_STATUS
        defaultUndifinedStatusShouldNotBeFound("porStatus.contains=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorStatusNotContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porStatus does not contain DEFAULT_POR_STATUS
        defaultUndifinedStatusShouldNotBeFound("porStatus.doesNotContain=" + DEFAULT_POR_STATUS);

        // Get all the undifinedStatusList where porStatus does not contain UPDATED_POR_STATUS
        defaultUndifinedStatusShouldBeFound("porStatus.doesNotContain=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorTechStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porTechStatus equals to DEFAULT_POR_TECH_STATUS
        defaultUndifinedStatusShouldBeFound("porTechStatus.equals=" + DEFAULT_POR_TECH_STATUS);

        // Get all the undifinedStatusList where porTechStatus equals to UPDATED_POR_TECH_STATUS
        defaultUndifinedStatusShouldNotBeFound("porTechStatus.equals=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorTechStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porTechStatus not equals to DEFAULT_POR_TECH_STATUS
        defaultUndifinedStatusShouldNotBeFound("porTechStatus.notEquals=" + DEFAULT_POR_TECH_STATUS);

        // Get all the undifinedStatusList where porTechStatus not equals to UPDATED_POR_TECH_STATUS
        defaultUndifinedStatusShouldBeFound("porTechStatus.notEquals=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorTechStatusIsInShouldWork() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porTechStatus in DEFAULT_POR_TECH_STATUS or UPDATED_POR_TECH_STATUS
        defaultUndifinedStatusShouldBeFound("porTechStatus.in=" + DEFAULT_POR_TECH_STATUS + "," + UPDATED_POR_TECH_STATUS);

        // Get all the undifinedStatusList where porTechStatus equals to UPDATED_POR_TECH_STATUS
        defaultUndifinedStatusShouldNotBeFound("porTechStatus.in=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorTechStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porTechStatus is not null
        defaultUndifinedStatusShouldBeFound("porTechStatus.specified=true");

        // Get all the undifinedStatusList where porTechStatus is null
        defaultUndifinedStatusShouldNotBeFound("porTechStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorTechStatusContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porTechStatus contains DEFAULT_POR_TECH_STATUS
        defaultUndifinedStatusShouldBeFound("porTechStatus.contains=" + DEFAULT_POR_TECH_STATUS);

        // Get all the undifinedStatusList where porTechStatus contains UPDATED_POR_TECH_STATUS
        defaultUndifinedStatusShouldNotBeFound("porTechStatus.contains=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorTechStatusNotContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porTechStatus does not contain DEFAULT_POR_TECH_STATUS
        defaultUndifinedStatusShouldNotBeFound("porTechStatus.doesNotContain=" + DEFAULT_POR_TECH_STATUS);

        // Get all the undifinedStatusList where porTechStatus does not contain UPDATED_POR_TECH_STATUS
        defaultUndifinedStatusShouldBeFound("porTechStatus.doesNotContain=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorErrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porErrCode equals to DEFAULT_POR_ERR_CODE
        defaultUndifinedStatusShouldBeFound("porErrCode.equals=" + DEFAULT_POR_ERR_CODE);

        // Get all the undifinedStatusList where porErrCode equals to UPDATED_POR_ERR_CODE
        defaultUndifinedStatusShouldNotBeFound("porErrCode.equals=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorErrCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porErrCode not equals to DEFAULT_POR_ERR_CODE
        defaultUndifinedStatusShouldNotBeFound("porErrCode.notEquals=" + DEFAULT_POR_ERR_CODE);

        // Get all the undifinedStatusList where porErrCode not equals to UPDATED_POR_ERR_CODE
        defaultUndifinedStatusShouldBeFound("porErrCode.notEquals=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorErrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porErrCode in DEFAULT_POR_ERR_CODE or UPDATED_POR_ERR_CODE
        defaultUndifinedStatusShouldBeFound("porErrCode.in=" + DEFAULT_POR_ERR_CODE + "," + UPDATED_POR_ERR_CODE);

        // Get all the undifinedStatusList where porErrCode equals to UPDATED_POR_ERR_CODE
        defaultUndifinedStatusShouldNotBeFound("porErrCode.in=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorErrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porErrCode is not null
        defaultUndifinedStatusShouldBeFound("porErrCode.specified=true");

        // Get all the undifinedStatusList where porErrCode is null
        defaultUndifinedStatusShouldNotBeFound("porErrCode.specified=false");
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorErrCodeContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porErrCode contains DEFAULT_POR_ERR_CODE
        defaultUndifinedStatusShouldBeFound("porErrCode.contains=" + DEFAULT_POR_ERR_CODE);

        // Get all the undifinedStatusList where porErrCode contains UPDATED_POR_ERR_CODE
        defaultUndifinedStatusShouldNotBeFound("porErrCode.contains=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorErrCodeNotContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porErrCode does not contain DEFAULT_POR_ERR_CODE
        defaultUndifinedStatusShouldNotBeFound("porErrCode.doesNotContain=" + DEFAULT_POR_ERR_CODE);

        // Get all the undifinedStatusList where porErrCode does not contain UPDATED_POR_ERR_CODE
        defaultUndifinedStatusShouldBeFound("porErrCode.doesNotContain=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRspCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRspCode equals to DEFAULT_POR_RSP_CODE
        defaultUndifinedStatusShouldBeFound("porRspCode.equals=" + DEFAULT_POR_RSP_CODE);

        // Get all the undifinedStatusList where porRspCode equals to UPDATED_POR_RSP_CODE
        defaultUndifinedStatusShouldNotBeFound("porRspCode.equals=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRspCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRspCode not equals to DEFAULT_POR_RSP_CODE
        defaultUndifinedStatusShouldNotBeFound("porRspCode.notEquals=" + DEFAULT_POR_RSP_CODE);

        // Get all the undifinedStatusList where porRspCode not equals to UPDATED_POR_RSP_CODE
        defaultUndifinedStatusShouldBeFound("porRspCode.notEquals=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRspCodeIsInShouldWork() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRspCode in DEFAULT_POR_RSP_CODE or UPDATED_POR_RSP_CODE
        defaultUndifinedStatusShouldBeFound("porRspCode.in=" + DEFAULT_POR_RSP_CODE + "," + UPDATED_POR_RSP_CODE);

        // Get all the undifinedStatusList where porRspCode equals to UPDATED_POR_RSP_CODE
        defaultUndifinedStatusShouldNotBeFound("porRspCode.in=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRspCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRspCode is not null
        defaultUndifinedStatusShouldBeFound("porRspCode.specified=true");

        // Get all the undifinedStatusList where porRspCode is null
        defaultUndifinedStatusShouldNotBeFound("porRspCode.specified=false");
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRspCodeContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRspCode contains DEFAULT_POR_RSP_CODE
        defaultUndifinedStatusShouldBeFound("porRspCode.contains=" + DEFAULT_POR_RSP_CODE);

        // Get all the undifinedStatusList where porRspCode contains UPDATED_POR_RSP_CODE
        defaultUndifinedStatusShouldNotBeFound("porRspCode.contains=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRspCodeNotContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRspCode does not contain DEFAULT_POR_RSP_CODE
        defaultUndifinedStatusShouldNotBeFound("porRspCode.doesNotContain=" + DEFAULT_POR_RSP_CODE);

        // Get all the undifinedStatusList where porRspCode does not contain UPDATED_POR_RSP_CODE
        defaultUndifinedStatusShouldBeFound("porRspCode.doesNotContain=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRequestIdIsEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRequestId equals to DEFAULT_POR_REQUEST_ID
        defaultUndifinedStatusShouldBeFound("porRequestId.equals=" + DEFAULT_POR_REQUEST_ID);

        // Get all the undifinedStatusList where porRequestId equals to UPDATED_POR_REQUEST_ID
        defaultUndifinedStatusShouldNotBeFound("porRequestId.equals=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRequestIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRequestId not equals to DEFAULT_POR_REQUEST_ID
        defaultUndifinedStatusShouldNotBeFound("porRequestId.notEquals=" + DEFAULT_POR_REQUEST_ID);

        // Get all the undifinedStatusList where porRequestId not equals to UPDATED_POR_REQUEST_ID
        defaultUndifinedStatusShouldBeFound("porRequestId.notEquals=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRequestIdIsInShouldWork() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRequestId in DEFAULT_POR_REQUEST_ID or UPDATED_POR_REQUEST_ID
        defaultUndifinedStatusShouldBeFound("porRequestId.in=" + DEFAULT_POR_REQUEST_ID + "," + UPDATED_POR_REQUEST_ID);

        // Get all the undifinedStatusList where porRequestId equals to UPDATED_POR_REQUEST_ID
        defaultUndifinedStatusShouldNotBeFound("porRequestId.in=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRequestIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRequestId is not null
        defaultUndifinedStatusShouldBeFound("porRequestId.specified=true");

        // Get all the undifinedStatusList where porRequestId is null
        defaultUndifinedStatusShouldNotBeFound("porRequestId.specified=false");
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRequestIdContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRequestId contains DEFAULT_POR_REQUEST_ID
        defaultUndifinedStatusShouldBeFound("porRequestId.contains=" + DEFAULT_POR_REQUEST_ID);

        // Get all the undifinedStatusList where porRequestId contains UPDATED_POR_REQUEST_ID
        defaultUndifinedStatusShouldNotBeFound("porRequestId.contains=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByPorRequestIdNotContainsSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where porRequestId does not contain DEFAULT_POR_REQUEST_ID
        defaultUndifinedStatusShouldNotBeFound("porRequestId.doesNotContain=" + DEFAULT_POR_REQUEST_ID);

        // Get all the undifinedStatusList where porRequestId does not contain UPDATED_POR_REQUEST_ID
        defaultUndifinedStatusShouldBeFound("porRequestId.doesNotContain=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByInsertDateIsEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where insertDate equals to DEFAULT_INSERT_DATE
        defaultUndifinedStatusShouldBeFound("insertDate.equals=" + DEFAULT_INSERT_DATE);

        // Get all the undifinedStatusList where insertDate equals to UPDATED_INSERT_DATE
        defaultUndifinedStatusShouldNotBeFound("insertDate.equals=" + UPDATED_INSERT_DATE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByInsertDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where insertDate not equals to DEFAULT_INSERT_DATE
        defaultUndifinedStatusShouldNotBeFound("insertDate.notEquals=" + DEFAULT_INSERT_DATE);

        // Get all the undifinedStatusList where insertDate not equals to UPDATED_INSERT_DATE
        defaultUndifinedStatusShouldBeFound("insertDate.notEquals=" + UPDATED_INSERT_DATE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByInsertDateIsInShouldWork() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where insertDate in DEFAULT_INSERT_DATE or UPDATED_INSERT_DATE
        defaultUndifinedStatusShouldBeFound("insertDate.in=" + DEFAULT_INSERT_DATE + "," + UPDATED_INSERT_DATE);

        // Get all the undifinedStatusList where insertDate equals to UPDATED_INSERT_DATE
        defaultUndifinedStatusShouldNotBeFound("insertDate.in=" + UPDATED_INSERT_DATE);
    }

    @Test
    @Transactional
    void getAllUndifinedStatusesByInsertDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        // Get all the undifinedStatusList where insertDate is not null
        defaultUndifinedStatusShouldBeFound("insertDate.specified=true");

        // Get all the undifinedStatusList where insertDate is null
        defaultUndifinedStatusShouldNotBeFound("insertDate.specified=false");
    }

    */
/**
 * Executes the search, and checks that the default entity is returned.
 *//*

    private void defaultUndifinedStatusShouldBeFound(String filter) throws Exception {
        restUndifinedStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(undifinedStatusEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].porStatus").value(hasItem(DEFAULT_POR_STATUS)))
            .andExpect(jsonPath("$.[*].porTechStatus").value(hasItem(DEFAULT_POR_TECH_STATUS)))
            .andExpect(jsonPath("$.[*].porErrCode").value(hasItem(DEFAULT_POR_ERR_CODE)))
            .andExpect(jsonPath("$.[*].porRspCode").value(hasItem(DEFAULT_POR_RSP_CODE)))
            .andExpect(jsonPath("$.[*].porRequestId").value(hasItem(DEFAULT_POR_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].insertDate").value(hasItem(DEFAULT_INSERT_DATE.toString())));

        // Check, that the count call also returns 1
        restUndifinedStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    */
/**
 * Executes the search, and checks that the default entity is not returned.
 *//*

    private void defaultUndifinedStatusShouldNotBeFound(String filter) throws Exception {
        restUndifinedStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUndifinedStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUndifinedStatus() throws Exception {
        // Get the undifinedStatus
        restUndifinedStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUndifinedStatus() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        int databaseSizeBeforeUpdate = undifinedStatusRepository.findAll().size();

        // Update the undifinedStatus
        UndifinedStatusEntity updatedUndifinedStatusEntity = undifinedStatusRepository.findById(undifinedStatusEntity.getId()).get();
        // Disconnect from session so that the updates on updatedUndifinedStatusEntity are not directly saved in db
        em.detach(updatedUndifinedStatusEntity);
        updatedUndifinedStatusEntity
            .porStatus(UPDATED_POR_STATUS)
            .porTechStatus(UPDATED_POR_TECH_STATUS)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .porRequestId(UPDATED_POR_REQUEST_ID)
            .insertDate(UPDATED_INSERT_DATE);
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(updatedUndifinedStatusEntity);

        restUndifinedStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, undifinedStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeUpdate);
        UndifinedStatusEntity testUndifinedStatus = undifinedStatusList.get(undifinedStatusList.size() - 1);
        assertThat(testUndifinedStatus.getPorStatus()).isEqualTo(UPDATED_POR_STATUS);
        assertThat(testUndifinedStatus.getPorTechStatus()).isEqualTo(UPDATED_POR_TECH_STATUS);
        assertThat(testUndifinedStatus.getPorErrCode()).isEqualTo(UPDATED_POR_ERR_CODE);
        assertThat(testUndifinedStatus.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testUndifinedStatus.getPorRequestId()).isEqualTo(UPDATED_POR_REQUEST_ID);
        assertThat(testUndifinedStatus.getInsertDate()).isEqualTo(UPDATED_INSERT_DATE);
    }

    @Test
    @Transactional
    void putNonExistingUndifinedStatus() throws Exception {
        int databaseSizeBeforeUpdate = undifinedStatusRepository.findAll().size();
        undifinedStatusEntity.setId(count.incrementAndGet());

        // Create the UndifinedStatus
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(undifinedStatusEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUndifinedStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, undifinedStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUndifinedStatus() throws Exception {
        int databaseSizeBeforeUpdate = undifinedStatusRepository.findAll().size();
        undifinedStatusEntity.setId(count.incrementAndGet());

        // Create the UndifinedStatus
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(undifinedStatusEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUndifinedStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUndifinedStatus() throws Exception {
        int databaseSizeBeforeUpdate = undifinedStatusRepository.findAll().size();
        undifinedStatusEntity.setId(count.incrementAndGet());

        // Create the UndifinedStatus
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(undifinedStatusEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUndifinedStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUndifinedStatusWithPatch() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        int databaseSizeBeforeUpdate = undifinedStatusRepository.findAll().size();

        // Update the undifinedStatus using partial update
        UndifinedStatusEntity partialUpdatedUndifinedStatusEntity = new UndifinedStatusEntity();
        partialUpdatedUndifinedStatusEntity.setId(undifinedStatusEntity.getId());

        partialUpdatedUndifinedStatusEntity.porTechStatus(UPDATED_POR_TECH_STATUS).porRspCode(UPDATED_POR_RSP_CODE);

        restUndifinedStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUndifinedStatusEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUndifinedStatusEntity))
            )
            .andExpect(status().isOk());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeUpdate);
        UndifinedStatusEntity testUndifinedStatus = undifinedStatusList.get(undifinedStatusList.size() - 1);
        assertThat(testUndifinedStatus.getPorStatus()).isEqualTo(DEFAULT_POR_STATUS);
        assertThat(testUndifinedStatus.getPorTechStatus()).isEqualTo(UPDATED_POR_TECH_STATUS);
        assertThat(testUndifinedStatus.getPorErrCode()).isEqualTo(DEFAULT_POR_ERR_CODE);
        assertThat(testUndifinedStatus.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testUndifinedStatus.getPorRequestId()).isEqualTo(DEFAULT_POR_REQUEST_ID);
        assertThat(testUndifinedStatus.getInsertDate()).isEqualTo(DEFAULT_INSERT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateUndifinedStatusWithPatch() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        int databaseSizeBeforeUpdate = undifinedStatusRepository.findAll().size();

        // Update the undifinedStatus using partial update
        UndifinedStatusEntity partialUpdatedUndifinedStatusEntity = new UndifinedStatusEntity();
        partialUpdatedUndifinedStatusEntity.setId(undifinedStatusEntity.getId());

        partialUpdatedUndifinedStatusEntity
            .porStatus(UPDATED_POR_STATUS)
            .porTechStatus(UPDATED_POR_TECH_STATUS)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .porRequestId(UPDATED_POR_REQUEST_ID)
            .insertDate(UPDATED_INSERT_DATE);

        restUndifinedStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUndifinedStatusEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUndifinedStatusEntity))
            )
            .andExpect(status().isOk());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeUpdate);
        UndifinedStatusEntity testUndifinedStatus = undifinedStatusList.get(undifinedStatusList.size() - 1);
        assertThat(testUndifinedStatus.getPorStatus()).isEqualTo(UPDATED_POR_STATUS);
        assertThat(testUndifinedStatus.getPorTechStatus()).isEqualTo(UPDATED_POR_TECH_STATUS);
        assertThat(testUndifinedStatus.getPorErrCode()).isEqualTo(UPDATED_POR_ERR_CODE);
        assertThat(testUndifinedStatus.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testUndifinedStatus.getPorRequestId()).isEqualTo(UPDATED_POR_REQUEST_ID);
        assertThat(testUndifinedStatus.getInsertDate()).isEqualTo(UPDATED_INSERT_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingUndifinedStatus() throws Exception {
        int databaseSizeBeforeUpdate = undifinedStatusRepository.findAll().size();
        undifinedStatusEntity.setId(count.incrementAndGet());

        // Create the UndifinedStatus
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(undifinedStatusEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUndifinedStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, undifinedStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUndifinedStatus() throws Exception {
        int databaseSizeBeforeUpdate = undifinedStatusRepository.findAll().size();
        undifinedStatusEntity.setId(count.incrementAndGet());

        // Create the UndifinedStatus
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(undifinedStatusEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUndifinedStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUndifinedStatus() throws Exception {
        int databaseSizeBeforeUpdate = undifinedStatusRepository.findAll().size();
        undifinedStatusEntity.setId(count.incrementAndGet());

        // Create the UndifinedStatus
        UndifinedStatusDTO undifinedStatusDTO = undifinedStatusMapper.toDto(undifinedStatusEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUndifinedStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(undifinedStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UndifinedStatus in the database
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUndifinedStatus() throws Exception {
        // Initialize the database
        undifinedStatusRepository.saveAndFlush(undifinedStatusEntity);

        int databaseSizeBeforeDelete = undifinedStatusRepository.findAll().size();

        // Delete the undifinedStatus
        restUndifinedStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, undifinedStatusEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UndifinedStatusEntity> undifinedStatusList = undifinedStatusRepository.findAll();
        assertThat(undifinedStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/
