import com.github.robfletcher.compass.CompassExtension

buildscript {

  repositories {
    jcenter()
    maven { setUrl("http://dl.bintray.com/robfletcher/gradle-plugins") }
    mavenCentral()
  }
  dependencies {
    classpath("com.github.robfletcher:compass-gradle-plugin:2.0.6")
  }
}

apply {
  plugin("java")
  plugin("application")
  plugin("com.github.robfletcher.compass")
}

group = "io.projectreactor"
version = "1.0.0.BUILD-SNAPSHOT"

configure<JavaPluginConvention> {
    setSourceCompatibility(1.8)
    setTargetCompatibility(1.8)
}

configure<ApplicationPluginConvention> {
    mainClassName = "io.projectreactor.Application"
}

configure<CompassExtension> {
  sassDir = file("$projectDir/src/main/sass")
  cssDir = file("$projectDir/src/main/resources/static/assets/css")
}

repositories {
  mavenCentral()
  maven { setUrl("http://repo.spring.io/libs-milestone") }
  maven { setUrl("https://repo.spring.io/snapshot") }
}

dependencies {
  compile("org.springframework:spring-web-reactive:5.0.0.BUILD-SNAPSHOT")
  // TODO Remove the spring-context-support dependency when https://jira.spring.io/browse/SPR-14908 will be fixed
  compile("org.springframework:spring-context-support:5.0.0.BUILD-SNAPSHOT")
  compile("io.projectreactor.ipc:reactor-netty:0.5.2.RELEASE")
}
