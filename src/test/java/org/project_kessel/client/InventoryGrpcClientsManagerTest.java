package org.project_kessel.client;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.project_kessel.api.inventory.v1beta1.KesselRhelHostServiceGrpc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryGrpcClientsManagerTest {

    @BeforeAll
    public static void testSetup() {
        /* Make sure all client managers shutdown/removed before tests */
        InventoryGrpcClientsManager.shutdownAll();
    }

    @AfterEach
    public void testTeardown() {
        /* Make sure all client managers shutdown/removed after each test */
        InventoryGrpcClientsManager.shutdownAll();
    }

    @Test
    public void testManagerReusePatterns() {
        var one = InventoryGrpcClientsManager.forInsecureClients("localhost:8080");
        var two = InventoryGrpcClientsManager.forInsecureClients("localhost:8080"); // same as one
        var three = InventoryGrpcClientsManager.forInsecureClients("localhost1:8080");
        var four = InventoryGrpcClientsManager.forSecureClients("localhost:8080");
        var five = InventoryGrpcClientsManager.forSecureClients("localhost1:8080");
        var six = InventoryGrpcClientsManager.forSecureClients("localhost1:8080"); // same as five

        assertNotNull(one);
        assertNotNull(two);
        assertNotNull(three);
        assertNotNull(four);
        assertNotNull(five);
        assertNotNull(six);
        assertEquals(one, two);
        assertNotEquals(two, three);
        assertEquals(five, six);
        assertNotEquals(four, five);
    }

    @Test
    public void testThreadingChaos() {
        /* Basic testing to ensure that we don't get ConcurrentModificationExceptions, or any other exceptions, when
         * creating and destroying managers on different threads. */

        try {
            Hashtable<String,InventoryGrpcClientsManager> managers = new Hashtable<>();

            int numberOfThreads = 100;
            ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
            CountDownLatch latch1 = new CountDownLatch(numberOfThreads / 3);
            CountDownLatch latch2 = new CountDownLatch(numberOfThreads * 2 / 3 - numberOfThreads / 3);
            CountDownLatch latch3 = new CountDownLatch(numberOfThreads - numberOfThreads * 2 / 3);

            /* A: Use 1/3 of threads to request/create managers at the same time. */
            for (int i = 0; i < numberOfThreads / 3; i++) {
                final int j = i;
                service.submit(() -> {
                    InventoryGrpcClientsManager manager;
                    if(j % 2 == 0) {
                        manager = InventoryGrpcClientsManager.forInsecureClients("localhost" + j);
                    } else {
                        manager = InventoryGrpcClientsManager.forSecureClients("localhost" + j);
                    }
                    managers.put("localhost" + j, manager);

                    latch1.countDown();
                });
            }

            latch1.await();
            /* B and C, below, trigger at the same time once A is done. */

            /* B: Use 1/3 of threads to shut down the above created managers. */
            for (int i = numberOfThreads / 3; i < numberOfThreads * 2 / 3; i++) {
                final int j = i - numberOfThreads / 3;
                service.submit(() -> {
                    InventoryGrpcClientsManager.shutdownManager(managers.get("localhost" + j));
                    latch2.countDown();
                });
            }

            /* C: Use 1/3 of the threads to recreate/retrieve the same managers at the same time as B. */
            for (int i = numberOfThreads * 2 / 3; i < numberOfThreads; i++) {
                final int j = i - numberOfThreads * 2 / 3;
                service.submit(() -> {
                    InventoryGrpcClientsManager manager;
                    if(j % 2 == 0) {
                        manager = InventoryGrpcClientsManager.forInsecureClients("localhost" + j);
                    } else {
                        manager = InventoryGrpcClientsManager.forSecureClients("localhost" + j);
                    }
                    managers.put("localhost" + j, manager);

                    latch3.countDown();
                });
            }
            latch2.await();
            latch3.await();
        } catch(Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void testManagerReuseInternal() throws Exception {
        InventoryGrpcClientsManager.forInsecureClients("localhost:8080");
        InventoryGrpcClientsManager.forInsecureClients("localhost:8080"); // same as one
        InventoryGrpcClientsManager.forInsecureClients("localhost1:8080");
        InventoryGrpcClientsManager.forSecureClients("localhost:8080");
        InventoryGrpcClientsManager.forSecureClients("localhost1:8080");
        InventoryGrpcClientsManager.forSecureClients("localhost1:8080"); // same as five

        var insecureField = InventoryGrpcClientsManager.class.getDeclaredField("insecureManagers");
        insecureField.setAccessible(true);
        var secureField = InventoryGrpcClientsManager.class.getDeclaredField("secureManagers");
        secureField.setAccessible(true);
        var insecureManagers = (HashMap<?,?>)insecureField.get(null);
        var secureManagers = (HashMap<?,?>)secureField.get(null);

        assertEquals(2, insecureManagers.size());
        assertEquals(2, secureManagers.size());
    }
}
