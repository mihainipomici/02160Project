package com.project.GUI;

import com.project.dto.Application;
import com.project.dto.ResponseObject;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Miguel
 */
public class LogIn extends javax.swing.JFrame {

    /**
     * Creates new form LogIn
     */
    public LogIn() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        fieldName = new JTextField();
        fieldPassword = new JPasswordField();
        errorLabel = new JLabel();
        // Variables declaration - do not modify//GEN-BEGIN:variables
        JButton confirm = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log-in");

        jLabel1.setFont(new Font("Tahoma", Font.PLAIN, 13)); // NOI18N
        jLabel1.setText("Name:");

        jLabel2.setFont(new Font("Tahoma", Font.PLAIN, 13)); // NOI18N
        jLabel2.setText("Password:");

        fieldName.addActionListener(this::jTextField1ActionPerformed);

        errorLabel.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 13)); // NOI18N
        errorLabel.setText("Invalid credentials");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        confirm.setText("Confirm");
        confirm.addActionListener(this::ConfirmActionPerformed);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(errorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(fieldPassword))
                        .addGap(64, 64, 64))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(errorLabel)
                    .addComponent(confirm))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextField1ActionPerformed
    
    static String loggedIn;
    
    void ConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmActionPerformed

        if (loggedIn.equals("client")){
            String username = fieldName.getText();
            String password = new String(fieldPassword.getPassword());


            ResponseObject responseObject = Application.login("Client", username, password, Controller.company);
            if (responseObject.getCode() == 140) {
            	Controller.activeUser = Controller.company.getClientDatabase().searchClient(username, "name").get(0);
                ClientMainMenu.newScreen();
                dispose();
            } else {
                errorLabel.setText(responseObject.getMessage());
                errorLabel.setVisible(true);
            }
        }
        else if (loggedIn.equals("company")){
            String username = fieldName.getText();
            String password = new String(fieldPassword.getPassword());



            ResponseObject responseObject = Application.login("Company", username, password, Controller.company);
            if (responseObject.getCode() == 140) {
            	Controller.activeUser = Controller.company;
                CompMainMenu.newScreen();
                dispose();
            } else {
                errorLabel.setText(responseObject.getMessage());
                errorLabel.setVisible(true);
            }
        }
}//GEN-LAST:event_ConfirmActionPerformed

    /**
     * 
     */
    public static void newScreen(String loginType) {
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
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LogIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        loggedIn = loginType;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new LogIn().setVisible(true));
    }

    JLabel errorLabel;
    JPasswordField fieldPassword;
    JTextField fieldName;
    // End of variables declaration//GEN-END:variables
}
