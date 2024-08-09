package org.project_kessel.inventory.example;

import org.project_kessel.api.inventory.v1.GetLivezRequest;
import org.project_kessel.inventory.client.InventoryGrpcClientsManager;

public class Caller {

    public static void main(String[] argv) {
        var url = "localhost:9081";
        var clientsManager = InventoryGrpcClientsManager.forInsecureClients(url);
          var healthClient = clientsManager.getHealthClient();
          var response = healthClient.getLivez(GetLivezRequest.newBuilder().build());
          System.out.println(response);

    }
}
