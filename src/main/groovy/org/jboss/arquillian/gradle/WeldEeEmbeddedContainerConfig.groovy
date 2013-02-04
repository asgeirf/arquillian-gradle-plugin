package org.jboss.arquillian.gradle
import org.gradle.api.Project

class WeldEeEmbeddedContainerConfig extends ContainerConfig {

    public WeldEeEmbeddedContainerConfig() {
        super('weld-ee-embedded', 'weldEeEmbedded', 'Weld EE 1.1 Embedded')
    }

    @Override
    public void configure(final Project project) {
        addRuntimeConfiguration(project)
        project.dependencies {
            "${name}Runtime" 'org.jboss.arquillian.container:arquillian-weld-ee-embedded-1.1:1.0.0.CR5'
            "${name}Runtime" 'org.jboss.weld:weld-core:1.1.1.Final'
            "${name}Runtime" 'org.slf4j:slf4j-log4j12:1.5.10'
            "${name}Runtime" 'log4j:log4j:1.2.14'
            "${name}Runtime" 'org.jboss.spec:jboss-javaee-web-6.0:3.0.2.Final'
        }
        addTestTask(project)
    }
}
