package app.kafka

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

class ConsumeData[DataObject](propertiesConfigConsumer:Properties,propertiesConfigTopics:Properties){

  val topic: String = propertiesConfigTopics.getProperty("topic.name")
  val pollTopic:Int = propertiesConfigTopics.getProperty("poll.topic").toInt
  val consumer:KafkaConsumer[String,DataObject] = new KafkaConsumer[String,DataObject](propertiesConfigConsumer)

  val topics:java.util.List[String] = new util.ArrayList[String]()
  topics.add(topic)
  consumer.subscribe(topics)

  var count:Int = 1

  def run(): Unit = {


    try
    {

      while (true) {
        val records: ConsumerRecords[String, DataObject] = consumer.poll(pollTopic)

        records.forEach(x => {

          println(x.value())

        })
      }

    }catch
      {
        case e:Exception => println("Error in ProducerData: ", e.printStackTrace())
      }finally {

      println("Messages Sent: ", count)
      count+=1
    }


  }
}
