package org.example.kafka.streams.demo;


import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;


// Original code:
// https://github.com/apache/kafka/blob/trunk/streams/quickstart/java/src/main/resources/archetype-resources/src/main/java/WordCount.java
public class WordCount {

    private static final WordCount INSTANCE = new WordCount();

    private WordCount() {}


    public static void main(String[] args) {
        // Define properties
        final Properties properties = INSTANCE.createProperties();

        // Define KafkaStreams
        final StreamsBuilder builder = new StreamsBuilder();
        INSTANCE.defineStream(builder);
        final KafkaStreams streams = new KafkaStreams(builder.build(), properties);

        // Prepare for starting KafkaStreams
        final CountDownLatch latch = INSTANCE.assignShutdownHook(streams);

        // Start KafkaStreams
        INSTANCE.startKafkaStreams(streams, latch);

        // Exit
        System.exit(0);
    }

    private Properties createProperties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return properties;
    }

    private void defineStream(final StreamsBuilder builder) {
        builder.<String, String>stream("streams-plaintext-input")
            // https://kafka.apache.org/23/javadoc/org/apache/kafka/streams/kstream/KStream.html
            .flatMapValues(value -> Arrays.asList(value.toLowerCase(Locale.getDefault()).split("\\W+")))
            .groupBy((key, value) -> value)
            .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"))
            .toStream()
            .to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));
    }

    private CountDownLatch assignShutdownHook(final KafkaStreams streams) {
        final CountDownLatch latch = new CountDownLatch(1);
        final Runnable shutdownHook = () -> {
            streams.close();
            latch.countDown();
        };
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "streams-shutdown-hook"));
        return latch;
    }

    private void startKafkaStreams(final KafkaStreams streams, final CountDownLatch latch) {
        try {
            streams.start();
            latch.await();
        } catch (final InterruptedException e) {
            System.exit(1);
        }
    }
}