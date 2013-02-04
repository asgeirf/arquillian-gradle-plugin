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
    public void container_should_add_testing_task() {
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
        project.tasks.getByName('weldEeEmbeddedTest')
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
