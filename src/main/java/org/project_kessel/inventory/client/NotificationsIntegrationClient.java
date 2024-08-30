package org.project_kessel.inventory.client;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import org.project_kessel.api.inventory.v1beta1.*;
import org.project_kessel.clients.*;


import java.util.logging.Logger;

public class NotificationsIntegrationClient extends KesselClient<KesselNotificationsIntegrationServiceGrpc.KesselNotificationsIntegrationServiceStub, KesselNotificationsIntegrationServiceGrpc.KesselNotificationsIntegrationServiceBlockingStub>{

    private static final Logger logger = Logger.getLogger(RhelHostClient.class.getName());

    NotificationsIntegrationClient(Channel channel) {
        super(KesselNotificationsIntegrationServiceGrpc.newStub(channel), KesselNotificationsIntegrationServiceGrpc.newBlockingStub(channel));
    }

    public CreateNotificationsIntegrationResponse CreateNotificationsIntegration(CreateNotificationsIntegrationRequest request) {
        return blockingStub.createNotificationsIntegration(request);
    }

    public void CreateNotificationsIntegration(CreateNotificationsIntegrationRequest request, StreamObserver<CreateNotificationsIntegrationResponse> responseObserver) {
        asyncStub.createNotificationsIntegration(request, responseObserver);
    }

    public Uni<CreateNotificationsIntegrationResponse> CreateNotificationsIntegrationUni(CreateNotificationsIntegrationRequest request) {
        final UnicastProcessor<CreateNotificationsIntegrationResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CreateNotificationsIntegrationResponse>() {
            @Override
            public void onNext(CreateNotificationsIntegrationResponse response) {
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
        CreateNotificationsIntegration(request, streamObserver);
        return uni;
    }


    public UpdateNotificationsIntegrationResponse CreateNotificationsIntegration(UpdateNotificationsIntegrationRequest request) {
        return blockingStub.updateNotificationsIntegration(request);
    }

    public void UpdateNotificationsIntegration(UpdateNotificationsIntegrationRequest request, StreamObserver<UpdateNotificationsIntegrationResponse> responseObserver) {
        asyncStub.updateNotificationsIntegration(request, responseObserver);
    }

    public Uni<UpdateNotificationsIntegrationResponse> UpdateNotificationsIntegrationUni(UpdateNotificationsIntegrationRequest request) {
        final UnicastProcessor<UpdateNotificationsIntegrationResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<UpdateNotificationsIntegrationResponse>() {
            @Override
            public void onNext(UpdateNotificationsIntegrationResponse response) {
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
        UpdateNotificationsIntegration(request, streamObserver);
        return uni;
    }

    public DeleteNotificationsIntegrationResponse DeleteNotificationsIntegration(DeleteNotificationsIntegrationRequest request) {
        return blockingStub.deleteNotificationsIntegration(request);
    }

    public void DeleteNotificationsIntegration(DeleteNotificationsIntegrationRequest request, StreamObserver<DeleteNotificationsIntegrationResponse> responseObserver) {
        asyncStub.deleteNotificationsIntegration(request, responseObserver);
    }

    public Uni<DeleteNotificationsIntegrationResponse> DeleteNotificationsIntegrationUni(DeleteNotificationsIntegrationRequest request) {
        final UnicastProcessor<DeleteNotificationsIntegrationResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<DeleteNotificationsIntegrationResponse>() {
            @Override
            public void onNext(DeleteNotificationsIntegrationResponse response) {
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
        DeleteNotificationsIntegration(request, streamObserver);
        return uni;
    }
}

