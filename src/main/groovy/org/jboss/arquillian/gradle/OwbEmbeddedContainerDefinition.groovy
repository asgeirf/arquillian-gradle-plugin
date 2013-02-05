package org.jboss.arquillian.gradle
import org.gradle.api.Project

class OwbEmbeddedContainerDefinition extends ContainerDefinition {

    public OwbEmbeddedContainerDefinition() {
        super(ContainerType.EMBEDDED, 'owb-embedded-1', 'owbEmbedded1', 'Apache OpenWebBeans 1.0')
    }

    @Override
    public void configure(final Project project) {
        addRuntimeConfiguration(project)
        project.dependencies {
            "${name}Runtime" 'org.jboss.arquillian.container:arquillian-openwebbeans-embedded-1:1.0.0.CR2'
            "${name}Runtime" 'org.apache.openwebbeans:openwebbeans-spi:1.1.7'
            "${name}Runtime" 'org.apache.openwebbeans:openwebbeans-impl:1.1.7'
        }
        addTestTask(project)
    }
}
