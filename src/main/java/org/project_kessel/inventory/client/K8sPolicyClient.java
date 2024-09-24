package org.project_kessel.inventory.client;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import org.project_kessel.api.inventory.v1beta1.resources.*;
import org.project_kessel.clients.KesselClient;

import java.util.logging.Logger;

public class K8sPolicyClient extends KesselClient<KesselK8sPolicyServiceGrpc.KesselK8sPolicyServiceStub, KesselK8sPolicyServiceGrpc.KesselK8sPolicyServiceBlockingStub>{
    private static final Logger logger = Logger.getLogger(K8sPolicyClient.class.getName());

    K8sPolicyClient(Channel channel){
        super(KesselK8sPolicyServiceGrpc.newStub(channel), KesselK8sPolicyServiceGrpc.newBlockingStub(channel));
    }

    public CreateK8sPolicyResponse CreateK8sPolicy(CreateK8sPolicyRequest request) {
       return blockingStub.createK8sPolicy(request);
    }

    public void CreateK8sPolicy(CreateK8sPolicyRequest request, StreamObserver<CreateK8sPolicyResponse> responseObserver) {
         asyncStub.createK8sPolicy(request, responseObserver);
    }

    public Uni<CreateK8sPolicyResponse> CreateK8sPolicyUni(CreateK8sPolicyRequest request) {
        final UnicastProcessor<CreateK8sPolicyResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CreateK8sPolicyResponse>() {
            @Override
            public void onNext(CreateK8sPolicyResponse response) {
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
        CreateK8sPolicy(request, streamObserver);
        return uni;
    }

    public DeleteK8sPolicyResponse DeleteK8sPolicy(DeleteK8sPolicyRequest request) {
        return blockingStub.deleteK8sPolicy(request);
    }

    public void DeleteK8sPolicy(DeleteK8sPolicyRequest request, StreamObserver<DeleteK8sPolicyResponse> responseObserver) {
        asyncStub.deleteK8sPolicy(request, responseObserver);
    }

    public Uni<DeleteK8sPolicyResponse> DeleteK8sPolicyUni(DeleteK8sPolicyRequest request) {
        final UnicastProcessor<DeleteK8sPolicyResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<DeleteK8sPolicyResponse>() {
            @Override
            public void onNext(DeleteK8sPolicyResponse response) {
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
        DeleteK8sPolicy(request, streamObserver);
        return uni;
    }

    public UpdateK8sPolicyResponse UpdateK8sPolicy(UpdateK8sPolicyRequest request) {
        return blockingStub.updateK8sPolicy(request);
    }

    public void UpdateK8sPolicy(UpdateK8sPolicyRequest request, StreamObserver<UpdateK8sPolicyResponse> responseObserver) {
        asyncStub.updateK8sPolicy(request, responseObserver);
    }

    public Uni<UpdateK8sPolicyResponse> UpdateK8sPolicyUni(UpdateK8sPolicyRequest request) {
        final UnicastProcessor<UpdateK8sPolicyResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<UpdateK8sPolicyResponse>() {
            @Override
            public void onNext(UpdateK8sPolicyResponse response) {
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
        UpdateK8sPolicy(request, streamObserver);
        return uni;
    }
}
