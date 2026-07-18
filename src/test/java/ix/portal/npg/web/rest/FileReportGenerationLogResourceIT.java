package ix.portal.npg.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ix.portal.npg.IntegrationTest;
import ix.portal.npg.domain.FileReportGenerationLogEntity;
import ix.portal.npg.repository.FileReportGenerationLogRepository;
import ix.portal.npg.service.dto.FileReportGenerationLogDTO;
import ix.portal.npg.service.mapper.FileReportGenerationLogMapper;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(authorities = { "ROLE_ADMIN", "900001", "fileReportGenerationLog" })
class FileReportGenerationLogResourceIT {

    private static final String DEFAULT_REPORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_NAME = "BBBBBBBBBB";
    private static final LocalDateTime DEFAULT_REPORT_DATE = LocalDateTime.of(2026, 1, 1, 0, 0);
    private static final LocalDateTime UPDATED_REPORT_DATE = LocalDateTime.of(2026, 2, 1, 0, 0);
    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";
    private static final Long DEFAULT_ROW_NUMBER = 1L;
    private static final Long UPDATED_ROW_NUMBER = 2L;
    private static final String DEFAULT_POR_NUMBER = "1234567890123456";
    private static final String UPDATED_POR_NUMBER = "6543210987654321";
    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/file-report-generation-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FileReportGenerationLogRepository fileReportGenerationLogRepository;

    @Autowired
    private FileReportGenerationLogMapper fileReportGenerationLogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileReportGenerationLogMockMvc;

    private FileReportGenerationLogEntity fileReportGenerationLogEntity;

    public static FileReportGenerationLogEntity createEntity(EntityManager em) {
        return new FileReportGenerationLogEntity()
            .reportName(DEFAULT_REPORT_NAME)
            .reportDate(DEFAULT_REPORT_DATE)
            .fileName(DEFAULT_FILE_NAME)
            .rowNumber(DEFAULT_ROW_NUMBER)
            .porNumber(DEFAULT_POR_NUMBER)
            .content(DEFAULT_CONTENT);
    }

    @BeforeEach
    void initTest() {
        fileReportGenerationLogEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createFileReportGenerationLog() throws Exception {
        int databaseSizeBeforeCreate = fileReportGenerationLogRepository.findAll().size();
        FileReportGenerationLogDTO fileReportGenerationLogDTO = fileReportGenerationLogMapper.toDto(fileReportGenerationLogEntity);

        restFileReportGenerationLogMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogDTO))
            )
            .andExpect(status().isCreated());

        List<FileReportGenerationLogEntity> fileReportGenerationLogList = fileReportGenerationLogRepository.findAll();
        assertThat(fileReportGenerationLogList).hasSize(databaseSizeBeforeCreate + 1);
        FileReportGenerationLogEntity testFileReportGenerationLog = fileReportGenerationLogList.get(fileReportGenerationLogList.size() - 1);
        assertThat(testFileReportGenerationLog.getReportName()).isEqualTo(DEFAULT_REPORT_NAME);
        assertThat(testFileReportGenerationLog.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllFileReportGenerationLogs() throws Exception {
        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);

        restFileReportGenerationLogMockMvc
            .perform(get(ENTITY_API_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(header().exists("X-Table-Count"))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileReportGenerationLogEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportName").value(hasItem(DEFAULT_REPORT_NAME)));
    }

    @Test
    @Transactional
    void getAllFileReportGenerationLogs_ignoresBlankReportNameEquals() throws Exception {
        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);

        // Mimics Angular sending empty mat-option as reportName.equals=
        restFileReportGenerationLogMockMvc
            .perform(
                get(ENTITY_API_URL)
                    .param("reportName.equals", "")
                    .param("porNumber.equals", "   ")
                    .param("page", "0")
                    .param("size", "20")
            )
            .andExpect(status().isOk())
            .andExpect(header().exists("X-Total-Count"))
            .andExpect(header().exists("X-Table-Count"))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileReportGenerationLogEntity.getId().intValue())));
    }

    @Test
    @Transactional
    void getFileReportGenerationLog() throws Exception {
        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);

        restFileReportGenerationLogMockMvc
            .perform(get(ENTITY_API_URL_ID, fileReportGenerationLogEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileReportGenerationLogEntity.getId().intValue()))
            .andExpect(jsonPath("$.reportName").value(DEFAULT_REPORT_NAME));
    }

    @Test
    @Transactional
    void updateFileReportGenerationLog() throws Exception {
        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);

        FileReportGenerationLogEntity updatedFileReportGenerationLog = fileReportGenerationLogRepository
            .findById(fileReportGenerationLogEntity.getId())
            .orElseThrow();
        em.detach(updatedFileReportGenerationLog);
        updatedFileReportGenerationLog
            .reportName(UPDATED_REPORT_NAME)
            .reportDate(UPDATED_REPORT_DATE)
            .fileName(UPDATED_FILE_NAME)
            .rowNumber(UPDATED_ROW_NUMBER)
            .porNumber(UPDATED_POR_NUMBER)
            .content(UPDATED_CONTENT);

        restFileReportGenerationLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFileReportGenerationLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileReportGenerationLogMapper.toDto(updatedFileReportGenerationLog)))
            )
            .andExpect(status().isOk());

        FileReportGenerationLogEntity testFileReportGenerationLog = fileReportGenerationLogRepository
            .findById(fileReportGenerationLogEntity.getId())
            .orElseThrow();
        assertThat(testFileReportGenerationLog.getReportName()).isEqualTo(UPDATED_REPORT_NAME);
        assertThat(testFileReportGenerationLog.getContent()).isEqualTo(UPDATED_CONTENT);
    }

    @Test
    @Transactional
    void deleteFileReportGenerationLog() throws Exception {
        fileReportGenerationLogRepository.saveAndFlush(fileReportGenerationLogEntity);
        int databaseSizeBeforeDelete = fileReportGenerationLogRepository.findAll().size();

        restFileReportGenerationLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileReportGenerationLogEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        assertThat(fileReportGenerationLogRepository.findAll()).hasSize(databaseSizeBeforeDelete - 1);
    }
}
