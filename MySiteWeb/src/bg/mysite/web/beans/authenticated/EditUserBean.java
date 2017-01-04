package bg.mysite.web.beans.authenticated;

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
import org.primefaces.model.UploadedFile;

import bg.mysite.entity.UserModel;
import bg.mysite.service.userservice.UserService;
import bg.mysite.web.utils.GeneralUtils;
import bg.mysite.web.utils.MessageUtils;

@ManagedBean(name = "editUserBean")
@ViewScoped
public class EditUserBean {

	@Inject
	HttpServletRequest request;

	@EJB
	UserService userService;

	private UserModel user;
	private UploadedFile file;
	private static final String PROFILE_PAGE_REDIRECT = "/page/user/profile?faces-redirect=true&includeViewParams=true&username=";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\."
			+ "[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern;

	@PostConstruct
	public void init() {
		UserModel loggedUser = (UserModel) request.getSession().getAttribute("LOGGED_USER");
		if (loggedUser != null) {
			this.setUser(loggedUser);
		}
	}

	public String updateAction() {

		if (!validate()) {
			return null;
		}
		byte[] file = new byte[getFile().getContents().length];
		if(file.length==0 ){
			user.setImage(null);
		}
		else{
			System.arraycopy(getFile().getContents(), 0, file, 0, getFile().getContents().length);
			user.setImage(file);
		}
		
		userService.update(user);

		return PROFILE_PAGE_REDIRECT + user.getUsername();
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	protected boolean validate() {
		boolean hasErrors = false;
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
