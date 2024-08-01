package org.project_kessel.client;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import org.project_kessel.api.inventory.v1beta1.CreateRHELHostRequest;
import org.project_kessel.api.inventory.v1beta1.CreateRHELHostResponse;
import org.project_kessel.api.inventory.v1beta1.HostsServiceGrpc;

import java.util.logging.Logger;

public class HostsClient {
    private static final Logger logger = Logger.getLogger(HostsClient.class.getName());

    private final HostsServiceGrpc.HostsServiceStub asyncStub;
    private final HostsServiceGrpc.HostsServiceBlockingStub blockingStub;

    HostsClient(Channel channel){
        asyncStub = HostsServiceGrpc.newStub(channel);
        blockingStub = HostsServiceGrpc.newBlockingStub(channel);
    }

    public void CreateRHELHost(CreateRHELHostRequest request, StreamObserver<CreateRHELHostResponse> responseStreamObserver) {
        asyncStub.createRHELHost(request, responseStreamObserver);
    }

    public Uni<CreateRHELHostResponse> CreateRHELHostUni(CreateRHELHostRequest request) {
        final UnicastProcessor<CreateRHELHostResponse> responseProcessor = UnicastProcessor.create();
        var streamObserver = new StreamObserver<CreateRHELHostResponse>() {
            @Override
            public void onNext(CreateRHELHostResponse response) {
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
        CreateRHELHost(request, streamObserver);
        return uni;
    }

    public CreateRHELHostResponse CreateRHELHost(CreateRHELHostRequest request) {
        return blockingStub.createRHELHost(request);
    }
}
