package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckStatusDTO.class);
        CheckStatusDTO checkStatusDTO1 = new CheckStatusDTO();
        checkStatusDTO1.setId(1L);
        CheckStatusDTO checkStatusDTO2 = new CheckStatusDTO();
        assertThat(checkStatusDTO1).isNotEqualTo(checkStatusDTO2);
        checkStatusDTO2.setId(checkStatusDTO1.getId());
        assertThat(checkStatusDTO1).isEqualTo(checkStatusDTO2);
        checkStatusDTO2.setId(2L);
        assertThat(checkStatusDTO1).isNotEqualTo(checkStatusDTO2);
        checkStatusDTO1.setId(null);
        assertThat(checkStatusDTO1).isNotEqualTo(checkStatusDTO2);
    }
}
