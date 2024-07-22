# Assignment of Configuration Parameters

## Goal
- Forward problem
  - Listing all Apache Kafka configuration properties and the associated "entities" (ex. <i>ssl.engine.factory.class - producer</i>)
- Inverse problem
  - Finding the list of "entities" associated with a given configuration property

## Execution
- Local: Launch [config-param-assigner.html](./config-param-assigner.html)
- GitHub: [HTML Preview](https://htmlpreview.github.io/?https://github.com/nurlicht/Apache-Kafka-to-go/blob/main/tools/1.Doc_Search/config-param-assigner.html)

## Expected Result
![](screenshot.png)

## Miscellaneous
  - [List of constants](https://kafka.apache.org/37/javadoc/constant-values.html) of the [Javadoc](https://kafka.apache.org/37/javadoc/index.html).
  - [Kafka-scripts](https://github.com/apache/kafka/blob/trunk/bin/kafka-console-consumer.sh) and [associated classes](https://github.com/apache/kafka/blob/trunk/tools/src/main/java/org/apache/kafka/tools/consumer/ConsoleConsumer.java)
