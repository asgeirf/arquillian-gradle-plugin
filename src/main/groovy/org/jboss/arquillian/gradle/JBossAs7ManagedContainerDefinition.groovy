package org.jboss.arquillian.gradle

import org.gradle.api.Project;

public class JBossAs7ManagedContainerDefinition extends ContainerDefinition {

    public JBossAs7ManagedContainerDefinition() {
        super(ContainerType.MANAGED, 'jboss-as7-managed', 'jBossAs7Managed', 'JBoss AS 7.1 Managed');
    }

    @Override
    void configure(final Project project) {
        addRuntimeConfiguration(project)
        project.dependencies {
            "${name}Runtime" 'org.jboss.as:jboss-as-arquillian-container-managed:7.1.1.Final'
            "${name}Runtime" 'org.jboss.spec:jboss-javaee-web-6.0:3.0.2.Final'
        }
        addTestTask(project)
    }
}
