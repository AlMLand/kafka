FROM confluentinc/cp-kafka-connect-base:7.3.0

ENV CONNECT_PLUGIN_PATH="/usr/share/java,/usr/share/confluent-hub-components,/data/connect-jars"

COPY build/libs/kafkaConnectCustom-0.0.1-SNAPSHOT.jar /opt/kafka/plugins/
