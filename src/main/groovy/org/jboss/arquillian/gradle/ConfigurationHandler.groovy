package org.jboss.arquillian.gradle

interface ConfigurationHandler<T> {
    void onConfigure(T configuration);
}
