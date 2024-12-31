plugins {
    id("java")
    application
}

application {
    mainClass = "org.example.Main"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.undertow:undertow-core:2.3.18.Final")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.53")
    implementation("org.postgresql:postgresql:42.7.4")
    implementation("com.zaxxer:HikariCP:6.2.1")
    implementation("io.github.cdimascio:dotenv-java:3.0.1")
    testImplementation(platform("org.junit:junit-bom:5.11.0-M1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0-M1")
}

tasks.test {
    useJUnitPlatform()
}