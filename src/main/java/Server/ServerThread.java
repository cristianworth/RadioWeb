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
    ServerSocket serverSocket;
    public volatile static ArrayList<String> ArrBuf = new ArrayList<String>();
    public volatile static LinkedList<MusicProtocol> ArrMPT = new LinkedList<MusicProtocol>();

    public ServerThread() throws IOException {
        this("ServerThread");
    }
        public ServerThread(String name) throws IOException {
        super(name);
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        serverSocket = new ServerSocket(6666, 10, addr);
        socket = new DatagramSocket(4445, addr);
        System.out.println("Server iniciado no endereço: 127.0.0.1");
    }
    
    public ServerThread(String name,String IP) throws IOException {
        super(name);
        InetAddress addr = InetAddress.getByName(IP);
        serverSocket = new ServerSocket(6666, 10, addr);
        socket = new DatagramSocket(4445, addr);
        System.out.println("Server iniciado no endereço: "+IP);
    }

    /**
     * Metodo runProcess metodo encarregado de iniciar a thread process e
     * organizar os arquivos quando acaba os arquivos, limpa a thread liberando
     * para o servidor inciar outra caso o controllador mande mais arquivos
     * chama o metodo splitFile para quebrar os arquivos monta os protocos que
     * vao ser enviados ao client caso uma musica comece nova, manda para todos
     * os clients conectados usando o sendToAll
     *
     * @see #splitFile(byte[])
     * @see #sendToAll()
     */
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
                                    music = new MusicProtocol(i, mp.getNome(), b, chunks.size() - 1, mp.getDuracao(), mp.getChunkDuracao(), mp.getPlaylistNome());
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
                } catch (InterruptedException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        process.start();
    }

    /**
     * Metodo ClockStart inicia a contagem da musica, a cada ChunkDuracao
     * calculado pelo ServerLoop, -50ms remove uma parte do arquivo quando o
     * count do clock chega no numero total de arquivos, finaliza a contagem,
     * liberando a thread para enviar mais arquivos
     *
     * @see #ServerLoop()
     * @param interval ChunkDuracao em MS dos chunks da musica
     * @param total total de pedaços no qual foi dividido a musica
     * @exception InterruptedException
     */
    public void ClockStart(int interval, int total) throws InterruptedException {
        while (this.val < total) {
            Thread.sleep(interval - 50);
            this.val++;
            this.senders.remove();
        }
        System.out.println("ended");
        this.val = 0;
    }

    /**
     * Metodo init inicia o servidor e as threads para receber a controller e os
     * clients;
     *
     * @see #receive()
     * @see #ServerLoop()
     */
    public void init() {
        receive();
        Thread thread = new Thread() {
            public void run() {
                ServerLoop();
            }
        };
        thread.start();
    }

    /**
     * Metodo sendToAll Envia os arquvios da queue para todos os clients
     * conectados
     *
     * @see #send(java.net.InetAddress, int)
     */
    private void sendToAll() {
        for (int i = 0; i < clients.size(); i++) {
            ServerClient client = clients.get(i);
            send(client.address, client.port);
        }
    }

    /**
     * Metodo send Envia os arquvios da queue para um client delay de 5ms entre
     * cada arquivo, para o client processar e reduzir perdas em runtime
     *
     * @param address InetAddress do client
     * @param port porta do client
     */
    private void send(final InetAddress address, final int port) {
        send = new Thread("Send") {
            public void run() {

                try {
                    ArrayList<String> sendersNow = new ArrayList<String>(senders);
                    if (allowsend) {
                        for (String s : sendersNow) {
                            byte[] data = s.getBytes();
                            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
                            send.sleep(5);
                            socket.send(packet);
                        }
                    }
                } catch (InterruptedException | IOException ex) {
                    Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        send.start();
    }

    /**
     * Metodo ServerLoop Recebe os arquivos da controller calcula o tamanho
     * medio de cada arquivo com base nos 10kb que serao enviados por vez para o
     * client se o processo de envio não esta acontecendo, inicia o mesmo
     *
     * @see #runProcess()
     */
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
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
            }
            try {
                new ServerThread().start();
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * Metodo receive Inicia a Thread receive essa Thread recebe os clients e
     * adiciona eles ao array de clients se o client enviar Exit, remove ele da
     * lista de clients se o client ja está na lista, e foi recebido novamente,
     * envia os arquivos novamente se o client é novo, envia os arquivos
     *
     * @see #send(java.net.InetAddress, int)
     */
    private void receive() {
        receive = new Thread("Receive") {
            public void run() {
                while (true) {
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException e) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, e);
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

    /**
     * Metodo splitFile quebra o arquivo em arquivos menores de 10kb calcula
     * quantos arquivos serao necessarios retorna um array de array's de bytes
     * representando o arquivo
     *
     * @param in byte[] representante do arquivo recebido da controller
     * @return ArrayList<byte[]>
     * @exception IOException
     * @exception NoSuchAlgorithmException
     */
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
