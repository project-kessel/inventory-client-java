package org.project_kessel.inventory.client;

import java.util.logging.Logger;

import org.project_kessel.api.inventory.v1beta2.CheckForUpdateRequest;
import org.project_kessel.api.inventory.v1beta2.CheckForUpdateResponse;
import org.project_kessel.api.inventory.v1beta2.CheckRequest;
import org.project_kessel.api.inventory.v1beta2.CheckResponse;
import org.project_kessel.api.inventory.v1beta2.DeleteResourceRequest;
import org.project_kessel.api.inventory.v1beta2.DeleteResourceResponse;
import org.project_kessel.api.inventory.v1beta2.KesselInventoryServiceGrpc;
import org.project_kessel.api.inventory.v1beta2.ReportResourceRequest;
import org.project_kessel.api.inventory.v1beta2.ReportResourceResponse;
import org.project_kessel.api.inventory.v1beta2.StreamedListObjectsRequest;
import org.project_kessel.api.inventory.v1beta2.StreamedListObjectsResponse;
import org.project_kessel.clients.KesselClient;

import java.util.function.Consumer;
import java.util.Iterator;
import io.grpc.Channel;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;


public class KesselInventoryClient extends KesselClient<KesselInventoryServiceGrpc.KesselInventoryServiceStub, KesselInventoryServiceGrpc.KesselInventoryServiceBlockingStub> {
    private static final Logger logger = Logger.getLogger(KesselInventoryClient.class.getName());

    KesselInventoryClient(Channel channel) {
        super(KesselInventoryServiceGrpc.newStub(channel), KesselInventoryServiceGrpc.newBlockingStub(channel));
    }

    public CheckResponse Check(CheckRequest request) {
        return blockingStub.check(request);
    } 

    public void Check(CheckRequest request, StreamObserver<CheckResponse> responseObserver) {
        asyncStub.check(request, responseObserver);
    }

    
    public CheckForUpdateResponse CheckForUpdate(CheckForUpdateRequest request) {
        return blockingStub.checkForUpdate(request);
    }
    
    public void CheckForUpdate(CheckForUpdateRequest request, StreamObserver<CheckForUpdateResponse> responObserver) {
        asyncStub.checkForUpdate(request, responObserver);
    }
    
    
    public ReportResourceResponse ReportResource(ReportResourceRequest request) {
        return blockingStub.reportResource(request);
    }
    
    public void ReportResource(ReportResourceRequest request, StreamObserver<ReportResourceResponse> responObserver) {
        asyncStub.reportResource(request, responObserver);
    }
    
    public DeleteResourceResponse DeleteResource(DeleteResourceRequest request) {
        return blockingStub.deleteResource(request);
    }
    
    public void DeleteResource(DeleteResourceRequest request, StreamObserver<DeleteResourceResponse> responObserver) {
        asyncStub.deleteResource(request, responObserver);
    }
    
    public void streamedListObjects(StreamedListObjectsRequest request,
    StreamObserver<StreamedListObjectsResponse> responseObserver) {
        asyncStub.streamedListObjects(request, responseObserver);
    }
    
    public Iterator<StreamedListObjectsResponse> streamedListObjects(StreamedListObjectsRequest request) {
        return blockingStub.streamedListObjects(request);
    }
    
    private <T> Uni<T> toUni(Consumer<StreamObserver<T>> grpcCall) {
        final UnicastProcessor<T> processor = UnicastProcessor.create();
        StreamObserver<T> observer = new StreamObserver<T>() {
            @Override
            public void onNext(T value) {
                processor.onNext(value);
            }
            @Override
            public void onError(Throwable t) {
                processor.onError(t);
            }
            @Override
            public void onCompleted() {
                processor.onComplete();
            }
        };
        grpcCall.accept(observer);
        return Uni.createFrom().publisher(processor);
    }

    public Multi<StreamedListObjectsResponse> streamedListObjectsMulti(StreamedListObjectsRequest request) {
        final UnicastProcessor<StreamedListObjectsResponse> responseProcessor = UnicastProcessor.create();

        var streamObserver = new StreamObserver<StreamedListObjectsResponse>() {
            @Override
            public void onNext(StreamedListObjectsResponse response) {
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

        var multi = Multi.createFrom().publisher(responseProcessor);
        streamedListObjects(request, streamObserver);

        return multi;
    }
}
