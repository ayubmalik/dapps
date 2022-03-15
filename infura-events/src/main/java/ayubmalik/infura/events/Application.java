package ayubmalik.infura.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    var network = System.getenv("ETH_NETWORK");
    var projectId = System.getenv("PROJECT_ID");
    var secret = System.getenv("SECRET");

    var subscriber = new EthSubscriber(network, projectId, secret);
    subscriber.subscribe();
  }
}
