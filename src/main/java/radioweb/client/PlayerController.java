/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radioweb.client;

import Server.MusicProtocol;
import javax.sound.sampled.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.util.LinkedList;
import javax.sound.sampled.DataLine.Info;
import javax.swing.JLabel;

/**
 *
 * @author Marcelo
 */
   /**
    * Classe PlayerController
    * responsavel por administrar a reprodução do client
   */

public class PlayerController extends Thread implements Runnable {

    private Player mp3Player;
    private boolean loop;
    private volatile boolean value;
    public volatile ArrayList<MusicProtocol> chunksMP;
    public volatile LinkedList<ArrayList> queue = new LinkedList<ArrayList>();
    Thread Play, timeout;
    public volatile AudioFormat outFormat;
    public volatile Info info;
    public volatile JLabel jLabelMusica;
    public volatile JLabel jLabelPlaylist;
    public volatile String ip;
    
    PlayerController() {
        
    }

    public PlayerController(JLabel jLabelMusica, JLabel jLabelPlaylist,String ip) {
        this.jLabelMusica = jLabelMusica;
        this.jLabelPlaylist = jLabelPlaylist;
        this.ip = ip;

    }
    

   /**
   * Metodo Run()
   * inicia conexçao com o servidor, fica idle até receber um retorno;
   * quando recebe o ultimo arquivo da musica, inicicia a reprodução
   */
    public void run() {
        try {
            queue.clear();
            int port;
            InetAddress address;
            DatagramPacket packet;
            byte[] sendBuf = new byte[30000];
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = new byte[30000];
            address = InetAddress.getByName(ip);
            InputStream myInputStream = null;
            packet = new DatagramPacket(buf, buf.length,
                    address, 4445);
            socket.send(packet);
            System.out.println("connected");
            play();
            int i = 0;
            int total = 0;
            chunksMP = new ArrayList<MusicProtocol>();
            while (true) {
                System.out.println("recieving");
                packet = new DatagramPacket(buf, buf.length);
                if (total > 0) {
                    timeOut();
                }
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                MusicProtocol mp = new MusicProtocol().getProtocolo(received);
                chunksMP.add(mp);
                System.out.println("index: " + mp.getIndex() + " Total: " + mp.getTotal());
                i++;
                if (total == 0) {
                    jLabelMusica.setText(mp.getNome());
                    jLabelPlaylist.setText(mp.getPlaylistNome());
                    total = mp.getTotal();
                }
                if (mp.getTotal() == mp.getIndex()) {
                    i = 0;
                    total = 0;
                    ArrayList<MusicProtocol> m = (chunksMP);
                    queue.add(m);
                    timeout.interrupt();
                    chunksMP = new ArrayList<MusicProtocol>();
                } else if (total != mp.getTotal()) {
                    i = 0;
                    total = 0;
                    timeout.interrupt();
                    chunksMP = new ArrayList<MusicProtocol>();
                }
            }
        }  catch (InterruptedException | IOException  ex) {
            Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   /**
    * Metodo timeOut()
    * inicia assim que começa o recebimento dos arquivos da musica
    * apos 10s sem receber nada, inicia a reprodução
    * @exception InterruptedException
   */
    public void timeOut() throws InterruptedException {
        if (timeout != null) {
            timeout.interrupt();
        }
        timeout = new Thread("timout") {
            public void run() {
                try {
                    timeout.sleep(10000);
                    System.out.println("timeout");
                    ArrayList<MusicProtocol> m = (chunksMP);
                    queue.add(m);
                    chunksMP = new ArrayList<MusicProtocol>();
                } catch (InterruptedException ex) {

                }

            }
        };
        timeout.start();
    }
   /**
    * Metodo play()
    * inicia a Thread que une os arquivos e começa a tocar os arquivos da musica
    * fica em idle até ter algo para tocar
    * @see #mergeFileParts(java.util.ArrayList) 
   */
    public void play() {
        Play = new Thread("Play") {
            public void run() {
                try {
                    while (true) {
                        while (true) {
                            if (!queue.isEmpty()) {
                                break;
                            }
                        }
                        mp3Player = new Player(new ByteArrayInputStream(mergeFileParts(queue.getFirst())));
                        queue.remove();
                        mp3Player.play();
                    }
                } catch (IOException | JavaLayerException ex) {
                    Logger.getLogger(PlayerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Play.start();
    }
   /**
    * Metodo close()
    * finaliza todas as threads e para a musica
    * notifica o servidor que o cliente desconectou
    * @exception SocketException
    * @exception IOException
   */
    public void close() throws SocketException, IOException {
        if (mp3Player != null) {
            mp3Player.close();
        }
        Play.interrupt();
        InetAddress address;
        DatagramPacket packet;
        byte[] buf = new byte[3000];
        address = InetAddress.getByName(ip);
        buf = (new String("Exit").getBytes());
        DatagramSocket socket = new DatagramSocket();
        packet = new DatagramPacket(buf, buf.length,
                address, 4445);
        socket.send(packet);
    }
   /**
    * Metodo mergeFileParts()
    * recebe um Array de partes de musica, une em um arquivo só
    * Retorna um arquivo reproduzivel
    * @param queue Array de pedaços do arquivo, dentro do protocolo
    * @return byte[]
    * @exception IOException
   */
    private byte[] mergeFileParts(ArrayList<MusicProtocol> queue) throws IOException {
        InputStream inp = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            for (MusicProtocol mp : queue) {
                inp = new ByteArrayInputStream(mp.getStream());
                int partFilesize = (int) mp.getStream().length;
                byte[] b = new byte[partFilesize];
                int i = inp.read(b, 0, partFilesize);
                out.write(b, 0, i);
                inp.close();
                inp = null;
            }

        } finally {
            if (inp != null) {
                inp.close();
            }
            if (out != null) {
                out.close();
            }
        }
        return out.toByteArray();
    }
}
