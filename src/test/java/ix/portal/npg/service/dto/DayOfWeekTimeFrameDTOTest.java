package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DayOfWeekTimeFrameDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayOfWeekTimeFrameDTO.class);
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO1 = new DayOfWeekTimeFrameDTO();
        dayOfWeekTimeFrameDTO1.setId(1L);
        DayOfWeekTimeFrameDTO dayOfWeekTimeFrameDTO2 = new DayOfWeekTimeFrameDTO();
        assertThat(dayOfWeekTimeFrameDTO1).isNotEqualTo(dayOfWeekTimeFrameDTO2);
        dayOfWeekTimeFrameDTO2.setId(dayOfWeekTimeFrameDTO1.getId());
        assertThat(dayOfWeekTimeFrameDTO1).isEqualTo(dayOfWeekTimeFrameDTO2);
        dayOfWeekTimeFrameDTO2.setId(2L);
        assertThat(dayOfWeekTimeFrameDTO1).isNotEqualTo(dayOfWeekTimeFrameDTO2);
        dayOfWeekTimeFrameDTO1.setId(null);
        assertThat(dayOfWeekTimeFrameDTO1).isNotEqualTo(dayOfWeekTimeFrameDTO2);
    }
}
