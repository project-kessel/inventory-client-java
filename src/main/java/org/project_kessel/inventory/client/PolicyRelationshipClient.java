package org.project_kessel.inventory.client;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import org.project_kessel.api.inventory.v1beta1.*;


import java.util.logging.Logger;

public class PolicyRelationshipClient {
    private static final Logger logger = Logger.getLogger(PoliciesClient.class.getName());

    private final KesselPolicyRelationshipServiceGrpc.KesselPolicyRelationshipServiceStub asyncStub;
    private final KesselPolicyRelationshipServiceGrpc.KesselPolicyRelationshipServiceBlockingStub blockingStub;

    PolicyRelationshipClient(Channel channel){
        asyncStub = KesselPolicyRelationshipServiceGrpc.newStub(channel);
        blockingStub = KesselPolicyRelationshipServiceGrpc.newBlockingStub(channel);
    }

    public CreatePolicyRelationshipResponse CreatePolicyRelationship(CreatePolicyRelationshipRequest request){
        return blockingStub.createPolicyRelationship(request);
    }

    public void CreatePolicyRelationship(CreatePolicyRelationshipRequest request, StreamObserver<CreatePolicyRelationshipResponse> responseObserver){
        asyncStub.createPolicyRelationship(request, responseObserver);
    }

    public Uni<CreatePolicyRelationshipResponse> CreatePolicyRelationshipUni(CreatePolicyRelationshipRequest request) {
        final UnicastProcessor<CreatePolicyRelationshipResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CreatePolicyRelationshipResponse>() {
            @Override
            public void onNext(CreatePolicyRelationshipResponse response) {
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
        CreatePolicyRelationship(request, streamObserver);
        return uni;
    }

    public DeleteResourceRelationshipByUrnResponse DeleteResourceRelationshipByURN(DeleteResourceRelationshipByUrnRequest request){
        return blockingStub.deleteResourceRelationshipByUrn(request);
    }

    public void DeleteResourceRelationshipByURN(DeleteResourceRelationshipByUrnRequest request, StreamObserver<DeleteResourceRelationshipByUrnResponse> responseObserver){
        asyncStub.deleteResourceRelationshipByUrn(request, responseObserver);
    }

    public Uni<DeleteResourceRelationshipByUrnResponse> DeleteResourceRelationshipByURNUni(DeleteResourceRelationshipByUrnRequest request) {
        final UnicastProcessor<DeleteResourceRelationshipByUrnResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<DeleteResourceRelationshipByUrnResponse>() {
            @Override
            public void onNext(DeleteResourceRelationshipByUrnResponse response) {
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
        DeleteResourceRelationshipByURN(request, streamObserver);
        return uni;
    }

    public UpdateResourceRelationshipByUrnHsResponse UpdateResourceRelationshipByURNHs(UpdateResourceRelationshipByUrnHsRequest request){
        return blockingStub.updateResourceRelationshipByUrnHs(request);
    }

    public void UpdateResourceRelationshipByURNHs(UpdateResourceRelationshipByUrnHsRequest request, StreamObserver<UpdateResourceRelationshipByUrnHsResponse> responseObserver){
        asyncStub.updateResourceRelationshipByUrnHs(request, responseObserver);
    }

    public Uni<UpdateResourceRelationshipByUrnHsResponse> UpdateResourceRelationshipByURNHsUni(UpdateResourceRelationshipByUrnHsRequest request) {
        final UnicastProcessor<UpdateResourceRelationshipByUrnHsResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<UpdateResourceRelationshipByUrnHsResponse>() {
            @Override
            public void onNext(UpdateResourceRelationshipByUrnHsResponse response) {
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
        UpdateResourceRelationshipByURNHs(request, streamObserver);
        return uni;
    }



}
