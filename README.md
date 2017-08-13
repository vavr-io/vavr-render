[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.vavr/vavr-render/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.vavr/vavr-render)
[![Build Status](https://travis-ci.org/vavr-io/vavr-render.svg?branch=master)](https://travis-ci.org/vavr-io/vavr-render)
[![codecov](https://codecov.io/gh/vavr-io/vavr-render/branch/master/graph/badge.svg)](https://codecov.io/gh/vavr-io/vavr-render)

# vavr-render

A rendering library for [Vavr](http://vavr.io/), currently housing ascii prettifyers.

## Usage

### Maven

```xml
<dependency>
  <groupId>io.vavr</groupId>
  <artifactId>vavr-render</artifactId>
  <version>0.9.0</version>
</dependency>
```

### Gradle

```groovy
compile("io.vavr:vavr-render:0.9.0")
```
## Using Developer Versions

Developer versions can be found [here](https://oss.sonatype.org/content/repositories/snapshots/io/vavr/vavr-render).

### Maven

```xml
<dependency>
  <groupId>io.vavr</groupId>
  <artifactId>vavr-render</artifactId>
  <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Ensure that your `~/.m2/settings.xml` contains the following:

```xml
<profiles>
    <profile>
        <id>allow-snapshots</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
        <repositories>
            <repository>
                <id>snapshots-repo</id>
                <url>https://oss.sonatype.org/content/repositories/snapshots</url>
                <releases>
                    <enabled>false</enabled>
                </releases>
                <snapshots>
                    <enabled>true</enabled>
                </snapshots>
            </repository>
        </repositories>
    </profile>
</profiles>
```

### Gradle

```groovy
compile("io.vavr:vavr-render:1.0.0-SNAPSHOT")
```

Ensure that your `build.gradle` contains the following:

```groovy
repositories {
    mavenCentral()
    maven {
        url "https://oss.sonatype.org/content/repositories/snapshots"
    }
}
```
