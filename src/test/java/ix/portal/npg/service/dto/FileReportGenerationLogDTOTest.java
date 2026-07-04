package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileReportGenerationLogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileReportGenerationLogDTO.class);
        FileReportGenerationLogDTO fileReportGenerationLogDTO1 = new FileReportGenerationLogDTO();
        fileReportGenerationLogDTO1.setId(1L);
        FileReportGenerationLogDTO fileReportGenerationLogDTO2 = new FileReportGenerationLogDTO();
        assertThat(fileReportGenerationLogDTO1).isNotEqualTo(fileReportGenerationLogDTO2);
        fileReportGenerationLogDTO2.setId(fileReportGenerationLogDTO1.getId());
        assertThat(fileReportGenerationLogDTO1).isEqualTo(fileReportGenerationLogDTO2);
        fileReportGenerationLogDTO2.setId(2L);
        assertThat(fileReportGenerationLogDTO1).isNotEqualTo(fileReportGenerationLogDTO2);
        fileReportGenerationLogDTO1.setId(null);
        assertThat(fileReportGenerationLogDTO1).isNotEqualTo(fileReportGenerationLogDTO2);
    }
}
