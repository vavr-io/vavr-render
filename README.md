[![Build Status](https://travis-ci.org/javaslang/javaslang-render.svg?branch=master)](https://travis-ci.org/javaslang/javaslang-render)
[![Coverage Status](https://codecov.io/github/javaslang/javaslang-render/coverage.svg?branch=master)](https://codecov.io/github/javaslang/javaslang-render?branch=master)

# javaslang-render

A rendering library for [Javaslang](http://javaslang.com/), currently housing ascii prettifyers.

## Using Developer Versions

Developer versions can be found [here](https://oss.sonatype.org/content/repositories/snapshots/com/javaslang/javaslang-render).

### Maven

```xml
<dependency>
  <groupId>com.javaslang</groupId>
  <artifactId>javaslang-render</artifactId>
  <version>2.0.0-SNAPSHOT</version>
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
compile("com.javaslang:javaslang-render:2.0.0-SNAPSHOT")
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
