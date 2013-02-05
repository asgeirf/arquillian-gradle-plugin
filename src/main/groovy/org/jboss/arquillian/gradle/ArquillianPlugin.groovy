package org.jboss.arquillian.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.plugins.JavaPlugin

import static org.jboss.arquillian.gradle.BillOfMaterial.ARQUILLIAN_BOM

class ArquillianPlugin implements Plugin<Project>, ConfigurationHandler<ArquillianPluginConvention> {
    private static final Logger logger = Logging.getLogger(ArquillianPlugin)

    private Map<String, ContainerDefinition> supportedContainers = new HashMap<String, ContainerDefinition>()
    private Map<String, String> supportedExtensions = new HashMap<String,String>()

    private Project project

    public ArquillianPlugin() {
        addContainer(new JBossAs7ManagedContainerDefinition())
        addContainer(new WeldSeEmbeddedContainerDefinition())
        addContainer(new WeldEeEmbeddedContainerDefinition())
        addContainer(new JettyContainerConfig())
    }

    @Override
    public void apply(Project project) {
        this.project = project
        project.plugins.apply(JavaPlugin.class)
        project.convention.plugins.arquillian = new ArquillianPluginConvention(this)
    }

    @Override
    void onConfigure(final ArquillianPluginConvention configuration) {
        configuration.useTestNG ? configureTestNg() : configureJunit()
        configureContainers(configuration.containers)
        configureExtensions(configuration.extensions)
    }

    private void configureJunit() {
        project.dependencies {
            testCompile ARQUILLIAN_BOM.resolve('org.jboss.arquillian.junit:arquillian-junit-container')
        }
    }

    private void configureTestNg() {
        project.dependencies {
            testCompile ARQUILLIAN_BOM.resolve('org.jboss.arquillian.testng:arquillian-testng-container')
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

    private void addContainer(ContainerDefinition containerConfig) {
        supportedContainers.put(containerConfig.id, containerConfig)
    }

}

