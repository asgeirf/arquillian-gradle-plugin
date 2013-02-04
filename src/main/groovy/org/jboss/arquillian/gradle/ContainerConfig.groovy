package org.jboss.arquillian.gradle

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test

abstract class ContainerConfig  {
    final String id, name, fullName
    public ContainerConfig(String id, String name, String fullName = name) {
        this.id = id
        this.name = name
        this.fullName = fullName
    }

    public abstract void configure(Project project);

    protected void addRuntimeConfiguration(Project project) {
        project.configurations {
            "${name}Runtime" { extendsFrom testRuntime}
        }

    }

    protected void addTestTask(Project project) {
        Test test = project.tasks.add("${name}Test", Test)
        test.group = 'Verification'
        test.description = "Runs the Arquillian tests against the ${fullName} container"
        test.classpath += project.configurations[name + 'Runtime']
    }
}
