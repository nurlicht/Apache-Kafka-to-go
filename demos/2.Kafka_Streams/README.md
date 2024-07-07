# Kafka Streams

## Goal
Simplified execution of the Quickstart for Apache Kafka Streams ([Code](https://github.com/apache/kafka/tree/trunk/streams/quickstart), [How to run](https://kafka.apache.org/24/documentation/streams/quickstart), [Explanations](https://kafka.apache.org/24/documentation/streams/tutorial))

## Improvements
  - No need for JDK, JRE, Gradle, or Maven on the local machine
  - Unification and automation of steps 1-3 in [How to run](https://kafka.apache.org/24/documentation/streams/quickstart)
  - Adaptation of Kafka script-paths to a specific Kafka-image ([apache/kafka:3.7.0](https://hub.docker.com/layers/apache/kafka/3.7.0/images/sha256-3e324d2bd331570676436b24f625e5dcf1facdfbd62efcffabc6b69b1abc13cc?context=explore)) 

## Requirements
Support for docker-compose locally ([DockerDesktop](https://www.docker.com/products/docker-desktop/))

## Modes of Local Execution
  - Java-client on the Kafka-Server
  - Java-client in another JVM

## Execution (Locally, Java-Client on the Kafka-Server)
1. Clone this repository.
    - The local path will be referred to as `<path>`.
2. Start DockerDesktop.
3. Open 4 terminals.
    - Kafka-server
    - KafkaStreams client
    - Producer (Source for KafkaStreams)
    - Consumer (Sink for KafkaStreams) 
4.  Terminal 1: `cd <path> && docker-compose up`
    - In the logs, look for
      - _Transition from STARTING to STARTED_
      - _Created log for partition streams-plaintext-input-0_
      - _Created log for partition streams-wordcount-output-0_  
5.  Terminal 2: `docker exec -it kafka /opt/kafka/bin/kafka-run-class.sh org.apache.kafka.streams.examples.wordcount.WordCountDemo`
    - In the logs, a few (harmless) warnings appear.
6.  Terminal 3: `docker exec -it kafka /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-plaintext-input`
    - A new prompt appears to accept inputs.
7.  Terminal 4: `docker exec -it kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-wordcount-output --from-beginning --formatter kafka.tools.DefaultMessageFormatter --property print.key=true --property print.value=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer`
8.  Adjust Terminals 3 (Producer) and 4 (Consumer) to be visible completely and simultaneously on the monitor.
9.  Loop
    - Terminal 3: Enter series of (occasionally repeated) words and press Enter.
    - Terminal 4: Monitor the appearance of new and repeated words and the associated word-count (histogram).
10. Check out the documentation ([Code](https://github.com/apache/kafka/tree/trunk/streams/quickstart) and [Explanations](https://kafka.apache.org/24/documentation/streams/tutorial)).

## Execution (Locally, Java-Client in another JVM)
Same as the previous case, except for Step 5:
  - Make sure JDK (8+) and Maven are installed on the local machine.
  - Build the project with Maven: `mvn clean package`
  - Start the project with Maven: `java -jar ./target/kafka.streams.demo-1.0-SNAPSHOT-jar-with-dependencies.jar`

Is the KafkaStreams client as fast as the previous case?

## Cleanup
  - Stop the consumer
  - Stop the producer
  - Stop KafkaStreams
  - Stop Kafka
  - Stop the Kafka container (DockerDesktop >> Containers)
  - Delete the Kafka container (DockerDesktop >> Containers)
  - Delete the volumes associated with the Kafka container (DockerDesktop >> Volumes)
  - Run `docker system prune --volumes --force`
  - _Quit_ (not just close) DockerDesktop
  - Run (Windows with WSL): `wsl --shutdown`
  - Inspect and delete this local (Windows) folder, if any: `<home>/AppData/Local/Temp/kafka-streams`
