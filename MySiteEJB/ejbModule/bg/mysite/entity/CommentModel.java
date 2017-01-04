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
@Table(name = "COMMENTS")
public class CommentModel extends BaseDomainObject {
	private static final long serialVersionUID = 1L;
	private PostModel post;
	private Date createdDate;
	private String content;
	private UserModel author;

	@JoinColumn(name = "post_id", referencedColumnName = "id")
	@ManyToOne
	public PostModel getPost() {
		return post;
	}

	public void setPost(PostModel post) {
		this.post = post;
	}

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "content", length = 4000, nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JoinColumn(name = "author_id", referencedColumnName = "id")
	@ManyToOne
	public UserModel getAuthor() {
		return author;
	}

	public void setAuthor(UserModel author) {
		this.author = author;
	}

}
