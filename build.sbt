name := "lyrics-bot"

version := "0.1.7"

scalaVersion := "2.11.2"

organization := "org.littlewings"

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature")

incOptions := incOptions.value.withNameHashing(true)

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

jetty()

artifactName in packageWar := {
  (scalaVersion: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    artifact.name + "." + artifact.extension
}

val jettyVersion = "9.0.0.v20130308"

libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "container",
  "org.eclipse.jetty" % "jetty-plus"   % jettyVersion % "container",
  "org.twitter4j" % "twitter4j-core" % "4.0.2",
  "com.typesafe" % "config" % "1.2.1",
  "org.quartz-scheduler" % "quartz" % "2.2.1",
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.2" % "test"
)
