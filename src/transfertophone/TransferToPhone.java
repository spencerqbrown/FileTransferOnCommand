/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfertophone;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.stream.Stream;
import javax.swing.JFileChooser;

/**
 *
 * @author Spencer
 */
public class TransferToPhone extends javax.swing.JFrame {
    
    private ArrayList<File> files;
    private File destination;
    JFileChooser sourceChooser;
    JFileChooser destinationChooser;
   

    /**
     * Creates new form TransferToPhone
     */
    public TransferToPhone() {
        initComponents();      
        
        files = new ArrayList<>();
        
        sourceChooser = new JFileChooser();
        sourceChooser.setCurrentDirectory(new File("C:\\temp"));
        sourceChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        sourceChooser.setMultiSelectionEnabled(true);
        
        destinationChooser = new JFileChooser();
        destinationChooser.setCurrentDirectory(new File("C:\\temp"));
        destinationChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectSourceButton = new javax.swing.JButton();
        messageLabel = new javax.swing.JLabel();
        transferButton = new javax.swing.JButton();
        selectDestinationButton = new javax.swing.JButton();
        transferLabel = new javax.swing.JLabel();
        destLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        selectSourceButton.setText("Select source...");
        selectSourceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSourceButtonActionPerformed(evt);
            }
        });

        transferButton.setText("Transfer");
        transferButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transferButtonActionPerformed(evt);
            }
        });

        selectDestinationButton.setText("Select destination...");
        selectDestinationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectDestinationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectSourceButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(messageLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(transferButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(transferLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(selectDestinationButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(destLabel)))
                .addContainerGap(257, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectSourceButton)
                    .addComponent(messageLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectDestinationButton)
                    .addComponent(destLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transferButton)
                    .addComponent(transferLabel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectSourceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSourceButtonActionPerformed
        int returnValue = sourceChooser.showOpenDialog(this);
        File sourcePath;
        File[] selectedFiles;
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFiles = sourceChooser.getSelectedFiles();
            for (File f: selectedFiles) {
                sourcePath = f;
                System.out.println(sourcePath);
                if (!files.contains(sourcePath)) {
                    files.add(sourcePath);
                }
            }
            if (selectedFiles.length == 1) {
                messageLabel.setText("File added to sources");
            } else {
                messageLabel.setText("Files added to sources");
            }   
        } else {
            messageLabel.setText("No file or directory selected");
        }
    }//GEN-LAST:event_selectSourceButtonActionPerformed

    private void selectDestinationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectDestinationButtonActionPerformed
        int returnValue = destinationChooser.showOpenDialog(this);
        File destPath;
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            destPath = destinationChooser.getSelectedFile();
            System.out.println(destPath);
            destination = destPath;
            destLabel.setText("Destination directory set");
        } else {
            destLabel.setText("No destination directory selected");
        }
    }//GEN-LAST:event_selectDestinationButtonActionPerformed

    private void transferButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transferButtonActionPerformed
        moveFiles();
    }//GEN-LAST:event_transferButtonActionPerformed

    private void moveFiles() {
        File newDir;
        if (files.size() == 0) {
            transferLabel.setText("No sources selected");
        } else if (destination == null) {
            transferLabel.setText("No destination selected");
        } else {
            for (File f: files) {
                univCopy(f, destination);
            }
            transferLabel.setText("Files successfully transferred");
        }
        
    }
    
    private void folderCopy(Path source, Path dest) {
        try {
            Files.walk(source).forEach(p -> fileCopy(p, dest.resolve(source.relativize(p))));
        } catch (IOException ioe) {
            transferLabel.setText("Failed to transfer");
        }
    }
    
    private void fileCopy(Path source, Path dest) {
        System.out.println(source);
        try {
            Files.copy(source, dest.resolve(source.getFileName()), REPLACE_EXISTING);
        } catch (IOException ioe) {
            transferLabel.setText("Failed to transfer");
        }
    }
    
    private void univCopy(File source, File dest) {
        if (source.isDirectory()) {
            String[] files;
            File newDir = new File(destination.getAbsolutePath() + "\\" + source.getName());
            System.out.println(newDir.getAbsolutePath());
            newDir.mkdir();
            files = source.list();
            File sourceFile;
            for (String f: files) {
                sourceFile = new File(source, f);
                univCopy(sourceFile, newDir);
            }
        } else {
            try {
                Files.copy(source.toPath(), dest.toPath().resolve(source.getName()), REPLACE_EXISTING);
            } catch (IOException ioe) {
                transferLabel.setText("Failed to transfer");
            }
        }
    }
    
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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TransferToPhone.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransferToPhone.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransferToPhone.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransferToPhone.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransferToPhone().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel destLabel;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JButton selectDestinationButton;
    private javax.swing.JButton selectSourceButton;
    private javax.swing.JButton transferButton;
    private javax.swing.JLabel transferLabel;
    // End of variables declaration//GEN-END:variables

    
}
