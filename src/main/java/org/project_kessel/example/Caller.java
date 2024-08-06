package org.project_kessel.example;

import org.project_kessel.api.inventory.v1.GetLivezRequest;
import org.project_kessel.api.inventory.v1beta1.CreateRhelHostRequest;
import org.project_kessel.api.inventory.v1beta1.Metadata;
import org.project_kessel.api.inventory.v1beta1.RhelHost;
import org.project_kessel.client.InventoryGrpcClientsManager;

public class Caller {

    public static void main(String[] argv) {
        var url = "localhost:9081";
        var clientsManager = InventoryGrpcClientsManager.forInsecureClients(url);
          var healthClient = clientsManager.getHealthClient();
          var response = healthClient.getLivez(GetLivezRequest.newBuilder().build());
          System.out.println(response);

    }
}
