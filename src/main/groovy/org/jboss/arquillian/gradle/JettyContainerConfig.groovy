package org.jboss.arquillian.gradle

import org.gradle.api.Project


class JettyContainerConfig extends ContainerConfig {
    private static final String JETTY_VERSION = '8.1.7.v20120910'
    public JettyContainerConfig() {
        super('jetty', 'jetty', "Jetty Embedded ${JETTY_VERSION}")
    }

    @Override
    void configure(final Project project) {
        addRuntimeConfiguration(project)
        project.dependencies {
            "${name}Runtime" 'org.jboss.arquillian.container:arquillian-jetty-embedded-7:1.0.0.CR1'
            "${name}Runtime" "org.eclipse.jetty:jetty-webapp:${JETTY_VERSION}"
            "${name}Runtime" "org.eclipse.jetty:jetty-plus:${JETTY_VERSION}"
        }
        addTestTask(project)
    }
}
