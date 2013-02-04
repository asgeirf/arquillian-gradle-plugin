package org.jboss.arquillian.gradle

import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging

class BillOfMaterial {
    private static final Logger logger = Logging.getLogger(ArquillianSetupPlugin)

    final String groupId, artifactId, version
    private List<String> dependencies = []
    private List<BillOfMaterial> importedBoms = []

    public BillOfMaterial(String groupId, String artifactId, String version, List<String> dependencies,
                          List<BillOfMaterial> importedBoms = []) {
        this.version = version
        this.artifactId = artifactId
        this.groupId = groupId
        this.dependencies = dependencies
        this.importedBoms = importedBoms
    }

    public String resolve(String artifact) {
        def boms = [this]
        boms.addAll(importedBoms)
        for (BillOfMaterial bom : boms) {
            if (bom.dependencies.contains(artifact)) {
                logger.debug("Resolving ${artifact} as ${artifact}:${bom.version}")
                return "${artifact}:${bom.version}"
            }
        }
        throw new IllegalArgumentException("artifact not found: ${artifact}")
    }
}
