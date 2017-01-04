package bg.mysite.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import bg.mysite.entity.base.BaseDomainObject;

@Entity
@Table(name = "USERS")
public class UserModel extends BaseDomainObject {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String email;
	private String description;
	private Date date;
	private String role;
	private byte[] image;

	public UserModel() {
	}

	public UserModel(Long id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	@Column(name = "username", length = 45, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 200, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email", length = 45, nullable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	@Column(name = "description", length = 4000)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "role", length = 45)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof UserModel)) {
			return false;
		}
		UserModel other = (UserModel) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Users[ id=" + id + " ]";
	}

	

}
