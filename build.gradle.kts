plugins {
    kotlin("jvm") version "2.2.10"
    kotlin("plugin.allopen") version "2.2.10"
    id("io.quarkus")
    id("org.graalvm.python") version "25.0.0"
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-rest")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

graalPy {
    packages.set(setOf(
        "markitdown[all]==0.1.3",
        "beautifulsoup4==4.13.5",
        "charset_normalizer==3.4.3",
        "magika==0.6.2",
        "markdownify==1.2.0",
        "azure_ai_documentintelligence==1.0.2",
        "azure_identity==1.25.0",
        "lxml==6.0.1",
        "mammoth==1.10.0",
        "olefile==0.47",
        "openpyxl==3.1.5",
        "pandas==2.2.3",
        "pdfminer_six==20250506",
        "python_pptx==1.0.2",
        "speechrecognition==3.14.3",
        "xlrd==2.0.2",
        "youtube_transcript_api==1.0.3",
        "onnxruntime==1.17.1",
        "numpy==2.2.6",
        "cobble==0.1.4",
        "isodate==0.7.2",
        "azure_core==1.35.1",
        "cryptography==46.0.1",
        "msal==1.33.0",
        "msal_extensions==1.3.1",
        "soupsieve==2.8",
        "et_xmlfile==2.0.0",
        "pillow==11.3.0",
        "xlsxwriter==3.2.9",
        "cffi==2.0.0",
        "coloredlogs==15.0.1",
        "flatbuffers==25.2.10",
        "protobuf==6.32.1",
        "pycparser==2.23",
        "humanfriendly==10.0",

        "huggingface==0.0.1",
        "transformers==4.56.1",
        "PyYAML==6.0.2",
        "regex==2025.9.18",
        "tokenizers==0.22.1",
        "safetensors==0.6.2",
        "hf_xet==1.1.10",

        "transformers[torch]==4.56.1",
        "psutil==7.1.0",
        "MarkupSafe==3.0.2",
        "torch==2.7.0",
    ))
}

group = "org.acme"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
        javaParameters = true
    }
}

var defaultJvmArgs = listOf(
    "--add-opens", "java.base/java.lang=ALL-UNNAMED",
    "--enable-native-access=ALL-UNNAMED",
    "--sun-misc-unsafe-memory-access=allow",
)

tasks.quarkusRun {
    jvmArgs = defaultJvmArgs
}

tasks.test {
    jvmArgs(defaultJvmArgs)
}
