package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OffDayDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OffDayDTO.class);
        OffDayDTO offDayDTO1 = new OffDayDTO();
        offDayDTO1.setId(1L);
        OffDayDTO offDayDTO2 = new OffDayDTO();
        assertThat(offDayDTO1).isNotEqualTo(offDayDTO2);
        offDayDTO2.setId(offDayDTO1.getId());
        assertThat(offDayDTO1).isEqualTo(offDayDTO2);
        offDayDTO2.setId(2L);
        assertThat(offDayDTO1).isNotEqualTo(offDayDTO2);
        offDayDTO1.setId(null);
        assertThat(offDayDTO1).isNotEqualTo(offDayDTO2);
    }
}
