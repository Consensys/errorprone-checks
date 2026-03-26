errorprone-checks
-----------------

[![Maven Central](https://img.shields.io/maven-central/v/io.consensys.protocols/errorprone-checks)](https://central.sonatype.com/artifact/io.consensys.protocols/errorprone-checks)

A collection of custom [errorprone] checks built against Error Prone 2.36.0.

## Usage
- Add errorprone-checks dependency
```groovy
repositories {
    mavenCentral()
}
```
```groovy
dependencies {
    errorprone("com.google.errorprone:error_prone_core:2.36.0")
    errorprone("io.consensys.protocols:errorprone-checks:<version>")
}
```

[errorprone]: (https://errorprone.info/docs/plugins)
