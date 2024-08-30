package org.project_kessel.inventory.client;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import org.project_kessel.api.inventory.v1beta1.*;
import org.project_kessel.clients.KesselClient;


import java.util.logging.Logger;

public class K8sClustersClient extends KesselClient<KesselK8sClusterServiceGrpc.KesselK8sClusterServiceStub, KesselK8sClusterServiceGrpc.KesselK8sClusterServiceBlockingStub>{
    private static final Logger logger = Logger.getLogger(K8sClustersClient.class.getName());

    K8sClustersClient(Channel channel){
        super(KesselK8sClusterServiceGrpc.newStub(channel), KesselK8sClusterServiceGrpc.newBlockingStub(channel));
    
    }

    public void UpdateK8sCluster(UpdateK8sClusterRequest request, StreamObserver<UpdateK8sClusterResponse> responseObserver){
        asyncStub.updateK8sCluster(request, responseObserver);
    }

    public UpdateK8sClusterResponse UpdateK8sCluster(UpdateK8sClusterRequest request){
        return blockingStub.updateK8sCluster(request);
    }

    public Uni<UpdateK8sClusterResponse> UpdateK8sClusterUni(UpdateK8sClusterRequest request) {
        final UnicastProcessor<UpdateK8sClusterResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<UpdateK8sClusterResponse>() {
            @Override
            public void onNext(UpdateK8sClusterResponse response) {
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
        UpdateK8sCluster(request, streamObserver);
        return uni;
    }

    public void DeleteK8sCluster(DeleteK8sClusterRequest request, StreamObserver<DeleteK8sClusterResponse> responseObserver){
        asyncStub.deleteK8sCluster(request, responseObserver);
    }

    public DeleteK8sClusterResponse DeleteK8sCluster(DeleteK8sClusterRequest request){
        return blockingStub.deleteK8sCluster(request);
    }

    public Uni<DeleteK8sClusterResponse> DeleteK8sClusterUni(DeleteK8sClusterRequest request) {
        final UnicastProcessor<DeleteK8sClusterResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<DeleteK8sClusterResponse>() {
            @Override
            public void onNext(DeleteK8sClusterResponse response) {
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
        DeleteK8sCluster(request, streamObserver);
        return uni;
    }

    public void CreateK8sCluster(CreateK8sClusterRequest request, StreamObserver<CreateK8sClusterResponse> responseObserver){
        asyncStub.createK8sCluster(request, responseObserver);
    }

    public CreateK8sClusterResponse CreateK8sCluster(CreateK8sClusterRequest request){
        return blockingStub.createK8sCluster(request);
    }

    public Uni<CreateK8sClusterResponse> CreateK8sClusterUni(CreateK8sClusterRequest request) {
        final UnicastProcessor<CreateK8sClusterResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CreateK8sClusterResponse>() {
            @Override
            public void onNext(CreateK8sClusterResponse response) {
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
        CreateK8sCluster(request, streamObserver);
        return uni;
    }



}
