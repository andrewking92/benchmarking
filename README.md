# benchmarking

mvn archetype:generate \
    -DgroupId=com.benchmarking \
    -DartifactId=benchmarking \
    -DarchetypeArtifactId=maven-archetype-quickstart \
    -DinteractiveMode=false


mvn compile exec:java -Dexec.mainClass="com.benchmarking.insert.SingleInsert" -Dmongodb.uri="mongodb://localhost:27017"