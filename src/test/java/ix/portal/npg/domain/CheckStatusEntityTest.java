package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckStatusEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckStatusEntity.class);
        CheckStatusEntity checkStatusEntity1 = new CheckStatusEntity();
        checkStatusEntity1.setId(1L);
        CheckStatusEntity checkStatusEntity2 = new CheckStatusEntity();
        checkStatusEntity2.setId(checkStatusEntity1.getId());
        assertThat(checkStatusEntity1).isEqualTo(checkStatusEntity2);
        checkStatusEntity2.setId(2L);
        assertThat(checkStatusEntity1).isNotEqualTo(checkStatusEntity2);
        checkStatusEntity1.setId(null);
        assertThat(checkStatusEntity1).isNotEqualTo(checkStatusEntity2);
    }
}
