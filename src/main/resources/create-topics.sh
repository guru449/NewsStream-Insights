#!/bin/bash

# Kafka broker address
KAFKA_BROKER=kafka:9092

# Wait for Kafka to be fully online
echo "Waiting for Kafka to be fully online..."
cub kafka-ready -b $KAFKA_BROKER 1 30

# Create the Kafka topic
echo "Creating the 'raw-news' topic..."
kafka-topics --bootstrap-server $KAFKA_BROKER --topic raw-news --create --partitions 1 --replication-factor 1 --if-not-exists

echo "Topic 'raw-news' created."
