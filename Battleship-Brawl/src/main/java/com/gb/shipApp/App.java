package com.gb.shipApp;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.FileWriter;

public class App
{
    public static void main(String[] args) throws IOException {
        // Test tester = new Test("127.0.0.1");
        // Test p = new Test("104.131.148.45");
        Test tester = new Test(
                               // "73.154.178.79"
                               "127.0.0.1"
                               , 8080);
        tester.setShips("THIS IS A TEST SETUP");
        System.out.println();
        System.out.println();
        tester.gameLoop();
    }

}

