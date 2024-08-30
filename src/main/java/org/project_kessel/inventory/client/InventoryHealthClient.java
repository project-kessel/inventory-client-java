package org.project_kessel.inventory.client;

import io.grpc.Channel;
import org.project_kessel.api.inventory.v1.*;
import org.project_kessel.clients.*;


import java.util.logging.Logger;

public class InventoryHealthClient extends KesselClient<KesselInventoryHealthServiceGrpc.KesselInventoryHealthServiceStub, KesselInventoryHealthServiceGrpc.KesselInventoryHealthServiceBlockingStub>{
    private static final Logger logger = Logger.getLogger(InventoryHealthClient.class.getName());

    InventoryHealthClient(Channel channel){
        super(KesselInventoryHealthServiceGrpc.newStub(channel), KesselInventoryHealthServiceGrpc.newBlockingStub(channel));
    }

    public GetReadyzResponse getReadyz(GetReadyzRequest request) {
        return blockingStub.getReadyz(request);
    }

    public GetLivezResponse getLivez(GetLivezRequest request) {
        return blockingStub.getLivez(request);
    }

}
