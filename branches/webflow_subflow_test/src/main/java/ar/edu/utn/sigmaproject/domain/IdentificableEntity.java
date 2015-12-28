package ar.edu.utn.sigmaproject.domain;

import org.hibernate.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public abstract class IdentificableEntity implements Serializable {

	private static final long serialVersionUID = -3416377418451399286L;
	protected Long id;
	protected String uid = UUID.randomUUID().toString();
	private Date dateCreated;
	private Date lastModified;
	private int version = 0;

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ENT_GEN")
    @TableGenerator(name="ENT_GEN")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    @Column(nullable = false, length = 128)
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

    @Basic(optional = false)
    @Index(name = "index_dateCreated")
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

    @Basic(optional = false)
    @Index(name = "index_lastModified")
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

    @Version
    @Column(nullable = false)
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof IdentificableEntity)) {
			return false;
		}
		IdentificableEntity other = (IdentificableEntity) obj;
		if (uid == null) {
			if (other.uid != null) {
				return false;
			}
		} else if (!uid.equals(other.uid)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + ", uid=" + uid + "]";
	}
}
