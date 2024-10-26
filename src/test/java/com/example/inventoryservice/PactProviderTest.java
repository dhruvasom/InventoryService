package com.example.inventoryservice;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.example.inventoryservice.service.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("InventoryCatalogue")
@PactFolder("src/main/java/pact")
public class PactProviderTest {

    @LocalServerPort
    private int port;

    @Autowired
    InventoryRepository inventoryRepository;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTest(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    public void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost",port));
    }

    @State(value = "Product is available in stock", action = StateChangeAction.SETUP)
    public void inventoryExistsForProduct() {

    }

    @State(value = "Product is available in stock", action = StateChangeAction.TEARDOWN)
    public void cleanupInventory() {

    }
}
