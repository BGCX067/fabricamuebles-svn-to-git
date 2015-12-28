package ar.edu.utn.sigmaproject.domain.filter;

import java.util.List;

/**
 * User: Gian Franco Zabarino
 * Date: 23/08/12
 */
public interface EntityFilter<T> {
    EntityFilter<T> addSort(SortCriteria sort);

    EntityFilter<T> ddFetch(String fetch);

    List<SortCriteria> getSortCriterias();

    List<String> getFetchList();
}
