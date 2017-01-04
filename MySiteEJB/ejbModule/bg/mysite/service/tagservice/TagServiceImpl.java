package bg.mysite.service.tagservice;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bg.mysite.entity.TagModel;

@Stateless
public class TagServiceImpl implements TagService {

	@PersistenceContext
	protected EntityManager entityManager;

	public TagServiceImpl() {
		super();
	}

	@Override
	public TagModel save(TagModel entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public TagModel findById(String name) {
		String query = "select model from TagModel model where model.name like :t";
		Query q = entityManager.createQuery(query);
		q.setParameter("t", name);
		try {
			return (TagModel) q.getSingleResult();
		} catch (NoResultException nre) {
			// the user doesn't exist
			return null;
		}

	}

}
