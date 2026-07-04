package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.SettingEntity;
import ix.portal.npg.repository.SettingRepository;
import ix.portal.npg.service.criteria.SettingCriteria;
import ix.portal.npg.service.dto.SettingDTO;
import ix.portal.npg.service.mapper.SettingMapper;
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

/**
 * Integration tests for the {@link SettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "setting" })
class SettingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "TEST_SETTING_KEY_A";
    private static final String UPDATED_KEY = "TEST_SETTING_KEY_B";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSettingMockMvc;

    private SettingEntity settingEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SettingEntity createEntity(EntityManager em) {
        SettingEntity settingEntity = new SettingEntity().name(DEFAULT_NAME).key(DEFAULT_KEY).value(DEFAULT_VALUE);
        return settingEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SettingEntity createUpdatedEntity(EntityManager em) {
        SettingEntity settingEntity = new SettingEntity().name(UPDATED_NAME).key(UPDATED_KEY).value(UPDATED_VALUE);
        return settingEntity;
    }


    private void deleteExistingSettingTestRows() {
        em.createNativeQuery(
            """
            delete from setting
            where jhi_key in (
                'AAAAAAAAAA',
                'BBBBBBBBBB',
                'TEST_SETTING_KEY_A',
                'TEST_SETTING_KEY_B'
            )
            """
        ).executeUpdate();

        em.flush();
        em.clear();
    }

    private void cleanSettingTestData() {
        em.createNativeQuery(
            """
            delete from setting
            where jhi_key in ('AAAAAAAAAA', 'BBBBBBBBBB')
               or name in ('AAAAAAAAAA', 'BBBBBBBBBB')
               or jhi_value in ('AAAAAAAAAA', 'BBBBBBBBBB')
            """
        ).executeUpdate();

        em.flush();
        em.clear();
    }
    @BeforeEach
    public void initTest() {
        deleteExistingSettingTestRows();
settingEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createSetting() throws Exception {
        int databaseSizeBeforeCreate = settingRepository.findAll().size();
        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);
        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isCreated());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate + 1);
        SettingEntity testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSetting.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testSetting.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createSettingWithExistingId() throws Exception {
        // Create the Setting with an existing ID
        settingEntity.setId(1L);
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        int databaseSizeBeforeCreate = settingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingRepository.findAll().size();
        // set the field null
        settingEntity.setName(null);

        // Create the Setting, which fails.
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingRepository.findAll().size();
        // set the field null
        settingEntity.setKey(null);

        // Create the Setting, which fails.
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = settingRepository.findAll().size();
        // set the field null
        settingEntity.setValue(null);

        // Create the Setting, which fails.
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        restSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isBadRequest());

        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSettings() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settingEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get the setting
        restSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, settingEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(settingEntity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getSettingsByIdFiltering() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        Long id = settingEntity.getId();

        defaultSettingShouldBeFound("id.equals=" + id);
        defaultSettingShouldNotBeFound("id.notEquals=" + id);

        defaultSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSettingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSettingsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where name equals to DEFAULT_NAME
        defaultSettingShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the settingList where name equals to UPDATED_NAME
        defaultSettingShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSettingsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where name not equals to DEFAULT_NAME
        defaultSettingShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the settingList where name not equals to UPDATED_NAME
        defaultSettingShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSettingsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSettingShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the settingList where name equals to UPDATED_NAME
        defaultSettingShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSettingsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where name is not null
        defaultSettingShouldBeFound("name.specified=true");

        // Get all the settingList where name is null
        defaultSettingShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsByNameContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where name contains DEFAULT_NAME
        defaultSettingShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the settingList where name contains UPDATED_NAME
        defaultSettingShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSettingsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where name does not contain DEFAULT_NAME
        defaultSettingShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the settingList where name does not contain UPDATED_NAME
        defaultSettingShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSettingsByKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where key equals to DEFAULT_KEY
        defaultSettingShouldBeFound("key.equals=" + DEFAULT_KEY);

        // Get all the settingList where key equals to UPDATED_KEY
        defaultSettingShouldNotBeFound("key.equals=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllSettingsByKeyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where key not equals to DEFAULT_KEY
        defaultSettingShouldNotBeFound("key.notEquals=" + DEFAULT_KEY);

        // Get all the settingList where key not equals to UPDATED_KEY
        defaultSettingShouldBeFound("key.notEquals=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllSettingsByKeyIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where key in DEFAULT_KEY or UPDATED_KEY
        defaultSettingShouldBeFound("key.in=" + DEFAULT_KEY + "," + UPDATED_KEY);

        // Get all the settingList where key equals to UPDATED_KEY
        defaultSettingShouldNotBeFound("key.in=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllSettingsByKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where key is not null
        defaultSettingShouldBeFound("key.specified=true");

        // Get all the settingList where key is null
        defaultSettingShouldNotBeFound("key.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsByKeyContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where key contains DEFAULT_KEY
        defaultSettingShouldBeFound("key.contains=" + DEFAULT_KEY);

        // Get all the settingList where key contains UPDATED_KEY
        defaultSettingShouldNotBeFound("key.contains=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllSettingsByKeyNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where key does not contain DEFAULT_KEY
        defaultSettingShouldNotBeFound("key.doesNotContain=" + DEFAULT_KEY);

        // Get all the settingList where key does not contain UPDATED_KEY
        defaultSettingShouldBeFound("key.doesNotContain=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    void getAllSettingsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where value equals to DEFAULT_VALUE
        defaultSettingShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the settingList where value equals to UPDATED_VALUE
        defaultSettingShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSettingsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where value not equals to DEFAULT_VALUE
        defaultSettingShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the settingList where value not equals to UPDATED_VALUE
        defaultSettingShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSettingsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultSettingShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the settingList where value equals to UPDATED_VALUE
        defaultSettingShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSettingsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where value is not null
        defaultSettingShouldBeFound("value.specified=true");

        // Get all the settingList where value is null
        defaultSettingShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllSettingsByValueContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where value contains DEFAULT_VALUE
        defaultSettingShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the settingList where value contains UPDATED_VALUE
        defaultSettingShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSettingsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        // Get all the settingList where value does not contain DEFAULT_VALUE
        defaultSettingShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the settingList where value does not contain UPDATED_VALUE
        defaultSettingShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSettingShouldBeFound(String filter) throws Exception {
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(settingEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSettingShouldNotBeFound(String filter) throws Exception {
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSetting() throws Exception {
        // Get the setting
        restSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting
        SettingEntity updatedSettingEntity = settingRepository.findById(settingEntity.getId()).get();
        // Disconnect from session so that the updates on updatedSettingEntity are not directly saved in db
        em.detach(updatedSettingEntity);
        updatedSettingEntity.name(UPDATED_NAME).key(UPDATED_KEY).value(UPDATED_VALUE);
        SettingDTO settingDTO = settingMapper.toDto(updatedSettingEntity);

        restSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, settingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        SettingEntity testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSetting.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testSetting.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        settingEntity.setId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, settingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        settingEntity.setId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        settingEntity.setId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(settingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSettingWithPatch() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting using partial update
        SettingEntity partialUpdatedSettingEntity = new SettingEntity();
        partialUpdatedSettingEntity.setId(settingEntity.getId());

        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSettingEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSettingEntity))
            )
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        SettingEntity testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSetting.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testSetting.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateSettingWithPatch() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        int databaseSizeBeforeUpdate = settingRepository.findAll().size();

        // Update the setting using partial update
        SettingEntity partialUpdatedSettingEntity = new SettingEntity();
        partialUpdatedSettingEntity.setId(settingEntity.getId());

        partialUpdatedSettingEntity.name(UPDATED_NAME).key(UPDATED_KEY).value(UPDATED_VALUE);

        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSettingEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSettingEntity))
            )
            .andExpect(status().isOk());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
        SettingEntity testSetting = settingList.get(settingList.size() - 1);
        assertThat(testSetting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSetting.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testSetting.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        settingEntity.setId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, settingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        settingEntity.setId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSetting() throws Exception {
        int databaseSizeBeforeUpdate = settingRepository.findAll().size();
        settingEntity.setId(count.incrementAndGet());

        // Create the Setting
        SettingDTO settingDTO = settingMapper.toDto(settingEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSettingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(settingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Setting in the database
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSetting() throws Exception {
        // Initialize the database
        settingRepository.saveAndFlush(settingEntity);

        int databaseSizeBeforeDelete = settingRepository.findAll().size();

        // Delete the setting
        restSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, settingEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SettingEntity> settingList = settingRepository.findAll();
        assertThat(settingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
