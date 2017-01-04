package bg.mysite.web.beans.home;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import bg.mysite.entity.PostModel;
import bg.mysite.entity.UserModel;
import bg.mysite.service.postservice.PostService;
import bg.mysite.service.userservice.UserService;

@ManagedBean(name = "searchBean")
@SessionScoped
public class SearchBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String text;
	@Inject
	HttpServletRequest request;

	@EJB
	UserService userService;
	@EJB
	PostService postService;

	@PostConstruct
	public void init() {
	}

	public List<PostModel> allPopularPosts() {
		List<PostModel> list=postService.findByTag(text);
		Collections.sort(list, new Comparator<PostModel>() {
		   public int compare(PostModel left, PostModel right)  {
		        return (int) (right.getNumberLikes()-left.getNumberLikes() );
		    }
		});
		return list;
	}
	
	public List<PostModel> allNewPosts(){
		return postService.newPostfindByTag(text);
	}
	public List<UserModel> allProfiles(){
		return userService.findByTag(text);
	}
	 public StreamedContent getImage() throws IOException {
	        FacesContext context = FacesContext.getCurrentInstance();
	        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
	            return new DefaultStreamedContent();
	        }
	        else {
	            String postId = context.getExternalContext().getRequestParameterMap().get("id");
	            PostModel post = postService.findById(Long.valueOf(postId));
	            return new DefaultStreamedContent(new ByteArrayInputStream(post.getImage()),"image/jpg");
	        }
	    }
	 public StreamedContent getImageUser() throws IOException {
	        FacesContext context = FacesContext.getCurrentInstance();

	        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
	            return new DefaultStreamedContent();
	        }
	        else {
	            String userId = context.getExternalContext().getRequestParameterMap().get("id");
	            UserModel user = userService.findById(Long.valueOf(userId));
	            return new DefaultStreamedContent(new ByteArrayInputStream(user.getImage()),"image/jpg");
	        }
	    }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
