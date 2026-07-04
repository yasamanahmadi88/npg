package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BusinessConfigEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessConfigEntity.class);
        BusinessConfigEntity businessConfigEntity1 = new BusinessConfigEntity();
        businessConfigEntity1.setId(1L + "");
        BusinessConfigEntity businessConfigEntity2 = new BusinessConfigEntity();
        businessConfigEntity2.setId(businessConfigEntity1.getId());
        assertThat(businessConfigEntity1).isEqualTo(businessConfigEntity2);
        businessConfigEntity2.setId(2L + "");
        assertThat(businessConfigEntity1).isNotEqualTo(businessConfigEntity2);
        businessConfigEntity1.setId(null);
        assertThat(businessConfigEntity1).isNotEqualTo(businessConfigEntity2);
    }
}
