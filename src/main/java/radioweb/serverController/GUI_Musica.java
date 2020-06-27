package radioweb.serverController;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class GUI_Musica extends javax.swing.JFrame {

    Statement st;
    DefaultTableModel modelo;

    private String Nome = "";
    private String Caminho = "";

    public GUI_Musica() {
        initComponents();
        jlCaminho.setText("");
        this.setLocationRelativeTo(null); //coloca o frame centralizado na tela

        try {
            st = new DBConexao().getConnection(); //faz a conexão com o banco de dados
        } catch (Exception e) {
            Logger.getLogger(GUI_Musica.class.getName()).log(Level.SEVERE, null, e); //log de erro
            System.out.println("Error: " + e.toString() + e.getMessage());
        }

        jtTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfNome.setText(jtTabela.getValueAt(jtTabela.getSelectedRow(), 0).toString());
                try {
                    SetarCaminho(Decodifica(jtTabela.getModel().getValueAt(jtTabela.getSelectedRow(), 2).toString()));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(GUI_Musica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Listar();
    }

    /**
     * Metodo LimpaCampo limpa os campos de nome e caminho
     */
    public void LimpaCampo() {
        jtfNome.setText("");
        jlCaminho.setText("");
    }

    /**
     * Metodo LinhaValida Verifica se tem uma linha selecionada
     *
     * @return boolean
     */
    public boolean LinhaValida() {
        int row = jtTabela.getSelectedRow();
        if (row >= 0) { //zero é a primeira coluna
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo BuscaCodigoSelecionado busca o codigo da linha selecionada
     *
     * @return int
     */
    public int BuscaCodigoSelecionado() {
        int row = jtTabela.getSelectedRow();
        int column = 1; //coluna do codigo
        int codigoSelecionado = Integer.valueOf(jtTabela.getModel().getValueAt(row, column).toString());
        System.out.println("codigoselecionado");
        return codigoSelecionado;
    }

    /**
     * Metodo Listar Lista na tabela
     */
    private void Listar() {
        String colunas[] = {"Música", "Código", "Caminho"};
        modelo = new DefaultTableModel(colunas, 0);

        try {
            String sql = "SELECT id_musica, nome_musica, caminho_musica FROM Musica";
            ResultSet rec = st.executeQuery(sql);
            while (rec.next()) {
                String nome = rec.getString("nome_musica");
                String codigo = rec.getString("id_musica");
                String caminho = rec.getString("caminho_musica");

                modelo.addRow(new Object[]{nome, codigo, caminho});
                LimpaCampo();
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Erro ao listar!! " + s.toString());
        }
        jtTabela.setModel(modelo);
        jtTabela.getColumnModel().removeColumn(jtTabela.getColumnModel().getColumn(1)); //remove codigo
        jtTabela.getColumnModel().removeColumn(jtTabela.getColumnModel().getColumn(1)); //remove caminho
    }

    /**
     * Metodo Alterar Altera o valor no banco
     */
    public void Alterar() {
        Nome = jtfNome.getText();
        //Caminho = Caminho.replaceAll("%5C", "\\\\");
        try {
            Caminho = URLEncoder.encode(Caminho, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GUI_Musica.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Camminho alterar" + Caminho);
        String set = " SET nome_musica='" + Nome + "', caminho_musica='" + Caminho + "'";
        String where = " WHERE id_musica=" + BuscaCodigoSelecionado();
        String sql = "UPDATE Musica" + set + where;

        try {
            int resp = st.executeUpdate(sql);
            if (resp == 1) {
                Listar();
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Informações não alteradas!! " + s.toString());
        }
    }

    /**
     * Metodo Incluir Inclui o valor no banco
     */
    public void Incluir() {
        Nome = jtfNome.getText();
        Caminho = jlCaminho.getText();
        try {
            Caminho = URLEncoder.encode(Caminho, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GUI_Musica.class.getName()).log(Level.SEVERE, null, ex);
        }
        String values = "'" + Nome + "', '" + Caminho + "'";
        String sql = "INSERT INTO Musica (nome_musica, caminho_musica) VALUES(" + values + ")";

        try {
            int resp = st.executeUpdate(sql);
            if (resp == 1) {
                Listar();
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Informações não incluida!! " + s.toString());
        }
    }

    /**
     * Metodo Excluir Exclui o valor no banco
     */
    public void Excluir() {
        String sql = "DELETE FROM Musica WHERE id_musica=" + BuscaCodigoSelecionado();

        try {
            int resp = st.executeUpdate(sql);
            if (resp == 1) {
                Listar();
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(this, "Informações não excluidas!! " + s.toString());
        }
    }

    /**
     * Metodo Decodifica decodifica uma URL em URI
     *
     * @param caminho string com o caminho em URI
     * @return string
     * @throws UnsupportedEncodingException
     */
    public String Decodifica(String caminho) throws UnsupportedEncodingException {
        try {
            caminho = URLDecoder.decode(caminho, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GUI_Musica.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caminho;
    }

    /**
     * Metodo SetarCaminho seta o label com o caminho
     *
     * @param caminho string caminho
     */
    public void SetarCaminho(String caminho) {
        Caminho = caminho;
        jlCaminho.setText(caminho);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jbListar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtTabela = new javax.swing.JTable();
        jbIncluir = new javax.swing.JButton();
        jbAlterar = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();
        jbMenu = new javax.swing.JButton();
        jtfNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jbArquivo = new javax.swing.JButton();
        jlCaminho = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Musica");
        setResizable(false);

        jbListar.setText("Listar");
        jbListar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbListarMouseClicked(evt);
            }
        });

        jtTabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Música", "Código", "Caminho"
            }
        ));
        jScrollPane2.setViewportView(jtTabela);
        if (jtTabela.getColumnModel().getColumnCount() > 0) {
            jtTabela.getColumnModel().getColumn(1).setResizable(false);
        }

        jbIncluir.setText("Incluir");
        jbIncluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbIncluirMouseClicked(evt);
            }
        });

        jbAlterar.setText("Alterar");
        jbAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbAlterarMouseClicked(evt);
            }
        });

        jbExcluir.setText("Excluir");
        jbExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbExcluirMouseClicked(evt);
            }
        });

        jbMenu.setText("<<< Voltar");
        jbMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbMenuMouseClicked(evt);
            }
        });

        jLabel2.setText("Música:");

        jbArquivo.setText("Arquivo");
        jbArquivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbArquivoMouseClicked(evt);
            }
        });

        jlCaminho.setText("Caminho");
        jlCaminho.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCaminho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jtfNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbArquivo))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jbListar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jbMenu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbIncluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jbListar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbArquivo)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlCaminho)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbExcluir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbAlterar)
                        .addComponent(jbIncluir)
                        .addComponent(jbMenu)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
     /**
     * Metodo jbListarMouseClicked chama o Listar
     *
     * @see #Listar()
     * @param evt MouseEvent
     */
    private void jbListarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbListarMouseClicked
        Listar();
    }//GEN-LAST:event_jbListarMouseClicked
    /**
     * Metodo jbIncluirMouseClicked chama o Incluir
     *
     * @see #Incluir()
     * @param evt MouseEvent
     */
    private void jbIncluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbIncluirMouseClicked
        if (!jtfNome.getText().equals("") && !jlCaminho.getText().equals("")) {
            Incluir();
        } else {
            JOptionPane.showMessageDialog(this, "Não é possível criar uma música sem um nome ou caminho!!");
        }
    }//GEN-LAST:event_jbIncluirMouseClicked
    /**
     * Metodo jbAlterarMouseClicked chama o Alterar
     *
     * @see #Alterar()
     * @param evt MouseEvent
     */
    private void jbAlterarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbAlterarMouseClicked
        if (LinhaValida()) {
            if (!jtfNome.getText().equals("")) {
                Alterar();
            } else {
                JOptionPane.showMessageDialog(this, "Não é possível criar uma música sem um nome!!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um registro para Alterar");
        }
    }//GEN-LAST:event_jbAlterarMouseClicked
    /**
     * Metodo jbExcluirMouseClicked chama o Excluir
     *
     * @see #Excluir()
     * @param evt MouseEvent
     */
    private void jbExcluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbExcluirMouseClicked
        if (LinhaValida()) {
            Excluir();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um registro para Excluir");
        }
    }//GEN-LAST:event_jbExcluirMouseClicked
    /**
     * Metodo jbMenuMouseClicked retorna ao GUI_Main
     *
     * @see GUI_Main
     * @param evt MouseEvent
     */
    private void jbMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbMenuMouseClicked
        //volta para o menu principal
        new GUI_Main().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jbMenuMouseClicked
    /**
     * Metodo jbArquivoMouseClicked abre o file chooser
     */
    private void jbArquivoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbArquivoMouseClicked
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            SetarCaminho(selectedFile.getAbsolutePath());
        }
    }//GEN-LAST:event_jbArquivoMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbAlterar;
    private javax.swing.JButton jbArquivo;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JButton jbIncluir;
    private javax.swing.JButton jbListar;
    private javax.swing.JButton jbMenu;
    private javax.swing.JLabel jlCaminho;
    private javax.swing.JTable jtTabela;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
