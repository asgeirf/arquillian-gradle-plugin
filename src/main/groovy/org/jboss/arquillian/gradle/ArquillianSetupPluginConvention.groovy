package org.jboss.arquillian.gradle

class ArquillianSetupPluginConvention {

    private ConfigurationHandler<ArquillianSetupPluginConvention> handler
    List<String> containers = []
    List<String> extensions = []
    boolean useTestNG = false

    ArquillianSetupPluginConvention(ConfigurationHandler<ArquillianSetupPluginConvention> handler) {
        this.handler = handler;
    }

    public void arquillian(Closure closure) {
        closure.delegate = this;
        closure();
        handler.onConfigure(this)
    }
}

