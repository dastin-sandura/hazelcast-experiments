package com.blogspot.dastinsandura;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.IdGenerator;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        switch (args[0]) {
            case "client":
                createNewClientAndReadData();
                break;
            case "server":
                createNewNodeAndAddMessagesToCluster();
                break;
            default:
                throw new RuntimeException("Either server or client modes are available. Choose one.");
        }
    }

    private static void createNewClientAndReadData() {
        ClientConfig config = new ClientConfig();
        config.setAddresses(Collections.singletonList("127.0.0.1:5701"));
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
        IMap<Object, Object> data = client.getMap("data");
        System.out.println(data.size());

    }

    private static void createNewNodeAndAddMessagesToCluster() {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        System.out.println("Hello world! " + hazelcastInstance);
        IMap<Object, Object> data = hazelcastInstance.getMap("data");
        IdGenerator idGenerator = hazelcastInstance.getIdGenerator("idName");
        for (int i = 0; i < 5; i++) {
            data.put(idGenerator.newId(), String.format("Message %d", i));
        }
    }
}