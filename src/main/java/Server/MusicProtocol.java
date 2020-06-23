/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

/**
 *
 * @author Marcelo
 */
public class MusicProtocol {

    private int index;
    private String nome;
    private byte[] stream;
    private int total;

    public MusicProtocol() {

    }

    public MusicProtocol(int index, String nome, byte[] stream, int total) {
        this.index = index;
        this.nome = nome;
        this.stream = stream;
        this.total = total;
    }

    public MusicProtocol getProtocolo(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MusicProtocol protocolo = mapper.readValue(json, MusicProtocol.class);  
        return protocolo;
    }

    public String getJson() throws IOException {
        ObjectWriter ow = new ObjectMapper().writer();
        String JsonProcoloMusica = ow.writeValueAsString(this);
        return JsonProcoloMusica;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getStream() {
        return stream;
    }

    public void setStream(byte[] stream) {
        this.stream = stream;
    }
}
