# Gradle Arquillian Plugin

The plugin simplifies configuration of [Arquillian](http://www.arquillian.org) in Gradle. It adds a simple DSL for specifying which containers and extensions of Arquillian you wish to make use of in your tests. 

## Usage

To use the Gradle Arquillian plugin, include in your build script:

    apply plugin: 'arquillian'
    
    arquillian {
        containers = ['jboss-as7-managed']
        extensions = ['warp-jsf']
    }

You can now run Arquillian tests through:
    
    gradle jBossAs7ManagedTest
