package ar.edu.utn.sigmaproject.domain.security;

import ar.edu.utn.sigmaproject.domain.IdentificableEntity;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role extends IdentificableEntity implements Serializable {

	private static final long serialVersionUID = 5274499807836158222L;
    private String name;
    private String description;
    private List<Permission> permissions = new ArrayList<Permission>();

    @Column(length = 60)
    @Length(max = 60)
    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    @Column(length = 1024)
    @Length(max = 1024)
    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
    }

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "perm_name"))
    @ForeignKey(name = "fk_role_perm_role", inverseName = "fk_role_perm_perm")
    @OrderColumn(name="role_perm_idx")
    public List<Permission> getPermissions() {
            return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
            this.permissions = permissions;
    }

    @Override
    public String toString() {
            return "Role [id=" + id + ", uid=" + uid + ", name=" + name
                            + ", description=" + description + ", permissions="
                            + permissions + "]";
    }

}
