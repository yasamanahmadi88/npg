package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationTemplateMapperTest {

    private NotificationTemplateMapper notificationTemplateMapper;

    @BeforeEach
    public void setUp() {
        notificationTemplateMapper = new NotificationTemplateMapperImpl();
    }
}
