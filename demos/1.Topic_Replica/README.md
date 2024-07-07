# Topic Replica

## Goal
Simplified execution of multiple Kafka-brokers and observation of in-sync replicas upo failure and restart of brokers.

## Requirements
Support for docker-compose locally ([DockerDesktop](https://www.docker.com/products/docker-desktop/))

## Docker Image for Kafka
The same configuration can be used with [Apache Kafka 3.7.0](https://hub.docker.com/layers/apache/kafka/3.7.0/images/sha256-3e324d2bd331570676436b24f625e5dcf1facdfbd62efcffabc6b69b1abc13cc?context=explore) or [Confluent Kafka 7.6.1](https://hub.docker.com/layers/confluentinc/cp-kafka/7.6.1/images/sha256-0bae779102557a9e117b5f6729cba4e8b6d49b76eddad956d4e2e1223ecf64a3?context=explore).


## Execution
1. Start DockerDesktop.
2. Open a terminal (terminal 1) and start the Apache docker-compose file:
    - `docker-compose -f docker-compose-apache-3brokers-controller-kraft.yaml up`
    - In the logs, look for "Kafka Server started" from kafka1, kafka2, kafka3, and kafka4.
3.  Open another terminl (terminal 2) and create a topic with 3 replicas: `docker exec -it kafka1 /opt/kafka/bin/kafka-topics.sh --create --topic kafka-replica-test --replication-factor 3 --bootstrap-server localhost:9092`
4.  In terminal 2, monitor the in-sync replicas of the topic: `docker exec -it kafka1 /opt/kafka/bin/kafka-topics.sh --describe --topic kafka-replica-test --bootstrap-server localhost:9092`
5.  Stop the container kafka2 and monitor replicas (Step 4) again.
6.  Restart the container kafka2 and monitor replicas (Step 4) again.
7.  Stop the containers kafka2 and kafka3 and monitor replicas (Step 4) again.
8.  Restart the containers kafka2 and kafka3 and monitor replicas (Step 4) again.
9. Do the Cleanup step detailed below.
10. Repeat the previous steps by first (Step 2) running the [confluent docker-compose file](./docker-compose-confluent-3brokers-controller-kraft.yaml) and then repeating the following steps.
    - Note that `docker ... /opt/kafka/bin/kafka-*.sh ...` should be replaced with `docker ... /bin/kafka-* ...`. 

## Cleanup
  - Stop the Kafka containers (DockerDesktop >> Containers)
  - Delete the Kafka containers (DockerDesktop >> Containers)
  - Delete the volumes associated with the Kafka containers (DockerDesktop >> Volumes)
  - Run `docker system prune --volumes --force`
  - _Quit_ (not just close) DockerDesktop
  - Run (Windows with WSL): `wsl --shutdown`
