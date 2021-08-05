package com.stockmarketcharter.helper;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.stockmarketcharter.dao.StockExchangeRepository;
import com.stockmarketcharter.dao.StockPriceRepository;
import com.stockmarketcharter.model.StockExchangeEntity;
import com.stockmarketcharter.model.StockPriceEntity;

@Component
public class FileUpload {
	
	@Autowired
	private StockPriceRepository stockpricerepo;
	
	@Autowired
	private StockExchangeRepository serepo;
	
	
	public final String UPLOAD_DIR="C:\\Users\\Nimisha\\Documents\\workspace-spring-tool-suite-4-4.11.0.RELEASE\\StockMarketCharter\\src\\main\\resources\\static\\samplefile"; 
	public boolean uploadfile(MultipartFile file)
	{
		boolean f= false;
		try {

			Files.copy(file.getInputStream(),Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			f=true;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return f;
	}
	
	public boolean savetoDB(String path)throws IOException, InvalidFormatException
	{	
		
		try {
			Workbook workbook = WorkbookFactory.create(new File(path));
			System.out.println("Total Number of sheets in Workbook: " + workbook.getNumberOfSheets());
			for(Sheet sheet: workbook) {
	            System.out.println("Sheet Name " + sheet.getSheetName());
	            DataFormatter dataFormatter = new DataFormatter();
	            int i=0;
	            for(Row row: sheet)
	            {
	            	if(i==0)
	            	{
	            		i++;
	            		continue;
	            	}
	            	else if(row.getCell(0).getNumericCellValue()==0.0 &&row.getCell(2).getNumericCellValue()==0.0)
	            	{
	            		break;
	            	}
	            	else
	            	{
	            		double cc=row.getCell(0).getNumericCellValue();
	            		int val=(int)(cc);
	            		System.out.println(val);
	            		String companyCode= Integer.toString(val);
	            		System.out.println(companyCode);
	            		
		            	String sename=row.getCell(1).getStringCellValue();
		            	System.out.println(sename);
		            	
		            	double cp=row.getCell(2).getNumericCellValue();
		            	String currentPrice=Double.toString(cp);
		            	System.out.println(currentPrice);
						Date date= row.getCell(3).getDateCellValue();
						System.out.println(date);
		            	String Time= row.getCell(4).getStringCellValue();
		            	String nt=Time.replaceAll(" ", "");
		            	System.out.println(nt);
		            	
		            	LocalDate exdate = date.toInstant()
		            		      .atZone(java.time.ZoneId.systemDefault())
		            		      .toLocalDate();
		            	
		            	LocalTime extime=LocalTime.parse(nt);
		            	
		            	StockPriceEntity spe =new StockPriceEntity();
		            	spe.setCompanyCode(companyCode);
		            	spe.setCurrentPrice(currentPrice);
		            	spe.setDate(exdate);
		            	spe.setTime(extime);
		            	StockExchangeEntity stockExchange = serepo.findBystockExchange(sename);
		            	spe.setStockexchange(stockExchange);
		            	
		            	StockPriceEntity stockPriceEntity = stockpricerepo.save(spe);
		            		
	            	}
	            		
	            }
	           return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
