package com.example.demo.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.UserDTO;

@Transactional
@Repository("userDao")
public class UserDaoImpl extends BaseDAO implements UserDao {
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

//	@Autowired
//	private SessionFactory sessionFactory;

	
	@SuppressWarnings("unchecked")
	public List<UserDTO> getUserByAuthToken(String token) throws HibernateException {
		List<UserDTO> userDetailsList = null;
		try {
			Session session = getCurrentSession();
			userDetailsList = session.createCriteria(UserDTO.class).add(Restrictions.eq("authToken", token)).list();
		} catch (HibernateException exception) {
		}
		return userDetailsList;
	}

	@Override
	public UserDTO checkUser(String emailId) {
		UserDTO user = null;
		try {
			Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("emailId", emailId));
			user = (UserDTO) criteria.uniqueResult();
		} catch (HibernateException exception) {
			logger.error(exception.getMessage(), exception);
		}
		return user;
	}

	@Override
	public UserDTO saveUser(UserDTO userDTO) {
		try {
			save(userDTO);
		} catch (HibernateException exception) {
			logger.error(exception.getMessage(), exception);
		}
		return userDTO;
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) {
		try {
			update(userDTO);
		} catch (HibernateException exception) {
			logger.error(exception.getMessage(), exception);
		}
		return userDTO;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserDTO> getUserList() throws HibernateException {
		List<UserDTO> userDTOs = null;
		try {
			Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
			userDTOs = criteria.list();
		} catch (HibernateException exception) {
			logger.error(exception.getMessage(), exception);
		}
		return userDTOs;
	}
	
	@Override
	public List getUserDetails() {
		Criteria criteria = getCurrentSession().createCriteria(UserDTO.class);
		return criteria.list();
	}

}
