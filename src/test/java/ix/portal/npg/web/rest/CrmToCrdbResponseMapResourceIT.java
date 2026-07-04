package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.CrmToCrdbResponseMapEntity;
import ix.portal.npg.repository.CrmToCrdbResponseMapRepository;
import ix.portal.npg.service.criteria.CrmToCrdbResponseMapCriteria;
import ix.portal.npg.service.dto.CrmToCrdbResponseMapDTO;
import ix.portal.npg.service.mapper.CrmToCrdbResponseMapMapper;
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
 * Integration tests for the {@link CrmToCrdbResponseMapResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "crmToCrdbResponseMap" })class CrmToCrdbResponseMapResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CRM_INTERFACE = "AAAAAAAAAA";
    private static final String UPDATED_CRM_INTERFACE = "BBBBBBBBBB";

    private static final String DEFAULT_RSP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RSP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RSP_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_RSP_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/crm-to-crdb-response-maps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CrmToCrdbResponseMapRepository crmToCrdbResponseMapRepository;

    @Autowired
    private CrmToCrdbResponseMapMapper crmToCrdbResponseMapMapper;

    @Autowired
    private EntityManager em;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc restCrmToCrdbResponseMapMockMvc;

    private CrmToCrdbResponseMapEntity crmToCrdbResponseMapEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmToCrdbResponseMapEntity createEntity(EntityManager em) {
        CrmToCrdbResponseMapEntity crmToCrdbResponseMapEntity = new CrmToCrdbResponseMapEntity()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .crmInterface(DEFAULT_CRM_INTERFACE)
            .rspCode(DEFAULT_RSP_CODE)
            .rspNote(DEFAULT_RSP_NOTE);
        return crmToCrdbResponseMapEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrmToCrdbResponseMapEntity createUpdatedEntity(EntityManager em) {
        CrmToCrdbResponseMapEntity crmToCrdbResponseMapEntity = new CrmToCrdbResponseMapEntity()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .crmInterface(UPDATED_CRM_INTERFACE)
            .rspCode(UPDATED_RSP_CODE)
            .rspNote(UPDATED_RSP_NOTE);
        return crmToCrdbResponseMapEntity;
    }

    @BeforeEach
    public void initTest() {
        jdbcTemplate.update("delete from TBL_CRM_TO_CRDB_RESPONSE_MAP");
        em.clear();

        crmToCrdbResponseMapEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createCrmToCrdbResponseMap() throws Exception {
        int databaseSizeBeforeCreate = crmToCrdbResponseMapRepository.findAll().size();
        // Create the CrmToCrdbResponseMap
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);
        restCrmToCrdbResponseMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeCreate + 1);
        CrmToCrdbResponseMapEntity testCrmToCrdbResponseMap = crmToCrdbResponseMapList.get(crmToCrdbResponseMapList.size() - 1);
        assertThat(testCrmToCrdbResponseMap.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCrmToCrdbResponseMap.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCrmToCrdbResponseMap.getCrmInterface()).isEqualTo(DEFAULT_CRM_INTERFACE);
        assertThat(testCrmToCrdbResponseMap.getRspCode()).isEqualTo(DEFAULT_RSP_CODE);
        assertThat(testCrmToCrdbResponseMap.getRspNote()).isEqualTo(DEFAULT_RSP_NOTE);
    }

    @Test
    @Transactional
    void createCrmToCrdbResponseMapWithExistingId() throws Exception {
        // Create the CrmToCrdbResponseMap with an existing ID
        crmToCrdbResponseMapEntity.setId(1L);
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        int databaseSizeBeforeCreate = crmToCrdbResponseMapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrmToCrdbResponseMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmToCrdbResponseMapRepository.findAll().size();
        // set the field null
        crmToCrdbResponseMapEntity.setCode(null);

        // Create the CrmToCrdbResponseMap, which fails.
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        restCrmToCrdbResponseMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmToCrdbResponseMapRepository.findAll().size();
        // set the field null
        crmToCrdbResponseMapEntity.setDescription(null);

        // Create the CrmToCrdbResponseMap, which fails.
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        restCrmToCrdbResponseMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCrmInterfaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmToCrdbResponseMapRepository.findAll().size();
        // set the field null
        crmToCrdbResponseMapEntity.setCrmInterface(null);

        // Create the CrmToCrdbResponseMap, which fails.
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        restCrmToCrdbResponseMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRspCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmToCrdbResponseMapRepository.findAll().size();
        // set the field null
        crmToCrdbResponseMapEntity.setRspCode(null);

        // Create the CrmToCrdbResponseMap, which fails.
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        restCrmToCrdbResponseMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRspNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = crmToCrdbResponseMapRepository.findAll().size();
        // set the field null
        crmToCrdbResponseMapEntity.setRspNote(null);

        // Create the CrmToCrdbResponseMap, which fails.
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        restCrmToCrdbResponseMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMaps() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList
        restCrmToCrdbResponseMapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmToCrdbResponseMapEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].crmInterface").value(hasItem(DEFAULT_CRM_INTERFACE)))
            .andExpect(jsonPath("$.[*].rspCode").value(hasItem(DEFAULT_RSP_CODE)))
            .andExpect(jsonPath("$.[*].rspNote").value(hasItem(DEFAULT_RSP_NOTE)));
    }

    @Test
    @Transactional
    void getCrmToCrdbResponseMap() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get the crmToCrdbResponseMap
        restCrmToCrdbResponseMapMockMvc
            .perform(get(ENTITY_API_URL_ID, crmToCrdbResponseMapEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crmToCrdbResponseMapEntity.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.crmInterface").value(DEFAULT_CRM_INTERFACE))
            .andExpect(jsonPath("$.rspCode").value(DEFAULT_RSP_CODE))
            .andExpect(jsonPath("$.rspNote").value(DEFAULT_RSP_NOTE));
    }

    @Test
    @Transactional
    void getCrmToCrdbResponseMapsByIdFiltering() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        Long id = crmToCrdbResponseMapEntity.getId();

        defaultCrmToCrdbResponseMapShouldBeFound("id.equals=" + id);
        defaultCrmToCrdbResponseMapShouldNotBeFound("id.notEquals=" + id);

        defaultCrmToCrdbResponseMapShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCrmToCrdbResponseMapShouldNotBeFound("id.greaterThan=" + id);

        defaultCrmToCrdbResponseMapShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCrmToCrdbResponseMapShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where code equals to DEFAULT_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the crmToCrdbResponseMapList where code equals to UPDATED_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where code not equals to DEFAULT_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the crmToCrdbResponseMapList where code not equals to UPDATED_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where code in DEFAULT_CODE or UPDATED_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the crmToCrdbResponseMapList where code equals to UPDATED_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where code is not null
        defaultCrmToCrdbResponseMapShouldBeFound("code.specified=true");

        // Get all the crmToCrdbResponseMapList where code is null
        defaultCrmToCrdbResponseMapShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCodeContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where code contains DEFAULT_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the crmToCrdbResponseMapList where code contains UPDATED_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where code does not contain DEFAULT_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the crmToCrdbResponseMapList where code does not contain UPDATED_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where description equals to DEFAULT_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the crmToCrdbResponseMapList where description equals to UPDATED_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where description not equals to DEFAULT_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the crmToCrdbResponseMapList where description not equals to UPDATED_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the crmToCrdbResponseMapList where description equals to UPDATED_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where description is not null
        defaultCrmToCrdbResponseMapShouldBeFound("description.specified=true");

        // Get all the crmToCrdbResponseMapList where description is null
        defaultCrmToCrdbResponseMapShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where description contains DEFAULT_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the crmToCrdbResponseMapList where description contains UPDATED_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where description does not contain DEFAULT_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the crmToCrdbResponseMapList where description does not contain UPDATED_DESCRIPTION
        defaultCrmToCrdbResponseMapShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCrmInterfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where crmInterface equals to DEFAULT_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldBeFound("crmInterface.equals=" + DEFAULT_CRM_INTERFACE);

        // Get all the crmToCrdbResponseMapList where crmInterface equals to UPDATED_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldNotBeFound("crmInterface.equals=" + UPDATED_CRM_INTERFACE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCrmInterfaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where crmInterface not equals to DEFAULT_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldNotBeFound("crmInterface.notEquals=" + DEFAULT_CRM_INTERFACE);

        // Get all the crmToCrdbResponseMapList where crmInterface not equals to UPDATED_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldBeFound("crmInterface.notEquals=" + UPDATED_CRM_INTERFACE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCrmInterfaceIsInShouldWork() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where crmInterface in DEFAULT_CRM_INTERFACE or UPDATED_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldBeFound("crmInterface.in=" + DEFAULT_CRM_INTERFACE + "," + UPDATED_CRM_INTERFACE);

        // Get all the crmToCrdbResponseMapList where crmInterface equals to UPDATED_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldNotBeFound("crmInterface.in=" + UPDATED_CRM_INTERFACE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCrmInterfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where crmInterface is not null
        defaultCrmToCrdbResponseMapShouldBeFound("crmInterface.specified=true");

        // Get all the crmToCrdbResponseMapList where crmInterface is null
        defaultCrmToCrdbResponseMapShouldNotBeFound("crmInterface.specified=false");
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCrmInterfaceContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where crmInterface contains DEFAULT_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldBeFound("crmInterface.contains=" + DEFAULT_CRM_INTERFACE);

        // Get all the crmToCrdbResponseMapList where crmInterface contains UPDATED_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldNotBeFound("crmInterface.contains=" + UPDATED_CRM_INTERFACE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByCrmInterfaceNotContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where crmInterface does not contain DEFAULT_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldNotBeFound("crmInterface.doesNotContain=" + DEFAULT_CRM_INTERFACE);

        // Get all the crmToCrdbResponseMapList where crmInterface does not contain UPDATED_CRM_INTERFACE
        defaultCrmToCrdbResponseMapShouldBeFound("crmInterface.doesNotContain=" + UPDATED_CRM_INTERFACE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspCode equals to DEFAULT_RSP_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("rspCode.equals=" + DEFAULT_RSP_CODE);

        // Get all the crmToCrdbResponseMapList where rspCode equals to UPDATED_RSP_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspCode.equals=" + UPDATED_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspCode not equals to DEFAULT_RSP_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspCode.notEquals=" + DEFAULT_RSP_CODE);

        // Get all the crmToCrdbResponseMapList where rspCode not equals to UPDATED_RSP_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("rspCode.notEquals=" + UPDATED_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspCodeIsInShouldWork() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspCode in DEFAULT_RSP_CODE or UPDATED_RSP_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("rspCode.in=" + DEFAULT_RSP_CODE + "," + UPDATED_RSP_CODE);

        // Get all the crmToCrdbResponseMapList where rspCode equals to UPDATED_RSP_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspCode.in=" + UPDATED_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspCode is not null
        defaultCrmToCrdbResponseMapShouldBeFound("rspCode.specified=true");

        // Get all the crmToCrdbResponseMapList where rspCode is null
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspCodeContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspCode contains DEFAULT_RSP_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("rspCode.contains=" + DEFAULT_RSP_CODE);

        // Get all the crmToCrdbResponseMapList where rspCode contains UPDATED_RSP_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspCode.contains=" + UPDATED_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspCodeNotContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspCode does not contain DEFAULT_RSP_CODE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspCode.doesNotContain=" + DEFAULT_RSP_CODE);

        // Get all the crmToCrdbResponseMapList where rspCode does not contain UPDATED_RSP_CODE
        defaultCrmToCrdbResponseMapShouldBeFound("rspCode.doesNotContain=" + UPDATED_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspNote equals to DEFAULT_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldBeFound("rspNote.equals=" + DEFAULT_RSP_NOTE);

        // Get all the crmToCrdbResponseMapList where rspNote equals to UPDATED_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspNote.equals=" + UPDATED_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspNote not equals to DEFAULT_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspNote.notEquals=" + DEFAULT_RSP_NOTE);

        // Get all the crmToCrdbResponseMapList where rspNote not equals to UPDATED_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldBeFound("rspNote.notEquals=" + UPDATED_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspNoteIsInShouldWork() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspNote in DEFAULT_RSP_NOTE or UPDATED_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldBeFound("rspNote.in=" + DEFAULT_RSP_NOTE + "," + UPDATED_RSP_NOTE);

        // Get all the crmToCrdbResponseMapList where rspNote equals to UPDATED_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspNote.in=" + UPDATED_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspNote is not null
        defaultCrmToCrdbResponseMapShouldBeFound("rspNote.specified=true");

        // Get all the crmToCrdbResponseMapList where rspNote is null
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspNote.specified=false");
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspNoteContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspNote contains DEFAULT_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldBeFound("rspNote.contains=" + DEFAULT_RSP_NOTE);

        // Get all the crmToCrdbResponseMapList where rspNote contains UPDATED_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspNote.contains=" + UPDATED_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllCrmToCrdbResponseMapsByRspNoteNotContainsSomething() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        // Get all the crmToCrdbResponseMapList where rspNote does not contain DEFAULT_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldNotBeFound("rspNote.doesNotContain=" + DEFAULT_RSP_NOTE);

        // Get all the crmToCrdbResponseMapList where rspNote does not contain UPDATED_RSP_NOTE
        defaultCrmToCrdbResponseMapShouldBeFound("rspNote.doesNotContain=" + UPDATED_RSP_NOTE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCrmToCrdbResponseMapShouldBeFound(String filter) throws Exception {
        restCrmToCrdbResponseMapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crmToCrdbResponseMapEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].crmInterface").value(hasItem(DEFAULT_CRM_INTERFACE)))
            .andExpect(jsonPath("$.[*].rspCode").value(hasItem(DEFAULT_RSP_CODE)))
            .andExpect(jsonPath("$.[*].rspNote").value(hasItem(DEFAULT_RSP_NOTE)));

        // Check, that the count call also returns 1
        restCrmToCrdbResponseMapMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCrmToCrdbResponseMapShouldNotBeFound(String filter) throws Exception {
        restCrmToCrdbResponseMapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCrmToCrdbResponseMapMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCrmToCrdbResponseMap() throws Exception {
        // Get the crmToCrdbResponseMap
        restCrmToCrdbResponseMapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCrmToCrdbResponseMap() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        int databaseSizeBeforeUpdate = crmToCrdbResponseMapRepository.findAll().size();

        // Update the crmToCrdbResponseMap
        CrmToCrdbResponseMapEntity updatedCrmToCrdbResponseMapEntity = crmToCrdbResponseMapRepository
            .findById(crmToCrdbResponseMapEntity.getId())
            .get();
        // Disconnect from session so that the updates on updatedCrmToCrdbResponseMapEntity are not directly saved in db
        em.detach(updatedCrmToCrdbResponseMapEntity);
        updatedCrmToCrdbResponseMapEntity
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .crmInterface(UPDATED_CRM_INTERFACE)
            .rspCode(UPDATED_RSP_CODE)
            .rspNote(UPDATED_RSP_NOTE);
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(updatedCrmToCrdbResponseMapEntity);

        restCrmToCrdbResponseMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crmToCrdbResponseMapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isOk());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeUpdate);
        CrmToCrdbResponseMapEntity testCrmToCrdbResponseMap = crmToCrdbResponseMapList.get(crmToCrdbResponseMapList.size() - 1);
        assertThat(testCrmToCrdbResponseMap.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCrmToCrdbResponseMap.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCrmToCrdbResponseMap.getCrmInterface()).isEqualTo(UPDATED_CRM_INTERFACE);
        assertThat(testCrmToCrdbResponseMap.getRspCode()).isEqualTo(UPDATED_RSP_CODE);
        assertThat(testCrmToCrdbResponseMap.getRspNote()).isEqualTo(UPDATED_RSP_NOTE);
    }

    @Test
    @Transactional
    void putNonExistingCrmToCrdbResponseMap() throws Exception {
        int databaseSizeBeforeUpdate = crmToCrdbResponseMapRepository.findAll().size();
        crmToCrdbResponseMapEntity.setId(count.incrementAndGet());

        // Create the CrmToCrdbResponseMap
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrmToCrdbResponseMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crmToCrdbResponseMapDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCrmToCrdbResponseMap() throws Exception {
        int databaseSizeBeforeUpdate = crmToCrdbResponseMapRepository.findAll().size();
        crmToCrdbResponseMapEntity.setId(count.incrementAndGet());

        // Create the CrmToCrdbResponseMap
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrmToCrdbResponseMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCrmToCrdbResponseMap() throws Exception {
        int databaseSizeBeforeUpdate = crmToCrdbResponseMapRepository.findAll().size();
        crmToCrdbResponseMapEntity.setId(count.incrementAndGet());

        // Create the CrmToCrdbResponseMap
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrmToCrdbResponseMapMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCrmToCrdbResponseMapWithPatch() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        int databaseSizeBeforeUpdate = crmToCrdbResponseMapRepository.findAll().size();

        // Update the crmToCrdbResponseMap using partial update
        CrmToCrdbResponseMapEntity partialUpdatedCrmToCrdbResponseMapEntity = new CrmToCrdbResponseMapEntity();
        partialUpdatedCrmToCrdbResponseMapEntity.setId(crmToCrdbResponseMapEntity.getId());

        partialUpdatedCrmToCrdbResponseMapEntity
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .crmInterface(UPDATED_CRM_INTERFACE)
            .rspCode(UPDATED_RSP_CODE);

        restCrmToCrdbResponseMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrmToCrdbResponseMapEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrmToCrdbResponseMapEntity))
            )
            .andExpect(status().isOk());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeUpdate);
        CrmToCrdbResponseMapEntity testCrmToCrdbResponseMap = crmToCrdbResponseMapList.get(crmToCrdbResponseMapList.size() - 1);
        assertThat(testCrmToCrdbResponseMap.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCrmToCrdbResponseMap.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCrmToCrdbResponseMap.getCrmInterface()).isEqualTo(UPDATED_CRM_INTERFACE);
        assertThat(testCrmToCrdbResponseMap.getRspCode()).isEqualTo(UPDATED_RSP_CODE);
        assertThat(testCrmToCrdbResponseMap.getRspNote()).isEqualTo(DEFAULT_RSP_NOTE);
    }

    @Test
    @Transactional
    void fullUpdateCrmToCrdbResponseMapWithPatch() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        int databaseSizeBeforeUpdate = crmToCrdbResponseMapRepository.findAll().size();

        // Update the crmToCrdbResponseMap using partial update
        CrmToCrdbResponseMapEntity partialUpdatedCrmToCrdbResponseMapEntity = new CrmToCrdbResponseMapEntity();
        partialUpdatedCrmToCrdbResponseMapEntity.setId(crmToCrdbResponseMapEntity.getId());

        partialUpdatedCrmToCrdbResponseMapEntity
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .crmInterface(UPDATED_CRM_INTERFACE)
            .rspCode(UPDATED_RSP_CODE)
            .rspNote(UPDATED_RSP_NOTE);

        restCrmToCrdbResponseMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrmToCrdbResponseMapEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrmToCrdbResponseMapEntity))
            )
            .andExpect(status().isOk());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeUpdate);
        CrmToCrdbResponseMapEntity testCrmToCrdbResponseMap = crmToCrdbResponseMapList.get(crmToCrdbResponseMapList.size() - 1);
        assertThat(testCrmToCrdbResponseMap.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCrmToCrdbResponseMap.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCrmToCrdbResponseMap.getCrmInterface()).isEqualTo(UPDATED_CRM_INTERFACE);
        assertThat(testCrmToCrdbResponseMap.getRspCode()).isEqualTo(UPDATED_RSP_CODE);
        assertThat(testCrmToCrdbResponseMap.getRspNote()).isEqualTo(UPDATED_RSP_NOTE);
    }

    @Test
    @Transactional
    void patchNonExistingCrmToCrdbResponseMap() throws Exception {
        int databaseSizeBeforeUpdate = crmToCrdbResponseMapRepository.findAll().size();
        crmToCrdbResponseMapEntity.setId(count.incrementAndGet());

        // Create the CrmToCrdbResponseMap
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrmToCrdbResponseMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, crmToCrdbResponseMapDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCrmToCrdbResponseMap() throws Exception {
        int databaseSizeBeforeUpdate = crmToCrdbResponseMapRepository.findAll().size();
        crmToCrdbResponseMapEntity.setId(count.incrementAndGet());

        // Create the CrmToCrdbResponseMap
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrmToCrdbResponseMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCrmToCrdbResponseMap() throws Exception {
        int databaseSizeBeforeUpdate = crmToCrdbResponseMapRepository.findAll().size();
        crmToCrdbResponseMapEntity.setId(count.incrementAndGet());

        // Create the CrmToCrdbResponseMap
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO = crmToCrdbResponseMapMapper.toDto(crmToCrdbResponseMapEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrmToCrdbResponseMapMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crmToCrdbResponseMapDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CrmToCrdbResponseMap in the database
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCrmToCrdbResponseMap() throws Exception {
        // Initialize the database
        crmToCrdbResponseMapRepository.saveAndFlush(crmToCrdbResponseMapEntity);

        int databaseSizeBeforeDelete = crmToCrdbResponseMapRepository.findAll().size();

        // Delete the crmToCrdbResponseMap
        restCrmToCrdbResponseMapMockMvc
            .perform(delete(ENTITY_API_URL_ID, crmToCrdbResponseMapEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CrmToCrdbResponseMapEntity> crmToCrdbResponseMapList = crmToCrdbResponseMapRepository.findAll();
        assertThat(crmToCrdbResponseMapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
