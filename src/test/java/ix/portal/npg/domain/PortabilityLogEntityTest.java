package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PortabilityLogEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortabilityLogEntity.class);
        PortabilityLogEntity portabilityLogEntity1 = new PortabilityLogEntity();
        portabilityLogEntity1.setId(1L);
        PortabilityLogEntity portabilityLogEntity2 = new PortabilityLogEntity();
        portabilityLogEntity2.setId(portabilityLogEntity1.getId());
        assertThat(portabilityLogEntity1).isEqualTo(portabilityLogEntity2);
        portabilityLogEntity2.setId(2L);
        assertThat(portabilityLogEntity1).isNotEqualTo(portabilityLogEntity2);
        portabilityLogEntity1.setId(null);
        assertThat(portabilityLogEntity1).isNotEqualTo(portabilityLogEntity2);
    }
}
