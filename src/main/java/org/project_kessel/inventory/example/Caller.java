package org.project_kessel.inventory.example;

import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.project_kessel.api.inventory.v1.GetLivezRequest;
import org.project_kessel.api.inventory.v1beta2.Allowed;
import org.project_kessel.api.inventory.v1beta2.CheckForUpdateRequest;
import org.project_kessel.api.inventory.v1beta2.CheckForUpdateResponse;
import org.project_kessel.api.inventory.v1beta2.CheckRequest;
import org.project_kessel.api.inventory.v1beta2.CheckResponse;
import org.project_kessel.api.inventory.v1beta2.DeleteResourceRequest;
import org.project_kessel.api.inventory.v1beta2.DeleteResourceResponse;
import org.project_kessel.api.inventory.v1beta2.ReportResourceRequest;
import org.project_kessel.api.inventory.v1beta2.ReportResourceResponse;
import org.project_kessel.api.inventory.v1beta2.ReporterReference;
import org.project_kessel.api.inventory.v1beta2.RepresentationMetadata;
import org.project_kessel.api.inventory.v1beta2.RepresentationType;
import org.project_kessel.api.inventory.v1beta2.ResourceReference;
import org.project_kessel.api.inventory.v1beta2.ResourceRepresentations;
import org.project_kessel.api.inventory.v1beta2.StreamedListObjectsRequest;
import org.project_kessel.api.inventory.v1beta2.StreamedListObjectsResponse;
import org.project_kessel.api.inventory.v1beta2.SubjectReference;
import org.project_kessel.api.inventory.v1beta2.WriteVisibility;
import org.project_kessel.inventory.client.InventoryGrpcClientsManager;
import org.project_kessel.inventory.client.KesselInventoryClient;

import com.google.protobuf.Struct;
import com.google.protobuf.Value;

import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;

public class Caller {

    static String url = "localhost:9081";
    static InventoryGrpcClientsManager clientsManager = InventoryGrpcClientsManager.forInsecureClients(url);
    static KesselInventoryClient inventoryClient = clientsManager.getKesselInventoryClient();

    public static void main(String[] argv) {
        var healthClient = clientsManager.getHealthClient();
        var response = healthClient.getLivez(GetLivezRequest.newBuilder().build());
        System.out.println(response);

        checkExample();
        checkForUpdateExample();
        reportResourceExample();
        streamedListObjectsExample();
        deleteResourceExample();
    }

    public static void checkExample() {
        var checkRequest = CheckRequest.newBuilder()
                .setSubject(SubjectReference.newBuilder().setResource(ResourceReference.newBuilder()
                        .setResourceId("bob")
                        .setResourceType("principal")
                        .setReporter(ReporterReference.newBuilder()
                                .setType("rbac")
                                .build())
                        .build())
                        .build())
                .setRelation("member")
                .setObject(ResourceReference.newBuilder()
                        .setResourceId("bob_club")
                        .setResourceType("group")
                        .setReporter(ReporterReference.newBuilder()
                                .setType("rbac")
                                .build())
                        .build())
                .build();

        var checkResponse = inventoryClient.Check(checkRequest);
        var permitted = checkResponse.getAllowed() == Allowed.ALLOWED_TRUE;

        if (permitted) {
            System.out.println("Blocking: Permitted");
        } else {
            System.out.println("Blocking: Denied");
        }

        /*
         * Non-blocking
         */

        final CountDownLatch conditionLatch = new CountDownLatch(1);
        var streamObserver = new StreamObserver<CheckResponse>() {
            @Override
            public void onNext(CheckResponse response) {
                /*
                 * Because we don't return a stream, but a response object with all the
                 * relationships inside,
                 * we get no benefit from an async/non-blocking call right now. It all returns
                 * at once.
                 */
                var permitted = response.getAllowed() == Allowed.ALLOWED_TRUE;

                if (permitted) {
                    System.out.println("Non-blocking: Permitted");
                } else {
                    System.out.println("Non-blocking: Denied");
                }
            }

            @Override
            public void onError(Throwable t) {
                // TODO:
            }

            @Override
            public void onCompleted() {
                conditionLatch.countDown();
            }
        };

        inventoryClient.Check(checkRequest, streamObserver);

        /*
         * Use a passed-in countdownlatch to wait for the result async on the main
         * thread
         */
        try {
            conditionLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /*
         * Non-blocking reactive style
         */

        Uni<CheckResponse> uni = inventoryClient.CheckUni(checkRequest);

        /*
         * Pattern where we may want collect all the responses, but still operate on
         * each as it comes in.
         */
        CheckResponse cr = uni.onItem()
                .invoke(() -> {
                    if (permitted) {
                        System.out.println("Reactive non-blocking: Permitted");
                    } else {
                        System.out.println("Reactive non-blocking: Denied");
                    }
                })
                .await().indefinitely();
    }

    public static void checkForUpdateExample() {
        var checkRequest = CheckForUpdateRequest.newBuilder()
                .setSubject(SubjectReference.newBuilder().setResource(ResourceReference.newBuilder()
                        .setResourceId("bob")
                        .setResourceType("principal")
                        .setReporter(ReporterReference.newBuilder()
                                .setType("rbac")
                                .build())
                        .build())
                        .build())
                .setRelation("member")
                .setObject(ResourceReference.newBuilder()
                        .setResourceId("bob_club")
                        .setResourceType("group")
                        .setReporter(ReporterReference.newBuilder()
                                .setType("rbac")
                                .build())
                        .build())
                .build();

        var checkResponse = inventoryClient.CheckForUpdate(checkRequest);
        var permitted = checkResponse.getAllowed() == Allowed.ALLOWED_TRUE;

        if (permitted) {
            System.out.println("Blocking: Permitted");
        } else {
            System.out.println("Blocking: Denied");
        }

        /*
         * Non-blocking
         */

        final CountDownLatch conditionLatch = new CountDownLatch(1);
        var streamObserver = new StreamObserver<CheckForUpdateResponse>() {
            @Override
            public void onNext(CheckForUpdateResponse response) {
                /*
                 * Because we don't return a stream, but a response object with all the
                 * relationships inside,
                 * we get no benefit from an async/non-blocking call right now. It all returns
                 * at once.
                 */
                var permitted = response.getAllowed() == Allowed.ALLOWED_TRUE;

                if (permitted) {
                    System.out.println("Non-blocking: Permitted");
                } else {
                    System.out.println("Non-blocking: Denied");
                }
            }

            @Override
            public void onError(Throwable t) {
                // TODO:
            }

            @Override
            public void onCompleted() {
                conditionLatch.countDown();
            }
        };

        inventoryClient.CheckForUpdate(checkRequest, streamObserver);

        /*
         * Use a passed-in countdownlatch to wait for the result async on the main
         * thread
         */
        try {
            conditionLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        /*
         * Non-blocking reactive style
         */

        Uni<CheckForUpdateResponse> uni = inventoryClient.CheckForUpdateUni(checkRequest);

        /*
         * Pattern where we may want collect all the responses, but still operate on
         * each as it comes in.
         */
        CheckForUpdateResponse cr = uni.onItem()
                .invoke(() -> {
                    if (permitted) {
                        System.out.println("Reactive non-blocking: Permitted");
                    } else {
                        System.out.println("Reactive non-blocking: Denied");
                    }
                })
                .await().indefinitely();
    }

    public static void reportResourceExample() {
        var reportResourceRequest = ReportResourceRequest.newBuilder()
                .setWriteVisibility(WriteVisibility.IMMEDIATE)
                .setType("host")
                .setReporterType("hbi")
                .setReporterInstanceId("3088be62-1c60-4884-b133-9200542d0b3f")
                .setRepresentations(ResourceRepresentations.newBuilder()
                        .setReporter(Struct.newBuilder()
                                .putFields("satellite_id",
                                        Value.newBuilder().setStringValue("2c4196f1-0371-4f4c-8913-e113cfaa6e67")
                                                .build())
                                .putFields("sub_manager_id",
                                        Value.newBuilder().setStringValue("af94f92b-0b65-4cac-b449-6b77e665a08f")
                                                .build())
                                .putFields("insights_inventory_id",
                                        Value.newBuilder().setStringValue("05707922-7b0a-4fe6-982d-6adbc7695b8f")
                                                .build())
                                .putFields("ansible_host", Value.newBuilder().setStringValue("host-1").build()))
                        .setMetadata(RepresentationMetadata.newBuilder()
                                .setLocalResourceId("dd1b73b9-3e33-4264-968c-e3ce55b9afec")
                                .setApiHref("https://apiHref.com/")
                                .setConsoleHref("https://www.console.com/")
                                .setReporterVersion("2.7.16"))
                        .setCommon(Struct.newBuilder()
                                .putFields("workspace_id",
                                        Value.newBuilder().setStringValue("a64d17d0-aec3-410a-acd0-e0b85b22c076")
                                                .build())
                                .build())
                        .build())
                .build();

        // Can only create the same object once.
        var reportResourceResponse = inventoryClient.ReportResource(reportResourceRequest);
        var permitted = reportResourceResponse == ReportResourceResponse.getDefaultInstance();

        if (permitted) {
            System.out.println("Reported.");
        }
    }

    public static void deleteResourceExample() {
        var deleteResourceRequest = DeleteResourceRequest.newBuilder()
                .setReference(ResourceReference.newBuilder()
                        .setResourceId("dd1b73b9-3e33-4264-968c-e3ce55b9afec")
                        .setResourceType("host")
                        .setReporter(ReporterReference.newBuilder()
                                .setType("hbi"))
                        .build())
                .build();

        // Can only delete the same object once.
        var deleteResourceResponse = inventoryClient.DeleteResource(deleteResourceRequest);
        var permitted = deleteResourceResponse == DeleteResourceResponse.getDefaultInstance();

        if (permitted) {
            System.out.println("Deleted.");
        }
    }

    public static void streamedListObjectsExample() {
        var streamedListObjectsRequest = StreamedListObjectsRequest.newBuilder()
                .setRelation("workspace")
                .setSubject(SubjectReference.newBuilder()
                        .setResource(ResourceReference.newBuilder()
                                .setResourceId("a64d17d0-aec3-410a-acd0-e0b85b22c076")
                                .setResourceType("workspace")
                                .setReporter(ReporterReference.newBuilder()
                                        .setType("rbac")
                                        .build())
                                .build())
                        .build())
                .setObjectType(RepresentationType.newBuilder()
                        .setResourceType("host")
                        .setReporterType("hbi")
                        .build())
                .build();

        var responseIterator = inventoryClient.streamedListObjects(streamedListObjectsRequest);
        Iterable<StreamedListObjectsResponse> iterable = () -> responseIterator;

        var objects = StreamSupport.stream(iterable.spliterator(), false)
            .map(StreamedListObjectsResponse::getObject)
            .collect(Collectors.toList());
        System.out.println("Blocking StreamedListObjects: " + objects);
    }
}
