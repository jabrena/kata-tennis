package com.hatim.nexeo.katatennis;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "com.hatim.nexeo.katatennis", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchitectureOnionTest {


    @ArchTest
    static final ArchRule respecter_les_couches_de_clean_architecture =
            onionArchitecture()
                    .domainModels("..domain.model..")
                    .domainServices("..domain.service..")
                    .applicationServices("..application..")
                    .adapter("Gateway", "..infrastructure.gateway..")
                    .adapter("Resource", "..infrastructure.resource..");

}
