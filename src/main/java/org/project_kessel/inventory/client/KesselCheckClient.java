package org.project_kessel.inventory.client;

import java.util.logging.Logger;

import org.project_kessel.api.inventory.v1beta1.authz.CheckForCreateRequest;
import org.project_kessel.api.inventory.v1beta1.authz.CheckForCreateResponse;
import org.project_kessel.api.inventory.v1beta1.authz.CheckForUpdateRequest;
import org.project_kessel.api.inventory.v1beta1.authz.CheckForUpdateResponse;
import org.project_kessel.api.inventory.v1beta1.authz.CheckForViewRequest;
import org.project_kessel.api.inventory.v1beta1.authz.CheckForViewResponse;
import org.project_kessel.api.inventory.v1beta1.authz.KesselCheckServiceGrpc;
import org.project_kessel.clients.KesselClient;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;

public class KesselCheckClient extends KesselClient<KesselCheckServiceGrpc.KesselCheckServiceStub, KesselCheckServiceGrpc.KesselCheckServiceBlockingStub> {
    private static final Logger logger = Logger.getLogger(RhelHostClient.class.getName());

    KesselCheckClient(Channel channel) {
        super(KesselCheckServiceGrpc.newStub(channel), KesselCheckServiceGrpc.newBlockingStub(channel));
    }


    public CheckForViewResponse CheckForView(CheckForViewRequest request) {
        return blockingStub.checkForView(request);
    }

    public void CheckForView(CheckForViewRequest request, StreamObserver<CheckForViewResponse> responObserver) {
        asyncStub.checkForView(request, responObserver);
    }
    
    public Uni<CheckForViewResponse> CheckForViewUni(CheckForViewRequest request) {
        final UnicastProcessor<CheckForViewResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CheckForViewResponse>() {
            @Override
            public void onNext(CheckForViewResponse response) {
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
        CheckForView(request, streamObserver);
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


    public CheckForCreateResponse CheckForCreate(CheckForCreateRequest request) {
        return blockingStub.checkForCreate(request);
    }

    public void CheckForCreate(CheckForCreateRequest request, StreamObserver<CheckForCreateResponse> responObserver) {
        asyncStub.checkForCreate(request, responObserver);
    }
    
    public Uni<CheckForCreateResponse> CheckForCreateUni(CheckForCreateRequest request) {
        final UnicastProcessor<CheckForCreateResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CheckForCreateResponse>() {
            @Override
            public void onNext(CheckForCreateResponse response) {
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
        CheckForCreate(request, streamObserver);
        return uni;
    }
}
