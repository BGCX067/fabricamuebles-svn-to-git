/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Cristian
 */
@Entity
public class ComprobanteRelaciones implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idRemitoCompra")
    private Remito remitoCompra;
    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idRemitoVenta")
    private Remito remitoVenta;
    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idRemitoProduccion")
    private Remito remitoProduccion;
    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idFacturaCompra")
    private Factura facturaCompra;
    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idFacturaVenta")
    private Factura facturaVenta;
    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idOrdenDeCompra")
    private OrdenDeCompra ordenDeCompra;
    @ManyToOne(cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SELECT)
    @JoinColumn(name = "idOrdenDeProduccion")
    private OrdenDeProduccion ordenDeProduccion;

    public Long getId() {
        return id;
    }

    public OrdenDeCompra getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public void setOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }

    public OrdenDeProduccion getOrdenDeProduccion() {
        return ordenDeProduccion;
    }

    public void setOrdenDeProduccion(OrdenDeProduccion ordenDeProduccion) {
        this.ordenDeProduccion = ordenDeProduccion;
    }

    public Remito getRemitoCompra() {
        return remitoCompra;
    }

    public void setRemitoCompra(Remito remitoCompra) {
        this.remitoCompra = remitoCompra;
    }

    public Remito getRemitoVenta() {
        return remitoVenta;
    }

    public void setRemitoVenta(Remito remitoVenta) {
        this.remitoVenta = remitoVenta;
    }

    public Factura getFacturaCompra() {
        return facturaCompra;
    }

    public void setFacturaCompra(Factura facturaCompra) {
        this.facturaCompra = facturaCompra;
    }

    public Factura getFacturaVenta() {
        return facturaVenta;
    }

    public void setFacturaVenta(Factura facturaVenta) {
        this.facturaVenta = facturaVenta;
    }

    public void setRemitoProduccion(Remito remitoProduccion) {
        this.remitoProduccion = remitoProduccion;
    }

    public Remito getRemitoProduccion() {
        return this.remitoProduccion;
    }
}
