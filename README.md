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

For examples, look at the [master_gradle](https://github.com/asgeirf/arquillian-showcase/blob/master_gradle/) branch of the Arquillian Showcase, in particular the [cdi](https://github.com/asgeirf/arquillian-showcase/blob/master_gradle/cdi/build.gradle) and [warp](https://github.com/asgeirf/arquillian-showcase/blob/master_gradle/warp/build.gradle) examples.
