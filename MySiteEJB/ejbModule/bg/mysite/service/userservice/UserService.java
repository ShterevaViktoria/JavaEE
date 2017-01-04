package bg.mysite.service.userservice;

import java.util.List;

import javax.ejb.Local;

import bg.mysite.entity.UserModel;

@Local
public interface UserService {
	List<UserModel> findAllUsers();

	List<UserModel> findFollowerUsers(UserModel user);

	List<UserModel> findFollowYouUsers(UserModel user);

	UserModel save(UserModel entity);

	UserModel update(UserModel entity);

	void delete(UserModel entity);

	UserModel findById(Long id);

	UserModel findByUserName(String username);

	List<UserModel> findByTag(String tag);

	UserModel loginUser(String aUsername, String aPassword);

	UserModel checkUserExists(String username, Long id);
}