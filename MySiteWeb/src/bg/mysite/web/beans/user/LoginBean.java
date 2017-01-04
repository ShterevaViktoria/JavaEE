package bg.mysite.web.beans.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import bg.mysite.entity.UserModel;
import bg.mysite.service.userservice.UserService;
import bg.mysite.web.utils.GeneralUtils;
import bg.mysite.web.utils.MessageUtils;

/**
 * The purpose of this class is to provide backend functionality of the login
 */
@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;

	// beans.xml need to be added in WEB-INF in order CDI to work
	@Inject
	private HttpServletRequest request;

	private String username;
	private String password;
	@EJB
	UserService userService;

	private static final String PROFILE_PAGE_REDIRECT = "/page/user/profile?faces-redirect=true&includeViewParams=true&username=";
	private static final String LOGIN_PAGE_REDIRECT = "/page/user/login?faces-redirect=true";
	private static final String REGISTRATION_PAGE_REDIRECT = "/page/user/createUser?faces-redirect=true";

	@PostConstruct
	public void init() {
	}
	  public void buttonAction(ActionEvent actionEvent) {
	        addMessage("Welcome to Primefaces!!");
	    }
	     
	    public void addMessage(String summary) {
	        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
	        FacesContext.getCurrentInstance().addMessage(null, message);
	    }

	public String login() {

		String cryptedPassword = GeneralUtils.encodeSha256Password(password);

		UserModel userModel = userService.loginUser(username, cryptedPassword);

		if (null == userModel) {
			MessageUtils.addErrorMessage("login.error.invalid.credentials");

			return "";
		} else {
			request.getSession().setAttribute("LOGGED_USER", userModel);
			return PROFILE_PAGE_REDIRECT+userModel.getUsername();
		}
	}

	public String logout() {
		request.getSession().invalidate();
		return LOGIN_PAGE_REDIRECT;
	}

	public String registerAction() {
		return REGISTRATION_PAGE_REDIRECT;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}