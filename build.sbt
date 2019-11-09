name := "cats-conts"

organization := "danslapman"

version := "0.5"

scalaVersion := "2.13.1"

crossScalaVersions := Seq("2.11.12", "2.12.10", "2.13.1")

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, y)) if y < 13 => Seq("-Ypartial-unification")
    case _ => Seq()
  }
}

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.0.0"
)

licenses += ("WTFPL", url("http://www.wtfpl.net"))

bintrayOrganization := Some("danslapman")

bintrayReleaseOnPublish in ThisBuild := false