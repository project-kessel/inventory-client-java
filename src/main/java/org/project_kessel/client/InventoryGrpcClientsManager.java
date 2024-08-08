package org.project_kessel.client;

import io.grpc.*;

import java.util.HashMap;

public class InventoryGrpcClientsManager {
    private static final HashMap<String, InventoryGrpcClientsManager> insecureManagers = new HashMap<>();
    private static final HashMap<String, InventoryGrpcClientsManager> secureManagers = new HashMap<>();

    private final ManagedChannel channel;

    public static synchronized InventoryGrpcClientsManager forInsecureClients(String targetUrl) {
        if (!insecureManagers.containsKey(targetUrl)) {
            var manager = new InventoryGrpcClientsManager(targetUrl, InsecureChannelCredentials.create());
            insecureManagers.put(targetUrl, manager);
        }
        return insecureManagers.get(targetUrl);
    }

    public static synchronized InventoryGrpcClientsManager forSecureClients(String targetUrl) {
        if (!secureManagers.containsKey(targetUrl)) {
            var tlsChannelCredentials = TlsChannelCredentials.create();
            var manager = new InventoryGrpcClientsManager(targetUrl, tlsChannelCredentials);
            secureManagers.put(targetUrl, manager);
        }
        return secureManagers.get(targetUrl);
    }


    public static synchronized void shutdownAll() {
        for (var manager : insecureManagers.values()) {
            manager.closeClientChannel();
        }
        insecureManagers.clear();
        for (var manager : secureManagers.values()) {
            manager.closeClientChannel();
        }
        secureManagers.clear();
    }

    public static synchronized void shutdownManager(InventoryGrpcClientsManager managerToShutdown) {
        var iter = insecureManagers.entrySet().iterator();
        while (iter.hasNext()) {
            var entry = iter.next();
            if(entry.getValue().channel == managerToShutdown.channel) {
                entry.getValue().closeClientChannel();
                iter.remove();
                return;
            }
        }
        iter = secureManagers.entrySet().iterator();
        while (iter.hasNext()) {
            var entry = iter.next();
            if(entry.getValue().channel == managerToShutdown.channel) {
                entry.getValue().closeClientChannel();
                iter.remove();
                return;
            }
        }
    }
    /**
     *
     * Bearer token and other things can be added to ChannelCredentials. New static factory methods can be added.
     * Config management also required.
     * @param targetUrl
     * @param credentials
     */
    private InventoryGrpcClientsManager(String targetUrl, ChannelCredentials credentials) {
        this.channel = Grpc.newChannelBuilder(targetUrl, credentials).build();
    }

    private void closeClientChannel() {
        channel.shutdown();
    }

    public RhelHostClient getHostClient() {
        return new RhelHostClient(channel);
    }

    public InventoryHealthClient getHealthClient() {
        return new InventoryHealthClient(channel);
    }

    public K8sClustersClient getK8sClustersClient() {
        return new K8sClustersClient(channel);
    }

    public PoliciesClient getPoliciesClient() {
        return new PoliciesClient(channel);
    }

    public PolicyRelationshipClient getRelationshipsClient() {
        return new PolicyRelationshipClient(channel);
    }

    public NotificationsIntegrationClient getNotificationsIntegrationClient() {
        return new NotificationsIntegrationClient(channel);
    }
}
