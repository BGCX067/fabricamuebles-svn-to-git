package ar.edu.utn.sigmaproject.domain.filter;

import ar.edu.utn.sigmaproject.domain.Product;
import ar.edu.utn.sigmaproject.domain.Supplier;

/**
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
public class ProductFilter<E extends Product> extends DefaultEntityFilter<E> {
    private String name;
    private Supplier supplier;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
