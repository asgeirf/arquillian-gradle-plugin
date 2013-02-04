package org.jboss.arquillian.gradle

import org.gradle.api.Project

abstract class ContainerConfig  {
    final String id, name
    public ContainerConfig(String id, String name) {
        this.id = id
        this.name = name
    }

    public abstract void configure(Project project);

}
