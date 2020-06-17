/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.*;
import java.net.*;

/**
 *
 * @author Marcelo
 */
public class ServerRecieveThread {
    ServerSocket serverSocket;
    public void init() {
        try {
             serverSocket = new ServerSocket(999);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Thread thread = new Thread() {
            public void run() {
                ServerLoop();
            }
        };
        thread.start();
    }

    public void ServerLoop() {
        while (true) {
            System.out.println("Server loop");
            try {
                Socket clientSocket = serverSocket.accept();

                InputStreamReader inputstreamreader = new InputStreamReader(clientSocket.getInputStream());

                BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

                PrintWriter printwriter = new PrintWriter(clientSocket.getOutputStream(), true);

                String line = "";
                boolean done = false;
                while (((line = bufferedreader.readLine()) != null) && (!done)) {
                    System.out.println(line);

                    if (line.compareToIgnoreCase("Exit") == 0) {
                        done = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
