name := "cats-conts"

organization := "danslapman"

version := "0.4"

scalaVersion := "2.12.10"

crossScalaVersions := Seq("2.11.12", "2.12.10")

scalacOptions += "-Ypartial-unification"

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.0.0"
)

licenses += ("WTFPL", url("http://www.wtfpl.net"))

bintrayOrganization := Some("danslapman")

bintrayReleaseOnPublish in ThisBuild := false