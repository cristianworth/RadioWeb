/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radioweb.client;

import javax.sound.sampled.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Marcelo
 */
public class PlayerController extends Thread implements Runnable {

    private Player mp3Player;
    private boolean loop;

    PlayerController() {
        
    }

    public void run() {
        BufferedInputStream in = null;
        try (Socket socket = new Socket("127.0.0.1", 6666)) {
            if (socket.isConnected()) {
                in = new BufferedInputStream(socket.getInputStream());
                mp3Player = new Player(in);
                mp3Player.play();

            }
        } catch (IOException ex) {
        } catch (JavaLayerException ex) {
        }


    }

    public void close() {
        loop = false;
        mp3Player.close();
        this.interrupt();
    }
}
