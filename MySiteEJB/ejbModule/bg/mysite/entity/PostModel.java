package bg.mysite.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import bg.mysite.entity.base.BaseDomainObject;

@Entity
@Table(name = "POSTS")
public class PostModel extends BaseDomainObject {
	private static final long serialVersionUID = 1L;
	private String content;
	private Date createdDate;
	private UserModel author;
	private byte[] image;
	private Long numberComments;
	private Long numberLikes;
	private Long numberReposts;
	
	public PostModel() {
	}

	
	public PostModel(Long id,String content, Date createdDate, UserModel author, byte[] image, Long numberReposts,Long numberLikes,Long numberComments) {
		this.id = id;
		this.content = content;
		this.createdDate = createdDate;
		this.author = author;
		this.image = image;
		this.numberReposts = numberReposts;
		this.numberLikes=numberLikes;
		this.numberComments=numberComments;
		
	}


	@Column(name = "content", length = 4000, nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@JoinColumn(name = "author_id", referencedColumnName = "id")
	@ManyToOne
	public UserModel getAuthor() {
		return author;
	}

	public void setAuthor(UserModel author) {
		this.author = author;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
	@Transient
	public Long getNumberComments() {
		return numberComments;
	}

	public void setNumberComments(Long numberComments) {
		this.numberComments = numberComments;
	}
	@Transient
	public Long getNumberLikes() {
		return numberLikes;
	}

	public void setNumberLikes(Long numberLikes) {
		this.numberLikes = numberLikes;
	}
	@Transient
	public Long getNumberReposts() {
		return numberReposts;
	}

	public void setNumberReposts(Long numberReposts) {
		this.numberReposts = numberReposts;
	}
}
