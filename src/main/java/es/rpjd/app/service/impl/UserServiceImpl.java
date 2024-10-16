package es.rpjd.app.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
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
		
		List<User> usuarios = session.createQuery("FROM User", User.class).list();

		return usuarios;
	}

	@Transactional
	@Override
	public User insert(User object) {
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.persist(object);
			
			session.flush();
		} catch (Exception e) {
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				// Ya se ha comprobado que se detecta la subcausa
				System.err.println("Se ha producido una excepción de restricciones.");
				e.printStackTrace();
			} else {
				System.err.println("Excepción general lanzada: ");
				e.printStackTrace();
			}
		}
		
		return null;
	}

}
