package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OffDayEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OffDayEntity.class);
        OffDayEntity offDayEntity1 = new OffDayEntity();
        offDayEntity1.setId(1L);
        OffDayEntity offDayEntity2 = new OffDayEntity();
        offDayEntity2.setId(offDayEntity1.getId());
        assertThat(offDayEntity1).isEqualTo(offDayEntity2);
        offDayEntity2.setId(2L);
        assertThat(offDayEntity1).isNotEqualTo(offDayEntity2);
        offDayEntity1.setId(null);
        assertThat(offDayEntity1).isNotEqualTo(offDayEntity2);
    }
}
