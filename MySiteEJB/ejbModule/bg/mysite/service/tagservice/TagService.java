package bg.mysite.service.tagservice;

import bg.mysite.entity.TagModel;

public interface TagService {
	TagModel save(TagModel entity);

	TagModel findById(String name);
}
