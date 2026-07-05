package ix.portal.npg.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ix.portal.npg.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotificationTemplateEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationTemplateEntity.class);
        NotificationTemplateEntity notificationTemplateEntity1 = new NotificationTemplateEntity();
        notificationTemplateEntity1.setId(1L);
        NotificationTemplateEntity notificationTemplateEntity2 = new NotificationTemplateEntity();
        notificationTemplateEntity2.setId(notificationTemplateEntity1.getId());
        assertThat(notificationTemplateEntity1).isEqualTo(notificationTemplateEntity2);
        notificationTemplateEntity2.setId(2L);
        assertThat(notificationTemplateEntity1).isNotEqualTo(notificationTemplateEntity2);
        notificationTemplateEntity1.setId(null);
        assertThat(notificationTemplateEntity1).isNotEqualTo(notificationTemplateEntity2);
    }
}
