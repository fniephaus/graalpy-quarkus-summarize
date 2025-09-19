package org.graalvm.python

import io.quarkus.runtime.Startup
import io.smallrye.common.annotation.Blocking
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Value

@Startup
@Blocking
@ApplicationScoped
class GraalPyService @Inject constructor(
    provider: GraalPyContextProvider
) {
    private val pythonCtx: Context =
        provider.pythonContext().apply {
            eval(
                "python", """
                import os
                from markitdown import MarkItDown, StreamInfo
                from transformers import AutoModelForCausalLM, AutoTokenizer
                
                
                checkpoint = "HuggingFaceTB/SmolLM2-360M"
                tokenizer = AutoTokenizer.from_pretrained(checkpoint)
                model = AutoModelForCausalLM.from_pretrained(checkpoint)
                
                
                def hello(name):
                    return f"Hi {name} from GraalPy"
                
                
                def convert(filename, filepath):
                    md = MarkItDown(enable_plugins=False)
                    info = StreamInfo(filename=filename, extension=os.path.splitext(filename)[1])
                    return md.convert(filepath, stream_info=info).text_content.replace("-\n", "").replace("\n", " ")
                
                
                def summarize(text):
                    input_text = text + "\n\nIn summary, tl;dr in one sentence the above says that"
                    inputs = tokenizer.encode(input_text, return_tensors="pt")
                    output = model.generate(inputs)
                    generated_text = tokenizer.decode(output[0], skip_special_tokens=True)
                    return generated_text[len(input_text):]
            """.trimIndent()
            )
    }

    private val greetFn: Value = pythonCtx.getBindings("python").getMember("hello")
    private val convertFn: Value= pythonCtx.getBindings("python").getMember("convert")
    private val summarizeFn: Value= pythonCtx.getBindings("python").getMember("summarize")

    fun hello(name: String): String = greetFn.execute(name).asString()
    fun convert(fileName: String, filePath: String): String = convertFn.execute(fileName, filePath).asString()
    fun summarize(text: String): String = summarizeFn.execute(text).asString()
}
