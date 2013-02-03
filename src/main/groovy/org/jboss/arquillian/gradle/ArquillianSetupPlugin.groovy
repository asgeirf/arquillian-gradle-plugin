package org.jboss.arquillian.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.JavaPlugin

class ArquillianSetupPlugin implements Plugin<Project> {
    private static final Logger logger = Logging.getLogger(ArquillianSetupPlugin)

    @Override
    public void apply(Project project) {
        project.plugins.apply(JavaPlugin.class)
        project.convention.plugins.arquillian = new ArquillianSetupPluginConvention(project)
    }


    static class ArquillianSetupPluginConvention {
        def supportedContainers = [
                weldEeEmbedded: 'weld-ee-embedded'
        ]
        private Project project
        String[] containers = [supportedContainers.weldEeEmbedded]
        String[] extensions = []
        boolean useTestNG = false
        BillOfMaterial bom = createArquillianBom()

        ArquillianSetupPluginConvention(Project project) {
            this.project = project;
        }

        def arquillian(Closure closure) {
            closure.delegate = this;
            closure();
            if (useTestNG) {
                addTestNgDependencies()
            } else {
                addJunitDependencies()
            }
            addContainerDependencies()
        }

        def addJunitDependencies() {
            project.dependencies {
                testCompile bom.resolve('org.jboss.arquillian.junit:arquillian-junit-container')
            }
        }

        def addTestNgDependencies() {
            project.dependencies {
                testCompile bom.resolve('org.jboss.arquillian.testng:arquillian-testng-container')
            }
        }

        def addContainerDependencies() {
            if ("jetty" in containers) {
                logger.debug("Adding jetty dependencies")
                project.dependencies {
                    testRuntime "org.jboss.arquillian.container:arquillian-jetty-embedded-7:1.0.0.CR1"
                    testRuntime "org.eclipse.jetty:jetty-webapp:8.1.7.v20120910"
                    testRuntime "org.eclipse.jetty:jetty-plus:8.1.7.v20120910"
                }
            }
            if ("weld-ee-embedded" in containers) {
                logger.debug("Adding weld ee dependencies")
                project.dependencies {
                    testRuntime "org.jboss.arquillian.container:arquillian-weld-ee-embedded-1.1:1.0.0.CR5"
                    testRuntime "org.jboss.weld:weld-core:1.1.1.Final"
                    testRuntime "org.slf4j:slf4j-log4j12:1.5.10"
                    testRuntime "log4j:log4j:1.2.14"
                    testRuntime "org.jboss.spec:jboss-javaee-web-6.0:3.0.2.Final"
                }
            }
        }

        def createArquillianBom() {
            def versions = [
                    arquillian: '1.0.3.Final',
                    shrinkwrap_shrinkwrap: '1.0.1',
                    shrinkwrap_descriptors: '2.0.0-alpha-3',
                    shrinkwrap_resolver: '1.0.0-beta-7'
            ]

            def arqBom = new BillOfMaterial('org.jboss.arquillian', 'arquillian-bom', versions.arquillian,
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
                            new BillOfMaterial('org.jboss.shrinkwrap', 'shrinkwrap-bom', versions.shrinkwrap_shrinkwrap,
                                    [
                                            'org.jboss.shrinkwrap:shrinkwrap-api',
                                            'org.jboss.shrinkwrap:shrinkwrap-spi',
                                            'org.jboss.shrinkwrap:shrinkwrap-impl-base'
                                    ]
                            ),
                            new BillOfMaterial('org.jboss.shrinkwrap.resolver', 'shrinkwrap-resolver-bom', versions.shrinkwrap_resolver,
                                    [
                                            'org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-api',
                                            'org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-api-maven',
                                            'org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-impl-maven'
                                    ]
                            ),
                            new BillOfMaterial('org.jboss.shrinkwrap.descriptors', 'shrinkwrap-descriptors-bom', versions.shrinkwrap_descriptors,
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

            return arqBom
        }
    }

    static class BillOfMaterial {
        String groupId, artifactId, version
        def dependencies = []
        def importedBoms = []

        BillOfMaterial(groupId, artifactId, version, dependencies, importedBoms = []) {
            this.version = version
            this.artifactId = artifactId
            this.groupId = groupId
            this.dependencies = dependencies
            this.importedBoms = importedBoms
        }

        def resolve(String artifact) {
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
    }

}
