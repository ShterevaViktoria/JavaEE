package bg.mysite.web.beans.post;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.UploadedFile;

import bg.mysite.entity.PostModel;
import bg.mysite.entity.RepostModel;
import bg.mysite.entity.TagModel;
import bg.mysite.entity.UserModel;
import bg.mysite.service.postservice.PostService;
import bg.mysite.service.tagservice.TagService;
import bg.mysite.service.userservice.UserService;
import bg.mysite.web.utils.GeneralUtils;
import bg.mysite.web.utils.MessageUtils;

@ManagedBean(name = "createPostBean")
@ViewScoped
public class CreatePostBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final String PROFILE_PAGE_REDIRECT = "/page/user/profile?faces-redirect=true&includeViewParams=true&username=";
	@Inject
	HttpServletRequest request;

	@EJB
	PostService postService;
	@EJB
	TagService tagService;
	@EJB
	UserService userService;

	private UserModel user;
	private PostModel post;
	private UploadedFile file;
	private String tags;
	private String userNickName;

	@PostConstruct
	public void init() {
		setPost(new PostModel());
		UserModel loggedUser = (UserModel) request.getSession().getAttribute("LOGGED_USER");
		if (loggedUser != null) {
			user = loggedUser;
		}
	}

	public String createAction() {
		Date date = new Date();
		post.setCreatedDate(date);
		post.setAuthor(user);
		byte[] file = new byte[getFile().getContents().length];
		if(file.length==0 ){
			post.setImage(null);
		}
		else{
			System.arraycopy(getFile().getContents(), 0, file, 0, getFile().getContents().length);
			post.setImage(file);
		}
		postService.save(post);
		
		String[] arrOfContent=post.getContent().split("[\\s,.?!]+");
		for(int i=0;i<arrOfContent.length;i++){
			if(arrOfContent[i].startsWith("#")){
				tagService.save(new TagModel(arrOfContent[i],post));
			}
		}
		
		return PROFILE_PAGE_REDIRECT+user.getUsername();
	}

	protected boolean validate() {
		boolean hasErrors = false;
		if (StringUtils.isBlank(post.getContent())) {
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

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public PostModel getPost() {
		return post;
	}

	public void setPost(PostModel post) {
		this.post = post;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

}
