# benchmarking

# JAR

mvn clean package


# UTIL

java -Dmongodb.uri="mongodb://localhost:27017" -Dmongodb.database="test" -jar target/benchmarking-1.0-SNAPSHOT-jar-with-dependencies.jar util ping 1000

java -Dmongodb.uri="mongodb://localhost:27017" -Dmongodb.database="test" -jar target/benchmarking-1.0-SNAPSHOT-jar-with-dependencies.jar util rename bulk bulk2

java -Dmongodb.uri="mongodb://localhost:27017" -Dmongodb.database="test" -jar target/benchmarking-1.0-SNAPSHOT-jar-with-dependencies.jar util drop bulk



# INSERT

# n = 1,000 threads = 1

java -Dmongodb.uri="mongodb://localhost:27017" -Dmongodb.database="test" -Dmongodb.collection="bulk" -Dmongodb.documents="1000" -Dmongodb.threads="1" -jar target/benchmarking-1.0-SNAPSHOT-jar-with-dependencies.jar insert single

java -Dmongodb.uri="mongodb://localhost:27017" -Dmongodb.database="test" -Dmongodb.collection="bulk" -Dmongodb.documents="1000" -Dmongodb.threads="1" -jar target/benchmarking-1.0-SNAPSHOT-jar-with-dependencies.jar insert bulk


# REPLACE

# n = 1,000 threads = 1

java -Dmongodb.uri="mongodb://localhost:27017" -Dmongodb.database="test" -Dmongodb.collection="bulk" -Dmongodb.documents="1000" -Dmongodb.threads="1" -jar target/benchmarking-1.0-SNAPSHOT-jar-with-dependencies.jar replace single

java -Dmongodb.uri="mongodb://localhost:27017" -Dmongodb.database="test" -Dmongodb.collection="bulk" -Dmongodb.documents="1000" -Dmongodb.threads="1" -jar target/benchmarking-1.0-SNAPSHOT-jar-with-dependencies.jar replace bulk
