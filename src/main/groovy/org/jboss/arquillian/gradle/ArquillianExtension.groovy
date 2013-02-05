package org.jboss.arquillian.gradle

import org.gradle.api.Project

abstract class ArquillianExtension {
    final String id, fullName

    ArquillianExtension(final String id, final String fullName) {
        this.id = id
        this.fullName = fullName
    }

    public abstract void configure(Project project);

}
