package org.jboss.arquillian.gradle

import org.gradle.api.Project
class WarpJsfExtension extends ArquillianExtension {

    WarpJsfExtension() {
        super('warp-jsf', 'Warp JSF')
    }

    @Override
    void configure(final Project project) {
        project.dependencies {
            testCompile 'org.jboss.arquillian.extension:arquillian-warp-api:1.0.0.Alpha2'
            testRuntime 'org.jboss.arquillian.extension:arquillian-warp-impl:1.0.0.Alpha2'
            testCompile 'org.jboss.arquillian.extension:arquillian-warp-jsf:1.0.0.Alpha2'
            testCompile 'org.jboss.arquillian.extension:arquillian-drone-webdriver-depchain:1.0.0.Final'
        }
    }
}
