package org.jboss.arquillian.gradle

import org.gradle.api.Project


class JettyContainerConfig extends ContainerConfig {

    public JettyContainerConfig() {
        super('jetty', 'jetty')
    }

    @Override
    void configure(final Project project) {
        project.dependencies {
            testRuntime "org.jboss.arquillian.container:arquillian-jetty-embedded-7:1.0.0.CR1"
            testRuntime "org.eclipse.jetty:jetty-webapp:8.1.7.v20120910"
            testRuntime "org.eclipse.jetty:jetty-plus:8.1.7.v20120910"
        }
    }
}
