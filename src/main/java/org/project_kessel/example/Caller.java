package org.project_kessel.example;

import org.project_kessel.api.inventory.v1beta1.CreateRHELHostRequest;
import org.project_kessel.api.inventory.v1beta1.RhelHost;
import org.project_kessel.client.InventoryGrpcClientsManager;

public class Caller {

    public static void main(String[] argv) {
        var url = "localhost:9000";
        var clientsManager = InventoryGrpcClientsManager.forInsecureClients(url);
        var hostClient = clientsManager.getHostClient();

        RhelHost host = RhelHost.newBuilder().build();
        CreateRHELHostRequest createRHELHostRequest = CreateRHELHostRequest.newBuilder()
                .setHost(host).build();

        var hostResponse = hostClient.CreateRHELHost(createRHELHostRequest);
        System.out.println(hostResponse.getHost());

    }
}
