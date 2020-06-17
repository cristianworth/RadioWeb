/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Marcelo
 */
public class MusicProtocol {
    private int index;
    private String nome;
    private byte[] stream;

    public MusicProtocol(int index, String nome, byte[] stream) {
        this.index = index;
        this.nome = nome;
        this.stream = stream;
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
