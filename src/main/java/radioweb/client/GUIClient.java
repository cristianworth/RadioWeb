package radioweb.client;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.UIManager;

public class GUIClient extends javax.swing.JFrame {
    boolean isSelected = true;
    float volume = 0;
    PlayerController pc;
    public GUIClient() {
        initComponents();
        jsVolume.setMinimum(0);
        jsVolume.setMaximum(100);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbPlayPause = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jsVolume = new javax.swing.JSlider();

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jbPlayPause, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jsVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(44, 44, 44)
                .addComponent(jbPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jsVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbPlayPauseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbPlayPauseMouseClicked
        if (isSelected) {
            isSelected = false;
            jbPlayPause.setText("STOP ⏹");
            try{
                //https://gamepedia.cursecdn.com/dota2_gamepedia/f/fb/Vo_nyx_assassin_nyx_laugh_06.mp3
           pc = new PlayerController("https://download.mp3free-is.fun/k/Jimmy-Eat-World-The-Middle.mp3",false);
            pc.start();
        } catch (NullPointerException ex) {
                
        } 
        } else {
             pc.close();
            isSelected = true;
            jbPlayPause.setText("PLAY ▶");
            
        }
    }//GEN-LAST:event_jbPlayPauseMouseClicked

    private void jsVolumeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jsVolumeStateChanged
Mixer.Info [] mixers = AudioSystem.getMixerInfo();  
System.out.println("There are " + mixers.length + " mixer info objects");  
for (Mixer.Info mixerInfo : mixers)  
{  
    System.out.println("Mixer name: " + mixerInfo.getName());  
    Mixer mixer = AudioSystem.getMixer(mixerInfo);  
    Line.Info [] lineInfos = mixer.getTargetLineInfo(); // target, not source  
    for (Line.Info lineInfo : lineInfos)  
    {  
        System.out.println("  Line.Info: " + lineInfo);  
        Line line = null;  
        boolean opened = true;  
        try  
        {  
            line = mixer.getLine(lineInfo);  
            opened = line.isOpen() || line instanceof Clip;  
            if (!opened)  
            {  
                line.open();  
            }
           float  v1 = jsVolume.getValue();
           volume = v1/100;
            FloatControl volCtrl = (FloatControl)line.getControl(FloatControl.Type.VOLUME);
            volCtrl.setValue(volume);
            System.out.println("    volCtrl.getValue() = " + volCtrl.getValue());  
        }  
        catch (LineUnavailableException e)  
        {  
            e.printStackTrace();  
        }  
        catch (IllegalArgumentException iaEx)  
        {  
            System.out.println("    " + iaEx);  
        }  
        finally  
        {  
            if (line != null && !opened)  
            {  
                line.close();  
            }  
        }  
    }  
} 
        System.out.println("var" + volume);
        System.out.println("Volume atual: " + jsVolume.getValue()/100);
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
        try{
            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        }catch(Exception e){
            
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
    private javax.swing.JSlider jsVolume;
    // End of variables declaration//GEN-END:variables
}
