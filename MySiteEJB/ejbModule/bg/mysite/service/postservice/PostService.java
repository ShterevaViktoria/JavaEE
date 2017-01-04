package bg.mysite.service.postservice;

import java.util.List;

import javax.ejb.Local;

import bg.mysite.entity.PostModel;
import bg.mysite.entity.RepostModel;
import bg.mysite.entity.UserModel;

@Local
public interface PostService {
	List<PostModel> findAllPosts();

	PostModel save(PostModel entity);

	void save(RepostModel entity);
	void delete(RepostModel entity);

	PostModel update(PostModel entity);

	void delete(PostModel entity);

	PostModel findById(Long id);

	List<PostModel> findByTag(String tag);
	RepostModel findRepost(UserModel user,PostModel post);

	List<PostModel> mostPopular();

	List<PostModel> findByIdUser(Long id);

	List<PostModel> newPostfindByTag(String tag);

	List<PostModel> findRepost(UserModel usermodel);

	List<PostModel> findLikedPosts(UserModel usermodel);

	List<PostModel> findAllPosts(UserModel usermodel);
}
