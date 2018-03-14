package com.example.demo.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.example.demo.dto.UserDTO;

public interface UserDao {

	public List<UserDTO> getUserByAuthToken(String authToken);

	UserDTO checkUser(String emailId);

	UserDTO saveUser(UserDTO userDTO);

	List<UserDTO> getUserList() throws HibernateException;

	List getUserDetails();

	UserDTO updateUser(UserDTO userDTO);

}
