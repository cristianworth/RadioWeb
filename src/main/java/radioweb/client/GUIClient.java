package radioweb.client;

import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class GUIClient extends javax.swing.JFrame {

    boolean isSelected = true;
    float volume = 0;
    PlayerController pc;
    public String ip = "127.0.0.1";

    public GUIClient() {
        initComponents();
        jsVolume.setMinimum(0);
        jsVolume.setMaximum(100);
        this.setTitle("Rádio Webson");
        try {
            mostraGif();
        } catch (MalformedURLException ex) {
            Logger.getLogger(GUIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo mostraGif exibe o gif do "AlienPls" (o alien dançandinho)
     *
     * @exception MalformedURLException
     */
    private void mostraGif() throws MalformedURLException {
        Random gerador = new Random();
        URL url;
        if (gerador.nextInt(2) == 0) {
        url = new URL("https://media.tenor.com/images/a70cf6c7c1996c141e33b54428c8ebaa/tenor.gif");
        }else{
       url = new URL("https://cdn.betterttv.net/emote/5805580c3d506fea7ee357d6/3x");
        }
        ImageIcon image = new javax.swing.ImageIcon(url);
        int width = 80;
        int height = 80;
        image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));

        jlGif.setIcon(image);
    }

    private static final String PATTERN
            = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    /**
     * Metodo validateIP 
     * Varifica se o IP é valido
     *
     * @param ip String IP
     * @return boolean
     */
    public static boolean validateIP(String ip) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbPlayPause = new javax.swing.JButton();
        jLabelMusica = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jsVolume = new javax.swing.JSlider();
        jlGif = new javax.swing.JLabel();
        jLabelPlaylist = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jbPlayPause.setText("PLAY ▶");
        jbPlayPause.setPreferredSize(new java.awt.Dimension(85, 25));
        jbPlayPause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbPlayPauseMouseClicked(evt);
            }
        });

        jLabelMusica.setText(" ");

        jLabel1.setText("Radio Webson");

        jsVolume.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jsVolumeStateChanged(evt);
            }
        });
        jsVolume.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jsVolumeMouseReleased(evt);
            }
        });

        jlGif.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelPlaylist.setText(" ");

        jButton1.setText("IP");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addComponent(jsVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jbPlayPause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelMusica, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelPlaylist, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap())
                    .addComponent(jlGif, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlGif, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPlaylist)
                .addGap(1, 1, 1)
                .addComponent(jLabelMusica)
                .addGap(18, 18, 18)
                .addComponent(jbPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbPlayPauseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbPlayPauseMouseClicked
        if (isSelected) {
            isSelected = false;
            jbPlayPause.setText("STOP ⏹");
            try {
                pc = new PlayerController(jLabelMusica, jLabelPlaylist,(this.ip==null?"127.0.0.1":this.ip));
                pc.start();
            } catch (NullPointerException ex) {
            }
        } else {
            try {
                pc.close();
            } catch (IOException ex) {
                Logger.getLogger(GUIClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            isSelected = true;
            jbPlayPause.setText("PLAY ▶");
        }
    }//GEN-LAST:event_jbPlayPauseMouseClicked
    /**
     * Metodo jsVolumeStateChanged ajusta o volume do mixer
     *
     * @param evt evento ChangeEvent
     */
    private void jsVolumeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsVolumeStateChanged
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        System.out.println("There are " + mixers.length + " mixer info objects");
        for (Mixer.Info mixerInfo : mixers) {
            System.out.println("Mixer name: " + mixerInfo.getName());
            Mixer mixer = AudioSystem.getMixer(mixerInfo);

            Line.Info[] lineInfos = mixer.getTargetLineInfo(); // target, not source  
            for (Line.Info lineInfo : lineInfos) {
                System.out.println("  Line.Info: " + lineInfo);
                Line line = null;
                boolean opened = true;
                try {
                    line = mixer.getLine(lineInfo);
                    opened = line.isOpen() || line instanceof Clip;
                    if (!opened) {
                        line.open();
                    }
                    float v1 = jsVolume.getValue();
                    volume = v1 / 100;
                    FloatControl volCtrl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                    if (mixer.isLineSupported(Port.Info.SPEAKER)) {
                        volCtrl.setValue(volume);
                    }
                    System.out.println("    volCtrl.getValue() = " + volCtrl.getValue());
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException iaEx) {
                    System.out.println("    " + iaEx);
                } finally {
                    if (line != null && !opened) {
                        line.close();
                    }
                }
            }
        }
        System.out.println("var" + volume);
        System.out.println("Volume atual: " + jsVolume.getValue() / 100);
    }//GEN-LAST:event_jsVolumeStateChanged

    private void jsVolumeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsVolumeMouseReleased
       // System.out.println("Volume released: " + jsVolume.getValue());
    }//GEN-LAST:event_jsVolumeMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String newip = JOptionPane.showInputDialog(null, "Endereço IPV4 do servidor", "Informe o IP", JOptionPane.INFORMATION_MESSAGE);
        if (newip != null) {
            if (validateIP(newip)){
            this.ip = newip;
        }else{
             JOptionPane.showMessageDialog(null, "Endereço IPV4 invalido", "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            Properties props = new Properties();
            props.put("logoString", "Rádio Webson");
            HiFiLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(GUIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelMusica;
    private javax.swing.JLabel jLabelPlaylist;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbPlayPause;
    private javax.swing.JLabel jlGif;
    private javax.swing.JSlider jsVolume;
    // End of variables declaration//GEN-END:variables
}
