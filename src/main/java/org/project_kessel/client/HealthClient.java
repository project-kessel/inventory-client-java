package org.project_kessel.client;

import io.grpc.Channel;
import org.project_kessel.api.inventory.v1.*;

import java.util.logging.Logger;

public class HealthClient {
    private static final Logger logger = Logger.getLogger(HealthClient.class.getName());

    private final KesselInventoryHealthServiceGrpc.KesselInventoryHealthServiceBlockingStub blockingStub;

    HealthClient(Channel channel){
        blockingStub = KesselInventoryHealthServiceGrpc.newBlockingStub(channel);
    }

    public GetReadyzResponse getReadyz(GetReadyzRequest request) {
        return blockingStub.getReadyz(request);
    }

    public GetLivezResponse getLivez(GetLivezRequest request) {
        return blockingStub.getLivez(request);
    }

}
