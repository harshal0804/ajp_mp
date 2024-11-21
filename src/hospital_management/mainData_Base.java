/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hospital_management;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author harshalgadre
 */
public class mainData_Base extends javax.swing.JFrame {

    /**
     * Creates new form mainData_Base
     */
    public mainData_Base() {
        initComponents();
        displaydata();
    }

    
    
Connection con;
PreparedStatement ps; 

    public void displaydata(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/harshal1","root","");
            
            ps = con.prepareStatement("select * from master_data");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData r = rs.getMetaData();
            
            int col = r.getColumnCount();
            DefaultTableModel tf = (DefaultTableModel) jTable1.getModel();
            tf.setRowCount(0);
            
            while(rs.next())
            {
                Vector v = new Vector(); 
                for(int i = 0; i < col; i++)
                {
                   
v.add(rs.getString("patient_id"));
v.add(rs.getString("patient_name"));
v.add(rs.getString("patient_dob"));
v.add(rs.getString("patient_contact"));
v.add(rs.getString("patient_address"));
v.add(rs.getString("doctor_id"));
v.add(rs.getString("doctor_name"));
v.add(rs.getString("doctor_specialization"));
v.add(rs.getString("doctor_contact"));
 
 
v.add(rs.getString("fees"));
v.add(rs.getString("room_id"));
v.add(rs.getString("room_type"));
v.add(rs.getString("room_capacity"));
v.add(rs.getString("room_price"));

                    
                     
                    
                }
                tf.addRow(v);
                 
                
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(appointments.class.getName()).log(Level.SEVERE, null, ex);
        }
    
     
    }
    
    public void searchAllColumns() {
    String searchString = jTextField1.getText().trim(); // Get input from JTextField

    // Validate input
    if (searchString.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // SQL query
    String query = "SELECT * FROM master_data WHERE " +
            "master_id LIKE ? OR patient_id LIKE ? OR patient_name LIKE ? OR " +
            "patient_dob LIKE ? OR patient_contact LIKE ? OR patient_address LIKE ? OR " +
            "doctor_id LIKE ? OR doctor_name LIKE ? OR doctor_specialization LIKE ? OR " +
            "doctor_contact LIKE ? OR appointment_date LIKE ? OR disease LIKE ? OR " +
            "fees LIKE ? OR room_id LIKE ? OR room_type LIKE ? OR " +
            "room_capacity LIKE ? OR room_price LIKE ?";

    try (
        // Establish database connection
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/harshal1", "root", "");
        PreparedStatement preparedStatement = connection.prepareStatement(query)
    ) {
        // Set the parameters for each column
        for (int i = 1; i <= 17; i++) {
            preparedStatement.setString(i, "%" + searchString + "%");
        }

        // Execute the query
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            StringBuilder results = new StringBuilder();
            boolean found = false;

            // Iterate through the results
            while (resultSet.next()) {
                found = true;
                results.append("Master ID: ").append(resultSet.getString("master_id"))
                       .append(", Patient Name: ").append(resultSet.getString("patient_name"))
                       .append(", Doctor Name: ").append(resultSet.getString("doctor_name"))
                       .append(", Room Type: ").append(resultSet.getString("room_type"))
                       .append("\n");
            }

            // Display results
            if (found) {
                jTextArea1.setText(results.toString());
            } else {
                jTextArea1.setText("No results found for: " + searchString);
            }
        }
    } catch (SQLException ex) {
        // Handle SQL exceptions
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    
    
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTextField1.setText("jTextField1");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(374, 374, 374)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(483, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1312, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(373, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(12, 12, 12)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(413, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jButton1.addActionListener(e -> searchAllColumns());


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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainData_Base.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainData_Base.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainData_Base.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainData_Base.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new mainData_Base().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
