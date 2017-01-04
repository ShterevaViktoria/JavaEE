package bg.mysite.service.commentservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bg.mysite.entity.CommentModel;
@Stateless
public class CommentServiceImpl implements CommentService {

	@PersistenceContext
	protected EntityManager entityManager;

	public CommentServiceImpl() {
		super();
	}

	@Override
	public CommentModel save(CommentModel entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public void delete(CommentModel entity) {
		entityManager.remove(entity);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommentModel> findByPostId(Long id) {
		String query = "select model from CommentModel model where model.post.id=:em order by model.createdDate asc";
		Query q = entityManager.createQuery(query);
		q.setParameter("em", id);
		return q.getResultList();
	}

}
