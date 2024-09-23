package org.project_kessel.inventory.client;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import org.project_kessel.api.inventory.v1beta1.relationships.*;
import org.project_kessel.clients.KesselClient;


import java.util.logging.Logger;

public class K8SPolicyIsPropagatedToK8SClusterClient extends KesselClient<KesselK8SPolicyIsPropagatedToK8SClusterServiceGrpc.KesselK8SPolicyIsPropagatedToK8SClusterServiceStub, KesselK8SPolicyIsPropagatedToK8SClusterServiceGrpc.KesselK8SPolicyIsPropagatedToK8SClusterServiceBlockingStub>{
    private static final Logger logger = Logger.getLogger(K8sPolicyClient.class.getName());

    K8SPolicyIsPropagatedToK8SClusterClient(Channel channel){
        super(KesselK8SPolicyIsPropagatedToK8SClusterServiceGrpc.newStub(channel), KesselK8SPolicyIsPropagatedToK8SClusterServiceGrpc.newBlockingStub(channel));
    }

    public CreateK8SPolicyIsPropagatedToK8SClusterResponse CreateK8SPolicyIsPropagatedToK8SCluster(CreateK8SPolicyIsPropagatedToK8SClusterRequest request){
        return blockingStub.createK8SPolicyIsPropagatedToK8SCluster(request);
    }

    public void CreateK8SPolicyIsPropagatedToK8SCluster(CreateK8SPolicyIsPropagatedToK8SClusterRequest request, StreamObserver<CreateK8SPolicyIsPropagatedToK8SClusterResponse> responseObserver){
        asyncStub.createK8SPolicyIsPropagatedToK8SCluster(request, responseObserver);
    }

    public Uni<CreateK8SPolicyIsPropagatedToK8SClusterResponse> CreateK8SPolicyIsPropagatedToK8SClusterUni(CreateK8SPolicyIsPropagatedToK8SClusterRequest request) {
        final UnicastProcessor<CreateK8SPolicyIsPropagatedToK8SClusterResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CreateK8SPolicyIsPropagatedToK8SClusterResponse>() {
            @Override
            public void onNext(CreateK8SPolicyIsPropagatedToK8SClusterResponse response) {
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
        CreateK8SPolicyIsPropagatedToK8SCluster(request, streamObserver);
        return uni;
    }

    public DeleteK8SPolicyIsPropagatedToK8SClusterResponse DeleteK8SPolicyIsPropagatedToK8SCluster(DeleteK8SPolicyIsPropagatedToK8SClusterRequest request){
        return blockingStub.deleteK8SPolicyIsPropagatedToK8SCluster(request);
    }

    public void DeleteK8SPolicyIsPropagatedToK8SCluster(DeleteK8SPolicyIsPropagatedToK8SClusterRequest request, StreamObserver<DeleteK8SPolicyIsPropagatedToK8SClusterResponse> responseObserver){
        asyncStub.deleteK8SPolicyIsPropagatedToK8SCluster(request, responseObserver);
    }

    public Uni<DeleteK8SPolicyIsPropagatedToK8SClusterResponse> DeleteK8SPolicyIsPropagatedToK8SClusterUni(DeleteK8SPolicyIsPropagatedToK8SClusterRequest request) {
        final UnicastProcessor<DeleteK8SPolicyIsPropagatedToK8SClusterResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<DeleteK8SPolicyIsPropagatedToK8SClusterResponse>() {
            @Override
            public void onNext(DeleteK8SPolicyIsPropagatedToK8SClusterResponse response) {
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
        DeleteK8SPolicyIsPropagatedToK8SCluster(request, streamObserver);
        return uni;
    }

    public UpdateK8SPolicyIsPropagatedToK8SClusterResponse UpdateK8SPolicyIsPropagatedToK8SCluster(UpdateK8SPolicyIsPropagatedToK8SClusterRequest request){
        return blockingStub.updateK8SPolicyIsPropagatedToK8SCluster(request);
    }

    public void UpdateK8SPolicyIsPropagatedToK8SCluster(UpdateK8SPolicyIsPropagatedToK8SClusterRequest request, StreamObserver<UpdateK8SPolicyIsPropagatedToK8SClusterResponse> responseObserver){
        asyncStub.updateK8SPolicyIsPropagatedToK8SCluster(request, responseObserver);
    }

    public Uni<UpdateK8SPolicyIsPropagatedToK8SClusterResponse> UpdateK8SPolicyIsPropagatedToK8SClusterUni(UpdateK8SPolicyIsPropagatedToK8SClusterRequest request) {
        final UnicastProcessor<UpdateK8SPolicyIsPropagatedToK8SClusterResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<UpdateK8SPolicyIsPropagatedToK8SClusterResponse>() {
            @Override
            public void onNext(UpdateK8SPolicyIsPropagatedToK8SClusterResponse response) {
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
        UpdateK8SPolicyIsPropagatedToK8SCluster(request, streamObserver);
        return uni;
    }



}
