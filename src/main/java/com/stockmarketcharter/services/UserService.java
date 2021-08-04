package com.stockmarketcharter.services;

import com.stockmarketcharter.model.UserEntity;
import com.stockmarketcharter.model.UserRole;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.mail.MessagingException;


public interface UserService {
	
	public UserEntity createUser(UserEntity user, Set<UserRole> userRoles) throws Exception;
	public void sendVerificationEmail(UserEntity user,String siteURL) throws UnsupportedEncodingException, MessagingException;
	public boolean verify(String verificationCode);
}
