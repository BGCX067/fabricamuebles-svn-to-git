/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_iocp;
import org.gnu.glpk.glp_prob;

/**
 *
 * @author Zaba
 */
public class OptimizadorSimplex implements Optimizador {

    private Double anchoSierra;

    public OptimizadorSimplex() {
        this.anchoSierra = 0.0;
    }

    public OptimizadorSimplex(Double anchoSierra) {
        this.anchoSierra = anchoSierra;
    }

    public Map<Map<Double, Integer>, Integer> resolver(Map<Double, Integer> terminos, Integer largo) {
        Map<Map<Double, Integer>, Integer> listaResultados = new LinkedHashMap<Map<Double, Integer>, Integer>();
        Map<Double, Integer> mapa = new LinkedHashMap<Double, Integer>();
        mapa.putAll(terminos);
        while (!mapa.isEmpty()) {
            Map<Double, Integer> res = optimizar(mapa, largo);
            if (listaResultados.containsKey(res)) {
                listaResultados.put(res, listaResultados.get(res) + 1);
            } else {
                listaResultados.put(res, 1);
            }
            Iterator<Entry<Double, Integer>> iterator = res.entrySet().iterator();

            // Rutina que redefine el mapa de terminos a la funcion de resolucion con
            // lo que falta de asignar. Borra aquellas terminoss cuya demanda ya haya sido
            // abastecida
            while (iterator.hasNext()) {
                Entry<Double, Integer> entry = iterator.next();
                Integer piezasAntesResolucion = mapa.get(entry.getKey());
                if (piezasAntesResolucion - entry.getValue() > 0) {
                    mapa.put(entry.getKey(), piezasAntesResolucion - entry.getValue());
                } else {
                    mapa.remove(entry.getKey());
                }
            }
        }
        return listaResultados;
    }

    private Map<Double, Integer> optimizar(Map<Double, Integer> mapa, Integer largoMateriaPrima) {
        glp_prob glp_create_prob = GLPK.glp_create_prob();
        GLPK.glp_set_prob_name(glp_create_prob, "Problema de ejemplo");
        GLPK.glp_add_cols(glp_create_prob, mapa.size());
        Set<Entry<Double, Integer>> entrySet = mapa.entrySet();
        Iterator<Entry<Double, Integer>> iterator = entrySet.iterator();
        int i = 1;
        while(iterator.hasNext()) {
            Entry<Double, Integer> next = iterator.next();
            GLPK.glp_set_col_name(glp_create_prob, i, "x" + i);
            Integer integer = next.getValue();
            if (integer.equals(0)) {
                GLPK.glp_set_col_bnds(glp_create_prob, i, GLPK.GLP_FX, 0, next.getValue().doubleValue());
            } else {
                GLPK.glp_set_col_bnds(glp_create_prob, i, GLPK.GLP_DB, 0, next.getValue().doubleValue());
            }
            GLPK.glp_set_col_kind(glp_create_prob, i++, GLPK.GLP_IV);
        }

        GLPK.glp_add_rows(glp_create_prob, 1);
        GLPK.glp_set_row_name(glp_create_prob, 1, "c1");
        GLPK.glp_set_row_bnds(glp_create_prob, 1, GLPK.GLP_UP, 0, largoMateriaPrima.doubleValue() + anchoSierra.doubleValue());
        SWIGTYPE_p_int new_intArray = GLPK.new_intArray(mapa.size() + 1);
        iterator = entrySet.iterator();
        i = 1;
        while (iterator.hasNext()) {
            iterator.next();
            GLPK.intArray_setitem(new_intArray, i, i);
            i++;
        }
        SWIGTYPE_p_double new_doubleArray = GLPK.new_doubleArray(mapa.size() + 1);
        iterator = entrySet.iterator();
        i = 1;
        while (iterator.hasNext()) {
            Entry<Double, Integer> next = iterator.next();
            GLPK.doubleArray_setitem(new_doubleArray, i++, next.getKey().doubleValue() + anchoSierra.doubleValue());
        }
        GLPK.glp_set_mat_row(glp_create_prob, 1, mapa.size(), new_intArray, new_doubleArray);

        GLPK.glp_set_obj_name(glp_create_prob, "z");
        GLPK.glp_set_obj_dir(glp_create_prob, GLPK.GLP_MAX);
        iterator = entrySet.iterator();
        i = 1;
        while (iterator.hasNext()) {
            Entry<Double, Integer> next = iterator.next();
            GLPK.glp_set_obj_coef(glp_create_prob, i++, next.getKey().doubleValue());
        }

        glp_iocp glp_iocp = new glp_iocp();
        GLPK.glp_init_iocp(glp_iocp);
        glp_iocp.setPresolve(GLPKConstants.GLP_ON);
        //System.out.println(GLPK.glp_intopt(glp_create_prob, glp_iocp));
        Map<Double, Integer> res = new LinkedHashMap<Double, Integer>();
        if (GLPK.glp_simplex(glp_create_prob, null) == 0) {
            int glp_get_num_cols = GLPK.glp_get_num_cols(glp_create_prob);
            iterator = mapa.entrySet().iterator();
            for (i = 1; i <= glp_get_num_cols; i++) {
                Double d = GLPK.glp_get_col_prim(glp_create_prob, i);
                res.put(iterator.next().getKey(), d.intValue());
            }
            System.out.println(GLPK.glp_get_row_prim(glp_create_prob, 1));
        }
        return res;
    }
}
