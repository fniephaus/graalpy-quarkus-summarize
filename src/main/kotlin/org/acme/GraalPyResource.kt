package org.graalvm.python

import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.jboss.resteasy.reactive.RestForm
import org.jboss.resteasy.reactive.multipart.FileUpload

@Path("/")
class GraalPyResource @Inject constructor(
    private val service: GraalPyService
) {
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): String = service.hello("Quarkus")

    @POST
    @Path("/convert")
    @Produces(MediaType.TEXT_PLAIN)
    fun convert(
        @RestForm("file") fileUpload: FileUpload
    ): String =
        service.convert(
            fileUpload.fileName(),
            fileUpload.uploadedFile().toString(),
        )

    @POST
    @Path("/summarize")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    fun summarize(
        @RestForm("file") fileUpload: FileUpload
    ): String =
        service.summarize(convert(fileUpload))
}
