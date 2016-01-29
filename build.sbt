name := "test-game"

version := "1.0"

scalaVersion := "2.11.7"
//scalaVersion := "2.10.6"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "org.jbox2d" % "jbox2d-library" % "2.2.1.1"

libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4.1"
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.10" % "2.3.14"

libraryDependencies += "com.typesafe.akka" % "akka-remote_2.11" % "2.4.1"
//libraryDependencies += "com.typesafe.akka" % "akka-remote_2.10" % "2.3.14"

unmanagedBase <<= baseDirectory { base => base / "lib" / "jar" }

javaOptions += "-Djava.library.path=./lib/native/linux"

