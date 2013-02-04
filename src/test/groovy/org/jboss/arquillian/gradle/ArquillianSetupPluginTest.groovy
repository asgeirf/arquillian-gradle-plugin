package org.jboss.arquillian.gradle
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class ArquillianSetupPluginTest {

    private static final Logger logger = Logging.getLogger(ArquillianSetupPluginTest)
    private Project project
    @Before
    public void setup() {
        project = ProjectBuilder.builder().build();
    }

    @Test
    public void should_resolve_weld_ee_embedded_container_dependencies() {
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

    @Test(expected = IllegalArgumentException)
    public void should_fail_on_unknown_container() {
        project.with {
            apply plugin: 'arquillian-setup'
            repositories {
                mavenLocal()
                mavenCentral()
            }

            arquillian {
                containers = ['gasfish']
            }
        }
    }

}
