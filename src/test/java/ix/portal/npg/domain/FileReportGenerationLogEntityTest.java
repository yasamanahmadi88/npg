package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileReportGenerationLogEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileReportGenerationLogEntity.class);
        FileReportGenerationLogEntity fileReportGenerationLogEntity1 = new FileReportGenerationLogEntity();
        fileReportGenerationLogEntity1.setId(1L);
        FileReportGenerationLogEntity fileReportGenerationLogEntity2 = new FileReportGenerationLogEntity();
        fileReportGenerationLogEntity2.setId(fileReportGenerationLogEntity1.getId());
        assertThat(fileReportGenerationLogEntity1).isEqualTo(fileReportGenerationLogEntity2);
        fileReportGenerationLogEntity2.setId(2L);
        assertThat(fileReportGenerationLogEntity1).isNotEqualTo(fileReportGenerationLogEntity2);
        fileReportGenerationLogEntity1.setId(null);
        assertThat(fileReportGenerationLogEntity1).isNotEqualTo(fileReportGenerationLogEntity2);
    }
}
