package bg.mysite.web.beans.user;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import bg.mysite.entity.PostModel;
import bg.mysite.entity.RepostModel;
import bg.mysite.entity.UserModel;
import bg.mysite.service.postservice.PostService;
import bg.mysite.service.userservice.UserService;
import bg.mysite.web.utils.GeneralUtils;
import bg.mysite.web.utils.MessageUtils;

@ManagedBean(name = "profileBean")
@SessionScoped
public class ProfileBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String CREATE_PAGE_REDIRECT = "/page/post/createPost?faces-redirect=true";
	private static final String EDIT_PAGE_REDIRECT = "/page/authenticated/editUser?faces-redirect=true";
	private static final String LOGIN_PAGE_REDIRECT = "/page/user/login?faces-redirect=true";
	private static final String PROFILE_PAGE_REDIRECT = "/page/user/profile?faces-redirect=true&includeViewParams=true&username=";
	private String username;
	private boolean isAuthor;
	private boolean isLogged;
	@Inject
	HttpServletRequest request;

	@EJB
	UserService userService;
	@EJB
	PostService postService;

	private UserModel user;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
		} else {
			this.setUsername(context.getExternalContext().getRequestParameterMap().get("username"));
		}

		UserModel authorUser = userService.findByUserName(username);
		if (authorUser != null) {
			this.setUser(authorUser);
			UserModel loggedUser = (UserModel) request.getSession().getAttribute("LOGGED_USER");
			if (loggedUser != null) {
				this.setLogged(true);
				if (loggedUser.getId() == authorUser.getId()) {
					this.setAuthor(true);
				}
				else {
					this.setAuthor(false);
				}
			} else {
				this.setAuthor(false);
			}
		}
	}

	public List<PostModel> findAllPostsByUser() {
		return postService.findAllPosts(user);

	}

	public List<PostModel> findAllReposts() {
		return postService.findRepost(user);
	}

	public List<PostModel> findAllLikes() {
		return postService.findLikedPosts(user);
	}

	public List<UserModel> findFollowes() {
		return userService.findFollowerUsers(user);
	}

	public List<UserModel> findFollowesYou() {
		return userService.findFollowYouUsers(user);
	}

	public String createAction() {
		return CREATE_PAGE_REDIRECT;
	}

	public String editAction() {
		return EDIT_PAGE_REDIRECT;
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

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAuthor() {
		return isAuthor;
	}

	public void setAuthor(boolean isAuthor) {
		this.isAuthor = isAuthor;
	}

	public boolean isLogged() {
		return isLogged;
	}

	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}

}
