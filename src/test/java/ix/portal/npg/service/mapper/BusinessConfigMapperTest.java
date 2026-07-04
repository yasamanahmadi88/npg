package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BusinessConfigMapperTest {

    private BusinessConfigMapper businessConfigMapper;

    @BeforeEach
    public void setUp() {
        businessConfigMapper = new BusinessConfigMapperImpl();
    }
}
