package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TimeFrameDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeFrameDTO.class);
        TimeFrameDTO timeFrameDTO1 = new TimeFrameDTO();
        timeFrameDTO1.setId(1L);
        TimeFrameDTO timeFrameDTO2 = new TimeFrameDTO();
        assertThat(timeFrameDTO1).isNotEqualTo(timeFrameDTO2);
        timeFrameDTO2.setId(timeFrameDTO1.getId());
        assertThat(timeFrameDTO1).isEqualTo(timeFrameDTO2);
        timeFrameDTO2.setId(2L);
        assertThat(timeFrameDTO1).isNotEqualTo(timeFrameDTO2);
        timeFrameDTO1.setId(null);
        assertThat(timeFrameDTO1).isNotEqualTo(timeFrameDTO2);
    }
}
