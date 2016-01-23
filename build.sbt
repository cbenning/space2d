name := "test-game"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "org.jbox2d" % "jbox2d-library" % "2.2.1.1"

unmanagedBase <<= baseDirectory { base => base / "lib" / "jar" }

javaOptions += "-Djava.library.path=./lib/native/linux"