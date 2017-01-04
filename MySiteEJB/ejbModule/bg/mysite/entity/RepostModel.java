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
@Table(name = "REPOSTS")
public class RepostModel extends BaseDomainObject {
	private static final long serialVersionUID = 1L;
	private UserModel author;
	private PostModel post;
	private Date date;
	
	
	public RepostModel() {
	}

	public RepostModel(UserModel user,PostModel post,Date date) {
		this.author = user;
		this.post = post;
		this.date = date;
	}
	
	

	@JoinColumn(name = "author_id", referencedColumnName = "id")
	@ManyToOne
	public UserModel getAuthor() {
		return author;
	}

	public void setAuthor(UserModel author) {
		this.author = author;
	}

	@JoinColumn(name = "post_id", referencedColumnName = "id")
	@ManyToOne
	public PostModel getPost() {
		return post;
	}

	public void setPost(PostModel post) {
		this.post = post;
	}

	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
