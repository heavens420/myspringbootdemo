package com.es.eslearn.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;


@Configuration
//@ConfigurationProperties(prefix = "es")
public class EsConfig extends AbstractElasticsearchConfiguration {

//    @Value("${es.host}")
    private String host = "jing.tk";
//    @Value("${es.port}")
    private int port = 50010;

    @Override
    public RestHighLevelClient elasticsearchClient() {
//        final ClientConfiguration configuration = ClientConfiguration.builder().connectedTo("jing.tk:50010").build();
//        return RestClients.create(configuration).rest();
        System.out.println("0---------------------------------");
//        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));
        return new RestHighLevelClient(RestClient.builder(new HttpHost("81.68.241.197", 50010, "http")));
    }

}
