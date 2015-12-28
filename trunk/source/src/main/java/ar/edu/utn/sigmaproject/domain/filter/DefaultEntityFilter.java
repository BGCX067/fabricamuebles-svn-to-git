package ar.edu.utn.sigmaproject.domain.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultEntityFilter<T> implements Serializable, EntityFilter<T> {

	private static final long serialVersionUID = 1096996033538138632L;
	private List<SortCriteria> sortCriterias = new ArrayList<SortCriteria>();
	private List<String> fetchList = new ArrayList<String>();

	@Override
    public EntityFilter<T> addSort(final SortCriteria sort) {
		this.sortCriterias.add(sort);
		return this;
	}

	@Override
    public EntityFilter<T> ddFetch(String fetch) {
		this.fetchList.add(fetch);
		return this;
	}

	@Override
    public List<SortCriteria> getSortCriterias() {
		return Collections.unmodifiableList(sortCriterias);
	}

	@Override
    public List<String> getFetchList() {
		return Collections.unmodifiableList(fetchList);
	}
}
