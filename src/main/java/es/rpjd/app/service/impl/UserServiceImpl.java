package es.rpjd.app.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.rpjd.app.constants.DBResponseStatus;
import es.rpjd.app.hibernate.entity.User;
import es.rpjd.app.model.DBResponseModel;
import es.rpjd.app.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public UserServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	@Override
	public DBResponseModel<List<User>> getUsers() {
		Session session = sessionFactory.getCurrentSession();
		
		List<User> usuarios = session.createQuery("FROM User", User.class).list();

		return new DBResponseModel<>(DBResponseStatus.OK, "Usuarios obtenidos", usuarios);
	}

	@Transactional
	@Override
	public DBResponseModel<User> insert(User object) {
		Session session = sessionFactory.getCurrentSession();
		DBResponseModel<User> response;
		
		try {
			session.persist(object);
			
			session.flush();
			
			response = new DBResponseModel<User>(DBResponseStatus.OK, "Usuario insertado", object);
			
		} catch (Exception e) {
			response = new DBResponseModel<User>(DBResponseStatus.ERROR, e.getMessage(), null);
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
				// Ya se ha comprobado que se detecta la subcausa
				LOG.error("Se ha producido una excepción de restricciones.");
				e.printStackTrace();
			} else {
				LOG.error("Excepción general lanzada: ");
				e.printStackTrace();
			}
		}
		
		return response;
	}

}
