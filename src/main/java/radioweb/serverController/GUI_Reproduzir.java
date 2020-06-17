package radioweb.serverController;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import radioweb.client.PlayerController;

public class GUI_Reproduzir extends javax.swing.JFrame {

    private Statement st;
    private DefaultTableModel tabelaPlaylist;

    public GUI_Reproduzir() {
        initComponents();
        this.setLocationRelativeTo(null); //coloca o frame centralizado na tela
        try {
            st = new DBConexao().getConnection(); //faz a conexão com o banco de dados
        } catch (Exception e) {
            Logger.getLogger(GUI_Playlist.class.getName()).log(Level.SEVERE, null, e); //log de erro
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
        Listar();
    }

    public void Listar() {
        String colunas[] = {"Playlists", "Código"};
        tabelaPlaylist = new DefaultTableModel(colunas, 0);
        try {
            String sql = "SELECT id_playlist, nome_playlist FROM Playlist";
            ResultSet rec = st.executeQuery(sql);
            while (rec.next()) {
                String nome = rec.getString("nome_playlist");
                String codigo = rec.getString("id_playlist");

                tabelaPlaylist.addRow(new Object[]{nome, codigo});
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Erro ao listar!! " + s.toString());
        }
        jtPlaylist.setModel(tabelaPlaylist);
        jtPlaylist.getColumnModel().removeColumn(jtPlaylist.getColumnModel().getColumn(1)); //remove codigo

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane5 = new javax.swing.JScrollPane();
        jtPlaylist = new javax.swing.JTable();
        jbPlayPlaylist = new javax.swing.JButton();
        jbMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reproduzir");

        jtPlaylist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Playlists"
            }
        ));
        jScrollPane5.setViewportView(jtPlaylist);

        jbPlayPlaylist.setText("Play ▶");
        jbPlayPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbPlayPlaylistMouseClicked(evt);
            }
        });

        jbMenu.setText("<<< Voltar");
        jbMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbMenuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbPlayPlaylist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jbPlayPlaylist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbPlayPlaylistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbPlayPlaylistMouseClicked
        String codigoPlaylist = jtPlaylist.getModel().getValueAt(jtPlaylist.getSelectedRow(), 1).toString();
        BuscaCaminhoDasMusicas(codigoPlaylist);
    }//GEN-LAST:event_jbPlayPlaylistMouseClicked

    private void jbMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbMenuMouseClicked
        //volta para o menu principal
        new GUI_Main().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbMenuMouseClicked

    public void BuscaCaminhoDasMusicas(String codigoPlaylist) {
        try {
            String sql = "SELECT Musica.id_musica, Musica.nome_musica, Musica.caminho_musica from MusicaPlaylist\n"
                    + "LEFT JOIN Musica on (Musica.id_musica = MusicaPlaylist.id_musica)\n"
                    + "WHERE id_playlist = " + codigoPlaylist
                    + " ORDER BY id_musica_playlist";
            ResultSet rec = st.executeQuery(sql);
            while (rec.next()) {
                String caminho = rec.getString("caminho_musica");
                try {
                    caminho = Decodifica(caminho);
                    //FazAlgoNaControllerDoMarcelo(caminho);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(GUI_Reproduzir.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Erro ao listar!! " + s.toString());
        }
    }

    public String Decodifica(String caminho) throws UnsupportedEncodingException {
        try {
            caminho = URLDecoder.decode(caminho, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GUI_Musica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caminho;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton jbMenu;
    private javax.swing.JButton jbPlayPlaylist;
    private javax.swing.JTable jtPlaylist;
    // End of variables declaration//GEN-END:variables
}
