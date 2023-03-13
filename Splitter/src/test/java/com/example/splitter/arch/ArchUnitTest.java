package com.example.splitter.arch;


import com.example.splitter.SplitterApplication;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packagesOf = SplitterApplication.class,importOptions = ImportOption.DoNotIncludeTests.class )
public class ArchUnitTest {
    private final JavaClasses klassen =
            new ClassFileImporter()
                    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                    .importPackagesOf(SplitterApplication.class);
    @ArchTest
    ArchRule rule1 = noClasses().should().beAnnotatedWith(Deprecated.class);

    @ArchTest
    ArchRule rule2 = noMethods().should().beAnnotatedWith(Deprecated.class);

    @ArchTest
    ArchRule rule3 = noClasses().that().areNotAnnotatedWith(Controller.class).
            should().accessClassesThat().areAnnotatedWith(Controller.class);

    @ArchTest
    ArchRule rule4 = noClasses().that().areAnnotatedWith(Controller.class).should()
            .accessClassesThat().areAnnotatedWith(Repository.class);

    @ArchTest
    ArchRule rule5 = classes().that().areAnnotatedWith(Controller.class).should()
            .accessClassesThat().areAnnotatedWith(Service.class);

    @ArchTest
    ArchRule rule6 = noClasses().that().areAnnotatedWith(Controller.class).should()
            .accessClassesThat().areAnnotatedWith(Repository.class).andShould().accessClassesThat()
            .areAnnotatedWith(Service.class);


    @Test
    @DisplayName("Die Splitter Anwendung hat eine Onion Architektur")
    public void rule7() throws Exception {
        ArchRule rule7 = onionArchitecture()
                .domainModels("..domain..")
                .domainServices("..service")
                .applicationServices("..service")
                .adapter("web","..web")
                .adapter("persistence", "..repository");

        rule7.check(klassen);
    }





}
