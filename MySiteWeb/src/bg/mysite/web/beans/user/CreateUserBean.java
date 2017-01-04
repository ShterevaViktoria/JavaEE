package bg.mysite.web.beans.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import bg.mysite.entity.UserModel;
import bg.mysite.service.userservice.UserService;
import bg.mysite.web.utils.GeneralUtils;
import bg.mysite.web.utils.MessageUtils;

@ManagedBean(name = "createUserBean")
@ViewScoped
public class CreateUserBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	HttpServletRequest request;

	@EJB
	UserService userService;

	private UserModel user;
	private static final String PROFILE_PAGE_REDIRECT = "/page/user/profile?faces-redirect=true&includeViewParams=true&username=";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
			+ "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" + "(\\.[A-Za-z]{2,})$";
	private Pattern pattern;

	@PostConstruct
	public void init() {
		user = new UserModel();
	}

	public String createAction() {

		if (!validate()) {
			return null;
		}

		String cryptedPassword = GeneralUtils.encodeSha256Password(user.getPassword());
		user.setPassword(cryptedPassword);
		Date createdDate = new Date();
		user.setDate(createdDate);
		userService.save(user);

		return PROFILE_PAGE_REDIRECT+user.getUsername();
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	protected boolean validate() {
		boolean hasErrors = false;
		if (StringUtils.isBlank(user.getUsername())) {
			MessageUtils.addErrorMessage("error.required.username");
			hasErrors = true;
		}

		if (StringUtils.isBlank(user.getPassword())) {
			MessageUtils.addErrorMessage("error.required.password");
			hasErrors = true;
		}

		if (StringUtils.isBlank(user.getEmail())) {
			MessageUtils.addErrorMessage("error.required.email");
			hasErrors = true;
		}

		pattern = Pattern.compile(EMAIL_PATTERN);
		if (!pattern.matcher(user.getEmail()).matches()) {
			MessageUtils.addErrorMessage("error.invalid.email");
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
}