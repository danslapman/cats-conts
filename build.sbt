name := "cats-conts"

organization := "danslapman"

version := "0.4"

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.6")

scalacOptions += "-Ypartial-unification"

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.1.0"
)

licenses += ("WTFPL", url("http://www.wtfpl.net"))

bintrayOrganization := Some("danslapman")

bintrayReleaseOnPublish in ThisBuild := false