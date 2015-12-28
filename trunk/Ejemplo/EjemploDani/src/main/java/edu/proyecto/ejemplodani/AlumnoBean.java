/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.proyecto.ejemplodani;

import edu.proyecto.ejemplodani.dao.AlumnoDao;
import edu.proyecto.ejemplodani.model.Alumno;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 *
 * @author Zaba
 */
@ManagedBean
@ViewScoped
public class AlumnoBean implements Serializable {
    @EJB
    private AlumnoDao alumnoDao;
    
    private Alumno alumno;
    private Boolean edit;
    private Boolean add;
    
    Logger logger = Logger.getLogger(getClass().getName());
    
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        Long id = (Long) flash.get("id");
        if (id == null) {
            add = true;
            alumno = new Alumno();
        } else {
            edit = true;
            alumno = alumnoDao.get(id);
        }
    }
    
    public String save() {
        logger.info("save");
        alumnoDao.save(alumno);
        return "index?faces-redirect=true";
    }
    
    public String insert() {
        logger.info("insert");
        alumnoDao.add(alumno);
        return "index?faces-redirect=true";
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Boolean getAdd() {
        return add;
    }

    public void setAdd(Boolean add) {
        this.add = add;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }
}
