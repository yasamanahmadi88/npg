package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.TimeFrameEntity;
import ix.portal.npg.repository.TimeFrameRepository;
import ix.portal.npg.service.criteria.TimeFrameCriteria;
import ix.portal.npg.service.dto.TimeFrameDTO;
import ix.portal.npg.service.mapper.TimeFrameMapper;
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
 * Integration tests for the {@link TimeFrameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "timeFrame" })class TimeFrameResourceIT {

    private static final Long DEFAULT_BEGIN = 1L;
    private static final Long UPDATED_BEGIN = 2L;
    private static final Long SMALLER_BEGIN = 1L - 1L;

    private static final Long DEFAULT_END = 1L;
    private static final Long UPDATED_END = 2L;
    private static final Long SMALLER_END = 1L - 1L;

    private static final Long DEFAULT_OFF_DAY_ID = 1L;
    private static final Long UPDATED_OFF_DAY_ID = 2L;
    private static final Long SMALLER_OFF_DAY_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/time-frames";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TimeFrameRepository timeFrameRepository;

    @Autowired
    private TimeFrameMapper timeFrameMapper;

    @Autowired
    private EntityManager em;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc restTimeFrameMockMvc;

    private TimeFrameEntity timeFrameEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeFrameEntity createEntity(EntityManager em) {
        TimeFrameEntity timeFrameEntity = new TimeFrameEntity().begin(DEFAULT_BEGIN).end(DEFAULT_END).offDayId(DEFAULT_OFF_DAY_ID);
        return timeFrameEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeFrameEntity createUpdatedEntity(EntityManager em) {
        TimeFrameEntity timeFrameEntity = new TimeFrameEntity().begin(UPDATED_BEGIN).end(UPDATED_END).offDayId(UPDATED_OFF_DAY_ID);
        return timeFrameEntity;
    }

    @BeforeEach
    public void initTest() {
        jdbcTemplate.update("delete from TBL_DAY_OF_WEEK_TIME_FRAME");
        jdbcTemplate.update("delete from TBL_TIME_FRAME");
        jdbcTemplate.update("delete from TBL_OFF_DAY");
        em.clear();

        timeFrameEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createTimeFrame() throws Exception {
        int databaseSizeBeforeCreate = timeFrameRepository.findAll().size();
        // Create the TimeFrame
        TimeFrameDTO timeFrameDTO = timeFrameMapper.toDto(timeFrameEntity);
        restTimeFrameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeFrameDTO)))
            .andExpect(status().isCreated());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeCreate + 1);
        TimeFrameEntity testTimeFrame = timeFrameList.get(timeFrameList.size() - 1);
        assertThat(testTimeFrame.getBegin()).isEqualTo(DEFAULT_BEGIN);
        assertThat(testTimeFrame.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testTimeFrame.getOffDayId()).isEqualTo(DEFAULT_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void createTimeFrameWithExistingId() throws Exception {
        // Create the TimeFrame with an existing ID
        timeFrameEntity.setId(1L);
        TimeFrameDTO timeFrameDTO = timeFrameMapper.toDto(timeFrameEntity);

        int databaseSizeBeforeCreate = timeFrameRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeFrameMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeFrameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTimeFrames() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList
        restTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeFrameEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].begin").value(hasItem(DEFAULT_BEGIN.intValue())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.intValue())))
            .andExpect(jsonPath("$.[*].offDayId").value(hasItem(DEFAULT_OFF_DAY_ID.intValue())));
    }

    @Test
    @Transactional
    void getTimeFrame() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get the timeFrame
        restTimeFrameMockMvc
            .perform(get(ENTITY_API_URL_ID, timeFrameEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(timeFrameEntity.getId().intValue()))
            .andExpect(jsonPath("$.begin").value(DEFAULT_BEGIN.intValue()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.intValue()))
            .andExpect(jsonPath("$.offDayId").value(DEFAULT_OFF_DAY_ID.intValue()));
    }

    @Test
    @Transactional
    void getTimeFramesByIdFiltering() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        Long id = timeFrameEntity.getId();

        defaultTimeFrameShouldBeFound("id.equals=" + id);
        defaultTimeFrameShouldNotBeFound("id.notEquals=" + id);

        defaultTimeFrameShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTimeFrameShouldNotBeFound("id.greaterThan=" + id);

        defaultTimeFrameShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTimeFrameShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTimeFramesByBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where begin equals to DEFAULT_BEGIN
        defaultTimeFrameShouldBeFound("begin.equals=" + DEFAULT_BEGIN);

        // Get all the timeFrameList where begin equals to UPDATED_BEGIN
        defaultTimeFrameShouldNotBeFound("begin.equals=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllTimeFramesByBeginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where begin not equals to DEFAULT_BEGIN
        defaultTimeFrameShouldNotBeFound("begin.notEquals=" + DEFAULT_BEGIN);

        // Get all the timeFrameList where begin not equals to UPDATED_BEGIN
        defaultTimeFrameShouldBeFound("begin.notEquals=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllTimeFramesByBeginIsInShouldWork() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where begin in DEFAULT_BEGIN or UPDATED_BEGIN
        defaultTimeFrameShouldBeFound("begin.in=" + DEFAULT_BEGIN + "," + UPDATED_BEGIN);

        // Get all the timeFrameList where begin equals to UPDATED_BEGIN
        defaultTimeFrameShouldNotBeFound("begin.in=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllTimeFramesByBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where begin is not null
        defaultTimeFrameShouldBeFound("begin.specified=true");

        // Get all the timeFrameList where begin is null
        defaultTimeFrameShouldNotBeFound("begin.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeFramesByBeginIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where begin is greater than or equal to DEFAULT_BEGIN
        defaultTimeFrameShouldBeFound("begin.greaterThanOrEqual=" + DEFAULT_BEGIN);

        // Get all the timeFrameList where begin is greater than or equal to UPDATED_BEGIN
        defaultTimeFrameShouldNotBeFound("begin.greaterThanOrEqual=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllTimeFramesByBeginIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where begin is less than or equal to DEFAULT_BEGIN
        defaultTimeFrameShouldBeFound("begin.lessThanOrEqual=" + DEFAULT_BEGIN);

        // Get all the timeFrameList where begin is less than or equal to SMALLER_BEGIN
        defaultTimeFrameShouldNotBeFound("begin.lessThanOrEqual=" + SMALLER_BEGIN);
    }

    @Test
    @Transactional
    void getAllTimeFramesByBeginIsLessThanSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where begin is less than DEFAULT_BEGIN
        defaultTimeFrameShouldNotBeFound("begin.lessThan=" + DEFAULT_BEGIN);

        // Get all the timeFrameList where begin is less than UPDATED_BEGIN
        defaultTimeFrameShouldBeFound("begin.lessThan=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllTimeFramesByBeginIsGreaterThanSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where begin is greater than DEFAULT_BEGIN
        defaultTimeFrameShouldNotBeFound("begin.greaterThan=" + DEFAULT_BEGIN);

        // Get all the timeFrameList where begin is greater than SMALLER_BEGIN
        defaultTimeFrameShouldBeFound("begin.greaterThan=" + SMALLER_BEGIN);
    }

    @Test
    @Transactional
    void getAllTimeFramesByEndIsEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where end equals to DEFAULT_END
        defaultTimeFrameShouldBeFound("end.equals=" + DEFAULT_END);

        // Get all the timeFrameList where end equals to UPDATED_END
        defaultTimeFrameShouldNotBeFound("end.equals=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllTimeFramesByEndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where end not equals to DEFAULT_END
        defaultTimeFrameShouldNotBeFound("end.notEquals=" + DEFAULT_END);

        // Get all the timeFrameList where end not equals to UPDATED_END
        defaultTimeFrameShouldBeFound("end.notEquals=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllTimeFramesByEndIsInShouldWork() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where end in DEFAULT_END or UPDATED_END
        defaultTimeFrameShouldBeFound("end.in=" + DEFAULT_END + "," + UPDATED_END);

        // Get all the timeFrameList where end equals to UPDATED_END
        defaultTimeFrameShouldNotBeFound("end.in=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllTimeFramesByEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where end is not null
        defaultTimeFrameShouldBeFound("end.specified=true");

        // Get all the timeFrameList where end is null
        defaultTimeFrameShouldNotBeFound("end.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeFramesByEndIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where end is greater than or equal to DEFAULT_END
        defaultTimeFrameShouldBeFound("end.greaterThanOrEqual=" + DEFAULT_END);

        // Get all the timeFrameList where end is greater than or equal to UPDATED_END
        defaultTimeFrameShouldNotBeFound("end.greaterThanOrEqual=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllTimeFramesByEndIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where end is less than or equal to DEFAULT_END
        defaultTimeFrameShouldBeFound("end.lessThanOrEqual=" + DEFAULT_END);

        // Get all the timeFrameList where end is less than or equal to SMALLER_END
        defaultTimeFrameShouldNotBeFound("end.lessThanOrEqual=" + SMALLER_END);
    }

    @Test
    @Transactional
    void getAllTimeFramesByEndIsLessThanSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where end is less than DEFAULT_END
        defaultTimeFrameShouldNotBeFound("end.lessThan=" + DEFAULT_END);

        // Get all the timeFrameList where end is less than UPDATED_END
        defaultTimeFrameShouldBeFound("end.lessThan=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllTimeFramesByEndIsGreaterThanSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where end is greater than DEFAULT_END
        defaultTimeFrameShouldNotBeFound("end.greaterThan=" + DEFAULT_END);

        // Get all the timeFrameList where end is greater than SMALLER_END
        defaultTimeFrameShouldBeFound("end.greaterThan=" + SMALLER_END);
    }

    @Test
    @Transactional
    void getAllTimeFramesByOffDayIdIsEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where offDayId equals to DEFAULT_OFF_DAY_ID
        defaultTimeFrameShouldBeFound("offDayId.equals=" + DEFAULT_OFF_DAY_ID);

        // Get all the timeFrameList where offDayId equals to UPDATED_OFF_DAY_ID
        defaultTimeFrameShouldNotBeFound("offDayId.equals=" + UPDATED_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void getAllTimeFramesByOffDayIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where offDayId not equals to DEFAULT_OFF_DAY_ID
        defaultTimeFrameShouldNotBeFound("offDayId.notEquals=" + DEFAULT_OFF_DAY_ID);

        // Get all the timeFrameList where offDayId not equals to UPDATED_OFF_DAY_ID
        defaultTimeFrameShouldBeFound("offDayId.notEquals=" + UPDATED_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void getAllTimeFramesByOffDayIdIsInShouldWork() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where offDayId in DEFAULT_OFF_DAY_ID or UPDATED_OFF_DAY_ID
        defaultTimeFrameShouldBeFound("offDayId.in=" + DEFAULT_OFF_DAY_ID + "," + UPDATED_OFF_DAY_ID);

        // Get all the timeFrameList where offDayId equals to UPDATED_OFF_DAY_ID
        defaultTimeFrameShouldNotBeFound("offDayId.in=" + UPDATED_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void getAllTimeFramesByOffDayIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where offDayId is not null
        defaultTimeFrameShouldBeFound("offDayId.specified=true");

        // Get all the timeFrameList where offDayId is null
        defaultTimeFrameShouldNotBeFound("offDayId.specified=false");
    }

    @Test
    @Transactional
    void getAllTimeFramesByOffDayIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where offDayId is greater than or equal to DEFAULT_OFF_DAY_ID
        defaultTimeFrameShouldBeFound("offDayId.greaterThanOrEqual=" + DEFAULT_OFF_DAY_ID);

        // Get all the timeFrameList where offDayId is greater than or equal to UPDATED_OFF_DAY_ID
        defaultTimeFrameShouldNotBeFound("offDayId.greaterThanOrEqual=" + UPDATED_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void getAllTimeFramesByOffDayIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where offDayId is less than or equal to DEFAULT_OFF_DAY_ID
        defaultTimeFrameShouldBeFound("offDayId.lessThanOrEqual=" + DEFAULT_OFF_DAY_ID);

        // Get all the timeFrameList where offDayId is less than or equal to SMALLER_OFF_DAY_ID
        defaultTimeFrameShouldNotBeFound("offDayId.lessThanOrEqual=" + SMALLER_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void getAllTimeFramesByOffDayIdIsLessThanSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where offDayId is less than DEFAULT_OFF_DAY_ID
        defaultTimeFrameShouldNotBeFound("offDayId.lessThan=" + DEFAULT_OFF_DAY_ID);

        // Get all the timeFrameList where offDayId is less than UPDATED_OFF_DAY_ID
        defaultTimeFrameShouldBeFound("offDayId.lessThan=" + UPDATED_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void getAllTimeFramesByOffDayIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        // Get all the timeFrameList where offDayId is greater than DEFAULT_OFF_DAY_ID
        defaultTimeFrameShouldNotBeFound("offDayId.greaterThan=" + DEFAULT_OFF_DAY_ID);

        // Get all the timeFrameList where offDayId is greater than SMALLER_OFF_DAY_ID
        defaultTimeFrameShouldBeFound("offDayId.greaterThan=" + SMALLER_OFF_DAY_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTimeFrameShouldBeFound(String filter) throws Exception {
        restTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeFrameEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].begin").value(hasItem(DEFAULT_BEGIN.intValue())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.intValue())))
            .andExpect(jsonPath("$.[*].offDayId").value(hasItem(DEFAULT_OFF_DAY_ID.intValue())));

        // Check, that the count call also returns 1
        restTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTimeFrameShouldNotBeFound(String filter) throws Exception {
        restTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTimeFrame() throws Exception {
        // Get the timeFrame
        restTimeFrameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTimeFrame() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        int databaseSizeBeforeUpdate = timeFrameRepository.findAll().size();

        // Update the timeFrame
        TimeFrameEntity updatedTimeFrameEntity = timeFrameRepository.findById(timeFrameEntity.getId()).get();
        // Disconnect from session so that the updates on updatedTimeFrameEntity are not directly saved in db
        em.detach(updatedTimeFrameEntity);
        updatedTimeFrameEntity.begin(UPDATED_BEGIN).end(UPDATED_END).offDayId(UPDATED_OFF_DAY_ID);
        TimeFrameDTO timeFrameDTO = timeFrameMapper.toDto(updatedTimeFrameEntity);

        restTimeFrameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeFrameDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeFrameDTO))
            )
            .andExpect(status().isOk());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeUpdate);
        TimeFrameEntity testTimeFrame = timeFrameList.get(timeFrameList.size() - 1);
        assertThat(testTimeFrame.getBegin()).isEqualTo(UPDATED_BEGIN);
        assertThat(testTimeFrame.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testTimeFrame.getOffDayId()).isEqualTo(UPDATED_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void putNonExistingTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = timeFrameRepository.findAll().size();
        timeFrameEntity.setId(count.incrementAndGet());

        // Create the TimeFrame
        TimeFrameDTO timeFrameDTO = timeFrameMapper.toDto(timeFrameEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeFrameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, timeFrameDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeFrameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = timeFrameRepository.findAll().size();
        timeFrameEntity.setId(count.incrementAndGet());

        // Create the TimeFrame
        TimeFrameDTO timeFrameDTO = timeFrameMapper.toDto(timeFrameEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeFrameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(timeFrameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = timeFrameRepository.findAll().size();
        timeFrameEntity.setId(count.incrementAndGet());

        // Create the TimeFrame
        TimeFrameDTO timeFrameDTO = timeFrameMapper.toDto(timeFrameEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeFrameMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(timeFrameDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTimeFrameWithPatch() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        int databaseSizeBeforeUpdate = timeFrameRepository.findAll().size();

        // Update the timeFrame using partial update
        TimeFrameEntity partialUpdatedTimeFrameEntity = new TimeFrameEntity();
        partialUpdatedTimeFrameEntity.setId(timeFrameEntity.getId());

        restTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeFrameEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeFrameEntity))
            )
            .andExpect(status().isOk());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeUpdate);
        TimeFrameEntity testTimeFrame = timeFrameList.get(timeFrameList.size() - 1);
        assertThat(testTimeFrame.getBegin()).isEqualTo(DEFAULT_BEGIN);
        assertThat(testTimeFrame.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testTimeFrame.getOffDayId()).isEqualTo(DEFAULT_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void fullUpdateTimeFrameWithPatch() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        int databaseSizeBeforeUpdate = timeFrameRepository.findAll().size();

        // Update the timeFrame using partial update
        TimeFrameEntity partialUpdatedTimeFrameEntity = new TimeFrameEntity();
        partialUpdatedTimeFrameEntity.setId(timeFrameEntity.getId());

        partialUpdatedTimeFrameEntity.begin(UPDATED_BEGIN).end(UPDATED_END).offDayId(UPDATED_OFF_DAY_ID);

        restTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTimeFrameEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTimeFrameEntity))
            )
            .andExpect(status().isOk());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeUpdate);
        TimeFrameEntity testTimeFrame = timeFrameList.get(timeFrameList.size() - 1);
        assertThat(testTimeFrame.getBegin()).isEqualTo(UPDATED_BEGIN);
        assertThat(testTimeFrame.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testTimeFrame.getOffDayId()).isEqualTo(UPDATED_OFF_DAY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = timeFrameRepository.findAll().size();
        timeFrameEntity.setId(count.incrementAndGet());

        // Create the TimeFrame
        TimeFrameDTO timeFrameDTO = timeFrameMapper.toDto(timeFrameEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, timeFrameDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeFrameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = timeFrameRepository.findAll().size();
        timeFrameEntity.setId(count.incrementAndGet());

        // Create the TimeFrame
        TimeFrameDTO timeFrameDTO = timeFrameMapper.toDto(timeFrameEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(timeFrameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = timeFrameRepository.findAll().size();
        timeFrameEntity.setId(count.incrementAndGet());

        // Create the TimeFrame
        TimeFrameDTO timeFrameDTO = timeFrameMapper.toDto(timeFrameEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(timeFrameDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TimeFrame in the database
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTimeFrame() throws Exception {
        // Initialize the database
        timeFrameRepository.saveAndFlush(timeFrameEntity);

        int databaseSizeBeforeDelete = timeFrameRepository.findAll().size();

        // Delete the timeFrame
        restTimeFrameMockMvc
            .perform(delete(ENTITY_API_URL_ID, timeFrameEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TimeFrameEntity> timeFrameList = timeFrameRepository.findAll();
        assertThat(timeFrameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
