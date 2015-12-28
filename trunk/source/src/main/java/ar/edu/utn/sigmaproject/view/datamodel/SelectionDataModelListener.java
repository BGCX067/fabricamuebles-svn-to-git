package ar.edu.utn.sigmaproject.view.datamodel;


public interface SelectionDataModelListener<T> {

	void rowSelected(SelectionDataModelEvent<T> event);
	
	void rowUnselected(SelectionDataModelEvent<T> event);
	
	boolean isRowSelected(SelectionDataModelEvent<T> event);	
}
