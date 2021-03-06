/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pantallas;

import clases.MuebleEstructura;
import clases.Trabajo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DanielRH
 */
public class PantallaTrabajos extends javax.swing.JFrame {

    /**
     * Creates new form PantallaTrabajos
     */
    public PantallaTrabajos() {
        initComponents();
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

        btnAgregarTrabajo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTrabajos = new javax.swing.JTable();
        btnSiguiente = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        btnEditarTrabajo = new javax.swing.JButton();
        txtTrabajoSeleccionado = new javax.swing.JTextField();
        btnEliminarTrabajo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        btnAgregarTrabajo.setText("Agregar Trabajo");
        btnAgregarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTrabajoActionPerformed(evt);
            }
        });

        tablaTrabajos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaTrabajos);

        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnAtras.setText("Atras");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        btnEditarTrabajo.setText("Modificar Trabajo");
        btnEditarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarTrabajoActionPerformed(evt);
            }
        });

        txtTrabajoSeleccionado.setEditable(false);

        btnEliminarTrabajo.setText("Eliminar Trabajo");
        btnEliminarTrabajo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTrabajoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTrabajoSeleccionado)
                        .addGap(18, 18, 18)
                        .addComponent(btnAtras)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSiguiente))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnAgregarTrabajo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnEditarTrabajo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnEliminarTrabajo))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarTrabajo)
                    .addComponent(btnEditarTrabajo)
                    .addComponent(btnEliminarTrabajo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSiguiente)
                    .addComponent(btnAtras)
                    .addComponent(txtTrabajoSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        PantallaPiezas pantP=new PantallaPiezas();
        pantP.setMuebleActual(muebleActual);
        pantP.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
         // TODO add your handling code here:
        PantallaFinalizacionNuevaEstructura pantF=new PantallaFinalizacionNuevaEstructura();
        pantF.setMuebleActual(muebleActual);
        pantF.setVisible(true);
        this.dispose();
}//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnAgregarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTrabajoActionPerformed
        // TODO add your handling code here:
        PantallaAgregarTrabajo pantAT=new PantallaAgregarTrabajo();
        pantAT.setFramePadre(this);
        pantAT.setVisible(true);
    }//GEN-LAST:event_btnAgregarTrabajoActionPerformed

    private void btnEditarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarTrabajoActionPerformed
        // TODO add your handling code here:
        if(txtTrabajoSeleccionado.getText().compareTo("")==0)
        {
            JOptionPane.showMessageDialog(getContentPane(),"No se seleccionó ningún Trabajo","Error",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            Trabajo trabajoSeleccionado=null;
            LinkedList listaTrabajos=muebleActual.getTrabajos();
            Iterator iter = listaTrabajos.iterator();
            while(iter.hasNext())
            {
                Trabajo aux = (Trabajo)iter.next();
                if(aux.getNombre().compareTo(txtTrabajoSeleccionado.getText())==0)
                {
                    trabajoSeleccionado=aux;
                }
            }
            PantallaAgregarTrabajo pantAT=new PantallaAgregarTrabajo();
            pantAT.setFramePadre(this);
            pantAT.setTrabajoActual(trabajoSeleccionado);
            muebleActual.getTrabajos().remove(trabajoSeleccionado);
            pantAT.setVisible(true);
        }
    }//GEN-LAST:event_btnEditarTrabajoActionPerformed

    private void btnEliminarTrabajoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTrabajoActionPerformed
        // TODO add your handling code here:
        if(txtTrabajoSeleccionado.getText().compareTo("")==0)
        {
            JOptionPane.showMessageDialog(getContentPane(),"No se seleccionó ningún Trabajo","Error",JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            Trabajo trabajoSeleccionado=null;
            LinkedList listaTrabajos=muebleActual.getTrabajos();
            Iterator iter = listaTrabajos.iterator();
            while(iter.hasNext())
            {
                Trabajo aux = (Trabajo)iter.next();
                if(aux.getNombre().compareTo(txtTrabajoSeleccionado.getText())==0)
                {
                    trabajoSeleccionado=aux;
                }
            }
            muebleActual.getTrabajos().remove(trabajoSeleccionado);
            actualizarTablaTrabajos();
        }
    }//GEN-LAST:event_btnEliminarTrabajoActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaTrabajos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaTrabajos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaTrabajos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaTrabajos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new PantallaTrabajos().setVisible(true);
            }
        });
    }
    
    public void setMuebleActual(MuebleEstructura mueble)
    {
        muebleActual=mueble;
        if(muebleActual==null)
        {
            muebleActual=new MuebleEstructura();
        }
        actualizarTablaTrabajos();
    }
    
    public MuebleEstructura getMuebleActual()
    {
        if(muebleActual==null)
        {
            muebleActual=new MuebleEstructura();
        }
        return muebleActual;
    }
    
    public void agregarNuevoTrabajo(Trabajo nuevo)
    {
        if(muebleActual==null)
        {
            muebleActual=new MuebleEstructura();
        }
        muebleActual.getTrabajos().add(nuevo);
        actualizarTablaTrabajos();
    }
    
    private void actualizarTablaTrabajos()
    {
        LinkedList listaTrabajos=muebleActual.getTrabajos();
        String nombresColumnas[] = {"Trabajos"};
        trabajosTablaModel = new DefaultTableModel(nombresColumnas,0);
        Iterator iter = listaTrabajos.iterator();
        while(iter.hasNext())
        {
            Trabajo aux = (Trabajo)iter.next();
            String datosFila[] = {aux.getNombre()};
            trabajosTablaModel.addRow(datosFila);
            
        }
        tablaTrabajos.setModel(trabajosTablaModel);
    }
    
    private void cargarMouseListener()
    {
        MouseAdapter adaptadorMouse=new MouseAdapter()
           {@Override
           public void mouseClicked(MouseEvent e) 
              {
                 int fila = tablaTrabajos.rowAtPoint(e.getPoint());
                 int columna = tablaTrabajos.columnAtPoint(e.getPoint());
                 if ((fila > -1) && (columna > -1))
                 {
                     txtTrabajoSeleccionado.setText(tablaTrabajos.getModel().getValueAt(fila, 0)+"");
                 }
              }
           };
        
        tablaTrabajos.addMouseListener(adaptadorMouse);
    }
    
    private DefaultTableModel trabajosTablaModel;
    private MuebleEstructura muebleActual;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarTrabajo;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnEditarTrabajo;
    private javax.swing.JButton btnEliminarTrabajo;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaTrabajos;
    private javax.swing.JTextField txtTrabajoSeleccionado;
    // End of variables declaration//GEN-END:variables
}
