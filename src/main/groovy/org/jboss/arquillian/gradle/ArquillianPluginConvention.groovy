package org.jboss.arquillian.gradle

class ArquillianPluginConvention {

    private ConfigurationHandler<ArquillianPluginConvention> handler
    List<String> containers = []
    List<String> extensions = []
    boolean useTestNG = false

    ArquillianPluginConvention(ConfigurationHandler<ArquillianPluginConvention> handler) {
        this.handler = handler;
    }

    public void arquillian(Closure closure) {
        closure.delegate = this;
        closure();
        handler.onConfigure(this)
    }
}

