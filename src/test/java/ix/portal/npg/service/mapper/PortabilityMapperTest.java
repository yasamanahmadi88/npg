package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PortabilityMapperTest {

    private PortabilityMapper portabilityMapper;

    @BeforeEach
    public void setUp() {
        portabilityMapper = new PortabilityMapperImpl();
    }
}
