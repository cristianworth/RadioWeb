/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radioweb.serverController;

/**
 *
 * @author Marcelo
 */
public class Musica {
    private int id_musica;
    private String nome_musica;
    private String caminho_musica;

    public Musica(int id_musica, String nome_musica, String caminho_musica) {
        this.id_musica = id_musica;
        this.nome_musica = nome_musica;
        this.caminho_musica = caminho_musica;
    }

    public int getId_musica() {
        return id_musica;
    }

    public void setId_musica(int id_musica) {
        this.id_musica = id_musica;
    }

    public String getNome_musica() {
        return nome_musica;
    }

    public void setNome_musica(String nome_musica) {
        this.nome_musica = nome_musica;
    }

    public String getCaminho_musica() {
        return caminho_musica;
    }

    public void setCaminho_musica(String caminho_musica) {
        this.caminho_musica = caminho_musica;
    }
    
}
