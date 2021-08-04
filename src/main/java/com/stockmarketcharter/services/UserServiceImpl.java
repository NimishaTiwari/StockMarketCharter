package com.stockmarketcharter.services;

import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockmarketcharter.dao.RoleRepository;
import com.stockmarketcharter.dao.UserRepository;
import com.stockmarketcharter.model.UserEntity;
import com.stockmarketcharter.model.UserRole;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public UserEntity createUser(UserEntity user, Set<UserRole> userRoles) throws Exception {
		
		UserEntity local = this.userRepository.findByUsername(user.getUsername());
		if(local!=null)
		{
			System.out.println("User already exits");
			throw new Exception("User already exists");
			
		}
		else {
			for(UserRole ur : userRoles)
			{
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			user.setConfirmed(false);
			String randomCode= RandomString.make(64);
			user.setVerification_code(randomCode);
			
			local = this.userRepository.save(user);
		}
		return local;
	}

	@Override
	public void sendVerificationEmail(UserEntity user,String siteURL) throws UnsupportedEncodingException, MessagingException {
		
		String subject="Please verify your registration";
		String senderName="Stock Market Charter Team";
		String mailContent="<p>Dear "+user.getFirstname()+" "+user.getLastname()+",</p>";
		mailContent+="<p>Please click the given link below to verif your account</p>";
		String verifyURL=siteURL + "verify?code=" + user.getVerification_code();
		mailContent+= "<h3><a href=\""+verifyURL+"\"><i>Verify</i></a></hr>";
		mailContent+="<p>Thank you !<br> The Stock Market Charter Team</p>";
		
		MimeMessage message=mailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message);
		
		helper.setFrom("stockmarketcharter@gmail.com",senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);
	
	}

	public boolean verify(String verificationCode)
	{
		UserEntity user=userRepository.findByVerification_code(verificationCode);
		if(user==null || user.isEnabled())
		{
			return false;
		}
		else
		{
			userRepository.enable(user.getUserId());
			return true;
		}
	}
}
