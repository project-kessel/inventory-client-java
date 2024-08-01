package org.project_kessel.client;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import org.project_kessel.api.inventory.v1beta1.*;

import java.util.logging.Logger;

public class RelationshipsClient {
    private static final Logger logger = Logger.getLogger(PoliciesClient.class.getName());

    private final RelationshipsServiceGrpc.RelationshipsServiceStub asyncStub;
    private final RelationshipsServiceGrpc.RelationshipsServiceBlockingStub blockingStub;

    RelationshipsClient(Channel channel){
        asyncStub = RelationshipsServiceGrpc.newStub(channel);
        blockingStub = RelationshipsServiceGrpc.newBlockingStub(channel);
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

    public DeleteResourceRelationshipByURNResponse DeleteResourceRelationshipByURN(DeleteResourceRelationshipByURNRequest request){
        return blockingStub.deleteResourceRelationshipByURN(request);
    }

    public void DeleteResourceRelationshipByURN(DeleteResourceRelationshipByURNRequest request, StreamObserver<DeleteResourceRelationshipByURNResponse> responseObserver){
        asyncStub.deleteResourceRelationshipByURN(request, responseObserver);
    }

    public Uni<DeleteResourceRelationshipByURNResponse> DeleteResourceRelationshipByURNUni(DeleteResourceRelationshipByURNRequest request) {
        final UnicastProcessor<DeleteResourceRelationshipByURNResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<DeleteResourceRelationshipByURNResponse>() {
            @Override
            public void onNext(DeleteResourceRelationshipByURNResponse response) {
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

    public UpdateResourceRelationshipByURNResponse UpdateResourceRelationshipByURNHs(UpdateResourceRelationshipByURNHsRequest request){
        return blockingStub.updateResourceRelationshipByURNHs(request);
    }

    public void UpdateResourceRelationshipByURNHs(UpdateResourceRelationshipByURNHsRequest request, StreamObserver<UpdateResourceRelationshipByURNResponse> responseObserver){
        asyncStub.updateResourceRelationshipByURNHs(request, responseObserver);
    }

    public Uni<UpdateResourceRelationshipByURNResponse> UpdateResourceRelationshipByURNHsUni(UpdateResourceRelationshipByURNHsRequest request) {
        final UnicastProcessor<UpdateResourceRelationshipByURNResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<UpdateResourceRelationshipByURNResponse>() {
            @Override
            public void onNext(UpdateResourceRelationshipByURNResponse response) {
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
