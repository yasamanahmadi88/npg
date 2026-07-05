package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckStatusMapperTest {

    private CheckStatusMapper checkStatusMapper;

    @BeforeEach
    public void setUp() {
        checkStatusMapper = new CheckStatusMapperImpl();
    }
}
