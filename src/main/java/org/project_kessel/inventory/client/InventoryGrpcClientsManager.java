package org.project_kessel.inventory.client;

import io.grpc.Channel;
import org.project_kessel.clients.ChannelManager;
import org.project_kessel.clients.KesselClientsManager;

public class InventoryGrpcClientsManager extends KesselClientsManager {
    private InventoryGrpcClientsManager(Channel channel) {
        super(channel);
    }

    private static final String CHANNEL_MANAGER_KEY = InventoryGrpcClientsManager.class.getName();

    public static InventoryGrpcClientsManager forInsecureClients(String targetUrl) {
        return new InventoryGrpcClientsManager(ChannelManager.getInstance(CHANNEL_MANAGER_KEY).forInsecureClients(targetUrl));
    }

    public static InventoryGrpcClientsManager forInsecureClients(String targetUrl, Config.AuthenticationConfig authnConfig) throws RuntimeException {
        return new InventoryGrpcClientsManager(ChannelManager.getInstance(CHANNEL_MANAGER_KEY).forInsecureClients(targetUrl, AuthnConfigConverter.convert(authnConfig)));
    }

    public static InventoryGrpcClientsManager forSecureClients(String targetUrl) {
        return new InventoryGrpcClientsManager(ChannelManager.getInstance(CHANNEL_MANAGER_KEY).forSecureClients(targetUrl));
    }

    public static InventoryGrpcClientsManager forSecureClients(String targetUrl, Config.AuthenticationConfig authnConfig) {
        return new InventoryGrpcClientsManager(ChannelManager.getInstance(CHANNEL_MANAGER_KEY).forSecureClients(targetUrl, AuthnConfigConverter.convert(authnConfig)));
    }

    public static void shutdownAll() {
        ChannelManager.getInstance(CHANNEL_MANAGER_KEY).shutdownAll();
    }

    public static void shutdownManager(InventoryGrpcClientsManager managerToShutdown) {
        ChannelManager.getInstance(CHANNEL_MANAGER_KEY).shutdownChannel(managerToShutdown.channel);
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
