/*
package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.PortabilityLogEntity;
import ix.portal.npg.repository.PortabilityLogRepository;
import ix.portal.npg.service.criteria.PortabilityLogCriteria;
import ix.portal.npg.service.dto.PortabilityLogDTO;
import ix.portal.npg.service.mapper.PortabilityLogMapper;
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
 * Integration tests for the {@link PortabilityLogResource} REST controller.
 *//*

@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "portabilityLog" })class PortabilityLogResourceIT {

    private static final Long DEFAULT_POR_ID = 1L;
    private static final Long UPDATED_POR_ID = 2L;
    private static final Long SMALLER_POR_ID = 1L - 1L;

    private static final String DEFAULT_POR_REQUEST_ID = "AAAAAAAAAA";
    private static final String UPDATED_POR_REQUEST_ID = "BBBBBBBBBB";

    private static final String DEFAULT_POR_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_POR_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_POR_LEGAL_TERM = 1;
    private static final Integer UPDATED_POR_LEGAL_TERM = 2;
    private static final Integer SMALLER_POR_LEGAL_TERM = 1 - 1;

    private static final String DEFAULT_POR_OPR = "AAAAAAAAAA";
    private static final String UPDATED_POR_OPR = "BBBBBBBBBB";

    private static final String DEFAULT_POR_ACC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_POR_ACC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_ID_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_POR_ID_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_POR_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_POR_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_POR_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_POR_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_POR_PORTED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POR_PORTED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_POR_ROUTING = "AAAAAAAAAA";
    private static final String UPDATED_POR_ROUTING = "BBBBBBBBBB";

    private static final String DEFAULT_POR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_POR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_OP_ORG = "AAAAAAAAAA";
    private static final String UPDATED_POR_OP_ORG = "BBBBBBBBBB";

    private static final String DEFAULT_POR_RSP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POR_RSP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_RSP_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_POR_RSP_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_CANCEL_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_POR_CANCEL_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_MNPID = "AAAAAAAAAA";
    private static final String UPDATED_POR_MNPID = "BBBBBBBBBB";

    private static final Instant DEFAULT_PORTATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PORTATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PORTA_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PORTA_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MVNO = "AAAAAAAAAA";
    private static final String UPDATED_MVNO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_POR_ERR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POR_ERR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_ERR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_POR_ERR_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_OPD = "AAAAAAAAAA";
    private static final String UPDATED_POR_OPD = "BBBBBBBBBB";

    private static final String DEFAULT_POR_NUM_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_POR_NUM_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_POR_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_POR_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_POR_DEADLINE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POR_DEADLINE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_POR_RESPONSE_TIMESTAMP = 1L;
    private static final Long UPDATED_POR_RESPONSE_TIMESTAMP = 2L;
    private static final Long SMALLER_POR_RESPONSE_TIMESTAMP = 1L - 1L;

    private static final Integer DEFAULT_POR_ELIGIBLE = 1;
    private static final Integer UPDATED_POR_ELIGIBLE = 2;
    private static final Integer SMALLER_POR_ELIGIBLE = 1 - 1;

    private static final Integer DEFAULT_POR_BILLING_OK = 1;
    private static final Integer UPDATED_POR_BILLING_OK = 2;
    private static final Integer SMALLER_POR_BILLING_OK = 1 - 1;

    private static final String DEFAULT_INTERMEDIARY_ACTION_STATE = "AAAAAAAAAA";
    private static final String UPDATED_INTERMEDIARY_ACTION_STATE = "BBBBBBBBBB";

    private static final Instant DEFAULT_POR_CR_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POR_CR_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_POR_UPD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POR_UPD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_POR_TECH_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_POR_TECH_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_POR_TECH_DEADLINE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POR_TECH_DEADLINE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_REF_POR_ID = 1L;
    private static final Long UPDATED_REF_POR_ID = 2L;
    private static final Long SMALLER_REF_POR_ID = 1L - 1L;

    private static final Integer DEFAULT_NEED_MANUAL_RETRY = 1;
    private static final Integer UPDATED_NEED_MANUAL_RETRY = 2;
    private static final Integer SMALLER_NEED_MANUAL_RETRY = 1 - 1;

    private static final Integer DEFAULT_RETRY_COUNT = 1;
    private static final Integer UPDATED_RETRY_COUNT = 2;
    private static final Integer SMALLER_RETRY_COUNT = 1 - 1;

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSERT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSERT_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/portability-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PortabilityLogRepository portabilityLogRepository;

    @Autowired
    private PortabilityLogMapper portabilityLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPortabilityLogMockMvc;

    private PortabilityLogEntity portabilityLogEntity;

    */
/**
 * Create an entity for this test.
 *
 * This is a static method, as tests for other entities might also need it,
 * if they test an entity which requires the current entity.
 *//*

    public static PortabilityLogEntity createEntity(EntityManager em) {
        PortabilityLogEntity portabilityLogEntity = new PortabilityLogEntity()
            .porId(DEFAULT_POR_ID)
            .porRequestId(DEFAULT_POR_REQUEST_ID)
            .porNumber(DEFAULT_POR_NUMBER)
            .porLegalTerm(DEFAULT_POR_LEGAL_TERM)
            .porOpr(DEFAULT_POR_OPR)
            .porAccType(DEFAULT_POR_ACC_TYPE)
            .porIdNumber(DEFAULT_POR_ID_NUMBER)
            .porContactNumber(DEFAULT_POR_CONTACT_NUMBER)
            .porStatus(DEFAULT_POR_STATUS)
            .porPortedDate(DEFAULT_POR_PORTED_DATE)
            .porRouting(DEFAULT_POR_ROUTING)
            .porType(DEFAULT_POR_TYPE)
            .porOpOrg(DEFAULT_POR_OP_ORG)
            .porRspCode(DEFAULT_POR_RSP_CODE)
            .porRspNote(DEFAULT_POR_RSP_NOTE)
            .porCancelNote(DEFAULT_POR_CANCEL_NOTE)
            .porMnpid(DEFAULT_POR_MNPID)
            .portationDate(DEFAULT_PORTATION_DATE)
            .portaCode(DEFAULT_PORTA_CODE)
            .mvno(DEFAULT_MVNO)
            .context(DEFAULT_CONTEXT)
            .porErrCode(DEFAULT_POR_ERR_CODE)
            .porErrMessage(DEFAULT_POR_ERR_MESSAGE)
            .porOpd(DEFAULT_POR_OPD)
            .porNumType(DEFAULT_POR_NUM_TYPE)
            .porNote(DEFAULT_POR_NOTE)
            .porDeadline(DEFAULT_POR_DEADLINE)
            .porResponseTimestamp(DEFAULT_POR_RESPONSE_TIMESTAMP)
            .porEligible(DEFAULT_POR_ELIGIBLE)
            .porBillingOk(DEFAULT_POR_BILLING_OK)
            .intermediaryActionState(DEFAULT_INTERMEDIARY_ACTION_STATE)
            .porCrDate(DEFAULT_POR_CR_DATE)
            .porUpdDate(DEFAULT_POR_UPD_DATE)
            .porTechStatus(DEFAULT_POR_TECH_STATUS)
            .porTechDeadline(DEFAULT_POR_TECH_DEADLINE)
            .refPorId(DEFAULT_REF_POR_ID)
            .needManualRetry(DEFAULT_NEED_MANUAL_RETRY)
            .retryCount(DEFAULT_RETRY_COUNT)
            .action(DEFAULT_ACTION)
            .request(DEFAULT_REQUEST)
            .insertTimestamp(DEFAULT_INSERT_TIMESTAMP);
        return portabilityLogEntity;
    }

    */
/**
 * Create an updated entity for this test.
 *
 * This is a static method, as tests for other entities might also need it,
 * if they test an entity which requires the current entity.
 *//*

    public static PortabilityLogEntity createUpdatedEntity(EntityManager em) {
        PortabilityLogEntity portabilityLogEntity = new PortabilityLogEntity()
            .porId(UPDATED_POR_ID)
            .porRequestId(UPDATED_POR_REQUEST_ID)
            .porNumber(UPDATED_POR_NUMBER)
            .porLegalTerm(UPDATED_POR_LEGAL_TERM)
            .porOpr(UPDATED_POR_OPR)
            .porAccType(UPDATED_POR_ACC_TYPE)
            .porIdNumber(UPDATED_POR_ID_NUMBER)
            .porContactNumber(UPDATED_POR_CONTACT_NUMBER)
            .porStatus(UPDATED_POR_STATUS)
            .porPortedDate(UPDATED_POR_PORTED_DATE)
            .porRouting(UPDATED_POR_ROUTING)
            .porType(UPDATED_POR_TYPE)
            .porOpOrg(UPDATED_POR_OP_ORG)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .porRspNote(UPDATED_POR_RSP_NOTE)
            .porCancelNote(UPDATED_POR_CANCEL_NOTE)
            .porMnpid(UPDATED_POR_MNPID)
            .portationDate(UPDATED_PORTATION_DATE)
            .portaCode(UPDATED_PORTA_CODE)
            .mvno(UPDATED_MVNO)
            .context(UPDATED_CONTEXT)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porErrMessage(UPDATED_POR_ERR_MESSAGE)
            .porOpd(UPDATED_POR_OPD)
            .porNumType(UPDATED_POR_NUM_TYPE)
            .porNote(UPDATED_POR_NOTE)
            .porDeadline(UPDATED_POR_DEADLINE)
            .porResponseTimestamp(UPDATED_POR_RESPONSE_TIMESTAMP)
            .porEligible(UPDATED_POR_ELIGIBLE)
            .porBillingOk(UPDATED_POR_BILLING_OK)
            .intermediaryActionState(UPDATED_INTERMEDIARY_ACTION_STATE)
            .porCrDate(UPDATED_POR_CR_DATE)
            .porUpdDate(UPDATED_POR_UPD_DATE)
            .porTechStatus(UPDATED_POR_TECH_STATUS)
            .porTechDeadline(UPDATED_POR_TECH_DEADLINE)
            .refPorId(UPDATED_REF_POR_ID)
            .needManualRetry(UPDATED_NEED_MANUAL_RETRY)
            .retryCount(UPDATED_RETRY_COUNT)
            .action(UPDATED_ACTION)
            .request(UPDATED_REQUEST)
            .insertTimestamp(UPDATED_INSERT_TIMESTAMP);
        return portabilityLogEntity;
    }

    @BeforeEach
    public void initTest() {
portabilityLogEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createPortabilityLog() throws Exception {
        int databaseSizeBeforeCreate = portabilityLogRepository.findAll().size();
        // Create the PortabilityLog
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);
        restPortabilityLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeCreate + 1);
        PortabilityLogEntity testPortabilityLog = portabilityLogList.get(portabilityLogList.size() - 1);
        assertThat(testPortabilityLog.getPorId()).isEqualTo(DEFAULT_POR_ID);
        assertThat(testPortabilityLog.getPorRequestId()).isEqualTo(DEFAULT_POR_REQUEST_ID);
        assertThat(testPortabilityLog.getPorNumber()).isEqualTo(DEFAULT_POR_NUMBER);
        assertThat(testPortabilityLog.getPorLegalTerm()).isEqualTo(DEFAULT_POR_LEGAL_TERM);
        assertThat(testPortabilityLog.getPorOpr()).isEqualTo(DEFAULT_POR_OPR);
        assertThat(testPortabilityLog.getPorAccType()).isEqualTo(DEFAULT_POR_ACC_TYPE);
        assertThat(testPortabilityLog.getPorIdNumber()).isEqualTo(DEFAULT_POR_ID_NUMBER);
        assertThat(testPortabilityLog.getPorContactNumber()).isEqualTo(DEFAULT_POR_CONTACT_NUMBER);
        assertThat(testPortabilityLog.getPorStatus()).isEqualTo(DEFAULT_POR_STATUS);
        assertThat(testPortabilityLog.getPorPortedDate()).isEqualTo(DEFAULT_POR_PORTED_DATE);
        assertThat(testPortabilityLog.getPorRouting()).isEqualTo(DEFAULT_POR_ROUTING);
        assertThat(testPortabilityLog.getPorType()).isEqualTo(DEFAULT_POR_TYPE);
        assertThat(testPortabilityLog.getPorOpOrg()).isEqualTo(DEFAULT_POR_OP_ORG);
        assertThat(testPortabilityLog.getPorRspCode()).isEqualTo(DEFAULT_POR_RSP_CODE);
        assertThat(testPortabilityLog.getPorRspNote()).isEqualTo(DEFAULT_POR_RSP_NOTE);
        assertThat(testPortabilityLog.getPorCancelNote()).isEqualTo(DEFAULT_POR_CANCEL_NOTE);
        assertThat(testPortabilityLog.getPorMnpid()).isEqualTo(DEFAULT_POR_MNPID);
        assertThat(testPortabilityLog.getPortationDate()).isEqualTo(DEFAULT_PORTATION_DATE);
        assertThat(testPortabilityLog.getPortaCode()).isEqualTo(DEFAULT_PORTA_CODE);
        assertThat(testPortabilityLog.getMvno()).isEqualTo(DEFAULT_MVNO);
        assertThat(testPortabilityLog.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testPortabilityLog.getPorErrCode()).isEqualTo(DEFAULT_POR_ERR_CODE);
        assertThat(testPortabilityLog.getPorErrMessage()).isEqualTo(DEFAULT_POR_ERR_MESSAGE);
        assertThat(testPortabilityLog.getPorOpd()).isEqualTo(DEFAULT_POR_OPD);
        assertThat(testPortabilityLog.getPorNumType()).isEqualTo(DEFAULT_POR_NUM_TYPE);
        assertThat(testPortabilityLog.getPorNote()).isEqualTo(DEFAULT_POR_NOTE);
        assertThat(testPortabilityLog.getPorDeadline()).isEqualTo(DEFAULT_POR_DEADLINE);
        assertThat(testPortabilityLog.getPorResponseTimestamp()).isEqualTo(DEFAULT_POR_RESPONSE_TIMESTAMP);
        assertThat(testPortabilityLog.getPorEligible()).isEqualTo(DEFAULT_POR_ELIGIBLE);
        assertThat(testPortabilityLog.getPorBillingOk()).isEqualTo(DEFAULT_POR_BILLING_OK);
        assertThat(testPortabilityLog.getIntermediaryActionState()).isEqualTo(DEFAULT_INTERMEDIARY_ACTION_STATE);
        assertThat(testPortabilityLog.getPorCrDate()).isEqualTo(DEFAULT_POR_CR_DATE);
        assertThat(testPortabilityLog.getPorUpdDate()).isEqualTo(DEFAULT_POR_UPD_DATE);
        assertThat(testPortabilityLog.getPorTechStatus()).isEqualTo(DEFAULT_POR_TECH_STATUS);
        assertThat(testPortabilityLog.getPorTechDeadline()).isEqualTo(DEFAULT_POR_TECH_DEADLINE);
        assertThat(testPortabilityLog.getRefPorId()).isEqualTo(DEFAULT_REF_POR_ID);
        assertThat(testPortabilityLog.getNeedManualRetry()).isEqualTo(DEFAULT_NEED_MANUAL_RETRY);
        assertThat(testPortabilityLog.getRetryCount()).isEqualTo(DEFAULT_RETRY_COUNT);
        assertThat(testPortabilityLog.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testPortabilityLog.getRequest()).isEqualTo(DEFAULT_REQUEST);
        assertThat(testPortabilityLog.getInsertTimestamp()).isEqualTo(DEFAULT_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createPortabilityLogWithExistingId() throws Exception {
        // Create the PortabilityLog with an existing ID
        portabilityLogEntity.setId(1L);
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        int databaseSizeBeforeCreate = portabilityLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortabilityLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPorIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = portabilityLogRepository.findAll().size();
        // set the field null
        portabilityLogEntity.setPorId(null);

        // Create the PortabilityLog, which fails.
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        restPortabilityLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isBadRequest());

        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRetryCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = portabilityLogRepository.findAll().size();
        // set the field null
        portabilityLogEntity.setRetryCount(null);

        // Create the PortabilityLog, which fails.
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        restPortabilityLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isBadRequest());

        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInsertTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = portabilityLogRepository.findAll().size();
        // set the field null
        portabilityLogEntity.setInsertTimestamp(null);

        // Create the PortabilityLog, which fails.
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        restPortabilityLogMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isBadRequest());

        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPortabilityLogs() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList
        restPortabilityLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portabilityLogEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].porId").value(hasItem(DEFAULT_POR_ID.intValue())))
            .andExpect(jsonPath("$.[*].porRequestId").value(hasItem(DEFAULT_POR_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].porNumber").value(hasItem(DEFAULT_POR_NUMBER)))
            .andExpect(jsonPath("$.[*].porLegalTerm").value(hasItem(DEFAULT_POR_LEGAL_TERM)))
            .andExpect(jsonPath("$.[*].porOpr").value(hasItem(DEFAULT_POR_OPR)))
            .andExpect(jsonPath("$.[*].porAccType").value(hasItem(DEFAULT_POR_ACC_TYPE)))
            .andExpect(jsonPath("$.[*].porIdNumber").value(hasItem(DEFAULT_POR_ID_NUMBER)))
            .andExpect(jsonPath("$.[*].porContactNumber").value(hasItem(DEFAULT_POR_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].porStatus").value(hasItem(DEFAULT_POR_STATUS)))
            .andExpect(jsonPath("$.[*].porPortedDate").value(hasItem(DEFAULT_POR_PORTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].porRouting").value(hasItem(DEFAULT_POR_ROUTING)))
            .andExpect(jsonPath("$.[*].porType").value(hasItem(DEFAULT_POR_TYPE)))
            .andExpect(jsonPath("$.[*].porOpOrg").value(hasItem(DEFAULT_POR_OP_ORG)))
            .andExpect(jsonPath("$.[*].porRspCode").value(hasItem(DEFAULT_POR_RSP_CODE)))
            .andExpect(jsonPath("$.[*].porRspNote").value(hasItem(DEFAULT_POR_RSP_NOTE)))
            .andExpect(jsonPath("$.[*].porCancelNote").value(hasItem(DEFAULT_POR_CANCEL_NOTE)))
            .andExpect(jsonPath("$.[*].porMnpid").value(hasItem(DEFAULT_POR_MNPID)))
            .andExpect(jsonPath("$.[*].portationDate").value(hasItem(DEFAULT_PORTATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].portaCode").value(hasItem(DEFAULT_PORTA_CODE)))
            .andExpect(jsonPath("$.[*].mvno").value(hasItem(DEFAULT_MVNO)))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].porErrCode").value(hasItem(DEFAULT_POR_ERR_CODE)))
            .andExpect(jsonPath("$.[*].porErrMessage").value(hasItem(DEFAULT_POR_ERR_MESSAGE)))
            .andExpect(jsonPath("$.[*].porOpd").value(hasItem(DEFAULT_POR_OPD)))
            .andExpect(jsonPath("$.[*].porNumType").value(hasItem(DEFAULT_POR_NUM_TYPE)))
            .andExpect(jsonPath("$.[*].porNote").value(hasItem(DEFAULT_POR_NOTE)))
            .andExpect(jsonPath("$.[*].porDeadline").value(hasItem(DEFAULT_POR_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].porResponseTimestamp").value(hasItem(DEFAULT_POR_RESPONSE_TIMESTAMP.intValue())))
            .andExpect(jsonPath("$.[*].porEligible").value(hasItem(DEFAULT_POR_ELIGIBLE)))
            .andExpect(jsonPath("$.[*].porBillingOk").value(hasItem(DEFAULT_POR_BILLING_OK)))
            .andExpect(jsonPath("$.[*].intermediaryActionState").value(hasItem(DEFAULT_INTERMEDIARY_ACTION_STATE)))
            .andExpect(jsonPath("$.[*].porCrDate").value(hasItem(DEFAULT_POR_CR_DATE.toString())))
            .andExpect(jsonPath("$.[*].porUpdDate").value(hasItem(DEFAULT_POR_UPD_DATE.toString())))
            .andExpect(jsonPath("$.[*].porTechStatus").value(hasItem(DEFAULT_POR_TECH_STATUS)))
            .andExpect(jsonPath("$.[*].porTechDeadline").value(hasItem(DEFAULT_POR_TECH_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].refPorId").value(hasItem(DEFAULT_REF_POR_ID.intValue())))
            .andExpect(jsonPath("$.[*].needManualRetry").value(hasItem(DEFAULT_NEED_MANUAL_RETRY)))
            .andExpect(jsonPath("$.[*].retryCount").value(hasItem(DEFAULT_RETRY_COUNT)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].request").value(hasItem(DEFAULT_REQUEST)))
            .andExpect(jsonPath("$.[*].insertTimestamp").value(hasItem(DEFAULT_INSERT_TIMESTAMP.toString())));
    }

    @Test
    @Transactional
    void getPortabilityLog() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get the portabilityLog
        restPortabilityLogMockMvc
            .perform(get(ENTITY_API_URL_ID, portabilityLogEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(portabilityLogEntity.getId().intValue()))
            .andExpect(jsonPath("$.porId").value(DEFAULT_POR_ID.intValue()))
            .andExpect(jsonPath("$.porRequestId").value(DEFAULT_POR_REQUEST_ID))
            .andExpect(jsonPath("$.porNumber").value(DEFAULT_POR_NUMBER))
            .andExpect(jsonPath("$.porLegalTerm").value(DEFAULT_POR_LEGAL_TERM))
            .andExpect(jsonPath("$.porOpr").value(DEFAULT_POR_OPR))
            .andExpect(jsonPath("$.porAccType").value(DEFAULT_POR_ACC_TYPE))
            .andExpect(jsonPath("$.porIdNumber").value(DEFAULT_POR_ID_NUMBER))
            .andExpect(jsonPath("$.porContactNumber").value(DEFAULT_POR_CONTACT_NUMBER))
            .andExpect(jsonPath("$.porStatus").value(DEFAULT_POR_STATUS))
            .andExpect(jsonPath("$.porPortedDate").value(DEFAULT_POR_PORTED_DATE.toString()))
            .andExpect(jsonPath("$.porRouting").value(DEFAULT_POR_ROUTING))
            .andExpect(jsonPath("$.porType").value(DEFAULT_POR_TYPE))
            .andExpect(jsonPath("$.porOpOrg").value(DEFAULT_POR_OP_ORG))
            .andExpect(jsonPath("$.porRspCode").value(DEFAULT_POR_RSP_CODE))
            .andExpect(jsonPath("$.porRspNote").value(DEFAULT_POR_RSP_NOTE))
            .andExpect(jsonPath("$.porCancelNote").value(DEFAULT_POR_CANCEL_NOTE))
            .andExpect(jsonPath("$.porMnpid").value(DEFAULT_POR_MNPID))
            .andExpect(jsonPath("$.portationDate").value(DEFAULT_PORTATION_DATE.toString()))
            .andExpect(jsonPath("$.portaCode").value(DEFAULT_PORTA_CODE))
            .andExpect(jsonPath("$.mvno").value(DEFAULT_MVNO))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT))
            .andExpect(jsonPath("$.porErrCode").value(DEFAULT_POR_ERR_CODE))
            .andExpect(jsonPath("$.porErrMessage").value(DEFAULT_POR_ERR_MESSAGE))
            .andExpect(jsonPath("$.porOpd").value(DEFAULT_POR_OPD))
            .andExpect(jsonPath("$.porNumType").value(DEFAULT_POR_NUM_TYPE))
            .andExpect(jsonPath("$.porNote").value(DEFAULT_POR_NOTE))
            .andExpect(jsonPath("$.porDeadline").value(DEFAULT_POR_DEADLINE.toString()))
            .andExpect(jsonPath("$.porResponseTimestamp").value(DEFAULT_POR_RESPONSE_TIMESTAMP.intValue()))
            .andExpect(jsonPath("$.porEligible").value(DEFAULT_POR_ELIGIBLE))
            .andExpect(jsonPath("$.porBillingOk").value(DEFAULT_POR_BILLING_OK))
            .andExpect(jsonPath("$.intermediaryActionState").value(DEFAULT_INTERMEDIARY_ACTION_STATE))
            .andExpect(jsonPath("$.porCrDate").value(DEFAULT_POR_CR_DATE.toString()))
            .andExpect(jsonPath("$.porUpdDate").value(DEFAULT_POR_UPD_DATE.toString()))
            .andExpect(jsonPath("$.porTechStatus").value(DEFAULT_POR_TECH_STATUS))
            .andExpect(jsonPath("$.porTechDeadline").value(DEFAULT_POR_TECH_DEADLINE.toString()))
            .andExpect(jsonPath("$.refPorId").value(DEFAULT_REF_POR_ID.intValue()))
            .andExpect(jsonPath("$.needManualRetry").value(DEFAULT_NEED_MANUAL_RETRY))
            .andExpect(jsonPath("$.retryCount").value(DEFAULT_RETRY_COUNT))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.request").value(DEFAULT_REQUEST))
            .andExpect(jsonPath("$.insertTimestamp").value(DEFAULT_INSERT_TIMESTAMP.toString()));
    }

    @Test
    @Transactional
    void getPortabilityLogsByIdFiltering() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        Long id = portabilityLogEntity.getId();

        defaultPortabilityLogShouldBeFound("id.equals=" + id);
        defaultPortabilityLogShouldNotBeFound("id.notEquals=" + id);

        defaultPortabilityLogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPortabilityLogShouldNotBeFound("id.greaterThan=" + id);

        defaultPortabilityLogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPortabilityLogShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porId equals to DEFAULT_POR_ID
        defaultPortabilityLogShouldBeFound("porId.equals=" + DEFAULT_POR_ID);

        // Get all the portabilityLogList where porId equals to UPDATED_POR_ID
        defaultPortabilityLogShouldNotBeFound("porId.equals=" + UPDATED_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porId not equals to DEFAULT_POR_ID
        defaultPortabilityLogShouldNotBeFound("porId.notEquals=" + DEFAULT_POR_ID);

        // Get all the portabilityLogList where porId not equals to UPDATED_POR_ID
        defaultPortabilityLogShouldBeFound("porId.notEquals=" + UPDATED_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porId in DEFAULT_POR_ID or UPDATED_POR_ID
        defaultPortabilityLogShouldBeFound("porId.in=" + DEFAULT_POR_ID + "," + UPDATED_POR_ID);

        // Get all the portabilityLogList where porId equals to UPDATED_POR_ID
        defaultPortabilityLogShouldNotBeFound("porId.in=" + UPDATED_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porId is not null
        defaultPortabilityLogShouldBeFound("porId.specified=true");

        // Get all the portabilityLogList where porId is null
        defaultPortabilityLogShouldNotBeFound("porId.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porId is greater than or equal to DEFAULT_POR_ID
        defaultPortabilityLogShouldBeFound("porId.greaterThanOrEqual=" + DEFAULT_POR_ID);

        // Get all the portabilityLogList where porId is greater than or equal to UPDATED_POR_ID
        defaultPortabilityLogShouldNotBeFound("porId.greaterThanOrEqual=" + UPDATED_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porId is less than or equal to DEFAULT_POR_ID
        defaultPortabilityLogShouldBeFound("porId.lessThanOrEqual=" + DEFAULT_POR_ID);

        // Get all the portabilityLogList where porId is less than or equal to SMALLER_POR_ID
        defaultPortabilityLogShouldNotBeFound("porId.lessThanOrEqual=" + SMALLER_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porId is less than DEFAULT_POR_ID
        defaultPortabilityLogShouldNotBeFound("porId.lessThan=" + DEFAULT_POR_ID);

        // Get all the portabilityLogList where porId is less than UPDATED_POR_ID
        defaultPortabilityLogShouldBeFound("porId.lessThan=" + UPDATED_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porId is greater than DEFAULT_POR_ID
        defaultPortabilityLogShouldNotBeFound("porId.greaterThan=" + DEFAULT_POR_ID);

        // Get all the portabilityLogList where porId is greater than SMALLER_POR_ID
        defaultPortabilityLogShouldBeFound("porId.greaterThan=" + SMALLER_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRequestIdIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRequestId equals to DEFAULT_POR_REQUEST_ID
        defaultPortabilityLogShouldBeFound("porRequestId.equals=" + DEFAULT_POR_REQUEST_ID);

        // Get all the portabilityLogList where porRequestId equals to UPDATED_POR_REQUEST_ID
        defaultPortabilityLogShouldNotBeFound("porRequestId.equals=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRequestIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRequestId not equals to DEFAULT_POR_REQUEST_ID
        defaultPortabilityLogShouldNotBeFound("porRequestId.notEquals=" + DEFAULT_POR_REQUEST_ID);

        // Get all the portabilityLogList where porRequestId not equals to UPDATED_POR_REQUEST_ID
        defaultPortabilityLogShouldBeFound("porRequestId.notEquals=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRequestIdIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRequestId in DEFAULT_POR_REQUEST_ID or UPDATED_POR_REQUEST_ID
        defaultPortabilityLogShouldBeFound("porRequestId.in=" + DEFAULT_POR_REQUEST_ID + "," + UPDATED_POR_REQUEST_ID);

        // Get all the portabilityLogList where porRequestId equals to UPDATED_POR_REQUEST_ID
        defaultPortabilityLogShouldNotBeFound("porRequestId.in=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRequestIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRequestId is not null
        defaultPortabilityLogShouldBeFound("porRequestId.specified=true");

        // Get all the portabilityLogList where porRequestId is null
        defaultPortabilityLogShouldNotBeFound("porRequestId.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRequestIdContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRequestId contains DEFAULT_POR_REQUEST_ID
        defaultPortabilityLogShouldBeFound("porRequestId.contains=" + DEFAULT_POR_REQUEST_ID);

        // Get all the portabilityLogList where porRequestId contains UPDATED_POR_REQUEST_ID
        defaultPortabilityLogShouldNotBeFound("porRequestId.contains=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRequestIdNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRequestId does not contain DEFAULT_POR_REQUEST_ID
        defaultPortabilityLogShouldNotBeFound("porRequestId.doesNotContain=" + DEFAULT_POR_REQUEST_ID);

        // Get all the portabilityLogList where porRequestId does not contain UPDATED_POR_REQUEST_ID
        defaultPortabilityLogShouldBeFound("porRequestId.doesNotContain=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumber equals to DEFAULT_POR_NUMBER
        defaultPortabilityLogShouldBeFound("porNumber.equals=" + DEFAULT_POR_NUMBER);

        // Get all the portabilityLogList where porNumber equals to UPDATED_POR_NUMBER
        defaultPortabilityLogShouldNotBeFound("porNumber.equals=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumber not equals to DEFAULT_POR_NUMBER
        defaultPortabilityLogShouldNotBeFound("porNumber.notEquals=" + DEFAULT_POR_NUMBER);

        // Get all the portabilityLogList where porNumber not equals to UPDATED_POR_NUMBER
        defaultPortabilityLogShouldBeFound("porNumber.notEquals=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumberIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumber in DEFAULT_POR_NUMBER or UPDATED_POR_NUMBER
        defaultPortabilityLogShouldBeFound("porNumber.in=" + DEFAULT_POR_NUMBER + "," + UPDATED_POR_NUMBER);

        // Get all the portabilityLogList where porNumber equals to UPDATED_POR_NUMBER
        defaultPortabilityLogShouldNotBeFound("porNumber.in=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumber is not null
        defaultPortabilityLogShouldBeFound("porNumber.specified=true");

        // Get all the portabilityLogList where porNumber is null
        defaultPortabilityLogShouldNotBeFound("porNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumberContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumber contains DEFAULT_POR_NUMBER
        defaultPortabilityLogShouldBeFound("porNumber.contains=" + DEFAULT_POR_NUMBER);

        // Get all the portabilityLogList where porNumber contains UPDATED_POR_NUMBER
        defaultPortabilityLogShouldNotBeFound("porNumber.contains=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumberNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumber does not contain DEFAULT_POR_NUMBER
        defaultPortabilityLogShouldNotBeFound("porNumber.doesNotContain=" + DEFAULT_POR_NUMBER);

        // Get all the portabilityLogList where porNumber does not contain UPDATED_POR_NUMBER
        defaultPortabilityLogShouldBeFound("porNumber.doesNotContain=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorLegalTermIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porLegalTerm equals to DEFAULT_POR_LEGAL_TERM
        defaultPortabilityLogShouldBeFound("porLegalTerm.equals=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityLogList where porLegalTerm equals to UPDATED_POR_LEGAL_TERM
        defaultPortabilityLogShouldNotBeFound("porLegalTerm.equals=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorLegalTermIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porLegalTerm not equals to DEFAULT_POR_LEGAL_TERM
        defaultPortabilityLogShouldNotBeFound("porLegalTerm.notEquals=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityLogList where porLegalTerm not equals to UPDATED_POR_LEGAL_TERM
        defaultPortabilityLogShouldBeFound("porLegalTerm.notEquals=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorLegalTermIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porLegalTerm in DEFAULT_POR_LEGAL_TERM or UPDATED_POR_LEGAL_TERM
        defaultPortabilityLogShouldBeFound("porLegalTerm.in=" + DEFAULT_POR_LEGAL_TERM + "," + UPDATED_POR_LEGAL_TERM);

        // Get all the portabilityLogList where porLegalTerm equals to UPDATED_POR_LEGAL_TERM
        defaultPortabilityLogShouldNotBeFound("porLegalTerm.in=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorLegalTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porLegalTerm is not null
        defaultPortabilityLogShouldBeFound("porLegalTerm.specified=true");

        // Get all the portabilityLogList where porLegalTerm is null
        defaultPortabilityLogShouldNotBeFound("porLegalTerm.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorLegalTermIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porLegalTerm is greater than or equal to DEFAULT_POR_LEGAL_TERM
        defaultPortabilityLogShouldBeFound("porLegalTerm.greaterThanOrEqual=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityLogList where porLegalTerm is greater than or equal to UPDATED_POR_LEGAL_TERM
        defaultPortabilityLogShouldNotBeFound("porLegalTerm.greaterThanOrEqual=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorLegalTermIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porLegalTerm is less than or equal to DEFAULT_POR_LEGAL_TERM
        defaultPortabilityLogShouldBeFound("porLegalTerm.lessThanOrEqual=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityLogList where porLegalTerm is less than or equal to SMALLER_POR_LEGAL_TERM
        defaultPortabilityLogShouldNotBeFound("porLegalTerm.lessThanOrEqual=" + SMALLER_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorLegalTermIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porLegalTerm is less than DEFAULT_POR_LEGAL_TERM
        defaultPortabilityLogShouldNotBeFound("porLegalTerm.lessThan=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityLogList where porLegalTerm is less than UPDATED_POR_LEGAL_TERM
        defaultPortabilityLogShouldBeFound("porLegalTerm.lessThan=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorLegalTermIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porLegalTerm is greater than DEFAULT_POR_LEGAL_TERM
        defaultPortabilityLogShouldNotBeFound("porLegalTerm.greaterThan=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityLogList where porLegalTerm is greater than SMALLER_POR_LEGAL_TERM
        defaultPortabilityLogShouldBeFound("porLegalTerm.greaterThan=" + SMALLER_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOprIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpr equals to DEFAULT_POR_OPR
        defaultPortabilityLogShouldBeFound("porOpr.equals=" + DEFAULT_POR_OPR);

        // Get all the portabilityLogList where porOpr equals to UPDATED_POR_OPR
        defaultPortabilityLogShouldNotBeFound("porOpr.equals=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOprIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpr not equals to DEFAULT_POR_OPR
        defaultPortabilityLogShouldNotBeFound("porOpr.notEquals=" + DEFAULT_POR_OPR);

        // Get all the portabilityLogList where porOpr not equals to UPDATED_POR_OPR
        defaultPortabilityLogShouldBeFound("porOpr.notEquals=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOprIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpr in DEFAULT_POR_OPR or UPDATED_POR_OPR
        defaultPortabilityLogShouldBeFound("porOpr.in=" + DEFAULT_POR_OPR + "," + UPDATED_POR_OPR);

        // Get all the portabilityLogList where porOpr equals to UPDATED_POR_OPR
        defaultPortabilityLogShouldNotBeFound("porOpr.in=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOprIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpr is not null
        defaultPortabilityLogShouldBeFound("porOpr.specified=true");

        // Get all the portabilityLogList where porOpr is null
        defaultPortabilityLogShouldNotBeFound("porOpr.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOprContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpr contains DEFAULT_POR_OPR
        defaultPortabilityLogShouldBeFound("porOpr.contains=" + DEFAULT_POR_OPR);

        // Get all the portabilityLogList where porOpr contains UPDATED_POR_OPR
        defaultPortabilityLogShouldNotBeFound("porOpr.contains=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOprNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpr does not contain DEFAULT_POR_OPR
        defaultPortabilityLogShouldNotBeFound("porOpr.doesNotContain=" + DEFAULT_POR_OPR);

        // Get all the portabilityLogList where porOpr does not contain UPDATED_POR_OPR
        defaultPortabilityLogShouldBeFound("porOpr.doesNotContain=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorAccTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porAccType equals to DEFAULT_POR_ACC_TYPE
        defaultPortabilityLogShouldBeFound("porAccType.equals=" + DEFAULT_POR_ACC_TYPE);

        // Get all the portabilityLogList where porAccType equals to UPDATED_POR_ACC_TYPE
        defaultPortabilityLogShouldNotBeFound("porAccType.equals=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorAccTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porAccType not equals to DEFAULT_POR_ACC_TYPE
        defaultPortabilityLogShouldNotBeFound("porAccType.notEquals=" + DEFAULT_POR_ACC_TYPE);

        // Get all the portabilityLogList where porAccType not equals to UPDATED_POR_ACC_TYPE
        defaultPortabilityLogShouldBeFound("porAccType.notEquals=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorAccTypeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porAccType in DEFAULT_POR_ACC_TYPE or UPDATED_POR_ACC_TYPE
        defaultPortabilityLogShouldBeFound("porAccType.in=" + DEFAULT_POR_ACC_TYPE + "," + UPDATED_POR_ACC_TYPE);

        // Get all the portabilityLogList where porAccType equals to UPDATED_POR_ACC_TYPE
        defaultPortabilityLogShouldNotBeFound("porAccType.in=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorAccTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porAccType is not null
        defaultPortabilityLogShouldBeFound("porAccType.specified=true");

        // Get all the portabilityLogList where porAccType is null
        defaultPortabilityLogShouldNotBeFound("porAccType.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorAccTypeContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porAccType contains DEFAULT_POR_ACC_TYPE
        defaultPortabilityLogShouldBeFound("porAccType.contains=" + DEFAULT_POR_ACC_TYPE);

        // Get all the portabilityLogList where porAccType contains UPDATED_POR_ACC_TYPE
        defaultPortabilityLogShouldNotBeFound("porAccType.contains=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorAccTypeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porAccType does not contain DEFAULT_POR_ACC_TYPE
        defaultPortabilityLogShouldNotBeFound("porAccType.doesNotContain=" + DEFAULT_POR_ACC_TYPE);

        // Get all the portabilityLogList where porAccType does not contain UPDATED_POR_ACC_TYPE
        defaultPortabilityLogShouldBeFound("porAccType.doesNotContain=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porIdNumber equals to DEFAULT_POR_ID_NUMBER
        defaultPortabilityLogShouldBeFound("porIdNumber.equals=" + DEFAULT_POR_ID_NUMBER);

        // Get all the portabilityLogList where porIdNumber equals to UPDATED_POR_ID_NUMBER
        defaultPortabilityLogShouldNotBeFound("porIdNumber.equals=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porIdNumber not equals to DEFAULT_POR_ID_NUMBER
        defaultPortabilityLogShouldNotBeFound("porIdNumber.notEquals=" + DEFAULT_POR_ID_NUMBER);

        // Get all the portabilityLogList where porIdNumber not equals to UPDATED_POR_ID_NUMBER
        defaultPortabilityLogShouldBeFound("porIdNumber.notEquals=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdNumberIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porIdNumber in DEFAULT_POR_ID_NUMBER or UPDATED_POR_ID_NUMBER
        defaultPortabilityLogShouldBeFound("porIdNumber.in=" + DEFAULT_POR_ID_NUMBER + "," + UPDATED_POR_ID_NUMBER);

        // Get all the portabilityLogList where porIdNumber equals to UPDATED_POR_ID_NUMBER
        defaultPortabilityLogShouldNotBeFound("porIdNumber.in=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porIdNumber is not null
        defaultPortabilityLogShouldBeFound("porIdNumber.specified=true");

        // Get all the portabilityLogList where porIdNumber is null
        defaultPortabilityLogShouldNotBeFound("porIdNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdNumberContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porIdNumber contains DEFAULT_POR_ID_NUMBER
        defaultPortabilityLogShouldBeFound("porIdNumber.contains=" + DEFAULT_POR_ID_NUMBER);

        // Get all the portabilityLogList where porIdNumber contains UPDATED_POR_ID_NUMBER
        defaultPortabilityLogShouldNotBeFound("porIdNumber.contains=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorIdNumberNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porIdNumber does not contain DEFAULT_POR_ID_NUMBER
        defaultPortabilityLogShouldNotBeFound("porIdNumber.doesNotContain=" + DEFAULT_POR_ID_NUMBER);

        // Get all the portabilityLogList where porIdNumber does not contain UPDATED_POR_ID_NUMBER
        defaultPortabilityLogShouldBeFound("porIdNumber.doesNotContain=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porContactNumber equals to DEFAULT_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldBeFound("porContactNumber.equals=" + DEFAULT_POR_CONTACT_NUMBER);

        // Get all the portabilityLogList where porContactNumber equals to UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldNotBeFound("porContactNumber.equals=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorContactNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porContactNumber not equals to DEFAULT_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldNotBeFound("porContactNumber.notEquals=" + DEFAULT_POR_CONTACT_NUMBER);

        // Get all the portabilityLogList where porContactNumber not equals to UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldBeFound("porContactNumber.notEquals=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porContactNumber in DEFAULT_POR_CONTACT_NUMBER or UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldBeFound("porContactNumber.in=" + DEFAULT_POR_CONTACT_NUMBER + "," + UPDATED_POR_CONTACT_NUMBER);

        // Get all the portabilityLogList where porContactNumber equals to UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldNotBeFound("porContactNumber.in=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porContactNumber is not null
        defaultPortabilityLogShouldBeFound("porContactNumber.specified=true");

        // Get all the portabilityLogList where porContactNumber is null
        defaultPortabilityLogShouldNotBeFound("porContactNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorContactNumberContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porContactNumber contains DEFAULT_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldBeFound("porContactNumber.contains=" + DEFAULT_POR_CONTACT_NUMBER);

        // Get all the portabilityLogList where porContactNumber contains UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldNotBeFound("porContactNumber.contains=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorContactNumberNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porContactNumber does not contain DEFAULT_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldNotBeFound("porContactNumber.doesNotContain=" + DEFAULT_POR_CONTACT_NUMBER);

        // Get all the portabilityLogList where porContactNumber does not contain UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityLogShouldBeFound("porContactNumber.doesNotContain=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porStatus equals to DEFAULT_POR_STATUS
        defaultPortabilityLogShouldBeFound("porStatus.equals=" + DEFAULT_POR_STATUS);

        // Get all the portabilityLogList where porStatus equals to UPDATED_POR_STATUS
        defaultPortabilityLogShouldNotBeFound("porStatus.equals=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porStatus not equals to DEFAULT_POR_STATUS
        defaultPortabilityLogShouldNotBeFound("porStatus.notEquals=" + DEFAULT_POR_STATUS);

        // Get all the portabilityLogList where porStatus not equals to UPDATED_POR_STATUS
        defaultPortabilityLogShouldBeFound("porStatus.notEquals=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorStatusIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porStatus in DEFAULT_POR_STATUS or UPDATED_POR_STATUS
        defaultPortabilityLogShouldBeFound("porStatus.in=" + DEFAULT_POR_STATUS + "," + UPDATED_POR_STATUS);

        // Get all the portabilityLogList where porStatus equals to UPDATED_POR_STATUS
        defaultPortabilityLogShouldNotBeFound("porStatus.in=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porStatus is not null
        defaultPortabilityLogShouldBeFound("porStatus.specified=true");

        // Get all the portabilityLogList where porStatus is null
        defaultPortabilityLogShouldNotBeFound("porStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorStatusContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porStatus contains DEFAULT_POR_STATUS
        defaultPortabilityLogShouldBeFound("porStatus.contains=" + DEFAULT_POR_STATUS);

        // Get all the portabilityLogList where porStatus contains UPDATED_POR_STATUS
        defaultPortabilityLogShouldNotBeFound("porStatus.contains=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorStatusNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porStatus does not contain DEFAULT_POR_STATUS
        defaultPortabilityLogShouldNotBeFound("porStatus.doesNotContain=" + DEFAULT_POR_STATUS);

        // Get all the portabilityLogList where porStatus does not contain UPDATED_POR_STATUS
        defaultPortabilityLogShouldBeFound("porStatus.doesNotContain=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorPortedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porPortedDate equals to DEFAULT_POR_PORTED_DATE
        defaultPortabilityLogShouldBeFound("porPortedDate.equals=" + DEFAULT_POR_PORTED_DATE);

        // Get all the portabilityLogList where porPortedDate equals to UPDATED_POR_PORTED_DATE
        defaultPortabilityLogShouldNotBeFound("porPortedDate.equals=" + UPDATED_POR_PORTED_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorPortedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porPortedDate not equals to DEFAULT_POR_PORTED_DATE
        defaultPortabilityLogShouldNotBeFound("porPortedDate.notEquals=" + DEFAULT_POR_PORTED_DATE);

        // Get all the portabilityLogList where porPortedDate not equals to UPDATED_POR_PORTED_DATE
        defaultPortabilityLogShouldBeFound("porPortedDate.notEquals=" + UPDATED_POR_PORTED_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorPortedDateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porPortedDate in DEFAULT_POR_PORTED_DATE or UPDATED_POR_PORTED_DATE
        defaultPortabilityLogShouldBeFound("porPortedDate.in=" + DEFAULT_POR_PORTED_DATE + "," + UPDATED_POR_PORTED_DATE);

        // Get all the portabilityLogList where porPortedDate equals to UPDATED_POR_PORTED_DATE
        defaultPortabilityLogShouldNotBeFound("porPortedDate.in=" + UPDATED_POR_PORTED_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorPortedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porPortedDate is not null
        defaultPortabilityLogShouldBeFound("porPortedDate.specified=true");

        // Get all the portabilityLogList where porPortedDate is null
        defaultPortabilityLogShouldNotBeFound("porPortedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRoutingIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRouting equals to DEFAULT_POR_ROUTING
        defaultPortabilityLogShouldBeFound("porRouting.equals=" + DEFAULT_POR_ROUTING);

        // Get all the portabilityLogList where porRouting equals to UPDATED_POR_ROUTING
        defaultPortabilityLogShouldNotBeFound("porRouting.equals=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRoutingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRouting not equals to DEFAULT_POR_ROUTING
        defaultPortabilityLogShouldNotBeFound("porRouting.notEquals=" + DEFAULT_POR_ROUTING);

        // Get all the portabilityLogList where porRouting not equals to UPDATED_POR_ROUTING
        defaultPortabilityLogShouldBeFound("porRouting.notEquals=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRoutingIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRouting in DEFAULT_POR_ROUTING or UPDATED_POR_ROUTING
        defaultPortabilityLogShouldBeFound("porRouting.in=" + DEFAULT_POR_ROUTING + "," + UPDATED_POR_ROUTING);

        // Get all the portabilityLogList where porRouting equals to UPDATED_POR_ROUTING
        defaultPortabilityLogShouldNotBeFound("porRouting.in=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRoutingIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRouting is not null
        defaultPortabilityLogShouldBeFound("porRouting.specified=true");

        // Get all the portabilityLogList where porRouting is null
        defaultPortabilityLogShouldNotBeFound("porRouting.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRoutingContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRouting contains DEFAULT_POR_ROUTING
        defaultPortabilityLogShouldBeFound("porRouting.contains=" + DEFAULT_POR_ROUTING);

        // Get all the portabilityLogList where porRouting contains UPDATED_POR_ROUTING
        defaultPortabilityLogShouldNotBeFound("porRouting.contains=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRoutingNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRouting does not contain DEFAULT_POR_ROUTING
        defaultPortabilityLogShouldNotBeFound("porRouting.doesNotContain=" + DEFAULT_POR_ROUTING);

        // Get all the portabilityLogList where porRouting does not contain UPDATED_POR_ROUTING
        defaultPortabilityLogShouldBeFound("porRouting.doesNotContain=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porType equals to DEFAULT_POR_TYPE
        defaultPortabilityLogShouldBeFound("porType.equals=" + DEFAULT_POR_TYPE);

        // Get all the portabilityLogList where porType equals to UPDATED_POR_TYPE
        defaultPortabilityLogShouldNotBeFound("porType.equals=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porType not equals to DEFAULT_POR_TYPE
        defaultPortabilityLogShouldNotBeFound("porType.notEquals=" + DEFAULT_POR_TYPE);

        // Get all the portabilityLogList where porType not equals to UPDATED_POR_TYPE
        defaultPortabilityLogShouldBeFound("porType.notEquals=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTypeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porType in DEFAULT_POR_TYPE or UPDATED_POR_TYPE
        defaultPortabilityLogShouldBeFound("porType.in=" + DEFAULT_POR_TYPE + "," + UPDATED_POR_TYPE);

        // Get all the portabilityLogList where porType equals to UPDATED_POR_TYPE
        defaultPortabilityLogShouldNotBeFound("porType.in=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porType is not null
        defaultPortabilityLogShouldBeFound("porType.specified=true");

        // Get all the portabilityLogList where porType is null
        defaultPortabilityLogShouldNotBeFound("porType.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTypeContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porType contains DEFAULT_POR_TYPE
        defaultPortabilityLogShouldBeFound("porType.contains=" + DEFAULT_POR_TYPE);

        // Get all the portabilityLogList where porType contains UPDATED_POR_TYPE
        defaultPortabilityLogShouldNotBeFound("porType.contains=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTypeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porType does not contain DEFAULT_POR_TYPE
        defaultPortabilityLogShouldNotBeFound("porType.doesNotContain=" + DEFAULT_POR_TYPE);

        // Get all the portabilityLogList where porType does not contain UPDATED_POR_TYPE
        defaultPortabilityLogShouldBeFound("porType.doesNotContain=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpOrg equals to DEFAULT_POR_OP_ORG
        defaultPortabilityLogShouldBeFound("porOpOrg.equals=" + DEFAULT_POR_OP_ORG);

        // Get all the portabilityLogList where porOpOrg equals to UPDATED_POR_OP_ORG
        defaultPortabilityLogShouldNotBeFound("porOpOrg.equals=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpOrg not equals to DEFAULT_POR_OP_ORG
        defaultPortabilityLogShouldNotBeFound("porOpOrg.notEquals=" + DEFAULT_POR_OP_ORG);

        // Get all the portabilityLogList where porOpOrg not equals to UPDATED_POR_OP_ORG
        defaultPortabilityLogShouldBeFound("porOpOrg.notEquals=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpOrgIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpOrg in DEFAULT_POR_OP_ORG or UPDATED_POR_OP_ORG
        defaultPortabilityLogShouldBeFound("porOpOrg.in=" + DEFAULT_POR_OP_ORG + "," + UPDATED_POR_OP_ORG);

        // Get all the portabilityLogList where porOpOrg equals to UPDATED_POR_OP_ORG
        defaultPortabilityLogShouldNotBeFound("porOpOrg.in=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpOrg is not null
        defaultPortabilityLogShouldBeFound("porOpOrg.specified=true");

        // Get all the portabilityLogList where porOpOrg is null
        defaultPortabilityLogShouldNotBeFound("porOpOrg.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpOrgContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpOrg contains DEFAULT_POR_OP_ORG
        defaultPortabilityLogShouldBeFound("porOpOrg.contains=" + DEFAULT_POR_OP_ORG);

        // Get all the portabilityLogList where porOpOrg contains UPDATED_POR_OP_ORG
        defaultPortabilityLogShouldNotBeFound("porOpOrg.contains=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpOrgNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpOrg does not contain DEFAULT_POR_OP_ORG
        defaultPortabilityLogShouldNotBeFound("porOpOrg.doesNotContain=" + DEFAULT_POR_OP_ORG);

        // Get all the portabilityLogList where porOpOrg does not contain UPDATED_POR_OP_ORG
        defaultPortabilityLogShouldBeFound("porOpOrg.doesNotContain=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspCode equals to DEFAULT_POR_RSP_CODE
        defaultPortabilityLogShouldBeFound("porRspCode.equals=" + DEFAULT_POR_RSP_CODE);

        // Get all the portabilityLogList where porRspCode equals to UPDATED_POR_RSP_CODE
        defaultPortabilityLogShouldNotBeFound("porRspCode.equals=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspCode not equals to DEFAULT_POR_RSP_CODE
        defaultPortabilityLogShouldNotBeFound("porRspCode.notEquals=" + DEFAULT_POR_RSP_CODE);

        // Get all the portabilityLogList where porRspCode not equals to UPDATED_POR_RSP_CODE
        defaultPortabilityLogShouldBeFound("porRspCode.notEquals=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspCodeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspCode in DEFAULT_POR_RSP_CODE or UPDATED_POR_RSP_CODE
        defaultPortabilityLogShouldBeFound("porRspCode.in=" + DEFAULT_POR_RSP_CODE + "," + UPDATED_POR_RSP_CODE);

        // Get all the portabilityLogList where porRspCode equals to UPDATED_POR_RSP_CODE
        defaultPortabilityLogShouldNotBeFound("porRspCode.in=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspCode is not null
        defaultPortabilityLogShouldBeFound("porRspCode.specified=true");

        // Get all the portabilityLogList where porRspCode is null
        defaultPortabilityLogShouldNotBeFound("porRspCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspCodeContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspCode contains DEFAULT_POR_RSP_CODE
        defaultPortabilityLogShouldBeFound("porRspCode.contains=" + DEFAULT_POR_RSP_CODE);

        // Get all the portabilityLogList where porRspCode contains UPDATED_POR_RSP_CODE
        defaultPortabilityLogShouldNotBeFound("porRspCode.contains=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspCodeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspCode does not contain DEFAULT_POR_RSP_CODE
        defaultPortabilityLogShouldNotBeFound("porRspCode.doesNotContain=" + DEFAULT_POR_RSP_CODE);

        // Get all the portabilityLogList where porRspCode does not contain UPDATED_POR_RSP_CODE
        defaultPortabilityLogShouldBeFound("porRspCode.doesNotContain=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspNote equals to DEFAULT_POR_RSP_NOTE
        defaultPortabilityLogShouldBeFound("porRspNote.equals=" + DEFAULT_POR_RSP_NOTE);

        // Get all the portabilityLogList where porRspNote equals to UPDATED_POR_RSP_NOTE
        defaultPortabilityLogShouldNotBeFound("porRspNote.equals=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspNote not equals to DEFAULT_POR_RSP_NOTE
        defaultPortabilityLogShouldNotBeFound("porRspNote.notEquals=" + DEFAULT_POR_RSP_NOTE);

        // Get all the portabilityLogList where porRspNote not equals to UPDATED_POR_RSP_NOTE
        defaultPortabilityLogShouldBeFound("porRspNote.notEquals=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspNoteIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspNote in DEFAULT_POR_RSP_NOTE or UPDATED_POR_RSP_NOTE
        defaultPortabilityLogShouldBeFound("porRspNote.in=" + DEFAULT_POR_RSP_NOTE + "," + UPDATED_POR_RSP_NOTE);

        // Get all the portabilityLogList where porRspNote equals to UPDATED_POR_RSP_NOTE
        defaultPortabilityLogShouldNotBeFound("porRspNote.in=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspNote is not null
        defaultPortabilityLogShouldBeFound("porRspNote.specified=true");

        // Get all the portabilityLogList where porRspNote is null
        defaultPortabilityLogShouldNotBeFound("porRspNote.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspNoteContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspNote contains DEFAULT_POR_RSP_NOTE
        defaultPortabilityLogShouldBeFound("porRspNote.contains=" + DEFAULT_POR_RSP_NOTE);

        // Get all the portabilityLogList where porRspNote contains UPDATED_POR_RSP_NOTE
        defaultPortabilityLogShouldNotBeFound("porRspNote.contains=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorRspNoteNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porRspNote does not contain DEFAULT_POR_RSP_NOTE
        defaultPortabilityLogShouldNotBeFound("porRspNote.doesNotContain=" + DEFAULT_POR_RSP_NOTE);

        // Get all the portabilityLogList where porRspNote does not contain UPDATED_POR_RSP_NOTE
        defaultPortabilityLogShouldBeFound("porRspNote.doesNotContain=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCancelNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCancelNote equals to DEFAULT_POR_CANCEL_NOTE
        defaultPortabilityLogShouldBeFound("porCancelNote.equals=" + DEFAULT_POR_CANCEL_NOTE);

        // Get all the portabilityLogList where porCancelNote equals to UPDATED_POR_CANCEL_NOTE
        defaultPortabilityLogShouldNotBeFound("porCancelNote.equals=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCancelNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCancelNote not equals to DEFAULT_POR_CANCEL_NOTE
        defaultPortabilityLogShouldNotBeFound("porCancelNote.notEquals=" + DEFAULT_POR_CANCEL_NOTE);

        // Get all the portabilityLogList where porCancelNote not equals to UPDATED_POR_CANCEL_NOTE
        defaultPortabilityLogShouldBeFound("porCancelNote.notEquals=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCancelNoteIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCancelNote in DEFAULT_POR_CANCEL_NOTE or UPDATED_POR_CANCEL_NOTE
        defaultPortabilityLogShouldBeFound("porCancelNote.in=" + DEFAULT_POR_CANCEL_NOTE + "," + UPDATED_POR_CANCEL_NOTE);

        // Get all the portabilityLogList where porCancelNote equals to UPDATED_POR_CANCEL_NOTE
        defaultPortabilityLogShouldNotBeFound("porCancelNote.in=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCancelNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCancelNote is not null
        defaultPortabilityLogShouldBeFound("porCancelNote.specified=true");

        // Get all the portabilityLogList where porCancelNote is null
        defaultPortabilityLogShouldNotBeFound("porCancelNote.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCancelNoteContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCancelNote contains DEFAULT_POR_CANCEL_NOTE
        defaultPortabilityLogShouldBeFound("porCancelNote.contains=" + DEFAULT_POR_CANCEL_NOTE);

        // Get all the portabilityLogList where porCancelNote contains UPDATED_POR_CANCEL_NOTE
        defaultPortabilityLogShouldNotBeFound("porCancelNote.contains=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCancelNoteNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCancelNote does not contain DEFAULT_POR_CANCEL_NOTE
        defaultPortabilityLogShouldNotBeFound("porCancelNote.doesNotContain=" + DEFAULT_POR_CANCEL_NOTE);

        // Get all the portabilityLogList where porCancelNote does not contain UPDATED_POR_CANCEL_NOTE
        defaultPortabilityLogShouldBeFound("porCancelNote.doesNotContain=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorMnpidIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porMnpid equals to DEFAULT_POR_MNPID
        defaultPortabilityLogShouldBeFound("porMnpid.equals=" + DEFAULT_POR_MNPID);

        // Get all the portabilityLogList where porMnpid equals to UPDATED_POR_MNPID
        defaultPortabilityLogShouldNotBeFound("porMnpid.equals=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorMnpidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porMnpid not equals to DEFAULT_POR_MNPID
        defaultPortabilityLogShouldNotBeFound("porMnpid.notEquals=" + DEFAULT_POR_MNPID);

        // Get all the portabilityLogList where porMnpid not equals to UPDATED_POR_MNPID
        defaultPortabilityLogShouldBeFound("porMnpid.notEquals=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorMnpidIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porMnpid in DEFAULT_POR_MNPID or UPDATED_POR_MNPID
        defaultPortabilityLogShouldBeFound("porMnpid.in=" + DEFAULT_POR_MNPID + "," + UPDATED_POR_MNPID);

        // Get all the portabilityLogList where porMnpid equals to UPDATED_POR_MNPID
        defaultPortabilityLogShouldNotBeFound("porMnpid.in=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorMnpidIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porMnpid is not null
        defaultPortabilityLogShouldBeFound("porMnpid.specified=true");

        // Get all the portabilityLogList where porMnpid is null
        defaultPortabilityLogShouldNotBeFound("porMnpid.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorMnpidContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porMnpid contains DEFAULT_POR_MNPID
        defaultPortabilityLogShouldBeFound("porMnpid.contains=" + DEFAULT_POR_MNPID);

        // Get all the portabilityLogList where porMnpid contains UPDATED_POR_MNPID
        defaultPortabilityLogShouldNotBeFound("porMnpid.contains=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorMnpidNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porMnpid does not contain DEFAULT_POR_MNPID
        defaultPortabilityLogShouldNotBeFound("porMnpid.doesNotContain=" + DEFAULT_POR_MNPID);

        // Get all the portabilityLogList where porMnpid does not contain UPDATED_POR_MNPID
        defaultPortabilityLogShouldBeFound("porMnpid.doesNotContain=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portationDate equals to DEFAULT_PORTATION_DATE
        defaultPortabilityLogShouldBeFound("portationDate.equals=" + DEFAULT_PORTATION_DATE);

        // Get all the portabilityLogList where portationDate equals to UPDATED_PORTATION_DATE
        defaultPortabilityLogShouldNotBeFound("portationDate.equals=" + UPDATED_PORTATION_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portationDate not equals to DEFAULT_PORTATION_DATE
        defaultPortabilityLogShouldNotBeFound("portationDate.notEquals=" + DEFAULT_PORTATION_DATE);

        // Get all the portabilityLogList where portationDate not equals to UPDATED_PORTATION_DATE
        defaultPortabilityLogShouldBeFound("portationDate.notEquals=" + UPDATED_PORTATION_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortationDateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portationDate in DEFAULT_PORTATION_DATE or UPDATED_PORTATION_DATE
        defaultPortabilityLogShouldBeFound("portationDate.in=" + DEFAULT_PORTATION_DATE + "," + UPDATED_PORTATION_DATE);

        // Get all the portabilityLogList where portationDate equals to UPDATED_PORTATION_DATE
        defaultPortabilityLogShouldNotBeFound("portationDate.in=" + UPDATED_PORTATION_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portationDate is not null
        defaultPortabilityLogShouldBeFound("portationDate.specified=true");

        // Get all the portabilityLogList where portationDate is null
        defaultPortabilityLogShouldNotBeFound("portationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortaCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portaCode equals to DEFAULT_PORTA_CODE
        defaultPortabilityLogShouldBeFound("portaCode.equals=" + DEFAULT_PORTA_CODE);

        // Get all the portabilityLogList where portaCode equals to UPDATED_PORTA_CODE
        defaultPortabilityLogShouldNotBeFound("portaCode.equals=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortaCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portaCode not equals to DEFAULT_PORTA_CODE
        defaultPortabilityLogShouldNotBeFound("portaCode.notEquals=" + DEFAULT_PORTA_CODE);

        // Get all the portabilityLogList where portaCode not equals to UPDATED_PORTA_CODE
        defaultPortabilityLogShouldBeFound("portaCode.notEquals=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortaCodeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portaCode in DEFAULT_PORTA_CODE or UPDATED_PORTA_CODE
        defaultPortabilityLogShouldBeFound("portaCode.in=" + DEFAULT_PORTA_CODE + "," + UPDATED_PORTA_CODE);

        // Get all the portabilityLogList where portaCode equals to UPDATED_PORTA_CODE
        defaultPortabilityLogShouldNotBeFound("portaCode.in=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortaCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portaCode is not null
        defaultPortabilityLogShouldBeFound("portaCode.specified=true");

        // Get all the portabilityLogList where portaCode is null
        defaultPortabilityLogShouldNotBeFound("portaCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortaCodeContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portaCode contains DEFAULT_PORTA_CODE
        defaultPortabilityLogShouldBeFound("portaCode.contains=" + DEFAULT_PORTA_CODE);

        // Get all the portabilityLogList where portaCode contains UPDATED_PORTA_CODE
        defaultPortabilityLogShouldNotBeFound("portaCode.contains=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPortaCodeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where portaCode does not contain DEFAULT_PORTA_CODE
        defaultPortabilityLogShouldNotBeFound("portaCode.doesNotContain=" + DEFAULT_PORTA_CODE);

        // Get all the portabilityLogList where portaCode does not contain UPDATED_PORTA_CODE
        defaultPortabilityLogShouldBeFound("portaCode.doesNotContain=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByMvnoIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where mvno equals to DEFAULT_MVNO
        defaultPortabilityLogShouldBeFound("mvno.equals=" + DEFAULT_MVNO);

        // Get all the portabilityLogList where mvno equals to UPDATED_MVNO
        defaultPortabilityLogShouldNotBeFound("mvno.equals=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByMvnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where mvno not equals to DEFAULT_MVNO
        defaultPortabilityLogShouldNotBeFound("mvno.notEquals=" + DEFAULT_MVNO);

        // Get all the portabilityLogList where mvno not equals to UPDATED_MVNO
        defaultPortabilityLogShouldBeFound("mvno.notEquals=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByMvnoIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where mvno in DEFAULT_MVNO or UPDATED_MVNO
        defaultPortabilityLogShouldBeFound("mvno.in=" + DEFAULT_MVNO + "," + UPDATED_MVNO);

        // Get all the portabilityLogList where mvno equals to UPDATED_MVNO
        defaultPortabilityLogShouldNotBeFound("mvno.in=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByMvnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where mvno is not null
        defaultPortabilityLogShouldBeFound("mvno.specified=true");

        // Get all the portabilityLogList where mvno is null
        defaultPortabilityLogShouldNotBeFound("mvno.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByMvnoContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where mvno contains DEFAULT_MVNO
        defaultPortabilityLogShouldBeFound("mvno.contains=" + DEFAULT_MVNO);

        // Get all the portabilityLogList where mvno contains UPDATED_MVNO
        defaultPortabilityLogShouldNotBeFound("mvno.contains=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByMvnoNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where mvno does not contain DEFAULT_MVNO
        defaultPortabilityLogShouldNotBeFound("mvno.doesNotContain=" + DEFAULT_MVNO);

        // Get all the portabilityLogList where mvno does not contain UPDATED_MVNO
        defaultPortabilityLogShouldBeFound("mvno.doesNotContain=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByContextIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where context equals to DEFAULT_CONTEXT
        defaultPortabilityLogShouldBeFound("context.equals=" + DEFAULT_CONTEXT);

        // Get all the portabilityLogList where context equals to UPDATED_CONTEXT
        defaultPortabilityLogShouldNotBeFound("context.equals=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByContextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where context not equals to DEFAULT_CONTEXT
        defaultPortabilityLogShouldNotBeFound("context.notEquals=" + DEFAULT_CONTEXT);

        // Get all the portabilityLogList where context not equals to UPDATED_CONTEXT
        defaultPortabilityLogShouldBeFound("context.notEquals=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByContextIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where context in DEFAULT_CONTEXT or UPDATED_CONTEXT
        defaultPortabilityLogShouldBeFound("context.in=" + DEFAULT_CONTEXT + "," + UPDATED_CONTEXT);

        // Get all the portabilityLogList where context equals to UPDATED_CONTEXT
        defaultPortabilityLogShouldNotBeFound("context.in=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByContextIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where context is not null
        defaultPortabilityLogShouldBeFound("context.specified=true");

        // Get all the portabilityLogList where context is null
        defaultPortabilityLogShouldNotBeFound("context.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByContextContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where context contains DEFAULT_CONTEXT
        defaultPortabilityLogShouldBeFound("context.contains=" + DEFAULT_CONTEXT);

        // Get all the portabilityLogList where context contains UPDATED_CONTEXT
        defaultPortabilityLogShouldNotBeFound("context.contains=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByContextNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where context does not contain DEFAULT_CONTEXT
        defaultPortabilityLogShouldNotBeFound("context.doesNotContain=" + DEFAULT_CONTEXT);

        // Get all the portabilityLogList where context does not contain UPDATED_CONTEXT
        defaultPortabilityLogShouldBeFound("context.doesNotContain=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrCode equals to DEFAULT_POR_ERR_CODE
        defaultPortabilityLogShouldBeFound("porErrCode.equals=" + DEFAULT_POR_ERR_CODE);

        // Get all the portabilityLogList where porErrCode equals to UPDATED_POR_ERR_CODE
        defaultPortabilityLogShouldNotBeFound("porErrCode.equals=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrCode not equals to DEFAULT_POR_ERR_CODE
        defaultPortabilityLogShouldNotBeFound("porErrCode.notEquals=" + DEFAULT_POR_ERR_CODE);

        // Get all the portabilityLogList where porErrCode not equals to UPDATED_POR_ERR_CODE
        defaultPortabilityLogShouldBeFound("porErrCode.notEquals=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrCode in DEFAULT_POR_ERR_CODE or UPDATED_POR_ERR_CODE
        defaultPortabilityLogShouldBeFound("porErrCode.in=" + DEFAULT_POR_ERR_CODE + "," + UPDATED_POR_ERR_CODE);

        // Get all the portabilityLogList where porErrCode equals to UPDATED_POR_ERR_CODE
        defaultPortabilityLogShouldNotBeFound("porErrCode.in=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrCode is not null
        defaultPortabilityLogShouldBeFound("porErrCode.specified=true");

        // Get all the portabilityLogList where porErrCode is null
        defaultPortabilityLogShouldNotBeFound("porErrCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrCodeContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrCode contains DEFAULT_POR_ERR_CODE
        defaultPortabilityLogShouldBeFound("porErrCode.contains=" + DEFAULT_POR_ERR_CODE);

        // Get all the portabilityLogList where porErrCode contains UPDATED_POR_ERR_CODE
        defaultPortabilityLogShouldNotBeFound("porErrCode.contains=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrCodeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrCode does not contain DEFAULT_POR_ERR_CODE
        defaultPortabilityLogShouldNotBeFound("porErrCode.doesNotContain=" + DEFAULT_POR_ERR_CODE);

        // Get all the portabilityLogList where porErrCode does not contain UPDATED_POR_ERR_CODE
        defaultPortabilityLogShouldBeFound("porErrCode.doesNotContain=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrMessage equals to DEFAULT_POR_ERR_MESSAGE
        defaultPortabilityLogShouldBeFound("porErrMessage.equals=" + DEFAULT_POR_ERR_MESSAGE);

        // Get all the portabilityLogList where porErrMessage equals to UPDATED_POR_ERR_MESSAGE
        defaultPortabilityLogShouldNotBeFound("porErrMessage.equals=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrMessage not equals to DEFAULT_POR_ERR_MESSAGE
        defaultPortabilityLogShouldNotBeFound("porErrMessage.notEquals=" + DEFAULT_POR_ERR_MESSAGE);

        // Get all the portabilityLogList where porErrMessage not equals to UPDATED_POR_ERR_MESSAGE
        defaultPortabilityLogShouldBeFound("porErrMessage.notEquals=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrMessageIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrMessage in DEFAULT_POR_ERR_MESSAGE or UPDATED_POR_ERR_MESSAGE
        defaultPortabilityLogShouldBeFound("porErrMessage.in=" + DEFAULT_POR_ERR_MESSAGE + "," + UPDATED_POR_ERR_MESSAGE);

        // Get all the portabilityLogList where porErrMessage equals to UPDATED_POR_ERR_MESSAGE
        defaultPortabilityLogShouldNotBeFound("porErrMessage.in=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrMessage is not null
        defaultPortabilityLogShouldBeFound("porErrMessage.specified=true");

        // Get all the portabilityLogList where porErrMessage is null
        defaultPortabilityLogShouldNotBeFound("porErrMessage.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrMessageContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrMessage contains DEFAULT_POR_ERR_MESSAGE
        defaultPortabilityLogShouldBeFound("porErrMessage.contains=" + DEFAULT_POR_ERR_MESSAGE);

        // Get all the portabilityLogList where porErrMessage contains UPDATED_POR_ERR_MESSAGE
        defaultPortabilityLogShouldNotBeFound("porErrMessage.contains=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorErrMessageNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porErrMessage does not contain DEFAULT_POR_ERR_MESSAGE
        defaultPortabilityLogShouldNotBeFound("porErrMessage.doesNotContain=" + DEFAULT_POR_ERR_MESSAGE);

        // Get all the portabilityLogList where porErrMessage does not contain UPDATED_POR_ERR_MESSAGE
        defaultPortabilityLogShouldBeFound("porErrMessage.doesNotContain=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpdIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpd equals to DEFAULT_POR_OPD
        defaultPortabilityLogShouldBeFound("porOpd.equals=" + DEFAULT_POR_OPD);

        // Get all the portabilityLogList where porOpd equals to UPDATED_POR_OPD
        defaultPortabilityLogShouldNotBeFound("porOpd.equals=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpd not equals to DEFAULT_POR_OPD
        defaultPortabilityLogShouldNotBeFound("porOpd.notEquals=" + DEFAULT_POR_OPD);

        // Get all the portabilityLogList where porOpd not equals to UPDATED_POR_OPD
        defaultPortabilityLogShouldBeFound("porOpd.notEquals=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpdIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpd in DEFAULT_POR_OPD or UPDATED_POR_OPD
        defaultPortabilityLogShouldBeFound("porOpd.in=" + DEFAULT_POR_OPD + "," + UPDATED_POR_OPD);

        // Get all the portabilityLogList where porOpd equals to UPDATED_POR_OPD
        defaultPortabilityLogShouldNotBeFound("porOpd.in=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpdIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpd is not null
        defaultPortabilityLogShouldBeFound("porOpd.specified=true");

        // Get all the portabilityLogList where porOpd is null
        defaultPortabilityLogShouldNotBeFound("porOpd.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpdContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpd contains DEFAULT_POR_OPD
        defaultPortabilityLogShouldBeFound("porOpd.contains=" + DEFAULT_POR_OPD);

        // Get all the portabilityLogList where porOpd contains UPDATED_POR_OPD
        defaultPortabilityLogShouldNotBeFound("porOpd.contains=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorOpdNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porOpd does not contain DEFAULT_POR_OPD
        defaultPortabilityLogShouldNotBeFound("porOpd.doesNotContain=" + DEFAULT_POR_OPD);

        // Get all the portabilityLogList where porOpd does not contain UPDATED_POR_OPD
        defaultPortabilityLogShouldBeFound("porOpd.doesNotContain=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumType equals to DEFAULT_POR_NUM_TYPE
        defaultPortabilityLogShouldBeFound("porNumType.equals=" + DEFAULT_POR_NUM_TYPE);

        // Get all the portabilityLogList where porNumType equals to UPDATED_POR_NUM_TYPE
        defaultPortabilityLogShouldNotBeFound("porNumType.equals=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumType not equals to DEFAULT_POR_NUM_TYPE
        defaultPortabilityLogShouldNotBeFound("porNumType.notEquals=" + DEFAULT_POR_NUM_TYPE);

        // Get all the portabilityLogList where porNumType not equals to UPDATED_POR_NUM_TYPE
        defaultPortabilityLogShouldBeFound("porNumType.notEquals=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumTypeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumType in DEFAULT_POR_NUM_TYPE or UPDATED_POR_NUM_TYPE
        defaultPortabilityLogShouldBeFound("porNumType.in=" + DEFAULT_POR_NUM_TYPE + "," + UPDATED_POR_NUM_TYPE);

        // Get all the portabilityLogList where porNumType equals to UPDATED_POR_NUM_TYPE
        defaultPortabilityLogShouldNotBeFound("porNumType.in=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumType is not null
        defaultPortabilityLogShouldBeFound("porNumType.specified=true");

        // Get all the portabilityLogList where porNumType is null
        defaultPortabilityLogShouldNotBeFound("porNumType.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumTypeContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumType contains DEFAULT_POR_NUM_TYPE
        defaultPortabilityLogShouldBeFound("porNumType.contains=" + DEFAULT_POR_NUM_TYPE);

        // Get all the portabilityLogList where porNumType contains UPDATED_POR_NUM_TYPE
        defaultPortabilityLogShouldNotBeFound("porNumType.contains=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNumTypeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNumType does not contain DEFAULT_POR_NUM_TYPE
        defaultPortabilityLogShouldNotBeFound("porNumType.doesNotContain=" + DEFAULT_POR_NUM_TYPE);

        // Get all the portabilityLogList where porNumType does not contain UPDATED_POR_NUM_TYPE
        defaultPortabilityLogShouldBeFound("porNumType.doesNotContain=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNote equals to DEFAULT_POR_NOTE
        defaultPortabilityLogShouldBeFound("porNote.equals=" + DEFAULT_POR_NOTE);

        // Get all the portabilityLogList where porNote equals to UPDATED_POR_NOTE
        defaultPortabilityLogShouldNotBeFound("porNote.equals=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNote not equals to DEFAULT_POR_NOTE
        defaultPortabilityLogShouldNotBeFound("porNote.notEquals=" + DEFAULT_POR_NOTE);

        // Get all the portabilityLogList where porNote not equals to UPDATED_POR_NOTE
        defaultPortabilityLogShouldBeFound("porNote.notEquals=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNoteIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNote in DEFAULT_POR_NOTE or UPDATED_POR_NOTE
        defaultPortabilityLogShouldBeFound("porNote.in=" + DEFAULT_POR_NOTE + "," + UPDATED_POR_NOTE);

        // Get all the portabilityLogList where porNote equals to UPDATED_POR_NOTE
        defaultPortabilityLogShouldNotBeFound("porNote.in=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNote is not null
        defaultPortabilityLogShouldBeFound("porNote.specified=true");

        // Get all the portabilityLogList where porNote is null
        defaultPortabilityLogShouldNotBeFound("porNote.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNoteContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNote contains DEFAULT_POR_NOTE
        defaultPortabilityLogShouldBeFound("porNote.contains=" + DEFAULT_POR_NOTE);

        // Get all the portabilityLogList where porNote contains UPDATED_POR_NOTE
        defaultPortabilityLogShouldNotBeFound("porNote.contains=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorNoteNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porNote does not contain DEFAULT_POR_NOTE
        defaultPortabilityLogShouldNotBeFound("porNote.doesNotContain=" + DEFAULT_POR_NOTE);

        // Get all the portabilityLogList where porNote does not contain UPDATED_POR_NOTE
        defaultPortabilityLogShouldBeFound("porNote.doesNotContain=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorDeadlineIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porDeadline equals to DEFAULT_POR_DEADLINE
        defaultPortabilityLogShouldBeFound("porDeadline.equals=" + DEFAULT_POR_DEADLINE);

        // Get all the portabilityLogList where porDeadline equals to UPDATED_POR_DEADLINE
        defaultPortabilityLogShouldNotBeFound("porDeadline.equals=" + UPDATED_POR_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorDeadlineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porDeadline not equals to DEFAULT_POR_DEADLINE
        defaultPortabilityLogShouldNotBeFound("porDeadline.notEquals=" + DEFAULT_POR_DEADLINE);

        // Get all the portabilityLogList where porDeadline not equals to UPDATED_POR_DEADLINE
        defaultPortabilityLogShouldBeFound("porDeadline.notEquals=" + UPDATED_POR_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorDeadlineIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porDeadline in DEFAULT_POR_DEADLINE or UPDATED_POR_DEADLINE
        defaultPortabilityLogShouldBeFound("porDeadline.in=" + DEFAULT_POR_DEADLINE + "," + UPDATED_POR_DEADLINE);

        // Get all the portabilityLogList where porDeadline equals to UPDATED_POR_DEADLINE
        defaultPortabilityLogShouldNotBeFound("porDeadline.in=" + UPDATED_POR_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorDeadlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porDeadline is not null
        defaultPortabilityLogShouldBeFound("porDeadline.specified=true");

        // Get all the portabilityLogList where porDeadline is null
        defaultPortabilityLogShouldNotBeFound("porDeadline.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorResponseTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porResponseTimestamp equals to DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldBeFound("porResponseTimestamp.equals=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityLogList where porResponseTimestamp equals to UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("porResponseTimestamp.equals=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorResponseTimestampIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porResponseTimestamp not equals to DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("porResponseTimestamp.notEquals=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityLogList where porResponseTimestamp not equals to UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldBeFound("porResponseTimestamp.notEquals=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorResponseTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porResponseTimestamp in DEFAULT_POR_RESPONSE_TIMESTAMP or UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldBeFound(
            "porResponseTimestamp.in=" + DEFAULT_POR_RESPONSE_TIMESTAMP + "," + UPDATED_POR_RESPONSE_TIMESTAMP
        );

        // Get all the portabilityLogList where porResponseTimestamp equals to UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("porResponseTimestamp.in=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorResponseTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porResponseTimestamp is not null
        defaultPortabilityLogShouldBeFound("porResponseTimestamp.specified=true");

        // Get all the portabilityLogList where porResponseTimestamp is null
        defaultPortabilityLogShouldNotBeFound("porResponseTimestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorResponseTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porResponseTimestamp is greater than or equal to DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldBeFound("porResponseTimestamp.greaterThanOrEqual=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityLogList where porResponseTimestamp is greater than or equal to UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("porResponseTimestamp.greaterThanOrEqual=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorResponseTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porResponseTimestamp is less than or equal to DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldBeFound("porResponseTimestamp.lessThanOrEqual=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityLogList where porResponseTimestamp is less than or equal to SMALLER_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("porResponseTimestamp.lessThanOrEqual=" + SMALLER_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorResponseTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porResponseTimestamp is less than DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("porResponseTimestamp.lessThan=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityLogList where porResponseTimestamp is less than UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldBeFound("porResponseTimestamp.lessThan=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorResponseTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porResponseTimestamp is greater than DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("porResponseTimestamp.greaterThan=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityLogList where porResponseTimestamp is greater than SMALLER_POR_RESPONSE_TIMESTAMP
        defaultPortabilityLogShouldBeFound("porResponseTimestamp.greaterThan=" + SMALLER_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorEligibleIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porEligible equals to DEFAULT_POR_ELIGIBLE
        defaultPortabilityLogShouldBeFound("porEligible.equals=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityLogList where porEligible equals to UPDATED_POR_ELIGIBLE
        defaultPortabilityLogShouldNotBeFound("porEligible.equals=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorEligibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porEligible not equals to DEFAULT_POR_ELIGIBLE
        defaultPortabilityLogShouldNotBeFound("porEligible.notEquals=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityLogList where porEligible not equals to UPDATED_POR_ELIGIBLE
        defaultPortabilityLogShouldBeFound("porEligible.notEquals=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorEligibleIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porEligible in DEFAULT_POR_ELIGIBLE or UPDATED_POR_ELIGIBLE
        defaultPortabilityLogShouldBeFound("porEligible.in=" + DEFAULT_POR_ELIGIBLE + "," + UPDATED_POR_ELIGIBLE);

        // Get all the portabilityLogList where porEligible equals to UPDATED_POR_ELIGIBLE
        defaultPortabilityLogShouldNotBeFound("porEligible.in=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorEligibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porEligible is not null
        defaultPortabilityLogShouldBeFound("porEligible.specified=true");

        // Get all the portabilityLogList where porEligible is null
        defaultPortabilityLogShouldNotBeFound("porEligible.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorEligibleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porEligible is greater than or equal to DEFAULT_POR_ELIGIBLE
        defaultPortabilityLogShouldBeFound("porEligible.greaterThanOrEqual=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityLogList where porEligible is greater than or equal to UPDATED_POR_ELIGIBLE
        defaultPortabilityLogShouldNotBeFound("porEligible.greaterThanOrEqual=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorEligibleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porEligible is less than or equal to DEFAULT_POR_ELIGIBLE
        defaultPortabilityLogShouldBeFound("porEligible.lessThanOrEqual=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityLogList where porEligible is less than or equal to SMALLER_POR_ELIGIBLE
        defaultPortabilityLogShouldNotBeFound("porEligible.lessThanOrEqual=" + SMALLER_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorEligibleIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porEligible is less than DEFAULT_POR_ELIGIBLE
        defaultPortabilityLogShouldNotBeFound("porEligible.lessThan=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityLogList where porEligible is less than UPDATED_POR_ELIGIBLE
        defaultPortabilityLogShouldBeFound("porEligible.lessThan=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorEligibleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porEligible is greater than DEFAULT_POR_ELIGIBLE
        defaultPortabilityLogShouldNotBeFound("porEligible.greaterThan=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityLogList where porEligible is greater than SMALLER_POR_ELIGIBLE
        defaultPortabilityLogShouldBeFound("porEligible.greaterThan=" + SMALLER_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorBillingOkIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porBillingOk equals to DEFAULT_POR_BILLING_OK
        defaultPortabilityLogShouldBeFound("porBillingOk.equals=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityLogList where porBillingOk equals to UPDATED_POR_BILLING_OK
        defaultPortabilityLogShouldNotBeFound("porBillingOk.equals=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorBillingOkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porBillingOk not equals to DEFAULT_POR_BILLING_OK
        defaultPortabilityLogShouldNotBeFound("porBillingOk.notEquals=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityLogList where porBillingOk not equals to UPDATED_POR_BILLING_OK
        defaultPortabilityLogShouldBeFound("porBillingOk.notEquals=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorBillingOkIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porBillingOk in DEFAULT_POR_BILLING_OK or UPDATED_POR_BILLING_OK
        defaultPortabilityLogShouldBeFound("porBillingOk.in=" + DEFAULT_POR_BILLING_OK + "," + UPDATED_POR_BILLING_OK);

        // Get all the portabilityLogList where porBillingOk equals to UPDATED_POR_BILLING_OK
        defaultPortabilityLogShouldNotBeFound("porBillingOk.in=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorBillingOkIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porBillingOk is not null
        defaultPortabilityLogShouldBeFound("porBillingOk.specified=true");

        // Get all the portabilityLogList where porBillingOk is null
        defaultPortabilityLogShouldNotBeFound("porBillingOk.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorBillingOkIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porBillingOk is greater than or equal to DEFAULT_POR_BILLING_OK
        defaultPortabilityLogShouldBeFound("porBillingOk.greaterThanOrEqual=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityLogList where porBillingOk is greater than or equal to UPDATED_POR_BILLING_OK
        defaultPortabilityLogShouldNotBeFound("porBillingOk.greaterThanOrEqual=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorBillingOkIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porBillingOk is less than or equal to DEFAULT_POR_BILLING_OK
        defaultPortabilityLogShouldBeFound("porBillingOk.lessThanOrEqual=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityLogList where porBillingOk is less than or equal to SMALLER_POR_BILLING_OK
        defaultPortabilityLogShouldNotBeFound("porBillingOk.lessThanOrEqual=" + SMALLER_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorBillingOkIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porBillingOk is less than DEFAULT_POR_BILLING_OK
        defaultPortabilityLogShouldNotBeFound("porBillingOk.lessThan=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityLogList where porBillingOk is less than UPDATED_POR_BILLING_OK
        defaultPortabilityLogShouldBeFound("porBillingOk.lessThan=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorBillingOkIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porBillingOk is greater than DEFAULT_POR_BILLING_OK
        defaultPortabilityLogShouldNotBeFound("porBillingOk.greaterThan=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityLogList where porBillingOk is greater than SMALLER_POR_BILLING_OK
        defaultPortabilityLogShouldBeFound("porBillingOk.greaterThan=" + SMALLER_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByIntermediaryActionStateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where intermediaryActionState equals to DEFAULT_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldBeFound("intermediaryActionState.equals=" + DEFAULT_INTERMEDIARY_ACTION_STATE);

        // Get all the portabilityLogList where intermediaryActionState equals to UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldNotBeFound("intermediaryActionState.equals=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByIntermediaryActionStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where intermediaryActionState not equals to DEFAULT_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldNotBeFound("intermediaryActionState.notEquals=" + DEFAULT_INTERMEDIARY_ACTION_STATE);

        // Get all the portabilityLogList where intermediaryActionState not equals to UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldBeFound("intermediaryActionState.notEquals=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByIntermediaryActionStateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where intermediaryActionState in DEFAULT_INTERMEDIARY_ACTION_STATE or UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldBeFound(
            "intermediaryActionState.in=" + DEFAULT_INTERMEDIARY_ACTION_STATE + "," + UPDATED_INTERMEDIARY_ACTION_STATE
        );

        // Get all the portabilityLogList where intermediaryActionState equals to UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldNotBeFound("intermediaryActionState.in=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByIntermediaryActionStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where intermediaryActionState is not null
        defaultPortabilityLogShouldBeFound("intermediaryActionState.specified=true");

        // Get all the portabilityLogList where intermediaryActionState is null
        defaultPortabilityLogShouldNotBeFound("intermediaryActionState.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByIntermediaryActionStateContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where intermediaryActionState contains DEFAULT_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldBeFound("intermediaryActionState.contains=" + DEFAULT_INTERMEDIARY_ACTION_STATE);

        // Get all the portabilityLogList where intermediaryActionState contains UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldNotBeFound("intermediaryActionState.contains=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByIntermediaryActionStateNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where intermediaryActionState does not contain DEFAULT_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldNotBeFound("intermediaryActionState.doesNotContain=" + DEFAULT_INTERMEDIARY_ACTION_STATE);

        // Get all the portabilityLogList where intermediaryActionState does not contain UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityLogShouldBeFound("intermediaryActionState.doesNotContain=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCrDateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCrDate equals to DEFAULT_POR_CR_DATE
        defaultPortabilityLogShouldBeFound("porCrDate.equals=" + DEFAULT_POR_CR_DATE);

        // Get all the portabilityLogList where porCrDate equals to UPDATED_POR_CR_DATE
        defaultPortabilityLogShouldNotBeFound("porCrDate.equals=" + UPDATED_POR_CR_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCrDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCrDate not equals to DEFAULT_POR_CR_DATE
        defaultPortabilityLogShouldNotBeFound("porCrDate.notEquals=" + DEFAULT_POR_CR_DATE);

        // Get all the portabilityLogList where porCrDate not equals to UPDATED_POR_CR_DATE
        defaultPortabilityLogShouldBeFound("porCrDate.notEquals=" + UPDATED_POR_CR_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCrDateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCrDate in DEFAULT_POR_CR_DATE or UPDATED_POR_CR_DATE
        defaultPortabilityLogShouldBeFound("porCrDate.in=" + DEFAULT_POR_CR_DATE + "," + UPDATED_POR_CR_DATE);

        // Get all the portabilityLogList where porCrDate equals to UPDATED_POR_CR_DATE
        defaultPortabilityLogShouldNotBeFound("porCrDate.in=" + UPDATED_POR_CR_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorCrDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porCrDate is not null
        defaultPortabilityLogShouldBeFound("porCrDate.specified=true");

        // Get all the portabilityLogList where porCrDate is null
        defaultPortabilityLogShouldNotBeFound("porCrDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorUpdDateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porUpdDate equals to DEFAULT_POR_UPD_DATE
        defaultPortabilityLogShouldBeFound("porUpdDate.equals=" + DEFAULT_POR_UPD_DATE);

        // Get all the portabilityLogList where porUpdDate equals to UPDATED_POR_UPD_DATE
        defaultPortabilityLogShouldNotBeFound("porUpdDate.equals=" + UPDATED_POR_UPD_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorUpdDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porUpdDate not equals to DEFAULT_POR_UPD_DATE
        defaultPortabilityLogShouldNotBeFound("porUpdDate.notEquals=" + DEFAULT_POR_UPD_DATE);

        // Get all the portabilityLogList where porUpdDate not equals to UPDATED_POR_UPD_DATE
        defaultPortabilityLogShouldBeFound("porUpdDate.notEquals=" + UPDATED_POR_UPD_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorUpdDateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porUpdDate in DEFAULT_POR_UPD_DATE or UPDATED_POR_UPD_DATE
        defaultPortabilityLogShouldBeFound("porUpdDate.in=" + DEFAULT_POR_UPD_DATE + "," + UPDATED_POR_UPD_DATE);

        // Get all the portabilityLogList where porUpdDate equals to UPDATED_POR_UPD_DATE
        defaultPortabilityLogShouldNotBeFound("porUpdDate.in=" + UPDATED_POR_UPD_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorUpdDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porUpdDate is not null
        defaultPortabilityLogShouldBeFound("porUpdDate.specified=true");

        // Get all the portabilityLogList where porUpdDate is null
        defaultPortabilityLogShouldNotBeFound("porUpdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechStatus equals to DEFAULT_POR_TECH_STATUS
        defaultPortabilityLogShouldBeFound("porTechStatus.equals=" + DEFAULT_POR_TECH_STATUS);

        // Get all the portabilityLogList where porTechStatus equals to UPDATED_POR_TECH_STATUS
        defaultPortabilityLogShouldNotBeFound("porTechStatus.equals=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechStatus not equals to DEFAULT_POR_TECH_STATUS
        defaultPortabilityLogShouldNotBeFound("porTechStatus.notEquals=" + DEFAULT_POR_TECH_STATUS);

        // Get all the portabilityLogList where porTechStatus not equals to UPDATED_POR_TECH_STATUS
        defaultPortabilityLogShouldBeFound("porTechStatus.notEquals=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechStatusIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechStatus in DEFAULT_POR_TECH_STATUS or UPDATED_POR_TECH_STATUS
        defaultPortabilityLogShouldBeFound("porTechStatus.in=" + DEFAULT_POR_TECH_STATUS + "," + UPDATED_POR_TECH_STATUS);

        // Get all the portabilityLogList where porTechStatus equals to UPDATED_POR_TECH_STATUS
        defaultPortabilityLogShouldNotBeFound("porTechStatus.in=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechStatus is not null
        defaultPortabilityLogShouldBeFound("porTechStatus.specified=true");

        // Get all the portabilityLogList where porTechStatus is null
        defaultPortabilityLogShouldNotBeFound("porTechStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechStatusContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechStatus contains DEFAULT_POR_TECH_STATUS
        defaultPortabilityLogShouldBeFound("porTechStatus.contains=" + DEFAULT_POR_TECH_STATUS);

        // Get all the portabilityLogList where porTechStatus contains UPDATED_POR_TECH_STATUS
        defaultPortabilityLogShouldNotBeFound("porTechStatus.contains=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechStatusNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechStatus does not contain DEFAULT_POR_TECH_STATUS
        defaultPortabilityLogShouldNotBeFound("porTechStatus.doesNotContain=" + DEFAULT_POR_TECH_STATUS);

        // Get all the portabilityLogList where porTechStatus does not contain UPDATED_POR_TECH_STATUS
        defaultPortabilityLogShouldBeFound("porTechStatus.doesNotContain=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechDeadlineIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechDeadline equals to DEFAULT_POR_TECH_DEADLINE
        defaultPortabilityLogShouldBeFound("porTechDeadline.equals=" + DEFAULT_POR_TECH_DEADLINE);

        // Get all the portabilityLogList where porTechDeadline equals to UPDATED_POR_TECH_DEADLINE
        defaultPortabilityLogShouldNotBeFound("porTechDeadline.equals=" + UPDATED_POR_TECH_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechDeadlineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechDeadline not equals to DEFAULT_POR_TECH_DEADLINE
        defaultPortabilityLogShouldNotBeFound("porTechDeadline.notEquals=" + DEFAULT_POR_TECH_DEADLINE);

        // Get all the portabilityLogList where porTechDeadline not equals to UPDATED_POR_TECH_DEADLINE
        defaultPortabilityLogShouldBeFound("porTechDeadline.notEquals=" + UPDATED_POR_TECH_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechDeadlineIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechDeadline in DEFAULT_POR_TECH_DEADLINE or UPDATED_POR_TECH_DEADLINE
        defaultPortabilityLogShouldBeFound("porTechDeadline.in=" + DEFAULT_POR_TECH_DEADLINE + "," + UPDATED_POR_TECH_DEADLINE);

        // Get all the portabilityLogList where porTechDeadline equals to UPDATED_POR_TECH_DEADLINE
        defaultPortabilityLogShouldNotBeFound("porTechDeadline.in=" + UPDATED_POR_TECH_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByPorTechDeadlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where porTechDeadline is not null
        defaultPortabilityLogShouldBeFound("porTechDeadline.specified=true");

        // Get all the portabilityLogList where porTechDeadline is null
        defaultPortabilityLogShouldNotBeFound("porTechDeadline.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRefPorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where refPorId equals to DEFAULT_REF_POR_ID
        defaultPortabilityLogShouldBeFound("refPorId.equals=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityLogList where refPorId equals to UPDATED_REF_POR_ID
        defaultPortabilityLogShouldNotBeFound("refPorId.equals=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRefPorIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where refPorId not equals to DEFAULT_REF_POR_ID
        defaultPortabilityLogShouldNotBeFound("refPorId.notEquals=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityLogList where refPorId not equals to UPDATED_REF_POR_ID
        defaultPortabilityLogShouldBeFound("refPorId.notEquals=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRefPorIdIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where refPorId in DEFAULT_REF_POR_ID or UPDATED_REF_POR_ID
        defaultPortabilityLogShouldBeFound("refPorId.in=" + DEFAULT_REF_POR_ID + "," + UPDATED_REF_POR_ID);

        // Get all the portabilityLogList where refPorId equals to UPDATED_REF_POR_ID
        defaultPortabilityLogShouldNotBeFound("refPorId.in=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRefPorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where refPorId is not null
        defaultPortabilityLogShouldBeFound("refPorId.specified=true");

        // Get all the portabilityLogList where refPorId is null
        defaultPortabilityLogShouldNotBeFound("refPorId.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRefPorIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where refPorId is greater than or equal to DEFAULT_REF_POR_ID
        defaultPortabilityLogShouldBeFound("refPorId.greaterThanOrEqual=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityLogList where refPorId is greater than or equal to UPDATED_REF_POR_ID
        defaultPortabilityLogShouldNotBeFound("refPorId.greaterThanOrEqual=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRefPorIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where refPorId is less than or equal to DEFAULT_REF_POR_ID
        defaultPortabilityLogShouldBeFound("refPorId.lessThanOrEqual=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityLogList where refPorId is less than or equal to SMALLER_REF_POR_ID
        defaultPortabilityLogShouldNotBeFound("refPorId.lessThanOrEqual=" + SMALLER_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRefPorIdIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where refPorId is less than DEFAULT_REF_POR_ID
        defaultPortabilityLogShouldNotBeFound("refPorId.lessThan=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityLogList where refPorId is less than UPDATED_REF_POR_ID
        defaultPortabilityLogShouldBeFound("refPorId.lessThan=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRefPorIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where refPorId is greater than DEFAULT_REF_POR_ID
        defaultPortabilityLogShouldNotBeFound("refPorId.greaterThan=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityLogList where refPorId is greater than SMALLER_REF_POR_ID
        defaultPortabilityLogShouldBeFound("refPorId.greaterThan=" + SMALLER_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByNeedManualRetryIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where needManualRetry equals to DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldBeFound("needManualRetry.equals=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityLogList where needManualRetry equals to UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldNotBeFound("needManualRetry.equals=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByNeedManualRetryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where needManualRetry not equals to DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldNotBeFound("needManualRetry.notEquals=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityLogList where needManualRetry not equals to UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldBeFound("needManualRetry.notEquals=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByNeedManualRetryIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where needManualRetry in DEFAULT_NEED_MANUAL_RETRY or UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldBeFound("needManualRetry.in=" + DEFAULT_NEED_MANUAL_RETRY + "," + UPDATED_NEED_MANUAL_RETRY);

        // Get all the portabilityLogList where needManualRetry equals to UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldNotBeFound("needManualRetry.in=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByNeedManualRetryIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where needManualRetry is not null
        defaultPortabilityLogShouldBeFound("needManualRetry.specified=true");

        // Get all the portabilityLogList where needManualRetry is null
        defaultPortabilityLogShouldNotBeFound("needManualRetry.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByNeedManualRetryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where needManualRetry is greater than or equal to DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldBeFound("needManualRetry.greaterThanOrEqual=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityLogList where needManualRetry is greater than or equal to UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldNotBeFound("needManualRetry.greaterThanOrEqual=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByNeedManualRetryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where needManualRetry is less than or equal to DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldBeFound("needManualRetry.lessThanOrEqual=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityLogList where needManualRetry is less than or equal to SMALLER_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldNotBeFound("needManualRetry.lessThanOrEqual=" + SMALLER_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByNeedManualRetryIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where needManualRetry is less than DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldNotBeFound("needManualRetry.lessThan=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityLogList where needManualRetry is less than UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldBeFound("needManualRetry.lessThan=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByNeedManualRetryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where needManualRetry is greater than DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldNotBeFound("needManualRetry.greaterThan=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityLogList where needManualRetry is greater than SMALLER_NEED_MANUAL_RETRY
        defaultPortabilityLogShouldBeFound("needManualRetry.greaterThan=" + SMALLER_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRetryCountIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where retryCount equals to DEFAULT_RETRY_COUNT
        defaultPortabilityLogShouldBeFound("retryCount.equals=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityLogList where retryCount equals to UPDATED_RETRY_COUNT
        defaultPortabilityLogShouldNotBeFound("retryCount.equals=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRetryCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where retryCount not equals to DEFAULT_RETRY_COUNT
        defaultPortabilityLogShouldNotBeFound("retryCount.notEquals=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityLogList where retryCount not equals to UPDATED_RETRY_COUNT
        defaultPortabilityLogShouldBeFound("retryCount.notEquals=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRetryCountIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where retryCount in DEFAULT_RETRY_COUNT or UPDATED_RETRY_COUNT
        defaultPortabilityLogShouldBeFound("retryCount.in=" + DEFAULT_RETRY_COUNT + "," + UPDATED_RETRY_COUNT);

        // Get all the portabilityLogList where retryCount equals to UPDATED_RETRY_COUNT
        defaultPortabilityLogShouldNotBeFound("retryCount.in=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRetryCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where retryCount is not null
        defaultPortabilityLogShouldBeFound("retryCount.specified=true");

        // Get all the portabilityLogList where retryCount is null
        defaultPortabilityLogShouldNotBeFound("retryCount.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRetryCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where retryCount is greater than or equal to DEFAULT_RETRY_COUNT
        defaultPortabilityLogShouldBeFound("retryCount.greaterThanOrEqual=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityLogList where retryCount is greater than or equal to UPDATED_RETRY_COUNT
        defaultPortabilityLogShouldNotBeFound("retryCount.greaterThanOrEqual=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRetryCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where retryCount is less than or equal to DEFAULT_RETRY_COUNT
        defaultPortabilityLogShouldBeFound("retryCount.lessThanOrEqual=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityLogList where retryCount is less than or equal to SMALLER_RETRY_COUNT
        defaultPortabilityLogShouldNotBeFound("retryCount.lessThanOrEqual=" + SMALLER_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRetryCountIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where retryCount is less than DEFAULT_RETRY_COUNT
        defaultPortabilityLogShouldNotBeFound("retryCount.lessThan=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityLogList where retryCount is less than UPDATED_RETRY_COUNT
        defaultPortabilityLogShouldBeFound("retryCount.lessThan=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRetryCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where retryCount is greater than DEFAULT_RETRY_COUNT
        defaultPortabilityLogShouldNotBeFound("retryCount.greaterThan=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityLogList where retryCount is greater than SMALLER_RETRY_COUNT
        defaultPortabilityLogShouldBeFound("retryCount.greaterThan=" + SMALLER_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByActionIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where action equals to DEFAULT_ACTION
        defaultPortabilityLogShouldBeFound("action.equals=" + DEFAULT_ACTION);

        // Get all the portabilityLogList where action equals to UPDATED_ACTION
        defaultPortabilityLogShouldNotBeFound("action.equals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByActionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where action not equals to DEFAULT_ACTION
        defaultPortabilityLogShouldNotBeFound("action.notEquals=" + DEFAULT_ACTION);

        // Get all the portabilityLogList where action not equals to UPDATED_ACTION
        defaultPortabilityLogShouldBeFound("action.notEquals=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByActionIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where action in DEFAULT_ACTION or UPDATED_ACTION
        defaultPortabilityLogShouldBeFound("action.in=" + DEFAULT_ACTION + "," + UPDATED_ACTION);

        // Get all the portabilityLogList where action equals to UPDATED_ACTION
        defaultPortabilityLogShouldNotBeFound("action.in=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByActionIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where action is not null
        defaultPortabilityLogShouldBeFound("action.specified=true");

        // Get all the portabilityLogList where action is null
        defaultPortabilityLogShouldNotBeFound("action.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByActionContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where action contains DEFAULT_ACTION
        defaultPortabilityLogShouldBeFound("action.contains=" + DEFAULT_ACTION);

        // Get all the portabilityLogList where action contains UPDATED_ACTION
        defaultPortabilityLogShouldNotBeFound("action.contains=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByActionNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where action does not contain DEFAULT_ACTION
        defaultPortabilityLogShouldNotBeFound("action.doesNotContain=" + DEFAULT_ACTION);

        // Get all the portabilityLogList where action does not contain UPDATED_ACTION
        defaultPortabilityLogShouldBeFound("action.doesNotContain=" + UPDATED_ACTION);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRequestIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where request equals to DEFAULT_REQUEST
        defaultPortabilityLogShouldBeFound("request.equals=" + DEFAULT_REQUEST);

        // Get all the portabilityLogList where request equals to UPDATED_REQUEST
        defaultPortabilityLogShouldNotBeFound("request.equals=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRequestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where request not equals to DEFAULT_REQUEST
        defaultPortabilityLogShouldNotBeFound("request.notEquals=" + DEFAULT_REQUEST);

        // Get all the portabilityLogList where request not equals to UPDATED_REQUEST
        defaultPortabilityLogShouldBeFound("request.notEquals=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRequestIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where request in DEFAULT_REQUEST or UPDATED_REQUEST
        defaultPortabilityLogShouldBeFound("request.in=" + DEFAULT_REQUEST + "," + UPDATED_REQUEST);

        // Get all the portabilityLogList where request equals to UPDATED_REQUEST
        defaultPortabilityLogShouldNotBeFound("request.in=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRequestIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where request is not null
        defaultPortabilityLogShouldBeFound("request.specified=true");

        // Get all the portabilityLogList where request is null
        defaultPortabilityLogShouldNotBeFound("request.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRequestContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where request contains DEFAULT_REQUEST
        defaultPortabilityLogShouldBeFound("request.contains=" + DEFAULT_REQUEST);

        // Get all the portabilityLogList where request contains UPDATED_REQUEST
        defaultPortabilityLogShouldNotBeFound("request.contains=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByRequestNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where request does not contain DEFAULT_REQUEST
        defaultPortabilityLogShouldNotBeFound("request.doesNotContain=" + DEFAULT_REQUEST);

        // Get all the portabilityLogList where request does not contain UPDATED_REQUEST
        defaultPortabilityLogShouldBeFound("request.doesNotContain=" + UPDATED_REQUEST);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByInsertTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where insertTimestamp equals to DEFAULT_INSERT_TIMESTAMP
        defaultPortabilityLogShouldBeFound("insertTimestamp.equals=" + DEFAULT_INSERT_TIMESTAMP);

        // Get all the portabilityLogList where insertTimestamp equals to UPDATED_INSERT_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("insertTimestamp.equals=" + UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByInsertTimestampIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where insertTimestamp not equals to DEFAULT_INSERT_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("insertTimestamp.notEquals=" + DEFAULT_INSERT_TIMESTAMP);

        // Get all the portabilityLogList where insertTimestamp not equals to UPDATED_INSERT_TIMESTAMP
        defaultPortabilityLogShouldBeFound("insertTimestamp.notEquals=" + UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByInsertTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where insertTimestamp in DEFAULT_INSERT_TIMESTAMP or UPDATED_INSERT_TIMESTAMP
        defaultPortabilityLogShouldBeFound("insertTimestamp.in=" + DEFAULT_INSERT_TIMESTAMP + "," + UPDATED_INSERT_TIMESTAMP);

        // Get all the portabilityLogList where insertTimestamp equals to UPDATED_INSERT_TIMESTAMP
        defaultPortabilityLogShouldNotBeFound("insertTimestamp.in=" + UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilityLogsByInsertTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        // Get all the portabilityLogList where insertTimestamp is not null
        defaultPortabilityLogShouldBeFound("insertTimestamp.specified=true");

        // Get all the portabilityLogList where insertTimestamp is null
        defaultPortabilityLogShouldNotBeFound("insertTimestamp.specified=false");
    }

    */
/**
 * Executes the search, and checks that the default entity is returned.
 *//*

    private void defaultPortabilityLogShouldBeFound(String filter) throws Exception {
        restPortabilityLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portabilityLogEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].porId").value(hasItem(DEFAULT_POR_ID.intValue())))
            .andExpect(jsonPath("$.[*].porRequestId").value(hasItem(DEFAULT_POR_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].porNumber").value(hasItem(DEFAULT_POR_NUMBER)))
            .andExpect(jsonPath("$.[*].porLegalTerm").value(hasItem(DEFAULT_POR_LEGAL_TERM)))
            .andExpect(jsonPath("$.[*].porOpr").value(hasItem(DEFAULT_POR_OPR)))
            .andExpect(jsonPath("$.[*].porAccType").value(hasItem(DEFAULT_POR_ACC_TYPE)))
            .andExpect(jsonPath("$.[*].porIdNumber").value(hasItem(DEFAULT_POR_ID_NUMBER)))
            .andExpect(jsonPath("$.[*].porContactNumber").value(hasItem(DEFAULT_POR_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].porStatus").value(hasItem(DEFAULT_POR_STATUS)))
            .andExpect(jsonPath("$.[*].porPortedDate").value(hasItem(DEFAULT_POR_PORTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].porRouting").value(hasItem(DEFAULT_POR_ROUTING)))
            .andExpect(jsonPath("$.[*].porType").value(hasItem(DEFAULT_POR_TYPE)))
            .andExpect(jsonPath("$.[*].porOpOrg").value(hasItem(DEFAULT_POR_OP_ORG)))
            .andExpect(jsonPath("$.[*].porRspCode").value(hasItem(DEFAULT_POR_RSP_CODE)))
            .andExpect(jsonPath("$.[*].porRspNote").value(hasItem(DEFAULT_POR_RSP_NOTE)))
            .andExpect(jsonPath("$.[*].porCancelNote").value(hasItem(DEFAULT_POR_CANCEL_NOTE)))
            .andExpect(jsonPath("$.[*].porMnpid").value(hasItem(DEFAULT_POR_MNPID)))
            .andExpect(jsonPath("$.[*].portationDate").value(hasItem(DEFAULT_PORTATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].portaCode").value(hasItem(DEFAULT_PORTA_CODE)))
            .andExpect(jsonPath("$.[*].mvno").value(hasItem(DEFAULT_MVNO)))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].porErrCode").value(hasItem(DEFAULT_POR_ERR_CODE)))
            .andExpect(jsonPath("$.[*].porErrMessage").value(hasItem(DEFAULT_POR_ERR_MESSAGE)))
            .andExpect(jsonPath("$.[*].porOpd").value(hasItem(DEFAULT_POR_OPD)))
            .andExpect(jsonPath("$.[*].porNumType").value(hasItem(DEFAULT_POR_NUM_TYPE)))
            .andExpect(jsonPath("$.[*].porNote").value(hasItem(DEFAULT_POR_NOTE)))
            .andExpect(jsonPath("$.[*].porDeadline").value(hasItem(DEFAULT_POR_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].porResponseTimestamp").value(hasItem(DEFAULT_POR_RESPONSE_TIMESTAMP.intValue())))
            .andExpect(jsonPath("$.[*].porEligible").value(hasItem(DEFAULT_POR_ELIGIBLE)))
            .andExpect(jsonPath("$.[*].porBillingOk").value(hasItem(DEFAULT_POR_BILLING_OK)))
            .andExpect(jsonPath("$.[*].intermediaryActionState").value(hasItem(DEFAULT_INTERMEDIARY_ACTION_STATE)))
            .andExpect(jsonPath("$.[*].porCrDate").value(hasItem(DEFAULT_POR_CR_DATE.toString())))
            .andExpect(jsonPath("$.[*].porUpdDate").value(hasItem(DEFAULT_POR_UPD_DATE.toString())))
            .andExpect(jsonPath("$.[*].porTechStatus").value(hasItem(DEFAULT_POR_TECH_STATUS)))
            .andExpect(jsonPath("$.[*].porTechDeadline").value(hasItem(DEFAULT_POR_TECH_DEADLINE.toString())))
            .andExpect(jsonPath("$.[*].refPorId").value(hasItem(DEFAULT_REF_POR_ID.intValue())))
            .andExpect(jsonPath("$.[*].needManualRetry").value(hasItem(DEFAULT_NEED_MANUAL_RETRY)))
            .andExpect(jsonPath("$.[*].retryCount").value(hasItem(DEFAULT_RETRY_COUNT)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].request").value(hasItem(DEFAULT_REQUEST)))
            .andExpect(jsonPath("$.[*].insertTimestamp").value(hasItem(DEFAULT_INSERT_TIMESTAMP.toString())));

        // Check, that the count call also returns 1
        restPortabilityLogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    */
/**
 * Executes the search, and checks that the default entity is not returned.
 *//*

    private void defaultPortabilityLogShouldNotBeFound(String filter) throws Exception {
        restPortabilityLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPortabilityLogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPortabilityLog() throws Exception {
        // Get the portabilityLog
        restPortabilityLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPortabilityLog() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        int databaseSizeBeforeUpdate = portabilityLogRepository.findAll().size();

        // Update the portabilityLog
        PortabilityLogEntity updatedPortabilityLogEntity = portabilityLogRepository.findById(portabilityLogEntity.getId()).get();
        // Disconnect from session so that the updates on updatedPortabilityLogEntity are not directly saved in db
        em.detach(updatedPortabilityLogEntity);
        updatedPortabilityLogEntity
            .porId(UPDATED_POR_ID)
            .porRequestId(UPDATED_POR_REQUEST_ID)
            .porNumber(UPDATED_POR_NUMBER)
            .porLegalTerm(UPDATED_POR_LEGAL_TERM)
            .porOpr(UPDATED_POR_OPR)
            .porAccType(UPDATED_POR_ACC_TYPE)
            .porIdNumber(UPDATED_POR_ID_NUMBER)
            .porContactNumber(UPDATED_POR_CONTACT_NUMBER)
            .porStatus(UPDATED_POR_STATUS)
            .porPortedDate(UPDATED_POR_PORTED_DATE)
            .porRouting(UPDATED_POR_ROUTING)
            .porType(UPDATED_POR_TYPE)
            .porOpOrg(UPDATED_POR_OP_ORG)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .porRspNote(UPDATED_POR_RSP_NOTE)
            .porCancelNote(UPDATED_POR_CANCEL_NOTE)
            .porMnpid(UPDATED_POR_MNPID)
            .portationDate(UPDATED_PORTATION_DATE)
            .portaCode(UPDATED_PORTA_CODE)
            .mvno(UPDATED_MVNO)
            .context(UPDATED_CONTEXT)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porErrMessage(UPDATED_POR_ERR_MESSAGE)
            .porOpd(UPDATED_POR_OPD)
            .porNumType(UPDATED_POR_NUM_TYPE)
            .porNote(UPDATED_POR_NOTE)
            .porDeadline(UPDATED_POR_DEADLINE)
            .porResponseTimestamp(UPDATED_POR_RESPONSE_TIMESTAMP)
            .porEligible(UPDATED_POR_ELIGIBLE)
            .porBillingOk(UPDATED_POR_BILLING_OK)
            .intermediaryActionState(UPDATED_INTERMEDIARY_ACTION_STATE)
            .porCrDate(UPDATED_POR_CR_DATE)
            .porUpdDate(UPDATED_POR_UPD_DATE)
            .porTechStatus(UPDATED_POR_TECH_STATUS)
            .porTechDeadline(UPDATED_POR_TECH_DEADLINE)
            .refPorId(UPDATED_REF_POR_ID)
            .needManualRetry(UPDATED_NEED_MANUAL_RETRY)
            .retryCount(UPDATED_RETRY_COUNT)
            .action(UPDATED_ACTION)
            .request(UPDATED_REQUEST)
            .insertTimestamp(UPDATED_INSERT_TIMESTAMP);
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(updatedPortabilityLogEntity);

        restPortabilityLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portabilityLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeUpdate);
        PortabilityLogEntity testPortabilityLog = portabilityLogList.get(portabilityLogList.size() - 1);
        assertThat(testPortabilityLog.getPorId()).isEqualTo(UPDATED_POR_ID);
        assertThat(testPortabilityLog.getPorRequestId()).isEqualTo(UPDATED_POR_REQUEST_ID);
        assertThat(testPortabilityLog.getPorNumber()).isEqualTo(UPDATED_POR_NUMBER);
        assertThat(testPortabilityLog.getPorLegalTerm()).isEqualTo(UPDATED_POR_LEGAL_TERM);
        assertThat(testPortabilityLog.getPorOpr()).isEqualTo(UPDATED_POR_OPR);
        assertThat(testPortabilityLog.getPorAccType()).isEqualTo(UPDATED_POR_ACC_TYPE);
        assertThat(testPortabilityLog.getPorIdNumber()).isEqualTo(UPDATED_POR_ID_NUMBER);
        assertThat(testPortabilityLog.getPorContactNumber()).isEqualTo(UPDATED_POR_CONTACT_NUMBER);
        assertThat(testPortabilityLog.getPorStatus()).isEqualTo(UPDATED_POR_STATUS);
        assertThat(testPortabilityLog.getPorPortedDate()).isEqualTo(UPDATED_POR_PORTED_DATE);
        assertThat(testPortabilityLog.getPorRouting()).isEqualTo(UPDATED_POR_ROUTING);
        assertThat(testPortabilityLog.getPorType()).isEqualTo(UPDATED_POR_TYPE);
        assertThat(testPortabilityLog.getPorOpOrg()).isEqualTo(UPDATED_POR_OP_ORG);
        assertThat(testPortabilityLog.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testPortabilityLog.getPorRspNote()).isEqualTo(UPDATED_POR_RSP_NOTE);
        assertThat(testPortabilityLog.getPorCancelNote()).isEqualTo(UPDATED_POR_CANCEL_NOTE);
        assertThat(testPortabilityLog.getPorMnpid()).isEqualTo(UPDATED_POR_MNPID);
        assertThat(testPortabilityLog.getPortationDate()).isEqualTo(UPDATED_PORTATION_DATE);
        assertThat(testPortabilityLog.getPortaCode()).isEqualTo(UPDATED_PORTA_CODE);
        assertThat(testPortabilityLog.getMvno()).isEqualTo(UPDATED_MVNO);
        assertThat(testPortabilityLog.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testPortabilityLog.getPorErrCode()).isEqualTo(UPDATED_POR_ERR_CODE);
        assertThat(testPortabilityLog.getPorErrMessage()).isEqualTo(UPDATED_POR_ERR_MESSAGE);
        assertThat(testPortabilityLog.getPorOpd()).isEqualTo(UPDATED_POR_OPD);
        assertThat(testPortabilityLog.getPorNumType()).isEqualTo(UPDATED_POR_NUM_TYPE);
        assertThat(testPortabilityLog.getPorNote()).isEqualTo(UPDATED_POR_NOTE);
        assertThat(testPortabilityLog.getPorDeadline()).isEqualTo(UPDATED_POR_DEADLINE);
        assertThat(testPortabilityLog.getPorResponseTimestamp()).isEqualTo(UPDATED_POR_RESPONSE_TIMESTAMP);
        assertThat(testPortabilityLog.getPorEligible()).isEqualTo(UPDATED_POR_ELIGIBLE);
        assertThat(testPortabilityLog.getPorBillingOk()).isEqualTo(UPDATED_POR_BILLING_OK);
        assertThat(testPortabilityLog.getIntermediaryActionState()).isEqualTo(UPDATED_INTERMEDIARY_ACTION_STATE);
        assertThat(testPortabilityLog.getPorCrDate()).isEqualTo(UPDATED_POR_CR_DATE);
        assertThat(testPortabilityLog.getPorUpdDate()).isEqualTo(UPDATED_POR_UPD_DATE);
        assertThat(testPortabilityLog.getPorTechStatus()).isEqualTo(UPDATED_POR_TECH_STATUS);
        assertThat(testPortabilityLog.getPorTechDeadline()).isEqualTo(UPDATED_POR_TECH_DEADLINE);
        assertThat(testPortabilityLog.getRefPorId()).isEqualTo(UPDATED_REF_POR_ID);
        assertThat(testPortabilityLog.getNeedManualRetry()).isEqualTo(UPDATED_NEED_MANUAL_RETRY);
        assertThat(testPortabilityLog.getRetryCount()).isEqualTo(UPDATED_RETRY_COUNT);
        assertThat(testPortabilityLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testPortabilityLog.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testPortabilityLog.getInsertTimestamp()).isEqualTo(UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingPortabilityLog() throws Exception {
        int databaseSizeBeforeUpdate = portabilityLogRepository.findAll().size();
        portabilityLogEntity.setId(count.incrementAndGet());

        // Create the PortabilityLog
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortabilityLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portabilityLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPortabilityLog() throws Exception {
        int databaseSizeBeforeUpdate = portabilityLogRepository.findAll().size();
        portabilityLogEntity.setId(count.incrementAndGet());

        // Create the PortabilityLog
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortabilityLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPortabilityLog() throws Exception {
        int databaseSizeBeforeUpdate = portabilityLogRepository.findAll().size();
        portabilityLogEntity.setId(count.incrementAndGet());

        // Create the PortabilityLog
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortabilityLogMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePortabilityLogWithPatch() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        int databaseSizeBeforeUpdate = portabilityLogRepository.findAll().size();

        // Update the portabilityLog using partial update
        PortabilityLogEntity partialUpdatedPortabilityLogEntity = new PortabilityLogEntity();
        partialUpdatedPortabilityLogEntity.setId(portabilityLogEntity.getId());

        partialUpdatedPortabilityLogEntity
            .porRequestId(UPDATED_POR_REQUEST_ID)
            .porNumber(UPDATED_POR_NUMBER)
            .porOpr(UPDATED_POR_OPR)
            .porAccType(UPDATED_POR_ACC_TYPE)
            .porIdNumber(UPDATED_POR_ID_NUMBER)
            .porRouting(UPDATED_POR_ROUTING)
            .porOpOrg(UPDATED_POR_OP_ORG)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .porCancelNote(UPDATED_POR_CANCEL_NOTE)
            .porMnpid(UPDATED_POR_MNPID)
            .portaCode(UPDATED_PORTA_CODE)
            .porErrMessage(UPDATED_POR_ERR_MESSAGE)
            .porOpd(UPDATED_POR_OPD)
            .porResponseTimestamp(UPDATED_POR_RESPONSE_TIMESTAMP)
            .porBillingOk(UPDATED_POR_BILLING_OK)
            .intermediaryActionState(UPDATED_INTERMEDIARY_ACTION_STATE)
            .porCrDate(UPDATED_POR_CR_DATE)
            .porUpdDate(UPDATED_POR_UPD_DATE)
            .retryCount(UPDATED_RETRY_COUNT)
            .request(UPDATED_REQUEST)
            .insertTimestamp(UPDATED_INSERT_TIMESTAMP);

        restPortabilityLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortabilityLogEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortabilityLogEntity))
            )
            .andExpect(status().isOk());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeUpdate);
        PortabilityLogEntity testPortabilityLog = portabilityLogList.get(portabilityLogList.size() - 1);
        assertThat(testPortabilityLog.getPorId()).isEqualTo(DEFAULT_POR_ID);
        assertThat(testPortabilityLog.getPorRequestId()).isEqualTo(UPDATED_POR_REQUEST_ID);
        assertThat(testPortabilityLog.getPorNumber()).isEqualTo(UPDATED_POR_NUMBER);
        assertThat(testPortabilityLog.getPorLegalTerm()).isEqualTo(DEFAULT_POR_LEGAL_TERM);
        assertThat(testPortabilityLog.getPorOpr()).isEqualTo(UPDATED_POR_OPR);
        assertThat(testPortabilityLog.getPorAccType()).isEqualTo(UPDATED_POR_ACC_TYPE);
        assertThat(testPortabilityLog.getPorIdNumber()).isEqualTo(UPDATED_POR_ID_NUMBER);
        assertThat(testPortabilityLog.getPorContactNumber()).isEqualTo(DEFAULT_POR_CONTACT_NUMBER);
        assertThat(testPortabilityLog.getPorStatus()).isEqualTo(DEFAULT_POR_STATUS);
        assertThat(testPortabilityLog.getPorPortedDate()).isEqualTo(DEFAULT_POR_PORTED_DATE);
        assertThat(testPortabilityLog.getPorRouting()).isEqualTo(UPDATED_POR_ROUTING);
        assertThat(testPortabilityLog.getPorType()).isEqualTo(DEFAULT_POR_TYPE);
        assertThat(testPortabilityLog.getPorOpOrg()).isEqualTo(UPDATED_POR_OP_ORG);
        assertThat(testPortabilityLog.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testPortabilityLog.getPorRspNote()).isEqualTo(DEFAULT_POR_RSP_NOTE);
        assertThat(testPortabilityLog.getPorCancelNote()).isEqualTo(UPDATED_POR_CANCEL_NOTE);
        assertThat(testPortabilityLog.getPorMnpid()).isEqualTo(UPDATED_POR_MNPID);
        assertThat(testPortabilityLog.getPortationDate()).isEqualTo(DEFAULT_PORTATION_DATE);
        assertThat(testPortabilityLog.getPortaCode()).isEqualTo(UPDATED_PORTA_CODE);
        assertThat(testPortabilityLog.getMvno()).isEqualTo(DEFAULT_MVNO);
        assertThat(testPortabilityLog.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testPortabilityLog.getPorErrCode()).isEqualTo(DEFAULT_POR_ERR_CODE);
        assertThat(testPortabilityLog.getPorErrMessage()).isEqualTo(UPDATED_POR_ERR_MESSAGE);
        assertThat(testPortabilityLog.getPorOpd()).isEqualTo(UPDATED_POR_OPD);
        assertThat(testPortabilityLog.getPorNumType()).isEqualTo(DEFAULT_POR_NUM_TYPE);
        assertThat(testPortabilityLog.getPorNote()).isEqualTo(DEFAULT_POR_NOTE);
        assertThat(testPortabilityLog.getPorDeadline()).isEqualTo(DEFAULT_POR_DEADLINE);
        assertThat(testPortabilityLog.getPorResponseTimestamp()).isEqualTo(UPDATED_POR_RESPONSE_TIMESTAMP);
        assertThat(testPortabilityLog.getPorEligible()).isEqualTo(DEFAULT_POR_ELIGIBLE);
        assertThat(testPortabilityLog.getPorBillingOk()).isEqualTo(UPDATED_POR_BILLING_OK);
        assertThat(testPortabilityLog.getIntermediaryActionState()).isEqualTo(UPDATED_INTERMEDIARY_ACTION_STATE);
        assertThat(testPortabilityLog.getPorCrDate()).isEqualTo(UPDATED_POR_CR_DATE);
        assertThat(testPortabilityLog.getPorUpdDate()).isEqualTo(UPDATED_POR_UPD_DATE);
        assertThat(testPortabilityLog.getPorTechStatus()).isEqualTo(DEFAULT_POR_TECH_STATUS);
        assertThat(testPortabilityLog.getPorTechDeadline()).isEqualTo(DEFAULT_POR_TECH_DEADLINE);
        assertThat(testPortabilityLog.getRefPorId()).isEqualTo(DEFAULT_REF_POR_ID);
        assertThat(testPortabilityLog.getNeedManualRetry()).isEqualTo(DEFAULT_NEED_MANUAL_RETRY);
        assertThat(testPortabilityLog.getRetryCount()).isEqualTo(UPDATED_RETRY_COUNT);
        assertThat(testPortabilityLog.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testPortabilityLog.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testPortabilityLog.getInsertTimestamp()).isEqualTo(UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdatePortabilityLogWithPatch() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        int databaseSizeBeforeUpdate = portabilityLogRepository.findAll().size();

        // Update the portabilityLog using partial update
        PortabilityLogEntity partialUpdatedPortabilityLogEntity = new PortabilityLogEntity();
        partialUpdatedPortabilityLogEntity.setId(portabilityLogEntity.getId());

        partialUpdatedPortabilityLogEntity
            .porId(UPDATED_POR_ID)
            .porRequestId(UPDATED_POR_REQUEST_ID)
            .porNumber(UPDATED_POR_NUMBER)
            .porLegalTerm(UPDATED_POR_LEGAL_TERM)
            .porOpr(UPDATED_POR_OPR)
            .porAccType(UPDATED_POR_ACC_TYPE)
            .porIdNumber(UPDATED_POR_ID_NUMBER)
            .porContactNumber(UPDATED_POR_CONTACT_NUMBER)
            .porStatus(UPDATED_POR_STATUS)
            .porPortedDate(UPDATED_POR_PORTED_DATE)
            .porRouting(UPDATED_POR_ROUTING)
            .porType(UPDATED_POR_TYPE)
            .porOpOrg(UPDATED_POR_OP_ORG)
            .porRspCode(UPDATED_POR_RSP_CODE)
            .porRspNote(UPDATED_POR_RSP_NOTE)
            .porCancelNote(UPDATED_POR_CANCEL_NOTE)
            .porMnpid(UPDATED_POR_MNPID)
            .portationDate(UPDATED_PORTATION_DATE)
            .portaCode(UPDATED_PORTA_CODE)
            .mvno(UPDATED_MVNO)
            .context(UPDATED_CONTEXT)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porErrMessage(UPDATED_POR_ERR_MESSAGE)
            .porOpd(UPDATED_POR_OPD)
            .porNumType(UPDATED_POR_NUM_TYPE)
            .porNote(UPDATED_POR_NOTE)
            .porDeadline(UPDATED_POR_DEADLINE)
            .porResponseTimestamp(UPDATED_POR_RESPONSE_TIMESTAMP)
            .porEligible(UPDATED_POR_ELIGIBLE)
            .porBillingOk(UPDATED_POR_BILLING_OK)
            .intermediaryActionState(UPDATED_INTERMEDIARY_ACTION_STATE)
            .porCrDate(UPDATED_POR_CR_DATE)
            .porUpdDate(UPDATED_POR_UPD_DATE)
            .porTechStatus(UPDATED_POR_TECH_STATUS)
            .porTechDeadline(UPDATED_POR_TECH_DEADLINE)
            .refPorId(UPDATED_REF_POR_ID)
            .needManualRetry(UPDATED_NEED_MANUAL_RETRY)
            .retryCount(UPDATED_RETRY_COUNT)
            .action(UPDATED_ACTION)
            .request(UPDATED_REQUEST)
            .insertTimestamp(UPDATED_INSERT_TIMESTAMP);

        restPortabilityLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortabilityLogEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortabilityLogEntity))
            )
            .andExpect(status().isOk());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeUpdate);
        PortabilityLogEntity testPortabilityLog = portabilityLogList.get(portabilityLogList.size() - 1);
        assertThat(testPortabilityLog.getPorId()).isEqualTo(UPDATED_POR_ID);
        assertThat(testPortabilityLog.getPorRequestId()).isEqualTo(UPDATED_POR_REQUEST_ID);
        assertThat(testPortabilityLog.getPorNumber()).isEqualTo(UPDATED_POR_NUMBER);
        assertThat(testPortabilityLog.getPorLegalTerm()).isEqualTo(UPDATED_POR_LEGAL_TERM);
        assertThat(testPortabilityLog.getPorOpr()).isEqualTo(UPDATED_POR_OPR);
        assertThat(testPortabilityLog.getPorAccType()).isEqualTo(UPDATED_POR_ACC_TYPE);
        assertThat(testPortabilityLog.getPorIdNumber()).isEqualTo(UPDATED_POR_ID_NUMBER);
        assertThat(testPortabilityLog.getPorContactNumber()).isEqualTo(UPDATED_POR_CONTACT_NUMBER);
        assertThat(testPortabilityLog.getPorStatus()).isEqualTo(UPDATED_POR_STATUS);
        assertThat(testPortabilityLog.getPorPortedDate()).isEqualTo(UPDATED_POR_PORTED_DATE);
        assertThat(testPortabilityLog.getPorRouting()).isEqualTo(UPDATED_POR_ROUTING);
        assertThat(testPortabilityLog.getPorType()).isEqualTo(UPDATED_POR_TYPE);
        assertThat(testPortabilityLog.getPorOpOrg()).isEqualTo(UPDATED_POR_OP_ORG);
        assertThat(testPortabilityLog.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testPortabilityLog.getPorRspNote()).isEqualTo(UPDATED_POR_RSP_NOTE);
        assertThat(testPortabilityLog.getPorCancelNote()).isEqualTo(UPDATED_POR_CANCEL_NOTE);
        assertThat(testPortabilityLog.getPorMnpid()).isEqualTo(UPDATED_POR_MNPID);
        assertThat(testPortabilityLog.getPortationDate()).isEqualTo(UPDATED_PORTATION_DATE);
        assertThat(testPortabilityLog.getPortaCode()).isEqualTo(UPDATED_PORTA_CODE);
        assertThat(testPortabilityLog.getMvno()).isEqualTo(UPDATED_MVNO);
        assertThat(testPortabilityLog.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testPortabilityLog.getPorErrCode()).isEqualTo(UPDATED_POR_ERR_CODE);
        assertThat(testPortabilityLog.getPorErrMessage()).isEqualTo(UPDATED_POR_ERR_MESSAGE);
        assertThat(testPortabilityLog.getPorOpd()).isEqualTo(UPDATED_POR_OPD);
        assertThat(testPortabilityLog.getPorNumType()).isEqualTo(UPDATED_POR_NUM_TYPE);
        assertThat(testPortabilityLog.getPorNote()).isEqualTo(UPDATED_POR_NOTE);
        assertThat(testPortabilityLog.getPorDeadline()).isEqualTo(UPDATED_POR_DEADLINE);
        assertThat(testPortabilityLog.getPorResponseTimestamp()).isEqualTo(UPDATED_POR_RESPONSE_TIMESTAMP);
        assertThat(testPortabilityLog.getPorEligible()).isEqualTo(UPDATED_POR_ELIGIBLE);
        assertThat(testPortabilityLog.getPorBillingOk()).isEqualTo(UPDATED_POR_BILLING_OK);
        assertThat(testPortabilityLog.getIntermediaryActionState()).isEqualTo(UPDATED_INTERMEDIARY_ACTION_STATE);
        assertThat(testPortabilityLog.getPorCrDate()).isEqualTo(UPDATED_POR_CR_DATE);
        assertThat(testPortabilityLog.getPorUpdDate()).isEqualTo(UPDATED_POR_UPD_DATE);
        assertThat(testPortabilityLog.getPorTechStatus()).isEqualTo(UPDATED_POR_TECH_STATUS);
        assertThat(testPortabilityLog.getPorTechDeadline()).isEqualTo(UPDATED_POR_TECH_DEADLINE);
        assertThat(testPortabilityLog.getRefPorId()).isEqualTo(UPDATED_REF_POR_ID);
        assertThat(testPortabilityLog.getNeedManualRetry()).isEqualTo(UPDATED_NEED_MANUAL_RETRY);
        assertThat(testPortabilityLog.getRetryCount()).isEqualTo(UPDATED_RETRY_COUNT);
        assertThat(testPortabilityLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testPortabilityLog.getRequest()).isEqualTo(UPDATED_REQUEST);
        assertThat(testPortabilityLog.getInsertTimestamp()).isEqualTo(UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingPortabilityLog() throws Exception {
        int databaseSizeBeforeUpdate = portabilityLogRepository.findAll().size();
        portabilityLogEntity.setId(count.incrementAndGet());

        // Create the PortabilityLog
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortabilityLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, portabilityLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPortabilityLog() throws Exception {
        int databaseSizeBeforeUpdate = portabilityLogRepository.findAll().size();
        portabilityLogEntity.setId(count.incrementAndGet());

        // Create the PortabilityLog
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortabilityLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPortabilityLog() throws Exception {
        int databaseSizeBeforeUpdate = portabilityLogRepository.findAll().size();
        portabilityLogEntity.setId(count.incrementAndGet());

        // Create the PortabilityLog
        PortabilityLogDTO portabilityLogDTO = portabilityLogMapper.toDto(portabilityLogEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortabilityLogMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portabilityLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PortabilityLog in the database
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePortabilityLog() throws Exception {
        // Initialize the database
        portabilityLogRepository.saveAndFlush(portabilityLogEntity);

        int databaseSizeBeforeDelete = portabilityLogRepository.findAll().size();

        // Delete the portabilityLog
        restPortabilityLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, portabilityLogEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PortabilityLogEntity> portabilityLogList = portabilityLogRepository.findAll();
        assertThat(portabilityLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/
