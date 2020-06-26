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

    ArrayList<Musica> musicas = new ArrayList<Musica>();
    String PlaylistNome;
    String IP;

    public SenderController(ArrayList<Musica> musicas,String PlaylistNome, String IP) throws IOException {

        this.musicas = musicas;
        this.PlaylistNome = PlaylistNome;
        this.IP = IP;
    }
    /**
     * Metodo run
     * abre o arquivo da musca, calcula a duração media em s
     * manda as muscas para o servidor
     * @see #getDurationWithMp3Spi(java.lang.String) 
     */
    public void run() {
        try {
            try (Socket socket = new Socket(IP, 6666)) {
                if (socket.isConnected()) {
                    MusicProtocol mp = null;
                    ArrayList<MusicProtocol> mpA = new ArrayList<MusicProtocol>();
                    for (Musica m : musicas) {//mudar pra receber array de musca e o nome da playlist ?
                        File soundFile = AudioUtil.getSoundFile(m.getCaminho_musica());
                        FileInputStream in = new FileInputStream(soundFile);
                        mp = new MusicProtocol(m.getNome_musica(), IOUtils.toByteArray(in), getDurationWithMp3Spi(m.getCaminho_musica()),PlaylistNome);
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
    /**
     * Metodo getDurationWithMp3Spi
     * abre o arquivo da musca, calcula a duração media em s usando o framerate
     * @param filename caminho do arquivo
     * @return float
     * @throws UnsupportedAudioFileException
     * @throws IOException
     */
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
