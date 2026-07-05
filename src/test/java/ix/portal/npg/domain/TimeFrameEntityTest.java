package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TimeFrameEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeFrameEntity.class);
        TimeFrameEntity timeFrameEntity1 = new TimeFrameEntity();
        timeFrameEntity1.setId(1L);
        TimeFrameEntity timeFrameEntity2 = new TimeFrameEntity();
        timeFrameEntity2.setId(timeFrameEntity1.getId());
        assertThat(timeFrameEntity1).isEqualTo(timeFrameEntity2);
        timeFrameEntity2.setId(2L);
        assertThat(timeFrameEntity1).isNotEqualTo(timeFrameEntity2);
        timeFrameEntity1.setId(null);
        assertThat(timeFrameEntity1).isNotEqualTo(timeFrameEntity2);
    }
}
