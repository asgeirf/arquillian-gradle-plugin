# Gradle Arquillian Setup Plugin

The plugin simplifies configuration of [Arquillian](http://www.arquillian.org) in Gradle.

## Usage

To use the Arquillian Setup plugin, include in your build script:

    apply plugin: 'arquillian-setup'
    
    arquillian {
        containers = ['weld-ee-embedded']
    }

