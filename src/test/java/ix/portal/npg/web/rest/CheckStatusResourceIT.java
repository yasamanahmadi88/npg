package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.CheckStatusEntity;
import ix.portal.npg.repository.CheckStatusRepository;
import ix.portal.npg.service.criteria.CheckStatusCriteria;
import ix.portal.npg.service.dto.CheckStatusDTO;
import ix.portal.npg.service.mapper.CheckStatusMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CheckStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "checkStatus" })
class CheckStatusResourceIT {

    private static final String DEFAULT_POR_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_POR_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_POR_TECH_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_POR_TECH_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_POR_ERR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POR_ERR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_RSP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POR_RSP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_MESSAGE_FA = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_MESSAGE_FA = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS_MESSAGE_EN = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_MESSAGE_EN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/check-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckStatusRepository checkStatusRepository;

    @Autowired
    private CheckStatusMapper checkStatusMapper;

    @Autowired
    private EntityManager em;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc restCheckStatusMockMvc;

    private CheckStatusEntity checkStatusEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckStatusEntity createEntity(EntityManager em) {
        CheckStatusEntity checkStatusEntity = new CheckStatusEntity()
            .porStatus(DEFAULT_POR_STATUS)
            .porTechStatus(DEFAULT_POR_TECH_STATUS)
            .porErrCode(DEFAULT_POR_ERR_CODE)
            .porRspCode(DEFAULT_POR_RSP_CODE)
            .statusMessageFa(DEFAULT_STATUS_MESSAGE_FA)
            .statusMessageEn(DEFAULT_STATUS_MESSAGE_EN);
        return checkStatusEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckStatusEntity createUpdatedEntity(EntityManager em) {
        CheckStatusEntity checkStatusEntity = new CheckStatusEntity()
            .porStatus(UPDATED_POR_STATUS)
            .porTechStatus(UPDATED_POR_TECH_STATUS)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .statusMessageFa(UPDATED_STATUS_MESSAGE_FA)
            .statusMessageEn(UPDATED_STATUS_MESSAGE_EN);
        return checkStatusEntity;
    }

    @BeforeEach
    public void initTest() {
        jdbcTemplate.update("delete from TBL_CHECK_STATUS");
        em.clear();

        checkStatusEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckStatus() throws Exception {
        int databaseSizeBeforeCreate = checkStatusRepository.findAll().size();
        // Create the CheckStatus
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);
        restCheckStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CheckStatusEntity testCheckStatus = checkStatusList.get(checkStatusList.size() - 1);
        assertThat(testCheckStatus.getPorStatus()).isEqualTo(DEFAULT_POR_STATUS);
        assertThat(testCheckStatus.getPorTechStatus()).isEqualTo(DEFAULT_POR_TECH_STATUS);
        assertThat(testCheckStatus.getPorErrCode()).isEqualTo(DEFAULT_POR_ERR_CODE);
        assertThat(testCheckStatus.getPorRspCode()).isEqualTo(DEFAULT_POR_RSP_CODE);
        assertThat(testCheckStatus.getStatusMessageFa()).isEqualTo(DEFAULT_STATUS_MESSAGE_FA);
        assertThat(testCheckStatus.getStatusMessageEn()).isEqualTo(DEFAULT_STATUS_MESSAGE_EN);
    }

    @Test
    @Transactional
    void createCheckStatusWithExistingId() throws Exception {
        // Create the CheckStatus with an existing ID
        checkStatusEntity.setId(1L);
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);

        int databaseSizeBeforeCreate = checkStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPorStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkStatusRepository.findAll().size();
        // set the field null
        checkStatusEntity.setPorStatus(null);

        // Create the CheckStatus, which fails.
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);

        restCheckStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusMessageFaIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkStatusRepository.findAll().size();
        // set the field null
        checkStatusEntity.setStatusMessageFa(null);

        // Create the CheckStatus, which fails.
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);

        restCheckStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCheckStatuses() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList
        restCheckStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkStatusEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].porStatus").value(hasItem(DEFAULT_POR_STATUS)))
            .andExpect(jsonPath("$.[*].porTechStatus").value(hasItem(DEFAULT_POR_TECH_STATUS)))
            .andExpect(jsonPath("$.[*].porErrCode").value(hasItem(DEFAULT_POR_ERR_CODE)))
            .andExpect(jsonPath("$.[*].porRspCode").value(hasItem(DEFAULT_POR_RSP_CODE)))
            .andExpect(jsonPath("$.[*].statusMessageFa").value(hasItem(DEFAULT_STATUS_MESSAGE_FA)))
            .andExpect(jsonPath("$.[*].statusMessageEn").value(hasItem(DEFAULT_STATUS_MESSAGE_EN)));
    }

    @Test
    @Transactional
    void getCheckStatus() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get the checkStatus
        restCheckStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, checkStatusEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkStatusEntity.getId().intValue()))
            .andExpect(jsonPath("$.porStatus").value(DEFAULT_POR_STATUS))
            .andExpect(jsonPath("$.porTechStatus").value(DEFAULT_POR_TECH_STATUS))
            .andExpect(jsonPath("$.porErrCode").value(DEFAULT_POR_ERR_CODE))
            .andExpect(jsonPath("$.porRspCode").value(DEFAULT_POR_RSP_CODE))
            .andExpect(jsonPath("$.statusMessageFa").value(DEFAULT_STATUS_MESSAGE_FA))
            .andExpect(jsonPath("$.statusMessageEn").value(DEFAULT_STATUS_MESSAGE_EN));
    }

    @Test
    @Transactional
    void getCheckStatusesByIdFiltering() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        Long id = checkStatusEntity.getId();

        defaultCheckStatusShouldBeFound("id.equals=" + id);
        defaultCheckStatusShouldNotBeFound("id.notEquals=" + id);

        defaultCheckStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCheckStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultCheckStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCheckStatusShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porStatus equals to DEFAULT_POR_STATUS
        defaultCheckStatusShouldBeFound("porStatus.equals=" + DEFAULT_POR_STATUS);

        // Get all the checkStatusList where porStatus equals to UPDATED_POR_STATUS
        defaultCheckStatusShouldNotBeFound("porStatus.equals=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porStatus not equals to DEFAULT_POR_STATUS
        defaultCheckStatusShouldNotBeFound("porStatus.notEquals=" + DEFAULT_POR_STATUS);

        // Get all the checkStatusList where porStatus not equals to UPDATED_POR_STATUS
        defaultCheckStatusShouldBeFound("porStatus.notEquals=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorStatusIsInShouldWork() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porStatus in DEFAULT_POR_STATUS or UPDATED_POR_STATUS
        defaultCheckStatusShouldBeFound("porStatus.in=" + DEFAULT_POR_STATUS + "," + UPDATED_POR_STATUS);

        // Get all the checkStatusList where porStatus equals to UPDATED_POR_STATUS
        defaultCheckStatusShouldNotBeFound("porStatus.in=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porStatus is not null
        defaultCheckStatusShouldBeFound("porStatus.specified=true");

        // Get all the checkStatusList where porStatus is null
        defaultCheckStatusShouldNotBeFound("porStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorStatusContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porStatus contains DEFAULT_POR_STATUS
        defaultCheckStatusShouldBeFound("porStatus.contains=" + DEFAULT_POR_STATUS);

        // Get all the checkStatusList where porStatus contains UPDATED_POR_STATUS
        defaultCheckStatusShouldNotBeFound("porStatus.contains=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorStatusNotContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porStatus does not contain DEFAULT_POR_STATUS
        defaultCheckStatusShouldNotBeFound("porStatus.doesNotContain=" + DEFAULT_POR_STATUS);

        // Get all the checkStatusList where porStatus does not contain UPDATED_POR_STATUS
        defaultCheckStatusShouldBeFound("porStatus.doesNotContain=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorTechStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porTechStatus equals to DEFAULT_POR_TECH_STATUS
        defaultCheckStatusShouldBeFound("porTechStatus.equals=" + DEFAULT_POR_TECH_STATUS);

        // Get all the checkStatusList where porTechStatus equals to UPDATED_POR_TECH_STATUS
        defaultCheckStatusShouldNotBeFound("porTechStatus.equals=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorTechStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porTechStatus not equals to DEFAULT_POR_TECH_STATUS
        defaultCheckStatusShouldNotBeFound("porTechStatus.notEquals=" + DEFAULT_POR_TECH_STATUS);

        // Get all the checkStatusList where porTechStatus not equals to UPDATED_POR_TECH_STATUS
        defaultCheckStatusShouldBeFound("porTechStatus.notEquals=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorTechStatusIsInShouldWork() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porTechStatus in DEFAULT_POR_TECH_STATUS or UPDATED_POR_TECH_STATUS
        defaultCheckStatusShouldBeFound("porTechStatus.in=" + DEFAULT_POR_TECH_STATUS + "," + UPDATED_POR_TECH_STATUS);

        // Get all the checkStatusList where porTechStatus equals to UPDATED_POR_TECH_STATUS
        defaultCheckStatusShouldNotBeFound("porTechStatus.in=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorTechStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porTechStatus is not null
        defaultCheckStatusShouldBeFound("porTechStatus.specified=true");

        // Get all the checkStatusList where porTechStatus is null
        defaultCheckStatusShouldNotBeFound("porTechStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorTechStatusContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porTechStatus contains DEFAULT_POR_TECH_STATUS
        defaultCheckStatusShouldBeFound("porTechStatus.contains=" + DEFAULT_POR_TECH_STATUS);

        // Get all the checkStatusList where porTechStatus contains UPDATED_POR_TECH_STATUS
        defaultCheckStatusShouldNotBeFound("porTechStatus.contains=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorTechStatusNotContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porTechStatus does not contain DEFAULT_POR_TECH_STATUS
        defaultCheckStatusShouldNotBeFound("porTechStatus.doesNotContain=" + DEFAULT_POR_TECH_STATUS);

        // Get all the checkStatusList where porTechStatus does not contain UPDATED_POR_TECH_STATUS
        defaultCheckStatusShouldBeFound("porTechStatus.doesNotContain=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorErrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porErrCode equals to DEFAULT_POR_ERR_CODE
        defaultCheckStatusShouldBeFound("porErrCode.equals=" + DEFAULT_POR_ERR_CODE);

        // Get all the checkStatusList where porErrCode equals to UPDATED_POR_ERR_CODE
        defaultCheckStatusShouldNotBeFound("porErrCode.equals=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorErrCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porErrCode not equals to DEFAULT_POR_ERR_CODE
        defaultCheckStatusShouldNotBeFound("porErrCode.notEquals=" + DEFAULT_POR_ERR_CODE);

        // Get all the checkStatusList where porErrCode not equals to UPDATED_POR_ERR_CODE
        defaultCheckStatusShouldBeFound("porErrCode.notEquals=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorErrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porErrCode in DEFAULT_POR_ERR_CODE or UPDATED_POR_ERR_CODE
        defaultCheckStatusShouldBeFound("porErrCode.in=" + DEFAULT_POR_ERR_CODE + "," + UPDATED_POR_ERR_CODE);

        // Get all the checkStatusList where porErrCode equals to UPDATED_POR_ERR_CODE
        defaultCheckStatusShouldNotBeFound("porErrCode.in=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorErrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porErrCode is not null
        defaultCheckStatusShouldBeFound("porErrCode.specified=true");

        // Get all the checkStatusList where porErrCode is null
        defaultCheckStatusShouldNotBeFound("porErrCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorErrCodeContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porErrCode contains DEFAULT_POR_ERR_CODE
        defaultCheckStatusShouldBeFound("porErrCode.contains=" + DEFAULT_POR_ERR_CODE);

        // Get all the checkStatusList where porErrCode contains UPDATED_POR_ERR_CODE
        defaultCheckStatusShouldNotBeFound("porErrCode.contains=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorErrCodeNotContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porErrCode does not contain DEFAULT_POR_ERR_CODE
        defaultCheckStatusShouldNotBeFound("porErrCode.doesNotContain=" + DEFAULT_POR_ERR_CODE);

        // Get all the checkStatusList where porErrCode does not contain UPDATED_POR_ERR_CODE
        defaultCheckStatusShouldBeFound("porErrCode.doesNotContain=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorRspCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porRspCode equals to DEFAULT_POR_RSP_CODE
        defaultCheckStatusShouldBeFound("porRspCode.equals=" + DEFAULT_POR_RSP_CODE);

        // Get all the checkStatusList where porRspCode equals to UPDATED_POR_RSP_CODE
        defaultCheckStatusShouldNotBeFound("porRspCode.equals=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorRspCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porRspCode not equals to DEFAULT_POR_RSP_CODE
        defaultCheckStatusShouldNotBeFound("porRspCode.notEquals=" + DEFAULT_POR_RSP_CODE);

        // Get all the checkStatusList where porRspCode not equals to UPDATED_POR_RSP_CODE
        defaultCheckStatusShouldBeFound("porRspCode.notEquals=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorRspCodeIsInShouldWork() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porRspCode in DEFAULT_POR_RSP_CODE or UPDATED_POR_RSP_CODE
        defaultCheckStatusShouldBeFound("porRspCode.in=" + DEFAULT_POR_RSP_CODE + "," + UPDATED_POR_RSP_CODE);

        // Get all the checkStatusList where porRspCode equals to UPDATED_POR_RSP_CODE
        defaultCheckStatusShouldNotBeFound("porRspCode.in=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorRspCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porRspCode is not null
        defaultCheckStatusShouldBeFound("porRspCode.specified=true");

        // Get all the checkStatusList where porRspCode is null
        defaultCheckStatusShouldNotBeFound("porRspCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorRspCodeContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porRspCode contains DEFAULT_POR_RSP_CODE
        defaultCheckStatusShouldBeFound("porRspCode.contains=" + DEFAULT_POR_RSP_CODE);

        // Get all the checkStatusList where porRspCode contains UPDATED_POR_RSP_CODE
        defaultCheckStatusShouldNotBeFound("porRspCode.contains=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByPorRspCodeNotContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where porRspCode does not contain DEFAULT_POR_RSP_CODE
        defaultCheckStatusShouldNotBeFound("porRspCode.doesNotContain=" + DEFAULT_POR_RSP_CODE);

        // Get all the checkStatusList where porRspCode does not contain UPDATED_POR_RSP_CODE
        defaultCheckStatusShouldBeFound("porRspCode.doesNotContain=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageFaIsEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageFa equals to DEFAULT_STATUS_MESSAGE_FA
        defaultCheckStatusShouldBeFound("statusMessageFa.equals=" + DEFAULT_STATUS_MESSAGE_FA);

        // Get all the checkStatusList where statusMessageFa equals to UPDATED_STATUS_MESSAGE_FA
        defaultCheckStatusShouldNotBeFound("statusMessageFa.equals=" + UPDATED_STATUS_MESSAGE_FA);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageFaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageFa not equals to DEFAULT_STATUS_MESSAGE_FA
        defaultCheckStatusShouldNotBeFound("statusMessageFa.notEquals=" + DEFAULT_STATUS_MESSAGE_FA);

        // Get all the checkStatusList where statusMessageFa not equals to UPDATED_STATUS_MESSAGE_FA
        defaultCheckStatusShouldBeFound("statusMessageFa.notEquals=" + UPDATED_STATUS_MESSAGE_FA);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageFaIsInShouldWork() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageFa in DEFAULT_STATUS_MESSAGE_FA or UPDATED_STATUS_MESSAGE_FA
        defaultCheckStatusShouldBeFound("statusMessageFa.in=" + DEFAULT_STATUS_MESSAGE_FA + "," + UPDATED_STATUS_MESSAGE_FA);

        // Get all the checkStatusList where statusMessageFa equals to UPDATED_STATUS_MESSAGE_FA
        defaultCheckStatusShouldNotBeFound("statusMessageFa.in=" + UPDATED_STATUS_MESSAGE_FA);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageFaIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageFa is not null
        defaultCheckStatusShouldBeFound("statusMessageFa.specified=true");

        // Get all the checkStatusList where statusMessageFa is null
        defaultCheckStatusShouldNotBeFound("statusMessageFa.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageFaContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageFa contains DEFAULT_STATUS_MESSAGE_FA
        defaultCheckStatusShouldBeFound("statusMessageFa.contains=" + DEFAULT_STATUS_MESSAGE_FA);

        // Get all the checkStatusList where statusMessageFa contains UPDATED_STATUS_MESSAGE_FA
        defaultCheckStatusShouldNotBeFound("statusMessageFa.contains=" + UPDATED_STATUS_MESSAGE_FA);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageFaNotContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageFa does not contain DEFAULT_STATUS_MESSAGE_FA
        defaultCheckStatusShouldNotBeFound("statusMessageFa.doesNotContain=" + DEFAULT_STATUS_MESSAGE_FA);

        // Get all the checkStatusList where statusMessageFa does not contain UPDATED_STATUS_MESSAGE_FA
        defaultCheckStatusShouldBeFound("statusMessageFa.doesNotContain=" + UPDATED_STATUS_MESSAGE_FA);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageEnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageEn equals to DEFAULT_STATUS_MESSAGE_EN
        defaultCheckStatusShouldBeFound("statusMessageEn.equals=" + DEFAULT_STATUS_MESSAGE_EN);

        // Get all the checkStatusList where statusMessageEn equals to UPDATED_STATUS_MESSAGE_EN
        defaultCheckStatusShouldNotBeFound("statusMessageEn.equals=" + UPDATED_STATUS_MESSAGE_EN);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageEn not equals to DEFAULT_STATUS_MESSAGE_EN
        defaultCheckStatusShouldNotBeFound("statusMessageEn.notEquals=" + DEFAULT_STATUS_MESSAGE_EN);

        // Get all the checkStatusList where statusMessageEn not equals to UPDATED_STATUS_MESSAGE_EN
        defaultCheckStatusShouldBeFound("statusMessageEn.notEquals=" + UPDATED_STATUS_MESSAGE_EN);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageEnIsInShouldWork() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageEn in DEFAULT_STATUS_MESSAGE_EN or UPDATED_STATUS_MESSAGE_EN
        defaultCheckStatusShouldBeFound("statusMessageEn.in=" + DEFAULT_STATUS_MESSAGE_EN + "," + UPDATED_STATUS_MESSAGE_EN);

        // Get all the checkStatusList where statusMessageEn equals to UPDATED_STATUS_MESSAGE_EN
        defaultCheckStatusShouldNotBeFound("statusMessageEn.in=" + UPDATED_STATUS_MESSAGE_EN);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageEn is not null
        defaultCheckStatusShouldBeFound("statusMessageEn.specified=true");

        // Get all the checkStatusList where statusMessageEn is null
        defaultCheckStatusShouldNotBeFound("statusMessageEn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageEnContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageEn contains DEFAULT_STATUS_MESSAGE_EN
        defaultCheckStatusShouldBeFound("statusMessageEn.contains=" + DEFAULT_STATUS_MESSAGE_EN);

        // Get all the checkStatusList where statusMessageEn contains UPDATED_STATUS_MESSAGE_EN
        defaultCheckStatusShouldNotBeFound("statusMessageEn.contains=" + UPDATED_STATUS_MESSAGE_EN);
    }

    @Test
    @Transactional
    void getAllCheckStatusesByStatusMessageEnNotContainsSomething() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        // Get all the checkStatusList where statusMessageEn does not contain DEFAULT_STATUS_MESSAGE_EN
        defaultCheckStatusShouldNotBeFound("statusMessageEn.doesNotContain=" + DEFAULT_STATUS_MESSAGE_EN);

        // Get all the checkStatusList where statusMessageEn does not contain UPDATED_STATUS_MESSAGE_EN
        defaultCheckStatusShouldBeFound("statusMessageEn.doesNotContain=" + UPDATED_STATUS_MESSAGE_EN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckStatusShouldBeFound(String filter) throws Exception {
        restCheckStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkStatusEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].porStatus").value(hasItem(DEFAULT_POR_STATUS)))
            .andExpect(jsonPath("$.[*].porTechStatus").value(hasItem(DEFAULT_POR_TECH_STATUS)))
            .andExpect(jsonPath("$.[*].porErrCode").value(hasItem(DEFAULT_POR_ERR_CODE)))
            .andExpect(jsonPath("$.[*].porRspCode").value(hasItem(DEFAULT_POR_RSP_CODE)))
            .andExpect(jsonPath("$.[*].statusMessageFa").value(hasItem(DEFAULT_STATUS_MESSAGE_FA)))
            .andExpect(jsonPath("$.[*].statusMessageEn").value(hasItem(DEFAULT_STATUS_MESSAGE_EN)));

        // Check, that the count call also returns 1
        restCheckStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckStatusShouldNotBeFound(String filter) throws Exception {
        restCheckStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckStatusMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckStatus() throws Exception {
        // Get the checkStatus
        restCheckStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCheckStatus() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        int databaseSizeBeforeUpdate = checkStatusRepository.findAll().size();

        // Update the checkStatus
        CheckStatusEntity updatedCheckStatusEntity = checkStatusRepository.findById(checkStatusEntity.getId()).get();
        // Disconnect from session so that the updates on updatedCheckStatusEntity are not directly saved in db
        em.detach(updatedCheckStatusEntity);
        updatedCheckStatusEntity
            .porStatus(UPDATED_POR_STATUS)
            .porTechStatus(UPDATED_POR_TECH_STATUS)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .statusMessageFa(UPDATED_STATUS_MESSAGE_FA)
            .statusMessageEn(UPDATED_STATUS_MESSAGE_EN);
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(updatedCheckStatusEntity);

        restCheckStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeUpdate);
        CheckStatusEntity testCheckStatus = checkStatusList.get(checkStatusList.size() - 1);
        assertThat(testCheckStatus.getPorStatus()).isEqualTo(UPDATED_POR_STATUS);
        assertThat(testCheckStatus.getPorTechStatus()).isEqualTo(UPDATED_POR_TECH_STATUS);
        assertThat(testCheckStatus.getPorErrCode()).isEqualTo(UPDATED_POR_ERR_CODE);
        assertThat(testCheckStatus.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testCheckStatus.getStatusMessageFa()).isEqualTo(UPDATED_STATUS_MESSAGE_FA);
        assertThat(testCheckStatus.getStatusMessageEn()).isEqualTo(UPDATED_STATUS_MESSAGE_EN);
    }

    @Test
    @Transactional
    void putNonExistingCheckStatus() throws Exception {
        int databaseSizeBeforeUpdate = checkStatusRepository.findAll().size();
        checkStatusEntity.setId(count.incrementAndGet());

        // Create the CheckStatus
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckStatus() throws Exception {
        int databaseSizeBeforeUpdate = checkStatusRepository.findAll().size();
        checkStatusEntity.setId(count.incrementAndGet());

        // Create the CheckStatus
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckStatus() throws Exception {
        int databaseSizeBeforeUpdate = checkStatusRepository.findAll().size();
        checkStatusEntity.setId(count.incrementAndGet());

        // Create the CheckStatus
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckStatusMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkStatusDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCheckStatusWithPatch() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        int databaseSizeBeforeUpdate = checkStatusRepository.findAll().size();

        // Update the checkStatus using partial update
        CheckStatusEntity partialUpdatedCheckStatusEntity = new CheckStatusEntity();
        partialUpdatedCheckStatusEntity.setId(checkStatusEntity.getId());

        partialUpdatedCheckStatusEntity.porStatus(UPDATED_POR_STATUS).statusMessageFa(UPDATED_STATUS_MESSAGE_FA);

        restCheckStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckStatusEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckStatusEntity))
            )
            .andExpect(status().isOk());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeUpdate);
        CheckStatusEntity testCheckStatus = checkStatusList.get(checkStatusList.size() - 1);
        assertThat(testCheckStatus.getPorStatus()).isEqualTo(UPDATED_POR_STATUS);
        assertThat(testCheckStatus.getPorTechStatus()).isEqualTo(DEFAULT_POR_TECH_STATUS);
        assertThat(testCheckStatus.getPorErrCode()).isEqualTo(DEFAULT_POR_ERR_CODE);
        assertThat(testCheckStatus.getPorRspCode()).isEqualTo(DEFAULT_POR_RSP_CODE);
        assertThat(testCheckStatus.getStatusMessageFa()).isEqualTo(UPDATED_STATUS_MESSAGE_FA);
        assertThat(testCheckStatus.getStatusMessageEn()).isEqualTo(DEFAULT_STATUS_MESSAGE_EN);
    }

    @Test
    @Transactional
    void fullUpdateCheckStatusWithPatch() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        int databaseSizeBeforeUpdate = checkStatusRepository.findAll().size();

        // Update the checkStatus using partial update
        CheckStatusEntity partialUpdatedCheckStatusEntity = new CheckStatusEntity();
        partialUpdatedCheckStatusEntity.setId(checkStatusEntity.getId());

        partialUpdatedCheckStatusEntity
            .porStatus(UPDATED_POR_STATUS)
            .porTechStatus(UPDATED_POR_TECH_STATUS)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .statusMessageFa(UPDATED_STATUS_MESSAGE_FA)
            .statusMessageEn(UPDATED_STATUS_MESSAGE_EN);

        restCheckStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckStatusEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckStatusEntity))
            )
            .andExpect(status().isOk());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeUpdate);
        CheckStatusEntity testCheckStatus = checkStatusList.get(checkStatusList.size() - 1);
        assertThat(testCheckStatus.getPorStatus()).isEqualTo(UPDATED_POR_STATUS);
        assertThat(testCheckStatus.getPorTechStatus()).isEqualTo(UPDATED_POR_TECH_STATUS);
        assertThat(testCheckStatus.getPorErrCode()).isEqualTo(UPDATED_POR_ERR_CODE);
        assertThat(testCheckStatus.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testCheckStatus.getStatusMessageFa()).isEqualTo(UPDATED_STATUS_MESSAGE_FA);
        assertThat(testCheckStatus.getStatusMessageEn()).isEqualTo(UPDATED_STATUS_MESSAGE_EN);
    }

    @Test
    @Transactional
    void patchNonExistingCheckStatus() throws Exception {
        int databaseSizeBeforeUpdate = checkStatusRepository.findAll().size();
        checkStatusEntity.setId(count.incrementAndGet());

        // Create the CheckStatus
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckStatus() throws Exception {
        int databaseSizeBeforeUpdate = checkStatusRepository.findAll().size();
        checkStatusEntity.setId(count.incrementAndGet());

        // Create the CheckStatus
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckStatus() throws Exception {
        int databaseSizeBeforeUpdate = checkStatusRepository.findAll().size();
        checkStatusEntity.setId(count.incrementAndGet());

        // Create the CheckStatus
        CheckStatusDTO checkStatusDTO = checkStatusMapper.toDto(checkStatusEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckStatusMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(checkStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckStatus in the database
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCheckStatus() throws Exception {
        // Initialize the database
        checkStatusRepository.saveAndFlush(checkStatusEntity);

        int databaseSizeBeforeDelete = checkStatusRepository.findAll().size();

        // Delete the checkStatus
        restCheckStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkStatusEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckStatusEntity> checkStatusList = checkStatusRepository.findAll();
        assertThat(checkStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
