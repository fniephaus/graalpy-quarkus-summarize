package org.graalvm.python

import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import org.graalvm.polyglot.Context
import org.graalvm.python.embedding.GraalPyResources
import org.graalvm.python.embedding.VirtualFileSystem
import java.nio.file.Files
import java.nio.file.Paths

@ApplicationScoped
class GraalPyContextProvider {

    @Produces
    fun pythonContext(): Context {
        val vfs = VirtualFileSystem.newBuilder()
            .resourceLoadingClass(GraalPyContextProvider::class.java)
            .build()
        val tmpdir = Files.createTempDirectory("graalpy-vfs")
        Runtime.getRuntime().addShutdownHook(Thread {
            Files.walk(tmpdir)
                .sorted(Comparator.reverseOrder())
                .forEach { Files.deleteIfExists(it) }
        })
        GraalPyResources.extractVirtualFileSystemResources(vfs, tmpdir)
        return GraalPyResources.contextBuilder(tmpdir)
            .option("python.WarnExperimentalFeatures", "false")
            .allowAllAccess(true)
            .build()
    }
}
