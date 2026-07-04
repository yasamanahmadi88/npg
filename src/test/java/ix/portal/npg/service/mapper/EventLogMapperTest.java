package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventLogMapperTest {

    private EventLogMapper eventLogMapper;

    @BeforeEach
    public void setUp() {
        eventLogMapper = new EventLogMapperImpl();
    }
}
