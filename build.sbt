name := "lyrics-bot"

version := "0.1"

scalaVersion := "2.11.0"

organization := "org.littlewings"

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature")

incOptions := incOptions.value.withNameHashing(true)

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

seq(webSettings :_*)

artifactName in packageWar := {
  (scalaVersion: ScalaVersion, module: ModuleID, artifact: Artifact) =>
    artifact.name + "." + artifact.extension
}

//val jettyVersion = "9.0.0.v20130308"
val jettyVersion = "8.0.4.v20111024"

libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % jettyVersion % "container",
  "org.eclipse.jetty" % "jetty-plus"   % jettyVersion % "container",
  "org.twitter4j" % "twitter4j-core" % "4.0.1",
  "com.typesafe" % "config" % "1.2.1",
  "org.quartz-scheduler" % "quartz" % "2.2.1",
  "javax.servlet" % "javax.servlet-api" % "3.0.1" % "provided",
  "org.scalatest" %% "scalatest" % "2.1.5" % "test"
)
