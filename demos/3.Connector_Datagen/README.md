# Kafka Connector (Confluent datagen example)

## Goal
Further automation and simplified execution of the [Confluent-demo for a datagen-connector](https://developer.confluent.io/tutorials/kafka-connect-datagen/kafka.html)

## Improvements
  - Automated connector creation and its confirmation (docker-compose health-check)

## Requirements
Support for docker-compose locally ([DockerDesktop](https://www.docker.com/products/docker-desktop/))

## Execution
1. Start DockerDesktop and in a terminal run `docker-compose up --build`
    - In the logs, wait for the message "Finished creating connector datagen_local_01"
    - Equivalent to Steps 1 to 4 [here](https://developer.confluent.io/tutorials/kafka-connect-datagen/kafka.html).
        - In a terminal, inspect the connector status with `curl -s http://localhost:8083/connectors/datagen_local_01/status`
2. In another terminal, consume the events from the connector topic
    - Same as Step 5 [here](https://developer.confluent.io/tutorials/kafka-connect-datagen/kafka.html).
        - In another terminal, run `docker exec -it connect kafka-avro-console-consumer --bootstrap-server broker:9092 --property schema.registry.url=http://schema-registry:8081 --topic pageviews --property print.key=true --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer --property key.separator=" : " --max-messages 10`.    

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
