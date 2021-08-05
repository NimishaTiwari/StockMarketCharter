package com.stockmarketcharter.controller;

import java.io.File;
import org.apache.poi.ss.usermodel.*;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.stockmarketcharter.dao.CompanyRepository;
import com.stockmarketcharter.dao.UserRepository;
import com.stockmarketcharter.helper.FileUpload;
import com.stockmarketcharter.helper.Message;
import com.stockmarketcharter.model.CompanyEntity;
import com.stockmarketcharter.model.StockPriceEntity;
import com.stockmarketcharter.model.UserEntity;

@Controller
@RequestMapping("/admin")
public class ExcelUploadController {

	@Autowired
	private UserRepository userrepository;

	@Autowired
	private FileUpload fileupload;
	
	@Autowired
	private CompanyRepository companyrepository;
	
	
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal)
	{
		String userName = principal.getName();
		UserEntity user = this.userrepository.findByUsername(userName);
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/uploadexcel")
	public String aboutPage(Model model)
	{
		model.addAttribute("title","Admin-UPLOAD");
		return "admin/importexcel";
	}
	
	
	@PostMapping("/process-excel")
	public String processExcel(Model model, @RequestParam("file") MultipartFile file, HttpSession session) throws IOException
	{
		final String UPLOAD_DIR="C:\\Users\\Nimisha\\Documents\\workspace-spring-tool-suite-4-4.11.0.RELEASE\\StockMarketCharter\\src\\main\\resources\\static\\samplefile"; 
		try {
			if(file.isEmpty())
			{	
				session.setAttribute("message",new Message("File is Empty ","alert-danger"));
				return "admin/importexcel";
			}
			 
			boolean uploaded = fileupload.uploadfile(file);
			if(uploaded)
			{
				String path= UPLOAD_DIR+File.separator+file.getOriginalFilename();
				boolean savetoDB=this.fileupload.savetoDB(path);
				if(savetoDB)
				{
					Workbook workbook = WorkbookFactory.create(new File(path));
					for(Sheet sheet: workbook)
					{
						long noofrows=sheet.getLastRowNum()-sheet.getFirstRowNum();
						model.addAttribute("rows",noofrows);
					}
					
//					CompanyEntity company = this.companyrepository.findById(Integer.parseInt(savetoDB.getCompanyCode())).get();
//					model.addAttribute("company",company);
//					model.addAttribute("sprice",savetoDB);
//					model.addAttribute("title","Successfully Added Excel");
					session.setAttribute("message",new Message("Successfully Uploaded Details and saved in database!! ","alert-success"));
					return "admin/importexcel";
				}
				session.setAttribute("message",new Message("Successfully Uploaded file but failed to save in database!! ","alert-warning"));
				return "admin/importexcel";
			}
			else
			{
				session.setAttribute("message",new Message("Something went wrong, Check the inputs again !! ","alert-danger"));
				return "admin/importexcel";
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			session.setAttribute("message",new Message("Something went wrong, Check the inputs again !! ","alert-danger"));
			return "admin/importexcel";
		}
		
	}
}