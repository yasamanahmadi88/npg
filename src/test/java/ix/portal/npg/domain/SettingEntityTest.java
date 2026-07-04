package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SettingEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SettingEntity.class);
        SettingEntity settingEntity1 = new SettingEntity();
        settingEntity1.setId(1L);
        SettingEntity settingEntity2 = new SettingEntity();
        settingEntity2.setId(settingEntity1.getId());
        assertThat(settingEntity1).isEqualTo(settingEntity2);
        settingEntity2.setId(2L);
        assertThat(settingEntity1).isNotEqualTo(settingEntity2);
        settingEntity1.setId(null);
        assertThat(settingEntity1).isNotEqualTo(settingEntity2);
    }
}
