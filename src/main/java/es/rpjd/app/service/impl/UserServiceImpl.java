package es.rpjd.app.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.rpjd.app.hibernate.entity.User;
import es.rpjd.app.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService{
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public UserServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	@Override
	public List<User> getUsers() {
		Session session = sessionFactory.getCurrentSession();
		User u1 = new User();
		u1.setUsername("username1");
		User u2 = new User();
		u2.setUsername("username2");
		session.persist(u1);
		session.persist(u2);
		
		session.flush();
		
		List<User> usuarios = session.createQuery("FROM User", User.class).list();
		

		return usuarios;
	}

}
