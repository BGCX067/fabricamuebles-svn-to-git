/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pantallas;

import clases.GestorGrabarLeerDelDisco;
import clases.Maquina;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DanielRH
 */
public class PantallaAgregarMaquina extends javax.swing.JDialog {

    /**
     * Creates new form PantallaAgregarMaquina
     */
    public PantallaAgregarMaquina(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarTabla();
        cargarMouseListener();
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
        tablaMaquinas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtNuevaMaquina = new javax.swing.JTextField();
        btnNueva = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtSeleccionada = new javax.swing.JTextField();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaMaquinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaMaquinas);

        jLabel1.setText("Nueva Maquina:");

        txtNuevaMaquina.setEnabled(false);

        btnNueva.setText("Agregar");
        btnNueva.setEnabled(false);
        btnNueva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaActionPerformed(evt);
            }
        });

        jLabel2.setText("Seleccionada:");

        txtSeleccionada.setEditable(false);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNuevaMaquina)
                                .addGap(18, 18, 18)
                                .addComponent(btnNueva)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSeleccionada)
                        .addGap(18, 18, 18)
                        .addComponent(btnAceptar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNuevaMaquina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNueva))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevaActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        if(txtSeleccionada.getText().compareTo("")!=0)
        {
            PantallaAgregarTrabajo padre=(PantallaAgregarTrabajo) this.getParent();
            Maquina nueva=new Maquina();
            nueva.setNombre(txtSeleccionada.getText());
            padre.agregarNuevaMaquina(nueva);
        }
        this.dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaAgregarMaquina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaAgregarMaquina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaAgregarMaquina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaAgregarMaquina.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                PantallaAgregarMaquina dialog = new PantallaAgregarMaquina(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    private void cargarTabla()
    {
        GestorGrabarLeerDelDisco manejador=new GestorGrabarLeerDelDisco();
        LinkedList listaHerramientas=manejador.obtenerLista(new Maquina());
        String nombresColumnas[] = {"Maquinas"};
        maquinasTablaModel = new DefaultTableModel(nombresColumnas,0);
        Iterator iter = listaHerramientas.iterator();
        while(iter.hasNext())
        {
            Maquina aux = (Maquina)iter.next();
            String datosFila[] = {aux.getNombre()};
            maquinasTablaModel.addRow(datosFila);
            
        }
        tablaMaquinas.setModel(maquinasTablaModel);
    }
    
    private void cargarMouseListener()
    {
        MouseAdapter adaptadorMouse=new MouseAdapter()
           {@Override
           public void mouseClicked(MouseEvent e) 
              {
                 int fila = tablaMaquinas.rowAtPoint(e.getPoint());
                 int columna = tablaMaquinas.columnAtPoint(e.getPoint());
                 if ((fila > -1) && (columna > -1))
                 {
                     txtSeleccionada.setText(tablaMaquinas.getModel().getValueAt(fila, 0)+"");
//                     herramientaSeleccionada=tablaHerramientas.getModel().getValueAt(fila, 0)+"";
//                     pacienteSeleccionado.setDni(Integer.parseInt(tablaEstructuras.getModel().getValueAt(fila, 0)+""));
//                     pacienteSeleccionado.setApellidoNombre(tablaEstructuras.getModel().getValueAt(fila, 1)+"");
                 }
              }
           };
        
        tablaMaquinas.addMouseListener(adaptadorMouse);
    }
    
    DefaultTableModel maquinasTablaModel;
    private Maquina maquinaSeleccionada;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnNueva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaMaquinas;
    private javax.swing.JTextField txtNuevaMaquina;
    private javax.swing.JTextField txtSeleccionada;
    // End of variables declaration//GEN-END:variables
}
