package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.ResourceAuthorityEntity;
import ix.portal.npg.domain.ResourceEntity;
import ix.portal.npg.domain.enumeration.Verb;
import ix.portal.npg.repository.ResourceAuthorityRepository;
import ix.portal.npg.service.criteria.ResourceAuthorityCriteria;
import ix.portal.npg.service.dto.ResourceAuthorityDTO;
import ix.portal.npg.service.mapper.ResourceAuthorityMapper;
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
 * Integration tests for the {@link ResourceAuthorityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "resourceAuthority" })
class ResourceAuthorityResourceIT {

    private static final Verb DEFAULT_VERB = Verb.NO_GRANT;
    private static final Verb UPDATED_VERB = Verb.VIEW;

    private static final Long DEFAULT_AUTHORITY_ID = 1L;
    private static final Long UPDATED_AUTHORITY_ID = 2L;
    private static final Long SMALLER_AUTHORITY_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/resource-authorities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ResourceAuthorityRepository resourceAuthorityRepository;

    @Autowired
    private ResourceAuthorityMapper resourceAuthorityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResourceAuthorityMockMvc;

    private ResourceAuthorityEntity resourceAuthorityEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceAuthorityEntity createEntity(EntityManager em) {
        ResourceAuthorityEntity resourceAuthorityEntity = new ResourceAuthorityEntity()
            .verb(DEFAULT_VERB)
            .authorityId(DEFAULT_AUTHORITY_ID);
        return resourceAuthorityEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceAuthorityEntity createUpdatedEntity(EntityManager em) {
        ResourceAuthorityEntity resourceAuthorityEntity = new ResourceAuthorityEntity()
            .verb(UPDATED_VERB)
            .authorityId(UPDATED_AUTHORITY_ID);
        return resourceAuthorityEntity;
    }


    private void deleteExistingResourceAuthorityTestRows() {
        em.createNativeQuery("delete from jhi_resource_authority").executeUpdate();
        em.createNativeQuery(
            """
            delete from jhi_resource
            where name in ('AAAAAAAAAA', 'BBBBBBBBBB')
               or display_name in ('AAAAAAAAAA', 'BBBBBBBBBB')
               or api_uri in ('AAAAAAAAAA', 'BBBBBBBBBB')
            """
        ).executeUpdate();

        em.flush();
        em.clear();
    }
    @BeforeEach
    public void initTest() {

        deleteExistingResourceAuthorityTestRows();
        resourceAuthorityEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createResourceAuthority() throws Exception {
        int databaseSizeBeforeCreate = resourceAuthorityRepository.findAll().size();
        // Create the ResourceAuthority
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);
        restResourceAuthorityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceAuthorityEntity testResourceAuthority = resourceAuthorityList.get(resourceAuthorityList.size() - 1);
        assertThat(testResourceAuthority.getVerb()).isEqualTo(DEFAULT_VERB);
        assertThat(testResourceAuthority.getAuthorityId()).isEqualTo(DEFAULT_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void createResourceAuthorityWithExistingId() throws Exception {
        // Create the ResourceAuthority with an existing ID
        resourceAuthorityEntity.setId(1L);
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);

        int databaseSizeBeforeCreate = resourceAuthorityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceAuthorityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVerbIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAuthorityRepository.findAll().size();
        // set the field null
        resourceAuthorityEntity.setVerb(null);

        // Create the ResourceAuthority, which fails.
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);

        restResourceAuthorityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthorityIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceAuthorityRepository.findAll().size();
        // set the field null
        resourceAuthorityEntity.setAuthorityId(null);

        // Create the ResourceAuthority, which fails.
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);

        restResourceAuthorityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllResourceAuthorities() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList
        restResourceAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceAuthorityEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].verb").value(hasItem(DEFAULT_VERB.toString())))
            .andExpect(jsonPath("$.[*].authorityId").value(hasItem(DEFAULT_AUTHORITY_ID.intValue())));
    }

    @Test
    @Transactional
    void getResourceAuthority() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get the resourceAuthority
        restResourceAuthorityMockMvc
            .perform(get(ENTITY_API_URL_ID, resourceAuthorityEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resourceAuthorityEntity.getId().intValue()))
            .andExpect(jsonPath("$.verb").value(DEFAULT_VERB.toString()))
            .andExpect(jsonPath("$.authorityId").value(DEFAULT_AUTHORITY_ID.intValue()));
    }

    @Test
    @Transactional
    void getResourceAuthoritiesByIdFiltering() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        Long id = resourceAuthorityEntity.getId();

        defaultResourceAuthorityShouldBeFound("id.equals=" + id);
        defaultResourceAuthorityShouldNotBeFound("id.notEquals=" + id);

        defaultResourceAuthorityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResourceAuthorityShouldNotBeFound("id.greaterThan=" + id);

        defaultResourceAuthorityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResourceAuthorityShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByVerbIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where verb equals to DEFAULT_VERB
        defaultResourceAuthorityShouldBeFound("verb.equals=" + DEFAULT_VERB);

        // Get all the resourceAuthorityList where verb equals to UPDATED_VERB
        defaultResourceAuthorityShouldNotBeFound("verb.equals=" + UPDATED_VERB);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByVerbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where verb not equals to DEFAULT_VERB
        defaultResourceAuthorityShouldNotBeFound("verb.notEquals=" + DEFAULT_VERB);

        // Get all the resourceAuthorityList where verb not equals to UPDATED_VERB
        defaultResourceAuthorityShouldBeFound("verb.notEquals=" + UPDATED_VERB);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByVerbIsInShouldWork() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where verb in DEFAULT_VERB or UPDATED_VERB
        defaultResourceAuthorityShouldBeFound("verb.in=" + DEFAULT_VERB + "," + UPDATED_VERB);

        // Get all the resourceAuthorityList where verb equals to UPDATED_VERB
        defaultResourceAuthorityShouldNotBeFound("verb.in=" + UPDATED_VERB);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByVerbIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where verb is not null
        defaultResourceAuthorityShouldBeFound("verb.specified=true");

        // Get all the resourceAuthorityList where verb is null
        defaultResourceAuthorityShouldNotBeFound("verb.specified=false");
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByAuthorityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where authorityId equals to DEFAULT_AUTHORITY_ID
        defaultResourceAuthorityShouldBeFound("authorityId.equals=" + DEFAULT_AUTHORITY_ID);

        // Get all the resourceAuthorityList where authorityId equals to UPDATED_AUTHORITY_ID
        defaultResourceAuthorityShouldNotBeFound("authorityId.equals=" + UPDATED_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByAuthorityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where authorityId not equals to DEFAULT_AUTHORITY_ID
        defaultResourceAuthorityShouldNotBeFound("authorityId.notEquals=" + DEFAULT_AUTHORITY_ID);

        // Get all the resourceAuthorityList where authorityId not equals to UPDATED_AUTHORITY_ID
        defaultResourceAuthorityShouldBeFound("authorityId.notEquals=" + UPDATED_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByAuthorityIdIsInShouldWork() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where authorityId in DEFAULT_AUTHORITY_ID or UPDATED_AUTHORITY_ID
        defaultResourceAuthorityShouldBeFound("authorityId.in=" + DEFAULT_AUTHORITY_ID + "," + UPDATED_AUTHORITY_ID);

        // Get all the resourceAuthorityList where authorityId equals to UPDATED_AUTHORITY_ID
        defaultResourceAuthorityShouldNotBeFound("authorityId.in=" + UPDATED_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByAuthorityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where authorityId is not null
        defaultResourceAuthorityShouldBeFound("authorityId.specified=true");

        // Get all the resourceAuthorityList where authorityId is null
        defaultResourceAuthorityShouldNotBeFound("authorityId.specified=false");
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByAuthorityIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where authorityId is greater than or equal to DEFAULT_AUTHORITY_ID
        defaultResourceAuthorityShouldBeFound("authorityId.greaterThanOrEqual=" + DEFAULT_AUTHORITY_ID);

        // Get all the resourceAuthorityList where authorityId is greater than or equal to UPDATED_AUTHORITY_ID
        defaultResourceAuthorityShouldNotBeFound("authorityId.greaterThanOrEqual=" + UPDATED_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByAuthorityIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where authorityId is less than or equal to DEFAULT_AUTHORITY_ID
        defaultResourceAuthorityShouldBeFound("authorityId.lessThanOrEqual=" + DEFAULT_AUTHORITY_ID);

        // Get all the resourceAuthorityList where authorityId is less than or equal to SMALLER_AUTHORITY_ID
        defaultResourceAuthorityShouldNotBeFound("authorityId.lessThanOrEqual=" + SMALLER_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByAuthorityIdIsLessThanSomething() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where authorityId is less than DEFAULT_AUTHORITY_ID
        defaultResourceAuthorityShouldNotBeFound("authorityId.lessThan=" + DEFAULT_AUTHORITY_ID);

        // Get all the resourceAuthorityList where authorityId is less than UPDATED_AUTHORITY_ID
        defaultResourceAuthorityShouldBeFound("authorityId.lessThan=" + UPDATED_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByAuthorityIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        // Get all the resourceAuthorityList where authorityId is greater than DEFAULT_AUTHORITY_ID
        defaultResourceAuthorityShouldNotBeFound("authorityId.greaterThan=" + DEFAULT_AUTHORITY_ID);

        // Get all the resourceAuthorityList where authorityId is greater than SMALLER_AUTHORITY_ID
        defaultResourceAuthorityShouldBeFound("authorityId.greaterThan=" + SMALLER_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void getAllResourceAuthoritiesByResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);
        ResourceEntity resource = ResourceResourceIT.createEntity(em);
        em.persist(resource);
        em.flush();
        resourceAuthorityEntity.setResource(resource);
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);
        Long resourceId = resource.getId();

        // Get all the resourceAuthorityList where resource equals to resourceId
        defaultResourceAuthorityShouldBeFound("resourceId.equals=" + resourceId);

        // Get all the resourceAuthorityList where resource equals to (resourceId + 1)
        defaultResourceAuthorityShouldNotBeFound("resourceId.equals=" + (resourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResourceAuthorityShouldBeFound(String filter) throws Exception {
        restResourceAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceAuthorityEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].verb").value(hasItem(DEFAULT_VERB.toString())))
            .andExpect(jsonPath("$.[*].authorityId").value(hasItem(DEFAULT_AUTHORITY_ID.intValue())));

        // Check, that the count call also returns 1
        restResourceAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResourceAuthorityShouldNotBeFound(String filter) throws Exception {
        restResourceAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResourceAuthorityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingResourceAuthority() throws Exception {
        // Get the resourceAuthority
        restResourceAuthorityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewResourceAuthority() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        int databaseSizeBeforeUpdate = resourceAuthorityRepository.findAll().size();

        // Update the resourceAuthority
        ResourceAuthorityEntity updatedResourceAuthorityEntity = resourceAuthorityRepository
            .findById(resourceAuthorityEntity.getId())
            .get();
        // Disconnect from session so that the updates on updatedResourceAuthorityEntity are not directly saved in db
        em.detach(updatedResourceAuthorityEntity);
        updatedResourceAuthorityEntity.verb(UPDATED_VERB).authorityId(UPDATED_AUTHORITY_ID);
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(updatedResourceAuthorityEntity);

        restResourceAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceAuthorityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isOk());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeUpdate);
        ResourceAuthorityEntity testResourceAuthority = resourceAuthorityList.get(resourceAuthorityList.size() - 1);
        assertThat(testResourceAuthority.getVerb()).isEqualTo(UPDATED_VERB);
        assertThat(testResourceAuthority.getAuthorityId()).isEqualTo(UPDATED_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void putNonExistingResourceAuthority() throws Exception {
        int databaseSizeBeforeUpdate = resourceAuthorityRepository.findAll().size();
        resourceAuthorityEntity.setId(count.incrementAndGet());

        // Create the ResourceAuthority
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, resourceAuthorityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchResourceAuthority() throws Exception {
        int databaseSizeBeforeUpdate = resourceAuthorityRepository.findAll().size();
        resourceAuthorityEntity.setId(count.incrementAndGet());

        // Create the ResourceAuthority
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamResourceAuthority() throws Exception {
        int databaseSizeBeforeUpdate = resourceAuthorityRepository.findAll().size();
        resourceAuthorityEntity.setId(count.incrementAndGet());

        // Create the ResourceAuthority
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceAuthorityMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateResourceAuthorityWithPatch() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        int databaseSizeBeforeUpdate = resourceAuthorityRepository.findAll().size();

        // Update the resourceAuthority using partial update
        ResourceAuthorityEntity partialUpdatedResourceAuthorityEntity = new ResourceAuthorityEntity();
        partialUpdatedResourceAuthorityEntity.setId(resourceAuthorityEntity.getId());

        partialUpdatedResourceAuthorityEntity.verb(UPDATED_VERB);

        restResourceAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResourceAuthorityEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceAuthorityEntity))
            )
            .andExpect(status().isOk());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeUpdate);
        ResourceAuthorityEntity testResourceAuthority = resourceAuthorityList.get(resourceAuthorityList.size() - 1);
        assertThat(testResourceAuthority.getVerb()).isEqualTo(UPDATED_VERB);
        assertThat(testResourceAuthority.getAuthorityId()).isEqualTo(DEFAULT_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void fullUpdateResourceAuthorityWithPatch() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        int databaseSizeBeforeUpdate = resourceAuthorityRepository.findAll().size();

        // Update the resourceAuthority using partial update
        ResourceAuthorityEntity partialUpdatedResourceAuthorityEntity = new ResourceAuthorityEntity();
        partialUpdatedResourceAuthorityEntity.setId(resourceAuthorityEntity.getId());

        partialUpdatedResourceAuthorityEntity.verb(UPDATED_VERB).authorityId(UPDATED_AUTHORITY_ID);

        restResourceAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResourceAuthorityEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResourceAuthorityEntity))
            )
            .andExpect(status().isOk());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeUpdate);
        ResourceAuthorityEntity testResourceAuthority = resourceAuthorityList.get(resourceAuthorityList.size() - 1);
        assertThat(testResourceAuthority.getVerb()).isEqualTo(UPDATED_VERB);
        assertThat(testResourceAuthority.getAuthorityId()).isEqualTo(UPDATED_AUTHORITY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingResourceAuthority() throws Exception {
        int databaseSizeBeforeUpdate = resourceAuthorityRepository.findAll().size();
        resourceAuthorityEntity.setId(count.incrementAndGet());

        // Create the ResourceAuthority
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, resourceAuthorityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchResourceAuthority() throws Exception {
        int databaseSizeBeforeUpdate = resourceAuthorityRepository.findAll().size();
        resourceAuthorityEntity.setId(count.incrementAndGet());

        // Create the ResourceAuthority
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamResourceAuthority() throws Exception {
        int databaseSizeBeforeUpdate = resourceAuthorityRepository.findAll().size();
        resourceAuthorityEntity.setId(count.incrementAndGet());

        // Create the ResourceAuthority
        ResourceAuthorityDTO resourceAuthorityDTO = resourceAuthorityMapper.toDto(resourceAuthorityEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResourceAuthorityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(resourceAuthorityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ResourceAuthority in the database
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteResourceAuthority() throws Exception {
        // Initialize the database
        resourceAuthorityRepository.saveAndFlush(resourceAuthorityEntity);

        int databaseSizeBeforeDelete = resourceAuthorityRepository.findAll().size();

        // Delete the resourceAuthority
        restResourceAuthorityMockMvc
            .perform(delete(ENTITY_API_URL_ID, resourceAuthorityEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResourceAuthorityEntity> resourceAuthorityList = resourceAuthorityRepository.findAll();
        assertThat(resourceAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
