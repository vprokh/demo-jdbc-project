plugins {
    id("java")
    id("org.flywaydb.flyway") version "10.0.0"
}

buildscript {
    dependencies {
        classpath("org.flywaydb:flyway-database-postgresql:10.4.1")
    }
}

flyway {
    url = "jdbc:postgresql://localhost:5432/FlyWayDemo"
    user = "user-name"
    password = "strong-password"
    schemas = arrayOf("public")
    flyway.cleanDisabled = false
}

tasks.register<org.flywaydb.gradle.task.FlywayMigrateTask>("migrateTestDatabase") {
    url = "jdbc:h2:${projectDir}/test;schema=PUBLIC"
}

tasks.register<org.flywaydb.gradle.task.FlywayInfoTask>("testDatabaseInfo") {
    url = "jdbc:h2:${projectDir}/test;schema=PUBLIC"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
//    if want to use red-gate version of FlyWay, we must define artifact repository download from
//    maven {
//        url = uri("https://download.red-gate.com/maven/release")
//    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation("com.h2database:h2:2.1.210")
    implementation("org.flywaydb:flyway-core:8.0.1")
    implementation("org.postgresql:postgresql:42.2.24")
    implementation("com.zaxxer:HikariCP:5.1.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-junit-jupiter:5.8.0")

}

tasks.test {
    useJUnitPlatform()

    dependsOn("migrateTestDatabase")
}
