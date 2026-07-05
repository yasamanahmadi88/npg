package ix.portal.npg.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrmToCrdbResponseMapMapperTest {

    private CrmToCrdbResponseMapMapper crmToCrdbResponseMapMapper;

    @BeforeEach
    public void setUp() {
        crmToCrdbResponseMapMapper = new CrmToCrdbResponseMapMapperImpl();
    }
}
