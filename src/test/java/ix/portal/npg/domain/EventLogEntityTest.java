package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventLogEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventLogEntity.class);
        EventLogEntity eventLogEntity1 = new EventLogEntity();
        eventLogEntity1.setId(1L);
        EventLogEntity eventLogEntity2 = new EventLogEntity();
        eventLogEntity2.setId(eventLogEntity1.getId());
        assertThat(eventLogEntity1).isEqualTo(eventLogEntity2);
        eventLogEntity2.setId(2L);
        assertThat(eventLogEntity1).isNotEqualTo(eventLogEntity2);
        eventLogEntity1.setId(null);
        assertThat(eventLogEntity1).isNotEqualTo(eventLogEntity2);
    }
}
