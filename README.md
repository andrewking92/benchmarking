# benchmarking

mvn compile exec:java -Dexec.mainClass="com.benchmarking.util.Ping" -Dmongodb.uri="mongodb+srv://<xxx>" -Dmongodb.database="test" -Dmongodb.count="1000"

mvn compile exec:java -Dexec.mainClass="com.benchmarking.insert.BulkInsertThreaded" -Dmongodb.uri="mongodb+srv://<xxx>" -Dmongodb.database="test" -Dmongodb.collection="bulk" -Dmongodb.documents="1000000" -Dmongodb.threads="500"

mvn compile exec:java -Dexec.mainClass="com.benchmarking.replace.BulkReplaceOneThreaded" -Dmongodb.uri="mongodb+srv://<xxx>" -Dmongodb.database="test" -Dmongodb.collection="bulk" -Dmongodb.documents="100000" -Dmongodb.threads="100"