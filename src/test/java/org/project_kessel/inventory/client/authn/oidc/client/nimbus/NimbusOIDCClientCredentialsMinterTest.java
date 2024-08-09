package org.project_kessel.inventory.client.authn.oidc.client.nimbus;

import org.junit.jupiter.api.Test;
import org.project_kessel.inventory.client.InventoryGrpcClientsManagerTest;
import org.project_kessel.inventory.client.authn.oidc.client.OIDCClientCredentialsMinter;
import org.project_kessel.inventory.client.fake.FakeIdp;


import static org.junit.jupiter.api.Assertions.*;
import static org.project_kessel.inventory.client.InventoryGrpcClientsManagerTest.dummyAuthConfigWithGoodOIDCClientCredentials;

public class NimbusOIDCClientCredentialsMinterTest {

    @Test
    void shouldReturnBearerHeaderWhenIdPAuthenticates() {
        var minter = new NimbusOIDCClientCredentialsMinter();
        var config = InventoryGrpcClientsManagerTest.dummyAuthConfigWithGoodOIDCClientCredentials().clientCredentialsConfig();
        OIDCClientCredentialsMinter.BearerHeader bearerHeader = null;
        try {
            FakeIdp fakeIdp = new FakeIdp(8090);
            fakeIdp.start();
            bearerHeader = minter.authenticateAndRetrieveAuthorizationHeader(config.get());
            fakeIdp.stop();
        } catch (OIDCClientCredentialsMinter.OIDCClientCredentialsMinterException e) {
            fail("Should not throw exception if authn is successful.");
        }

        assertNotNull(bearerHeader);
        assertEquals("Bearer blah", bearerHeader.getAuthorizationHeader());
    }

    @Test
    void shouldThrowExceptionWhenIdPAuthenticationFails() {
        var minter = new NimbusOIDCClientCredentialsMinter();
        var config = InventoryGrpcClientsManagerTest.dummyAuthConfigWithGoodOIDCClientCredentials().clientCredentialsConfig();
        FakeIdp fakeIdp = new FakeIdp(8090, false);
        try {
            fakeIdp.start();
            minter.authenticateAndRetrieveAuthorizationHeader(config.get());
            fail("Should throw exception if authn is not successful.");
        } catch (OIDCClientCredentialsMinter.OIDCClientCredentialsMinterException e) {
            // success
        } catch(Exception e) {
            fail("OIDCClientCredentialsMinterException expected.");
        } finally {
            fakeIdp.stop();
        }
    }
}
