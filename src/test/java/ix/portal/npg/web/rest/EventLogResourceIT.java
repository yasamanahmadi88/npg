/*
package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.EventLogEntity;
import ix.portal.npg.repository.EventLogRepository;
import ix.portal.npg.service.criteria.EventLogCriteria;
import ix.portal.npg.service.dto.EventLogDTO;
import ix.portal.npg.service.mapper.EventLogMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

*/
/**
 * Integration tests for the {@link EventLogResource} REST controller.
 *//*

@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "eventLog" })class EventLogResourceIT {

    private static final String DEFAULT_CONVERSATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONVERSATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SENDER = "AAAAAAAAAA";
    private static final String UPDATED_SENDER = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_REQUEST_BODY = "AAAAAAAAAA";
    private static final String UPDATED_REQUEST_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_BODY = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_BODY = "BBBBBBBBBB";

    private static final Integer DEFAULT_FLG_0_ORDINARY_1_EXCEPTION = 1;
    private static final Integer UPDATED_FLG_0_ORDINARY_1_EXCEPTION = 2;
    private static final Integer SMALLER_FLG_0_ORDINARY_1_EXCEPTION = 1 - 1;

    private static final String DEFAULT_EVENT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCEPTION_BODY = "AAAAAAAAAA";
    private static final String UPDATED_EXCEPTION_BODY = "BBBBBBBBBB";

    private static final String DEFAULT_HTTP_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_HTTP_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_INSERT_TIMESTAMP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSERT_TIMESTAMP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/event-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventLogRepository eventLogRepository;

    @Autowired
    private EventLogMapper eventLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventLogMockMvc;

    private EventLogEntity eventLogEntity;

    */
/**
 * Create an entity for this test.
 *
 * This is a static method, as tests for other entities might also need it,
 * if they test an entity which requires the current entity.
 *//*

    public static EventLogEntity createEntity(EntityManager em) {
        EventLogEntity eventLogEntity = new EventLogEntity()
            .conversationId(DEFAULT_CONVERSATION_ID)
            .sender(DEFAULT_SENDER)
            .receiver(DEFAULT_RECEIVER)
            .message(DEFAULT_MESSAGE)
            .requestBody(DEFAULT_REQUEST_BODY)
            .responseBody(DEFAULT_RESPONSE_BODY)
            .flg0Ordinary1Exception(DEFAULT_FLG_0_ORDINARY_1_EXCEPTION)
            .eventSource(DEFAULT_EVENT_SOURCE)
            .exceptionBody(DEFAULT_EXCEPTION_BODY)
            .httpStatus(DEFAULT_HTTP_STATUS)
            .insertTimestamp(DEFAULT_INSERT_TIMESTAMP);
        return eventLogEntity;
    }

    */
/**
 * Create an updated entity for this test.
 *
 * This is a static method, as tests for other entities might also need it,
 * if they test an entity which requires the current entity.
 *//*

    public static EventLogEntity createUpdatedEntity(EntityManager em) {
        EventLogEntity eventLogEntity = new EventLogEntity()
            .conversationId(UPDATED_CONVERSATION_ID)
            .sender(UPDATED_SENDER)
            .receiver(UPDATED_RECEIVER)
            .message(UPDATED_MESSAGE)
            .requestBody(UPDATED_REQUEST_BODY)
            .responseBody(UPDATED_RESPONSE_BODY)
            .flg0Ordinary1Exception(UPDATED_FLG_0_ORDINARY_1_EXCEPTION)
            .eventSource(UPDATED_EVENT_SOURCE)
            .exceptionBody(UPDATED_EXCEPTION_BODY)
            .httpStatus(UPDATED_HTTP_STATUS)
            .insertTimestamp(UPDATED_INSERT_TIMESTAMP);
        return eventLogEntity;
    }

    @BeforeEach
    public void initTest() {
eventLogEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createEventLog() throws Exception {
        int databaseSizeBeforeCreate = eventLogRepository.findAll().size();
        // Create the EventLog
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);
        restEventLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventLogDTO)))
            .andExpect(status().isCreated());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeCreate + 1);
        EventLogEntity testEventLog = eventLogList.get(eventLogList.size() - 1);
        assertThat(testEventLog.getConversationId()).isEqualTo(DEFAULT_CONVERSATION_ID);
        assertThat(testEventLog.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testEventLog.getReceiver()).isEqualTo(DEFAULT_RECEIVER);
        assertThat(testEventLog.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEventLog.getRequestBody()).isEqualTo(DEFAULT_REQUEST_BODY);
        assertThat(testEventLog.getResponseBody()).isEqualTo(DEFAULT_RESPONSE_BODY);
        assertThat(testEventLog.getFlg0Ordinary1Exception()).isEqualTo(DEFAULT_FLG_0_ORDINARY_1_EXCEPTION);
        assertThat(testEventLog.getEventSource()).isEqualTo(DEFAULT_EVENT_SOURCE);
        assertThat(testEventLog.getExceptionBody()).isEqualTo(DEFAULT_EXCEPTION_BODY);
        assertThat(testEventLog.getHttpStatus()).isEqualTo(DEFAULT_HTTP_STATUS);
        assertThat(testEventLog.getInsertTimestamp()).isEqualTo(DEFAULT_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void createEventLogWithExistingId() throws Exception {
        // Create the EventLog with an existing ID
        eventLogEntity.setId(1L);
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        int databaseSizeBeforeCreate = eventLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFlg0Ordinary1ExceptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventLogRepository.findAll().size();
        // set the field null
        eventLogEntity.setFlg0Ordinary1Exception(null);

        // Create the EventLog, which fails.
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        restEventLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventLogDTO)))
            .andExpect(status().isBadRequest());

        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEventSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventLogRepository.findAll().size();
        // set the field null
        eventLogEntity.setEventSource(null);

        // Create the EventLog, which fails.
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        restEventLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventLogDTO)))
            .andExpect(status().isBadRequest());

        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInsertTimestampIsRequired() throws Exception {
        int databaseSizeBeforeTest = eventLogRepository.findAll().size();
        // set the field null
        eventLogEntity.setInsertTimestamp(null);

        // Create the EventLog, which fails.
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        restEventLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventLogDTO)))
            .andExpect(status().isBadRequest());

        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEventLogs() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList
        restEventLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventLogEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].conversationId").value(hasItem(DEFAULT_CONVERSATION_ID)))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER)))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].requestBody").value(hasItem(DEFAULT_REQUEST_BODY)))
            .andExpect(jsonPath("$.[*].responseBody").value(hasItem(DEFAULT_RESPONSE_BODY)))
            .andExpect(jsonPath("$.[*].flg0Ordinary1Exception").value(hasItem(DEFAULT_FLG_0_ORDINARY_1_EXCEPTION)))
            .andExpect(jsonPath("$.[*].eventSource").value(hasItem(DEFAULT_EVENT_SOURCE)))
            .andExpect(jsonPath("$.[*].exceptionBody").value(hasItem(DEFAULT_EXCEPTION_BODY)))
            .andExpect(jsonPath("$.[*].httpStatus").value(hasItem(DEFAULT_HTTP_STATUS)))
            .andExpect(jsonPath("$.[*].insertTimestamp").value(hasItem(DEFAULT_INSERT_TIMESTAMP.toString())));
    }

    @Test
    @Transactional
    void getEventLog() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get the eventLog
        restEventLogMockMvc
            .perform(get(ENTITY_API_URL_ID, eventLogEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventLogEntity.getId().intValue()))
            .andExpect(jsonPath("$.conversationId").value(DEFAULT_CONVERSATION_ID))
            .andExpect(jsonPath("$.sender").value(DEFAULT_SENDER))
            .andExpect(jsonPath("$.receiver").value(DEFAULT_RECEIVER))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.requestBody").value(DEFAULT_REQUEST_BODY))
            .andExpect(jsonPath("$.responseBody").value(DEFAULT_RESPONSE_BODY))
            .andExpect(jsonPath("$.flg0Ordinary1Exception").value(DEFAULT_FLG_0_ORDINARY_1_EXCEPTION))
            .andExpect(jsonPath("$.eventSource").value(DEFAULT_EVENT_SOURCE))
            .andExpect(jsonPath("$.exceptionBody").value(DEFAULT_EXCEPTION_BODY))
            .andExpect(jsonPath("$.httpStatus").value(DEFAULT_HTTP_STATUS))
            .andExpect(jsonPath("$.insertTimestamp").value(DEFAULT_INSERT_TIMESTAMP.toString()));
    }

    @Test
    @Transactional
    void getEventLogsByIdFiltering() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        Long id = eventLogEntity.getId();

        defaultEventLogShouldBeFound("id.equals=" + id);
        defaultEventLogShouldNotBeFound("id.notEquals=" + id);

        defaultEventLogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEventLogShouldNotBeFound("id.greaterThan=" + id);

        defaultEventLogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEventLogShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEventLogsByConversationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where conversationId equals to DEFAULT_CONVERSATION_ID
        defaultEventLogShouldBeFound("conversationId.equals=" + DEFAULT_CONVERSATION_ID);

        // Get all the eventLogList where conversationId equals to UPDATED_CONVERSATION_ID
        defaultEventLogShouldNotBeFound("conversationId.equals=" + UPDATED_CONVERSATION_ID);
    }

    @Test
    @Transactional
    void getAllEventLogsByConversationIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where conversationId not equals to DEFAULT_CONVERSATION_ID
        defaultEventLogShouldNotBeFound("conversationId.notEquals=" + DEFAULT_CONVERSATION_ID);

        // Get all the eventLogList where conversationId not equals to UPDATED_CONVERSATION_ID
        defaultEventLogShouldBeFound("conversationId.notEquals=" + UPDATED_CONVERSATION_ID);
    }

    @Test
    @Transactional
    void getAllEventLogsByConversationIdIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where conversationId in DEFAULT_CONVERSATION_ID or UPDATED_CONVERSATION_ID
        defaultEventLogShouldBeFound("conversationId.in=" + DEFAULT_CONVERSATION_ID + "," + UPDATED_CONVERSATION_ID);

        // Get all the eventLogList where conversationId equals to UPDATED_CONVERSATION_ID
        defaultEventLogShouldNotBeFound("conversationId.in=" + UPDATED_CONVERSATION_ID);
    }

    @Test
    @Transactional
    void getAllEventLogsByConversationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where conversationId is not null
        defaultEventLogShouldBeFound("conversationId.specified=true");

        // Get all the eventLogList where conversationId is null
        defaultEventLogShouldNotBeFound("conversationId.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsByConversationIdContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where conversationId contains DEFAULT_CONVERSATION_ID
        defaultEventLogShouldBeFound("conversationId.contains=" + DEFAULT_CONVERSATION_ID);

        // Get all the eventLogList where conversationId contains UPDATED_CONVERSATION_ID
        defaultEventLogShouldNotBeFound("conversationId.contains=" + UPDATED_CONVERSATION_ID);
    }

    @Test
    @Transactional
    void getAllEventLogsByConversationIdNotContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where conversationId does not contain DEFAULT_CONVERSATION_ID
        defaultEventLogShouldNotBeFound("conversationId.doesNotContain=" + DEFAULT_CONVERSATION_ID);

        // Get all the eventLogList where conversationId does not contain UPDATED_CONVERSATION_ID
        defaultEventLogShouldBeFound("conversationId.doesNotContain=" + UPDATED_CONVERSATION_ID);
    }

    @Test
    @Transactional
    void getAllEventLogsBySenderIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where sender equals to DEFAULT_SENDER
        defaultEventLogShouldBeFound("sender.equals=" + DEFAULT_SENDER);

        // Get all the eventLogList where sender equals to UPDATED_SENDER
        defaultEventLogShouldNotBeFound("sender.equals=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    void getAllEventLogsBySenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where sender not equals to DEFAULT_SENDER
        defaultEventLogShouldNotBeFound("sender.notEquals=" + DEFAULT_SENDER);

        // Get all the eventLogList where sender not equals to UPDATED_SENDER
        defaultEventLogShouldBeFound("sender.notEquals=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    void getAllEventLogsBySenderIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where sender in DEFAULT_SENDER or UPDATED_SENDER
        defaultEventLogShouldBeFound("sender.in=" + DEFAULT_SENDER + "," + UPDATED_SENDER);

        // Get all the eventLogList where sender equals to UPDATED_SENDER
        defaultEventLogShouldNotBeFound("sender.in=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    void getAllEventLogsBySenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where sender is not null
        defaultEventLogShouldBeFound("sender.specified=true");

        // Get all the eventLogList where sender is null
        defaultEventLogShouldNotBeFound("sender.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsBySenderContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where sender contains DEFAULT_SENDER
        defaultEventLogShouldBeFound("sender.contains=" + DEFAULT_SENDER);

        // Get all the eventLogList where sender contains UPDATED_SENDER
        defaultEventLogShouldNotBeFound("sender.contains=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    void getAllEventLogsBySenderNotContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where sender does not contain DEFAULT_SENDER
        defaultEventLogShouldNotBeFound("sender.doesNotContain=" + DEFAULT_SENDER);

        // Get all the eventLogList where sender does not contain UPDATED_SENDER
        defaultEventLogShouldBeFound("sender.doesNotContain=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    void getAllEventLogsByReceiverIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where receiver equals to DEFAULT_RECEIVER
        defaultEventLogShouldBeFound("receiver.equals=" + DEFAULT_RECEIVER);

        // Get all the eventLogList where receiver equals to UPDATED_RECEIVER
        defaultEventLogShouldNotBeFound("receiver.equals=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    void getAllEventLogsByReceiverIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where receiver not equals to DEFAULT_RECEIVER
        defaultEventLogShouldNotBeFound("receiver.notEquals=" + DEFAULT_RECEIVER);

        // Get all the eventLogList where receiver not equals to UPDATED_RECEIVER
        defaultEventLogShouldBeFound("receiver.notEquals=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    void getAllEventLogsByReceiverIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where receiver in DEFAULT_RECEIVER or UPDATED_RECEIVER
        defaultEventLogShouldBeFound("receiver.in=" + DEFAULT_RECEIVER + "," + UPDATED_RECEIVER);

        // Get all the eventLogList where receiver equals to UPDATED_RECEIVER
        defaultEventLogShouldNotBeFound("receiver.in=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    void getAllEventLogsByReceiverIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where receiver is not null
        defaultEventLogShouldBeFound("receiver.specified=true");

        // Get all the eventLogList where receiver is null
        defaultEventLogShouldNotBeFound("receiver.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsByReceiverContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where receiver contains DEFAULT_RECEIVER
        defaultEventLogShouldBeFound("receiver.contains=" + DEFAULT_RECEIVER);

        // Get all the eventLogList where receiver contains UPDATED_RECEIVER
        defaultEventLogShouldNotBeFound("receiver.contains=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    void getAllEventLogsByReceiverNotContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where receiver does not contain DEFAULT_RECEIVER
        defaultEventLogShouldNotBeFound("receiver.doesNotContain=" + DEFAULT_RECEIVER);

        // Get all the eventLogList where receiver does not contain UPDATED_RECEIVER
        defaultEventLogShouldBeFound("receiver.doesNotContain=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    void getAllEventLogsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where message equals to DEFAULT_MESSAGE
        defaultEventLogShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the eventLogList where message equals to UPDATED_MESSAGE
        defaultEventLogShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllEventLogsByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where message not equals to DEFAULT_MESSAGE
        defaultEventLogShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the eventLogList where message not equals to UPDATED_MESSAGE
        defaultEventLogShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllEventLogsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultEventLogShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the eventLogList where message equals to UPDATED_MESSAGE
        defaultEventLogShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllEventLogsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where message is not null
        defaultEventLogShouldBeFound("message.specified=true");

        // Get all the eventLogList where message is null
        defaultEventLogShouldNotBeFound("message.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsByMessageContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where message contains DEFAULT_MESSAGE
        defaultEventLogShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the eventLogList where message contains UPDATED_MESSAGE
        defaultEventLogShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllEventLogsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where message does not contain DEFAULT_MESSAGE
        defaultEventLogShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the eventLogList where message does not contain UPDATED_MESSAGE
        defaultEventLogShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllEventLogsByRequestBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where requestBody equals to DEFAULT_REQUEST_BODY
        defaultEventLogShouldBeFound("requestBody.equals=" + DEFAULT_REQUEST_BODY);

        // Get all the eventLogList where requestBody equals to UPDATED_REQUEST_BODY
        defaultEventLogShouldNotBeFound("requestBody.equals=" + UPDATED_REQUEST_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByRequestBodyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where requestBody not equals to DEFAULT_REQUEST_BODY
        defaultEventLogShouldNotBeFound("requestBody.notEquals=" + DEFAULT_REQUEST_BODY);

        // Get all the eventLogList where requestBody not equals to UPDATED_REQUEST_BODY
        defaultEventLogShouldBeFound("requestBody.notEquals=" + UPDATED_REQUEST_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByRequestBodyIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where requestBody in DEFAULT_REQUEST_BODY or UPDATED_REQUEST_BODY
        defaultEventLogShouldBeFound("requestBody.in=" + DEFAULT_REQUEST_BODY + "," + UPDATED_REQUEST_BODY);

        // Get all the eventLogList where requestBody equals to UPDATED_REQUEST_BODY
        defaultEventLogShouldNotBeFound("requestBody.in=" + UPDATED_REQUEST_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByRequestBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where requestBody is not null
        defaultEventLogShouldBeFound("requestBody.specified=true");

        // Get all the eventLogList where requestBody is null
        defaultEventLogShouldNotBeFound("requestBody.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsByRequestBodyContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where requestBody contains DEFAULT_REQUEST_BODY
        defaultEventLogShouldBeFound("requestBody.contains=" + DEFAULT_REQUEST_BODY);

        // Get all the eventLogList where requestBody contains UPDATED_REQUEST_BODY
        defaultEventLogShouldNotBeFound("requestBody.contains=" + UPDATED_REQUEST_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByRequestBodyNotContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where requestBody does not contain DEFAULT_REQUEST_BODY
        defaultEventLogShouldNotBeFound("requestBody.doesNotContain=" + DEFAULT_REQUEST_BODY);

        // Get all the eventLogList where requestBody does not contain UPDATED_REQUEST_BODY
        defaultEventLogShouldBeFound("requestBody.doesNotContain=" + UPDATED_REQUEST_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByResponseBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where responseBody equals to DEFAULT_RESPONSE_BODY
        defaultEventLogShouldBeFound("responseBody.equals=" + DEFAULT_RESPONSE_BODY);

        // Get all the eventLogList where responseBody equals to UPDATED_RESPONSE_BODY
        defaultEventLogShouldNotBeFound("responseBody.equals=" + UPDATED_RESPONSE_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByResponseBodyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where responseBody not equals to DEFAULT_RESPONSE_BODY
        defaultEventLogShouldNotBeFound("responseBody.notEquals=" + DEFAULT_RESPONSE_BODY);

        // Get all the eventLogList where responseBody not equals to UPDATED_RESPONSE_BODY
        defaultEventLogShouldBeFound("responseBody.notEquals=" + UPDATED_RESPONSE_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByResponseBodyIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where responseBody in DEFAULT_RESPONSE_BODY or UPDATED_RESPONSE_BODY
        defaultEventLogShouldBeFound("responseBody.in=" + DEFAULT_RESPONSE_BODY + "," + UPDATED_RESPONSE_BODY);

        // Get all the eventLogList where responseBody equals to UPDATED_RESPONSE_BODY
        defaultEventLogShouldNotBeFound("responseBody.in=" + UPDATED_RESPONSE_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByResponseBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where responseBody is not null
        defaultEventLogShouldBeFound("responseBody.specified=true");

        // Get all the eventLogList where responseBody is null
        defaultEventLogShouldNotBeFound("responseBody.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsByResponseBodyContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where responseBody contains DEFAULT_RESPONSE_BODY
        defaultEventLogShouldBeFound("responseBody.contains=" + DEFAULT_RESPONSE_BODY);

        // Get all the eventLogList where responseBody contains UPDATED_RESPONSE_BODY
        defaultEventLogShouldNotBeFound("responseBody.contains=" + UPDATED_RESPONSE_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByResponseBodyNotContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where responseBody does not contain DEFAULT_RESPONSE_BODY
        defaultEventLogShouldNotBeFound("responseBody.doesNotContain=" + DEFAULT_RESPONSE_BODY);

        // Get all the eventLogList where responseBody does not contain UPDATED_RESPONSE_BODY
        defaultEventLogShouldBeFound("responseBody.doesNotContain=" + UPDATED_RESPONSE_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByFlg0Ordinary1ExceptionIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where flg0Ordinary1Exception equals to DEFAULT_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldBeFound("flg0Ordinary1Exception.equals=" + DEFAULT_FLG_0_ORDINARY_1_EXCEPTION);

        // Get all the eventLogList where flg0Ordinary1Exception equals to UPDATED_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldNotBeFound("flg0Ordinary1Exception.equals=" + UPDATED_FLG_0_ORDINARY_1_EXCEPTION);
    }

    @Test
    @Transactional
    void getAllEventLogsByFlg0Ordinary1ExceptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where flg0Ordinary1Exception not equals to DEFAULT_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldNotBeFound("flg0Ordinary1Exception.notEquals=" + DEFAULT_FLG_0_ORDINARY_1_EXCEPTION);

        // Get all the eventLogList where flg0Ordinary1Exception not equals to UPDATED_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldBeFound("flg0Ordinary1Exception.notEquals=" + UPDATED_FLG_0_ORDINARY_1_EXCEPTION);
    }

    @Test
    @Transactional
    void getAllEventLogsByFlg0Ordinary1ExceptionIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where flg0Ordinary1Exception in DEFAULT_FLG_0_ORDINARY_1_EXCEPTION or UPDATED_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldBeFound(
            "flg0Ordinary1Exception.in=" + DEFAULT_FLG_0_ORDINARY_1_EXCEPTION + "," + UPDATED_FLG_0_ORDINARY_1_EXCEPTION
        );

        // Get all the eventLogList where flg0Ordinary1Exception equals to UPDATED_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldNotBeFound("flg0Ordinary1Exception.in=" + UPDATED_FLG_0_ORDINARY_1_EXCEPTION);
    }

    @Test
    @Transactional
    void getAllEventLogsByFlg0Ordinary1ExceptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where flg0Ordinary1Exception is not null
        defaultEventLogShouldBeFound("flg0Ordinary1Exception.specified=true");

        // Get all the eventLogList where flg0Ordinary1Exception is null
        defaultEventLogShouldNotBeFound("flg0Ordinary1Exception.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsByFlg0Ordinary1ExceptionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where flg0Ordinary1Exception is greater than or equal to DEFAULT_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldBeFound("flg0Ordinary1Exception.greaterThanOrEqual=" + DEFAULT_FLG_0_ORDINARY_1_EXCEPTION);

        // Get all the eventLogList where flg0Ordinary1Exception is greater than or equal to UPDATED_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldNotBeFound("flg0Ordinary1Exception.greaterThanOrEqual=" + UPDATED_FLG_0_ORDINARY_1_EXCEPTION);
    }

    @Test
    @Transactional
    void getAllEventLogsByFlg0Ordinary1ExceptionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where flg0Ordinary1Exception is less than or equal to DEFAULT_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldBeFound("flg0Ordinary1Exception.lessThanOrEqual=" + DEFAULT_FLG_0_ORDINARY_1_EXCEPTION);

        // Get all the eventLogList where flg0Ordinary1Exception is less than or equal to SMALLER_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldNotBeFound("flg0Ordinary1Exception.lessThanOrEqual=" + SMALLER_FLG_0_ORDINARY_1_EXCEPTION);
    }

    @Test
    @Transactional
    void getAllEventLogsByFlg0Ordinary1ExceptionIsLessThanSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where flg0Ordinary1Exception is less than DEFAULT_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldNotBeFound("flg0Ordinary1Exception.lessThan=" + DEFAULT_FLG_0_ORDINARY_1_EXCEPTION);

        // Get all the eventLogList where flg0Ordinary1Exception is less than UPDATED_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldBeFound("flg0Ordinary1Exception.lessThan=" + UPDATED_FLG_0_ORDINARY_1_EXCEPTION);
    }

    @Test
    @Transactional
    void getAllEventLogsByFlg0Ordinary1ExceptionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where flg0Ordinary1Exception is greater than DEFAULT_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldNotBeFound("flg0Ordinary1Exception.greaterThan=" + DEFAULT_FLG_0_ORDINARY_1_EXCEPTION);

        // Get all the eventLogList where flg0Ordinary1Exception is greater than SMALLER_FLG_0_ORDINARY_1_EXCEPTION
        defaultEventLogShouldBeFound("flg0Ordinary1Exception.greaterThan=" + SMALLER_FLG_0_ORDINARY_1_EXCEPTION);
    }

    @Test
    @Transactional
    void getAllEventLogsByEventSourceIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where eventSource equals to DEFAULT_EVENT_SOURCE
        defaultEventLogShouldBeFound("eventSource.equals=" + DEFAULT_EVENT_SOURCE);

        // Get all the eventLogList where eventSource equals to UPDATED_EVENT_SOURCE
        defaultEventLogShouldNotBeFound("eventSource.equals=" + UPDATED_EVENT_SOURCE);
    }

    @Test
    @Transactional
    void getAllEventLogsByEventSourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where eventSource not equals to DEFAULT_EVENT_SOURCE
        defaultEventLogShouldNotBeFound("eventSource.notEquals=" + DEFAULT_EVENT_SOURCE);

        // Get all the eventLogList where eventSource not equals to UPDATED_EVENT_SOURCE
        defaultEventLogShouldBeFound("eventSource.notEquals=" + UPDATED_EVENT_SOURCE);
    }

    @Test
    @Transactional
    void getAllEventLogsByEventSourceIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where eventSource in DEFAULT_EVENT_SOURCE or UPDATED_EVENT_SOURCE
        defaultEventLogShouldBeFound("eventSource.in=" + DEFAULT_EVENT_SOURCE + "," + UPDATED_EVENT_SOURCE);

        // Get all the eventLogList where eventSource equals to UPDATED_EVENT_SOURCE
        defaultEventLogShouldNotBeFound("eventSource.in=" + UPDATED_EVENT_SOURCE);
    }

    @Test
    @Transactional
    void getAllEventLogsByEventSourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where eventSource is not null
        defaultEventLogShouldBeFound("eventSource.specified=true");

        // Get all the eventLogList where eventSource is null
        defaultEventLogShouldNotBeFound("eventSource.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsByEventSourceContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where eventSource contains DEFAULT_EVENT_SOURCE
        defaultEventLogShouldBeFound("eventSource.contains=" + DEFAULT_EVENT_SOURCE);

        // Get all the eventLogList where eventSource contains UPDATED_EVENT_SOURCE
        defaultEventLogShouldNotBeFound("eventSource.contains=" + UPDATED_EVENT_SOURCE);
    }

    @Test
    @Transactional
    void getAllEventLogsByEventSourceNotContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where eventSource does not contain DEFAULT_EVENT_SOURCE
        defaultEventLogShouldNotBeFound("eventSource.doesNotContain=" + DEFAULT_EVENT_SOURCE);

        // Get all the eventLogList where eventSource does not contain UPDATED_EVENT_SOURCE
        defaultEventLogShouldBeFound("eventSource.doesNotContain=" + UPDATED_EVENT_SOURCE);
    }

    @Test
    @Transactional
    void getAllEventLogsByExceptionBodyIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where exceptionBody equals to DEFAULT_EXCEPTION_BODY
        defaultEventLogShouldBeFound("exceptionBody.equals=" + DEFAULT_EXCEPTION_BODY);

        // Get all the eventLogList where exceptionBody equals to UPDATED_EXCEPTION_BODY
        defaultEventLogShouldNotBeFound("exceptionBody.equals=" + UPDATED_EXCEPTION_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByExceptionBodyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where exceptionBody not equals to DEFAULT_EXCEPTION_BODY
        defaultEventLogShouldNotBeFound("exceptionBody.notEquals=" + DEFAULT_EXCEPTION_BODY);

        // Get all the eventLogList where exceptionBody not equals to UPDATED_EXCEPTION_BODY
        defaultEventLogShouldBeFound("exceptionBody.notEquals=" + UPDATED_EXCEPTION_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByExceptionBodyIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where exceptionBody in DEFAULT_EXCEPTION_BODY or UPDATED_EXCEPTION_BODY
        defaultEventLogShouldBeFound("exceptionBody.in=" + DEFAULT_EXCEPTION_BODY + "," + UPDATED_EXCEPTION_BODY);

        // Get all the eventLogList where exceptionBody equals to UPDATED_EXCEPTION_BODY
        defaultEventLogShouldNotBeFound("exceptionBody.in=" + UPDATED_EXCEPTION_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByExceptionBodyIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where exceptionBody is not null
        defaultEventLogShouldBeFound("exceptionBody.specified=true");

        // Get all the eventLogList where exceptionBody is null
        defaultEventLogShouldNotBeFound("exceptionBody.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsByExceptionBodyContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where exceptionBody contains DEFAULT_EXCEPTION_BODY
        defaultEventLogShouldBeFound("exceptionBody.contains=" + DEFAULT_EXCEPTION_BODY);

        // Get all the eventLogList where exceptionBody contains UPDATED_EXCEPTION_BODY
        defaultEventLogShouldNotBeFound("exceptionBody.contains=" + UPDATED_EXCEPTION_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByExceptionBodyNotContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where exceptionBody does not contain DEFAULT_EXCEPTION_BODY
        defaultEventLogShouldNotBeFound("exceptionBody.doesNotContain=" + DEFAULT_EXCEPTION_BODY);

        // Get all the eventLogList where exceptionBody does not contain UPDATED_EXCEPTION_BODY
        defaultEventLogShouldBeFound("exceptionBody.doesNotContain=" + UPDATED_EXCEPTION_BODY);
    }

    @Test
    @Transactional
    void getAllEventLogsByHttpStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where httpStatus equals to DEFAULT_HTTP_STATUS
        defaultEventLogShouldBeFound("httpStatus.equals=" + DEFAULT_HTTP_STATUS);

        // Get all the eventLogList where httpStatus equals to UPDATED_HTTP_STATUS
        defaultEventLogShouldNotBeFound("httpStatus.equals=" + UPDATED_HTTP_STATUS);
    }

    @Test
    @Transactional
    void getAllEventLogsByHttpStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where httpStatus not equals to DEFAULT_HTTP_STATUS
        defaultEventLogShouldNotBeFound("httpStatus.notEquals=" + DEFAULT_HTTP_STATUS);

        // Get all the eventLogList where httpStatus not equals to UPDATED_HTTP_STATUS
        defaultEventLogShouldBeFound("httpStatus.notEquals=" + UPDATED_HTTP_STATUS);
    }

    @Test
    @Transactional
    void getAllEventLogsByHttpStatusIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where httpStatus in DEFAULT_HTTP_STATUS or UPDATED_HTTP_STATUS
        defaultEventLogShouldBeFound("httpStatus.in=" + DEFAULT_HTTP_STATUS + "," + UPDATED_HTTP_STATUS);

        // Get all the eventLogList where httpStatus equals to UPDATED_HTTP_STATUS
        defaultEventLogShouldNotBeFound("httpStatus.in=" + UPDATED_HTTP_STATUS);
    }

    @Test
    @Transactional
    void getAllEventLogsByHttpStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where httpStatus is not null
        defaultEventLogShouldBeFound("httpStatus.specified=true");

        // Get all the eventLogList where httpStatus is null
        defaultEventLogShouldNotBeFound("httpStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllEventLogsByHttpStatusContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where httpStatus contains DEFAULT_HTTP_STATUS
        defaultEventLogShouldBeFound("httpStatus.contains=" + DEFAULT_HTTP_STATUS);

        // Get all the eventLogList where httpStatus contains UPDATED_HTTP_STATUS
        defaultEventLogShouldNotBeFound("httpStatus.contains=" + UPDATED_HTTP_STATUS);
    }

    @Test
    @Transactional
    void getAllEventLogsByHttpStatusNotContainsSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where httpStatus does not contain DEFAULT_HTTP_STATUS
        defaultEventLogShouldNotBeFound("httpStatus.doesNotContain=" + DEFAULT_HTTP_STATUS);

        // Get all the eventLogList where httpStatus does not contain UPDATED_HTTP_STATUS
        defaultEventLogShouldBeFound("httpStatus.doesNotContain=" + UPDATED_HTTP_STATUS);
    }

    @Test
    @Transactional
    void getAllEventLogsByInsertTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where insertTimestamp equals to DEFAULT_INSERT_TIMESTAMP
        defaultEventLogShouldBeFound("insertTimestamp.equals=" + DEFAULT_INSERT_TIMESTAMP);

        // Get all the eventLogList where insertTimestamp equals to UPDATED_INSERT_TIMESTAMP
        defaultEventLogShouldNotBeFound("insertTimestamp.equals=" + UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllEventLogsByInsertTimestampIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where insertTimestamp not equals to DEFAULT_INSERT_TIMESTAMP
        defaultEventLogShouldNotBeFound("insertTimestamp.notEquals=" + DEFAULT_INSERT_TIMESTAMP);

        // Get all the eventLogList where insertTimestamp not equals to UPDATED_INSERT_TIMESTAMP
        defaultEventLogShouldBeFound("insertTimestamp.notEquals=" + UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllEventLogsByInsertTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where insertTimestamp in DEFAULT_INSERT_TIMESTAMP or UPDATED_INSERT_TIMESTAMP
        defaultEventLogShouldBeFound("insertTimestamp.in=" + DEFAULT_INSERT_TIMESTAMP + "," + UPDATED_INSERT_TIMESTAMP);

        // Get all the eventLogList where insertTimestamp equals to UPDATED_INSERT_TIMESTAMP
        defaultEventLogShouldNotBeFound("insertTimestamp.in=" + UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void getAllEventLogsByInsertTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        // Get all the eventLogList where insertTimestamp is not null
        defaultEventLogShouldBeFound("insertTimestamp.specified=true");

        // Get all the eventLogList where insertTimestamp is null
        defaultEventLogShouldNotBeFound("insertTimestamp.specified=false");
    }

    */
/**
 * Executes the search, and checks that the default entity is returned.
 *//*

    private void defaultEventLogShouldBeFound(String filter) throws Exception {
        restEventLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventLogEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].conversationId").value(hasItem(DEFAULT_CONVERSATION_ID)))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER)))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].requestBody").value(hasItem(DEFAULT_REQUEST_BODY)))
            .andExpect(jsonPath("$.[*].responseBody").value(hasItem(DEFAULT_RESPONSE_BODY)))
            .andExpect(jsonPath("$.[*].flg0Ordinary1Exception").value(hasItem(DEFAULT_FLG_0_ORDINARY_1_EXCEPTION)))
            .andExpect(jsonPath("$.[*].eventSource").value(hasItem(DEFAULT_EVENT_SOURCE)))
            .andExpect(jsonPath("$.[*].exceptionBody").value(hasItem(DEFAULT_EXCEPTION_BODY)))
            .andExpect(jsonPath("$.[*].httpStatus").value(hasItem(DEFAULT_HTTP_STATUS)))
            .andExpect(jsonPath("$.[*].insertTimestamp").value(hasItem(DEFAULT_INSERT_TIMESTAMP.toString())));

        // Check, that the count call also returns 1
        restEventLogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    */
/**
 * Executes the search, and checks that the default entity is not returned.
 *//*

    private void defaultEventLogShouldNotBeFound(String filter) throws Exception {
        restEventLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEventLogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEventLog() throws Exception {
        // Get the eventLog
        restEventLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEventLog() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();

        // Update the eventLog
        EventLogEntity updatedEventLogEntity = eventLogRepository.findById(eventLogEntity.getId()).get();
        // Disconnect from session so that the updates on updatedEventLogEntity are not directly saved in db
        em.detach(updatedEventLogEntity);
        updatedEventLogEntity
            .conversationId(UPDATED_CONVERSATION_ID)
            .sender(UPDATED_SENDER)
            .receiver(UPDATED_RECEIVER)
            .message(UPDATED_MESSAGE)
            .requestBody(UPDATED_REQUEST_BODY)
            .responseBody(UPDATED_RESPONSE_BODY)
            .flg0Ordinary1Exception(UPDATED_FLG_0_ORDINARY_1_EXCEPTION)
            .eventSource(UPDATED_EVENT_SOURCE)
            .exceptionBody(UPDATED_EXCEPTION_BODY)
            .httpStatus(UPDATED_HTTP_STATUS)
            .insertTimestamp(UPDATED_INSERT_TIMESTAMP);
        EventLogDTO eventLogDTO = eventLogMapper.toDto(updatedEventLogEntity);

        restEventLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeUpdate);
        EventLogEntity testEventLog = eventLogList.get(eventLogList.size() - 1);
        assertThat(testEventLog.getConversationId()).isEqualTo(UPDATED_CONVERSATION_ID);
        assertThat(testEventLog.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testEventLog.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testEventLog.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEventLog.getRequestBody()).isEqualTo(UPDATED_REQUEST_BODY);
        assertThat(testEventLog.getResponseBody()).isEqualTo(UPDATED_RESPONSE_BODY);
        assertThat(testEventLog.getFlg0Ordinary1Exception()).isEqualTo(UPDATED_FLG_0_ORDINARY_1_EXCEPTION);
        assertThat(testEventLog.getEventSource()).isEqualTo(UPDATED_EVENT_SOURCE);
        assertThat(testEventLog.getExceptionBody()).isEqualTo(UPDATED_EXCEPTION_BODY);
        assertThat(testEventLog.getHttpStatus()).isEqualTo(UPDATED_HTTP_STATUS);
        assertThat(testEventLog.getInsertTimestamp()).isEqualTo(UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void putNonExistingEventLog() throws Exception {
        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();
        eventLogEntity.setId(count.incrementAndGet());

        // Create the EventLog
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventLog() throws Exception {
        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();
        eventLogEntity.setId(count.incrementAndGet());

        // Create the EventLog
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventLog() throws Exception {
        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();
        eventLogEntity.setId(count.incrementAndGet());

        // Create the EventLog
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventLogWithPatch() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();

        // Update the eventLog using partial update
        EventLogEntity partialUpdatedEventLogEntity = new EventLogEntity();
        partialUpdatedEventLogEntity.setId(eventLogEntity.getId());

        partialUpdatedEventLogEntity
            .conversationId(UPDATED_CONVERSATION_ID)
            .receiver(UPDATED_RECEIVER)
            .responseBody(UPDATED_RESPONSE_BODY)
            .eventSource(UPDATED_EVENT_SOURCE);

        restEventLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventLogEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventLogEntity))
            )
            .andExpect(status().isOk());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeUpdate);
        EventLogEntity testEventLog = eventLogList.get(eventLogList.size() - 1);
        assertThat(testEventLog.getConversationId()).isEqualTo(UPDATED_CONVERSATION_ID);
        assertThat(testEventLog.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testEventLog.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testEventLog.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEventLog.getRequestBody()).isEqualTo(DEFAULT_REQUEST_BODY);
        assertThat(testEventLog.getResponseBody()).isEqualTo(UPDATED_RESPONSE_BODY);
        assertThat(testEventLog.getFlg0Ordinary1Exception()).isEqualTo(DEFAULT_FLG_0_ORDINARY_1_EXCEPTION);
        assertThat(testEventLog.getEventSource()).isEqualTo(UPDATED_EVENT_SOURCE);
        assertThat(testEventLog.getExceptionBody()).isEqualTo(DEFAULT_EXCEPTION_BODY);
        assertThat(testEventLog.getHttpStatus()).isEqualTo(DEFAULT_HTTP_STATUS);
        assertThat(testEventLog.getInsertTimestamp()).isEqualTo(DEFAULT_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void fullUpdateEventLogWithPatch() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();

        // Update the eventLog using partial update
        EventLogEntity partialUpdatedEventLogEntity = new EventLogEntity();
        partialUpdatedEventLogEntity.setId(eventLogEntity.getId());

        partialUpdatedEventLogEntity
            .conversationId(UPDATED_CONVERSATION_ID)
            .sender(UPDATED_SENDER)
            .receiver(UPDATED_RECEIVER)
            .message(UPDATED_MESSAGE)
            .requestBody(UPDATED_REQUEST_BODY)
            .responseBody(UPDATED_RESPONSE_BODY)
            .flg0Ordinary1Exception(UPDATED_FLG_0_ORDINARY_1_EXCEPTION)
            .eventSource(UPDATED_EVENT_SOURCE)
            .exceptionBody(UPDATED_EXCEPTION_BODY)
            .httpStatus(UPDATED_HTTP_STATUS)
            .insertTimestamp(UPDATED_INSERT_TIMESTAMP);

        restEventLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventLogEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventLogEntity))
            )
            .andExpect(status().isOk());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeUpdate);
        EventLogEntity testEventLog = eventLogList.get(eventLogList.size() - 1);
        assertThat(testEventLog.getConversationId()).isEqualTo(UPDATED_CONVERSATION_ID);
        assertThat(testEventLog.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testEventLog.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testEventLog.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEventLog.getRequestBody()).isEqualTo(UPDATED_REQUEST_BODY);
        assertThat(testEventLog.getResponseBody()).isEqualTo(UPDATED_RESPONSE_BODY);
        assertThat(testEventLog.getFlg0Ordinary1Exception()).isEqualTo(UPDATED_FLG_0_ORDINARY_1_EXCEPTION);
        assertThat(testEventLog.getEventSource()).isEqualTo(UPDATED_EVENT_SOURCE);
        assertThat(testEventLog.getExceptionBody()).isEqualTo(UPDATED_EXCEPTION_BODY);
        assertThat(testEventLog.getHttpStatus()).isEqualTo(UPDATED_HTTP_STATUS);
        assertThat(testEventLog.getInsertTimestamp()).isEqualTo(UPDATED_INSERT_TIMESTAMP);
    }

    @Test
    @Transactional
    void patchNonExistingEventLog() throws Exception {
        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();
        eventLogEntity.setId(count.incrementAndGet());

        // Create the EventLog
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventLog() throws Exception {
        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();
        eventLogEntity.setId(count.incrementAndGet());

        // Create the EventLog
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventLog() throws Exception {
        int databaseSizeBeforeUpdate = eventLogRepository.findAll().size();
        eventLogEntity.setId(count.incrementAndGet());

        // Create the EventLog
        EventLogDTO eventLogDTO = eventLogMapper.toDto(eventLogEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventLogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventLog in the database
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventLog() throws Exception {
        // Initialize the database
        eventLogRepository.saveAndFlush(eventLogEntity);

        int databaseSizeBeforeDelete = eventLogRepository.findAll().size();

        // Delete the eventLog
        restEventLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventLogEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventLogEntity> eventLogList = eventLogRepository.findAll();
        assertThat(eventLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/
