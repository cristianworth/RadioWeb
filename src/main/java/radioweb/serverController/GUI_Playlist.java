package radioweb.serverController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GUI_Playlist extends javax.swing.JFrame {
    
    private Statement st;
    private DefaultTableModel tabelaPlaylist, tabelaMusica_Playlist, tabelaMusica;
    
    public GUI_Playlist() {
        initComponents();
        this.setLocationRelativeTo(null); //coloca o frame centralizado na tela
        
        try {
            st = new DBConexao().getConnection(); //faz a conexão com o banco de dados
        } catch (Exception e) {
            Logger.getLogger(GUI_Playlist.class.getName()).log(Level.SEVERE, null, e); //log de erro
            System.out.println("Error: " + e.toString() + e.getMessage());
        }
        ListarInit();
        AddListenersTabela();
    }
    
    public void ListarInit() {
        ListarPlaylist();
        ListarMusica("");
    }
    
    public void AddListenersTabela() {
        jtPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AtualizaMusicas(jtPlaylist.getModel().getValueAt(jtPlaylist.getSelectedRow(), 1).toString());
            }
        });
    }
    
    public void AtualizaMusicas(String playlist) {
        /*atualiza as musicas para:
            *saber quais já foram adicionadas na playlist;
            *saber quais estão disponiveis para serem adicionadas na playlist;*/
        ListarMusica_Playlist(playlist);
        ListarMusica(playlist);
    }
    
    public void ListarPlaylist() {
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
    
    public void ListarMusica(String codigoPlaylist) {
        String colunas[] = {"Todas Músicas", "Código"};
        tabelaMusica = new DefaultTableModel(colunas, 0);
        try {
            String sql = "SELECT id_musica, nome_musica FROM Musica";
            
            if (codigoPlaylist != "") {
                String where = " WHERE Musica.id_musica NOT IN (SELECT id_musica FROM MusicaPlaylist WHERE id_playlist = " + codigoPlaylist + ")";
                sql += where;
            }
            
            ResultSet rec = st.executeQuery(sql);
            while (rec.next()) {
                String nome = rec.getString("nome_musica");
                String codigo = rec.getString("id_musica");
                
                tabelaMusica.addRow(new Object[]{nome, codigo});
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Erro ao listar!! " + s.toString());
        }
        jtMusica.setModel(tabelaMusica);
        jtMusica.getColumnModel().removeColumn(jtMusica.getColumnModel().getColumn(1)); //remove codigo
    }
    
    public void ListarMusica_Playlist(String codigoPlaylist) {
        String colunas[] = {"Músicas da Playlist", "Código"};
        tabelaMusica_Playlist = new DefaultTableModel(colunas, 0);
        try {
            String sql = "SELECT Musica.id_musica, Musica.nome_musica from MusicaPlaylist\n"
                    + "LEFT JOIN Musica on (Musica.id_musica = MusicaPlaylist.id_musica)\n"
                    + "WHERE id_playlist = " + codigoPlaylist
                    + " ORDER BY id_musica_playlist";
            ResultSet rec = st.executeQuery(sql);
            while (rec.next()) {
                String nome = rec.getString("nome_musica");
                String codigo = rec.getString("id_musica");
                
                tabelaMusica_Playlist.addRow(new Object[]{nome, codigo});
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Erro ao listar!! " + s.toString());
        }
        jtMusica_Playlist.setModel(tabelaMusica_Playlist);
        jtMusica_Playlist.getColumnModel().removeColumn(jtMusica_Playlist.getColumnModel().getColumn(1)); //remove codigo
    }
    
    public void LimpaCampo() {
        jtfNomePlaylist.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbAdicionaNaPlaylist = new javax.swing.JButton();
        jbCriarPlaylist = new javax.swing.JButton();
        jtfNomePlaylist = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButtonVoltar = new javax.swing.JButton();
        jbRemoveDaPlaylist = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtMusica = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtPlaylist = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtMusica_Playlist = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Playlist");

        jbAdicionaNaPlaylist.setText("<<");
        jbAdicionaNaPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionaNaPlaylistActionPerformed(evt);
            }
        });

        jbCriarPlaylist.setText("Criar playlist");
        jbCriarPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCriarPlaylistActionPerformed(evt);
            }
        });

        jLabel4.setText("CRIAR NOVA PLAYLIST");

        jButtonVoltar.setText("< < < Voltar");
        jButtonVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVoltarActionPerformed(evt);
            }
        });

        jbRemoveDaPlaylist.setText(">>");
        jbRemoveDaPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoveDaPlaylistActionPerformed(evt);
            }
        });

        jtMusica.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Todas Músicas"
            }
        ));
        jScrollPane4.setViewportView(jtMusica);

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

        jtMusica_Playlist.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Músicas da Playlist"
            }
        ));
        jScrollPane6.setViewportView(jtMusica_Playlist);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbAdicionaNaPlaylist)
                            .addComponent(jbRemoveDaPlaylist))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButtonVoltar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtfNomePlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbCriarPlaylist)))))
                .addGap(0, 14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6)
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jbAdicionaNaPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemoveDaPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNomePlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCriarPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbCriarPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCriarPlaylistActionPerformed
        //cria a playlist
        try {
            String sql = "INSERT INTO Playlist (nome_playlist) VALUES('" + jtfNomePlaylist.getText() + "')";
            int resp = st.executeUpdate(sql);
            if (resp == 1) {
                ListarPlaylist();
                LimpaCampo();
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Informações não incluida!! " + s.toString());
        }
    }//GEN-LAST:event_jbCriarPlaylistActionPerformed

    private void jButtonVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVoltarActionPerformed
        //volta para o main
        new GUI_Main().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButtonVoltarActionPerformed

    private void jbRemoveDaPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoveDaPlaylistActionPerformed
        try {
            String musica = jtMusica_Playlist.getModel().getValueAt(jtMusica_Playlist.getSelectedRow(), 1).toString();
            String playlist = jtPlaylist.getModel().getValueAt(jtPlaylist.getSelectedRow(), 1).toString();
            String sql = "DELETE FROM MusicaPlaylist WHERE id_musica=" + musica + " and id_playlist=" + playlist + "";
            int resp = st.executeUpdate(sql);
            if (resp == 1) {
                AtualizaMusicas(playlist);
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Informações não incluida!! " + s.toString());
        }
    }//GEN-LAST:event_jbRemoveDaPlaylistActionPerformed

    private void jbAdicionaNaPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionaNaPlaylistActionPerformed
        try {
            String musica = jtMusica.getModel().getValueAt(jtMusica.getSelectedRow(), 1).toString();
            String playlist = jtPlaylist.getModel().getValueAt(jtPlaylist.getSelectedRow(), 1).toString();
            String sql = "INSERT INTO MusicaPlaylist (id_musica, id_playlist) values (" + musica + ", " + playlist + ")";
            int resp = st.executeUpdate(sql);
            if (resp == 1) {
                AtualizaMusicas(playlist);
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Informações não incluida!! " + s.toString());
        }
    }//GEN-LAST:event_jbAdicionaNaPlaylistActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_Playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_Playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_Playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_Playlist.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI_Playlist().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonVoltar;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton jbAdicionaNaPlaylist;
    private javax.swing.JButton jbCriarPlaylist;
    private javax.swing.JButton jbRemoveDaPlaylist;
    private javax.swing.JTable jtMusica;
    private javax.swing.JTable jtMusica_Playlist;
    private javax.swing.JTable jtPlaylist;
    private javax.swing.JTextField jtfNomePlaylist;
    // End of variables declaration//GEN-END:variables

}
