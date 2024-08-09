package org.project_kessel.inventory.client;

import io.grpc.Channel;
import org.project_kessel.api.inventory.v1.*;


import java.util.logging.Logger;

public class InventoryHealthClient {
    private static final Logger logger = Logger.getLogger(InventoryHealthClient.class.getName());

    private final KesselInventoryHealthServiceGrpc.KesselInventoryHealthServiceBlockingStub blockingStub;

    InventoryHealthClient(Channel channel){
        blockingStub = KesselInventoryHealthServiceGrpc.newBlockingStub(channel);
    }

    public GetReadyzResponse getReadyz(GetReadyzRequest request) {
        return blockingStub.getReadyz(request);
    }

    public GetLivezResponse getLivez(GetLivezRequest request) {
        return blockingStub.getLivez(request);
    }

}
