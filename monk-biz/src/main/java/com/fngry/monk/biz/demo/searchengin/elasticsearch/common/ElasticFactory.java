package com.fngry.monk.biz.demo.searchengin.elasticsearch.common;


import com.fngry.monk.biz.constant.BizConstants;
import com.fngry.monk.common.util.SpringProperty;
import com.fngry.monk.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by gaorongyu on 16/12/13.
 */
@Slf4j
public class ElasticFactory {

    public static SearchEngineClient getClient() {
        return ElasticFactoryHook.client;
    }

    private static class ElasticFactoryHook {

        private static SearchEngineClient client = null;

        static {
            TransportClient transportClient = getTransportClient();

            initSearchEngineClient(transportClient);

            addShutdownHook();
        }

        private static TransportClient getTransportClient() {
            String clusterName = SpringProperty.getProperty("elastic.cluster.name");
            String address = SpringProperty.getProperty("elastic.address");

            // build transport client
            Settings settings = Settings.settingsBuilder()
                    // sniff the rest of cluster so we only need to set one ip
                    .put("client.transport.sniff", true)
                    .put("cluster.name", clusterName)
                    .put("client.transport.ping_timeout", "20s")
                    .put("client.transport.nodes_sampler_interval", "20s")
                    .build();

            TransportClient transportClient = TransportClient.builder()
                    .settings(settings)
                    .build();

            // set elastic address
            String[] addresses = StringUtil.split(address, BizConstants.TOKEN_COMMA);
            for (int i = 0; i < addresses.length; i++) {
                String[] ipPort = StringUtil.split(addresses[i], BizConstants.TOKEN_COLON);
                String ip = ipPort[0];
                int port = Integer.parseInt(ipPort[1]);

                try {
                    transportClient = transportClient.addTransportAddress(
                            new InetSocketTransportAddress(
                                    InetAddress.getByName(ip), port));
                } catch (UnknownHostException e) {
                    log.error(" search engine client add address error ", e);
                }
            }
            return transportClient;
        }

        private static void initSearchEngineClient(TransportClient transportClient) {
            client = new SearchEngineClient(transportClient);
        }

        private static void addShutdownHook() {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        log.info(" search engine client is close now!!! ");
                        if (client != null) {
                            client.close();
                        }
                    } catch (Exception e) {
                        log.error(" close search engine client error ", e);
                    }
                }
            });
        }

    }

}
