plugins {
    id("java")
    application
}

group = "de.skyslycer"
version = "1.0.0"

application {
    mainClass.set("de.skyslycer.connect4.Main")
}

repositories {
}

dependencies {
}

tasks.withType<JavaExec> {
    standardInput = System.`in`
}