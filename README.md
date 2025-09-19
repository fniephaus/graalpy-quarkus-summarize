# GraalPy Quarkus Demo

This demo embeds [`markitdown`](https://github.com/microsoft/markitdown) and [`transformers`](https://github.com/huggingface/transformers) via [GraalPy](https://www.graalvm.org/python/) in a [Quarkus](https://quarkus.io) app.


## Running the application

You can run the application using:

```shell script
./gradlew quarkusRun
```
> **_NOTE:_** Building the application may take some time as numerous Python packages with native extensions may need to be built from source.

## Endpoints

**/hello**: says hi from GraalPy
```shell script
$ curl http://localhost:8080/hello
Hi Quarkus from GraalPy
```

**/convert**: converts a file to text using `markitdown`
```shell script
$ curl http://localhost:8080/convert -F "file=@/path/to/test.pdf"
Lorem ipsum...
```

**/summarize**: summarizes text converted from a file using `markitdown` and `transformers`
```shell script
curl http://localhost:8080/convert -F "file=@/path/to/test.pdf"
Summary of lorem ipsum...
```
> **_NOTE:_** The selected model (*HuggingFaceTB/SmolLM2-360M*) is not able to handle a lot of text. If you want to summarize larger amounts of text, consider using a different model.

Example files are available in the [`markitdown` repository](https://github.com/microsoft/markitdown/tree/8a9d8f15936b2068bcb39ccc8d3b317f93784d86/packages/markitdown/tests/test_files).
