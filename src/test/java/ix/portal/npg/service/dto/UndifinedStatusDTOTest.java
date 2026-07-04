package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UndifinedStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UndifinedStatusDTO.class);
        UndifinedStatusDTO undifinedStatusDTO1 = new UndifinedStatusDTO();
        undifinedStatusDTO1.setId(1L);
        UndifinedStatusDTO undifinedStatusDTO2 = new UndifinedStatusDTO();
        assertThat(undifinedStatusDTO1).isNotEqualTo(undifinedStatusDTO2);
        undifinedStatusDTO2.setId(undifinedStatusDTO1.getId());
        assertThat(undifinedStatusDTO1).isEqualTo(undifinedStatusDTO2);
        undifinedStatusDTO2.setId(2L);
        assertThat(undifinedStatusDTO1).isNotEqualTo(undifinedStatusDTO2);
        undifinedStatusDTO1.setId(null);
        assertThat(undifinedStatusDTO1).isNotEqualTo(undifinedStatusDTO2);
    }
}
