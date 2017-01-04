package bg.mysite.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import bg.mysite.entity.base.BaseDomainObject;

@Entity
@Table(name = "TAGS")
public class TagModel extends BaseDomainObject {
	private static final long serialVersionUID = 1L;
	private String name;
	private PostModel post;

	
	public TagModel() {
	}

	public TagModel(String name,PostModel post) {
		this.name = name;
		this.post=post;
		
	}
	
	@Column(name = "content", length = 1000, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JoinColumn(name = "post_id", referencedColumnName = "id")
	@ManyToOne
	public PostModel getPost() {
		return post;
	}

	public void setPost(PostModel post) {
		this.post = post;
	}
}
