package org.jboss.arquillian.gradle

import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

class BillOfMaterial {
    private static final Logger logger = Logging.getLogger(ArquillianSetupPlugin)

    final String groupId, artifactId, version
    private List<String> dependencies = []
    private List<BillOfMaterial> importedBoms = []

    public BillOfMaterial(String groupId, String artifactId, String version, List<String> dependencies,
                          List<BillOfMaterial> importedBoms = []) {
        this.version = version
        this.artifactId = artifactId
        this.groupId = groupId
        this.dependencies = dependencies
        this.importedBoms = importedBoms
    }

    public String resolve(String artifact) {
        def boms = [this]
        boms.addAll(importedBoms)
        for (BillOfMaterial bom : boms) {
            if (bom.dependencies.contains(artifact)) {
                logger.debug("Resolving ${artifact} as ${artifact}:${bom.version}")
                return "${artifact}:${bom.version}"
            }
        }
        throw new IllegalArgumentException("artifact not found: ${artifact}")
    }

    private static final Map<String, String> ARQUILLIAN_VERSIONS = [
                    arquillian: '1.0.3.Final',
                    shrinkwrap_shrinkwrap: '1.0.1',
                    shrinkwrap_descriptors: '2.0.0-alpha-3',
                    shrinkwrap_resolver: '1.0.0-beta-7'
    ]

    public static final BillOfMaterial ARQUILLIAN_BOM =
        new BillOfMaterial('org.jboss.arquillian', 'arquillian-bom', ARQUILLIAN_VERSIONS.arquillian,
                    [
                            // core
                            'org.jboss.arquillian.core:arquillian-core-api',
                            'org.jboss.arquillian.core:arquillian-core-spi',
                            'org.jboss.arquillian.core:arquillian-core-impl-base',
                            // config
                            'org.jboss.arquillian.config:arquillian-config-api',
                            'org.jboss.arquillian.config:arquillian-config-spi',
                            'org.jboss.arquillian.config:arquillian-config-impl-base',
                            // test
                            'org.jboss.arquillian.test:arquillian-test-api',
                            'org.jboss.arquillian.test:arquillian-test-spi',
                            'org.jboss.arquillian.test:arquillian-test-impl-base',
                            // container
                            'org.jboss.arquillian.container:arquillian-container-api',
                            'org.jboss.arquillian.container:arquillian-container-spi',
                            'org.jboss.arquillian.container:arquillian-container-impl-base',
                            // container test
                            'org.jboss.arquillian.container:arquillian-container-test-api',
                            'org.jboss.arquillian.container:arquillian-container-test-spi',
                            'org.jboss.arquillian.container:arquillian-container-test-impl-base',
                            // junit
                            'org.jboss.arquillian.junit:arquillian-junit-core',
                            'org.jboss.arquillian.junit:arquillian-junit-container',
                            'org.jboss.arquillian.junit:arquillian-junit-standalone',
                            // testng
                            'org.jboss.arquillian.testng:arquillian-testng-core',
                            'org.jboss.arquillian.testng:arquillian-testng-container',
                            'org.jboss.arquillian.testng:arquillian-testng-standalone',
                            // protocols
                            'org.jboss.arquillian.protocol:arquillian-protocol-servlet',
                            'org.jboss.arquillian.protocol:arquillian-protocol-jmx',
                            // enrichers
                            'org.jboss.arquillian.testenricher:arquillian-testenricher-cdi',
                            'org.jboss.arquillian.testenricher:arquillian-testenricher-ejb',
                            'org.jboss.arquillian.testenricher:arquillian-testenricher-resource',
                            'org.jboss.arquillian.testenricher:arquillian-testenricher-initialcontext'
                    ],
                    [
                            new BillOfMaterial('org.jboss.shrinkwrap', 'shrinkwrap-bom', ARQUILLIAN_VERSIONS.shrinkwrap_shrinkwrap,
                                    [
                                            'org.jboss.shrinkwrap:shrinkwrap-api',
                                            'org.jboss.shrinkwrap:shrinkwrap-spi',
                                            'org.jboss.shrinkwrap:shrinkwrap-impl-base'
                                    ]
                            ),
                            new BillOfMaterial('org.jboss.shrinkwrap.resolver', 'shrinkwrap-resolver-bom', ARQUILLIAN_VERSIONS.shrinkwrap_resolver,
                                    [
                                            'org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-api',
                                            'org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-api-maven',
                                            'org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-impl-maven'
                                    ]
                            ),
                            new BillOfMaterial('org.jboss.shrinkwrap.descriptors', 'shrinkwrap-descriptors-bom', ARQUILLIAN_VERSIONS.shrinkwrap_descriptors,
                                    [
                                            'org.jboss.shrinkwrap.descriptors:shrinkwrap-descriptors-api-base',
                                            'org.jboss.shrinkwrap.descriptors:shrinkwrap-descriptors-api-javaee',
                                            'org.jboss.shrinkwrap.descriptors:shrinkwrap-descriptors-api-jboss',
                                            'org.jboss.shrinkwrap.descriptors:shrinkwrap-descriptors-gen',
                                            'org.jboss.shrinkwrap.descriptors:shrinkwrap-descriptors-impl-base',
                                            'org.jboss.shrinkwrap.descriptors:shrinkwrap-descriptors-impl-javaee',
                                            'org.jboss.shrinkwrap.descriptors:shrinkwrap-descriptors-impl-jboss',
                                            'org.jboss.shrinkwrap.descriptors:shrinkwrap-descriptors-spi'
                                    ]
                            )
                    ]
            )

}
