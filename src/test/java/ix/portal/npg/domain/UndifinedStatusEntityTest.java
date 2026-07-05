package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UndifinedStatusEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UndifinedStatusEntity.class);
        UndifinedStatusEntity undifinedStatusEntity1 = new UndifinedStatusEntity();
        undifinedStatusEntity1.setId(1L);
        UndifinedStatusEntity undifinedStatusEntity2 = new UndifinedStatusEntity();
        undifinedStatusEntity2.setId(undifinedStatusEntity1.getId());
        assertThat(undifinedStatusEntity1).isEqualTo(undifinedStatusEntity2);
        undifinedStatusEntity2.setId(2L);
        assertThat(undifinedStatusEntity1).isNotEqualTo(undifinedStatusEntity2);
        undifinedStatusEntity1.setId(null);
        assertThat(undifinedStatusEntity1).isNotEqualTo(undifinedStatusEntity2);
    }
}
