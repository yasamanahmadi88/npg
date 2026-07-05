package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UndifinedStatusMapperTest {

    private UndifinedStatusMapper undifinedStatusMapper;

    @BeforeEach
    public void setUp() {
        undifinedStatusMapper = new UndifinedStatusMapperImpl();
    }
}
