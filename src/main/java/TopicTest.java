import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;

public class TopicTest {

  public static void main(String args[]) throws Exception {
    String bootstrap = args[0];
    String topic = args[1];
    System.out.println("Creating topic "+topic+" on "+bootstrap);
    Properties config = new Properties();
    config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
    config.put(AdminClientConfig.CLIENT_ID_CONFIG, "topics-test");
    config.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
    config.put("sasl.mechanism", "PLAIN");
    AdminClient admin = KafkaAdminClient.create(config);
    NewTopic newTopic = new NewTopic(topic, 1, (short)1);
    CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));
    result.values().get(topic).get();
  }

}
