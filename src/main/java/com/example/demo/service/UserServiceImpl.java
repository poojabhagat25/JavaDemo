package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
//import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Exception.UserException;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.EmailDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserModel;
//import com.example.demo.util.ApplicationBeanUtil;
//import com.example.demo.util.EmailSender;
//import com.example.demo.util.LocaleConverter;
//import com.example.demo.util.ResourceManager;
import com.example.demo.util.TokenGenerator;
//import com.example.demo.validationClasses.CustomEmailValidator;
//import com.example.demo.validationClasses.FileURLValidator;

import static com.example.demo.constants.RestConstants.NOT_FOUND;
import static com.example.demo.constants.RestConstants.REGISTER_FAILED_EXCEPTION;
import static com.example.demo.constants.RestConstants.USER_ALREADY_EXIST_EXCEPTION;
import static com.example.demo.constants.RestConstants.PASSWORD_MISMATCH;
import static com.example.demo.constants.RestConstants.USER_NOT_REGISTERED;
import static com.example.demo.constants.RestConstants.LOG_IN_FAILED;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDTO getUserByAuthToken(String authToken) {
		UserDTO dto = null;
		List<UserDTO> userDtos = userDao.getUserByAuthToken(authToken);
		if (!userDtos.isEmpty()) {
			dto = (UserDTO) userDtos.get(0);
			return dto;
		}
		return dto;
	}

	@Override
	public UserDTO saveUser(UserDTO user) throws UserException, Exception {
		// Locale locale = LocaleConverter.getLocaleFromRequest(request);
		UserDTO userDTO = userDao.checkUser(user.getEmailId());
		if (userDTO != null) {
			// already exist
			throw new Exception("User already exists.");

		}

		try {
			String authToken = TokenGenerator.generateToken(user.getEmailId() + new Date());
			user.setAuthToken(authToken);
			user = userDao.saveUser(user);

		} catch (Exception ex) {
			logger.error(ex.getStackTrace(), ex);
			throw new UserException(REGISTER_FAILED_EXCEPTION);
		}
		return user;
	}

	@Override
	public UserDTO logIn(UserDTO userModel) throws Exception {

		UserDTO user = userDao.checkUser(userModel.getEmailId());
		if (user == null) {
			throw new UserException(USER_NOT_REGISTERED);
		}
		if (user != null && !user.getPassword().equals(userModel.getPassword())) {
			throw new UserException(PASSWORD_MISMATCH);
		}
		String authToken = TokenGenerator.generateToken(user.getEmailId() + new Date());
		user.setAuthToken(authToken);
		userDao.updateUser(user);
		return user;
	}

	// @Override
	// public void forgotPassword(String email)
	// throws UserException {
	// EmailDTO emailDTO = new EmailDTO();
	//
	// UserDTO retrievedUser = userDao.checkUser(email);
	// if (retrievedUser == null) {
	// logger.error(ResourceManager.getMessage(USER_NOT_REGISTERED, null,
	// NOT_FOUND, locale));
	// throw new UserException(ResourceManager.getMessage(USER_NOT_REGISTERED,
	// null, NOT_FOUND, locale));
	// }
	//
	// emailDTO.setFrom("Rest API Demo");
	// emailDTO.setTo(retrievedUser.getEmailId());
	// emailDTO.setUsername(retrievedUser.getFirstName());
	// emailDTO.setPassword(retrievedUser.getPassword());
	// try {
	// // Working code using velocity dependancies
	// EmailSender emailSender = (EmailSender)
	// ApplicationBeanUtil.getApplicationContext().getBean("mail");
	// emailSender.sendForgotPasswordMail(emailDTO);
	// } catch (Exception e) {
	// logger.error(e.getMessage() + e.getStackTrace());
	// }
	//
	// }

	@Override
	public List<UserModel> getUserList() throws Exception {
		List<UserModel> userModels = new ArrayList<UserModel>();
		try {
			// List<UserDTO> userDTOs = userDao.getUserList();
			// for (UserDTO userDTO : userDTOs) {
			// UserModel userModel = dozerMapper.map(userDTO, UserModel.class);
			// userModels.add(userModel);
			// }
			UserDTO user = new UserDTO();
			user.setEmailId("test");
			user.setUsername("test");
			user.setPassword("test");
			userDao.saveUser(user);
			List userDTOs = userDao.getUserDetails();
			System.out.println("*********** Users count :" + userDTOs.size());
			for (int i = 1; i < 3; i++) {
				UserModel userModel = new UserModel();
				userModel.setId(i);
				userModel.setFirstName("user" + i);
				userModel.setLastName("lnm" + i);
				userModel.setEmailId("fnm.lnm" + i + "@mail.com");
				userModels.add(userModel);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return userModels;

	}

}
