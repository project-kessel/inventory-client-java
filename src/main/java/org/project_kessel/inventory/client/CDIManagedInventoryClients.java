package org.project_kessel.inventory.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.project_kessel.clients.authn.AuthenticationConfig.AuthMode;

/**
 * A managed bean for providing clients for injection in apps.
 * It has the current limitation that only one underlying grpc connection can be configured.
 * However, it is still possible to create more via KesselClientsManager implementation directly.
 * This class does nothing unless the client is being managed by a CDI container (e.g. Quarkus)
 */
@ApplicationScoped
public class CDIManagedInventoryClients {
    @Produces
    InventoryGrpcClientsManager getManager(Config config) {
        var isSecureClients = config.isSecureClients();
        var targetUrl = config.targetUrl();
        var authnEnabled = config.authenticationConfig().map(t -> !t.mode().equals(AuthMode.DISABLED)).orElse(false);
        if (isSecureClients) {
            if(authnEnabled) {
                return InventoryGrpcClientsManager.forSecureClients(targetUrl, config.authenticationConfig().get());
            }
            return InventoryGrpcClientsManager.forSecureClients(targetUrl);
        }

        if(authnEnabled) {
            return InventoryGrpcClientsManager.forInsecureClients(targetUrl, config.authenticationConfig().get());
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
