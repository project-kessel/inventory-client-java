package org.project_kessel.inventory.client;

import io.smallrye.config.SmallRyeConfigBuilder;
import io.smallrye.config.common.MapBackedConfigSource;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.fail;

class ConfigTest {

    @Test
    void canLoadBasicConfig() {
        /* Should always be able to build a Config from a ConfigSource with just a target url (i.e. minimal config for
         * now). Also tests whether the mapping annotations in Config are valid beyond static type checking. */
        try {
            new SmallRyeConfigBuilder()
                    .withSources(new MapBackedConfigSource("test", new HashMap<>(), Integer.MAX_VALUE) {
                                     @Override
                                     public String getValue(String propertyName) {
                                         if ("inventory-api.target-url".equals(propertyName)) {
                                             return "http://localhost:8080";
                                         }
                                         return null;
                                     }
                                 }
                    )
                    .withMapping(Config.class)
                    .build();
        }
        catch (Exception e) {
            fail("Generating a config objective with minimal config should not fail.");
        }
    }

}
