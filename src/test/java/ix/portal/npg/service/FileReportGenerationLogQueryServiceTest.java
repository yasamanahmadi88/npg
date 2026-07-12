package ix.portal.npg.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ix.portal.npg.domain.FileReportGenerationLogEntity;
import ix.portal.npg.repository.FileReportGenerationLogRepository;
import ix.portal.npg.service.criteria.FileReportGenerationLogCriteria;
import ix.portal.npg.service.dto.FileReportGenerationLogDTO;
import ix.portal.npg.service.mapper.FileReportGenerationLogMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import tech.jhipster.service.filter.StringFilter;

@ExtendWith(MockitoExtension.class)
class FileReportGenerationLogQueryServiceTest {

    @Mock
    private FileReportGenerationLogRepository repository;

    @Mock
    private FileReportGenerationLogMapper mapper;

    private FileReportGenerationLogQueryService queryService;

    @BeforeEach
    void setUp() {
        queryService = new FileReportGenerationLogQueryService(repository, mapper);
    }

    @Test
    void findByCriteria_withoutActiveFilters_usesUnfilteredFindAll() {
        Pageable pageable = PageRequest.of(0, 20);
        FileReportGenerationLogEntity entity = new FileReportGenerationLogEntity();
        entity.setId(1L);
        FileReportGenerationLogDTO dto = new FileReportGenerationLogDTO();
        dto.setId(1L);

        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity), pageable, 1));
        when(mapper.toDto(entity)).thenReturn(dto);

        FileReportGenerationLogCriteria criteria = new FileReportGenerationLogCriteria();
        StringFilter blankEquals = new StringFilter();
        blankEquals.setEquals("");
        criteria.setReportName(blankEquals);
        StringFilter blankContains = new StringFilter();
        blankContains.setContains("   ");
        criteria.setFileName(blankContains);

        Page<FileReportGenerationLogDTO> page = queryService.findByCriteria(criteria, pageable);

        assertThat(page.getContent()).containsExactly(dto);
        verify(repository).findAll(pageable);
        verify(repository, never()).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void findByCriteria_withActiveFilter_usesSpecificationQuery() {
        Pageable pageable = PageRequest.of(0, 20);
        FileReportGenerationLogEntity entity = new FileReportGenerationLogEntity();
        entity.setId(2L);
        FileReportGenerationLogDTO dto = new FileReportGenerationLogDTO();
        dto.setId(2L);

        when(repository.findAll(any(Specification.class), eq(pageable)))
            .thenReturn(new PageImpl<>(List.of(entity), pageable, 1));
        when(mapper.toDto(entity)).thenReturn(dto);

        FileReportGenerationLogCriteria criteria = new FileReportGenerationLogCriteria();
        StringFilter reportName = new StringFilter();
        reportName.setEquals("daily");
        criteria.setReportName(reportName);

        Page<FileReportGenerationLogDTO> page = queryService.findByCriteria(criteria, pageable);

        assertThat(page.getContent()).containsExactly(dto);
        verify(repository).findAll(any(Specification.class), eq(pageable));
        verify(repository, never()).findAll(pageable);
    }

    @Test
    void sanitizeCriteria_clearsBlankStringEquals() {
        FileReportGenerationLogCriteria criteria = new FileReportGenerationLogCriteria();
        StringFilter blank = new StringFilter();
        blank.setEquals("");
        criteria.setReportName(blank);

        queryService.sanitizeCriteria(criteria);

        assertThat(criteria.getReportName()).isNull();
        assertThat(queryService.hasActiveFilter(criteria)).isFalse();
    }
}
