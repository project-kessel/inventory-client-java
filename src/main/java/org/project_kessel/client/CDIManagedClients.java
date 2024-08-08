package org.project_kessel.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

/**
 * A managed bean for providing relations api clients for injection in apps.
 * It has the current limitation that only one underlying grpc connection can be configured.
 * However, it is still possible to create more via InventoryGrpcClientsManager directly.
 * This class does nothing unless the client is being managed by a CDI container (e.g. Quarkus)
 */
@ApplicationScoped
public class CDIManagedClients {
    @Produces
    InventoryGrpcClientsManager getManager(Config config) {
        var isSecureClients = config.isSecureClients();
        var targetUrl = config.targetUrl();

        if (isSecureClients) {
            return InventoryGrpcClientsManager.forSecureClients(targetUrl);
        }

        return InventoryGrpcClientsManager.forInsecureClients(targetUrl);
    }

    @Produces
    public RhelHostClient getHostClient(InventoryGrpcClientsManager manager) {
        return manager.getHostClient();
    }

    @Produces
    public InventoryHealthClient getHealthClient(InventoryGrpcClientsManager manager) {
        return manager.getHealthClient();
    }

    @Produces
    public K8sClustersClient getK8sClustersClient(InventoryGrpcClientsManager manager) {
        return manager.getK8sClustersClient();
    }

    @Produces
    public PoliciesClient getPoliciesClient(InventoryGrpcClientsManager manager) {
        return manager.getPoliciesClient();
    }

    @Produces
    public PolicyRelationshipClient getRelationshipsClient(InventoryGrpcClientsManager manager) {
        return manager.getRelationshipsClient();
    }

    @Produces
    public NotificationsIntegrationClient getNotificationsIntegrationClient(InventoryGrpcClientsManager manager) {
        return manager.getNotificationsIntegrationClient();
    }
}
