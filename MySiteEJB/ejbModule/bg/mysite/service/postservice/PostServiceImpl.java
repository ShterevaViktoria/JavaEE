package bg.mysite.service.postservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import bg.mysite.entity.PostModel;
import bg.mysite.entity.RepostModel;
import bg.mysite.entity.UserModel;

@Stateless
public class PostServiceImpl implements PostService{
	private static final String QUERY_STRING="select new PostModel(p.id, p.content, p.createdDate, p.author, p.image,(SELECT count(*) from RepostModel re where p=re.post),(SELECT count(*) from LikesModel li where p=li.postLiked),(SELECT count(*) from CommentModel c where p=c.post)) from PostModel p";
	 @PersistenceContext
	    protected EntityManager entityManager;

	    public PostServiceImpl() {
	        super();
	    }
	    
	@SuppressWarnings("unchecked")
	@Override
	public List<PostModel> findAllPosts() {
		 String query = "select model from PostModel model order by model.createdDate desc";
	        Query q = entityManager.createQuery(query);
	        return q.getResultList();
	}

	@Override
	public PostModel save(PostModel entity) {
		entityManager.persist(entity);
     return entity;
	}

	@Override
	public PostModel update(PostModel entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(PostModel entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PostModel findById(Long id) {
		String query=QUERY_STRING+" where p.id = :em";
		Query q = entityManager.createQuery(query.toString());
		q.setParameter("em", id);
		try {
			return (PostModel) q.getSingleResult();
		} catch (NoResultException nre) {
			// the user doesn't exist
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public  List<PostModel> findByTag(String tag){
		String query = QUERY_STRING+" , TagModel t where p=t.post and t.name like :searchTag";
        Query q = entityManager.createQuery(query);
        q.setParameter("searchTag", "%" + tag + "%");
        return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostModel> mostPopular() {
		String query = "select new PostModel(p.id, p.content, p.createdDate, p.author, p.image, (SELECT count(*) from RepostModel re where p=re.post)) from PostModel p ";
		Query q = entityManager.createQuery(query);

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostModel> findByIdUser(Long id) {
		String query="select po from PostModel po,RepostModel r where r.post=po and r.author.id=:bId";
		Query q = entityManager.createQuery(query);
		q.setParameter("bId", id);
		return q.getResultList();
	}

	@Override
	public void save(RepostModel entity) {
		entityManager.persist(entity);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostModel> findAllPosts(UserModel usermodel) {
		String query = QUERY_STRING+" where p.author=:usermodel order by p.createdDate desc";
        Query q = entityManager.createQuery(query);
        q.setParameter("usermodel", usermodel);
        return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostModel> newPostfindByTag(String tag) {
		String query = QUERY_STRING+" , TagModel t where p=t.post and t.name like :searchTag order by p.createdDate desc";
        Query q = entityManager.createQuery(query);
        q.setParameter("searchTag", "%" + tag + "%");
        return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostModel> findRepost(UserModel usermodel) {
		String query = QUERY_STRING+" , RepostModel r where p=r.post and r.author=:em order by r.date desc";
        Query q = entityManager.createQuery(query);
        q.setParameter("em", usermodel);
        return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostModel> findLikedPosts(UserModel usermodel) {
		String query = QUERY_STRING+" , LikesModel l where p=l.postLiked and l.userLikedPost=:em order by l.datedLiked desc";
        Query q = entityManager.createQuery(query);
        q.setParameter("em", usermodel);
        return q.getResultList();
	}

	@Override
	public RepostModel findRepost(UserModel user, PostModel post) {
		String query="select r from RepostModel where r.author = :em and r.post=:a";
		Query q = entityManager.createQuery(query.toString());
		q.setParameter("em", user);
		q.setParameter("a", post);
		try {
			return (RepostModel) q.getSingleResult();
		} catch (NoResultException nre) {
			// the user doesn't exist
			return null;
		}
	}

	@Override
	public void delete(RepostModel entity) {
		entityManager.remove(entity);
		
	}

	}
