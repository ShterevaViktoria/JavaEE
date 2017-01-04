package bg.mysite.web.beans.home;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bg.mysite.entity.UserModel;
import bg.mysite.service.postservice.PostService;

@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean isAuthenticated;
	private String searchedText;
	private UserModel user;
	@Inject
	private HttpServletRequest request;
	@EJB
	PostService postService;

	private static final String LOGIN_PAGE_REDIRECT = "/page/user/login?faces-redirect=true";
	private static final String HOME_PAGE_REDIRECT = "/page/authenticated/home?faces-redirect=true";
	private static final String PROFILE_PAGE_REDIRECT = "/page/user/profile?faces-redirect=true&includeViewParams=true&username=";
	private static final String SEARCH_PAGE_REDIRECT = "/page/home/search?faces-redirect=true&includeViewParams=true&tag=";

	@PostConstruct
	public void init() {
		HttpSession session = request.getSession();
		UserModel loggedUser = (UserModel) session.getAttribute("LOGGED_USER");
		if (loggedUser == null) {
			this.setAuthenticated(false);
		} else {
			this.setUser(loggedUser);
			this.setAuthenticated(true);
		}
	}

	public String loginAction() {
		return LOGIN_PAGE_REDIRECT;
	}

	public String tt() {
		return this.getSearchedText();
	}

	public String homeAction() {
		return HOME_PAGE_REDIRECT;
	}

	public String profileAction() {
		return PROFILE_PAGE_REDIRECT +this.user.getUsername();
	}

	public String handleKeyEvent() {
		return SEARCH_PAGE_REDIRECT + this.searchedText;
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public String getSearchedText() {
		return searchedText;
	}

	public void setSearchedText(String searchedText) {
		this.searchedText = searchedText;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

}
