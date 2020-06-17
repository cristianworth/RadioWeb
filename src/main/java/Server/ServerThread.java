/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Math.toIntExact;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

/**
 *
 * @author Marcelo
 */
public class ServerThread extends Thread {

    private final DatagramSocket socket;
    private BufferedReader in;

    public ServerThread() throws IOException {
        this("ServerThread");
    }

    public ServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);
    }

    public void run() {
        try {
            //File soundFile = AudioUtil.getSoundFile("D:\\Musica\\Alice In Chains - Dirt (1992) By Muro\\bbb.wav");
            //FileInputStream in = new FileInputStream(soundFile);
            byte[] buf = new byte[60];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
             socket.receive(packet);
            int count;
            ObjectMapper mapper = new ObjectMapper();
            //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            //String json = ow.writeValueAsString();
            String received = new String(packet.getData(), 0, packet.getLength());
            MusicProtocol mp = mapper.readValue(received, MusicProtocol.class);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String mps = ow.writeValueAsString(mp);
            byte[] b = received.getBytes();
            buf = mps.getBytes();
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            socket.send(packet);
            
        } catch (IOException ex) {

        }
    }

  
}
