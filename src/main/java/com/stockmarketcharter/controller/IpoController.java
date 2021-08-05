package com.stockmarketcharter.controller;

import java.security.Principal;
import java.util.List;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stockmarketcharter.dao.CompanyRepository;
import com.stockmarketcharter.dao.IpoRepository;
import com.stockmarketcharter.dao.StockExchangeRepository;
import com.stockmarketcharter.dao.UserRepository;
import com.stockmarketcharter.helper.Message;
import com.stockmarketcharter.model.CompanyEntity;
import com.stockmarketcharter.model.IPODetailEntity;
import com.stockmarketcharter.model.StockExchangeEntity;
import com.stockmarketcharter.model.UserEntity;


@Controller()
@RequestMapping("/admin")
public class IpoController {

	@Autowired
	private CompanyRepository companyrepository;
	
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
	
	@GetMapping("/addipo")
	public String openAddIPOForm(Model model)
	{
		model.addAttribute("title","Add IPO");
		model.addAttribute("ipo",new IPODetailEntity());
		return "admin/addipo";
	}
	
	@PostMapping("/process-ipo")
	public String processIPO(@ModelAttribute("ipo") IPODetailEntity ipo,@RequestParam(value="companyname") String companyname,@RequestParam(value="sename") String sename,Model model,HttpSession session)
	{
		try {
			
			model.addAttribute("title","Add IPO");
			System.out.println(companyname);
			System.out.println("DATA "+ ipo);
			
			CompanyEntity company = this.companyrepository.findBycompanyName(companyname);
			StockExchangeEntity stockExchangeEntity = this.serepository.findBystockExchange(sename);
			
			ipo.setStockExchange(stockExchangeEntity);
			ipo.setCompany(company);
			IPODetailEntity ipoentity = this.iporepository.save(ipo);
			
			session.setAttribute("message",new Message("Successfully Added IPO Details!! ","alert-success"));
			return "admin/addipo";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			session.setAttribute("message",new Message("Something went wrong, Check the inputs again !! ","alert-danger"));
			return "admin/addipo";
		}
		
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
		return "admin/viewipo";
		
	}
	
	@GetMapping("/ipo/{ipoId}")
	public String showIPODetail(@PathVariable("ipoId") Integer ipoId,Model model){
		
		System.out.println("IpoID "+ipoId);
		
		IPODetailEntity ipoEntity = this.iporepository.findById(ipoId).get();
		//System.out.println(ipoEntity);
		
		CompanyEntity company = ipoEntity.getCompany();
		StockExchangeEntity stockExchange = ipoEntity.getStockExchange();
		
		model.addAttribute("company", company);
		model.addAttribute("stockExchange",stockExchange);
		model.addAttribute("ipo",ipoEntity);
		return "admin/ipodetails";
	}
	
	@GetMapping("/deleteipo/{ipoId}")
	public String deleteIPO(@PathVariable("ipoId") Integer ipoId,HttpSession session){
		
		System.out.println("IpoID "+ipoId);
		
		IPODetailEntity ipoEntity = this.iporepository.findById(ipoId).get();
		
		try {
			ipoEntity.setCompany(null);
			ipoEntity.setStockExchange(null);
			
			this.iporepository.delete(ipoEntity);
			session.setAttribute("message", new Message("IPO Deleted Successfully","alert-success"));
		} catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message(" Failed to delete IPO","alert-danger"));
		}
		
		return "redirect:/admin/viewipo/0";
	}
	
	
	@PostMapping("/updateipo/{ipoId}")
	public String updateIPO(@PathVariable("ipoId") Integer ipoId,HttpSession session,Model model)
	{
		model.addAttribute("title","Update Ipo");
		IPODetailEntity ipoEntity = this.iporepository.findById(ipoId).get();
		model.addAttribute("ipo",ipoEntity);
		return "admin/updateipo";
		
	}
	
	@PostMapping("/process-updateipo")
	public String updateIPO(@ModelAttribute("ipo") IPODetailEntity ipo,@RequestParam(value="companyname") String companyname,@RequestParam(value="sename") String sename,Model model,HttpSession session)
	{
		try {
			
			model.addAttribute("title","Add IPO");
			System.out.println(companyname);
			System.out.println(sename);
			System.out.println("DATA "+ ipo);
			
			IPODetailEntity ipoEntity = this.iporepository.findById(ipo.getIpoId()).get();
			ipoEntity.setPricePerShare(ipo.getPricePerShare());
			ipoEntity.setTotalNoOfShares(ipo.getTotalNoOfShares());
			ipoEntity.setOpenDateTime(ipo.getOpenDateTime());
			ipoEntity.setRemarks(ipo.getRemarks());
			CompanyEntity company = this.companyrepository.findBycompanyName(companyname);
			StockExchangeEntity stockExchangeEntity = this.serepository.findBystockExchange(sename);
			ipoEntity.setCompany(company);
			ipoEntity.setStockExchange(stockExchangeEntity);
			this.iporepository.save(ipoEntity);
			
			session.setAttribute("message",new Message("Successfully Added IPO Details!! ","alert-success"));
			return "admin/addipo";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			session.setAttribute("message",new Message("Something went wrong, Check the inputs again !! ","alert-danger"));
			return "admin/addipo";
		}
		
	}
}
