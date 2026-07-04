//package ix.portal.npg.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import ix.portal.npg.IntegrationTest;
//import ix.portal.npg.domain.FileReportGenerationLogEntity;
//import ix.portal.npg.repository.FileReportGenerationLogRepository;
//import ix.portal.npg.service.criteria.FileReportGenerationLogCriteria;
//import ix.portal.npg.service.dto.FileReportGenerationLogDTO;
//import ix.portal.npg.service.mapper.FileReportGenerationLogMapper;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link FileReportGenerationLogResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class FileReportGenerationLogResourceIT {
//
//    private static final String DEFAULT_REPORT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_REPORT_NAME = "BBBBBBBBBB";
//
//    private static final LocalDateTime DEFAULT_REPORT_DATE = 1L;
//    private static final Long UPDATED_REPORT_DATE = 2L;
//    private static final Long SMALLER_REPORT_DATE = 1L - 1L;
//
//    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";
//
//    private static final Long DEFAULT_ROW_NUMBER = 1L;
//    private static final Long UPDATED_ROW_NUMBER = 2L;
//    private static final Long SMALLER_ROW_NUMBER = 1L - 1L;
//
//    private static final String DEFAULT_POR_NUMBER = "AAAAAAAAAA";
//    private static final String UPDATED_POR_NUMBER = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
//    private static final String UPDATED_CONTENT = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/file-report-generation-logs";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private FileReportGenerationLogRepository fileReportGenerationLogRepository;
//
//    @Autowired
//    private FileReportGenerationLogMapper fileReportGenerationLogMapper;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restFileReportGenerationLogMockMvc;
//
//    private FileReportGenerationLogEntity fileReportGenerationLogEntity;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static FileReportGenerationLogEntity createEntity(EntityManager em) {
//        FileReportGenerationLogEntity fileReportGenerationLogEntity = new FileReportGenerationLogEntity()
//            .reportName(DEFAULT_REPORT_NAME)
//            .reportDate(DEFAULT_REPORT_DATE)
//            .fileName(DEFAULT_FILE_NAME)
//            .rowNumber(DEFAULT_ROW_NUMBER)
//            .porNumber(DEFAULT_POR_NUMBER)
//            .content(DEFAULT_CONTENT);
//        return fileReportGenerationLogEntity;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static FileReportGenerationLogEntity createUpdatedEntity(EntityManager em) {
//        FileReportGenerationLogEntity fileReportGenerationLogEntity = new FileReportGenerationLogEntity()
//            .reportName(UPDATED_REPORT_NAME)
//            .reportDate(UPDATED_REPORT_DATE)
//            .fileName(UPDATED_FILE_NAME)
//            .rowNumber(UPDATED_ROW_NUMBER)
//            .porNumber(UPDATED_POR_NUMBER)
//            .content(UPDATED_CONTENT);
//        return fileReportGenerationLogEntity;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        fileReportGenerationLogEntity = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createFileReportGenerationLog() throws Exception {
//        int databaseSizeBeforeCreate = fileReportGenerationLogRepository.findAll().size();
//        // Create the FileReportGenerationLog
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//        restFileReportGenerationLogMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeCreate + 1);
//        FileReportGenerationLogEntity testFileReportGenerationLog = fileReportGenerationLogList.get(fileReportGenerationLogList.size() - 1);
//        assertThat(testFileReportGenerationLog.getReportName()).isEqualTo(DEFAULT_REPORT_NAME);
//        assertThat(testFileReportGenerationLog.getReportDate()).isEqualTo(DEFAULT_REPORT_DATE);
//        assertThat(testFileReportGenerationLog.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
//        assertThat(testFileReportGenerationLog.getRowNumber()).isEqualTo(DEFAULT_ROW_NUMBER);
//        assertThat(testFileReportGenerationLog.getPorNumber()).isEqualTo(DEFAULT_POR_NUMBER);
//        assertThat(testFileReportGenerationLog.getContent()).isEqualTo(DEFAULT_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    void createFileReportGenerationLogWithExistingId() throws Exception {
//        // Create the FileReportGenerationLog with an existing ID
//        fileReportGenerationLogEntity.setId(1L);
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        int databaseSizeBeforeCreate = fileReportGenerationLogRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restFileReportGenerationLogMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkReportNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fileReportGenerationLogRepository.findAll().size();
//        // set the field null
//        fileReportGenerationLogEntity.setReportName(null);
//
//        // Create the FileReportGenerationLog, which fails.
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        restFileReportGenerationLogMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkReportDateIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fileReportGenerationLogRepository.findAll().size();
//        // set the field null
//        fileReportGenerationLogEntity.setReportDate(null);
//
//        // Create the FileReportGenerationLog, which fails.
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        restFileReportGenerationLogMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkFileNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fileReportGenerationLogRepository.findAll().size();
//        // set the field null
//        fileReportGenerationLogEntity.setFileName(null);
//
//        // Create the FileReportGenerationLog, which fails.
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        restFileReportGenerationLogMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkRowNumberIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fileReportGenerationLogRepository.findAll().size();
//        // set the field null
//        fileReportGenerationLogEntity.setRowNumber(null);
//
//        // Create the FileReportGenerationLog, which fails.
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        restFileReportGenerationLogMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkPorNumberIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fileReportGenerationLogRepository.findAll().size();
//        // set the field null
//        fileReportGenerationLogEntity.setPorNumber(null);
//
//        // Create the FileReportGenerationLog, which fails.
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        restFileReportGenerationLogMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkContentIsRequired() throws Exception {
//        int databaseSizeBeforeTest = fileReportGenerationLogRepository.findAll().size();
//        // set the field null
//        fileReportGenerationLogEntity.setContent(null);
//
//        // Create the FileReportGenerationLog, which fails.
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        restFileReportGenerationLogMockMvc
//            .perform(
//                post(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogs() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList
//        restFileReportGenerationLogMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(fileReportGenerationLogEntity.getId().intValue())))
//            .andExpect(jsonPath("$.[*].reportName").value(hasItem(DEFAULT_REPORT_NAME)))
//            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.intValue())))
//            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
//            .andExpect(jsonPath("$.[*].rowNumber").value(hasItem(DEFAULT_ROW_NUMBER.intValue())))
//            .andExpect(jsonPath("$.[*].porNumber").value(hasItem(DEFAULT_POR_NUMBER)))
//            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
//    }
//
//    @Test
//    @Transactional
//    void getFileReportGenerationLog() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get the fileReportGenerationLog
//        restFileReportGenerationLogMockMvc
//            .perform(get(ENTITY_API_URL_ID, fileReportGenerationLogEntity.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(fileReportGenerationLogEntity.getId().intValue()))
//            .andExpect(jsonPath("$.reportName").value(DEFAULT_REPORT_NAME))
//            .andExpect(jsonPath("$.reportDate").value(DEFAULT_REPORT_DATE.intValue()))
//            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
//            .andExpect(jsonPath("$.rowNumber").value(DEFAULT_ROW_NUMBER.intValue()))
//            .andExpect(jsonPath("$.porNumber").value(DEFAULT_POR_NUMBER))
//            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT));
//    }
//
//    @Test
//    @Transactional
//    void getFileReportGenerationLogsByIdFiltering() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        Long id = fileReportGenerationLogEntity.getId();
//
//        defaultFileReportGenerationLogShouldBeFound("id.equals=" + id);
//        defaultFileReportGenerationLogShouldNotBeFound("id.notEquals=" + id);
//
//        defaultFileReportGenerationLogShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultFileReportGenerationLogShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultFileReportGenerationLogShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultFileReportGenerationLogShouldNotBeFound("id.lessThan=" + id);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportName equals to DEFAULT_REPORT_NAME
//        defaultFileReportGenerationLogShouldBeFound("reportName.equals=" + DEFAULT_REPORT_NAME);
//
//        // Get all the fileReportGenerationLogList where reportName equals to UPDATED_REPORT_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("reportName.equals=" + UPDATED_REPORT_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportName not equals to DEFAULT_REPORT_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("reportName.notEquals=" + DEFAULT_REPORT_NAME);
//
//        // Get all the fileReportGenerationLogList where reportName not equals to UPDATED_REPORT_NAME
//        defaultFileReportGenerationLogShouldBeFound("reportName.notEquals=" + UPDATED_REPORT_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportName in DEFAULT_REPORT_NAME or UPDATED_REPORT_NAME
//        defaultFileReportGenerationLogShouldBeFound("reportName.in=" + DEFAULT_REPORT_NAME + "," + UPDATED_REPORT_NAME);
//
//        // Get all the fileReportGenerationLogList where reportName equals to UPDATED_REPORT_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("reportName.in=" + UPDATED_REPORT_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportName is not null
//        defaultFileReportGenerationLogShouldBeFound("reportName.specified=true");
//
//        // Get all the fileReportGenerationLogList where reportName is null
//        defaultFileReportGenerationLogShouldNotBeFound("reportName.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportNameContainsSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportName contains DEFAULT_REPORT_NAME
//        defaultFileReportGenerationLogShouldBeFound("reportName.contains=" + DEFAULT_REPORT_NAME);
//
//        // Get all the fileReportGenerationLogList where reportName contains UPDATED_REPORT_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("reportName.contains=" + UPDATED_REPORT_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportName does not contain DEFAULT_REPORT_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("reportName.doesNotContain=" + DEFAULT_REPORT_NAME);
//
//        // Get all the fileReportGenerationLogList where reportName does not contain UPDATED_REPORT_NAME
//        defaultFileReportGenerationLogShouldBeFound("reportName.doesNotContain=" + UPDATED_REPORT_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportDateIsEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportDate equals to DEFAULT_REPORT_DATE
//        defaultFileReportGenerationLogShouldBeFound("reportDate.equals=" + DEFAULT_REPORT_DATE);
//
//        // Get all the fileReportGenerationLogList where reportDate equals to UPDATED_REPORT_DATE
//        defaultFileReportGenerationLogShouldNotBeFound("reportDate.equals=" + UPDATED_REPORT_DATE);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportDateIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportDate not equals to DEFAULT_REPORT_DATE
//        defaultFileReportGenerationLogShouldNotBeFound("reportDate.notEquals=" + DEFAULT_REPORT_DATE);
//
//        // Get all the fileReportGenerationLogList where reportDate not equals to UPDATED_REPORT_DATE
//        defaultFileReportGenerationLogShouldBeFound("reportDate.notEquals=" + UPDATED_REPORT_DATE);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportDateIsInShouldWork() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportDate in DEFAULT_REPORT_DATE or UPDATED_REPORT_DATE
//        defaultFileReportGenerationLogShouldBeFound("reportDate.in=" + DEFAULT_REPORT_DATE + "," + UPDATED_REPORT_DATE);
//
//        // Get all the fileReportGenerationLogList where reportDate equals to UPDATED_REPORT_DATE
//        defaultFileReportGenerationLogShouldNotBeFound("reportDate.in=" + UPDATED_REPORT_DATE);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportDateIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportDate is not null
//        defaultFileReportGenerationLogShouldBeFound("reportDate.specified=true");
//
//        // Get all the fileReportGenerationLogList where reportDate is null
//        defaultFileReportGenerationLogShouldNotBeFound("reportDate.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportDateIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportDate is greater than or equal to DEFAULT_REPORT_DATE
//        defaultFileReportGenerationLogShouldBeFound("reportDate.greaterThanOrEqual=" + DEFAULT_REPORT_DATE);
//
//        // Get all the fileReportGenerationLogList where reportDate is greater than or equal to UPDATED_REPORT_DATE
//        defaultFileReportGenerationLogShouldNotBeFound("reportDate.greaterThanOrEqual=" + UPDATED_REPORT_DATE);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportDateIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportDate is less than or equal to DEFAULT_REPORT_DATE
//        defaultFileReportGenerationLogShouldBeFound("reportDate.lessThanOrEqual=" + DEFAULT_REPORT_DATE);
//
//        // Get all the fileReportGenerationLogList where reportDate is less than or equal to SMALLER_REPORT_DATE
//        defaultFileReportGenerationLogShouldNotBeFound("reportDate.lessThanOrEqual=" + SMALLER_REPORT_DATE);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportDateIsLessThanSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportDate is less than DEFAULT_REPORT_DATE
//        defaultFileReportGenerationLogShouldNotBeFound("reportDate.lessThan=" + DEFAULT_REPORT_DATE);
//
//        // Get all the fileReportGenerationLogList where reportDate is less than UPDATED_REPORT_DATE
//        defaultFileReportGenerationLogShouldBeFound("reportDate.lessThan=" + UPDATED_REPORT_DATE);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByReportDateIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where reportDate is greater than DEFAULT_REPORT_DATE
//        defaultFileReportGenerationLogShouldNotBeFound("reportDate.greaterThan=" + DEFAULT_REPORT_DATE);
//
//        // Get all the fileReportGenerationLogList where reportDate is greater than SMALLER_REPORT_DATE
//        defaultFileReportGenerationLogShouldBeFound("reportDate.greaterThan=" + SMALLER_REPORT_DATE);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByFileNameIsEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where fileName equals to DEFAULT_FILE_NAME
//        defaultFileReportGenerationLogShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);
//
//        // Get all the fileReportGenerationLogList where fileName equals to UPDATED_FILE_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByFileNameIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where fileName not equals to DEFAULT_FILE_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("fileName.notEquals=" + DEFAULT_FILE_NAME);
//
//        // Get all the fileReportGenerationLogList where fileName not equals to UPDATED_FILE_NAME
//        defaultFileReportGenerationLogShouldBeFound("fileName.notEquals=" + UPDATED_FILE_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByFileNameIsInShouldWork() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
//        defaultFileReportGenerationLogShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);
//
//        // Get all the fileReportGenerationLogList where fileName equals to UPDATED_FILE_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByFileNameIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where fileName is not null
//        defaultFileReportGenerationLogShouldBeFound("fileName.specified=true");
//
//        // Get all the fileReportGenerationLogList where fileName is null
//        defaultFileReportGenerationLogShouldNotBeFound("fileName.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByFileNameContainsSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where fileName contains DEFAULT_FILE_NAME
//        defaultFileReportGenerationLogShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);
//
//        // Get all the fileReportGenerationLogList where fileName contains UPDATED_FILE_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByFileNameNotContainsSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where fileName does not contain DEFAULT_FILE_NAME
//        defaultFileReportGenerationLogShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);
//
//        // Get all the fileReportGenerationLogList where fileName does not contain UPDATED_FILE_NAME
//        defaultFileReportGenerationLogShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByRowNumberIsEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where rowNumber equals to DEFAULT_ROW_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("rowNumber.equals=" + DEFAULT_ROW_NUMBER);
//
//        // Get all the fileReportGenerationLogList where rowNumber equals to UPDATED_ROW_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("rowNumber.equals=" + UPDATED_ROW_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByRowNumberIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where rowNumber not equals to DEFAULT_ROW_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("rowNumber.notEquals=" + DEFAULT_ROW_NUMBER);
//
//        // Get all the fileReportGenerationLogList where rowNumber not equals to UPDATED_ROW_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("rowNumber.notEquals=" + UPDATED_ROW_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByRowNumberIsInShouldWork() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where rowNumber in DEFAULT_ROW_NUMBER or UPDATED_ROW_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("rowNumber.in=" + DEFAULT_ROW_NUMBER + "," + UPDATED_ROW_NUMBER);
//
//        // Get all the fileReportGenerationLogList where rowNumber equals to UPDATED_ROW_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("rowNumber.in=" + UPDATED_ROW_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByRowNumberIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where rowNumber is not null
//        defaultFileReportGenerationLogShouldBeFound("rowNumber.specified=true");
//
//        // Get all the fileReportGenerationLogList where rowNumber is null
//        defaultFileReportGenerationLogShouldNotBeFound("rowNumber.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByRowNumberIsGreaterThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where rowNumber is greater than or equal to DEFAULT_ROW_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("rowNumber.greaterThanOrEqual=" + DEFAULT_ROW_NUMBER);
//
//        // Get all the fileReportGenerationLogList where rowNumber is greater than or equal to UPDATED_ROW_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("rowNumber.greaterThanOrEqual=" + UPDATED_ROW_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByRowNumberIsLessThanOrEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where rowNumber is less than or equal to DEFAULT_ROW_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("rowNumber.lessThanOrEqual=" + DEFAULT_ROW_NUMBER);
//
//        // Get all the fileReportGenerationLogList where rowNumber is less than or equal to SMALLER_ROW_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("rowNumber.lessThanOrEqual=" + SMALLER_ROW_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByRowNumberIsLessThanSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where rowNumber is less than DEFAULT_ROW_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("rowNumber.lessThan=" + DEFAULT_ROW_NUMBER);
//
//        // Get all the fileReportGenerationLogList where rowNumber is less than UPDATED_ROW_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("rowNumber.lessThan=" + UPDATED_ROW_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByRowNumberIsGreaterThanSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where rowNumber is greater than DEFAULT_ROW_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("rowNumber.greaterThan=" + DEFAULT_ROW_NUMBER);
//
//        // Get all the fileReportGenerationLogList where rowNumber is greater than SMALLER_ROW_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("rowNumber.greaterThan=" + SMALLER_ROW_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByPorNumberIsEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where porNumber equals to DEFAULT_POR_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("porNumber.equals=" + DEFAULT_POR_NUMBER);
//
//        // Get all the fileReportGenerationLogList where porNumber equals to UPDATED_POR_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("porNumber.equals=" + UPDATED_POR_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByPorNumberIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where porNumber not equals to DEFAULT_POR_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("porNumber.notEquals=" + DEFAULT_POR_NUMBER);
//
//        // Get all the fileReportGenerationLogList where porNumber not equals to UPDATED_POR_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("porNumber.notEquals=" + UPDATED_POR_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByPorNumberIsInShouldWork() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where porNumber in DEFAULT_POR_NUMBER or UPDATED_POR_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("porNumber.in=" + DEFAULT_POR_NUMBER + "," + UPDATED_POR_NUMBER);
//
//        // Get all the fileReportGenerationLogList where porNumber equals to UPDATED_POR_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("porNumber.in=" + UPDATED_POR_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByPorNumberIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where porNumber is not null
//        defaultFileReportGenerationLogShouldBeFound("porNumber.specified=true");
//
//        // Get all the fileReportGenerationLogList where porNumber is null
//        defaultFileReportGenerationLogShouldNotBeFound("porNumber.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByPorNumberContainsSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where porNumber contains DEFAULT_POR_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("porNumber.contains=" + DEFAULT_POR_NUMBER);
//
//        // Get all the fileReportGenerationLogList where porNumber contains UPDATED_POR_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("porNumber.contains=" + UPDATED_POR_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByPorNumberNotContainsSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where porNumber does not contain DEFAULT_POR_NUMBER
//        defaultFileReportGenerationLogShouldNotBeFound("porNumber.doesNotContain=" + DEFAULT_POR_NUMBER);
//
//        // Get all the fileReportGenerationLogList where porNumber does not contain UPDATED_POR_NUMBER
//        defaultFileReportGenerationLogShouldBeFound("porNumber.doesNotContain=" + UPDATED_POR_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByContentIsEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where content equals to DEFAULT_CONTENT
//        defaultFileReportGenerationLogShouldBeFound("content.equals=" + DEFAULT_CONTENT);
//
//        // Get all the fileReportGenerationLogList where content equals to UPDATED_CONTENT
//        defaultFileReportGenerationLogShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByContentIsNotEqualToSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where content not equals to DEFAULT_CONTENT
//        defaultFileReportGenerationLogShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);
//
//        // Get all the fileReportGenerationLogList where content not equals to UPDATED_CONTENT
//        defaultFileReportGenerationLogShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByContentIsInShouldWork() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where content in DEFAULT_CONTENT or UPDATED_CONTENT
//        defaultFileReportGenerationLogShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);
//
//        // Get all the fileReportGenerationLogList where content equals to UPDATED_CONTENT
//        defaultFileReportGenerationLogShouldNotBeFound("content.in=" + UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByContentIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where content is not null
//        defaultFileReportGenerationLogShouldBeFound("content.specified=true");
//
//        // Get all the fileReportGenerationLogList where content is null
//        defaultFileReportGenerationLogShouldNotBeFound("content.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByContentContainsSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where content contains DEFAULT_CONTENT
//        defaultFileReportGenerationLogShouldBeFound("content.contains=" + DEFAULT_CONTENT);
//
//        // Get all the fileReportGenerationLogList where content contains UPDATED_CONTENT
//        defaultFileReportGenerationLogShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    void getAllFileReportGenerationLogsByContentNotContainsSomething() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        // Get all the fileReportGenerationLogList where content does not contain DEFAULT_CONTENT
//        defaultFileReportGenerationLogShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);
//
//        // Get all the fileReportGenerationLogList where content does not contain UPDATED_CONTENT
//        defaultFileReportGenerationLogShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultFileReportGenerationLogShouldBeFound(String filter) throws Exception {
//        restFileReportGenerationLogMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(fileReportGenerationLogEntity.getId().intValue())))
//            .andExpect(jsonPath("$.[*].reportName").value(hasItem(DEFAULT_REPORT_NAME)))
//            .andExpect(jsonPath("$.[*].reportDate").value(hasItem(DEFAULT_REPORT_DATE.intValue())))
//            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
//            .andExpect(jsonPath("$.[*].rowNumber").value(hasItem(DEFAULT_ROW_NUMBER.intValue())))
//            .andExpect(jsonPath("$.[*].porNumber").value(hasItem(DEFAULT_POR_NUMBER)))
//            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)));
//
//        // Check, that the count call also returns 1
//        restFileReportGenerationLogMockMvc
//            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultFileReportGenerationLogShouldNotBeFound(String filter) throws Exception {
//        restFileReportGenerationLogMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restFileReportGenerationLogMockMvc
//            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingFileReportGenerationLog() throws Exception {
//        // Get the fileReportGenerationLog
//        restFileReportGenerationLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putNewFileReportGenerationLog() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        int databaseSizeBeforeUpdate = fileReportGenerationLogRepository.findAll().size();
//
//        // Update the fileReportGenerationLog
//        FileReportGenerationLogEntity updatedFileReportGenerationLogEntity = fileReportGenerationLogRepository
//            .findById(fileReportGenerationLogEntity.getId())
//            .get();
//        // Disconnect from session so that the updates on updatedFileReportGenerationLogEntity are not directly saved in db
//        em.detach(updatedFileReportGenerationLogEntity);
//        updatedFileReportGenerationLogEntity
//            .reportName(UPDATED_REPORT_NAME)
//            .reportDate(UPDATED_REPORT_DATE)
//            .fileName(UPDATED_FILE_NAME)
//            .rowNumber(UPDATED_ROW_NUMBER)
//            .porNumber(UPDATED_POR_NUMBER)
//            .content(UPDATED_CONTENT);
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(updatedFileReportGenerationLogEntity);
//
//        restFileReportGenerationLogMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, fileReportGenerationLogDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeUpdate);
//        FileReportGenerationLogEntity testFileReportGenerationLog = fileReportGenerationLogList.get(fileReportGenerationLogList.size() - 1);
//        assertThat(testFileReportGenerationLog.getReportName()).isEqualTo(UPDATED_REPORT_NAME);
//        assertThat(testFileReportGenerationLog.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
//        assertThat(testFileReportGenerationLog.getFileName()).isEqualTo(UPDATED_FILE_NAME);
//        assertThat(testFileReportGenerationLog.getRowNumber()).isEqualTo(UPDATED_ROW_NUMBER);
//        assertThat(testFileReportGenerationLog.getPorNumber()).isEqualTo(UPDATED_POR_NUMBER);
//        assertThat(testFileReportGenerationLog.getContent()).isEqualTo(UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingFileReportGenerationLog() throws Exception {
//        int databaseSizeBeforeUpdate = fileReportGenerationLogRepository.findAll().size();
//        fileReportGenerationLogEntity.setId(count.incrementAndGet());
//
//        // Create the FileReportGenerationLog
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restFileReportGenerationLogMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, fileReportGenerationLogDTO.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchFileReportGenerationLog() throws Exception {
//        int databaseSizeBeforeUpdate = fileReportGenerationLogRepository.findAll().size();
//        fileReportGenerationLogEntity.setId(count.incrementAndGet());
//
//        // Create the FileReportGenerationLog
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restFileReportGenerationLogMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamFileReportGenerationLog() throws Exception {
//        int databaseSizeBeforeUpdate = fileReportGenerationLogRepository.findAll().size();
//        fileReportGenerationLogEntity.setId(count.incrementAndGet());
//
//        // Create the FileReportGenerationLog
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restFileReportGenerationLogMockMvc
//            .perform(
//                put(ENTITY_API_URL)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateFileReportGenerationLogWithPatch() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        int databaseSizeBeforeUpdate = fileReportGenerationLogRepository.findAll().size();
//
//        // Update the fileReportGenerationLog using partial update
//        FileReportGenerationLogEntity partialUpdatedFileReportGenerationLogEntity = new FileReportGenerationLogEntity();
//        partialUpdatedFileReportGenerationLogEntity.setId(fileReportGenerationLogEntity.getId());
//
//        partialUpdatedFileReportGenerationLogEntity
//            .reportDate(UPDATED_REPORT_DATE)
//            .rowNumber(UPDATED_ROW_NUMBER)
//            .porNumber(UPDATED_POR_NUMBER)
//            .content(UPDATED_CONTENT);
//
//        restFileReportGenerationLogMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedFileReportGenerationLogEntity.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileReportGenerationLogEntity))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeUpdate);
//        FileReportGenerationLogEntity testFileReportGenerationLog = fileReportGenerationLogList.get(fileReportGenerationLogList.size() - 1);
//        assertThat(testFileReportGenerationLog.getReportName()).isEqualTo(DEFAULT_REPORT_NAME);
//        assertThat(testFileReportGenerationLog.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
//        assertThat(testFileReportGenerationLog.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
//        assertThat(testFileReportGenerationLog.getRowNumber()).isEqualTo(UPDATED_ROW_NUMBER);
//        assertThat(testFileReportGenerationLog.getPorNumber()).isEqualTo(UPDATED_POR_NUMBER);
//        assertThat(testFileReportGenerationLog.getContent()).isEqualTo(UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateFileReportGenerationLogWithPatch() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        int databaseSizeBeforeUpdate = fileReportGenerationLogRepository.findAll().size();
//
//        // Update the fileReportGenerationLog using partial update
//        FileReportGenerationLogEntity partialUpdatedFileReportGenerationLogEntity = new FileReportGenerationLogEntity();
//        partialUpdatedFileReportGenerationLogEntity.setId(fileReportGenerationLogEntity.getId());
//
//        partialUpdatedFileReportGenerationLogEntity
//            .reportName(UPDATED_REPORT_NAME)
//            .reportDate(UPDATED_REPORT_DATE)
//            .fileName(UPDATED_FILE_NAME)
//            .rowNumber(UPDATED_ROW_NUMBER)
//            .porNumber(UPDATED_POR_NUMBER)
//            .content(UPDATED_CONTENT);
//
//        restFileReportGenerationLogMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedFileReportGenerationLogEntity.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileReportGenerationLogEntity))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeUpdate);
//        FileReportGenerationLogEntity testFileReportGenerationLog = fileReportGenerationLogList.get(fileReportGenerationLogList.size() - 1);
//        assertThat(testFileReportGenerationLog.getReportName()).isEqualTo(UPDATED_REPORT_NAME);
//        assertThat(testFileReportGenerationLog.getReportDate()).isEqualTo(UPDATED_REPORT_DATE);
//        assertThat(testFileReportGenerationLog.getFileName()).isEqualTo(UPDATED_FILE_NAME);
//        assertThat(testFileReportGenerationLog.getRowNumber()).isEqualTo(UPDATED_ROW_NUMBER);
//        assertThat(testFileReportGenerationLog.getPorNumber()).isEqualTo(UPDATED_POR_NUMBER);
//        assertThat(testFileReportGenerationLog.getContent()).isEqualTo(UPDATED_CONTENT);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingFileReportGenerationLog() throws Exception {
//        int databaseSizeBeforeUpdate = fileReportGenerationLogRepository.findAll().size();
//        fileReportGenerationLogEntity.setId(count.incrementAndGet());
//
//        // Create the FileReportGenerationLog
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restFileReportGenerationLogMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, fileReportGenerationLogDTO.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchFileReportGenerationLog() throws Exception {
//        int databaseSizeBeforeUpdate = fileReportGenerationLogRepository.findAll().size();
//        fileReportGenerationLogEntity.setId(count.incrementAndGet());
//
//        // Create the FileReportGenerationLog
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restFileReportGenerationLogMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamFileReportGenerationLog() throws Exception {
//        int databaseSizeBeforeUpdate = fileReportGenerationLogRepository.findAll().size();
//        fileReportGenerationLogEntity.setId(count.incrementAndGet());
//
//        // Create the FileReportGenerationLog
//        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restFileReportGenerationLogMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the FileReportGenerationLog in the database
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteFileReportGenerationLog() throws Exception {
//        // Initialize the database
//        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
//
//        int databaseSizeBeforeDelete = fileReportGenerationLogRepository.findAll().size();
//
//        // Delete the fileReportGenerationLog
//        restFileReportGenerationLogMockMvc
//            .perform(delete(ENTITY_API_URL_ID, fileReportGenerationLogEntity.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
//        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
