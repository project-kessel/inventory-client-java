package org.project_kessel.example;

import org.project_kessel.api.inventory.v1beta1.CreateRhelHostRequest;
import org.project_kessel.api.inventory.v1beta1.RhelHost;
import org.project_kessel.client.InventoryGrpcClientsManager;

public class Caller {

    public static void main(String[] argv) {
        var url = "localhost:9000";
        var clientsManager = InventoryGrpcClientsManager.forInsecureClients(url);
        var hostClient = clientsManager.getHostClient();

        RhelHost host = RhelHost.newBuilder().build();
        CreateRhelHostRequest createRHELHostRequest =  CreateRhelHostRequest.newBuilder()
                .setHost(host).build();

        var hostResponse = hostClient.CreateRhelHost(createRHELHostRequest);
        System.out.println(hostResponse.getHost());

    }
}
