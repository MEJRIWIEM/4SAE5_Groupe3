package tn.esprit.spring.wecare.Aop;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.wecare.Controllers.Collaborators.OfferController;
import tn.esprit.spring.wecare.Entities.Collaborators.Offer;
import tn.esprit.spring.wecare.Services.Collaborators.OfferService;
import tn.esprit.spring.wecare.helper.offerExcelExporter;


@Slf4j
public class LoggingAspect {
	
	OfferService offerService;
	OfferController off;
	
	
	//@Scheduled(cron="* * * 1 JAN *")
	@Scheduled(cron = "*/10 * * * * *")
	//public void downloadOfferExcel() throws Exception{
		public void exportToExcel(HttpServletResponse response) throws IOException {
			response.setContentType("application/octet-stream");
			String headerKey = "Content-Disposition";
			String headervalue = "attachment; filename=ListOffersByRating.xlsx";

			response.setHeader(headerKey, headervalue);
			List<Offer> listOffers = offerService.listAll();
			offerExcelExporter exp = new offerExcelExporter(listOffers);
			exp.export(response);
			log.info("chiffre d’affaire = ");
		}
		
		
		
	}
	


