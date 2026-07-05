package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PortabilityLogMapperTest {

    private PortabilityLogMapper portabilityLogMapper;

    @BeforeEach
    public void setUp() {
        portabilityLogMapper = new PortabilityLogMapperImpl();
    }
}
