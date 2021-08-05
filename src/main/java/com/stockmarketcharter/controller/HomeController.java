package com.stockmarketcharter.controller;

import java.util.HashSet;
import java.util.Set;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stockmarketcharter.dao.UserRepository;
import com.stockmarketcharter.helper.Message;
import com.stockmarketcharter.model.Role;
import com.stockmarketcharter.model.UserRole;
import com.stockmarketcharter.model.UserEntity;
import com.stockmarketcharter.services.UserServiceImpl;

@Controller
public class HomeController {

			
		@Autowired
    	private UserServiceImpl userService;
		
		@Autowired
		private BCryptPasswordEncoder passwordEncoder;
		
		@RequestMapping("/")
		public String homePage(Model model)
		{
			model.addAttribute("title","Home- Stock Market Charter");
			return "home";
		}
		
		@RequestMapping("/about")
		public String aboutPage(Model model)
		{
			model.addAttribute("title","About- Stock Market Charter");
			return "about";
		}
		
		@RequestMapping("/signup")
		public String signUp(Model model)
		{
			model.addAttribute("title","Register- Stock Market Charter");
			model.addAttribute("userEntity", new UserEntity());
			return "signup";
		}
		
		@RequestMapping("/login")
		public String loginPage(Model model)
		{
			model.addAttribute("title","login- Stock Market Charter");
			return "login";
		}
		
			
		
		@PostMapping("/register")
		public String registerUser(@Valid @ModelAttribute("userEntity") UserEntity userEntity,BindingResult bindingResult,Model model,
				@RequestParam(value="agreement",defaultValue="false") boolean agreement,HttpSession session)		
		{
			try {
			if(!agreement)		
			{
				System.out.println("You have not agreed the term and conditions");
				throw new Exception("You have not agreed the term and conditions");
			}
				
				if(bindingResult.hasErrors()) 
				{
					System.out.println("ERROR "+bindingResult.toString());
					model.addAttribute("userEntity", userEntity);
					return "signup";
				}
				else {
					
					System.out.println("Agreement "+agreement);
					System.out.println("User "+ userEntity);
					
					//encrypting password
					userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
					
					//setting user type
					Role role=new Role();
					//role.setRoleName("ROLE_ADMIN");
					
					role.setRoleName("ROLE_USER");
					
					Set<UserRole> userRoles=new HashSet<>();
					UserRole userRole=new UserRole();
					userRole.setRole(role);
					userRole.setUser(userEntity);
					
					userRoles.add(userRole);
					
										
					//saving user in database
					this.userService.createUser(userEntity,userRoles);
					
					//Sending verification mail to user
					String siteURL="http://localhost:8080/";
					this.userService.sendVerificationEmail(userEntity,siteURL);
					
					//displaying entered data on signup page
					model.addAttribute("title","Email Confirmation- Stock Market Charter");
					model.addAttribute("userEntity",userEntity);
					
					session.setAttribute("message",new Message("Successfully Registered!! ","alert-success"));
					return "emailconfirmation";
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("userEntity",userEntity); 
				session.setAttribute("message",new Message("Something went wrong,Check the inputs again !! ","alert-danger"));
				return "signup";
			}
			
		}
	
		@GetMapping("/verify")
		public String verification(@Param("code") String code,Model model)
		{
			boolean verified= userService.verify(code);
			String title= verified? "verification Succeeded":"verification Failed";
			model.addAttribute("title",title);
			return verified ? "verification_success":"verification_failure";
		}
}
