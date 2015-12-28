package ar.edu.utn.sigmaproject.domain.filter;

public class SortCriteria {

	private String attribute;
	private Boolean asc;

    public SortCriteria(String attribute) {
        this(attribute, true);
    }

    public SortCriteria(String attribute, Boolean asc) {
        this.attribute = attribute;
        this.asc = asc;
    }

    public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Boolean getAsc() {
		return asc;
	}

	public void setAsc(Boolean asc) {
		this.asc = asc;
	}

}
