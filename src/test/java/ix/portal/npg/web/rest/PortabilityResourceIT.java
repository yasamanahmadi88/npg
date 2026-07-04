package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.PortabilityEntity;
import ix.portal.npg.repository.PortabilityRepository;
import ix.portal.npg.service.criteria.PortabilityCriteria;
import ix.portal.npg.service.dto.PortabilityDTO;
import ix.portal.npg.service.mapper.PortabilityMapper;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
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

/**
 * Integration tests for the {@link PortabilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "portability" })
class PortabilityResourceIT {


    private static final DateTimeFormatter TEST_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private static String jsonDate(LocalDateTime value) {
        return value.format(TEST_DATE_TIME_FORMATTER);
    }

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

    private static final LocalDateTime DEFAULT_POR_PORTED_DATE = LocalDateTime.of(2020, 1, 1, 10, 0, 0);
    private static final LocalDateTime UPDATED_POR_PORTED_DATE = LocalDateTime.of(2020, 1, 2, 10, 0, 0);

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

    private static final LocalDateTime DEFAULT_PORTATION_DATE = LocalDateTime.of(2020, 1, 1, 10, 0, 0);
    private static final LocalDateTime UPDATED_PORTATION_DATE = LocalDateTime.of(2020, 1, 2, 10, 0, 0);

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

    private static final LocalDateTime DEFAULT_POR_DEADLINE = LocalDateTime.of(2020, 1, 1, 10, 0, 0);
    private static final LocalDateTime UPDATED_POR_DEADLINE = LocalDateTime.of(2020, 1, 2, 10, 0, 0);

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

    private static final LocalDateTime DEFAULT_POR_CR_DATE = LocalDateTime.of(2020, 1, 1, 10, 0, 0);
    private static final LocalDateTime UPDATED_POR_CR_DATE = LocalDateTime.of(2020, 1, 2, 10, 0, 0);

    private static final LocalDateTime DEFAULT_POR_UPD_DATE = LocalDateTime.of(2020, 1, 1, 10, 0, 0);
    private static final LocalDateTime UPDATED_POR_UPD_DATE = LocalDateTime.of(2020, 1, 2, 10, 0, 0);

    private static final String DEFAULT_POR_TECH_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_POR_TECH_STATUS = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_POR_TECH_DEADLINE = LocalDateTime.of(2020, 1, 1, 10, 0, 0);
    private static final LocalDateTime UPDATED_POR_TECH_DEADLINE = LocalDateTime.of(2020, 1, 2, 10, 0, 0);

    private static final Integer DEFAULT_NEED_MANUAL_RETRY = 1;
    private static final Integer UPDATED_NEED_MANUAL_RETRY = 2;
    private static final Integer SMALLER_NEED_MANUAL_RETRY = 1 - 1;

    private static final Long DEFAULT_REF_POR_ID = 1L;
    private static final Long UPDATED_REF_POR_ID = 2L;
    private static final Long SMALLER_REF_POR_ID = 1L - 1L;

    private static final Integer DEFAULT_RETRY_COUNT = 1;
    private static final Integer UPDATED_RETRY_COUNT = 2;
    private static final Integer SMALLER_RETRY_COUNT = 1 - 1;

    private static final String ENTITY_API_URL = "/api/portabilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PortabilityRepository portabilityRepository;

    @Autowired
    private PortabilityMapper portabilityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPortabilityMockMvc;

    private PortabilityEntity portabilityEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PortabilityEntity createEntity(EntityManager em) {
        PortabilityEntity portabilityEntity = new PortabilityEntity()
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
            .needManualRetry(DEFAULT_NEED_MANUAL_RETRY)
            .refPorId(DEFAULT_REF_POR_ID)
            .retryCount(DEFAULT_RETRY_COUNT);
        return portabilityEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PortabilityEntity createUpdatedEntity(EntityManager em) {
        PortabilityEntity portabilityEntity = new PortabilityEntity()
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
            .needManualRetry(UPDATED_NEED_MANUAL_RETRY)
            .refPorId(UPDATED_REF_POR_ID)
            .retryCount(UPDATED_RETRY_COUNT);
        return portabilityEntity;
    }


    private void deleteExistingPortabilityTestRows() {
        em.createNativeQuery(
            """
            delete from tbl_portability_entity
            where por_request_id in ('AAAAAAAAAA', 'BBBBBBBBBB')
            """
        ).executeUpdate();

        em.flush();
        em.clear();
    }
    @BeforeEach
    public void initTest() {

        deleteExistingPortabilityTestRows();
        portabilityEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createPortability() throws Exception {
        int databaseSizeBeforeCreate = portabilityRepository.findAll().size();
        // Create the Portability
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(portabilityEntity);
        restPortabilityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeCreate + 1);
        PortabilityEntity testPortability = portabilityList.get(portabilityList.size() - 1);
        assertThat(testPortability.getPorRequestId()).isEqualTo(DEFAULT_POR_REQUEST_ID);
        assertThat(testPortability.getPorNumber()).isEqualTo(DEFAULT_POR_NUMBER);
        assertThat(testPortability.getPorLegalTerm()).isEqualTo(DEFAULT_POR_LEGAL_TERM);
        assertThat(testPortability.getPorOpr()).isEqualTo(DEFAULT_POR_OPR);
        assertThat(testPortability.getPorAccType()).isEqualTo(DEFAULT_POR_ACC_TYPE);
        assertThat(testPortability.getPorIdNumber()).isEqualTo(DEFAULT_POR_ID_NUMBER);
        assertThat(testPortability.getPorContactNumber()).isEqualTo(DEFAULT_POR_CONTACT_NUMBER);
        assertThat(testPortability.getPorStatus()).isEqualTo(DEFAULT_POR_STATUS);
        assertThat(testPortability.getPorPortedDate()).isEqualTo(DEFAULT_POR_PORTED_DATE);
        assertThat(testPortability.getPorRouting()).isEqualTo(DEFAULT_POR_ROUTING);
        assertThat(testPortability.getPorType()).isEqualTo(DEFAULT_POR_TYPE);
        assertThat(testPortability.getPorOpOrg()).isEqualTo(DEFAULT_POR_OP_ORG);
        assertThat(testPortability.getPorRspCode()).isEqualTo(DEFAULT_POR_RSP_CODE);
        assertThat(testPortability.getPorRspNote()).isEqualTo(DEFAULT_POR_RSP_NOTE);
        assertThat(testPortability.getPorCancelNote()).isEqualTo(DEFAULT_POR_CANCEL_NOTE);
        assertThat(testPortability.getPorMnpid()).isEqualTo(DEFAULT_POR_MNPID);
        assertThat(testPortability.getPortationDate()).isEqualTo(DEFAULT_PORTATION_DATE);
        assertThat(testPortability.getPortaCode()).isEqualTo(DEFAULT_PORTA_CODE);
        assertThat(testPortability.getMvno()).isEqualTo(DEFAULT_MVNO);
        assertThat(testPortability.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testPortability.getPorErrCode()).isEqualTo(DEFAULT_POR_ERR_CODE);
        assertThat(testPortability.getPorErrMessage()).isEqualTo(DEFAULT_POR_ERR_MESSAGE);
        assertThat(testPortability.getPorOpd()).isEqualTo(DEFAULT_POR_OPD);
        assertThat(testPortability.getPorNumType()).isEqualTo(DEFAULT_POR_NUM_TYPE);
        assertThat(testPortability.getPorNote()).isEqualTo(DEFAULT_POR_NOTE);
        assertThat(testPortability.getPorDeadline()).isEqualTo(DEFAULT_POR_DEADLINE);
        assertThat(testPortability.getPorResponseTimestamp()).isEqualTo(DEFAULT_POR_RESPONSE_TIMESTAMP);
        assertThat(testPortability.getPorEligible()).isEqualTo(DEFAULT_POR_ELIGIBLE);
        assertThat(testPortability.getPorBillingOk()).isEqualTo(DEFAULT_POR_BILLING_OK);
        assertThat(testPortability.getIntermediaryActionState()).isEqualTo(DEFAULT_INTERMEDIARY_ACTION_STATE);
        assertThat(testPortability.getPorCrDate()).isEqualTo(DEFAULT_POR_CR_DATE);
        assertThat(testPortability.getPorUpdDate()).isEqualTo(DEFAULT_POR_UPD_DATE);
        assertThat(testPortability.getPorTechStatus()).isEqualTo(DEFAULT_POR_TECH_STATUS);
        assertThat(testPortability.getPorTechDeadline()).isEqualTo(DEFAULT_POR_TECH_DEADLINE);
        assertThat(testPortability.getNeedManualRetry()).isEqualTo(DEFAULT_NEED_MANUAL_RETRY);
        assertThat(testPortability.getRefPorId()).isEqualTo(DEFAULT_REF_POR_ID);
        assertThat(testPortability.getRetryCount()).isEqualTo(DEFAULT_RETRY_COUNT);
    }

    @Test
    @Transactional
    void createPortabilityWithExistingId() throws Exception {
        // Create the Portability with an existing ID
        portabilityEntity.setId(1L);
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(portabilityEntity);

        int databaseSizeBeforeCreate = portabilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortabilityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRetryCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = portabilityRepository.findAll().size();
        // set the field null
        portabilityEntity.setRetryCount(null);

        // Create the Portability, which fails.
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(portabilityEntity);

        restPortabilityMockMvc
            .perform(
                post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityDTO))
            )
            .andExpect(status().isBadRequest());

        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPortabilities() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList
        restPortabilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portabilityEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].porRequestId").value(hasItem(DEFAULT_POR_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].porNumber").value(hasItem(DEFAULT_POR_NUMBER)))
            .andExpect(jsonPath("$.[*].porLegalTerm").value(hasItem(DEFAULT_POR_LEGAL_TERM)))
            .andExpect(jsonPath("$.[*].porOpr").value(hasItem(DEFAULT_POR_OPR)))
            .andExpect(jsonPath("$.[*].porAccType").value(hasItem(DEFAULT_POR_ACC_TYPE)))
            .andExpect(jsonPath("$.[*].porIdNumber").value(hasItem(DEFAULT_POR_ID_NUMBER)))
            .andExpect(jsonPath("$.[*].porContactNumber").value(hasItem(DEFAULT_POR_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].porStatus").value(hasItem(DEFAULT_POR_STATUS)))
            .andExpect(jsonPath("$.[*].porPortedDate").value(hasItem(jsonDate(DEFAULT_POR_PORTED_DATE))))
            .andExpect(jsonPath("$.[*].porRouting").value(hasItem(DEFAULT_POR_ROUTING)))
            .andExpect(jsonPath("$.[*].porType").value(hasItem(DEFAULT_POR_TYPE)))
            .andExpect(jsonPath("$.[*].porOpOrg").value(hasItem(DEFAULT_POR_OP_ORG)))
            .andExpect(jsonPath("$.[*].porRspCode").value(hasItem(DEFAULT_POR_RSP_CODE)))
            .andExpect(jsonPath("$.[*].porRspNote").value(hasItem(DEFAULT_POR_RSP_NOTE)))
            .andExpect(jsonPath("$.[*].porCancelNote").value(hasItem(DEFAULT_POR_CANCEL_NOTE)))
            .andExpect(jsonPath("$.[*].porMnpid").value(hasItem(DEFAULT_POR_MNPID)))
            .andExpect(jsonPath("$.[*].portationDate").value(hasItem(jsonDate(DEFAULT_PORTATION_DATE))))
            .andExpect(jsonPath("$.[*].portaCode").value(hasItem(DEFAULT_PORTA_CODE)))
            .andExpect(jsonPath("$.[*].mvno").value(hasItem(DEFAULT_MVNO)))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].porErrCode").value(hasItem(DEFAULT_POR_ERR_CODE)))
            .andExpect(jsonPath("$.[*].porErrMessage").value(hasItem(DEFAULT_POR_ERR_MESSAGE)))
            .andExpect(jsonPath("$.[*].porOpd").value(hasItem(DEFAULT_POR_OPD)))
            .andExpect(jsonPath("$.[*].porNumType").value(hasItem(DEFAULT_POR_NUM_TYPE)))
            .andExpect(jsonPath("$.[*].porNote").value(hasItem(DEFAULT_POR_NOTE)))
            .andExpect(jsonPath("$.[*].porDeadline").value(hasItem(jsonDate(DEFAULT_POR_DEADLINE))))
            .andExpect(jsonPath("$.[*].porResponseTimestamp").value(hasItem(DEFAULT_POR_RESPONSE_TIMESTAMP.intValue())))
            .andExpect(jsonPath("$.[*].porEligible").value(hasItem(DEFAULT_POR_ELIGIBLE)))
            .andExpect(jsonPath("$.[*].porBillingOk").value(hasItem(DEFAULT_POR_BILLING_OK)))
            .andExpect(jsonPath("$.[*].intermediaryActionState").value(hasItem(DEFAULT_INTERMEDIARY_ACTION_STATE)))
            .andExpect(jsonPath("$.[*].porCrDate").value(hasItem(jsonDate(DEFAULT_POR_CR_DATE))))
            .andExpect(jsonPath("$.[*].porUpdDate").value(hasItem(jsonDate(DEFAULT_POR_UPD_DATE))))
            .andExpect(jsonPath("$.[*].porTechStatus").value(hasItem(DEFAULT_POR_TECH_STATUS)))
            .andExpect(jsonPath("$.[*].porTechDeadline").value(hasItem(jsonDate(DEFAULT_POR_TECH_DEADLINE))))
            .andExpect(jsonPath("$.[*].needManualRetry").value(hasItem(DEFAULT_NEED_MANUAL_RETRY)))
            .andExpect(jsonPath("$.[*].refPorId").value(hasItem(DEFAULT_REF_POR_ID.intValue())))
            .andExpect(jsonPath("$.[*].retryCount").value(hasItem(DEFAULT_RETRY_COUNT)));
    }

    @Test
    @Transactional
    void getPortability() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get the portability
        restPortabilityMockMvc
            .perform(get(ENTITY_API_URL_ID, portabilityEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(portabilityEntity.getId().intValue()))
            .andExpect(jsonPath("$.porRequestId").value(DEFAULT_POR_REQUEST_ID))
            .andExpect(jsonPath("$.porNumber").value(DEFAULT_POR_NUMBER))
            .andExpect(jsonPath("$.porLegalTerm").value(DEFAULT_POR_LEGAL_TERM))
            .andExpect(jsonPath("$.porOpr").value(DEFAULT_POR_OPR))
            .andExpect(jsonPath("$.porAccType").value(DEFAULT_POR_ACC_TYPE))
            .andExpect(jsonPath("$.porIdNumber").value(DEFAULT_POR_ID_NUMBER))
            .andExpect(jsonPath("$.porContactNumber").value(DEFAULT_POR_CONTACT_NUMBER))
            .andExpect(jsonPath("$.porStatus").value(DEFAULT_POR_STATUS))
            .andExpect(jsonPath("$.porPortedDate").value(jsonDate(DEFAULT_POR_PORTED_DATE)))
            .andExpect(jsonPath("$.porRouting").value(DEFAULT_POR_ROUTING))
            .andExpect(jsonPath("$.porType").value(DEFAULT_POR_TYPE))
            .andExpect(jsonPath("$.porOpOrg").value(DEFAULT_POR_OP_ORG))
            .andExpect(jsonPath("$.porRspCode").value(DEFAULT_POR_RSP_CODE))
            .andExpect(jsonPath("$.porRspNote").value(DEFAULT_POR_RSP_NOTE))
            .andExpect(jsonPath("$.porCancelNote").value(DEFAULT_POR_CANCEL_NOTE))
            .andExpect(jsonPath("$.porMnpid").value(DEFAULT_POR_MNPID))
            .andExpect(jsonPath("$.portationDate").value(jsonDate(DEFAULT_PORTATION_DATE)))
            .andExpect(jsonPath("$.portaCode").value(DEFAULT_PORTA_CODE))
            .andExpect(jsonPath("$.mvno").value(DEFAULT_MVNO))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT))
            .andExpect(jsonPath("$.porErrCode").value(DEFAULT_POR_ERR_CODE))
            .andExpect(jsonPath("$.porErrMessage").value(DEFAULT_POR_ERR_MESSAGE))
            .andExpect(jsonPath("$.porOpd").value(DEFAULT_POR_OPD))
            .andExpect(jsonPath("$.porNumType").value(DEFAULT_POR_NUM_TYPE))
            .andExpect(jsonPath("$.porNote").value(DEFAULT_POR_NOTE))
            .andExpect(jsonPath("$.porDeadline").value(jsonDate(DEFAULT_POR_DEADLINE)))
            .andExpect(jsonPath("$.porResponseTimestamp").value(DEFAULT_POR_RESPONSE_TIMESTAMP.intValue()))
            .andExpect(jsonPath("$.porEligible").value(DEFAULT_POR_ELIGIBLE))
            .andExpect(jsonPath("$.porBillingOk").value(DEFAULT_POR_BILLING_OK))
            .andExpect(jsonPath("$.intermediaryActionState").value(DEFAULT_INTERMEDIARY_ACTION_STATE))
            .andExpect(jsonPath("$.porCrDate").value(jsonDate(DEFAULT_POR_CR_DATE)))
            .andExpect(jsonPath("$.porUpdDate").value(jsonDate(DEFAULT_POR_UPD_DATE)))
            .andExpect(jsonPath("$.porTechStatus").value(DEFAULT_POR_TECH_STATUS))
            .andExpect(jsonPath("$.porTechDeadline").value(jsonDate(DEFAULT_POR_TECH_DEADLINE)))
            .andExpect(jsonPath("$.needManualRetry").value(DEFAULT_NEED_MANUAL_RETRY))
            .andExpect(jsonPath("$.refPorId").value(DEFAULT_REF_POR_ID.intValue()))
            .andExpect(jsonPath("$.retryCount").value(DEFAULT_RETRY_COUNT));
    }

    @Test
    @Transactional
    void getPortabilitiesByIdFiltering() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        Long id = portabilityEntity.getId();

        defaultPortabilityShouldBeFound("id.equals=" + id);
        defaultPortabilityShouldNotBeFound("id.notEquals=" + id);

        defaultPortabilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPortabilityShouldNotBeFound("id.greaterThan=" + id);

        defaultPortabilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPortabilityShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRequestIdIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRequestId equals to DEFAULT_POR_REQUEST_ID
        defaultPortabilityShouldBeFound("porRequestId.equals=" + DEFAULT_POR_REQUEST_ID);

        // Get all the portabilityList where porRequestId equals to UPDATED_POR_REQUEST_ID
        defaultPortabilityShouldNotBeFound("porRequestId.equals=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRequestIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRequestId not equals to DEFAULT_POR_REQUEST_ID
        defaultPortabilityShouldNotBeFound("porRequestId.notEquals=" + DEFAULT_POR_REQUEST_ID);

        // Get all the portabilityList where porRequestId not equals to UPDATED_POR_REQUEST_ID
        defaultPortabilityShouldBeFound("porRequestId.notEquals=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRequestIdIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRequestId in DEFAULT_POR_REQUEST_ID or UPDATED_POR_REQUEST_ID
        defaultPortabilityShouldBeFound("porRequestId.in=" + DEFAULT_POR_REQUEST_ID + "," + UPDATED_POR_REQUEST_ID);

        // Get all the portabilityList where porRequestId equals to UPDATED_POR_REQUEST_ID
        defaultPortabilityShouldNotBeFound("porRequestId.in=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRequestIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRequestId is not null
        defaultPortabilityShouldBeFound("porRequestId.specified=true");

        // Get all the portabilityList where porRequestId is null
        defaultPortabilityShouldNotBeFound("porRequestId.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRequestIdContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRequestId contains DEFAULT_POR_REQUEST_ID
        defaultPortabilityShouldBeFound("porRequestId.contains=" + DEFAULT_POR_REQUEST_ID);

        // Get all the portabilityList where porRequestId contains UPDATED_POR_REQUEST_ID
        defaultPortabilityShouldNotBeFound("porRequestId.contains=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRequestIdNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRequestId does not contain DEFAULT_POR_REQUEST_ID
        defaultPortabilityShouldNotBeFound("porRequestId.doesNotContain=" + DEFAULT_POR_REQUEST_ID);

        // Get all the portabilityList where porRequestId does not contain UPDATED_POR_REQUEST_ID
        defaultPortabilityShouldBeFound("porRequestId.doesNotContain=" + UPDATED_POR_REQUEST_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumber equals to DEFAULT_POR_NUMBER
        defaultPortabilityShouldBeFound("porNumber.equals=" + DEFAULT_POR_NUMBER);

        // Get all the portabilityList where porNumber equals to UPDATED_POR_NUMBER
        defaultPortabilityShouldNotBeFound("porNumber.equals=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumber not equals to DEFAULT_POR_NUMBER
        defaultPortabilityShouldNotBeFound("porNumber.notEquals=" + DEFAULT_POR_NUMBER);

        // Get all the portabilityList where porNumber not equals to UPDATED_POR_NUMBER
        defaultPortabilityShouldBeFound("porNumber.notEquals=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumberIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumber in DEFAULT_POR_NUMBER or UPDATED_POR_NUMBER
        defaultPortabilityShouldBeFound("porNumber.in=" + DEFAULT_POR_NUMBER + "," + UPDATED_POR_NUMBER);

        // Get all the portabilityList where porNumber equals to UPDATED_POR_NUMBER
        defaultPortabilityShouldNotBeFound("porNumber.in=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumber is not null
        defaultPortabilityShouldBeFound("porNumber.specified=true");

        // Get all the portabilityList where porNumber is null
        defaultPortabilityShouldNotBeFound("porNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumberContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumber contains DEFAULT_POR_NUMBER
        defaultPortabilityShouldBeFound("porNumber.contains=" + DEFAULT_POR_NUMBER);

        // Get all the portabilityList where porNumber contains UPDATED_POR_NUMBER
        defaultPortabilityShouldNotBeFound("porNumber.contains=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumberNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumber does not contain DEFAULT_POR_NUMBER
        defaultPortabilityShouldNotBeFound("porNumber.doesNotContain=" + DEFAULT_POR_NUMBER);

        // Get all the portabilityList where porNumber does not contain UPDATED_POR_NUMBER
        defaultPortabilityShouldBeFound("porNumber.doesNotContain=" + UPDATED_POR_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorLegalTermIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porLegalTerm equals to DEFAULT_POR_LEGAL_TERM
        defaultPortabilityShouldBeFound("porLegalTerm.equals=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityList where porLegalTerm equals to UPDATED_POR_LEGAL_TERM
        defaultPortabilityShouldNotBeFound("porLegalTerm.equals=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorLegalTermIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porLegalTerm not equals to DEFAULT_POR_LEGAL_TERM
        defaultPortabilityShouldNotBeFound("porLegalTerm.notEquals=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityList where porLegalTerm not equals to UPDATED_POR_LEGAL_TERM
        defaultPortabilityShouldBeFound("porLegalTerm.notEquals=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorLegalTermIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porLegalTerm in DEFAULT_POR_LEGAL_TERM or UPDATED_POR_LEGAL_TERM
        defaultPortabilityShouldBeFound("porLegalTerm.in=" + DEFAULT_POR_LEGAL_TERM + "," + UPDATED_POR_LEGAL_TERM);

        // Get all the portabilityList where porLegalTerm equals to UPDATED_POR_LEGAL_TERM
        defaultPortabilityShouldNotBeFound("porLegalTerm.in=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorLegalTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porLegalTerm is not null
        defaultPortabilityShouldBeFound("porLegalTerm.specified=true");

        // Get all the portabilityList where porLegalTerm is null
        defaultPortabilityShouldNotBeFound("porLegalTerm.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorLegalTermIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porLegalTerm is greater than or equal to DEFAULT_POR_LEGAL_TERM
        defaultPortabilityShouldBeFound("porLegalTerm.greaterThanOrEqual=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityList where porLegalTerm is greater than or equal to UPDATED_POR_LEGAL_TERM
        defaultPortabilityShouldNotBeFound("porLegalTerm.greaterThanOrEqual=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorLegalTermIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porLegalTerm is less than or equal to DEFAULT_POR_LEGAL_TERM
        defaultPortabilityShouldBeFound("porLegalTerm.lessThanOrEqual=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityList where porLegalTerm is less than or equal to SMALLER_POR_LEGAL_TERM
        defaultPortabilityShouldNotBeFound("porLegalTerm.lessThanOrEqual=" + SMALLER_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorLegalTermIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porLegalTerm is less than DEFAULT_POR_LEGAL_TERM
        defaultPortabilityShouldNotBeFound("porLegalTerm.lessThan=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityList where porLegalTerm is less than UPDATED_POR_LEGAL_TERM
        defaultPortabilityShouldBeFound("porLegalTerm.lessThan=" + UPDATED_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorLegalTermIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porLegalTerm is greater than DEFAULT_POR_LEGAL_TERM
        defaultPortabilityShouldNotBeFound("porLegalTerm.greaterThan=" + DEFAULT_POR_LEGAL_TERM);

        // Get all the portabilityList where porLegalTerm is greater than SMALLER_POR_LEGAL_TERM
        defaultPortabilityShouldBeFound("porLegalTerm.greaterThan=" + SMALLER_POR_LEGAL_TERM);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOprIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpr equals to DEFAULT_POR_OPR
        defaultPortabilityShouldBeFound("porOpr.equals=" + DEFAULT_POR_OPR);

        // Get all the portabilityList where porOpr equals to UPDATED_POR_OPR
        defaultPortabilityShouldNotBeFound("porOpr.equals=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOprIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpr not equals to DEFAULT_POR_OPR
        defaultPortabilityShouldNotBeFound("porOpr.notEquals=" + DEFAULT_POR_OPR);

        // Get all the portabilityList where porOpr not equals to UPDATED_POR_OPR
        defaultPortabilityShouldBeFound("porOpr.notEquals=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOprIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpr in DEFAULT_POR_OPR or UPDATED_POR_OPR
        defaultPortabilityShouldBeFound("porOpr.in=" + DEFAULT_POR_OPR + "," + UPDATED_POR_OPR);

        // Get all the portabilityList where porOpr equals to UPDATED_POR_OPR
        defaultPortabilityShouldNotBeFound("porOpr.in=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOprIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpr is not null
        defaultPortabilityShouldBeFound("porOpr.specified=true");

        // Get all the portabilityList where porOpr is null
        defaultPortabilityShouldNotBeFound("porOpr.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOprContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpr contains DEFAULT_POR_OPR
        defaultPortabilityShouldBeFound("porOpr.contains=" + DEFAULT_POR_OPR);

        // Get all the portabilityList where porOpr contains UPDATED_POR_OPR
        defaultPortabilityShouldNotBeFound("porOpr.contains=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOprNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpr does not contain DEFAULT_POR_OPR
        defaultPortabilityShouldNotBeFound("porOpr.doesNotContain=" + DEFAULT_POR_OPR);

        // Get all the portabilityList where porOpr does not contain UPDATED_POR_OPR
        defaultPortabilityShouldBeFound("porOpr.doesNotContain=" + UPDATED_POR_OPR);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorAccTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porAccType equals to DEFAULT_POR_ACC_TYPE
        defaultPortabilityShouldBeFound("porAccType.equals=" + DEFAULT_POR_ACC_TYPE);

        // Get all the portabilityList where porAccType equals to UPDATED_POR_ACC_TYPE
        defaultPortabilityShouldNotBeFound("porAccType.equals=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorAccTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porAccType not equals to DEFAULT_POR_ACC_TYPE
        defaultPortabilityShouldNotBeFound("porAccType.notEquals=" + DEFAULT_POR_ACC_TYPE);

        // Get all the portabilityList where porAccType not equals to UPDATED_POR_ACC_TYPE
        defaultPortabilityShouldBeFound("porAccType.notEquals=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorAccTypeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porAccType in DEFAULT_POR_ACC_TYPE or UPDATED_POR_ACC_TYPE
        defaultPortabilityShouldBeFound("porAccType.in=" + DEFAULT_POR_ACC_TYPE + "," + UPDATED_POR_ACC_TYPE);

        // Get all the portabilityList where porAccType equals to UPDATED_POR_ACC_TYPE
        defaultPortabilityShouldNotBeFound("porAccType.in=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorAccTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porAccType is not null
        defaultPortabilityShouldBeFound("porAccType.specified=true");

        // Get all the portabilityList where porAccType is null
        defaultPortabilityShouldNotBeFound("porAccType.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorAccTypeContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porAccType contains DEFAULT_POR_ACC_TYPE
        defaultPortabilityShouldBeFound("porAccType.contains=" + DEFAULT_POR_ACC_TYPE);

        // Get all the portabilityList where porAccType contains UPDATED_POR_ACC_TYPE
        defaultPortabilityShouldNotBeFound("porAccType.contains=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorAccTypeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porAccType does not contain DEFAULT_POR_ACC_TYPE
        defaultPortabilityShouldNotBeFound("porAccType.doesNotContain=" + DEFAULT_POR_ACC_TYPE);

        // Get all the portabilityList where porAccType does not contain UPDATED_POR_ACC_TYPE
        defaultPortabilityShouldBeFound("porAccType.doesNotContain=" + UPDATED_POR_ACC_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorIdNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porIdNumber equals to DEFAULT_POR_ID_NUMBER
        defaultPortabilityShouldBeFound("porIdNumber.equals=" + DEFAULT_POR_ID_NUMBER);

        // Get all the portabilityList where porIdNumber equals to UPDATED_POR_ID_NUMBER
        defaultPortabilityShouldNotBeFound("porIdNumber.equals=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorIdNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porIdNumber not equals to DEFAULT_POR_ID_NUMBER
        defaultPortabilityShouldNotBeFound("porIdNumber.notEquals=" + DEFAULT_POR_ID_NUMBER);

        // Get all the portabilityList where porIdNumber not equals to UPDATED_POR_ID_NUMBER
        defaultPortabilityShouldBeFound("porIdNumber.notEquals=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorIdNumberIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porIdNumber in DEFAULT_POR_ID_NUMBER or UPDATED_POR_ID_NUMBER
        defaultPortabilityShouldBeFound("porIdNumber.in=" + DEFAULT_POR_ID_NUMBER + "," + UPDATED_POR_ID_NUMBER);

        // Get all the portabilityList where porIdNumber equals to UPDATED_POR_ID_NUMBER
        defaultPortabilityShouldNotBeFound("porIdNumber.in=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorIdNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porIdNumber is not null
        defaultPortabilityShouldBeFound("porIdNumber.specified=true");

        // Get all the portabilityList where porIdNumber is null
        defaultPortabilityShouldNotBeFound("porIdNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorIdNumberContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porIdNumber contains DEFAULT_POR_ID_NUMBER
        defaultPortabilityShouldBeFound("porIdNumber.contains=" + DEFAULT_POR_ID_NUMBER);

        // Get all the portabilityList where porIdNumber contains UPDATED_POR_ID_NUMBER
        defaultPortabilityShouldNotBeFound("porIdNumber.contains=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorIdNumberNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porIdNumber does not contain DEFAULT_POR_ID_NUMBER
        defaultPortabilityShouldNotBeFound("porIdNumber.doesNotContain=" + DEFAULT_POR_ID_NUMBER);

        // Get all the portabilityList where porIdNumber does not contain UPDATED_POR_ID_NUMBER
        defaultPortabilityShouldBeFound("porIdNumber.doesNotContain=" + UPDATED_POR_ID_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorContactNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porContactNumber equals to DEFAULT_POR_CONTACT_NUMBER
        defaultPortabilityShouldBeFound("porContactNumber.equals=" + DEFAULT_POR_CONTACT_NUMBER);

        // Get all the portabilityList where porContactNumber equals to UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityShouldNotBeFound("porContactNumber.equals=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorContactNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porContactNumber not equals to DEFAULT_POR_CONTACT_NUMBER
        defaultPortabilityShouldNotBeFound("porContactNumber.notEquals=" + DEFAULT_POR_CONTACT_NUMBER);

        // Get all the portabilityList where porContactNumber not equals to UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityShouldBeFound("porContactNumber.notEquals=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorContactNumberIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porContactNumber in DEFAULT_POR_CONTACT_NUMBER or UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityShouldBeFound("porContactNumber.in=" + DEFAULT_POR_CONTACT_NUMBER + "," + UPDATED_POR_CONTACT_NUMBER);

        // Get all the portabilityList where porContactNumber equals to UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityShouldNotBeFound("porContactNumber.in=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorContactNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porContactNumber is not null
        defaultPortabilityShouldBeFound("porContactNumber.specified=true");

        // Get all the portabilityList where porContactNumber is null
        defaultPortabilityShouldNotBeFound("porContactNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorContactNumberContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porContactNumber contains DEFAULT_POR_CONTACT_NUMBER
        defaultPortabilityShouldBeFound("porContactNumber.contains=" + DEFAULT_POR_CONTACT_NUMBER);

        // Get all the portabilityList where porContactNumber contains UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityShouldNotBeFound("porContactNumber.contains=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorContactNumberNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porContactNumber does not contain DEFAULT_POR_CONTACT_NUMBER
        defaultPortabilityShouldNotBeFound("porContactNumber.doesNotContain=" + DEFAULT_POR_CONTACT_NUMBER);

        // Get all the portabilityList where porContactNumber does not contain UPDATED_POR_CONTACT_NUMBER
        defaultPortabilityShouldBeFound("porContactNumber.doesNotContain=" + UPDATED_POR_CONTACT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porStatus equals to DEFAULT_POR_STATUS
        defaultPortabilityShouldBeFound("porStatus.equals=" + DEFAULT_POR_STATUS);

        // Get all the portabilityList where porStatus equals to UPDATED_POR_STATUS
        defaultPortabilityShouldNotBeFound("porStatus.equals=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porStatus not equals to DEFAULT_POR_STATUS
        defaultPortabilityShouldNotBeFound("porStatus.notEquals=" + DEFAULT_POR_STATUS);

        // Get all the portabilityList where porStatus not equals to UPDATED_POR_STATUS
        defaultPortabilityShouldBeFound("porStatus.notEquals=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorStatusIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porStatus in DEFAULT_POR_STATUS or UPDATED_POR_STATUS
        defaultPortabilityShouldBeFound("porStatus.in=" + DEFAULT_POR_STATUS + "," + UPDATED_POR_STATUS);

        // Get all the portabilityList where porStatus equals to UPDATED_POR_STATUS
        defaultPortabilityShouldNotBeFound("porStatus.in=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porStatus is not null
        defaultPortabilityShouldBeFound("porStatus.specified=true");

        // Get all the portabilityList where porStatus is null
        defaultPortabilityShouldNotBeFound("porStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorStatusContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porStatus contains DEFAULT_POR_STATUS
        defaultPortabilityShouldBeFound("porStatus.contains=" + DEFAULT_POR_STATUS);

        // Get all the portabilityList where porStatus contains UPDATED_POR_STATUS
        defaultPortabilityShouldNotBeFound("porStatus.contains=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorStatusNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porStatus does not contain DEFAULT_POR_STATUS
        defaultPortabilityShouldNotBeFound("porStatus.doesNotContain=" + DEFAULT_POR_STATUS);

        // Get all the portabilityList where porStatus does not contain UPDATED_POR_STATUS
        defaultPortabilityShouldBeFound("porStatus.doesNotContain=" + UPDATED_POR_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorPortedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porPortedDate equals to DEFAULT_POR_PORTED_DATE
        defaultPortabilityShouldBeFound("porPortedDate.equals=" + DEFAULT_POR_PORTED_DATE);

        // Get all the portabilityList where porPortedDate equals to UPDATED_POR_PORTED_DATE
        defaultPortabilityShouldNotBeFound("porPortedDate.equals=" + UPDATED_POR_PORTED_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorPortedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porPortedDate not equals to DEFAULT_POR_PORTED_DATE
        defaultPortabilityShouldNotBeFound("porPortedDate.notEquals=" + DEFAULT_POR_PORTED_DATE);

        // Get all the portabilityList where porPortedDate not equals to UPDATED_POR_PORTED_DATE
        defaultPortabilityShouldBeFound("porPortedDate.notEquals=" + UPDATED_POR_PORTED_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorPortedDateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porPortedDate in DEFAULT_POR_PORTED_DATE or UPDATED_POR_PORTED_DATE
        defaultPortabilityShouldBeFound("porPortedDate.in=" + DEFAULT_POR_PORTED_DATE + "," + UPDATED_POR_PORTED_DATE);

        // Get all the portabilityList where porPortedDate equals to UPDATED_POR_PORTED_DATE
        defaultPortabilityShouldNotBeFound("porPortedDate.in=" + UPDATED_POR_PORTED_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorPortedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porPortedDate is not null
        defaultPortabilityShouldBeFound("porPortedDate.specified=true");

        // Get all the portabilityList where porPortedDate is null
        defaultPortabilityShouldNotBeFound("porPortedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRoutingIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRouting equals to DEFAULT_POR_ROUTING
        defaultPortabilityShouldBeFound("porRouting.equals=" + DEFAULT_POR_ROUTING);

        // Get all the portabilityList where porRouting equals to UPDATED_POR_ROUTING
        defaultPortabilityShouldNotBeFound("porRouting.equals=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRoutingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRouting not equals to DEFAULT_POR_ROUTING
        defaultPortabilityShouldNotBeFound("porRouting.notEquals=" + DEFAULT_POR_ROUTING);

        // Get all the portabilityList where porRouting not equals to UPDATED_POR_ROUTING
        defaultPortabilityShouldBeFound("porRouting.notEquals=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRoutingIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRouting in DEFAULT_POR_ROUTING or UPDATED_POR_ROUTING
        defaultPortabilityShouldBeFound("porRouting.in=" + DEFAULT_POR_ROUTING + "," + UPDATED_POR_ROUTING);

        // Get all the portabilityList where porRouting equals to UPDATED_POR_ROUTING
        defaultPortabilityShouldNotBeFound("porRouting.in=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRoutingIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRouting is not null
        defaultPortabilityShouldBeFound("porRouting.specified=true");

        // Get all the portabilityList where porRouting is null
        defaultPortabilityShouldNotBeFound("porRouting.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRoutingContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRouting contains DEFAULT_POR_ROUTING
        defaultPortabilityShouldBeFound("porRouting.contains=" + DEFAULT_POR_ROUTING);

        // Get all the portabilityList where porRouting contains UPDATED_POR_ROUTING
        defaultPortabilityShouldNotBeFound("porRouting.contains=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRoutingNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRouting does not contain DEFAULT_POR_ROUTING
        defaultPortabilityShouldNotBeFound("porRouting.doesNotContain=" + DEFAULT_POR_ROUTING);

        // Get all the portabilityList where porRouting does not contain UPDATED_POR_ROUTING
        defaultPortabilityShouldBeFound("porRouting.doesNotContain=" + UPDATED_POR_ROUTING);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porType equals to DEFAULT_POR_TYPE
        defaultPortabilityShouldBeFound("porType.equals=" + DEFAULT_POR_TYPE);

        // Get all the portabilityList where porType equals to UPDATED_POR_TYPE
        defaultPortabilityShouldNotBeFound("porType.equals=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porType not equals to DEFAULT_POR_TYPE
        defaultPortabilityShouldNotBeFound("porType.notEquals=" + DEFAULT_POR_TYPE);

        // Get all the portabilityList where porType not equals to UPDATED_POR_TYPE
        defaultPortabilityShouldBeFound("porType.notEquals=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTypeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porType in DEFAULT_POR_TYPE or UPDATED_POR_TYPE
        defaultPortabilityShouldBeFound("porType.in=" + DEFAULT_POR_TYPE + "," + UPDATED_POR_TYPE);

        // Get all the portabilityList where porType equals to UPDATED_POR_TYPE
        defaultPortabilityShouldNotBeFound("porType.in=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porType is not null
        defaultPortabilityShouldBeFound("porType.specified=true");

        // Get all the portabilityList where porType is null
        defaultPortabilityShouldNotBeFound("porType.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTypeContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porType contains DEFAULT_POR_TYPE
        defaultPortabilityShouldBeFound("porType.contains=" + DEFAULT_POR_TYPE);

        // Get all the portabilityList where porType contains UPDATED_POR_TYPE
        defaultPortabilityShouldNotBeFound("porType.contains=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTypeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porType does not contain DEFAULT_POR_TYPE
        defaultPortabilityShouldNotBeFound("porType.doesNotContain=" + DEFAULT_POR_TYPE);

        // Get all the portabilityList where porType does not contain UPDATED_POR_TYPE
        defaultPortabilityShouldBeFound("porType.doesNotContain=" + UPDATED_POR_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpOrg equals to DEFAULT_POR_OP_ORG
        defaultPortabilityShouldBeFound("porOpOrg.equals=" + DEFAULT_POR_OP_ORG);

        // Get all the portabilityList where porOpOrg equals to UPDATED_POR_OP_ORG
        defaultPortabilityShouldNotBeFound("porOpOrg.equals=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpOrg not equals to DEFAULT_POR_OP_ORG
        defaultPortabilityShouldNotBeFound("porOpOrg.notEquals=" + DEFAULT_POR_OP_ORG);

        // Get all the portabilityList where porOpOrg not equals to UPDATED_POR_OP_ORG
        defaultPortabilityShouldBeFound("porOpOrg.notEquals=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpOrgIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpOrg in DEFAULT_POR_OP_ORG or UPDATED_POR_OP_ORG
        defaultPortabilityShouldBeFound("porOpOrg.in=" + DEFAULT_POR_OP_ORG + "," + UPDATED_POR_OP_ORG);

        // Get all the portabilityList where porOpOrg equals to UPDATED_POR_OP_ORG
        defaultPortabilityShouldNotBeFound("porOpOrg.in=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpOrg is not null
        defaultPortabilityShouldBeFound("porOpOrg.specified=true");

        // Get all the portabilityList where porOpOrg is null
        defaultPortabilityShouldNotBeFound("porOpOrg.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpOrgContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpOrg contains DEFAULT_POR_OP_ORG
        defaultPortabilityShouldBeFound("porOpOrg.contains=" + DEFAULT_POR_OP_ORG);

        // Get all the portabilityList where porOpOrg contains UPDATED_POR_OP_ORG
        defaultPortabilityShouldNotBeFound("porOpOrg.contains=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpOrgNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpOrg does not contain DEFAULT_POR_OP_ORG
        defaultPortabilityShouldNotBeFound("porOpOrg.doesNotContain=" + DEFAULT_POR_OP_ORG);

        // Get all the portabilityList where porOpOrg does not contain UPDATED_POR_OP_ORG
        defaultPortabilityShouldBeFound("porOpOrg.doesNotContain=" + UPDATED_POR_OP_ORG);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspCode equals to DEFAULT_POR_RSP_CODE
        defaultPortabilityShouldBeFound("porRspCode.equals=" + DEFAULT_POR_RSP_CODE);

        // Get all the portabilityList where porRspCode equals to UPDATED_POR_RSP_CODE
        defaultPortabilityShouldNotBeFound("porRspCode.equals=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspCode not equals to DEFAULT_POR_RSP_CODE
        defaultPortabilityShouldNotBeFound("porRspCode.notEquals=" + DEFAULT_POR_RSP_CODE);

        // Get all the portabilityList where porRspCode not equals to UPDATED_POR_RSP_CODE
        defaultPortabilityShouldBeFound("porRspCode.notEquals=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspCodeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspCode in DEFAULT_POR_RSP_CODE or UPDATED_POR_RSP_CODE
        defaultPortabilityShouldBeFound("porRspCode.in=" + DEFAULT_POR_RSP_CODE + "," + UPDATED_POR_RSP_CODE);

        // Get all the portabilityList where porRspCode equals to UPDATED_POR_RSP_CODE
        defaultPortabilityShouldNotBeFound("porRspCode.in=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspCode is not null
        defaultPortabilityShouldBeFound("porRspCode.specified=true");

        // Get all the portabilityList where porRspCode is null
        defaultPortabilityShouldNotBeFound("porRspCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspCodeContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspCode contains DEFAULT_POR_RSP_CODE
        defaultPortabilityShouldBeFound("porRspCode.contains=" + DEFAULT_POR_RSP_CODE);

        // Get all the portabilityList where porRspCode contains UPDATED_POR_RSP_CODE
        defaultPortabilityShouldNotBeFound("porRspCode.contains=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspCodeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspCode does not contain DEFAULT_POR_RSP_CODE
        defaultPortabilityShouldNotBeFound("porRspCode.doesNotContain=" + DEFAULT_POR_RSP_CODE);

        // Get all the portabilityList where porRspCode does not contain UPDATED_POR_RSP_CODE
        defaultPortabilityShouldBeFound("porRspCode.doesNotContain=" + UPDATED_POR_RSP_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspNote equals to DEFAULT_POR_RSP_NOTE
        defaultPortabilityShouldBeFound("porRspNote.equals=" + DEFAULT_POR_RSP_NOTE);

        // Get all the portabilityList where porRspNote equals to UPDATED_POR_RSP_NOTE
        defaultPortabilityShouldNotBeFound("porRspNote.equals=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspNote not equals to DEFAULT_POR_RSP_NOTE
        defaultPortabilityShouldNotBeFound("porRspNote.notEquals=" + DEFAULT_POR_RSP_NOTE);

        // Get all the portabilityList where porRspNote not equals to UPDATED_POR_RSP_NOTE
        defaultPortabilityShouldBeFound("porRspNote.notEquals=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspNoteIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspNote in DEFAULT_POR_RSP_NOTE or UPDATED_POR_RSP_NOTE
        defaultPortabilityShouldBeFound("porRspNote.in=" + DEFAULT_POR_RSP_NOTE + "," + UPDATED_POR_RSP_NOTE);

        // Get all the portabilityList where porRspNote equals to UPDATED_POR_RSP_NOTE
        defaultPortabilityShouldNotBeFound("porRspNote.in=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspNote is not null
        defaultPortabilityShouldBeFound("porRspNote.specified=true");

        // Get all the portabilityList where porRspNote is null
        defaultPortabilityShouldNotBeFound("porRspNote.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspNoteContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspNote contains DEFAULT_POR_RSP_NOTE
        defaultPortabilityShouldBeFound("porRspNote.contains=" + DEFAULT_POR_RSP_NOTE);

        // Get all the portabilityList where porRspNote contains UPDATED_POR_RSP_NOTE
        defaultPortabilityShouldNotBeFound("porRspNote.contains=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorRspNoteNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porRspNote does not contain DEFAULT_POR_RSP_NOTE
        defaultPortabilityShouldNotBeFound("porRspNote.doesNotContain=" + DEFAULT_POR_RSP_NOTE);

        // Get all the portabilityList where porRspNote does not contain UPDATED_POR_RSP_NOTE
        defaultPortabilityShouldBeFound("porRspNote.doesNotContain=" + UPDATED_POR_RSP_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCancelNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCancelNote equals to DEFAULT_POR_CANCEL_NOTE
        defaultPortabilityShouldBeFound("porCancelNote.equals=" + DEFAULT_POR_CANCEL_NOTE);

        // Get all the portabilityList where porCancelNote equals to UPDATED_POR_CANCEL_NOTE
        defaultPortabilityShouldNotBeFound("porCancelNote.equals=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCancelNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCancelNote not equals to DEFAULT_POR_CANCEL_NOTE
        defaultPortabilityShouldNotBeFound("porCancelNote.notEquals=" + DEFAULT_POR_CANCEL_NOTE);

        // Get all the portabilityList where porCancelNote not equals to UPDATED_POR_CANCEL_NOTE
        defaultPortabilityShouldBeFound("porCancelNote.notEquals=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCancelNoteIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCancelNote in DEFAULT_POR_CANCEL_NOTE or UPDATED_POR_CANCEL_NOTE
        defaultPortabilityShouldBeFound("porCancelNote.in=" + DEFAULT_POR_CANCEL_NOTE + "," + UPDATED_POR_CANCEL_NOTE);

        // Get all the portabilityList where porCancelNote equals to UPDATED_POR_CANCEL_NOTE
        defaultPortabilityShouldNotBeFound("porCancelNote.in=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCancelNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCancelNote is not null
        defaultPortabilityShouldBeFound("porCancelNote.specified=true");

        // Get all the portabilityList where porCancelNote is null
        defaultPortabilityShouldNotBeFound("porCancelNote.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCancelNoteContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCancelNote contains DEFAULT_POR_CANCEL_NOTE
        defaultPortabilityShouldBeFound("porCancelNote.contains=" + DEFAULT_POR_CANCEL_NOTE);

        // Get all the portabilityList where porCancelNote contains UPDATED_POR_CANCEL_NOTE
        defaultPortabilityShouldNotBeFound("porCancelNote.contains=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCancelNoteNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCancelNote does not contain DEFAULT_POR_CANCEL_NOTE
        defaultPortabilityShouldNotBeFound("porCancelNote.doesNotContain=" + DEFAULT_POR_CANCEL_NOTE);

        // Get all the portabilityList where porCancelNote does not contain UPDATED_POR_CANCEL_NOTE
        defaultPortabilityShouldBeFound("porCancelNote.doesNotContain=" + UPDATED_POR_CANCEL_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorMnpidIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porMnpid equals to DEFAULT_POR_MNPID
        defaultPortabilityShouldBeFound("porMnpid.equals=" + DEFAULT_POR_MNPID);

        // Get all the portabilityList where porMnpid equals to UPDATED_POR_MNPID
        defaultPortabilityShouldNotBeFound("porMnpid.equals=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorMnpidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porMnpid not equals to DEFAULT_POR_MNPID
        defaultPortabilityShouldNotBeFound("porMnpid.notEquals=" + DEFAULT_POR_MNPID);

        // Get all the portabilityList where porMnpid not equals to UPDATED_POR_MNPID
        defaultPortabilityShouldBeFound("porMnpid.notEquals=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorMnpidIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porMnpid in DEFAULT_POR_MNPID or UPDATED_POR_MNPID
        defaultPortabilityShouldBeFound("porMnpid.in=" + DEFAULT_POR_MNPID + "," + UPDATED_POR_MNPID);

        // Get all the portabilityList where porMnpid equals to UPDATED_POR_MNPID
        defaultPortabilityShouldNotBeFound("porMnpid.in=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorMnpidIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porMnpid is not null
        defaultPortabilityShouldBeFound("porMnpid.specified=true");

        // Get all the portabilityList where porMnpid is null
        defaultPortabilityShouldNotBeFound("porMnpid.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorMnpidContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porMnpid contains DEFAULT_POR_MNPID
        defaultPortabilityShouldBeFound("porMnpid.contains=" + DEFAULT_POR_MNPID);

        // Get all the portabilityList where porMnpid contains UPDATED_POR_MNPID
        defaultPortabilityShouldNotBeFound("porMnpid.contains=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorMnpidNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porMnpid does not contain DEFAULT_POR_MNPID
        defaultPortabilityShouldNotBeFound("porMnpid.doesNotContain=" + DEFAULT_POR_MNPID);

        // Get all the portabilityList where porMnpid does not contain UPDATED_POR_MNPID
        defaultPortabilityShouldBeFound("porMnpid.doesNotContain=" + UPDATED_POR_MNPID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portationDate equals to DEFAULT_PORTATION_DATE
        defaultPortabilityShouldBeFound("portationDate.equals=" + DEFAULT_PORTATION_DATE);

        // Get all the portabilityList where portationDate equals to UPDATED_PORTATION_DATE
        defaultPortabilityShouldNotBeFound("portationDate.equals=" + UPDATED_PORTATION_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portationDate not equals to DEFAULT_PORTATION_DATE
        defaultPortabilityShouldNotBeFound("portationDate.notEquals=" + DEFAULT_PORTATION_DATE);

        // Get all the portabilityList where portationDate not equals to UPDATED_PORTATION_DATE
        defaultPortabilityShouldBeFound("portationDate.notEquals=" + UPDATED_PORTATION_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortationDateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portationDate in DEFAULT_PORTATION_DATE or UPDATED_PORTATION_DATE
        defaultPortabilityShouldBeFound("portationDate.in=" + DEFAULT_PORTATION_DATE + "," + UPDATED_PORTATION_DATE);

        // Get all the portabilityList where portationDate equals to UPDATED_PORTATION_DATE
        defaultPortabilityShouldNotBeFound("portationDate.in=" + UPDATED_PORTATION_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portationDate is not null
        defaultPortabilityShouldBeFound("portationDate.specified=true");

        // Get all the portabilityList where portationDate is null
        defaultPortabilityShouldNotBeFound("portationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortaCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portaCode equals to DEFAULT_PORTA_CODE
        defaultPortabilityShouldBeFound("portaCode.equals=" + DEFAULT_PORTA_CODE);

        // Get all the portabilityList where portaCode equals to UPDATED_PORTA_CODE
        defaultPortabilityShouldNotBeFound("portaCode.equals=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortaCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portaCode not equals to DEFAULT_PORTA_CODE
        defaultPortabilityShouldNotBeFound("portaCode.notEquals=" + DEFAULT_PORTA_CODE);

        // Get all the portabilityList where portaCode not equals to UPDATED_PORTA_CODE
        defaultPortabilityShouldBeFound("portaCode.notEquals=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortaCodeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portaCode in DEFAULT_PORTA_CODE or UPDATED_PORTA_CODE
        defaultPortabilityShouldBeFound("portaCode.in=" + DEFAULT_PORTA_CODE + "," + UPDATED_PORTA_CODE);

        // Get all the portabilityList where portaCode equals to UPDATED_PORTA_CODE
        defaultPortabilityShouldNotBeFound("portaCode.in=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortaCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portaCode is not null
        defaultPortabilityShouldBeFound("portaCode.specified=true");

        // Get all the portabilityList where portaCode is null
        defaultPortabilityShouldNotBeFound("portaCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortaCodeContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portaCode contains DEFAULT_PORTA_CODE
        defaultPortabilityShouldBeFound("portaCode.contains=" + DEFAULT_PORTA_CODE);

        // Get all the portabilityList where portaCode contains UPDATED_PORTA_CODE
        defaultPortabilityShouldNotBeFound("portaCode.contains=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPortaCodeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where portaCode does not contain DEFAULT_PORTA_CODE
        defaultPortabilityShouldNotBeFound("portaCode.doesNotContain=" + DEFAULT_PORTA_CODE);

        // Get all the portabilityList where portaCode does not contain UPDATED_PORTA_CODE
        defaultPortabilityShouldBeFound("portaCode.doesNotContain=" + UPDATED_PORTA_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByMvnoIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where mvno equals to DEFAULT_MVNO
        defaultPortabilityShouldBeFound("mvno.equals=" + DEFAULT_MVNO);

        // Get all the portabilityList where mvno equals to UPDATED_MVNO
        defaultPortabilityShouldNotBeFound("mvno.equals=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByMvnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where mvno not equals to DEFAULT_MVNO
        defaultPortabilityShouldNotBeFound("mvno.notEquals=" + DEFAULT_MVNO);

        // Get all the portabilityList where mvno not equals to UPDATED_MVNO
        defaultPortabilityShouldBeFound("mvno.notEquals=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByMvnoIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where mvno in DEFAULT_MVNO or UPDATED_MVNO
        defaultPortabilityShouldBeFound("mvno.in=" + DEFAULT_MVNO + "," + UPDATED_MVNO);

        // Get all the portabilityList where mvno equals to UPDATED_MVNO
        defaultPortabilityShouldNotBeFound("mvno.in=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByMvnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where mvno is not null
        defaultPortabilityShouldBeFound("mvno.specified=true");

        // Get all the portabilityList where mvno is null
        defaultPortabilityShouldNotBeFound("mvno.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByMvnoContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where mvno contains DEFAULT_MVNO
        defaultPortabilityShouldBeFound("mvno.contains=" + DEFAULT_MVNO);

        // Get all the portabilityList where mvno contains UPDATED_MVNO
        defaultPortabilityShouldNotBeFound("mvno.contains=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByMvnoNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where mvno does not contain DEFAULT_MVNO
        defaultPortabilityShouldNotBeFound("mvno.doesNotContain=" + DEFAULT_MVNO);

        // Get all the portabilityList where mvno does not contain UPDATED_MVNO
        defaultPortabilityShouldBeFound("mvno.doesNotContain=" + UPDATED_MVNO);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByContextIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where context equals to DEFAULT_CONTEXT
        defaultPortabilityShouldBeFound("context.equals=" + DEFAULT_CONTEXT);

        // Get all the portabilityList where context equals to UPDATED_CONTEXT
        defaultPortabilityShouldNotBeFound("context.equals=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByContextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where context not equals to DEFAULT_CONTEXT
        defaultPortabilityShouldNotBeFound("context.notEquals=" + DEFAULT_CONTEXT);

        // Get all the portabilityList where context not equals to UPDATED_CONTEXT
        defaultPortabilityShouldBeFound("context.notEquals=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByContextIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where context in DEFAULT_CONTEXT or UPDATED_CONTEXT
        defaultPortabilityShouldBeFound("context.in=" + DEFAULT_CONTEXT + "," + UPDATED_CONTEXT);

        // Get all the portabilityList where context equals to UPDATED_CONTEXT
        defaultPortabilityShouldNotBeFound("context.in=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByContextIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where context is not null
        defaultPortabilityShouldBeFound("context.specified=true");

        // Get all the portabilityList where context is null
        defaultPortabilityShouldNotBeFound("context.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByContextContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where context contains DEFAULT_CONTEXT
        defaultPortabilityShouldBeFound("context.contains=" + DEFAULT_CONTEXT);

        // Get all the portabilityList where context contains UPDATED_CONTEXT
        defaultPortabilityShouldNotBeFound("context.contains=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByContextNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where context does not contain DEFAULT_CONTEXT
        defaultPortabilityShouldNotBeFound("context.doesNotContain=" + DEFAULT_CONTEXT);

        // Get all the portabilityList where context does not contain UPDATED_CONTEXT
        defaultPortabilityShouldBeFound("context.doesNotContain=" + UPDATED_CONTEXT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrCode equals to DEFAULT_POR_ERR_CODE
        defaultPortabilityShouldBeFound("porErrCode.equals=" + DEFAULT_POR_ERR_CODE);

        // Get all the portabilityList where porErrCode equals to UPDATED_POR_ERR_CODE
        defaultPortabilityShouldNotBeFound("porErrCode.equals=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrCode not equals to DEFAULT_POR_ERR_CODE
        defaultPortabilityShouldNotBeFound("porErrCode.notEquals=" + DEFAULT_POR_ERR_CODE);

        // Get all the portabilityList where porErrCode not equals to UPDATED_POR_ERR_CODE
        defaultPortabilityShouldBeFound("porErrCode.notEquals=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrCode in DEFAULT_POR_ERR_CODE or UPDATED_POR_ERR_CODE
        defaultPortabilityShouldBeFound("porErrCode.in=" + DEFAULT_POR_ERR_CODE + "," + UPDATED_POR_ERR_CODE);

        // Get all the portabilityList where porErrCode equals to UPDATED_POR_ERR_CODE
        defaultPortabilityShouldNotBeFound("porErrCode.in=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrCode is not null
        defaultPortabilityShouldBeFound("porErrCode.specified=true");

        // Get all the portabilityList where porErrCode is null
        defaultPortabilityShouldNotBeFound("porErrCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrCodeContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrCode contains DEFAULT_POR_ERR_CODE
        defaultPortabilityShouldBeFound("porErrCode.contains=" + DEFAULT_POR_ERR_CODE);

        // Get all the portabilityList where porErrCode contains UPDATED_POR_ERR_CODE
        defaultPortabilityShouldNotBeFound("porErrCode.contains=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrCodeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrCode does not contain DEFAULT_POR_ERR_CODE
        defaultPortabilityShouldNotBeFound("porErrCode.doesNotContain=" + DEFAULT_POR_ERR_CODE);

        // Get all the portabilityList where porErrCode does not contain UPDATED_POR_ERR_CODE
        defaultPortabilityShouldBeFound("porErrCode.doesNotContain=" + UPDATED_POR_ERR_CODE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrMessage equals to DEFAULT_POR_ERR_MESSAGE
        defaultPortabilityShouldBeFound("porErrMessage.equals=" + DEFAULT_POR_ERR_MESSAGE);

        // Get all the portabilityList where porErrMessage equals to UPDATED_POR_ERR_MESSAGE
        defaultPortabilityShouldNotBeFound("porErrMessage.equals=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrMessage not equals to DEFAULT_POR_ERR_MESSAGE
        defaultPortabilityShouldNotBeFound("porErrMessage.notEquals=" + DEFAULT_POR_ERR_MESSAGE);

        // Get all the portabilityList where porErrMessage not equals to UPDATED_POR_ERR_MESSAGE
        defaultPortabilityShouldBeFound("porErrMessage.notEquals=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrMessageIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrMessage in DEFAULT_POR_ERR_MESSAGE or UPDATED_POR_ERR_MESSAGE
        defaultPortabilityShouldBeFound("porErrMessage.in=" + DEFAULT_POR_ERR_MESSAGE + "," + UPDATED_POR_ERR_MESSAGE);

        // Get all the portabilityList where porErrMessage equals to UPDATED_POR_ERR_MESSAGE
        defaultPortabilityShouldNotBeFound("porErrMessage.in=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrMessage is not null
        defaultPortabilityShouldBeFound("porErrMessage.specified=true");

        // Get all the portabilityList where porErrMessage is null
        defaultPortabilityShouldNotBeFound("porErrMessage.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrMessageContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrMessage contains DEFAULT_POR_ERR_MESSAGE
        defaultPortabilityShouldBeFound("porErrMessage.contains=" + DEFAULT_POR_ERR_MESSAGE);

        // Get all the portabilityList where porErrMessage contains UPDATED_POR_ERR_MESSAGE
        defaultPortabilityShouldNotBeFound("porErrMessage.contains=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorErrMessageNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porErrMessage does not contain DEFAULT_POR_ERR_MESSAGE
        defaultPortabilityShouldNotBeFound("porErrMessage.doesNotContain=" + DEFAULT_POR_ERR_MESSAGE);

        // Get all the portabilityList where porErrMessage does not contain UPDATED_POR_ERR_MESSAGE
        defaultPortabilityShouldBeFound("porErrMessage.doesNotContain=" + UPDATED_POR_ERR_MESSAGE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpdIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpd equals to DEFAULT_POR_OPD
        defaultPortabilityShouldBeFound("porOpd.equals=" + DEFAULT_POR_OPD);

        // Get all the portabilityList where porOpd equals to UPDATED_POR_OPD
        defaultPortabilityShouldNotBeFound("porOpd.equals=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpd not equals to DEFAULT_POR_OPD
        defaultPortabilityShouldNotBeFound("porOpd.notEquals=" + DEFAULT_POR_OPD);

        // Get all the portabilityList where porOpd not equals to UPDATED_POR_OPD
        defaultPortabilityShouldBeFound("porOpd.notEquals=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpdIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpd in DEFAULT_POR_OPD or UPDATED_POR_OPD
        defaultPortabilityShouldBeFound("porOpd.in=" + DEFAULT_POR_OPD + "," + UPDATED_POR_OPD);

        // Get all the portabilityList where porOpd equals to UPDATED_POR_OPD
        defaultPortabilityShouldNotBeFound("porOpd.in=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpdIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpd is not null
        defaultPortabilityShouldBeFound("porOpd.specified=true");

        // Get all the portabilityList where porOpd is null
        defaultPortabilityShouldNotBeFound("porOpd.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpdContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpd contains DEFAULT_POR_OPD
        defaultPortabilityShouldBeFound("porOpd.contains=" + DEFAULT_POR_OPD);

        // Get all the portabilityList where porOpd contains UPDATED_POR_OPD
        defaultPortabilityShouldNotBeFound("porOpd.contains=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorOpdNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porOpd does not contain DEFAULT_POR_OPD
        defaultPortabilityShouldNotBeFound("porOpd.doesNotContain=" + DEFAULT_POR_OPD);

        // Get all the portabilityList where porOpd does not contain UPDATED_POR_OPD
        defaultPortabilityShouldBeFound("porOpd.doesNotContain=" + UPDATED_POR_OPD);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumType equals to DEFAULT_POR_NUM_TYPE
        defaultPortabilityShouldBeFound("porNumType.equals=" + DEFAULT_POR_NUM_TYPE);

        // Get all the portabilityList where porNumType equals to UPDATED_POR_NUM_TYPE
        defaultPortabilityShouldNotBeFound("porNumType.equals=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumType not equals to DEFAULT_POR_NUM_TYPE
        defaultPortabilityShouldNotBeFound("porNumType.notEquals=" + DEFAULT_POR_NUM_TYPE);

        // Get all the portabilityList where porNumType not equals to UPDATED_POR_NUM_TYPE
        defaultPortabilityShouldBeFound("porNumType.notEquals=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumTypeIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumType in DEFAULT_POR_NUM_TYPE or UPDATED_POR_NUM_TYPE
        defaultPortabilityShouldBeFound("porNumType.in=" + DEFAULT_POR_NUM_TYPE + "," + UPDATED_POR_NUM_TYPE);

        // Get all the portabilityList where porNumType equals to UPDATED_POR_NUM_TYPE
        defaultPortabilityShouldNotBeFound("porNumType.in=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumType is not null
        defaultPortabilityShouldBeFound("porNumType.specified=true");

        // Get all the portabilityList where porNumType is null
        defaultPortabilityShouldNotBeFound("porNumType.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumTypeContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumType contains DEFAULT_POR_NUM_TYPE
        defaultPortabilityShouldBeFound("porNumType.contains=" + DEFAULT_POR_NUM_TYPE);

        // Get all the portabilityList where porNumType contains UPDATED_POR_NUM_TYPE
        defaultPortabilityShouldNotBeFound("porNumType.contains=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNumTypeNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNumType does not contain DEFAULT_POR_NUM_TYPE
        defaultPortabilityShouldNotBeFound("porNumType.doesNotContain=" + DEFAULT_POR_NUM_TYPE);

        // Get all the portabilityList where porNumType does not contain UPDATED_POR_NUM_TYPE
        defaultPortabilityShouldBeFound("porNumType.doesNotContain=" + UPDATED_POR_NUM_TYPE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNote equals to DEFAULT_POR_NOTE
        defaultPortabilityShouldBeFound("porNote.equals=" + DEFAULT_POR_NOTE);

        // Get all the portabilityList where porNote equals to UPDATED_POR_NOTE
        defaultPortabilityShouldNotBeFound("porNote.equals=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNote not equals to DEFAULT_POR_NOTE
        defaultPortabilityShouldNotBeFound("porNote.notEquals=" + DEFAULT_POR_NOTE);

        // Get all the portabilityList where porNote not equals to UPDATED_POR_NOTE
        defaultPortabilityShouldBeFound("porNote.notEquals=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNoteIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNote in DEFAULT_POR_NOTE or UPDATED_POR_NOTE
        defaultPortabilityShouldBeFound("porNote.in=" + DEFAULT_POR_NOTE + "," + UPDATED_POR_NOTE);

        // Get all the portabilityList where porNote equals to UPDATED_POR_NOTE
        defaultPortabilityShouldNotBeFound("porNote.in=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNote is not null
        defaultPortabilityShouldBeFound("porNote.specified=true");

        // Get all the portabilityList where porNote is null
        defaultPortabilityShouldNotBeFound("porNote.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNoteContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNote contains DEFAULT_POR_NOTE
        defaultPortabilityShouldBeFound("porNote.contains=" + DEFAULT_POR_NOTE);

        // Get all the portabilityList where porNote contains UPDATED_POR_NOTE
        defaultPortabilityShouldNotBeFound("porNote.contains=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorNoteNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porNote does not contain DEFAULT_POR_NOTE
        defaultPortabilityShouldNotBeFound("porNote.doesNotContain=" + DEFAULT_POR_NOTE);

        // Get all the portabilityList where porNote does not contain UPDATED_POR_NOTE
        defaultPortabilityShouldBeFound("porNote.doesNotContain=" + UPDATED_POR_NOTE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorDeadlineIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porDeadline equals to DEFAULT_POR_DEADLINE
        defaultPortabilityShouldBeFound("porDeadline.equals=" + DEFAULT_POR_DEADLINE);

        // Get all the portabilityList where porDeadline equals to UPDATED_POR_DEADLINE
        defaultPortabilityShouldNotBeFound("porDeadline.equals=" + UPDATED_POR_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorDeadlineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porDeadline not equals to DEFAULT_POR_DEADLINE
        defaultPortabilityShouldNotBeFound("porDeadline.notEquals=" + DEFAULT_POR_DEADLINE);

        // Get all the portabilityList where porDeadline not equals to UPDATED_POR_DEADLINE
        defaultPortabilityShouldBeFound("porDeadline.notEquals=" + UPDATED_POR_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorDeadlineIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porDeadline in DEFAULT_POR_DEADLINE or UPDATED_POR_DEADLINE
        defaultPortabilityShouldBeFound("porDeadline.in=" + DEFAULT_POR_DEADLINE + "," + UPDATED_POR_DEADLINE);

        // Get all the portabilityList where porDeadline equals to UPDATED_POR_DEADLINE
        defaultPortabilityShouldNotBeFound("porDeadline.in=" + UPDATED_POR_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorDeadlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porDeadline is not null
        defaultPortabilityShouldBeFound("porDeadline.specified=true");

        // Get all the portabilityList where porDeadline is null
        defaultPortabilityShouldNotBeFound("porDeadline.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorResponseTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porResponseTimestamp equals to DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldBeFound("porResponseTimestamp.equals=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityList where porResponseTimestamp equals to UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldNotBeFound("porResponseTimestamp.equals=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorResponseTimestampIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porResponseTimestamp not equals to DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldNotBeFound("porResponseTimestamp.notEquals=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityList where porResponseTimestamp not equals to UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldBeFound("porResponseTimestamp.notEquals=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorResponseTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porResponseTimestamp in DEFAULT_POR_RESPONSE_TIMESTAMP or UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldBeFound("porResponseTimestamp.in=" + DEFAULT_POR_RESPONSE_TIMESTAMP + "," + UPDATED_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityList where porResponseTimestamp equals to UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldNotBeFound("porResponseTimestamp.in=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorResponseTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porResponseTimestamp is not null
        defaultPortabilityShouldBeFound("porResponseTimestamp.specified=true");

        // Get all the portabilityList where porResponseTimestamp is null
        defaultPortabilityShouldNotBeFound("porResponseTimestamp.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorResponseTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porResponseTimestamp is greater than or equal to DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldBeFound("porResponseTimestamp.greaterThanOrEqual=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityList where porResponseTimestamp is greater than or equal to UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldNotBeFound("porResponseTimestamp.greaterThanOrEqual=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorResponseTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porResponseTimestamp is less than or equal to DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldBeFound("porResponseTimestamp.lessThanOrEqual=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityList where porResponseTimestamp is less than or equal to SMALLER_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldNotBeFound("porResponseTimestamp.lessThanOrEqual=" + SMALLER_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorResponseTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porResponseTimestamp is less than DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldNotBeFound("porResponseTimestamp.lessThan=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityList where porResponseTimestamp is less than UPDATED_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldBeFound("porResponseTimestamp.lessThan=" + UPDATED_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorResponseTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porResponseTimestamp is greater than DEFAULT_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldNotBeFound("porResponseTimestamp.greaterThan=" + DEFAULT_POR_RESPONSE_TIMESTAMP);

        // Get all the portabilityList where porResponseTimestamp is greater than SMALLER_POR_RESPONSE_TIMESTAMP
        defaultPortabilityShouldBeFound("porResponseTimestamp.greaterThan=" + SMALLER_POR_RESPONSE_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorEligibleIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porEligible equals to DEFAULT_POR_ELIGIBLE
        defaultPortabilityShouldBeFound("porEligible.equals=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityList where porEligible equals to UPDATED_POR_ELIGIBLE
        defaultPortabilityShouldNotBeFound("porEligible.equals=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorEligibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porEligible not equals to DEFAULT_POR_ELIGIBLE
        defaultPortabilityShouldNotBeFound("porEligible.notEquals=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityList where porEligible not equals to UPDATED_POR_ELIGIBLE
        defaultPortabilityShouldBeFound("porEligible.notEquals=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorEligibleIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porEligible in DEFAULT_POR_ELIGIBLE or UPDATED_POR_ELIGIBLE
        defaultPortabilityShouldBeFound("porEligible.in=" + DEFAULT_POR_ELIGIBLE + "," + UPDATED_POR_ELIGIBLE);

        // Get all the portabilityList where porEligible equals to UPDATED_POR_ELIGIBLE
        defaultPortabilityShouldNotBeFound("porEligible.in=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorEligibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porEligible is not null
        defaultPortabilityShouldBeFound("porEligible.specified=true");

        // Get all the portabilityList where porEligible is null
        defaultPortabilityShouldNotBeFound("porEligible.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorEligibleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porEligible is greater than or equal to DEFAULT_POR_ELIGIBLE
        defaultPortabilityShouldBeFound("porEligible.greaterThanOrEqual=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityList where porEligible is greater than or equal to UPDATED_POR_ELIGIBLE
        defaultPortabilityShouldNotBeFound("porEligible.greaterThanOrEqual=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorEligibleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porEligible is less than or equal to DEFAULT_POR_ELIGIBLE
        defaultPortabilityShouldBeFound("porEligible.lessThanOrEqual=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityList where porEligible is less than or equal to SMALLER_POR_ELIGIBLE
        defaultPortabilityShouldNotBeFound("porEligible.lessThanOrEqual=" + SMALLER_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorEligibleIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porEligible is less than DEFAULT_POR_ELIGIBLE
        defaultPortabilityShouldNotBeFound("porEligible.lessThan=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityList where porEligible is less than UPDATED_POR_ELIGIBLE
        defaultPortabilityShouldBeFound("porEligible.lessThan=" + UPDATED_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorEligibleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porEligible is greater than DEFAULT_POR_ELIGIBLE
        defaultPortabilityShouldNotBeFound("porEligible.greaterThan=" + DEFAULT_POR_ELIGIBLE);

        // Get all the portabilityList where porEligible is greater than SMALLER_POR_ELIGIBLE
        defaultPortabilityShouldBeFound("porEligible.greaterThan=" + SMALLER_POR_ELIGIBLE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorBillingOkIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porBillingOk equals to DEFAULT_POR_BILLING_OK
        defaultPortabilityShouldBeFound("porBillingOk.equals=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityList where porBillingOk equals to UPDATED_POR_BILLING_OK
        defaultPortabilityShouldNotBeFound("porBillingOk.equals=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorBillingOkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porBillingOk not equals to DEFAULT_POR_BILLING_OK
        defaultPortabilityShouldNotBeFound("porBillingOk.notEquals=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityList where porBillingOk not equals to UPDATED_POR_BILLING_OK
        defaultPortabilityShouldBeFound("porBillingOk.notEquals=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorBillingOkIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porBillingOk in DEFAULT_POR_BILLING_OK or UPDATED_POR_BILLING_OK
        defaultPortabilityShouldBeFound("porBillingOk.in=" + DEFAULT_POR_BILLING_OK + "," + UPDATED_POR_BILLING_OK);

        // Get all the portabilityList where porBillingOk equals to UPDATED_POR_BILLING_OK
        defaultPortabilityShouldNotBeFound("porBillingOk.in=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorBillingOkIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porBillingOk is not null
        defaultPortabilityShouldBeFound("porBillingOk.specified=true");

        // Get all the portabilityList where porBillingOk is null
        defaultPortabilityShouldNotBeFound("porBillingOk.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorBillingOkIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porBillingOk is greater than or equal to DEFAULT_POR_BILLING_OK
        defaultPortabilityShouldBeFound("porBillingOk.greaterThanOrEqual=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityList where porBillingOk is greater than or equal to UPDATED_POR_BILLING_OK
        defaultPortabilityShouldNotBeFound("porBillingOk.greaterThanOrEqual=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorBillingOkIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porBillingOk is less than or equal to DEFAULT_POR_BILLING_OK
        defaultPortabilityShouldBeFound("porBillingOk.lessThanOrEqual=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityList where porBillingOk is less than or equal to SMALLER_POR_BILLING_OK
        defaultPortabilityShouldNotBeFound("porBillingOk.lessThanOrEqual=" + SMALLER_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorBillingOkIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porBillingOk is less than DEFAULT_POR_BILLING_OK
        defaultPortabilityShouldNotBeFound("porBillingOk.lessThan=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityList where porBillingOk is less than UPDATED_POR_BILLING_OK
        defaultPortabilityShouldBeFound("porBillingOk.lessThan=" + UPDATED_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorBillingOkIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porBillingOk is greater than DEFAULT_POR_BILLING_OK
        defaultPortabilityShouldNotBeFound("porBillingOk.greaterThan=" + DEFAULT_POR_BILLING_OK);

        // Get all the portabilityList where porBillingOk is greater than SMALLER_POR_BILLING_OK
        defaultPortabilityShouldBeFound("porBillingOk.greaterThan=" + SMALLER_POR_BILLING_OK);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByIntermediaryActionStateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where intermediaryActionState equals to DEFAULT_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldBeFound("intermediaryActionState.equals=" + DEFAULT_INTERMEDIARY_ACTION_STATE);

        // Get all the portabilityList where intermediaryActionState equals to UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldNotBeFound("intermediaryActionState.equals=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByIntermediaryActionStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where intermediaryActionState not equals to DEFAULT_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldNotBeFound("intermediaryActionState.notEquals=" + DEFAULT_INTERMEDIARY_ACTION_STATE);

        // Get all the portabilityList where intermediaryActionState not equals to UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldBeFound("intermediaryActionState.notEquals=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByIntermediaryActionStateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where intermediaryActionState in DEFAULT_INTERMEDIARY_ACTION_STATE or UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldBeFound(
            "intermediaryActionState.in=" + DEFAULT_INTERMEDIARY_ACTION_STATE + "," + UPDATED_INTERMEDIARY_ACTION_STATE
        );

        // Get all the portabilityList where intermediaryActionState equals to UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldNotBeFound("intermediaryActionState.in=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByIntermediaryActionStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where intermediaryActionState is not null
        defaultPortabilityShouldBeFound("intermediaryActionState.specified=true");

        // Get all the portabilityList where intermediaryActionState is null
        defaultPortabilityShouldNotBeFound("intermediaryActionState.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByIntermediaryActionStateContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where intermediaryActionState contains DEFAULT_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldBeFound("intermediaryActionState.contains=" + DEFAULT_INTERMEDIARY_ACTION_STATE);

        // Get all the portabilityList where intermediaryActionState contains UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldNotBeFound("intermediaryActionState.contains=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByIntermediaryActionStateNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where intermediaryActionState does not contain DEFAULT_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldNotBeFound("intermediaryActionState.doesNotContain=" + DEFAULT_INTERMEDIARY_ACTION_STATE);

        // Get all the portabilityList where intermediaryActionState does not contain UPDATED_INTERMEDIARY_ACTION_STATE
        defaultPortabilityShouldBeFound("intermediaryActionState.doesNotContain=" + UPDATED_INTERMEDIARY_ACTION_STATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCrDateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCrDate equals to DEFAULT_POR_CR_DATE
        defaultPortabilityShouldBeFound("porCrDate.equals=" + DEFAULT_POR_CR_DATE);

        // Get all the portabilityList where porCrDate equals to UPDATED_POR_CR_DATE
        defaultPortabilityShouldNotBeFound("porCrDate.equals=" + UPDATED_POR_CR_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCrDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCrDate not equals to DEFAULT_POR_CR_DATE
        defaultPortabilityShouldNotBeFound("porCrDate.notEquals=" + DEFAULT_POR_CR_DATE);

        // Get all the portabilityList where porCrDate not equals to UPDATED_POR_CR_DATE
        defaultPortabilityShouldBeFound("porCrDate.notEquals=" + UPDATED_POR_CR_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCrDateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCrDate in DEFAULT_POR_CR_DATE or UPDATED_POR_CR_DATE
        defaultPortabilityShouldBeFound("porCrDate.in=" + DEFAULT_POR_CR_DATE + "," + UPDATED_POR_CR_DATE);

        // Get all the portabilityList where porCrDate equals to UPDATED_POR_CR_DATE
        defaultPortabilityShouldNotBeFound("porCrDate.in=" + UPDATED_POR_CR_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorCrDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porCrDate is not null
        defaultPortabilityShouldBeFound("porCrDate.specified=true");

        // Get all the portabilityList where porCrDate is null
        defaultPortabilityShouldNotBeFound("porCrDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorUpdDateIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porUpdDate equals to DEFAULT_POR_UPD_DATE
        defaultPortabilityShouldBeFound("porUpdDate.equals=" + DEFAULT_POR_UPD_DATE);

        // Get all the portabilityList where porUpdDate equals to UPDATED_POR_UPD_DATE
        defaultPortabilityShouldNotBeFound("porUpdDate.equals=" + UPDATED_POR_UPD_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorUpdDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porUpdDate not equals to DEFAULT_POR_UPD_DATE
        defaultPortabilityShouldNotBeFound("porUpdDate.notEquals=" + DEFAULT_POR_UPD_DATE);

        // Get all the portabilityList where porUpdDate not equals to UPDATED_POR_UPD_DATE
        defaultPortabilityShouldBeFound("porUpdDate.notEquals=" + UPDATED_POR_UPD_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorUpdDateIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porUpdDate in DEFAULT_POR_UPD_DATE or UPDATED_POR_UPD_DATE
        defaultPortabilityShouldBeFound("porUpdDate.in=" + DEFAULT_POR_UPD_DATE + "," + UPDATED_POR_UPD_DATE);

        // Get all the portabilityList where porUpdDate equals to UPDATED_POR_UPD_DATE
        defaultPortabilityShouldNotBeFound("porUpdDate.in=" + UPDATED_POR_UPD_DATE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorUpdDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porUpdDate is not null
        defaultPortabilityShouldBeFound("porUpdDate.specified=true");

        // Get all the portabilityList where porUpdDate is null
        defaultPortabilityShouldNotBeFound("porUpdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechStatus equals to DEFAULT_POR_TECH_STATUS
        defaultPortabilityShouldBeFound("porTechStatus.equals=" + DEFAULT_POR_TECH_STATUS);

        // Get all the portabilityList where porTechStatus equals to UPDATED_POR_TECH_STATUS
        defaultPortabilityShouldNotBeFound("porTechStatus.equals=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechStatus not equals to DEFAULT_POR_TECH_STATUS
        defaultPortabilityShouldNotBeFound("porTechStatus.notEquals=" + DEFAULT_POR_TECH_STATUS);

        // Get all the portabilityList where porTechStatus not equals to UPDATED_POR_TECH_STATUS
        defaultPortabilityShouldBeFound("porTechStatus.notEquals=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechStatusIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechStatus in DEFAULT_POR_TECH_STATUS or UPDATED_POR_TECH_STATUS
        defaultPortabilityShouldBeFound("porTechStatus.in=" + DEFAULT_POR_TECH_STATUS + "," + UPDATED_POR_TECH_STATUS);

        // Get all the portabilityList where porTechStatus equals to UPDATED_POR_TECH_STATUS
        defaultPortabilityShouldNotBeFound("porTechStatus.in=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechStatus is not null
        defaultPortabilityShouldBeFound("porTechStatus.specified=true");

        // Get all the portabilityList where porTechStatus is null
        defaultPortabilityShouldNotBeFound("porTechStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechStatusContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechStatus contains DEFAULT_POR_TECH_STATUS
        defaultPortabilityShouldBeFound("porTechStatus.contains=" + DEFAULT_POR_TECH_STATUS);

        // Get all the portabilityList where porTechStatus contains UPDATED_POR_TECH_STATUS
        defaultPortabilityShouldNotBeFound("porTechStatus.contains=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechStatusNotContainsSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechStatus does not contain DEFAULT_POR_TECH_STATUS
        defaultPortabilityShouldNotBeFound("porTechStatus.doesNotContain=" + DEFAULT_POR_TECH_STATUS);

        // Get all the portabilityList where porTechStatus does not contain UPDATED_POR_TECH_STATUS
        defaultPortabilityShouldBeFound("porTechStatus.doesNotContain=" + UPDATED_POR_TECH_STATUS);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechDeadlineIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechDeadline equals to DEFAULT_POR_TECH_DEADLINE
        defaultPortabilityShouldBeFound("porTechDeadline.equals=" + DEFAULT_POR_TECH_DEADLINE);

        // Get all the portabilityList where porTechDeadline equals to UPDATED_POR_TECH_DEADLINE
        defaultPortabilityShouldNotBeFound("porTechDeadline.equals=" + UPDATED_POR_TECH_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechDeadlineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechDeadline not equals to DEFAULT_POR_TECH_DEADLINE
        defaultPortabilityShouldNotBeFound("porTechDeadline.notEquals=" + DEFAULT_POR_TECH_DEADLINE);

        // Get all the portabilityList where porTechDeadline not equals to UPDATED_POR_TECH_DEADLINE
        defaultPortabilityShouldBeFound("porTechDeadline.notEquals=" + UPDATED_POR_TECH_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechDeadlineIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechDeadline in DEFAULT_POR_TECH_DEADLINE or UPDATED_POR_TECH_DEADLINE
        defaultPortabilityShouldBeFound("porTechDeadline.in=" + DEFAULT_POR_TECH_DEADLINE + "," + UPDATED_POR_TECH_DEADLINE);

        // Get all the portabilityList where porTechDeadline equals to UPDATED_POR_TECH_DEADLINE
        defaultPortabilityShouldNotBeFound("porTechDeadline.in=" + UPDATED_POR_TECH_DEADLINE);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByPorTechDeadlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where porTechDeadline is not null
        defaultPortabilityShouldBeFound("porTechDeadline.specified=true");

        // Get all the portabilityList where porTechDeadline is null
        defaultPortabilityShouldNotBeFound("porTechDeadline.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByNeedManualRetryIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where needManualRetry equals to DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityShouldBeFound("needManualRetry.equals=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityList where needManualRetry equals to UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityShouldNotBeFound("needManualRetry.equals=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByNeedManualRetryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where needManualRetry not equals to DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityShouldNotBeFound("needManualRetry.notEquals=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityList where needManualRetry not equals to UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityShouldBeFound("needManualRetry.notEquals=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByNeedManualRetryIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where needManualRetry in DEFAULT_NEED_MANUAL_RETRY or UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityShouldBeFound("needManualRetry.in=" + DEFAULT_NEED_MANUAL_RETRY + "," + UPDATED_NEED_MANUAL_RETRY);

        // Get all the portabilityList where needManualRetry equals to UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityShouldNotBeFound("needManualRetry.in=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByNeedManualRetryIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where needManualRetry is not null
        defaultPortabilityShouldBeFound("needManualRetry.specified=true");

        // Get all the portabilityList where needManualRetry is null
        defaultPortabilityShouldNotBeFound("needManualRetry.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByNeedManualRetryIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where needManualRetry is greater than or equal to DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityShouldBeFound("needManualRetry.greaterThanOrEqual=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityList where needManualRetry is greater than or equal to UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityShouldNotBeFound("needManualRetry.greaterThanOrEqual=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByNeedManualRetryIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where needManualRetry is less than or equal to DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityShouldBeFound("needManualRetry.lessThanOrEqual=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityList where needManualRetry is less than or equal to SMALLER_NEED_MANUAL_RETRY
        defaultPortabilityShouldNotBeFound("needManualRetry.lessThanOrEqual=" + SMALLER_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByNeedManualRetryIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where needManualRetry is less than DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityShouldNotBeFound("needManualRetry.lessThan=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityList where needManualRetry is less than UPDATED_NEED_MANUAL_RETRY
        defaultPortabilityShouldBeFound("needManualRetry.lessThan=" + UPDATED_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByNeedManualRetryIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where needManualRetry is greater than DEFAULT_NEED_MANUAL_RETRY
        defaultPortabilityShouldNotBeFound("needManualRetry.greaterThan=" + DEFAULT_NEED_MANUAL_RETRY);

        // Get all the portabilityList where needManualRetry is greater than SMALLER_NEED_MANUAL_RETRY
        defaultPortabilityShouldBeFound("needManualRetry.greaterThan=" + SMALLER_NEED_MANUAL_RETRY);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRefPorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where refPorId equals to DEFAULT_REF_POR_ID
        defaultPortabilityShouldBeFound("refPorId.equals=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityList where refPorId equals to UPDATED_REF_POR_ID
        defaultPortabilityShouldNotBeFound("refPorId.equals=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRefPorIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where refPorId not equals to DEFAULT_REF_POR_ID
        defaultPortabilityShouldNotBeFound("refPorId.notEquals=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityList where refPorId not equals to UPDATED_REF_POR_ID
        defaultPortabilityShouldBeFound("refPorId.notEquals=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRefPorIdIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where refPorId in DEFAULT_REF_POR_ID or UPDATED_REF_POR_ID
        defaultPortabilityShouldBeFound("refPorId.in=" + DEFAULT_REF_POR_ID + "," + UPDATED_REF_POR_ID);

        // Get all the portabilityList where refPorId equals to UPDATED_REF_POR_ID
        defaultPortabilityShouldNotBeFound("refPorId.in=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRefPorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where refPorId is not null
        defaultPortabilityShouldBeFound("refPorId.specified=true");

        // Get all the portabilityList where refPorId is null
        defaultPortabilityShouldNotBeFound("refPorId.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRefPorIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where refPorId is greater than or equal to DEFAULT_REF_POR_ID
        defaultPortabilityShouldBeFound("refPorId.greaterThanOrEqual=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityList where refPorId is greater than or equal to UPDATED_REF_POR_ID
        defaultPortabilityShouldNotBeFound("refPorId.greaterThanOrEqual=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRefPorIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where refPorId is less than or equal to DEFAULT_REF_POR_ID
        defaultPortabilityShouldBeFound("refPorId.lessThanOrEqual=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityList where refPorId is less than or equal to SMALLER_REF_POR_ID
        defaultPortabilityShouldNotBeFound("refPorId.lessThanOrEqual=" + SMALLER_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRefPorIdIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where refPorId is less than DEFAULT_REF_POR_ID
        defaultPortabilityShouldNotBeFound("refPorId.lessThan=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityList where refPorId is less than UPDATED_REF_POR_ID
        defaultPortabilityShouldBeFound("refPorId.lessThan=" + UPDATED_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRefPorIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where refPorId is greater than DEFAULT_REF_POR_ID
        defaultPortabilityShouldNotBeFound("refPorId.greaterThan=" + DEFAULT_REF_POR_ID);

        // Get all the portabilityList where refPorId is greater than SMALLER_REF_POR_ID
        defaultPortabilityShouldBeFound("refPorId.greaterThan=" + SMALLER_REF_POR_ID);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRetryCountIsEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where retryCount equals to DEFAULT_RETRY_COUNT
        defaultPortabilityShouldBeFound("retryCount.equals=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityList where retryCount equals to UPDATED_RETRY_COUNT
        defaultPortabilityShouldNotBeFound("retryCount.equals=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRetryCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where retryCount not equals to DEFAULT_RETRY_COUNT
        defaultPortabilityShouldNotBeFound("retryCount.notEquals=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityList where retryCount not equals to UPDATED_RETRY_COUNT
        defaultPortabilityShouldBeFound("retryCount.notEquals=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRetryCountIsInShouldWork() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where retryCount in DEFAULT_RETRY_COUNT or UPDATED_RETRY_COUNT
        defaultPortabilityShouldBeFound("retryCount.in=" + DEFAULT_RETRY_COUNT + "," + UPDATED_RETRY_COUNT);

        // Get all the portabilityList where retryCount equals to UPDATED_RETRY_COUNT
        defaultPortabilityShouldNotBeFound("retryCount.in=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRetryCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where retryCount is not null
        defaultPortabilityShouldBeFound("retryCount.specified=true");

        // Get all the portabilityList where retryCount is null
        defaultPortabilityShouldNotBeFound("retryCount.specified=false");
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRetryCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where retryCount is greater than or equal to DEFAULT_RETRY_COUNT
        defaultPortabilityShouldBeFound("retryCount.greaterThanOrEqual=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityList where retryCount is greater than or equal to UPDATED_RETRY_COUNT
        defaultPortabilityShouldNotBeFound("retryCount.greaterThanOrEqual=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRetryCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where retryCount is less than or equal to DEFAULT_RETRY_COUNT
        defaultPortabilityShouldBeFound("retryCount.lessThanOrEqual=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityList where retryCount is less than or equal to SMALLER_RETRY_COUNT
        defaultPortabilityShouldNotBeFound("retryCount.lessThanOrEqual=" + SMALLER_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRetryCountIsLessThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where retryCount is less than DEFAULT_RETRY_COUNT
        defaultPortabilityShouldNotBeFound("retryCount.lessThan=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityList where retryCount is less than UPDATED_RETRY_COUNT
        defaultPortabilityShouldBeFound("retryCount.lessThan=" + UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void getAllPortabilitiesByRetryCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        // Get all the portabilityList where retryCount is greater than DEFAULT_RETRY_COUNT
        defaultPortabilityShouldNotBeFound("retryCount.greaterThan=" + DEFAULT_RETRY_COUNT);

        // Get all the portabilityList where retryCount is greater than SMALLER_RETRY_COUNT
        defaultPortabilityShouldBeFound("retryCount.greaterThan=" + SMALLER_RETRY_COUNT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPortabilityShouldBeFound(String filter) throws Exception {
        restPortabilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portabilityEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].porRequestId").value(hasItem(DEFAULT_POR_REQUEST_ID)))
            .andExpect(jsonPath("$.[*].porNumber").value(hasItem(DEFAULT_POR_NUMBER)))
            .andExpect(jsonPath("$.[*].porLegalTerm").value(hasItem(DEFAULT_POR_LEGAL_TERM)))
            .andExpect(jsonPath("$.[*].porOpr").value(hasItem(DEFAULT_POR_OPR)))
            .andExpect(jsonPath("$.[*].porAccType").value(hasItem(DEFAULT_POR_ACC_TYPE)))
            .andExpect(jsonPath("$.[*].porIdNumber").value(hasItem(DEFAULT_POR_ID_NUMBER)))
            .andExpect(jsonPath("$.[*].porContactNumber").value(hasItem(DEFAULT_POR_CONTACT_NUMBER)))
            .andExpect(jsonPath("$.[*].porStatus").value(hasItem(DEFAULT_POR_STATUS)))
            .andExpect(jsonPath("$.[*].porPortedDate").value(hasItem(jsonDate(DEFAULT_POR_PORTED_DATE))))
            .andExpect(jsonPath("$.[*].porRouting").value(hasItem(DEFAULT_POR_ROUTING)))
            .andExpect(jsonPath("$.[*].porType").value(hasItem(DEFAULT_POR_TYPE)))
            .andExpect(jsonPath("$.[*].porOpOrg").value(hasItem(DEFAULT_POR_OP_ORG)))
            .andExpect(jsonPath("$.[*].porRspCode").value(hasItem(DEFAULT_POR_RSP_CODE)))
            .andExpect(jsonPath("$.[*].porRspNote").value(hasItem(DEFAULT_POR_RSP_NOTE)))
            .andExpect(jsonPath("$.[*].porCancelNote").value(hasItem(DEFAULT_POR_CANCEL_NOTE)))
            .andExpect(jsonPath("$.[*].porMnpid").value(hasItem(DEFAULT_POR_MNPID)))
            .andExpect(jsonPath("$.[*].portationDate").value(hasItem(jsonDate(DEFAULT_PORTATION_DATE))))
            .andExpect(jsonPath("$.[*].portaCode").value(hasItem(DEFAULT_PORTA_CODE)))
            .andExpect(jsonPath("$.[*].mvno").value(hasItem(DEFAULT_MVNO)))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].porErrCode").value(hasItem(DEFAULT_POR_ERR_CODE)))
            .andExpect(jsonPath("$.[*].porErrMessage").value(hasItem(DEFAULT_POR_ERR_MESSAGE)))
            .andExpect(jsonPath("$.[*].porOpd").value(hasItem(DEFAULT_POR_OPD)))
            .andExpect(jsonPath("$.[*].porNumType").value(hasItem(DEFAULT_POR_NUM_TYPE)))
            .andExpect(jsonPath("$.[*].porNote").value(hasItem(DEFAULT_POR_NOTE)))
            .andExpect(jsonPath("$.[*].porDeadline").value(hasItem(jsonDate(DEFAULT_POR_DEADLINE))))
            .andExpect(jsonPath("$.[*].porResponseTimestamp").value(hasItem(DEFAULT_POR_RESPONSE_TIMESTAMP.intValue())))
            .andExpect(jsonPath("$.[*].porEligible").value(hasItem(DEFAULT_POR_ELIGIBLE)))
            .andExpect(jsonPath("$.[*].porBillingOk").value(hasItem(DEFAULT_POR_BILLING_OK)))
            .andExpect(jsonPath("$.[*].intermediaryActionState").value(hasItem(DEFAULT_INTERMEDIARY_ACTION_STATE)))
            .andExpect(jsonPath("$.[*].porCrDate").value(hasItem(jsonDate(DEFAULT_POR_CR_DATE))))
            .andExpect(jsonPath("$.[*].porUpdDate").value(hasItem(jsonDate(DEFAULT_POR_UPD_DATE))))
            .andExpect(jsonPath("$.[*].porTechStatus").value(hasItem(DEFAULT_POR_TECH_STATUS)))
            .andExpect(jsonPath("$.[*].porTechDeadline").value(hasItem(jsonDate(DEFAULT_POR_TECH_DEADLINE))))
            .andExpect(jsonPath("$.[*].needManualRetry").value(hasItem(DEFAULT_NEED_MANUAL_RETRY)))
            .andExpect(jsonPath("$.[*].refPorId").value(hasItem(DEFAULT_REF_POR_ID.intValue())))
            .andExpect(jsonPath("$.[*].retryCount").value(hasItem(DEFAULT_RETRY_COUNT)));

        // Check, that the count call also returns 1
        restPortabilityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPortabilityShouldNotBeFound(String filter) throws Exception {
        restPortabilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPortabilityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPortability() throws Exception {
        // Get the portability
        restPortabilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPortability() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        int databaseSizeBeforeUpdate = portabilityRepository.findAll().size();

        // Update the portability
        PortabilityEntity updatedPortabilityEntity = portabilityRepository.findById(portabilityEntity.getId()).get();
        // Disconnect from session so that the updates on updatedPortabilityEntity are not directly saved in db
        em.detach(updatedPortabilityEntity);
        updatedPortabilityEntity
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
            .needManualRetry(UPDATED_NEED_MANUAL_RETRY)
            .refPorId(UPDATED_REF_POR_ID)
            .retryCount(UPDATED_RETRY_COUNT);
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(updatedPortabilityEntity);

        restPortabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portabilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portabilityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeUpdate);
        PortabilityEntity testPortability = portabilityList.get(portabilityList.size() - 1);
        assertThat(testPortability.getPorRequestId()).isEqualTo(UPDATED_POR_REQUEST_ID);
        assertThat(testPortability.getPorNumber()).isEqualTo(UPDATED_POR_NUMBER);
        assertThat(testPortability.getPorLegalTerm()).isEqualTo(UPDATED_POR_LEGAL_TERM);
        assertThat(testPortability.getPorOpr()).isEqualTo(UPDATED_POR_OPR);
        assertThat(testPortability.getPorAccType()).isEqualTo(UPDATED_POR_ACC_TYPE);
        assertThat(testPortability.getPorIdNumber()).isEqualTo(UPDATED_POR_ID_NUMBER);
        assertThat(testPortability.getPorContactNumber()).isEqualTo(UPDATED_POR_CONTACT_NUMBER);
        assertThat(testPortability.getPorStatus()).isEqualTo(UPDATED_POR_STATUS);
        assertThat(testPortability.getPorPortedDate()).isEqualTo(UPDATED_POR_PORTED_DATE);
        assertThat(testPortability.getPorRouting()).isEqualTo(UPDATED_POR_ROUTING);
        assertThat(testPortability.getPorType()).isEqualTo(UPDATED_POR_TYPE);
        assertThat(testPortability.getPorOpOrg()).isEqualTo(UPDATED_POR_OP_ORG);
        assertThat(testPortability.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testPortability.getPorRspNote()).isEqualTo(UPDATED_POR_RSP_NOTE);
        assertThat(testPortability.getPorCancelNote()).isEqualTo(UPDATED_POR_CANCEL_NOTE);
        assertThat(testPortability.getPorMnpid()).isEqualTo(UPDATED_POR_MNPID);
        assertThat(testPortability.getPortationDate()).isEqualTo(UPDATED_PORTATION_DATE);
        assertThat(testPortability.getPortaCode()).isEqualTo(UPDATED_PORTA_CODE);
        assertThat(testPortability.getMvno()).isEqualTo(UPDATED_MVNO);
        assertThat(testPortability.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testPortability.getPorErrCode()).isEqualTo(UPDATED_POR_ERR_CODE);
        assertThat(testPortability.getPorErrMessage()).isEqualTo(UPDATED_POR_ERR_MESSAGE);
        assertThat(testPortability.getPorOpd()).isEqualTo(UPDATED_POR_OPD);
        assertThat(testPortability.getPorNumType()).isEqualTo(UPDATED_POR_NUM_TYPE);
        assertThat(testPortability.getPorNote()).isEqualTo(UPDATED_POR_NOTE);
        assertThat(testPortability.getPorDeadline()).isEqualTo(UPDATED_POR_DEADLINE);
        assertThat(testPortability.getPorResponseTimestamp()).isEqualTo(UPDATED_POR_RESPONSE_TIMESTAMP);
        assertThat(testPortability.getPorEligible()).isEqualTo(UPDATED_POR_ELIGIBLE);
        assertThat(testPortability.getPorBillingOk()).isEqualTo(UPDATED_POR_BILLING_OK);
        assertThat(testPortability.getIntermediaryActionState()).isEqualTo(UPDATED_INTERMEDIARY_ACTION_STATE);
        assertThat(testPortability.getPorCrDate()).isEqualTo(UPDATED_POR_CR_DATE);
        assertThat(testPortability.getPorUpdDate()).isEqualTo(UPDATED_POR_UPD_DATE);
        assertThat(testPortability.getPorTechStatus()).isEqualTo(UPDATED_POR_TECH_STATUS);
        assertThat(testPortability.getPorTechDeadline()).isEqualTo(UPDATED_POR_TECH_DEADLINE);
        assertThat(testPortability.getNeedManualRetry()).isEqualTo(UPDATED_NEED_MANUAL_RETRY);
        assertThat(testPortability.getRefPorId()).isEqualTo(UPDATED_REF_POR_ID);
        assertThat(testPortability.getRetryCount()).isEqualTo(UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingPortability() throws Exception {
        int databaseSizeBeforeUpdate = portabilityRepository.findAll().size();
        portabilityEntity.setId(count.incrementAndGet());

        // Create the Portability
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(portabilityEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, portabilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPortability() throws Exception {
        int databaseSizeBeforeUpdate = portabilityRepository.findAll().size();
        portabilityEntity.setId(count.incrementAndGet());

        // Create the Portability
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(portabilityEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortabilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(portabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPortability() throws Exception {
        int databaseSizeBeforeUpdate = portabilityRepository.findAll().size();
        portabilityEntity.setId(count.incrementAndGet());

        // Create the Portability
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(portabilityEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortabilityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(portabilityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePortabilityWithPatch() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        int databaseSizeBeforeUpdate = portabilityRepository.findAll().size();

        // Update the portability using partial update
        PortabilityEntity partialUpdatedPortabilityEntity = new PortabilityEntity();
        partialUpdatedPortabilityEntity.setId(portabilityEntity.getId());

        partialUpdatedPortabilityEntity
            .porRequestId(UPDATED_POR_REQUEST_ID)
            .porLegalTerm(UPDATED_POR_LEGAL_TERM)
            .porAccType(UPDATED_POR_ACC_TYPE)
            .porIdNumber(UPDATED_POR_ID_NUMBER)
            .porPortedDate(UPDATED_POR_PORTED_DATE)
            .porRouting(UPDATED_POR_ROUTING)
            .porType(UPDATED_POR_TYPE)
            .porMnpid(UPDATED_POR_MNPID)
            .portationDate(UPDATED_PORTATION_DATE)
            .portaCode(UPDATED_PORTA_CODE)
            .porErrCode(UPDATED_POR_ERR_CODE)
            .porNumType(UPDATED_POR_NUM_TYPE)
            .porNote(UPDATED_POR_NOTE)
            .intermediaryActionState(UPDATED_INTERMEDIARY_ACTION_STATE)
            .porCrDate(UPDATED_POR_CR_DATE);

        restPortabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortabilityEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortabilityEntity))
            )
            .andExpect(status().isOk());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeUpdate);
        PortabilityEntity testPortability = portabilityList.get(portabilityList.size() - 1);
        assertThat(testPortability.getPorRequestId()).isEqualTo(UPDATED_POR_REQUEST_ID);
        assertThat(testPortability.getPorNumber()).isEqualTo(DEFAULT_POR_NUMBER);
        assertThat(testPortability.getPorLegalTerm()).isEqualTo(UPDATED_POR_LEGAL_TERM);
        assertThat(testPortability.getPorOpr()).isEqualTo(DEFAULT_POR_OPR);
        assertThat(testPortability.getPorAccType()).isEqualTo(UPDATED_POR_ACC_TYPE);
        assertThat(testPortability.getPorIdNumber()).isEqualTo(UPDATED_POR_ID_NUMBER);
        assertThat(testPortability.getPorContactNumber()).isEqualTo(DEFAULT_POR_CONTACT_NUMBER);
        assertThat(testPortability.getPorStatus()).isEqualTo(DEFAULT_POR_STATUS);
        assertThat(testPortability.getPorPortedDate()).isEqualTo(UPDATED_POR_PORTED_DATE);
        assertThat(testPortability.getPorRouting()).isEqualTo(UPDATED_POR_ROUTING);
        assertThat(testPortability.getPorType()).isEqualTo(UPDATED_POR_TYPE);
        assertThat(testPortability.getPorOpOrg()).isEqualTo(DEFAULT_POR_OP_ORG);
        assertThat(testPortability.getPorRspCode()).isEqualTo(DEFAULT_POR_RSP_CODE);
        assertThat(testPortability.getPorRspNote()).isEqualTo(DEFAULT_POR_RSP_NOTE);
        assertThat(testPortability.getPorCancelNote()).isEqualTo(DEFAULT_POR_CANCEL_NOTE);
        assertThat(testPortability.getPorMnpid()).isEqualTo(UPDATED_POR_MNPID);
        assertThat(testPortability.getPortationDate()).isEqualTo(UPDATED_PORTATION_DATE);
        assertThat(testPortability.getPortaCode()).isEqualTo(UPDATED_PORTA_CODE);
        assertThat(testPortability.getMvno()).isEqualTo(DEFAULT_MVNO);
        assertThat(testPortability.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testPortability.getPorErrCode()).isEqualTo(UPDATED_POR_ERR_CODE);
        assertThat(testPortability.getPorErrMessage()).isEqualTo(DEFAULT_POR_ERR_MESSAGE);
        assertThat(testPortability.getPorOpd()).isEqualTo(DEFAULT_POR_OPD);
        assertThat(testPortability.getPorNumType()).isEqualTo(UPDATED_POR_NUM_TYPE);
        assertThat(testPortability.getPorNote()).isEqualTo(UPDATED_POR_NOTE);
        assertThat(testPortability.getPorDeadline()).isEqualTo(DEFAULT_POR_DEADLINE);
        assertThat(testPortability.getPorResponseTimestamp()).isEqualTo(DEFAULT_POR_RESPONSE_TIMESTAMP);
        assertThat(testPortability.getPorEligible()).isEqualTo(DEFAULT_POR_ELIGIBLE);
        assertThat(testPortability.getPorBillingOk()).isEqualTo(DEFAULT_POR_BILLING_OK);
        assertThat(testPortability.getIntermediaryActionState()).isEqualTo(UPDATED_INTERMEDIARY_ACTION_STATE);
        assertThat(testPortability.getPorCrDate()).isEqualTo(UPDATED_POR_CR_DATE);
        assertThat(testPortability.getPorUpdDate()).isEqualTo(DEFAULT_POR_UPD_DATE);
        assertThat(testPortability.getPorTechStatus()).isEqualTo(DEFAULT_POR_TECH_STATUS);
        assertThat(testPortability.getPorTechDeadline()).isEqualTo(DEFAULT_POR_TECH_DEADLINE);
        assertThat(testPortability.getNeedManualRetry()).isEqualTo(DEFAULT_NEED_MANUAL_RETRY);
        assertThat(testPortability.getRefPorId()).isEqualTo(DEFAULT_REF_POR_ID);
        assertThat(testPortability.getRetryCount()).isEqualTo(DEFAULT_RETRY_COUNT);
    }

    @Test
    @Transactional
    void fullUpdatePortabilityWithPatch() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        int databaseSizeBeforeUpdate = portabilityRepository.findAll().size();

        // Update the portability using partial update
        PortabilityEntity partialUpdatedPortabilityEntity = new PortabilityEntity();
        partialUpdatedPortabilityEntity.setId(portabilityEntity.getId());

        partialUpdatedPortabilityEntity
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
            .needManualRetry(UPDATED_NEED_MANUAL_RETRY)
            .refPorId(UPDATED_REF_POR_ID)
            .retryCount(UPDATED_RETRY_COUNT);

        restPortabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPortabilityEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPortabilityEntity))
            )
            .andExpect(status().isOk());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeUpdate);
        PortabilityEntity testPortability = portabilityList.get(portabilityList.size() - 1);
        assertThat(testPortability.getPorRequestId()).isEqualTo(UPDATED_POR_REQUEST_ID);
        assertThat(testPortability.getPorNumber()).isEqualTo(UPDATED_POR_NUMBER);
        assertThat(testPortability.getPorLegalTerm()).isEqualTo(UPDATED_POR_LEGAL_TERM);
        assertThat(testPortability.getPorOpr()).isEqualTo(UPDATED_POR_OPR);
        assertThat(testPortability.getPorAccType()).isEqualTo(UPDATED_POR_ACC_TYPE);
        assertThat(testPortability.getPorIdNumber()).isEqualTo(UPDATED_POR_ID_NUMBER);
        assertThat(testPortability.getPorContactNumber()).isEqualTo(UPDATED_POR_CONTACT_NUMBER);
        assertThat(testPortability.getPorStatus()).isEqualTo(UPDATED_POR_STATUS);
        assertThat(testPortability.getPorPortedDate()).isEqualTo(UPDATED_POR_PORTED_DATE);
        assertThat(testPortability.getPorRouting()).isEqualTo(UPDATED_POR_ROUTING);
        assertThat(testPortability.getPorType()).isEqualTo(UPDATED_POR_TYPE);
        assertThat(testPortability.getPorOpOrg()).isEqualTo(UPDATED_POR_OP_ORG);
        assertThat(testPortability.getPorRspCode()).isEqualTo(UPDATED_POR_RSP_CODE);
        assertThat(testPortability.getPorRspNote()).isEqualTo(UPDATED_POR_RSP_NOTE);
        assertThat(testPortability.getPorCancelNote()).isEqualTo(UPDATED_POR_CANCEL_NOTE);
        assertThat(testPortability.getPorMnpid()).isEqualTo(UPDATED_POR_MNPID);
        assertThat(testPortability.getPortationDate()).isEqualTo(UPDATED_PORTATION_DATE);
        assertThat(testPortability.getPortaCode()).isEqualTo(UPDATED_PORTA_CODE);
        assertThat(testPortability.getMvno()).isEqualTo(UPDATED_MVNO);
        assertThat(testPortability.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testPortability.getPorErrCode()).isEqualTo(UPDATED_POR_ERR_CODE);
        assertThat(testPortability.getPorErrMessage()).isEqualTo(UPDATED_POR_ERR_MESSAGE);
        assertThat(testPortability.getPorOpd()).isEqualTo(UPDATED_POR_OPD);
        assertThat(testPortability.getPorNumType()).isEqualTo(UPDATED_POR_NUM_TYPE);
        assertThat(testPortability.getPorNote()).isEqualTo(UPDATED_POR_NOTE);
        assertThat(testPortability.getPorDeadline()).isEqualTo(UPDATED_POR_DEADLINE);
        assertThat(testPortability.getPorResponseTimestamp()).isEqualTo(UPDATED_POR_RESPONSE_TIMESTAMP);
        assertThat(testPortability.getPorEligible()).isEqualTo(UPDATED_POR_ELIGIBLE);
        assertThat(testPortability.getPorBillingOk()).isEqualTo(UPDATED_POR_BILLING_OK);
        assertThat(testPortability.getIntermediaryActionState()).isEqualTo(UPDATED_INTERMEDIARY_ACTION_STATE);
        assertThat(testPortability.getPorCrDate()).isEqualTo(UPDATED_POR_CR_DATE);
        assertThat(testPortability.getPorUpdDate()).isEqualTo(UPDATED_POR_UPD_DATE);
        assertThat(testPortability.getPorTechStatus()).isEqualTo(UPDATED_POR_TECH_STATUS);
        assertThat(testPortability.getPorTechDeadline()).isEqualTo(UPDATED_POR_TECH_DEADLINE);
        assertThat(testPortability.getNeedManualRetry()).isEqualTo(UPDATED_NEED_MANUAL_RETRY);
        assertThat(testPortability.getRefPorId()).isEqualTo(UPDATED_REF_POR_ID);
        assertThat(testPortability.getRetryCount()).isEqualTo(UPDATED_RETRY_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingPortability() throws Exception {
        int databaseSizeBeforeUpdate = portabilityRepository.findAll().size();
        portabilityEntity.setId(count.incrementAndGet());

        // Create the Portability
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(portabilityEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPortabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, portabilityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPortability() throws Exception {
        int databaseSizeBeforeUpdate = portabilityRepository.findAll().size();
        portabilityEntity.setId(count.incrementAndGet());

        // Create the Portability
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(portabilityEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortabilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(portabilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPortability() throws Exception {
        int databaseSizeBeforeUpdate = portabilityRepository.findAll().size();
        portabilityEntity.setId(count.incrementAndGet());

        // Create the Portability
        PortabilityDTO portabilityDTO = portabilityMapper.toDto(portabilityEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPortabilityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(portabilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Portability in the database
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePortability() throws Exception {
        // Initialize the database
        portabilityRepository.saveAndFlush(portabilityEntity);

        int databaseSizeBeforeDelete = portabilityRepository.findAll().size();

        // Delete the portability
        restPortabilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, portabilityEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PortabilityEntity> portabilityList = portabilityRepository.findAll();
        assertThat(portabilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
