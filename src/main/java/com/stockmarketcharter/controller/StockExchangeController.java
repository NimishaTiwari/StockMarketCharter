package com.stockmarketcharter.controller;

import java.security.Principal;
import java.util.Optional;

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

import com.stockmarketcharter.dao.StockExchangeRepository;
import com.stockmarketcharter.dao.UserRepository;
import com.stockmarketcharter.helper.Message;
import com.stockmarketcharter.model.CompanyEntity;
import com.stockmarketcharter.model.SectorEntity;
import com.stockmarketcharter.model.StockExchangeEntity;
import com.stockmarketcharter.model.UserEntity;

@Controller()
@RequestMapping("/admin")
public class StockExchangeController {
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private StockExchangeRepository stockexchangerepository;
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal)
	{
		String userName = principal.getName();
		UserEntity user = this.userrepository.findByUsername(userName);
		model.addAttribute("user", user);
	}
	@GetMapping("/addstockexchange")
	public String openAddStockExchangeForm(Model model)
	{
		model.addAttribute("title","Add Stock Exchange");
		model.addAttribute("stockexchange",new StockExchangeEntity());
		return "admin/addstockexchange";
	}
	
	@PostMapping("/process-stockexchange")
	public String processStockExchange(@ModelAttribute("stockexchange") StockExchangeEntity stockexchange,HttpSession session)
	{
		try {
			
			System.out.println("DATA "+ stockexchange);
			this.stockexchangerepository.save(stockexchange);
			
			session.setAttribute("message",new Message("Successfully Added Stock Exchange Details!! ","alert-success"));
			return "admin/addstockexchange";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			session.setAttribute("message",new Message("Something went wrong, Check the inputs again !! ","alert-danger"));
			return "admin/addstockexchange";
		}
		
	}
	
	@GetMapping("/viewstockexchange/{page}")
	public String showStockExchange(@PathVariable("page") Integer page,Model model)
	{
		model.addAttribute("title","View Stock Exchange");
		
		Pageable pg = PageRequest.of(page, 4);
		
		Page<StockExchangeEntity> StockExchangelist = this.stockexchangerepository.findallStockExchange(pg);
		model.addAttribute("stockexchangelist",StockExchangelist);
		model.addAttribute("currentpage",page);
		model.addAttribute("totalpages",StockExchangelist.getTotalPages());
		return "admin/viewstockexchange";
	}
	
	
	@GetMapping("/stockexchange/{stockExchangeId}")
	public String showStockExchangeDetail(@PathVariable("stockExchangeId") Integer stockExchangeId,Model model){
		model.addAttribute("title","View Stock Exchange");
		System.out.println("SEID "+stockExchangeId);
		StockExchangeEntity stockexchangeentity = this.stockexchangerepository.findById(stockExchangeId).get();
		model.addAttribute("stockexchange", stockexchangeentity);
		return "admin/stockexchange";
	}
	
	
	@PostMapping("/updatestockexchange/{stockExchangeId}")
	public String updateStockExchange(@PathVariable("stockExchangeId") Integer stockExchangeId,HttpSession session,Model model)
	{
		model.addAttribute("title","Update Stock Exchange");
		StockExchangeEntity stockexchangeentity = this.stockexchangerepository.findById(stockExchangeId).get();
		model.addAttribute("stockexchange",stockexchangeentity);
		return "admin/updatestockexchange";
		
	}
	
	@PostMapping("/process-updatestockexchange")
	public String processUpdateStockExchange(@ModelAttribute("stockexchange") StockExchangeEntity stockexchange,HttpSession session)
	{
		try {
			
			System.out.println("DATA "+ stockexchange);
			StockExchangeEntity stockExchangeEntity = this.stockexchangerepository.findById(stockexchange.getStockExchangeId()).get();
			stockExchangeEntity.setStockExchange(stockexchange.getStockExchange());
			stockExchangeEntity.setContactAddress(stockexchange.getContactAddress());
			stockExchangeEntity.setRemarks(stockexchange.getRemarks());
			stockExchangeEntity.setSEBrief(stockexchange.getSEBrief());
			this.stockexchangerepository.save(stockExchangeEntity);
			
			session.setAttribute("message",new Message("Successfully Added Stock Exchange Details!! ","alert-success"));
			return "admin/stockexchange";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			session.setAttribute("message",new Message("Something went wrong, Check the inputs again !! ","alert-danger"));
			return "admin/addstockexchange";
		}
		
	}
	
	
}
