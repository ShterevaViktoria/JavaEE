package bg.mysite.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import bg.mysite.entity.base.BaseDomainObject;

@Entity
@Table(name = "FOLLOWS")
public class FollowsModel extends BaseDomainObject {
	private static final long serialVersionUID = 1L;

	private UserModel user;
	private Date date;
	private UserModel follower;

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne
	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JoinColumn(name = "follower_user_id", referencedColumnName = "id")
	@ManyToOne
	public UserModel getFollower() {
		return follower;
	}

	public void setFollower(UserModel follower) {
		this.follower = follower;
	}

}
