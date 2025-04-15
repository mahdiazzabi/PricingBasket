package com.pricing.basket;

import main.java.com.pricing.basket.PricingBasketApp;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PricingBasketAppTest {
    @Test
    public void alwaysTrue() {
        assertTrue(true);
    }


    @Test
    public void calculatesSubtotalForValidItems() {
        String input = "Apples Milk Bread\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        PricingBasketApp.main(new String[]{});

        String output = out.toString();
        assertTrue(output.contains("Subtotal: 3.10"));
    }

    @Test
    public void handlesUnknownItems() {
        String input = "UnknownItem Milk\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        PricingBasketApp.main(new String[]{});

        String output = out.toString();
        assertTrue(output.contains("Unknown item: UnknownItem"));
        assertTrue(output.contains("Subtotal: 1.30"));
    }

    @Test
    public void exitsWhenNoItemsAreEntered() {
        String input = "\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        PricingBasketApp.main(new String[]{});

        String output = out.toString();
        assertTrue(output.contains("No items entered. Exiting program."));
    }

    @Test
    public void calculatesSubtotalForEmptyBasket() {
        String input = " \n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        PricingBasketApp.main(new String[]{});

        String output = out.toString();
        assertTrue(output.contains("No items entered. Exiting program."));
    }

}