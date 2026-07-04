package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PortabilityEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortabilityEntity.class);
        PortabilityEntity portabilityEntity1 = new PortabilityEntity();
        portabilityEntity1.setId(1L);
        PortabilityEntity portabilityEntity2 = new PortabilityEntity();
        portabilityEntity2.setId(portabilityEntity1.getId());
        assertThat(portabilityEntity1).isEqualTo(portabilityEntity2);
        portabilityEntity2.setId(2L);
        assertThat(portabilityEntity1).isNotEqualTo(portabilityEntity2);
        portabilityEntity1.setId(null);
        assertThat(portabilityEntity1).isNotEqualTo(portabilityEntity2);
    }
}
