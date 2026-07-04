package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.DayOfWeekTimeFrameEntity;
import ix.portal.npg.repository.DayOfWeekTimeFrameRepository;
import ix.portal.npg.service.criteria.DayOfWeekTimeFrameCriteria;
import ix.portal.npg.service.dto.DayOfWeekTimeFrameDTO;
import ix.portal.npg.service.mapper.DayOfWeekTimeFrameMapper;
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
 * Integration tests for the {@link DayOfWeekTimeFrameResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "dayOfWeekTimeFrame" })class DayOfWeekTimeFrameResourceIT {

    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;
    private static final Integer SMALLER_DAY = 1 - 1;

    private static final Long DEFAULT_BEGIN = 1L;
    private static final Long UPDATED_BEGIN = 2L;
    private static final Long SMALLER_BEGIN = 1L - 1L;

    private static final Long DEFAULT_END = 1L;
    private static final Long UPDATED_END = 2L;
    private static final Long SMALLER_END = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/day-of-week-time-frames";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DayOfWeekTimeFrameRepository dayOfWeekTimeFrameRepository;

    @Autowired
    private DayOfWeekTimeFrameMapper dayOfWeekTimeFrameMapper;

    @Autowired
    private EntityManager em;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc restDayOfWeekTimeFrameMockMvc;

    private DayOfWeekTimeFrameEntity dayOfWeekTimeFrameEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DayOfWeekTimeFrameEntity createEntity(EntityManager em) {
        DayOfWeekTimeFrameEntity dayOfWeekTimeFrameEntity = new DayOfWeekTimeFrameEntity()
            .day(DEFAULT_DAY)
            .begin(DEFAULT_BEGIN)
            .end(DEFAULT_END);
        return dayOfWeekTimeFrameEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DayOfWeekTimeFrameEntity createUpdatedEntity(EntityManager em) {
        DayOfWeekTimeFrameEntity dayOfWeekTimeFrameEntity = new DayOfWeekTimeFrameEntity()
            .day(UPDATED_DAY)
            .begin(UPDATED_BEGIN)
            .end(UPDATED_END);
        return dayOfWeekTimeFrameEntity;
    }

    @BeforeEach
    public void initTest() {
        jdbcTemplate.update("delete from TBL_DAY_OF_WEEK_TIME_FRAME");
        jdbcTemplate.update("delete from TBL_TIME_FRAME");
        jdbcTemplate.update("delete from TBL_OFF_DAY");
        em.clear();

        dayOfWeekTimeFrameEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createDayOfWeekTimeFrame() throws Exception {
        int databaseSizeBeforeCreate = dayOfWeekTimeFrameRepository.findAll().size();
        // Create the DayOfWeekTimeFrame
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameEntity);
        restDayOfWeekTimeFrameMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayOfWeekTimeFrameDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeCreate + 1);
        DayOfWeekTimeFrameEntity testDayOfWeekTimeFrame = dayOfWeekTimeFrameList.get(dayOfWeekTimeFrameList.size() - 1);
        assertThat(testDayOfWeekTimeFrame.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testDayOfWeekTimeFrame.getBegin()).isEqualTo(DEFAULT_BEGIN);
        assertThat(testDayOfWeekTimeFrame.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    void createDayOfWeekTimeFrameWithExistingId() throws Exception {
        // Create the DayOfWeekTimeFrame with an existing ID
        dayOfWeekTimeFrameEntity.setId(1L);
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameEntity);

        int databaseSizeBeforeCreate = dayOfWeekTimeFrameRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayOfWeekTimeFrameMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayOfWeekTimeFrameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFrames() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList
        restDayOfWeekTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayOfWeekTimeFrameEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].begin").value(hasItem(DEFAULT_BEGIN.intValue())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.intValue())));
    }

    @Test
    @Transactional
    void getDayOfWeekTimeFrame() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get the dayOfWeekTimeFrame
        restDayOfWeekTimeFrameMockMvc
            .perform(get(ENTITY_API_URL_ID, dayOfWeekTimeFrameEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dayOfWeekTimeFrameEntity.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.begin").value(DEFAULT_BEGIN.intValue()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.intValue()));
    }

    @Test
    @Transactional
    void getDayOfWeekTimeFramesByIdFiltering() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        Long id = dayOfWeekTimeFrameEntity.getId();

        defaultDayOfWeekTimeFrameShouldBeFound("id.equals=" + id);
        defaultDayOfWeekTimeFrameShouldNotBeFound("id.notEquals=" + id);

        defaultDayOfWeekTimeFrameShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDayOfWeekTimeFrameShouldNotBeFound("id.greaterThan=" + id);

        defaultDayOfWeekTimeFrameShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDayOfWeekTimeFrameShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where day equals to DEFAULT_DAY
        defaultDayOfWeekTimeFrameShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the dayOfWeekTimeFrameList where day equals to UPDATED_DAY
        defaultDayOfWeekTimeFrameShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where day not equals to DEFAULT_DAY
        defaultDayOfWeekTimeFrameShouldNotBeFound("day.notEquals=" + DEFAULT_DAY);

        // Get all the dayOfWeekTimeFrameList where day not equals to UPDATED_DAY
        defaultDayOfWeekTimeFrameShouldBeFound("day.notEquals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByDayIsInShouldWork() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where day in DEFAULT_DAY or UPDATED_DAY
        defaultDayOfWeekTimeFrameShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the dayOfWeekTimeFrameList where day equals to UPDATED_DAY
        defaultDayOfWeekTimeFrameShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where day is not null
        defaultDayOfWeekTimeFrameShouldBeFound("day.specified=true");

        // Get all the dayOfWeekTimeFrameList where day is null
        defaultDayOfWeekTimeFrameShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where day is greater than or equal to DEFAULT_DAY
        defaultDayOfWeekTimeFrameShouldBeFound("day.greaterThanOrEqual=" + DEFAULT_DAY);

        // Get all the dayOfWeekTimeFrameList where day is greater than or equal to UPDATED_DAY
        defaultDayOfWeekTimeFrameShouldNotBeFound("day.greaterThanOrEqual=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where day is less than or equal to DEFAULT_DAY
        defaultDayOfWeekTimeFrameShouldBeFound("day.lessThanOrEqual=" + DEFAULT_DAY);

        // Get all the dayOfWeekTimeFrameList where day is less than or equal to SMALLER_DAY
        defaultDayOfWeekTimeFrameShouldNotBeFound("day.lessThanOrEqual=" + SMALLER_DAY);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByDayIsLessThanSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where day is less than DEFAULT_DAY
        defaultDayOfWeekTimeFrameShouldNotBeFound("day.lessThan=" + DEFAULT_DAY);

        // Get all the dayOfWeekTimeFrameList where day is less than UPDATED_DAY
        defaultDayOfWeekTimeFrameShouldBeFound("day.lessThan=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where day is greater than DEFAULT_DAY
        defaultDayOfWeekTimeFrameShouldNotBeFound("day.greaterThan=" + DEFAULT_DAY);

        // Get all the dayOfWeekTimeFrameList where day is greater than SMALLER_DAY
        defaultDayOfWeekTimeFrameShouldBeFound("day.greaterThan=" + SMALLER_DAY);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByBeginIsEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where begin equals to DEFAULT_BEGIN
        defaultDayOfWeekTimeFrameShouldBeFound("begin.equals=" + DEFAULT_BEGIN);

        // Get all the dayOfWeekTimeFrameList where begin equals to UPDATED_BEGIN
        defaultDayOfWeekTimeFrameShouldNotBeFound("begin.equals=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByBeginIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where begin not equals to DEFAULT_BEGIN
        defaultDayOfWeekTimeFrameShouldNotBeFound("begin.notEquals=" + DEFAULT_BEGIN);

        // Get all the dayOfWeekTimeFrameList where begin not equals to UPDATED_BEGIN
        defaultDayOfWeekTimeFrameShouldBeFound("begin.notEquals=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByBeginIsInShouldWork() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where begin in DEFAULT_BEGIN or UPDATED_BEGIN
        defaultDayOfWeekTimeFrameShouldBeFound("begin.in=" + DEFAULT_BEGIN + "," + UPDATED_BEGIN);

        // Get all the dayOfWeekTimeFrameList where begin equals to UPDATED_BEGIN
        defaultDayOfWeekTimeFrameShouldNotBeFound("begin.in=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByBeginIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where begin is not null
        defaultDayOfWeekTimeFrameShouldBeFound("begin.specified=true");

        // Get all the dayOfWeekTimeFrameList where begin is null
        defaultDayOfWeekTimeFrameShouldNotBeFound("begin.specified=false");
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByBeginIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where begin is greater than or equal to DEFAULT_BEGIN
        defaultDayOfWeekTimeFrameShouldBeFound("begin.greaterThanOrEqual=" + DEFAULT_BEGIN);

        // Get all the dayOfWeekTimeFrameList where begin is greater than or equal to UPDATED_BEGIN
        defaultDayOfWeekTimeFrameShouldNotBeFound("begin.greaterThanOrEqual=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByBeginIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where begin is less than or equal to DEFAULT_BEGIN
        defaultDayOfWeekTimeFrameShouldBeFound("begin.lessThanOrEqual=" + DEFAULT_BEGIN);

        // Get all the dayOfWeekTimeFrameList where begin is less than or equal to SMALLER_BEGIN
        defaultDayOfWeekTimeFrameShouldNotBeFound("begin.lessThanOrEqual=" + SMALLER_BEGIN);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByBeginIsLessThanSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where begin is less than DEFAULT_BEGIN
        defaultDayOfWeekTimeFrameShouldNotBeFound("begin.lessThan=" + DEFAULT_BEGIN);

        // Get all the dayOfWeekTimeFrameList where begin is less than UPDATED_BEGIN
        defaultDayOfWeekTimeFrameShouldBeFound("begin.lessThan=" + UPDATED_BEGIN);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByBeginIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where begin is greater than DEFAULT_BEGIN
        defaultDayOfWeekTimeFrameShouldNotBeFound("begin.greaterThan=" + DEFAULT_BEGIN);

        // Get all the dayOfWeekTimeFrameList where begin is greater than SMALLER_BEGIN
        defaultDayOfWeekTimeFrameShouldBeFound("begin.greaterThan=" + SMALLER_BEGIN);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByEndIsEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where end equals to DEFAULT_END
        defaultDayOfWeekTimeFrameShouldBeFound("end.equals=" + DEFAULT_END);

        // Get all the dayOfWeekTimeFrameList where end equals to UPDATED_END
        defaultDayOfWeekTimeFrameShouldNotBeFound("end.equals=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByEndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where end not equals to DEFAULT_END
        defaultDayOfWeekTimeFrameShouldNotBeFound("end.notEquals=" + DEFAULT_END);

        // Get all the dayOfWeekTimeFrameList where end not equals to UPDATED_END
        defaultDayOfWeekTimeFrameShouldBeFound("end.notEquals=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByEndIsInShouldWork() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where end in DEFAULT_END or UPDATED_END
        defaultDayOfWeekTimeFrameShouldBeFound("end.in=" + DEFAULT_END + "," + UPDATED_END);

        // Get all the dayOfWeekTimeFrameList where end equals to UPDATED_END
        defaultDayOfWeekTimeFrameShouldNotBeFound("end.in=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where end is not null
        defaultDayOfWeekTimeFrameShouldBeFound("end.specified=true");

        // Get all the dayOfWeekTimeFrameList where end is null
        defaultDayOfWeekTimeFrameShouldNotBeFound("end.specified=false");
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByEndIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where end is greater than or equal to DEFAULT_END
        defaultDayOfWeekTimeFrameShouldBeFound("end.greaterThanOrEqual=" + DEFAULT_END);

        // Get all the dayOfWeekTimeFrameList where end is greater than or equal to UPDATED_END
        defaultDayOfWeekTimeFrameShouldNotBeFound("end.greaterThanOrEqual=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByEndIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where end is less than or equal to DEFAULT_END
        defaultDayOfWeekTimeFrameShouldBeFound("end.lessThanOrEqual=" + DEFAULT_END);

        // Get all the dayOfWeekTimeFrameList where end is less than or equal to SMALLER_END
        defaultDayOfWeekTimeFrameShouldNotBeFound("end.lessThanOrEqual=" + SMALLER_END);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByEndIsLessThanSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where end is less than DEFAULT_END
        defaultDayOfWeekTimeFrameShouldNotBeFound("end.lessThan=" + DEFAULT_END);

        // Get all the dayOfWeekTimeFrameList where end is less than UPDATED_END
        defaultDayOfWeekTimeFrameShouldBeFound("end.lessThan=" + UPDATED_END);
    }

    @Test
    @Transactional
    void getAllDayOfWeekTimeFramesByEndIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        // Get all the dayOfWeekTimeFrameList where end is greater than DEFAULT_END
        defaultDayOfWeekTimeFrameShouldNotBeFound("end.greaterThan=" + DEFAULT_END);

        // Get all the dayOfWeekTimeFrameList where end is greater than SMALLER_END
        defaultDayOfWeekTimeFrameShouldBeFound("end.greaterThan=" + SMALLER_END);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDayOfWeekTimeFrameShouldBeFound(String filter) throws Exception {
        restDayOfWeekTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayOfWeekTimeFrameEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].begin").value(hasItem(DEFAULT_BEGIN.intValue())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.intValue())));

        // Check, that the count call also returns 1
        restDayOfWeekTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDayOfWeekTimeFrameShouldNotBeFound(String filter) throws Exception {
        restDayOfWeekTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDayOfWeekTimeFrameMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDayOfWeekTimeFrame() throws Exception {
        // Get the dayOfWeekTimeFrame
        restDayOfWeekTimeFrameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDayOfWeekTimeFrame() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        int databaseSizeBeforeUpdate = dayOfWeekTimeFrameRepository.findAll().size();

        // Update the dayOfWeekTimeFrame
        DayOfWeekTimeFrameEntity updatedDayOfWeekTimeFrameEntity = dayOfWeekTimeFrameRepository
            .findById(dayOfWeekTimeFrameEntity.getId())
            .get();
        // Disconnect from session so that the updates on updatedDayOfWeekTimeFrameEntity are not directly saved in db
        em.detach(updatedDayOfWeekTimeFrameEntity);
        updatedDayOfWeekTimeFrameEntity.day(UPDATED_DAY).begin(UPDATED_BEGIN).end(UPDATED_END);
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameMapper.toDto(updatedDayOfWeekTimeFrameEntity);

        restDayOfWeekTimeFrameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dayOfWeekTimeFrameDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayOfWeekTimeFrameDTO))
            )
            .andExpect(status().isOk());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeUpdate);
        DayOfWeekTimeFrameEntity testDayOfWeekTimeFrame = dayOfWeekTimeFrameList.get(dayOfWeekTimeFrameList.size() - 1);
        assertThat(testDayOfWeekTimeFrame.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testDayOfWeekTimeFrame.getBegin()).isEqualTo(UPDATED_BEGIN);
        assertThat(testDayOfWeekTimeFrame.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void putNonExistingDayOfWeekTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = dayOfWeekTimeFrameRepository.findAll().size();
        dayOfWeekTimeFrameEntity.setId(count.incrementAndGet());

        // Create the DayOfWeekTimeFrame
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDayOfWeekTimeFrameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dayOfWeekTimeFrameDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayOfWeekTimeFrameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDayOfWeekTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = dayOfWeekTimeFrameRepository.findAll().size();
        dayOfWeekTimeFrameEntity.setId(count.incrementAndGet());

        // Create the DayOfWeekTimeFrame
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayOfWeekTimeFrameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayOfWeekTimeFrameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDayOfWeekTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = dayOfWeekTimeFrameRepository.findAll().size();
        dayOfWeekTimeFrameEntity.setId(count.incrementAndGet());

        // Create the DayOfWeekTimeFrame
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayOfWeekTimeFrameMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayOfWeekTimeFrameDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDayOfWeekTimeFrameWithPatch() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        int databaseSizeBeforeUpdate = dayOfWeekTimeFrameRepository.findAll().size();

        // Update the dayOfWeekTimeFrame using partial update
        DayOfWeekTimeFrameEntity partialUpdatedDayOfWeekTimeFrameEntity = new DayOfWeekTimeFrameEntity();
        partialUpdatedDayOfWeekTimeFrameEntity.setId(dayOfWeekTimeFrameEntity.getId());

        partialUpdatedDayOfWeekTimeFrameEntity.end(UPDATED_END);

        restDayOfWeekTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDayOfWeekTimeFrameEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDayOfWeekTimeFrameEntity))
            )
            .andExpect(status().isOk());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeUpdate);
        DayOfWeekTimeFrameEntity testDayOfWeekTimeFrame = dayOfWeekTimeFrameList.get(dayOfWeekTimeFrameList.size() - 1);
        assertThat(testDayOfWeekTimeFrame.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testDayOfWeekTimeFrame.getBegin()).isEqualTo(DEFAULT_BEGIN);
        assertThat(testDayOfWeekTimeFrame.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void fullUpdateDayOfWeekTimeFrameWithPatch() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        int databaseSizeBeforeUpdate = dayOfWeekTimeFrameRepository.findAll().size();

        // Update the dayOfWeekTimeFrame using partial update
        DayOfWeekTimeFrameEntity partialUpdatedDayOfWeekTimeFrameEntity = new DayOfWeekTimeFrameEntity();
        partialUpdatedDayOfWeekTimeFrameEntity.setId(dayOfWeekTimeFrameEntity.getId());

        partialUpdatedDayOfWeekTimeFrameEntity.day(UPDATED_DAY).begin(UPDATED_BEGIN).end(UPDATED_END);

        restDayOfWeekTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDayOfWeekTimeFrameEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDayOfWeekTimeFrameEntity))
            )
            .andExpect(status().isOk());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeUpdate);
        DayOfWeekTimeFrameEntity testDayOfWeekTimeFrame = dayOfWeekTimeFrameList.get(dayOfWeekTimeFrameList.size() - 1);
        assertThat(testDayOfWeekTimeFrame.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testDayOfWeekTimeFrame.getBegin()).isEqualTo(UPDATED_BEGIN);
        assertThat(testDayOfWeekTimeFrame.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    void patchNonExistingDayOfWeekTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = dayOfWeekTimeFrameRepository.findAll().size();
        dayOfWeekTimeFrameEntity.setId(count.incrementAndGet());

        // Create the DayOfWeekTimeFrame
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDayOfWeekTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dayOfWeekTimeFrameDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dayOfWeekTimeFrameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDayOfWeekTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = dayOfWeekTimeFrameRepository.findAll().size();
        dayOfWeekTimeFrameEntity.setId(count.incrementAndGet());

        // Create the DayOfWeekTimeFrame
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayOfWeekTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dayOfWeekTimeFrameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDayOfWeekTimeFrame() throws Exception {
        int databaseSizeBeforeUpdate = dayOfWeekTimeFrameRepository.findAll().size();
        dayOfWeekTimeFrameEntity.setId(count.incrementAndGet());

        // Create the DayOfWeekTimeFrame
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO = dayOfWeekTimeFrameMapper.toDto(dayOfWeekTimeFrameEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayOfWeekTimeFrameMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dayOfWeekTimeFrameDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DayOfWeekTimeFrame in the database
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDayOfWeekTimeFrame() throws Exception {
        // Initialize the database
        dayOfWeekTimeFrameRepository.saveAndFlush(dayOfWeekTimeFrameEntity);

        int databaseSizeBeforeDelete = dayOfWeekTimeFrameRepository.findAll().size();

        // Delete the dayOfWeekTimeFrame
        restDayOfWeekTimeFrameMockMvc
            .perform(delete(ENTITY_API_URL_ID, dayOfWeekTimeFrameEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DayOfWeekTimeFrameEntity> dayOfWeekTimeFrameList = dayOfWeekTimeFrameRepository.findAll();
        assertThat(dayOfWeekTimeFrameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
