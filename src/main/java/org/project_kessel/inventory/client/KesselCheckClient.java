package org.project_kessel.inventory.client;

import java.util.logging.Logger;

import org.project_kessel.api.inventory.v1beta1.authz.CheckForUpdateRequest;
import org.project_kessel.api.inventory.v1beta1.authz.CheckForUpdateResponse;
import org.project_kessel.api.inventory.v1beta1.authz.CheckRequest;
import org.project_kessel.api.inventory.v1beta1.authz.CheckResponse;
import org.project_kessel.api.inventory.v1beta1.authz.KesselCheckServiceGrpc;
import org.project_kessel.clients.KesselClient;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;

public class KesselCheckClient extends KesselClient<KesselCheckServiceGrpc.KesselCheckServiceStub, KesselCheckServiceGrpc.KesselCheckServiceBlockingStub> {
    private static final Logger logger = Logger.getLogger(KesselCheckClient.class.getName());

    KesselCheckClient(Channel channel) {
        super(KesselCheckServiceGrpc.newStub(channel), KesselCheckServiceGrpc.newBlockingStub(channel));
    }


    public CheckResponse Check(CheckRequest request) {
        return blockingStub.check(request);
    }

    public void Check(CheckRequest request, StreamObserver<CheckResponse> responObserver) {
        asyncStub.check(request, responObserver);
    }
    
    public Uni<CheckResponse> CheckUni(CheckRequest request) {
        final UnicastProcessor<CheckResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CheckResponse>() {
            @Override
            public void onNext(CheckResponse response) {
                responseProcessor.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                responseProcessor.onError(t);
            }

            @Override
            public void onCompleted() {
                responseProcessor.onComplete();
            }
        };
        var uni = Uni.createFrom().publisher(responseProcessor);
        Check(request, streamObserver);
        return uni;
    }


    public CheckForUpdateResponse CheckForUpdate(CheckForUpdateRequest request) {
        return blockingStub.checkForUpdate(request);
    }

    public void CheckForUpdate(CheckForUpdateRequest request, StreamObserver<CheckForUpdateResponse> responObserver) {
        asyncStub.checkForUpdate(request, responObserver);
    }
    
    public Uni<CheckForUpdateResponse> CheckForUpdateUni(CheckForUpdateRequest request) {
        final UnicastProcessor<CheckForUpdateResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CheckForUpdateResponse>() {
            @Override
            public void onNext(CheckForUpdateResponse response) {
                responseProcessor.onNext(response);
            }

            @Override
            public void onError(Throwable t) {
                responseProcessor.onError(t);
            }

            @Override
            public void onCompleted() {
                responseProcessor.onComplete();
            }
        };
        var uni = Uni.createFrom().publisher(responseProcessor);
        CheckForUpdate(request, streamObserver);
        return uni;
    }
}
