package es.rpjd.app.service;

import java.util.List;

import es.rpjd.app.hibernate.entity.User;

public interface UserService {

	List<User> getUsers();
}
