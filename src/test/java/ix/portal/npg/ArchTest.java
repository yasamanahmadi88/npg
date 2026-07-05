package ix.portal.npg;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ix.portal.npg");

        noClasses()
            .that()
            .resideInAnyPackage("ix.portal.npg.service..")
            .or()
            .resideInAnyPackage("ix.portal.npg.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..ix.portal.npg.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
