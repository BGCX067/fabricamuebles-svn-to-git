/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package optimizacion;

import entidades.Articulo;
import entidades.ArticuloPresentacion;
import entidades.Familia;
import entidades.Insumo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.gnu.glpk.*;

/**
 *
 * @author Zaba
 */
public class SeleccionadorDePresentacionesParaMinimoDesperdicioMIP implements SeleccionadorDePresentacionesParaMinimoDesperdicio, GlpkCallbackListener {
    private Map<Integer, Integer> resultadoCallback;
    private Boolean callbackLlamado = false;

    public static void main(String[] args) {
        SeleccionadorDePresentacionesParaMinimoDesperdicio seleccionadorDePresentacionesParaMinimoDesperdicio = new SeleccionadorDePresentacionesParaMinimoDesperdicioMIP();
        Articulo articulo = new Insumo();
        ((Insumo)articulo).setNombre("Agua Joya");
        Familia familia = new Familia();
        familia.setNombre("Agua");
        ArticuloPresentacion ap1 = new ArticuloPresentacion();
        ap1.setArticulo(articulo);
        ap1.setCantidad(new BigDecimal(100));
        ArticuloPresentacion ap2 = new ArticuloPresentacion();
        ap2.setArticulo(articulo);
        ap2.setCantidad(new BigDecimal(250));
        ArticuloPresentacion ap3 = new ArticuloPresentacion();
        ap3.setArticulo(articulo);
        ap3.setCantidad(new BigDecimal(500));
        Map<ArticuloPresentacion, Integer> resultado = seleccionadorDePresentacionesParaMinimoDesperdicio.devolverSeleccionesOptimas(new BigDecimal(1350), Arrays.asList(ap1, ap2, ap3));
        Iterator<Entry<ArticuloPresentacion, Integer>> iterator = resultado.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<ArticuloPresentacion, Integer> next = iterator.next();
            System.out.println("Presentación: " + next.getKey().getDescripcion() + "\nCantidad: " + next.getValue());
        }
    }

    /*
     * Quiero 1350 ml de agua
     * Queremos minimizar el desperdicio.
     *
     * Sea x1 la cantidad de presentaciones de 100 ml a reservar
     * Sea x2 la cantidad de presentaciones de 250 ml a reservar
     * Sea x3 la cantidad de presentaciones de 500 ml a reservar
     * Sea d1 la cantidad de presentaciones de 100 ml disponibles para reservar
     * Sea d2 la cantidad de presentaciones de 100 ml disponibles para reservar
     * Sea d3 la cantidad de presentaciones de 100 ml disponibles para reservar
     *
     * min z = x1 * 100 + x2 * 250 + x3 * 500 - 1350
     * s.a.:
     * x1 * 100 + x2 * 250 + x3 * 500 >= 1350
     * 0<=x1<=d1
     * 0<=x2<=d2
     * 0<=x3<=d3
     * y sean x1, x2, x3, enteros
     */

    public Map<ArticuloPresentacion, Integer> devolverSeleccionesOptimas(BigDecimal cantidadTotalNecesaria, List<ArticuloPresentacion> presentacionesDisponibles) {
        glp_prob problema = org.gnu.glpk.GLPK.glp_create_prob();
        GLPK.glp_set_prob_name(problema, "Cálculo de presentaciones para mínimo desperdicio.");
        GLPK.glp_add_cols(problema, presentacionesDisponibles.size());

        // 0<=x1<=d1
        // 0<=x2<=d2
        // 0<=x3<=d3
        // y sean x1, x2, x3, enteros
        int i = 1;
        for (ArticuloPresentacion articuloPresentacion : presentacionesDisponibles) {
            GLPK.glp_set_col_name(problema, i, articuloPresentacion.getDescripcion());
            if (articuloPresentacion.getArticulo().getFamilia().getStockDisponible().compareTo(BigDecimal.ZERO) > 0) { // si stock disponible es mayor a cero
                // pongo que sea mayor o igual a cero, y menor o igual a la cantidad disponible
                GLPK.glp_set_col_bnds(problema, i, GLPKConstants.GLP_DB, 0, articuloPresentacion.getArticulo().getFamilia().getStockDisponible().doubleValue());
            } else {
                // pongo que sea cero, porque no hay stock disponible
                GLPK.glp_set_col_bnds(problema, i, GLPKConstants.GLP_FX, 0, 0);
            }
            GLPK.glp_set_col_kind(problema, i, GLPKConstants.GLP_IV); // asigno que es una variable entera
            i++;
        }

        // s.a.:
        // x1 * 100 + x2 * 250 + x3 * 500 >= 1350
        GLPK.glp_add_rows(problema, 1);
        GLPK.glp_set_row_name(problema, 1, "c1");
        GLPK.glp_set_row_bnds(problema, 1, GLPKConstants.GLP_LO, cantidadTotalNecesaria.doubleValue(), 0); // pongo que sea mayor o igual a la cantidad necesaria

        SWIGTYPE_p_int arrayInt = GLPK.new_intArray(presentacionesDisponibles.size() + 1);
        for (i = 1; i <= presentacionesDisponibles.size(); i++) {
            GLPK.intArray_setitem(arrayInt, i, i);
        }
        SWIGTYPE_p_double arrayDouble = GLPK.new_doubleArray(presentacionesDisponibles.size() + 1);
        i = 1;
        for (ArticuloPresentacion articuloPresentacion : presentacionesDisponibles) {
            GLPK.doubleArray_setitem(arrayDouble, i++, articuloPresentacion.getCantidad().doubleValue());
        }
        GLPK.glp_set_mat_row(problema, 1, presentacionesDisponibles.size(), arrayInt, arrayDouble);

        // min z = x1 * 100 + x2 * 250 + x3 * 500 - 1350
        GLPK.glp_set_obj_name(problema, "z");
        GLPK.glp_set_obj_dir(problema, GLPKConstants.GLP_MIN);
        GLPK.glp_set_obj_coef(problema, 0, cantidadTotalNecesaria.doubleValue() * -1); // + (- 1350)
        i = 1;
        for (ArticuloPresentacion articuloPresentacion : presentacionesDisponibles) {
            GLPK.glp_set_obj_coef(problema, i++, articuloPresentacion.getCantidad().doubleValue());
        }
        glp_iocp glp_iocp = new glp_iocp();
        GLPK.glp_init_iocp(glp_iocp);
        glp_iocp.setPresolve(GLPKConstants.GLP_ON);
        resultadoCallback = new HashMap<Integer, Integer>();
        GLPK.glp_intopt(problema, glp_iocp);
        if (callbackLlamado.booleanValue() == false) {
            int glp_get_num_cols = GLPK.glp_get_num_cols(problema);
            for (i = 1; i <= glp_get_num_cols; i++) {
                Double d = GLPK.glp_mip_col_val(problema, i);
                resultadoCallback.put(i, new Integer((int)d.doubleValue()));
                //System.out.println("*************************** " + i + ": " + d);
            }
            //System.out.println(GLPK.glp_get_row_prim(problema, 1));
        }
        Map<ArticuloPresentacion, Integer> resultado = new HashMap<ArticuloPresentacion, Integer>();
        Iterator<Entry<Integer, Integer>> iterator = resultadoCallback.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<Integer, Integer> entrada = iterator.next();
            resultado.put(presentacionesDisponibles.get(entrada.getKey() - 1), entrada.getValue());
        }
        return resultado;
    }

    public void callback(glp_tree glp_tr) {
        callbackLlamado = true;
        if (GLPK.glp_ios_reason(glp_tr) == GLPK.GLP_IBINGO || GLPK.glp_ios_reason(glp_tr) == GLPK.GLP_IROWGEN) {
            glp_prob problemaActual = GLPK.glp_ios_get_prob(glp_tr);
            int numResultados = GLPK.glp_get_num_cols(problemaActual);
            for (int i = 1; i <= numResultados; i++) {
                resultadoCallback.put(i, new Integer((int) GLPK.glp_mip_col_val(problemaActual, i)));
                //System.out.println("*************************** " + i + ": " + GLPK.glp_mip_col_val(problemaActual, i));
            }
            //System.out.println("");
        }
    }

}
