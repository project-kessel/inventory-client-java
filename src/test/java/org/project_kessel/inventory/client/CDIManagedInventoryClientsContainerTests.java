package org.project_kessel.inventory.client;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import jakarta.inject.Inject;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.project_kessel.api.inventory.v1beta1.resources.*;


import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Use Weld as a test container to check CDI functionality.
 */
@EnableWeld
class CDIManagedInventoryClientsContainerTests {
    @WeldSetup
    public WeldInitiator weld = WeldInitiator.from(new Weld().setBeanDiscoveryMode(BeanDiscoveryMode.ALL).addBeanClass(TestConfig.class)).build();

    private static final int testServerPort = 7000;

    @Inject
    RhelHostClient rhelHostClient;

    private static Server grpcServer;

    /*
     Start a grpcServer with the following services added and some custom responses that we can check for in the tests.
     */
    @BeforeAll
    static void setup() throws IOException {
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(testServerPort);

        serverBuilder.addService(new KesselRhelHostServiceGrpc.KesselRhelHostServiceImplBase() {
            @Override
           public void createRhelHost(CreateRhelHostRequest request, StreamObserver<CreateRhelHostResponse> responseObserver) {
               responseObserver.onNext(CreateRhelHostResponse.newBuilder().build());
               responseObserver.onCompleted();
           }

       });

        grpcServer = serverBuilder.build();
        grpcServer.start();
    }

    @AfterAll
    static void tearDown() {
        if (grpcServer != null) {
            grpcServer.shutdownNow();
        }
    }

    @Test
    void basicCDIWiringTest() {
        /* Make some calls to dummy services in test grpc server to test injected clients */
        CreateRhelHostRequest request = CreateRhelHostRequest.newBuilder()
                .setRhelHost(RhelHost.newBuilder()
                        .setMetadata(Metadata.newBuilder()
                                .setResourceType("rhel-host")
                                .setWorkspace("") // Set workspace value as needed
                                .build())
                        .setReporterData(ReporterData.newBuilder()
                                .setReporterType(ReporterData.ReporterType.ACM)
                                .setReporterInstanceId("user@example.com")
                                .setReporterVersion("0.1")
                                .setLocalResourceId("1")
                                .setApiHref("www.example.com")
                                .setConsoleHref("www.example.com")
                                .build())
                        .build())
                .build();

       var rhelHostResponse = rhelHostClient.CreateRhelHost(request);
       CreateK8sClusterRequest request1= CreateK8sClusterRequest.newBuilder().setK8SCluster(
               K8sCluster.newBuilder().setReporterData(ReporterData.newBuilder()
                               .setReporterType(ReporterData.ReporterType.ACM)
                               .setReporterInstanceId("user@example.com")
                               .setReporterVersion("0.1")
                               .setLocalResourceId("1")
                               .setApiHref("www.example.com")
                               .setConsoleHref("www.example.com")
                               .build())
                       .setMetadata(Metadata.newBuilder()
                       .setResourceType("rhel-host")
                       .setWorkspace("") // Set workspace value as needed
                       .build()).build()
       ).build();

        assertNotNull(rhelHostResponse);
    }

    /*
     Implementation of Config to inject manually with hardcoded settings.
     */
    static class TestConfig implements Config {
        @Override
        public boolean isSecureClients() {
            return false;
        }

        @Override
        public String targetUrl() {
            return "0.0.0.0:" + String.valueOf(testServerPort);
        }

        @Override
        public Optional<AuthenticationConfig> authenticationConfig() {
            return Optional.empty();
        }
    }
}
