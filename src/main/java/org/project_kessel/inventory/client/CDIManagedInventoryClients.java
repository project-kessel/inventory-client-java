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
    @ApplicationScoped
    public RhelHostClient getHostClient(InventoryGrpcClientsManager manager) {
        return manager.getHostClient();
    }

    @Produces
    @ApplicationScoped
    public InventoryHealthClient getHealthClient(InventoryGrpcClientsManager manager) {
        return manager.getHealthClient();
    }

    @Produces
    @ApplicationScoped
    public K8sClustersClient getK8sClustersClient(InventoryGrpcClientsManager manager) {
        return manager.getK8sClustersClient();
    }

    @Produces
    @ApplicationScoped
    public K8sPolicyClient getKesselK8sPolicyClient(InventoryGrpcClientsManager manager) {
        return manager.getKesselK8sPolicyClient();
    }

    @Produces
    @ApplicationScoped
    public K8SPolicyIsPropagatedToK8SClusterClient getKesselK8SPolicyIsPropagatedToK8SClusterClient(InventoryGrpcClientsManager manager) {
        return manager.getKesselK8SPolicyIsPropagatedToK8SClusterClient();
    }

    @Produces
    @ApplicationScoped
    public NotificationsIntegrationClient getNotificationsIntegrationClient(InventoryGrpcClientsManager manager) {
        return manager.getNotificationsIntegrationClient();
    }
}
