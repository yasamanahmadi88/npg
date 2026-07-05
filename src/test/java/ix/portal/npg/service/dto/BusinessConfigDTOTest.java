package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessConfigDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessConfigDTO.class);
        BusinessConfigDTO businessConfigDTO1 = new BusinessConfigDTO();
        businessConfigDTO1.setId(1L + "");
        BusinessConfigDTO businessConfigDTO2 = new BusinessConfigDTO();
        assertThat(businessConfigDTO1).isNotEqualTo(businessConfigDTO2);
        businessConfigDTO2.setId(businessConfigDTO1.getId());
        assertThat(businessConfigDTO1).isEqualTo(businessConfigDTO2);
        businessConfigDTO2.setId(2L + "");
        assertThat(businessConfigDTO1).isNotEqualTo(businessConfigDTO2);
        businessConfigDTO1.setId(null);
        assertThat(businessConfigDTO1).isNotEqualTo(businessConfigDTO2);
    }
}
