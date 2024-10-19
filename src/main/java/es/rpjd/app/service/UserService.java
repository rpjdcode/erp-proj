package es.rpjd.app.service;

import java.util.List;

import es.rpjd.app.hibernate.entity.User;
import es.rpjd.app.model.DBResponseModel;

public interface UserService {

	DBResponseModel<List<User>> getUsers();
	DBResponseModel<User> insert(User object);
}
