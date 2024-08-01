package org.project_kessel.client;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import org.project_kessel.api.inventory.v1beta1.*;

import java.util.logging.Logger;

public class PoliciesClient {
    private static final Logger logger = Logger.getLogger(PoliciesClient.class.getName());

    private final PoliciesServiceGrpc.PoliciesServiceStub asyncStub;
    private final PoliciesServiceGrpc.PoliciesServiceBlockingStub blockingStub;

    PoliciesClient(Channel channel){
        asyncStub = PoliciesServiceGrpc.newStub(channel);
        blockingStub = PoliciesServiceGrpc.newBlockingStub(channel);
    }

    public CreatePolicyResponse CreatePolicy(CreatePolicyRequest request) {
       return blockingStub.createPolicy(request);
    }

    public void CreatePolicy(CreatePolicyRequest request, StreamObserver<CreatePolicyResponse> responseObserver) {
         asyncStub.createPolicy(request, responseObserver);
    }

    public Uni<CreatePolicyResponse> CreatePolicyUni(CreatePolicyRequest request) {
        final UnicastProcessor<CreatePolicyResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CreatePolicyResponse>() {
            @Override
            public void onNext(CreatePolicyResponse response) {
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
        CreatePolicy(request, streamObserver);
        return uni;
    }

    public DeletePolicyResponse DeletePolicy(DeletePolicyRequest request) {
        return blockingStub.deletePolicy(request);
    }

    public void DeletePolicy(DeletePolicyRequest request, StreamObserver<DeletePolicyResponse> responseObserver) {
        asyncStub.deletePolicy(request, responseObserver);
    }

    public Uni<DeletePolicyResponse> DeletePolicyUni(DeletePolicyRequest request) {
        final UnicastProcessor<DeletePolicyResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<DeletePolicyResponse>() {
            @Override
            public void onNext(DeletePolicyResponse response) {
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
        DeletePolicy(request, streamObserver);
        return uni;
    }

    public UpdatePolicyResponse UpdatePolicy(UpdatePolicyRequest request) {
        return blockingStub.updatePolicy(request);
    }

    public void UpdatePolicy(UpdatePolicyRequest request, StreamObserver<UpdatePolicyResponse> responseObserver) {
        asyncStub.updatePolicy(request, responseObserver);
    }

    public Uni<UpdatePolicyResponse> UpdatePolicyUni(UpdatePolicyRequest request) {
        final UnicastProcessor<UpdatePolicyResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<UpdatePolicyResponse>() {
            @Override
            public void onNext(UpdatePolicyResponse response) {
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
        UpdatePolicy(request, streamObserver);
        return uni;
    }
}
