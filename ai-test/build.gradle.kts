plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("dev.langchain4j:langchain4j:0.30.0")
    // https://mvnrepository.com/artifact/dev.langchain4j/langchain4j-ollama
    implementation("dev.langchain4j:langchain4j-ollama:0.30.0")

}

tasks.test {
    useJUnitPlatform()
}