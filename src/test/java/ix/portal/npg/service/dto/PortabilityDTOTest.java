package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PortabilityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortabilityDTO.class);
        PortabilityDTO portabilityDTO1 = new PortabilityDTO();
        portabilityDTO1.setId(1L);
        PortabilityDTO portabilityDTO2 = new PortabilityDTO();
        assertThat(portabilityDTO1).isNotEqualTo(portabilityDTO2);
        portabilityDTO2.setId(portabilityDTO1.getId());
        assertThat(portabilityDTO1).isEqualTo(portabilityDTO2);
        portabilityDTO2.setId(2L);
        assertThat(portabilityDTO1).isNotEqualTo(portabilityDTO2);
        portabilityDTO1.setId(null);
        assertThat(portabilityDTO1).isNotEqualTo(portabilityDTO2);
    }
}
