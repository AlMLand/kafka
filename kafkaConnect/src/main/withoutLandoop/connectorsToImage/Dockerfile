FROM confluentinc/cp-kafka-connect-base:7.3.0
# v odnoj iz etih papok, budet ustanowlen confluent connector -> eto to chto ja zadaju v RUN stroke nizhe
ENV CONNECT_PLUGIN_PATH="/usr/share/java,/usr/share/confluent-hub-components,/data/connect-jars"

COPY ./kafkaConnectWikimediaJar/kafka-connect-wikimedia-1.0-all.jar /opt/kafka/plugins/

RUN confluent-hub install --no-prompt confluentinc/kafka-connect-datagen:0.6.0
RUN confluent-hub install --no-prompt confluentinc/kafka-connect-elasticsearch:14.0.5
RUN confluent-hub install --no-prompt confluentinc/kafka-connect-jdbc:10.6.4
