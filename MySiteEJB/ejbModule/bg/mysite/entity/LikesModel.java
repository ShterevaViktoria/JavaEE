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
@Table(name = "LIKES")
public class LikesModel extends BaseDomainObject {
	private static final long serialVersionUID = 1L;
	private UserModel userLikedPost;
	private Date datedLiked;
	private PostModel postLiked;

	public LikesModel() {
	}

	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne
	public UserModel getUserLikedPost() {
		return userLikedPost;
	}

	public void setUserLikedPost(UserModel userLikedPost) {
		this.userLikedPost = userLikedPost;
	}

	@Column(name = "date_liked")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDatedLiked() {
		return datedLiked;
	}

	public void setDatedLiked(Date datedLiked) {
		this.datedLiked = datedLiked;
	}

	@JoinColumn(name = "post_id", referencedColumnName = "id")
	@ManyToOne
	public PostModel getPostLiked() {
		return postLiked;
	}

	public void setPostLiked(PostModel postLiked) {
		this.postLiked = postLiked;
	}

}
