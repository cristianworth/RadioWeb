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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.Math.toIntExact;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Marcelo
 */
public class ServerThread extends Thread {

    private final DatagramSocket socket;
    private Thread process, send, receive;
    private List<ServerClient> clients = new ArrayList<ServerClient>();
    volatile int val;
    private volatile Boolean allowsend = false;
    volatile LinkedList<String> senders = new LinkedList<String>();

    public ServerThread() throws IOException {
        this("ServerThread");
    }

    public ServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);
    }

    public void runProcess() {
        process = new Thread("Process") {
            public void run() {
                try {
                    senders.clear();
                    while (ArrMPT.size() > 0) {
                        try {
                            String mps = "\n";
                            ArrayList<byte[]> chunks = new ArrayList<byte[]>();
                            MusicProtocol music;
                            while (!ArrMPT.isEmpty()) {
                                MusicProtocol mp = ArrMPT.getFirst();
                                chunks = splitFile(mp.getStream());
                                int i = 0;
                                for (byte[] b : chunks) {
                                    music = new MusicProtocol(i, mp.getNome(), b, chunks.size() - 1, mp.getDuracao(), mp.getChunkDuracao());
                                    mps = music.transformaJson();
                                    senders.add(mps + "\n");
                                    i++;
                                }
                                ArrMPT.remove();
                                allowsend = true;
                                if (clients.size() > 0) {
                                    sendToAll();
                                }
                                ClockStart(mp.getChunkDuracao(), chunks.size());
                            }
                            System.out.println("file ended");

                        } catch (IOException ex) {

                        }
                    }
                    process = null;
                } catch (NoSuchAlgorithmException ex) {

                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        process.start();
    }

    public void ClockStart(int interval, int total) throws InterruptedException {
        while (this.val < total) {
            Thread.sleep(interval - 50);
            this.val++;
            this.senders.remove();
        }
        System.out.println("ended");
        this.val = 0;
    }

    ServerSocket serverSocket;
    public volatile static ArrayList<String> ArrBuf = new ArrayList<String>();
    public volatile static LinkedList<MusicProtocol> ArrMPT = new LinkedList<MusicProtocol>();

    public void init() {
        try {
            serverSocket = new ServerSocket(6666);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        receive();
        Thread thread = new Thread() {
            public void run() {
                ServerLoop();
            }
        };
        thread.start();
    }

    private void sendToAll() {
        for (int i = 0; i < clients.size(); i++) {
            ServerClient client = clients.get(i);
            send(client.address, client.port);
        }
    }

    private void send(final InetAddress address, final int port) {
        send = new Thread("Send") {
            public void run() {

                try {
                    ArrayList<String> sendersNow = new ArrayList<String>(senders);
                    if (allowsend) {
                        for (String s : sendersNow) {
                            byte[] data = s.getBytes();
                            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                            send.sleep(2);
                            socket.send(packet);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        send.start();
    }

    public void ServerLoop() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                if (clientSocket.isBound()) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                    ArrayList<MusicProtocol> mps = (ArrayList<MusicProtocol>) objectInputStream.readObject();
                    System.out.println("Recieve " + mps.size() + " files from controller");
                    for (MusicProtocol mp : mps) {
                        long filesize = mp.getStream().length;
                        int splitval = (int) (Math.ceil((filesize + (filesize % 10000)) / (10000)));
                        int splitsize = (int) (filesize / splitval) + (int) (filesize % splitval);
                        float duracao = (mp.getDuracao() * splitsize) / filesize;
                        mp.setChunkDuracao((int) (duracao * 1000));
                        mp.setTotal(splitval);
                        ArrMPT.add(mp);
                    }
                    if (process == null) {
                        System.out.println("Process started");
                        runProcess();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                new ServerThread().start();
            } catch (IOException ex) {

            }
        }

    }

    private void receive() {
        receive = new Thread("Receive") {
            public void run() {
                while (true) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String string = new String(packet.getData());
                    if (clients.stream().filter(o -> o.address.equals(packet.getAddress())).findFirst().isPresent()) {
                        if (string.trim().equals("Exit")) {
                            clients.removeIf(c -> c.address.equals(packet.getAddress()));
                            System.out.println("removed");
                        } else {
                            System.out.println("Send");
                            send(packet.getAddress(), packet.getPort());
                        }
                    } else {
                        clients.add(new ServerClient("client", packet.getAddress(), packet.getPort()));
                        send(packet.getAddress(), packet.getPort());
                    }
                    System.out.println("Client Connect: " + packet.getAddress() + ":" + packet.getPort());
                }
            }
        };
        receive.start();
    }

    public static ArrayList<byte[]> splitFile(byte[] in) throws IOException, NoSuchAlgorithmException {
        ByteArrayOutputStream out = null;
        long filesize = in.length;
        InputStream is = null;
        int splitval = (int) (Math.ceil((filesize + (filesize % 10000)) / (10000)));
        int splitsize = (int) (filesize / splitval) + (int) (filesize % splitval);
        byte[] b = new byte[splitsize];
        ArrayList<byte[]> chunks = new ArrayList<byte[]>();
        try {
            is = new ByteArrayInputStream(in);
            for (int j = 1; j <= splitval; j++) {
                out = new ByteArrayOutputStream();
                int i = is.read(b);
                if (i >= 0) {
                    out.write(b, 0, i);
                    chunks.add(out.toByteArray());
                    out.close();
                    out = null;
                }
            }
            return chunks;
        } finally {
            if (is != null) {
                is.close();
            }
            if (out != null) {
                out.close();
            }

        }
    }

}
