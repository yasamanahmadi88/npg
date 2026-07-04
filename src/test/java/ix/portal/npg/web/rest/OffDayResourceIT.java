package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.OffDayEntity;
import ix.portal.npg.repository.OffDayRepository;
import ix.portal.npg.service.criteria.OffDayCriteria;
import ix.portal.npg.service.dto.OffDayDTO;
import ix.portal.npg.service.mapper.OffDayMapper;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OffDayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "offDay" })class OffDayResourceIT {

    private static final Instant DEFAULT_OFF_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OFF_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_FULL_OFF = 1;
    private static final Integer UPDATED_FULL_OFF = 2;
    private static final Integer SMALLER_FULL_OFF = 1 - 1;

    private static final String ENTITY_API_URL = "/api/off-days";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OffDayRepository offDayRepository;

    @Autowired
    private OffDayMapper offDayMapper;

    @Autowired
    private EntityManager em;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc restOffDayMockMvc;

    private OffDayEntity offDayEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OffDayEntity createEntity(EntityManager em) {
        OffDayEntity offDayEntity = new OffDayEntity().offDate(DEFAULT_OFF_DATE).fullOff(DEFAULT_FULL_OFF);
        return offDayEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OffDayEntity createUpdatedEntity(EntityManager em) {
        OffDayEntity offDayEntity = new OffDayEntity().offDate(UPDATED_OFF_DATE).fullOff(UPDATED_FULL_OFF);
        return offDayEntity;
    }

    @BeforeEach
    public void initTest() {
        jdbcTemplate.update("delete from TBL_DAY_OF_WEEK_TIME_FRAME");
        jdbcTemplate.update("delete from TBL_TIME_FRAME");
        jdbcTemplate.update("delete from TBL_OFF_DAY");
        em.clear();

        offDayEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createOffDay() throws Exception {
        int databaseSizeBeforeCreate = offDayRepository.findAll().size();
        // Create the OffDay
        OffDayDTO offDayDTO = offDayMapper.toDto(offDayEntity);
        restOffDayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offDayDTO)))
            .andExpect(status().isCreated());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeCreate + 1);
        OffDayEntity testOffDay = offDayList.get(offDayList.size() - 1);
        assertThat(testOffDay.getOffDate()).isEqualTo(DEFAULT_OFF_DATE);
        assertThat(testOffDay.getFullOff()).isEqualTo(DEFAULT_FULL_OFF);
    }

    @Test
    @Transactional
    void createOffDayWithExistingId() throws Exception {
        // Create the OffDay with an existing ID
        offDayEntity.setId(1L);
        OffDayDTO offDayDTO = offDayMapper.toDto(offDayEntity);

        int databaseSizeBeforeCreate = offDayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffDayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offDayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOffDays() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList
        restOffDayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offDayEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].offDate").value(hasItem(DEFAULT_OFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].fullOff").value(hasItem(DEFAULT_FULL_OFF)));
    }

    @Test
    @Transactional
    void getOffDay() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get the offDay
        restOffDayMockMvc
            .perform(get(ENTITY_API_URL_ID, offDayEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offDayEntity.getId().intValue()))
            .andExpect(jsonPath("$.offDate").value(DEFAULT_OFF_DATE.toString()))
            .andExpect(jsonPath("$.fullOff").value(DEFAULT_FULL_OFF));
    }

    @Test
    @Transactional
    void getOffDaysByIdFiltering() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        Long id = offDayEntity.getId();

        defaultOffDayShouldBeFound("id.equals=" + id);
        defaultOffDayShouldNotBeFound("id.notEquals=" + id);

        defaultOffDayShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOffDayShouldNotBeFound("id.greaterThan=" + id);

        defaultOffDayShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOffDayShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOffDaysByOffDateIsEqualToSomething() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where offDate equals to DEFAULT_OFF_DATE
        defaultOffDayShouldBeFound("offDate.equals=" + DEFAULT_OFF_DATE);

        // Get all the offDayList where offDate equals to UPDATED_OFF_DATE
        defaultOffDayShouldNotBeFound("offDate.equals=" + UPDATED_OFF_DATE);
    }

    @Test
    @Transactional
    void getAllOffDaysByOffDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where offDate not equals to DEFAULT_OFF_DATE
        defaultOffDayShouldNotBeFound("offDate.notEquals=" + DEFAULT_OFF_DATE);

        // Get all the offDayList where offDate not equals to UPDATED_OFF_DATE
        defaultOffDayShouldBeFound("offDate.notEquals=" + UPDATED_OFF_DATE);
    }

    @Test
    @Transactional
    void getAllOffDaysByOffDateIsInShouldWork() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where offDate in DEFAULT_OFF_DATE or UPDATED_OFF_DATE
        defaultOffDayShouldBeFound("offDate.in=" + DEFAULT_OFF_DATE + "," + UPDATED_OFF_DATE);

        // Get all the offDayList where offDate equals to UPDATED_OFF_DATE
        defaultOffDayShouldNotBeFound("offDate.in=" + UPDATED_OFF_DATE);
    }

    @Test
    @Transactional
    void getAllOffDaysByOffDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where offDate is not null
        defaultOffDayShouldBeFound("offDate.specified=true");

        // Get all the offDayList where offDate is null
        defaultOffDayShouldNotBeFound("offDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOffDaysByFullOffIsEqualToSomething() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where fullOff equals to DEFAULT_FULL_OFF
        defaultOffDayShouldBeFound("fullOff.equals=" + DEFAULT_FULL_OFF);

        // Get all the offDayList where fullOff equals to UPDATED_FULL_OFF
        defaultOffDayShouldNotBeFound("fullOff.equals=" + UPDATED_FULL_OFF);
    }

    @Test
    @Transactional
    void getAllOffDaysByFullOffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where fullOff not equals to DEFAULT_FULL_OFF
        defaultOffDayShouldNotBeFound("fullOff.notEquals=" + DEFAULT_FULL_OFF);

        // Get all the offDayList where fullOff not equals to UPDATED_FULL_OFF
        defaultOffDayShouldBeFound("fullOff.notEquals=" + UPDATED_FULL_OFF);
    }

    @Test
    @Transactional
    void getAllOffDaysByFullOffIsInShouldWork() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where fullOff in DEFAULT_FULL_OFF or UPDATED_FULL_OFF
        defaultOffDayShouldBeFound("fullOff.in=" + DEFAULT_FULL_OFF + "," + UPDATED_FULL_OFF);

        // Get all the offDayList where fullOff equals to UPDATED_FULL_OFF
        defaultOffDayShouldNotBeFound("fullOff.in=" + UPDATED_FULL_OFF);
    }

    @Test
    @Transactional
    void getAllOffDaysByFullOffIsNullOrNotNull() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where fullOff is not null
        defaultOffDayShouldBeFound("fullOff.specified=true");

        // Get all the offDayList where fullOff is null
        defaultOffDayShouldNotBeFound("fullOff.specified=false");
    }

    @Test
    @Transactional
    void getAllOffDaysByFullOffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where fullOff is greater than or equal to DEFAULT_FULL_OFF
        defaultOffDayShouldBeFound("fullOff.greaterThanOrEqual=" + DEFAULT_FULL_OFF);

        // Get all the offDayList where fullOff is greater than or equal to UPDATED_FULL_OFF
        defaultOffDayShouldNotBeFound("fullOff.greaterThanOrEqual=" + UPDATED_FULL_OFF);
    }

    @Test
    @Transactional
    void getAllOffDaysByFullOffIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where fullOff is less than or equal to DEFAULT_FULL_OFF
        defaultOffDayShouldBeFound("fullOff.lessThanOrEqual=" + DEFAULT_FULL_OFF);

        // Get all the offDayList where fullOff is less than or equal to SMALLER_FULL_OFF
        defaultOffDayShouldNotBeFound("fullOff.lessThanOrEqual=" + SMALLER_FULL_OFF);
    }

    @Test
    @Transactional
    void getAllOffDaysByFullOffIsLessThanSomething() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where fullOff is less than DEFAULT_FULL_OFF
        defaultOffDayShouldNotBeFound("fullOff.lessThan=" + DEFAULT_FULL_OFF);

        // Get all the offDayList where fullOff is less than UPDATED_FULL_OFF
        defaultOffDayShouldBeFound("fullOff.lessThan=" + UPDATED_FULL_OFF);
    }

    @Test
    @Transactional
    void getAllOffDaysByFullOffIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        // Get all the offDayList where fullOff is greater than DEFAULT_FULL_OFF
        defaultOffDayShouldNotBeFound("fullOff.greaterThan=" + DEFAULT_FULL_OFF);

        // Get all the offDayList where fullOff is greater than SMALLER_FULL_OFF
        defaultOffDayShouldBeFound("fullOff.greaterThan=" + SMALLER_FULL_OFF);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOffDayShouldBeFound(String filter) throws Exception {
        restOffDayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offDayEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].offDate").value(hasItem(DEFAULT_OFF_DATE.toString())))
            .andExpect(jsonPath("$.[*].fullOff").value(hasItem(DEFAULT_FULL_OFF)));

        // Check, that the count call also returns 1
        restOffDayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOffDayShouldNotBeFound(String filter) throws Exception {
        restOffDayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOffDayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOffDay() throws Exception {
        // Get the offDay
        restOffDayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOffDay() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        int databaseSizeBeforeUpdate = offDayRepository.findAll().size();

        // Update the offDay
        OffDayEntity updatedOffDayEntity = offDayRepository.findById(offDayEntity.getId()).get();
        // Disconnect from session so that the updates on updatedOffDayEntity are not directly saved in db
        em.detach(updatedOffDayEntity);
        updatedOffDayEntity.offDate(UPDATED_OFF_DATE).fullOff(UPDATED_FULL_OFF);
        OffDayDTO offDayDTO = offDayMapper.toDto(updatedOffDayEntity);

        restOffDayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offDayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offDayDTO))
            )
            .andExpect(status().isOk());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeUpdate);
        OffDayEntity testOffDay = offDayList.get(offDayList.size() - 1);
        assertThat(testOffDay.getOffDate()).isEqualTo(UPDATED_OFF_DATE);
        assertThat(testOffDay.getFullOff()).isEqualTo(UPDATED_FULL_OFF);
    }

    @Test
    @Transactional
    void putNonExistingOffDay() throws Exception {
        int databaseSizeBeforeUpdate = offDayRepository.findAll().size();
        offDayEntity.setId(count.incrementAndGet());

        // Create the OffDay
        OffDayDTO offDayDTO = offDayMapper.toDto(offDayEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffDayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, offDayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offDayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOffDay() throws Exception {
        int databaseSizeBeforeUpdate = offDayRepository.findAll().size();
        offDayEntity.setId(count.incrementAndGet());

        // Create the OffDay
        OffDayDTO offDayDTO = offDayMapper.toDto(offDayEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffDayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(offDayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOffDay() throws Exception {
        int databaseSizeBeforeUpdate = offDayRepository.findAll().size();
        offDayEntity.setId(count.incrementAndGet());

        // Create the OffDay
        OffDayDTO offDayDTO = offDayMapper.toDto(offDayEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffDayMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offDayDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOffDayWithPatch() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        int databaseSizeBeforeUpdate = offDayRepository.findAll().size();

        // Update the offDay using partial update
        OffDayEntity partialUpdatedOffDayEntity = new OffDayEntity();
        partialUpdatedOffDayEntity.setId(offDayEntity.getId());

        partialUpdatedOffDayEntity.offDate(UPDATED_OFF_DATE);

        restOffDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffDayEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffDayEntity))
            )
            .andExpect(status().isOk());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeUpdate);
        OffDayEntity testOffDay = offDayList.get(offDayList.size() - 1);
        assertThat(testOffDay.getOffDate()).isEqualTo(UPDATED_OFF_DATE);
        assertThat(testOffDay.getFullOff()).isEqualTo(DEFAULT_FULL_OFF);
    }

    @Test
    @Transactional
    void fullUpdateOffDayWithPatch() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        int databaseSizeBeforeUpdate = offDayRepository.findAll().size();

        // Update the offDay using partial update
        OffDayEntity partialUpdatedOffDayEntity = new OffDayEntity();
        partialUpdatedOffDayEntity.setId(offDayEntity.getId());

        partialUpdatedOffDayEntity.offDate(UPDATED_OFF_DATE).fullOff(UPDATED_FULL_OFF);

        restOffDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOffDayEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOffDayEntity))
            )
            .andExpect(status().isOk());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeUpdate);
        OffDayEntity testOffDay = offDayList.get(offDayList.size() - 1);
        assertThat(testOffDay.getOffDate()).isEqualTo(UPDATED_OFF_DATE);
        assertThat(testOffDay.getFullOff()).isEqualTo(UPDATED_FULL_OFF);
    }

    @Test
    @Transactional
    void patchNonExistingOffDay() throws Exception {
        int databaseSizeBeforeUpdate = offDayRepository.findAll().size();
        offDayEntity.setId(count.incrementAndGet());

        // Create the OffDay
        OffDayDTO offDayDTO = offDayMapper.toDto(offDayEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, offDayDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offDayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOffDay() throws Exception {
        int databaseSizeBeforeUpdate = offDayRepository.findAll().size();
        offDayEntity.setId(count.incrementAndGet());

        // Create the OffDay
        OffDayDTO offDayDTO = offDayMapper.toDto(offDayEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffDayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(offDayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOffDay() throws Exception {
        int databaseSizeBeforeUpdate = offDayRepository.findAll().size();
        offDayEntity.setId(count.incrementAndGet());

        // Create the OffDay
        OffDayDTO offDayDTO = offDayMapper.toDto(offDayEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOffDayMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(offDayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OffDay in the database
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOffDay() throws Exception {
        // Initialize the database
        offDayRepository.saveAndFlush(offDayEntity);

        int databaseSizeBeforeDelete = offDayRepository.findAll().size();

        // Delete the offDay
        restOffDayMockMvc
            .perform(delete(ENTITY_API_URL_ID, offDayEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OffDayEntity> offDayList = offDayRepository.findAll();
        assertThat(offDayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
