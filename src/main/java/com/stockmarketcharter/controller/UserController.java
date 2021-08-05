package com.stockmarketcharter.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

import com.stockmarketcharter.dao.CompanyRepository;
import com.stockmarketcharter.dao.IpoRepository;
import com.stockmarketcharter.dao.StockExchangeRepository;
import com.stockmarketcharter.dao.UserRepository;
import com.stockmarketcharter.model.CompanyEntity;
import com.stockmarketcharter.model.IPODetailEntity;
import com.stockmarketcharter.model.SectorEntity;
import com.stockmarketcharter.model.StockExchangeEntity;
import com.stockmarketcharter.model.UserEntity;
import com.stockmarketcharter.services.CompanyServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController
{

	@Autowired
	private CompanyRepository companyrepository;
	
	@Autowired
	private CompanyServiceImpl companyService;
	
	@Autowired
	private UserRepository userrepository;

	@Autowired
	private StockExchangeRepository serepository;
	
	@Autowired
	private IpoRepository iporepository;

	
	@ModelAttribute
	public void addCommonDatauser(Model model,Principal principal)
	{
		String userName = principal.getName();
		UserEntity user = this.userrepository.findByUsername(userName);
		model.addAttribute("user", user);
	}
	
	@ModelAttribute
	public void addCommonData(Model model)
	{
		List<CompanyEntity> companylist = this.companyrepository.findAll();
		model.addAttribute("companylist",companylist);
	}
	
	
	@RequestMapping("/dashboard")
	public String aboutPage(Model model)
	{
		model.addAttribute("title","User");
		return "user/dashboard";
	}
	
	@GetMapping("/viewcompany/{page}")
	public String showCompany(@PathVariable("page") Integer page,Model model)
	{
		model.addAttribute("title","View Company");
		
		Pageable pg = PageRequest.of(page, 4);
		
		Page<CompanyEntity> companylist = this.companyrepository.findallCompany(pg);
		model.addAttribute("companylist",companylist);
		model.addAttribute("currentpage",page);
		model.addAttribute("totalpages",companylist.getTotalPages());
		return "user/viewcompany";
	}
	
	@GetMapping("/company/{companyId}")
	public String showCompanyDetail(@PathVariable("companyId") Integer companyId,Model model){
		
		model.addAttribute("title","View Company");
		System.out.println("CID "+companyId);
		Optional<CompanyEntity> companydet = this.companyrepository.findById(companyId);
		CompanyEntity companyEntity = companydet.get();
		SectorEntity sector = companyEntity.getSector();
		model.addAttribute("company", companyEntity);
		model.addAttribute("sector",sector);
		return "user/companydetail";
	}
	
	@GetMapping("/viewipo/{page}")
	public String showIPO(@PathVariable("page") Integer page,Model model)
	{
		model.addAttribute("title","View IPO");
		
		Pageable pg = PageRequest.of(page, 4);
		
		Page<IPODetailEntity> ipolist = this.iporepository.findallIpos(pg);
		
		
		model.addAttribute("ipolist",ipolist);
		
		model.addAttribute("currentpage",page);
		model.addAttribute("totalpages",ipolist.getTotalPages());
		return "user/viewipo";
		
	}
	
	@GetMapping("/ipo/{ipoId}")
	public String showIPODetail(@PathVariable("ipoId") Integer ipoId,Model model){
		
		System.out.println("IpoID "+ipoId);
		model.addAttribute("title","View IPO");
		IPODetailEntity ipoEntity = this.iporepository.findById(ipoId).get();
		//System.out.println(ipoEntity);
		
		CompanyEntity company = ipoEntity.getCompany();
		StockExchangeEntity stockExchange = ipoEntity.getStockExchange();
		
		model.addAttribute("company", company);
		model.addAttribute("stockExchange",stockExchange);
		model.addAttribute("ipo",ipoEntity);
		return "user/ipodetails";
	}
	
}
