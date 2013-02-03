package org.jboss.arquillian.gradle

import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class ArquillianSetupPluginTest {

    private static final Logger logger = Logging.getLogger(ArquillianSetupPluginTest)

    @Test
    public void should_resolve_weld_ee_embedded_container_dependencies() {

        Project project = ProjectBuilder.builder().build();

        project.with {
            apply plugin: 'arquillian-setup'
            repositories {
                mavenLocal()
                mavenCentral()
            }

            arquillian {
                containers = ['weld-ee-embedded']
            }
        }
    }

}
