/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import java.io.Serializable;
/**
 *
 * @author Marcelo
 */
public class MusicProtocol implements Serializable  {
    
    private int index;
    private String nome;
    private byte[] stream;
    private int total;
    private float duracao;
    private int chunkDuracao;
    private String playlistNome;

    
    public MusicProtocol() {

    }

    public MusicProtocol(int index, String nome, byte[] stream, int total, float duracao, int chunkDuracao) {
        this.index = index;
        this.nome = nome;
        this.stream = stream;
        this.total = total;
        this.duracao = duracao;
        this.chunkDuracao = chunkDuracao;
    }

    public MusicProtocol(String nome, byte[] stream, float duracao) {
        this.nome = nome;
        this.stream = stream;
        this.duracao = duracao;
    }

    public MusicProtocol(String nome, byte[] stream, float duracao, String playlistNome) {
        this.nome = nome;
        this.stream = stream;
        this.duracao = duracao;
        this.playlistNome = playlistNome;
    }

    public MusicProtocol getProtocolo(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MusicProtocol protocolo = mapper.readValue(json, MusicProtocol.class);  
        return protocolo;
    }

    public String transformaJson() throws IOException {
        ObjectWriter ow = new ObjectMapper().writer();
        String JsonProcoloMusica = ow.writeValueAsString(this);
        return JsonProcoloMusica;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public float getDuracao() {
        return duracao;
    }

    public void setDuracao(float duracao) {
        this.duracao = duracao;
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

    public int getChunkDuracao() {
        return chunkDuracao;
    }

    public void setChunkDuracao(int chunkDuracao) {
        this.chunkDuracao = chunkDuracao;
    }
    
    public String getPlaylistNome() {
        return playlistNome;
    }

    public void setPlaylistNome(String playlistNome) {
        this.playlistNome = playlistNome;
    }
}
