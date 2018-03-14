package com.example.demo.service;

import java.util.List;

import com.example.demo.Exception.UserException;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserModel;

public interface UserService {
	public UserDTO getUserByAuthToken(String authToken);

//	UserModel saveUser(UserModel userModel, HttpServletRequest request) throws UserException, Exception;
//
//	UserModel logIn(UserModel userModel, HttpServletRequest request) throws Exception;
//
//	void forgotPassword(HttpServletRequest request, String email) throws UserException;

	List<UserModel> getUserList()throws Exception;

	UserDTO saveUser(UserDTO user) throws UserException, Exception;

	UserDTO logIn(UserDTO userModel) throws Exception;
}
