package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Exception.UserException;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.ResponseModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;

import static com.example.demo.constants.RestConstants.USER_REGISTERED_SUCCESSFULLY_MESSAGE;
import static com.example.demo.constants.RestConstants.USER_LOGGED_IN_SUCCESSFULLY;

@RestController
@RequestMapping("/authentication")
public class Authentication {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public UserDTO getUser(@QueryParam("token") String authToken) {
		UserDTO userDto = userService.getUserByAuthToken(authToken);
		return userDto;

	}

	@RequestMapping(value = "/getData", method = RequestMethod.GET)
	public String test1() {
		return "Welcome !!!!!!!!!!!";
	}

	/**
	 * This method is for sign-up user.
	 * 
	 * @param UserModel
	 *            contains user data
	 * @param HttpServletRequest
	 * @param HttpServletResponse
	 * @return UserModel.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseModel signUpUser(@RequestBody UserDTO user) throws Exception {
		ResponseModel responseModel = null;
		user = userService.saveUser(user);
		responseModel = ResponseModel.getInstance();
		responseModel.setObject(user);
		responseModel.setMessage(USER_REGISTERED_SUCCESSFULLY_MESSAGE);
		return responseModel;
	}
	
	/**
	 * This method is for login purpose.
	 * 
	 * @param UserModel
	 *            contains user emailId and password.
	 * @param HttpServletRequest
	 * @return UserModel.
	 * @throws Exception
	 */
	@RequestMapping(value = "/logIn", method = RequestMethod.POST)
	public ResponseModel logIn(@RequestBody UserDTO user) throws Exception {
		ResponseModel responseModel = null;
		user = userService.logIn(user);
		responseModel = ResponseModel.getInstance();
		responseModel.setObject(user);
		responseModel.setMessage(USER_LOGGED_IN_SUCCESSFULLY);
		return responseModel;
	}

	@RequestMapping(value = "/forgot_password", method = RequestMethod.GET)
	public ResponseModel getUserList(HttpServletRequest request) throws Exception {
		// logger.info("<------User List------>");
		// Locale locale = LocaleConverter.getLocaleFromRequest(request);
		List<UserModel> userModels = userService.getUserList();
		ResponseModel responseModel = ResponseModel.getInstance();
		responseModel.setObject(userModels);
		// responseModel.setMessage(ResourceManager.getMessage(USER_LIST_SENT_SUCCESSFULLY,
		// null, NOT_FOUND, locale));
		return responseModel;
	}
}
