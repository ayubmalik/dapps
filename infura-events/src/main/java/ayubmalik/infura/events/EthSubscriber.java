package ayubmalik.infura.events;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EthSubscriber {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String encodedSecret;
    private final String endpoint;
    private final HttpClient client;

    public EthSubscriber(String network, String projectId, String secret) {
        this.encodedSecret = encode(":" + secret);
        this.endpoint = String.format("https://%s.infura.io/v3/%s", network, projectId);
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .followRedirects(Redirect.NORMAL)
                .build();
    }

    public void subscribe() {
        var rpcBlockNum = """
                {"jsonrpc":"2.0","method":"eth_blockNumber","params": [],"id":1}
                """;
        var request = HttpRequest.newBuilder()
                .uri(url())
                .headers("content-type", "application/json", "authorizaton", encodedSecret)
                .POST(HttpRequest.BodyPublishers.ofString(rpcBlockNum))
                .build();

        try {
            var response = client.send(request, BodyHandlers.ofString());
            log.info("got response: {}", response);
        } catch (Exception e) {
            log.error("error", e);
        }
    }

    private String encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    private URI url() {
        try {
            return new URI(endpoint);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
