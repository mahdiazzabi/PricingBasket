package com.pricing.basket.config;
import com.pricing.basket.adapter.config.ProductConfigLoader;
import com.pricing.basket.domain.model.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductConfigLoaderTest {

    private ProductConfigLoader loader;

    @BeforeEach
    public void setUp() {
        loader = new ProductConfigLoader();
    }

    @Test
    public void loadsProductsFromMockedJsonFile() throws IOException {
        // Créer un fichier temporaire avec le contenu JSON
        File tempFile = File.createTempFile("products", ".json");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("""
                        {
                            "products": [
                                {"name": "Product1", "price": 1.00},
                                {"name": "Product2", "price": 2.50}
                            ]
                        }
                    """);
        }

        // Rediriger le chemin vers le fichier temporaire
        ProductConfigLoader.RESOURCES_PRODUCTS_JSON = tempFile.getAbsolutePath();

        // Charger les produits
        List<Product> products = loader.load();

        // Vérifier les résultats
        assertEquals(2, products.size());
        assertEquals("Product1", products.get(0).getName());

    }
    
    @Test
    public void throwsIOExceptionForMissingFile() {
        ProductConfigLoader.RESOURCES_PRODUCTS_JSON = "src/main/resources/missing.json";
        assertThrows(IOException.class, () -> loader.load());
    }

    @AfterAll
    public static void cleanUp() {
        ProductConfigLoader.RESOURCES_PRODUCTS_JSON = "src/main/resources/products.json";
    }

}
