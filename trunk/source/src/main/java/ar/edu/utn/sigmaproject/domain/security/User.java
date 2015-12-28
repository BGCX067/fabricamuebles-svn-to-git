package ar.edu.utn.sigmaproject.domain.security;

import ar.edu.utn.sigmaproject.domain.IdentificableEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends IdentificableEntity implements Serializable {

    private static final long serialVersionUID = 771480672471939569L;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private List<Role> roles = new ArrayList<Role>();

    public User() {
        super();
    }

    @Transient
    public String getCompleteName() {
        return lastName + ", " + firstName;
    }

    @Column(length = 255)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(length = 255)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Column(length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ForeignKey(name = "fk_user_role_user", inverseName = "fk_user_role_role")
    @OrderColumn(name = "user_role_idx")
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", uid=" + uid + ", firstName=" + firstName
                + ", lastName=" + lastName + ", username=" + username
                + ", password=" + password + ", email=" + email + ", roles="
                + roles + "]";
    }
}
