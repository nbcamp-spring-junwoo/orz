package com.junwoo.ott.global.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

  @Value("${elasticsearch.host}")
  private String serverHost;
  @Value("${elasticsearch.port}")
  private String serverPort;
  @Value("${elasticsearch.apiKey}")
  private String apiKey;
  private final String authorization = "Authorization";
  private final String key = "ApiKey ";

  @Bean
  public RestClient restClient() {

    return RestClient.builder(HttpHost.create("http://" + serverHost + ":" + serverPort))
        .setDefaultHeaders(new Header[]{new BasicHeader(authorization, key + apiKey)})
        .build();
  }

  @Bean
  public ElasticsearchClient elasticsearchClient() {
    ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule());
    JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper(objectMapper);

    ElasticsearchTransport transport = new RestClientTransport(
        restClient(),
        jsonpMapper
    );

    return new ElasticsearchClient(transport);
  }

}
