package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrmToCrdbResponseMapEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmToCrdbResponseMapEntity.class);
        CrmToCrdbResponseMapEntity crmToCrdbResponseMapEntity1 = new CrmToCrdbResponseMapEntity();
        crmToCrdbResponseMapEntity1.setId(1L);
        CrmToCrdbResponseMapEntity crmToCrdbResponseMapEntity2 = new CrmToCrdbResponseMapEntity();
        crmToCrdbResponseMapEntity2.setId(crmToCrdbResponseMapEntity1.getId());
        assertThat(crmToCrdbResponseMapEntity1).isEqualTo(crmToCrdbResponseMapEntity2);
        crmToCrdbResponseMapEntity2.setId(2L);
        assertThat(crmToCrdbResponseMapEntity1).isNotEqualTo(crmToCrdbResponseMapEntity2);
        crmToCrdbResponseMapEntity1.setId(null);
        assertThat(crmToCrdbResponseMapEntity1).isNotEqualTo(crmToCrdbResponseMapEntity2);
    }
}
