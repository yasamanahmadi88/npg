package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PortabilityLogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortabilityLogDTO.class);
        PortabilityLogDTO portabilityLogDTO1 = new PortabilityLogDTO();
        portabilityLogDTO1.setId(1L);
        PortabilityLogDTO portabilityLogDTO2 = new PortabilityLogDTO();
        assertThat(portabilityLogDTO1).isNotEqualTo(portabilityLogDTO2);
        portabilityLogDTO2.setId(portabilityLogDTO1.getId());
        assertThat(portabilityLogDTO1).isEqualTo(portabilityLogDTO2);
        portabilityLogDTO2.setId(2L);
        assertThat(portabilityLogDTO1).isNotEqualTo(portabilityLogDTO2);
        portabilityLogDTO1.setId(null);
        assertThat(portabilityLogDTO1).isNotEqualTo(portabilityLogDTO2);
    }
}
