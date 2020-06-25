package radioweb.client;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.UIManager;

public class GUIClient extends javax.swing.JFrame {

    boolean isSelected = true;
    float volume = 0;
    PlayerController pc;

    public GUIClient() {
        initComponents();
        jsVolume.setMinimum(0);
        jsVolume.setMaximum(100);

        try {
            mostraGif();
        } catch (MalformedURLException ex) {
            Logger.getLogger(GUIClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Metodo mostraGif
     * exibe o gif do "AlienPls" (o alien dançandinho)
     * @exception MalformedURLException
     */
    private void mostraGif() throws MalformedURLException {
        URL url = new URL("https://cdn.betterttv.net/emote/5805580c3d506fea7ee357d6/3x");
        ImageIcon image = new javax.swing.ImageIcon(url);
        
        //ImageIcon image = new javax.swing.ImageIcon(getClass().getResource("/tenor.gif"));
        int width = 80;
        int height = 80;
        image.setImage(image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        
        jlGif.setIcon(image);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbPlayPause = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jsVolume = new javax.swing.JSlider();
        jlGif = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jbPlayPause.setText("PLAY ▶");
        jbPlayPause.setPreferredSize(new java.awt.Dimension(85, 25));
        jbPlayPause.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbPlayPauseMouseClicked(evt);
            }
        });

        jLabel2.setText("Meet in the Middle - RU");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jsVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jbPlayPause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jbPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jsVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlGif, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
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
                pc = new PlayerController();
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
     * Metodo jsVolumeStateChanged
     * ajusta o volume do mixer
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
        System.out.println("Volume released: " + jsVolume.getValue());
    }//GEN-LAST:event_jsVolumeMouseReleased

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
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        } catch (Exception e) {

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbPlayPause;
    private javax.swing.JLabel jlGif;
    private javax.swing.JSlider jsVolume;
    // End of variables declaration//GEN-END:variables
}
