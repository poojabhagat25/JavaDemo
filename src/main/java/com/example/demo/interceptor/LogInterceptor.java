package com.example.demo.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.ErrorModel;
import com.example.demo.model.ResponseModel;
import com.example.demo.service.UserService;

import static com.example.demo.constants.RestConstants.INVALID_USER_TOKEN;
import static com.example.demo.constants.RestConstants.AUTHTOKEN;
import static com.example.demo.constants.RestConstants.AUTHTOKEN_KEYWORD;
import static com.example.demo.constants.RestConstants.USER;
import static com.example.demo.constants.RestConstants.LOGIN_URL;
import static com.example.demo.constants.RestConstants.FORGOT_PASSWORD;
import static com.example.demo.constants.RestConstants.SIGN_UP_USER_URL;
import static com.example.demo.constants.RestConstants.FAIL;
 
@Component
public class LogInterceptor implements HandlerInterceptor {
 
	Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		log.info("Request Completed!");
	}
 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		log.info("Method executed");
	}
 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		log.info("Before process request");
		System.out.println("Before process request");

		String pathInfo = request.getRequestURI();
		System.out.println("Method Type "+request.getMethod());
		
		log.info("PATH request" + pathInfo);
		if(!pathInfo.contains(SIGN_UP_USER_URL) && !pathInfo.contains(LOGIN_URL)
				&& !pathInfo.contains(FORGOT_PASSWORD)) 
		{
			// get the authToken value from header
			String authToken = request.getHeader(AUTHTOKEN);
			log.info(AUTHTOKEN_KEYWORD+ authToken);
			UserDTO user=null; 
			try{
				user = userService.getUserByAuthToken(authToken);
			}
			catch(Exception ex)
			{
				log.error(null, ex.getStackTrace(),ex);
			}
			if (user == null) {
				String errorMessage=INVALID_USER_TOKEN;						
				log.info(AUTHTOKEN_KEYWORD+ errorMessage);				
				ResponseModel responseModel = ResponseModel.getInstance();
				ErrorModel error = new ErrorModel(errorMessage);
				responseModel.setError(error);
				responseModel.setStatus(FAIL);
				String errorResponse = null;
				ObjectMapper mapper = new ObjectMapper();
				try {
					errorResponse = mapper.writeValueAsString(responseModel);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				response.setStatus(500);
				try {
					response.getWriter().write(errorResponse);
				} catch (IOException e) {
					e.printStackTrace();
				}
				throw new java.nio.file.AccessDeniedException(INVALID_USER_TOKEN);

			}
			request.setAttribute(USER, user);
		}
		return true;
	}
 
}
