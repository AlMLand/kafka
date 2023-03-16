rootProject.name = "kafka"
include("kafkaBasics")
include("kafkaProducerWikimedia")
include("kafkaConsumerOpenSearch")
include("docs")
include("kafkaConnect")
include("kafkaConnect:kafkaConnectCustom")
findProject(":kafkaConnect:kafkaConnectCustom")?.name = "kafkaConnectCustom"
