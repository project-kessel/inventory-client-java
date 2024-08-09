package org.project_kessel.inventory.client;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

class CDIManagedClientsTest {
    @Test
    void testInsecureNoAuthnMakesCorrectManagerCall() {
        Config config = makeDummyConfig(false, makeDummyAuthenticationConfig(false));
        CDIManagedClients cdiManagedClients = new CDIManagedClients();

        try (MockedStatic<InventoryGrpcClientsManager> dummyManager = Mockito.mockStatic(InventoryGrpcClientsManager.class)) {
            cdiManagedClients.getManager(config);
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forInsecureClients(anyString()),
                    times(1)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forInsecureClients(anyString(), any()),
                    times(0)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forSecureClients(anyString()),
                    times(0)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forSecureClients(anyString(), any()),
                    times(0)
            );
        }
    }

    @Test
    void testInsecureWithAuthnMakesCorrectManagerCall() {
        Config config = makeDummyConfig(false, makeDummyAuthenticationConfig(true));
        CDIManagedClients cdiManagedClients = new CDIManagedClients();

        try (MockedStatic<InventoryGrpcClientsManager> dummyManager = Mockito.mockStatic(InventoryGrpcClientsManager.class)) {
            cdiManagedClients.getManager(config);
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forInsecureClients(anyString()),
                    times(0)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forInsecureClients(anyString(), any()),
                    times(1)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forSecureClients(anyString()),
                    times(0)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forSecureClients(anyString(), any()),
                    times(0)
            );
        }
    }

    @Test
    void testSecureNoAuthnMakesCorrectManagerCall() {
        Config config = makeDummyConfig(true, makeDummyAuthenticationConfig(false));
        CDIManagedClients cdiManagedClients = new CDIManagedClients();

        try (MockedStatic<InventoryGrpcClientsManager> dummyManager = Mockito.mockStatic(InventoryGrpcClientsManager.class)) {
            cdiManagedClients.getManager(config);
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forInsecureClients(anyString()),
                    times(0)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forInsecureClients(anyString(), any()),
                    times(0)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forSecureClients(anyString()),
                    times(1)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forSecureClients(anyString(), any()),
                    times(0)
            );
        }
    }

    @Test
    void testSecureWithAuthnMakesCorrectManagerCall() {
        Config config = makeDummyConfig(true, makeDummyAuthenticationConfig(true));
        CDIManagedClients cdiManagedClients = new CDIManagedClients();

        try (MockedStatic<InventoryGrpcClientsManager> dummyManager = Mockito.mockStatic(InventoryGrpcClientsManager.class)) {
            cdiManagedClients.getManager(config);
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forInsecureClients(anyString()),
                    times(0)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forInsecureClients(anyString(), any()),
                    times(0)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forSecureClients(anyString()),
                    times(0)
            );
            dummyManager.verify(
                    () -> InventoryGrpcClientsManager.forSecureClients(anyString(), any()),
                    times(1)
            );
        }
    }

    static Config makeDummyConfig(boolean secure, Config.AuthenticationConfig authnConfig) {
        return new Config() {
            @Override
            public boolean isSecureClients() {
                return secure;
            }

            @Override
            public String targetUrl() {
                return "localhost";
            }

            @Override
            public Optional<AuthenticationConfig> authenticationConfig() {
                return Optional.of(authnConfig);
            }
        };
    }

    static Config.AuthenticationConfig makeDummyAuthenticationConfig(boolean authnEnabled) {
        return new Config.AuthenticationConfig() {
            @Override
            public Config.AuthMode mode() {
                if(!authnEnabled) {
                    return Config.AuthMode.DISABLED;
                }
                // pick some arbitrary non disabled mode
                return Config.AuthMode.OIDC_CLIENT_CREDENTIALS;
            }

            @Override
            public Optional<Config.OIDCClientCredentialsConfig> clientCredentialsConfig() {
                if(!authnEnabled) {
                    return Optional.empty();
                }

                // provide dummy config matching mode, above.
                return Optional.of(new Config.OIDCClientCredentialsConfig() {
                    @Override
                    public String issuer() {
                        return "";
                    }

                    @Override
                    public String clientId() {
                        return "";
                    }

                    @Override
                    public String clientSecret() {
                        return "";
                    }

                    @Override
                    public Optional<String[]> scope() {
                        return Optional.empty();
                    }

                    @Override
                    public Optional<String> oidcClientCredentialsMinterImplementation() {
                        return Optional.empty();
                    }
                });
            }
        };
    }
}
