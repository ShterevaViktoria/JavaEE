package bg.mysite.service.commentservice;

import java.util.List;

import javax.ejb.Local;

import bg.mysite.entity.CommentModel;

@Local
public interface CommentService {
	CommentModel save(CommentModel entity);

	void delete(CommentModel entity);
	List<CommentModel> findByPostId(Long id);
}
