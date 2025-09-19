package org.graalvm.python

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import org.graalvm.polyglot.Context
import org.graalvm.python.embedding.GraalPyResources
import java.nio.file.Paths

@ApplicationScoped
class GraalPyContextProvider {

    @Produces
    fun pythonContext(): Context =
        GraalPyResources.contextBuilder(Paths.get("build/resources/main/org.graalvm.python.vfs"))
            .option("python.WarnExperimentalFeatures", "false")
            .allowAllAccess(true)
            .build()
}
