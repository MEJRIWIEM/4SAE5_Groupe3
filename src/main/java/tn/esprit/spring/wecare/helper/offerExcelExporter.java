package tn.esprit.spring.wecare.helper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import tn.esprit.spring.wecare.Entities.Collaborators.Offer;

public class offerExcelExporter {

	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private List<Offer> listOffer;
	
	
	public offerExcelExporter(List<Offer> listOffer) {
		this.listOffer=listOffer;
		workbook = new XSSFWorkbook();
		
	}
	
	private void createCell(Row row,int columnCount, Object value,CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell=row.createCell(columnCount);
		if(value instanceof Long) {
			cell.setCellValue((Long) value);
		}else if(value instanceof Integer) {
			cell.setCellValue((Integer) value);
		}else if(value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		}else if(value instanceof Float) {
			cell.setCellValue((Float) value);
		}else if(value instanceof Double) {
			cell.setCellValue((Double) value);
		}else{
			cell.setCellValue((String) value);
		}
		cell.setCellStyle(style);
	}
	
	
	private void writeHeaderLine() {
		sheet=workbook.createSheet("Offer");
		
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setBold(true);
		font.setFontHeight(20);
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
		createCell(row,0,"Offer List By Rating  "+ currentDateTime,style);
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,5));
		font.setFontHeightInPoints((short)(10));
		
		row=sheet.createRow(1);
		font.setBold(true);
        font.setFontHeight(12);
        style.setFont(font);
        createCell(row, 0, "Collaborator Name", style);
        createCell(row, 1, "Offer Name", style);
        createCell(row, 2, "Offer Type", style);
        createCell(row, 3, "Offer percent", style);
        createCell(row, 4, "Offer Averge rating", style);
        createCell(row, 5, "Number of user rating Offer  ", style);
        
 
	}
	
	private void writeDataLines() {
		int rowCount=2;
		
		CellStyle style=workbook.createCellStyle();
		XSSFFont font=workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for(Offer off:listOffer) {
			Row row=sheet.createRow(rowCount++);
			int columnCount=0;
			createCell(row, columnCount++, off.getCollaborator().getName(), style);
			createCell(row, columnCount++, off.getName(), style);
			createCell(row, columnCount++, off.getTypeOffer().toString(), style);
			createCell(row, columnCount++, off.getPercent(), style);
			createCell(row, columnCount++, off.getRatingAvg(), style);
			createCell(row, columnCount++, off.getCountUser(), style);
		}
	}
	
	
	
	public void export(HttpServletResponse response) {
		try {writeHeaderLine();
		writeDataLines();
		
		ServletOutputStream outputStream=response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		}catch (IOException e) {
		      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
	    }
	}
}
