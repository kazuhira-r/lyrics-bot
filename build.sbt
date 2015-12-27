name := "lyrics-bot"

version := "0.1.8"

scalaVersion := "2.11.7"

organization := "org.littlewings"

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature")

updateOptions := updateOptions.value.withCachedResolution(true)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

artifactName := {
  (scalaVersion: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    //artifact.name + "." + artifact.extension
    "ROOT" + "." + artifact.extension
}

val jettyVersion = "9.0.0.v20130308"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "4.0.4",
  "com.typesafe" % "config" % "1.3.0",
  "org.quartz-scheduler" % "quartz" % "2.2.2",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test"
)

enablePlugins(JettyPlugin)
