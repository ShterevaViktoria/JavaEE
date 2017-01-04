package bg.mysite.web.beans.authenticated;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import bg.mysite.entity.CommentModel;
import bg.mysite.entity.PostModel;
import bg.mysite.entity.UserModel;
import bg.mysite.service.commentservice.CommentService;
import bg.mysite.service.postservice.PostService;
import bg.mysite.service.userservice.UserService;
import bg.mysite.web.utils.GeneralUtils;
import bg.mysite.web.utils.MessageUtils;

@ManagedBean(name = "commentBean")
@SessionScoped
public class CommentBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String COMMENT_PAGE_REDIRECT = "/page/authenticated/comment?faces-redirect=true&includeViewParams=true&post=";
	private String postId;
	private UserModel user;
	private PostModel post;
	private CommentModel comment;
	@Inject
	HttpServletRequest request;
	@EJB
	UserService userService;
	@EJB
	PostService postService;
	@EJB
	CommentService commentService;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
		} else {
			this.setPostId(context.getExternalContext().getRequestParameterMap().get("post"));
		}
		UserModel loggedUser = (UserModel) request.getSession().getAttribute("LOGGED_USER");
		if (loggedUser != null) {
			PostModel searchPost = postService.findById(Long.valueOf(postId));
			if (searchPost != null) {
				this.setUser(loggedUser);
				this.setPost(searchPost);
				this.setComment(new CommentModel());
			}
		}
	}

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String postId = context.getExternalContext().getRequestParameterMap().get("id");
			PostModel post = postService.findById(Long.valueOf(postId));
			return new DefaultStreamedContent(new ByteArrayInputStream(post.getImage()), "image/jpg");
		}
	}

	public StreamedContent getImageUser() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			String userId = context.getExternalContext().getRequestParameterMap().get("id");
			UserModel user = userService.findById(Long.valueOf(userId));
			return new DefaultStreamedContent(new ByteArrayInputStream(user.getImage()), "image/jpg");
		}
	}

	public String createAction() {

		if (!validate()) {
			return null;
		}

		comment.setAuthor(user);
		comment.setPost(post);
		Date createdDate = new Date();
		comment.setCreatedDate(createdDate);
		commentService.save(comment);

		return COMMENT_PAGE_REDIRECT + post.getId();
	}

	protected boolean validate() {
		boolean hasErrors = false;
		if (StringUtils.isBlank(comment.getContent())) {
			MessageUtils.addErrorMessage("error.required.content");
			hasErrors = true;
		}

		if (hasErrors) {
			return false;
		}

		return true;
	}

	/**
	 * Verifies if a error messages are present in the context
	 */
	public boolean hasErrors() {
		Iterator<FacesMessage> messages = FacesContext.getCurrentInstance().getMessages();
		for (; messages.hasNext();) {
			FacesMessage message = messages.next();
			if (message.getSeverity().compareTo(FacesMessage.SEVERITY_ERROR) == 0) {
				return true;
			}
		}

		return false;
	}

	public List<CommentModel> findAllComments() {
		return commentService.findByPostId(Long.valueOf(postId));
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public PostModel getPost() {
		return post;
	}

	public void setPost(PostModel post) {
		this.post = post;
	}

	public CommentModel getComment() {
		return comment;
	}

	public void setComment(CommentModel comment) {
		this.comment = comment;
	}
}
