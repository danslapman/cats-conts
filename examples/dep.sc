import coursier.MavenRepository

interp.repositories() ++= Seq(MavenRepository(
  "http://dl.bintray.com/danslapman/maven"
))