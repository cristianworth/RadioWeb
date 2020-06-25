/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radioweb.serverController;

import Server.MusicProtocol;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Header;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Marcelo
 */
public class SenderController extends Thread {

    ArrayList<String> caminhos = new ArrayList<String>();

    public SenderController(ArrayList<String> caminhos) throws IOException {

        this.caminhos = caminhos;

    }

    public void run() {
        try {
            try (Socket socket = new Socket("127.0.0.1", 6666)) {
                if (socket.isConnected()) {
                    MusicProtocol mp = null;
                    ArrayList<MusicProtocol> mpA = new ArrayList<MusicProtocol>();
                    for (String c : caminhos) {//mudar pra receber array de musca e o nome da playlist ?
                        File soundFile = AudioUtil.getSoundFile(c);
                        FileInputStream in = new FileInputStream(soundFile);
                        mp = new MusicProtocol("AQUI VAI O NOME DA MUSCIA, e a playlist no ultimo parametro", IOUtils.toByteArray(in), getDurationWithMp3Spi(c));
                        mpA.add(mp);
                    }
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println();
                    out.writeObject(mpA);
                    out.flush();
                }
            } catch (UnsupportedAudioFileException ex) {
            }

        } catch (IOException ex) {

        }
    }

    private float getDurationWithMp3Spi(String filename) throws UnsupportedAudioFileException, IOException {

        Header h = null;
        FileInputStream file = null;
        try {
            file = new FileInputStream(filename);
        } catch (FileNotFoundException ex) {
        }
        Bitstream bitstream = new Bitstream(file);
        try {
            h = bitstream.readFrame();

        } catch (BitstreamException ex) {
        }
        long tn = 0;
        try {
            tn = file.getChannel().size();
        } catch (IOException ex) {
        }
        return h.total_ms((int) tn) / 1000;

    }
}
