package com.stockmarketcharter.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.stockmarketcharter.dao.UserRepository;
import com.stockmarketcharter.model.CompanyEntity;
import com.stockmarketcharter.model.UserEntity;


@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userrepository;

	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal)
	{
		String userName = principal.getName();
		UserEntity user = this.userrepository.findByUsername(userName);
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/dashboard")
	public String aboutPage(Model model)
	{
		model.addAttribute("title","Admin-Dashboard");
		return "admin/dashboard";
	}
	
	@GetMapping("/viewuser/{page}")
	public String showuser(@PathVariable("page") Integer page,Model model)
	{
		model.addAttribute("title","View User");
		
		Pageable pg = PageRequest.of(page, 4);
		
		Page<UserEntity> userlist = this.userrepository.findallUser(pg);
		
		model.addAttribute("userlist",userlist);
		model.addAttribute("currentpage",page);
		model.addAttribute("totalpages",userlist.getTotalPages());
		return "admin/viewuser";
	}
	
	
}
