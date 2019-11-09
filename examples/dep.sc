import coursierapi.MavenRepository

interp.repositories.update(interp.repositories() ::: List(MavenRepository.of(
  "http://dl.bintray.com/danslapman/maven"
)))