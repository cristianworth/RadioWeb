/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radioweb.client;
import javax.sound.sampled.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
/**
 *
 * @author Marcelo
 */
public class PlayerController extends Thread implements Runnable {

        private Player mp3Player;
        private Thread playerThread;
private String fileLocation;
    private boolean loop;
    private Player prehravac;
    
    
public void play(String song) {
         
         mp3Player = null;
        
        try {
            BufferedInputStream in = new BufferedInputStream(new URL(song).openStream());


            mp3Player = new Player(in);
            
             playerThread = new Thread(this);
             playerThread.start();
             mp3Player.play();
         
          
        
          
          
          
        } catch (MalformedURLException ex) {
        } catch (IOException e) {
        } catch (JavaLayerException e) {
        } catch (NullPointerException ex) {
        }

}

    public PlayerController(String fileLocation, boolean loop) {
        this.fileLocation = fileLocation;
        this.loop = loop;
    }
    

    public void run() {
         try {
            do {
                BufferedInputStream buff = new BufferedInputStream(new URL(fileLocation).openStream());
                prehravac = new Player(buff);
                prehravac.play();
            } while (loop);
        } catch (Exception ioe) {
            
        }
    }
     public void close(){
        loop = false;
        prehravac.close();
        this.interrupt();
    }
}