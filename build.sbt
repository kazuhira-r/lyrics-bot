name := "lyrics-bot"

version := "0.2.0"

scalaVersion := "2.11.8"

organization := "org.littlewings"

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature")

updateOptions := updateOptions.value.withCachedResolution(true)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

artifactName := {
  (scalaVersion: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    //artifact.name + "." + artifact.extension
    "ROOT" + "." + artifact.extension
}

enablePlugins(JettyPlugin)

webappWebInfClasses := true

val twitter4jVersion = "4.0.4"
val typesafeConfigVersion = "1.3.0"
val quartzVersion = "2.2.3"
val servletApiVersion = "3.1.0"
val scalaTestVersion = "3.0.0"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % twitter4jVersion % Compile,
  "com.typesafe" % "config" % typesafeConfigVersion % Compile,
  "org.quartz-scheduler" % "quartz" % quartzVersion % Compile,
  "javax.servlet" % "javax.servlet-api" % servletApiVersion % Provided,
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test
)
