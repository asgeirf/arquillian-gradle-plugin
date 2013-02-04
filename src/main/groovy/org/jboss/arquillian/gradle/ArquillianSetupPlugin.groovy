package org.jboss.arquillian.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.JavaPlugin

class ArquillianSetupPlugin implements Plugin<Project>, ConfigurationHandler<ArquillianSetupPluginConvention> {
    private static final Logger logger = Logging.getLogger(ArquillianSetupPlugin)

    private Map<String, ContainerConfig> supportedContainers = new HashMap<String, ContainerConfig>()
    private Map<String, String> supportedExtensions = new HashMap<String,String>()

    private BillOfMaterial bom = createArquillianBom()
    private Project project

    public ArquillianSetupPlugin() {
        addContainer(new WeldEeEmbeddedContainerConfig())
        addContainer(new JettyContainerConfig())
    }

    @Override
    public void apply(Project project) {
        this.project = project
        project.plugins.apply(JavaPlugin.class)
        project.convention.plugins.arquillian = new ArquillianSetupPluginConvention(this)
    }

    @Override
    void onConfigure(final ArquillianSetupPluginConvention configuration) {
        configuration.useTestNG ? configureTestNg() : configureJunit()
        configureContainers(configuration.containers)
        configureExtensions(configuration.extensions)
    }

    private void configureJunit() {
        project.dependencies {
            testCompile bom.resolve('org.jboss.arquillian.junit:arquillian-junit-container')
        }
    }

    private void configureTestNg() {
        project.dependencies {
            testCompile bom.resolve('org.jboss.arquillian.testng:arquillian-testng-container')
        }
    }

    private void configureContainers(List<String> containers) {
        for (c in containers) {
            if (!supportedContainers.containsKey(c)) {
                throw new IllegalArgumentException("${c} is not a supported container. Available containers are (" + supportedContainers.values().join(", ") + ")")
            }
            logger.debug("configuring container ${c}")
            supportedContainers.get(c).configure(project)
        }

    }

    private void configureExtensions(List<String> extensions) {
    }

    private void addContainer(ContainerConfig containerConfig) {
        supportedContainers.put(containerConfig.id, containerConfig)
    }

    private static BillOfMaterial createArquillianBom() {
        Map<String, String> versions = [
                arquillian: '1.0.3.Final',
                shrinkwrap_shrinkwrap: '1.0.1',
                shrinkwrap_descriptors: '2.0.0-alpha-3',
                shrinkwrap_resolver: '1.0.0-beta-7'
        ]

        BillOfMaterial arqBom = new BillOfMaterial('org.jboss.arquillian', 'arquillian-bom', versions.arquillian,
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

