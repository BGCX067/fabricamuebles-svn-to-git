/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pantallas;

import clases.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
public class PantallaAgregarPieza extends javax.swing.JDialog {

    /**
     * Creates new form PantallaAgragarPieza
     */
    public PantallaAgregarPieza(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarMouseListener();
        txtCantidad.addKeyListener(new KeyAdapter()
        {
            @Override
        public void keyTyped(KeyEvent e)
        {
            char caracter = e.getKeyChar();
            // Verificar si la tecla pulsada no es un digito
            if(((caracter < '0') ||
                (caracter > '9')) &&
                (caracter != '\b' /*corresponde a BACK_SPACE*/))
            {
                e.consume();  // ignorar el evento de teclado
            }
        }
        });
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
        tablaPiezas = new javax.swing.JTable();
        btnAceptar = new javax.swing.JButton();
        txtSeleccionada = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNuevaPieza = new javax.swing.JTextField();
        btnNueva = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        tablaPiezas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaPiezas);

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        txtSeleccionada.setEditable(false);

        jLabel1.setText("Seleccionada:");

        jLabel2.setText("Nueva Pieza:");

        txtNuevaPieza.setEnabled(false);

        btnNueva.setText("Agregar");
        btnNueva.setEnabled(false);

        jLabel3.setText("Cantidad:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNuevaPieza)
                        .addGap(18, 18, 18)
                        .addComponent(btnNueva))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantidad)
                        .addGap(18, 18, 18)
                        .addComponent(btnAceptar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSeleccionada)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNuevaPieza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNueva))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptar))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
        if(txtSeleccionada.getText().compareTo("")!=0&&txtCantidad.getText().compareTo("")!=0)
        {
            PantallaAgregarTrabajo padre=(PantallaAgregarTrabajo) this.getParent();
            PiezaConCantidad nuevoCant=new PiezaConCantidad();
            nuevoCant.setActual(new Pieza(txtSeleccionada.getText()));
            nuevoCant.setCantidad(Integer.parseInt(txtCantidad.getText()));
            padre.agregarNuevaPieza(nuevoCant);
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
            java.util.logging.Logger.getLogger(PantallaAgregarPieza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaAgregarPieza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaAgregarPieza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaAgregarPieza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                PantallaAgregarPieza dialog = new PantallaAgregarPieza(new javax.swing.JFrame(), true);
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
    
    private void actualizarTablaPiezasDelTrabajo(LinkedList listaPiezas)
    {
        String nombresColumnas[] = {"Piezas", "Cantidad"};
        piezasTablaModel = new DefaultTableModel(nombresColumnas,0);
        Iterator iter = listaPiezas.iterator();
        while(iter.hasNext())
        {
            Pieza aux = (Pieza)iter.next();
            String datosFila[] = {aux.getNombre(), aux.getCantidad()+""};
            piezasTablaModel.addRow(datosFila);
            
        }
        tablaPiezas.setModel(piezasTablaModel);
    }
    
    private void cargarMouseListener()
    {
        MouseAdapter adaptadorMouse=new MouseAdapter()
           {@Override
           public void mouseClicked(MouseEvent e) 
              {
                 int fila = tablaPiezas.rowAtPoint(e.getPoint());
                 int columna = tablaPiezas.columnAtPoint(e.getPoint());
                 if ((fila > -1) && (columna > -1))
                 {
                     txtSeleccionada.setText(tablaPiezas.getModel().getValueAt(fila, 0)+"");
                     txtCantidad.setText(tablaPiezas.getModel().getValueAt(fila, 1)+"");
                 }
              }
           };
        
        tablaPiezas.addMouseListener(adaptadorMouse);
    }
    
    public void setMuebleActual(MuebleEstructura mueble)
    {
        muebleActual=mueble;
        if(muebleActual==null)
        {
            muebleActual=new MuebleEstructura();
        }
        actualizarTablaPiezasDelTrabajo(muebleActual.getPartes());
    }
    
    private MuebleEstructura muebleActual;
    private DefaultTableModel piezasTablaModel;
    private Pieza piezaSeleccionada;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnNueva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaPiezas;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtNuevaPieza;
    private javax.swing.JTextField txtSeleccionada;
    // End of variables declaration//GEN-END:variables
}