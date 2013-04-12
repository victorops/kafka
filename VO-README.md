# Additional build/run instructions for VictorOps

In addition to the README.md in this repo, this document gives more specific instructions related to using kafka in a dev situation at VictorOps.  This describes only a single Zookeeper
and single Kafka server setup, which should be fine for dev purposes.  It is relatively simple to run multiples, but that's not discussed in any detail here.

# Build
We build for Scala 2.10.1.  In the root of your clone, run:
> __sbt "++2.10.1 package"__

# Filesystem prep
The sample/default configurations that come with Kafka have Zookeeper and Kafka storing their data in /tmp.  For persistence of ZK and Kafka data across reboots, make the following directories:

 *  /var/kafka/kafka-logs/
 *  /var/kafka/kafka_metrics/
 *  /var/kafka/zookeeper/

Ensure that these directories writable by the user under which ZK and Kafka will run.

# Kafka config
Edit (or make a copy of and edit) config/server.properties.  Where "/tmp" appears in the file, change it to "/var/kafka" to match
the directories you just created.

In the "Log Basics" section, add the following lines:

>auto.create.topics.enable=true

> default.replication.factor=1

If you will run multiple Kafka brokers, change the replication factor accordingly.  You will need separate config files for each broker.
Each broker will need it's own broker ID and separte log and metrics directories.

In the "Zookeeper" section, change the zk.connect line to:

> zk.connect=localhost:2181/vo_kafka

If you will run multiple Zookeeper servers, add them before the path:

> zk.connect=localhost:2181,localhost:2182,localhost:2183/vo_kafka

The node path, /vo_kafka, appears only once, at the end of the list of ZK servers.

# Zookeeper config

Edit (or copy) config/zookeeper.properties.  Where "/tmp" appears in the file, change to "/var/kafka"

# Running examples (largely the same as the original README.md)

1. Start zookeeper:
> ./bin/zookeeper-server-start.sh config/zookeeper.properties

1. Create a zk node /vo\_kafka.  Our platform is configured to use the /vo\_kafka node as a namespace for broker and consumer data kept in Zookeeper. Unfortunately,
there is no easy way to create this path at the moment, so you have to do it manually.
> ./bin/zookeeper-shell.sh localhost:2181
> create /vo_kafka -<br /> &lt;ctrl&gt;-d to exit

1. Start one kafka broker:
> ./bin/kafka-server-start.sh config/server.properties

1. Create a topic, note we set replica to 1.  With auto.create.topics enabled in the server.properties file, this could be considered optional:
> ./bin/kafka-create-topic.sh --topic testtopic --replica 1 --zookeeper localhost:2181

1. Start a console producer:
> ./bin/kafka-console-producer.sh --broker-list localhost:9092 --sync --topic testtopic

1. Type some messages in the console.  <ctrl>-d to exit.  If you are running multiple brokers, comma separate them in the broker-list argument.

1. Start a console consumer
> ./bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic testtopic --from-beginning

If all that works, and your consumer is showing the text you typed into the producer, you should be good to go.

# See also
The original kafka quick start: <https://cwiki.apache.org/KAFKA/kafka-08-quick-start.html>

