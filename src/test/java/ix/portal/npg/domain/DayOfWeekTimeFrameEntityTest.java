package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DayOfWeekTimeFrameEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayOfWeekTimeFrameEntity.class);
        DayOfWeekTimeFrameEntity dayOfWeekTimeFrameEntity1 = new DayOfWeekTimeFrameEntity();
        dayOfWeekTimeFrameEntity1.setId(1L);
        DayOfWeekTimeFrameEntity dayOfWeekTimeFrameEntity2 = new DayOfWeekTimeFrameEntity();
        dayOfWeekTimeFrameEntity2.setId(dayOfWeekTimeFrameEntity1.getId());
        assertThat(dayOfWeekTimeFrameEntity1).isEqualTo(dayOfWeekTimeFrameEntity2);
        dayOfWeekTimeFrameEntity2.setId(2L);
        assertThat(dayOfWeekTimeFrameEntity1).isNotEqualTo(dayOfWeekTimeFrameEntity2);
        dayOfWeekTimeFrameEntity1.setId(null);
        assertThat(dayOfWeekTimeFrameEntity1).isNotEqualTo(dayOfWeekTimeFrameEntity2);
    }
}
