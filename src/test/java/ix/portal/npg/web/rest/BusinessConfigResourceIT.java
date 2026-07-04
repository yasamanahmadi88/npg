/*
package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.BusinessConfigEntity;
import ix.portal.npg.repository.BusinessConfigRepository;
import ix.portal.npg.service.criteria.BusinessConfigCriteria;
import ix.portal.npg.service.dto.BusinessConfigDTO;
import ix.portal.npg.service.mapper.BusinessConfigMapper;
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
 * Integration tests for the {@link BusinessConfigResource} REST controller.
 *//*

@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "businessConfig" })class BusinessConfigResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/business-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BusinessConfigRepository businessConfigRepository;

    @Autowired
    private BusinessConfigMapper businessConfigMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessConfigMockMvc;

    private BusinessConfigEntity businessConfigEntity;

    */
/**
 * Create an entity for this test.
 *
 * This is a static method, as tests for other entities might also need it,
 * if they test an entity which requires the current entity.
 *//*

    public static BusinessConfigEntity createEntity(EntityManager em) {
        BusinessConfigEntity businessConfigEntity = new BusinessConfigEntity().value(DEFAULT_VALUE);
        return businessConfigEntity;
    }

    */
/**
 * Create an updated entity for this test.
 *
 * This is a static method, as tests for other entities might also need it,
 * if they test an entity which requires the current entity.
 *//*

    public static BusinessConfigEntity createUpdatedEntity(EntityManager em) {
        BusinessConfigEntity businessConfigEntity = new BusinessConfigEntity().value(UPDATED_VALUE);
        return businessConfigEntity;
    }

    @BeforeEach
    public void initTest() {
businessConfigEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createBusinessConfig() throws Exception {
        int databaseSizeBeforeCreate = businessConfigRepository.findAll().size();
        // Create the BusinessConfig
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(businessConfigEntity);
        restBusinessConfigMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessConfigEntity testBusinessConfig = businessConfigList.get(businessConfigList.size() - 1);
        assertThat(testBusinessConfig.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createBusinessConfigWithExistingId() throws Exception {
        // Create the BusinessConfig with an existing ID
        businessConfigEntity.setId(1L);
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(businessConfigEntity);

        int databaseSizeBeforeCreate = businessConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessConfigMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = businessConfigRepository.findAll().size();
        // set the field null
        businessConfigEntity.setValue(null);

        // Create the BusinessConfig, which fails.
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(businessConfigEntity);

        restBusinessConfigMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isBadRequest());

        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBusinessConfigs() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        // Get all the businessConfigList
        restBusinessConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessConfigEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getBusinessConfig() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        // Get the businessConfig
        restBusinessConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, businessConfigEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessConfigEntity.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getBusinessConfigsByIdFiltering() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        Long id = businessConfigEntity.getId();

        defaultBusinessConfigShouldBeFound("id.equals=" + id);
        defaultBusinessConfigShouldNotBeFound("id.notEquals=" + id);

        defaultBusinessConfigShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBusinessConfigShouldNotBeFound("id.greaterThan=" + id);

        defaultBusinessConfigShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBusinessConfigShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBusinessConfigsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        // Get all the businessConfigList where value equals to DEFAULT_VALUE
        defaultBusinessConfigShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the businessConfigList where value equals to UPDATED_VALUE
        defaultBusinessConfigShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllBusinessConfigsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        // Get all the businessConfigList where value not equals to DEFAULT_VALUE
        defaultBusinessConfigShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the businessConfigList where value not equals to UPDATED_VALUE
        defaultBusinessConfigShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllBusinessConfigsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        // Get all the businessConfigList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultBusinessConfigShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the businessConfigList where value equals to UPDATED_VALUE
        defaultBusinessConfigShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllBusinessConfigsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        // Get all the businessConfigList where value is not null
        defaultBusinessConfigShouldBeFound("value.specified=true");

        // Get all the businessConfigList where value is null
        defaultBusinessConfigShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllBusinessConfigsByValueContainsSomething() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        // Get all the businessConfigList where value contains DEFAULT_VALUE
        defaultBusinessConfigShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the businessConfigList where value contains UPDATED_VALUE
        defaultBusinessConfigShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllBusinessConfigsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        // Get all the businessConfigList where value does not contain DEFAULT_VALUE
        defaultBusinessConfigShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the businessConfigList where value does not contain UPDATED_VALUE
        defaultBusinessConfigShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    */
/**
 * Executes the search, and checks that the default entity is returned.
 *//*

    private void defaultBusinessConfigShouldBeFound(String filter) throws Exception {
        restBusinessConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessConfigEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));

        // Check, that the count call also returns 1
        restBusinessConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    */
/**
 * Executes the search, and checks that the default entity is not returned.
 *//*

    private void defaultBusinessConfigShouldNotBeFound(String filter) throws Exception {
        restBusinessConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBusinessConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBusinessConfig() throws Exception {
        // Get the businessConfig
        restBusinessConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBusinessConfig() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        int databaseSizeBeforeUpdate = businessConfigRepository.findAll().size();

        // Update the businessConfig
        BusinessConfigEntity updatedBusinessConfigEntity = businessConfigRepository.findById(businessConfigEntity.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessConfigEntity are not directly saved in db
        em.detach(updatedBusinessConfigEntity);
        updatedBusinessConfigEntity.value(UPDATED_VALUE);
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(updatedBusinessConfigEntity);

        restBusinessConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isOk());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeUpdate);
        BusinessConfigEntity testBusinessConfig = businessConfigList.get(businessConfigList.size() - 1);
        assertThat(testBusinessConfig.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingBusinessConfig() throws Exception {
        int databaseSizeBeforeUpdate = businessConfigRepository.findAll().size();
        businessConfigEntity.setId(count.incrementAndGet());

        // Create the BusinessConfig
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(businessConfigEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, businessConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBusinessConfig() throws Exception {
        int databaseSizeBeforeUpdate = businessConfigRepository.findAll().size();
        businessConfigEntity.setId(count.incrementAndGet());

        // Create the BusinessConfig
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(businessConfigEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBusinessConfig() throws Exception {
        int databaseSizeBeforeUpdate = businessConfigRepository.findAll().size();
        businessConfigEntity.setId(count.incrementAndGet());

        // Create the BusinessConfig
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(businessConfigEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessConfigMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBusinessConfigWithPatch() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        int databaseSizeBeforeUpdate = businessConfigRepository.findAll().size();

        // Update the businessConfig using partial update
        BusinessConfigEntity partialUpdatedBusinessConfigEntity = new BusinessConfigEntity();
        partialUpdatedBusinessConfigEntity.setId(businessConfigEntity.getId());

        partialUpdatedBusinessConfigEntity.value(UPDATED_VALUE);

        restBusinessConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessConfigEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessConfigEntity))
            )
            .andExpect(status().isOk());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeUpdate);
        BusinessConfigEntity testBusinessConfig = businessConfigList.get(businessConfigList.size() - 1);
        assertThat(testBusinessConfig.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateBusinessConfigWithPatch() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        int databaseSizeBeforeUpdate = businessConfigRepository.findAll().size();

        // Update the businessConfig using partial update
        BusinessConfigEntity partialUpdatedBusinessConfigEntity = new BusinessConfigEntity();
        partialUpdatedBusinessConfigEntity.setId(businessConfigEntity.getId());

        partialUpdatedBusinessConfigEntity.value(UPDATED_VALUE);

        restBusinessConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBusinessConfigEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBusinessConfigEntity))
            )
            .andExpect(status().isOk());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeUpdate);
        BusinessConfigEntity testBusinessConfig = businessConfigList.get(businessConfigList.size() - 1);
        assertThat(testBusinessConfig.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingBusinessConfig() throws Exception {
        int databaseSizeBeforeUpdate = businessConfigRepository.findAll().size();
        businessConfigEntity.setId(count.incrementAndGet());

        // Create the BusinessConfig
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(businessConfigEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, businessConfigDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBusinessConfig() throws Exception {
        int databaseSizeBeforeUpdate = businessConfigRepository.findAll().size();
        businessConfigEntity.setId(count.incrementAndGet());

        // Create the BusinessConfig
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(businessConfigEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBusinessConfig() throws Exception {
        int databaseSizeBeforeUpdate = businessConfigRepository.findAll().size();
        businessConfigEntity.setId(count.incrementAndGet());

        // Create the BusinessConfig
        BusinessConfigDTO businessConfigDTO = businessConfigMapper.toDto(businessConfigEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBusinessConfigMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(businessConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BusinessConfig in the database
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBusinessConfig() throws Exception {
        // Initialize the database
        businessConfigRepository.saveAndFlush(businessConfigEntity);

        int databaseSizeBeforeDelete = businessConfigRepository.findAll().size();

        // Delete the businessConfig
        restBusinessConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, businessConfigEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessConfigEntity> businessConfigList = businessConfigRepository.findAll();
        assertThat(businessConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
*/
