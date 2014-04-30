To build :

> mvn compile assembly:single


To launch, enter both following commands :


java -cp "./lib/jersey-core-1.16.jar:lib/jersey-grizzly2-1.16.jar:target/supersql-1.0-SNAPSHOT-jar-with-dependencie\
s.jar" supersql.rest.HttpStaticMain 2> static.err > static.out &

java -cp "./lib/jersey-core-1.16.jar:lib/jersey-grizzly2-1.16.jar:target/supersql-1.0-SNAPSHOT-jar-with-dependencie\
s.jar" supersql.rest.Main 2> rest.err > rest.out &








