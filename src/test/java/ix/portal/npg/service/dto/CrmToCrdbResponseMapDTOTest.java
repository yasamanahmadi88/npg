package ix.portal.npg.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrmToCrdbResponseMapDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrmToCrdbResponseMapDTO.class);
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO1 = new CrmToCrdbResponseMapDTO();
        crmToCrdbResponseMapDTO1.setId(1L);
        CrmToCrdbResponseMapDTO crmToCrdbResponseMapDTO2 = new CrmToCrdbResponseMapDTO();
        assertThat(crmToCrdbResponseMapDTO1).isNotEqualTo(crmToCrdbResponseMapDTO2);
        crmToCrdbResponseMapDTO2.setId(crmToCrdbResponseMapDTO1.getId());
        assertThat(crmToCrdbResponseMapDTO1).isEqualTo(crmToCrdbResponseMapDTO2);
        crmToCrdbResponseMapDTO2.setId(2L);
        assertThat(crmToCrdbResponseMapDTO1).isNotEqualTo(crmToCrdbResponseMapDTO2);
        crmToCrdbResponseMapDTO1.setId(null);
        assertThat(crmToCrdbResponseMapDTO1).isNotEqualTo(crmToCrdbResponseMapDTO2);
    }
}
