import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.AlterConfigsResult;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;

public class TopicTest {

  public static void main(String args[]) throws Exception {
    String bootstrap = args[0];
    String topic = args[1];
    Properties config = new Properties();
    config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
    config.put(AdminClientConfig.CLIENT_ID_CONFIG, "topics-test");
    config.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
    config.put("sasl.mechanism", "PLAIN");
    AdminClient admin = KafkaAdminClient.create(config);

    System.out.println("Creating topic "+topic+" on "+bootstrap);
    NewTopic newTopic = new NewTopic(topic, 1, (short)1);
    CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));
    result.values().get(topic).get();

    ConfigResource topicResource = new ConfigResource(ConfigResource.Type.TOPIC, topic);

    System.out.println("Getting topic "+topic+" configuration");
    DescribeConfigsResult describeResult = admin.describeConfigs(Collections.singleton(topicResource));
    Map<ConfigResource, Config> topicConfig = describeResult.all().get();
    System.out.println(config);

    System.out.println("Modifying topic "+topic+" configuration");
    ConfigEntry retentionEntry = new ConfigEntry(TopicConfig.RETENTION_MS_CONFIG, "12345");
    Map<ConfigResource, Config> updateConfig = new HashMap<ConfigResource, Config>();
    updateConfig.put(topicResource, new Config(Collections.singleton(retentionEntry)));
    AlterConfigsResult alterResult = admin.alterConfigs(updateConfig);
    alterResult.all().get();
  }

}
