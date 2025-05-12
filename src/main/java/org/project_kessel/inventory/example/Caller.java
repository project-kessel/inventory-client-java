package org.project_kessel.inventory.example;

import java.util.concurrent.CountDownLatch;

import org.project_kessel.api.inventory.v1.GetLivezRequest;
import org.project_kessel.api.inventory.v1beta2.Allowed;
import org.project_kessel.api.inventory.v1beta2.CheckForUpdateRequest;
import org.project_kessel.api.inventory.v1beta2.CheckForUpdateResponse;
import org.project_kessel.api.inventory.v1beta2.CheckRequest;
import org.project_kessel.api.inventory.v1beta2.CheckResponse;
import org.project_kessel.api.inventory.v1beta2.ReporterReference;
import org.project_kessel.api.inventory.v1beta2.ResourceReference;
import org.project_kessel.api.inventory.v1beta2.SubjectReference;
import org.project_kessel.inventory.client.InventoryGrpcClientsManager;
import org.project_kessel.inventory.client.KesselInventoryClient;

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
}
