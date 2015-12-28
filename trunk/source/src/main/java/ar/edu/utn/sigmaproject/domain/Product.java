package ar.edu.utn.sigmaproject.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorColumn(name="kind")
public abstract class Product extends IdentificableEntity {

	private static final long serialVersionUID = -3003105084104940600L;
	
	private String name;
	private String description;
	private byte[] photo;
    private List<Supplier> suppliers;

    @Transient
    public String getPrintableName() {
        return getName();
    }
	
	@Column
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
    @Length(max = 255)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column
	public byte[] getPhoto() {
		return photo;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

    @ManyToMany
    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}
