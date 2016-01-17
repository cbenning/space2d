name := "test-game"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

unmanagedBase <<= baseDirectory { base => base / "lib" / "jar" }

javaOptions += "-Djava.library.path=./lib/native/linux"