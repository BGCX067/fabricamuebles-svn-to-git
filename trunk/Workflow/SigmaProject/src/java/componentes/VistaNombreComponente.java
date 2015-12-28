/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package componentes;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.outputpanel.OutputPanel;

/**
 *
 * @author Zaba
 */
@ManagedBean
@RequestScoped
public class VistaNombreComponente implements java.io.Serializable {

    private org.primefaces.component.outputpanel.OutputPanel panelDireccion;
    private org.primefaces.component.outputpanel.OutputPanel panelTelefono;
    private org.primefaces.component.datatable.DataTable listadoDirecciones;
    private org.primefaces.component.datatable.DataTable listadoTelefonos;

    public DataTable getListadoTelefonos() {
        return listadoTelefonos;
    }

    public void setListadoTelefonos(DataTable listadoTelefonos) {
        this.listadoTelefonos = listadoTelefonos;
    }

    public DataTable getListadoDirecciones() {
        return listadoDirecciones;
    }

    public void setListadoDirecciones(DataTable listadoDirecciones) {
        this.listadoDirecciones = listadoDirecciones;
    }

    public OutputPanel getPanelDireccion() {
        return panelDireccion;
    }

    public void setPanelDireccion(OutputPanel panelDireccion) {
        this.panelDireccion = panelDireccion;
    }

    public OutputPanel getPanelTelefono() {
        return panelTelefono;
    }

    public void setPanelTelefono(OutputPanel panelTelefono) {
        this.panelTelefono = panelTelefono;
    }
}
