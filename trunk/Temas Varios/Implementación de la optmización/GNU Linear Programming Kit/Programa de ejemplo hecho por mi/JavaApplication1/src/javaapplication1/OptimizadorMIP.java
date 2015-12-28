/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.GlpkCallback;
import org.gnu.glpk.GlpkCallbackListener;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_iocp;
import org.gnu.glpk.glp_prob;
import org.gnu.glpk.glp_tree;

/**
 *
 * @author Zaba
 */
public class OptimizadorMIP implements Optimizador, GlpkCallbackListener {

    private Map<Double, Integer> resultadoCallback;
    private Double anchoSierra;

    public OptimizadorMIP() {
        anchoSierra = 0.0;
    }

    public OptimizadorMIP(Double anchoSierra) {
        this.anchoSierra = anchoSierra;
    }

    public Map<Map<Double, Integer>, Integer> resolver(Map<Double, Integer> terminos, Integer largo) {
        Map<Map<Double, Integer>, Integer> listaResultados = new LinkedHashMap<Map<Double, Integer>, Integer>();
        Map<Double, Integer> mapa = new LinkedHashMap<Double, Integer>();
        mapa.putAll(terminos);
        GlpkCallback.addListener(this);
        while (!mapa.isEmpty()) {
            Map<Double, Integer> res = optimizar(mapa, largo);

            Iterator<Entry<Double, Integer>> iterator = res.entrySet().iterator();
            Integer vecesPosibles = -1;
            while (iterator.hasNext()) {
                Double largoPieza = iterator.next().getKey();
                Integer cantidadCortes = res.get(largoPieza);
                if (cantidadCortes > 0 && (vecesPosibles == -1 || (mapa.get(largoPieza) / cantidadCortes) < vecesPosibles)) {
                    vecesPosibles = mapa.get(largoPieza) / cantidadCortes;
                }
            }

            listaResultados.put(res, vecesPosibles);

            iterator = res.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<Double, Integer> entry = iterator.next();
                Integer piezasAntesResolucion = mapa.get(entry.getKey());
                Integer piezasConsumidas = entry.getValue() * vecesPosibles;
                if (piezasAntesResolucion - piezasConsumidas > 0) {
                    mapa.put(entry.getKey(), piezasAntesResolucion - piezasConsumidas);
                } else {
                    mapa.remove(entry.getKey());
                }
            }
        }
        return listaResultados;
    }

    private Map<Double, Integer> optimizar(Map<Double, Integer> mapa, Integer largoMateriaPrima) {
        Iterator<Entry<Double, Integer>> iterator = mapa.entrySet().iterator();
        Double acumulador = 0.0;
        while (iterator.hasNext()) {
            Entry<Double, Integer> entrada = iterator.next();
            acumulador += entrada.getKey() * entrada.getValue();
        }
        Map<Double, Integer> res;
        if (acumulador > largoMateriaPrima && mapa.size() > 1) {
            res = new LinkedHashMap<Double, Integer>();
            glp_prob glp_create_prob = GLPK.glp_create_prob();
            GLPK.glp_set_prob_name(glp_create_prob, "Problema de ejemplo");
            GLPK.glp_add_cols(glp_create_prob, mapa.size());
            Set<Entry<Double, Integer>> entrySet = mapa.entrySet();
            iterator = entrySet.iterator();
            int i = 1;
            while (iterator.hasNext()) {
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
            resultadoCallback = new LinkedHashMap<Double, Integer>();
            GLPK.glp_intopt(glp_create_prob, glp_iocp);
            iterator = resultadoCallback.entrySet().iterator();
            Iterator<Entry<Double, Integer>> iteratorMapa = mapa.entrySet().iterator();
            while (iterator.hasNext()) {
                Integer numero = iterator.next().getValue();
                res.put(iteratorMapa.next().getKey(), numero);
            }
        } else {
            res = new OptimizadorSimplex(anchoSierra).resolver(mapa, largoMateriaPrima).keySet().iterator().next();
        }
        return res;
    }

    public void callback(glp_tree tree) {
        if (GLPK.glp_ios_reason(tree) == GLPK.GLP_IBINGO || GLPK.glp_ios_reason(tree) == GLPK.GLP_IROWGEN) {
            glp_prob problemaActual = GLPK.glp_ios_get_prob(tree);
            int numResultados = GLPK.glp_get_num_cols(problemaActual);
            for (int i = 1; i <= numResultados; i++) {
                resultadoCallback.put(new Double(i), new Integer((int) GLPK.glp_mip_col_val(problemaActual, i)));
            }
        }
    }
}
