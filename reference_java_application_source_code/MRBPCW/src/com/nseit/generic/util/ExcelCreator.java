package com.nseit.generic.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDetailsBean;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.PaymentReportBean;

public class ExcelCreator {
	public HSSFWorkbook createWorkbook(List<List<String>> userList)
			throws Exception {

		List<String> rowList = new ArrayList<String>();

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("User Data");

		/**
		 * Setting the width of the first three columns.
		 */
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 3500);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);

		/**
		 * Style for the header cells.
		 */
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(boldFont);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Request ID"));
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Date"));
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Time"));
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Level of Exam"));
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("User ID"));
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Password"));
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("IPIN"));

		for (int index = 0; index < userList.size(); index++) {
			row = sheet.createRow(index + 1);
			rowList = userList.get(index);

			cell = row.createCell(0);
			HSSFRichTextString requestID = new HSSFRichTextString(rowList
					.get(3));
			cell.setCellValue(requestID);

			cell = row.createCell(1);
			HSSFRichTextString date = new HSSFRichTextString(rowList.get(4));
			cell.setCellValue(date);

			cell = row.createCell(2);
			HSSFRichTextString time = new HSSFRichTextString(rowList.get(5));
			cell.setCellValue(time);

			cell = row.createCell(3);
			HSSFRichTextString discipline = new HSSFRichTextString(rowList
					.get(6));
			cell.setCellValue(discipline);

			cell = row.createCell(4);
			HSSFRichTextString userID = new HSSFRichTextString(rowList.get(0));
			cell.setCellValue(userID);

			cell = row.createCell(5);
			HSSFRichTextString password = new HSSFRichTextString(rowList.get(1));
			cell.setCellValue(password);

			cell = row.createCell(6);
			HSSFRichTextString ipin = new HSSFRichTextString(rowList.get(2));
			cell.setCellValue(ipin);

		}

		return wb;

	}

	public static HSSFWorkbook createSeatUitlizationREport(
			List<List> reportList) {

		List<String> slotList = new ArrayList<String>();
		List<String> singleRow = null;
 
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Seat Utilization Report");

		/**
		 * Setting the width of the first three columns.
		 */
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 3500);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);

		/**
		 * Style for the header cells.
		 */
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setWrapText(true);
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(boldFont);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Sr. No."));
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Center Name"));
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("City"));
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Total Capacity"));	
		slotList = reportList.get(reportList.size()-1);
		int k= 4;
		for(String tstDateNSlot : slotList)
		{
			cell = row.createCell(k);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue(new HSSFRichTextString(tstDateNSlot.replace("<br/>", " ")));
			k++;
		}
		
		int rowIndex =1;
		for(int rowNum=0;rowNum <reportList.size()-1;rowNum++)
		{
			singleRow = reportList.get(rowNum);
			//cell = row.createCell(rowNum);
			row = sheet.createRow(++rowIndex);
			cell = row.createCell(0);
			HSSFRichTextString srNo = new HSSFRichTextString((rowNum + 1) + "");
			cell.setCellValue(srNo);
			for(int columnVal = 0 ; columnVal < singleRow.size();columnVal++ )
			{
				cell = row.createCell(columnVal+1);
				HSSFRichTextString batchCellText = new HSSFRichTextString(singleRow.get(columnVal));
				cell.setCellValue(batchCellText);
				cell.setCellStyle(headerCellStyle3);
			}
		}
		
		/*
		HashMap<String, Object> maxDayDetails = reportList.get(0);
		// reportList.remove(0);
		Integer maxDays = null;
		if (maxDayDetails != null) {
			maxDays = (Integer) maxDayDetails.get("maxDay");
			int col = 3;
			for (int i = 0; i < maxDays; i++) {
				int start = 3 * (i + 1) + 1;
				cell = row.createCell(++col);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString("Day " + (i + 1)
						+ " Seat Utilization "));
				
				sheet.addMergedRegion(new CellRangeAddress(0, 0, start, start+2));
				
				cell = row.createCell(++col);
				cell.setCellValue(new HSSFRichTextString(""));
				cell = row.createCell(++col);
				cell.setCellValue(new HSSFRichTextString(""));

			}
		}

		//sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 6));
		//sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 9));

		row = sheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString(""));
		cell = row.createCell(1);
		cell.setCellValue(new HSSFRichTextString(""));
		cell = row.createCell(2);
		cell.setCellValue(new HSSFRichTextString(""));
		cell = row.createCell(3);
		cell.setCellValue(new HSSFRichTextString(""));

		int col = 3;
		for (int i = 0; i < maxDays; i++) {
			// cell = row.createCell(++col);
			// cell.setCellStyle(headerCellStyle);
			for (int j = 0; j < 3; j++) {
				cell = row.createCell(++col);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString("Batch " + (j + 1)));
			}
		}

		HashMap<String, Object> bean = null;
		int rowIndex = 1;
		for (int index = 1; index < reportList.size(); index++) {
			row = sheet.createRow(++rowIndex);
			bean = reportList.get(index);

			cell = row.createCell(0);
			HSSFRichTextString srNo = new HSSFRichTextString((index + 1) + "");
			cell.setCellValue(srNo);

			cell = row.createCell(1);
			HSSFRichTextString requestID = new HSSFRichTextString(bean
					.get("center")
					+ "");
			cell.setCellValue(requestID);

			cell = row.createCell(2);
			HSSFRichTextString date = new HSSFRichTextString(bean.get("city")
					+ "");
			cell.setCellValue(date);

			cell = row.createCell(3);
			HSSFRichTextString time = new HSSFRichTextString(bean
					.get("capacity")
					+ "");
			cell.setCellValue(time);

			HashMap<String, HashMap<String, String>> dayMap = (HashMap<String, HashMap<String, String>>) bean
					.get("dayMap");

			int lastcol = 3;
			for (int i = 0; i < maxDays; i++) {
				if (dayMap != null && dayMap.get("DAY" + (i + 1)) != null) {
					HashMap<String, String> dayMapDtl = dayMap.get("DAY"
							+ (i + 1));
					if (dayMapDtl != null) {
						cell = row.createCell(++lastcol);
						HSSFRichTextString batchCellText = new HSSFRichTextString(
								dayMapDtl.get("BATCH1") + "");
						cell.setCellValue(batchCellText);

						cell = row.createCell(++lastcol);
						batchCellText = new HSSFRichTextString(dayMapDtl
								.get("BATCH2")
								+ "");
						cell.setCellValue(batchCellText);

						cell = row.createCell(++lastcol);
						batchCellText = new HSSFRichTextString(dayMapDtl
								.get("BATCH3")
								+ "");
						cell.setCellValue(batchCellText);
					}
				} else {
					for (int j = 0; j < 3; j++) {
						cell = row.createCell(++lastcol);
						cell.setCellValue(new HSSFRichTextString("NA"));
					}
				}

			}

		}
*/
		return wb;

	}
	
	
	public HSSFWorkbook createWorkbookForPaymentReport(List<PaymentReportBean> paymentCollectionReportDetailList)throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle dateCellStyle = wb.createCellStyle();
		DataFormat poiFormat = wb.createDataFormat();
		dateCellStyle.setDataFormat(poiFormat.getFormat("dd-MMM-yyyy"));
		HSSFSheet sheet = wb.createSheet("Payment Collection Report");
		
		/**
		 * Setting the width of the first three columns.
		 */
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 7500);
		
		/**
		 * Style for the header cells.
		 */
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setWrapText(true);
		
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(boldFont);
		
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCell cellHeader = rowHeader.createCell(3);
		cellHeader.setCellStyle(headerCellStyle2);
		cellHeader.setCellValue(new HSSFRichTextString("Payment Collection Report"));
		
		cellHeader = rowHeader.createCell(7);
		cellHeader.setCellStyle(headerCellStyle);

		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		headerCellStyle2.setFont(f);
		HSSFRichTextString richString = new HSSFRichTextString("Generated on");
		cellHeader.setCellValue(richString);

	
         
         
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		
		Date date = new Date();
		dateFormat.format(date);
		
		cellHeader = rowHeader.createCell(10);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(dateFormat.format(date));
		
		HSSFRow row = sheet.createRow(2);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Sr No"));
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Payment Date"));
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Payment Mode"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Total Amount Collected(in INR)"));
		
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
		for (int count = 0; count < paymentCollectionReportDetailList.size(); count++) {
			row = sheet.createRow(count+3);
			cell = row.createCell(0); 
			
			HSSFCellStyle style = wb.createCellStyle();
			
			PaymentReportBean paymentReportBean = paymentCollectionReportDetailList.get(count);
			
			HSSFRichTextString srNo = new HSSFRichTextString(String.valueOf(count+1));
			cell.setCellValue(Integer.parseInt(String.valueOf(count+1)));
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellStyle(headerCellStyle3);
			
			//HSSFRichTextString paymentDate = new HSSFRichTextString(paymentReportBean.getOPD_CREATED_DATE());
			String paymentD = paymentReportBean.getOPD_CREATED_DATE();
			Date payamentDate = simpledateformat.parse(paymentD);
			cell.setCellValue(payamentDate);
			cell.setCellStyle(dateCellStyle);
			cell = row.createCell(2);
			cell.setCellStyle(headerCellStyle3);
			
			HSSFRichTextString paymentDesc = new HSSFRichTextString(paymentReportBean.getOPTM_PAYMENT_DESC());
			cell.setCellValue(paymentDesc);
			cell = row.createCell(3); 
			
			HSSFRichTextString amount = new HSSFRichTextString(String.valueOf(paymentReportBean.getOPD_AMOUNT()));
			if(!"null".equalsIgnoreCase(String.valueOf(paymentReportBean.getOPD_AMOUNT())) && !String.valueOf(paymentReportBean.getOPD_AMOUNT()).equalsIgnoreCase(""))
			cell.setCellValue(paymentReportBean.getOPD_AMOUNT());
			else{
				cell.setCellValue(0.0);
			}
			cell = row.createCell(4);
			cell.setCellStyle(headerCellStyle3);
			
		}
		
		return wb;
	}
	
	
	public HSSFWorkbook createWorkbookForCandidateDetails(List<CandidateDetailsBean> candidateDetailList)throws Exception {/*

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Candidate Details");
		
		*//**
		 * Setting the width of the first three columns.
		 *//*
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 7000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 7000);
		sheet.setColumnWidth(10, 7000);
		sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 7000);
		sheet.setColumnWidth(13, 7000);
		sheet.setColumnWidth(14, 7000);
		sheet.setColumnWidth(15, 7000);
		sheet.setColumnWidth(16, 7000);
		sheet.setColumnWidth(17, 7000);
		sheet.setColumnWidth(18, 7000);
		sheet.setColumnWidth(19, 7000);
		sheet.setColumnWidth(20, 7000);
		sheet.setColumnWidth(21, 7000);
		sheet.setColumnWidth(22, 7000);
		sheet.setColumnWidth(23, 7000);
		sheet.setColumnWidth(24, 7000);
		sheet.setColumnWidth(25, 7000);
		sheet.setColumnWidth(26, 7000);
		sheet.setColumnWidth(27, 7000);
		sheet.setColumnWidth(28, 7000);
		sheet.setColumnWidth(29, 7000);
		sheet.setColumnWidth(30, 7000);
		sheet.setColumnWidth(31, 7000);
		sheet.setColumnWidth(32, 7000);
		sheet.setColumnWidth(33, 7000);
		sheet.setColumnWidth(34, 7000);
		sheet.setColumnWidth(35, 7000);
		sheet.setColumnWidth(36, 7000);
		sheet.setColumnWidth(37, 7000);
		sheet.setColumnWidth(38, 7000);
		sheet.setColumnWidth(39, 7000);
		sheet.setColumnWidth(40, 7000);
		sheet.setColumnWidth(41, 7000);
		sheet.setColumnWidth(42, 7000);
		sheet.setColumnWidth(43, 7000);
		sheet.setColumnWidth(44, 7000);
		sheet.setColumnWidth(45, 7000);
		sheet.setColumnWidth(46, 7000);
		sheet.setColumnWidth(47, 7000);
		sheet.setColumnWidth(48, 7000);
		sheet.setColumnWidth(49, 7000);
		sheet.setColumnWidth(50, 7000);
		sheet.setColumnWidth(51, 7000);
		sheet.setColumnWidth(52, 7000);
		sheet.setColumnWidth(53, 7000);
		sheet.setColumnWidth(54, 7000);
		sheet.setColumnWidth(55, 7000);
		sheet.setColumnWidth(56, 7000);
		sheet.setColumnWidth(57, 7000);
		sheet.setColumnWidth(58, 7000);
		sheet.setColumnWidth(59, 7000);
		sheet.setColumnWidth(60, 7000);
		sheet.setColumnWidth(61, 7000);
		sheet.setColumnWidth(62, 7000);
		sheet.setColumnWidth(63, 7000);
		
		
		*//**
		 * Style for the header cells.
		 *//*
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Name"));
		
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Date of Birth"));
		
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Age"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Gender"));
		
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Category"));
		
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Correspondence Address : Address"));
		
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Correspondence Address : City"));
		
		cell = row.createCell(7);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Correspondence Address : State"));
		
		
		cell = row.createCell(8);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Correspondence Address : Pin"));
		
		cell = row.createCell(9);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Correspondence Address : Tel No."));
		
		cell = row.createCell(10);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Correspondence Address : Mobile"));
		
		cell = row.createCell(11);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Correspondence Address : Email"));
		
		cell = row.createCell(12);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Permanent Address : Address"));
		
		cell = row.createCell(13);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Permanent Address : City"));
		
		
		cell = row.createCell(14);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Permanent Address : State"));
		
		cell = row.createCell(15);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Permanent Address : Pin"));
		
		
		cell = row.createCell(16);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Permanent Address : Tel No"));
		
		
		cell = row.createCell(17);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Permanent Address : Mobile No"));
		
		

		cell = row.createCell(18);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Permanent Address : Email"));
		
		

		cell = row.createCell(19);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Name of Highest Degree"));
		
		
		cell = row.createCell(20);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Name of Institute/Department/College"));
		
		cell = row.createCell(21);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Name of University"));
		
		cell = row.createCell(22);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Name of Head of the Department/Coordinator"));
		
		
		cell = row.createCell(23);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("DBT Supported Teaching Programme "));
		
		cell = row.createCell(24);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Year of Passing"));
		
		cell = row.createCell(25);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Semester/Yearly System"));
		
		
		cell = row.createCell(26);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Last Semester/Year upto which result has been declared"));
		
		
		cell = row.createCell(27);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Aggregate % of marks of all semesters/year up to which result has been declared"));
		
		

		cell = row.createCell(28);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Brief description of project during highest degree"));
		
		

		cell = row.createCell(29);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Applying for JRF(CSIR/UGC/DBT/ICMR)"));
		
		
		cell = row.createCell(30);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Field of Interest"));
		
		
		cell = row.createCell(31);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Have you been earlier trained under BITP"));
		
		cell = row.createCell(32);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Name of Company"));
		
		
		cell = row.createCell(33);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Period of Training"));
		
		cell = row.createCell(34);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Any award/scholarship"));
		
		cell = row.createCell(35);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Any other relevant information"));
		
		
		cell = row.createCell(36);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Area of Project"));
		
		
		cell = row.createCell(37);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Preferred venue for interview "));
		
		

		cell = row.createCell(38);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("payment"));

		cell = row.createCell(39);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("phoneSTD"));
		
		
		cell = row.createCell(40);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("alternateMobile"));
		
		
		cell = row.createCell(41);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("palternateMobile"));
		
		cell = row.createCell(42);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("testCenter"));
		
		
		cell = row.createCell(43);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("photo"));
		
		cell = row.createCell(44);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("signature"));
		
		cell = row.createCell(45);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("documentSubmit"));
		
		
		cell = row.createCell(46);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("appearedForTest"));
		
		
		cell = row.createCell(47);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Account number"));
		
		

		cell = row.createCell(48);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("NEFT/IFSC Code"));
		
		

		cell = row.createCell(49);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Bank name"));
		
		
		cell = row.createCell(50);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Branch address"));
		
		
		cell = row.createCell(51);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Additional field 1"));
		
		
		cell = row.createCell(52);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Additional field 2"));
		
		cell = row.createCell(53);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Additional field 3"));
		
		cell = row.createCell(54);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Additional field 4"));
		
		cell = row.createCell(55);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Additional field 5"));
		
		cell = row.createCell(56);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("application id"));
		
		cell = row.createCell(57);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("payment id"));
		
		cell = row.createCell(58);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("First name"));
		
		cell = row.createCell(59);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("last name"));
		
		cell = row.createCell(60);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Name as per bank account"));
		
		cell = row.createCell(61);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Password"));
		
		cell = row.createCell(62);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Communication Permanent Mobile Number"));
		
		cell = row.createCell(63);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Communication Alternate Mobile Number"));
		
		
		cell = row.createCell(64);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("testCenter 2"));
		
		int count = 0;
		
		for (CandidateDetailsBean candidateDetailsBean : candidateDetailList) {
			row = sheet.createRow(count+1);
			
			HSSFCellStyle style = wb.createCellStyle();
			if (candidateDetailsBean!=null){
				row = sheet.createRow(count+1);
				cell = row.createCell(0);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getNAME());
				
				
				cell = row.createCell(1);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOcd_date_of_birth());
				
				
				cell = row.createCell(2);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getAge());
				
				cell = row.createCell(3);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getGender());
				
				cell = row.createCell(4);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOcd_category_fk());
				
				cell = row.createCell(5);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOcd_comm_address());
				
				cell = row.createCell(6);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOcd_comm_city());
				
				cell = row.createCell(7);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_comm_state_fk()));
				
				
				cell = row.createCell(8);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_comm_pin_code()));
				
				cell = row.createCell(9);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getCommlandlineno()));
				
				cell = row.createCell(10);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_phone_number()));
				
				cell = row.createCell(11);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOum_email_id()));
				
				cell = row.createCell(12);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOCD_PERM_ADDRESS()));
				
				cell = row.createCell(13);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOCD_PERM_CITY()));
				
				
				cell = row.createCell(14);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_perm_state_fk()));
				
				cell = row.createCell(15);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_perm_pin_code()));
				
				
				cell = row.createCell(16);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getPermlandlineno()));
				
				
				cell = row.createCell(17);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOum_mobile_no()));
				
				

				cell = row.createCell(18);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOum_email_id()));
				
				

				cell = row.createCell(19);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_highest_degree()));
				
				
				cell = row.createCell(20);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_institute()));
				
				cell = row.createCell(21);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_university()));
				
				cell = row.createCell(22);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_hod()));
				
				
				cell = row.createCell(23);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_dbt_prog()));
				
				cell = row.createCell(24);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOum_year_desc()));
				
				cell = row.createCell(25);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getPattern()));
				
				
				cell = row.createCell(26);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_sem_year_declared()));
				
				
				cell = row.createCell(27);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_perc()));
				
				

				cell = row.createCell(28);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_brief_desc_proj()));
				
				

				cell = row.createCell(29);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_apply_jrf()));
				
				
				cell = row.createCell(30);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_field_intrest_fk()));
				
				
				cell = row.createCell(31);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_trained_bitp()));
				
				cell = row.createCell(32);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_company_name()));
				
				
				cell = row.createCell(33);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_period_training()));
				
				cell = row.createCell(34);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_scholorship()));
				
				cell = row.createCell(35);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_relevant_info()));
				
				
				cell = row.createCell(36);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_area_project_fk()));
				
				
				cell = row.createCell(37);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_int_loc_fk()));
				
				

				cell = row.createCell(38);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getPayment()));

				cell = row.createCell(39);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_perm_std_code()));
				
				
				cell = row.createCell(40);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOum_mobile_no()));
				
				
				cell = row.createCell(41);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOcd_phone_number()));
				
				cell = row.createCell(42);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOCPD_PREF_TEST_CENTRE_1_FK()));
				
				
				cell = row.createCell(43);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getPhoto());
				
				cell = row.createCell(44);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getSIGN());
				
				cell = row.createCell(45);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getDoc());
				
				
				cell = row.createCell(46);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue("FALSE");
				
				
				cell = row.createCell(47);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOcd_account_number());
				
				

				cell = row.createCell(48);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOcd_ifs_code());
				
				

				cell = row.createCell(49);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOcd_bank_name());
				
				
				cell = row.createCell(50);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOcd_bank_branch_addr());
				
				
				cell = row.createCell(51);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue("");
				
				
				cell = row.createCell(52);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue("");
				
				cell = row.createCell(53);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue("");
				
				cell = row.createCell(54);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue("");
				
				cell = row.createCell(55);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue("");
				
				
				cell = row.createCell(56);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOUM_USER_ID());
				
				cell = row.createCell(57);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOPD_TRANSACTION_NO());
				
				cell = row.createCell(58);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOCD_FIRST_NAME());
				
				cell = row.createCell(59);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOCD_LAST_NAME());
				
				cell = row.createCell(60);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOCD_NAME_BANK_RECORDS());
				
				cell = row.createCell(61);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getPassword());
				
				cell = row.createCell(62);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOCD_COMM_PERM_MOBILE_NO());
				
				cell = row.createCell(63);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOCD_COMM_ALT_MOBILE_NO());
				
				cell = row.createCell(64);
				cell.setCellStyle(headerCellStyle);
				cell.setCellValue(candidateDetailsBean.getOCPD_PREF_TEST_CENTRE_2_FK());
				
				count++;
			}
		}
		
		return wb;
	*/
	


		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Candidate Details");
		
		/**
		 * Setting the width of the first three columns.
		 */
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 7000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 7000);
		sheet.setColumnWidth(10, 7000);
		sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 7000);
		sheet.setColumnWidth(13, 7000);
		sheet.setColumnWidth(14, 7000);
		sheet.setColumnWidth(15, 7000);
		sheet.setColumnWidth(16, 7000);
		sheet.setColumnWidth(17, 7000);
		sheet.setColumnWidth(18, 7000);
		sheet.setColumnWidth(19, 7000);
		sheet.setColumnWidth(20, 7000);
		sheet.setColumnWidth(21, 7000);
		sheet.setColumnWidth(22, 7000);
		
		/**
		 * Style for the header cells.
		 */
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setWrapText(true);
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Client Code"));
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Enrollment Id"));
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("First Name"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Last Name"));
		
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Phone"));
		
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Mobile No."));
		
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Email"));
		
		cell = row.createCell(7);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Test City"));
		
		cell = row.createCell(8);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Date of Birth"));
		
		cell = row.createCell(9);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Address/ Unit"));
		
		cell = row.createCell(10);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Pincode"));
		
		cell = row.createCell(11);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("City"));
		
		cell = row.createCell(12);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Other City"));
		
		cell = row.createCell(13);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("State"));
		
		cell = row.createCell(14);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Exam Center"));
		
		cell = row.createCell(15);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Exam Date"));
		
		cell = row.createCell(16);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Exam Time"));
		
		cell = row.createCell(17);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Module"));

		cell = row.createCell(18);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Test Center ID"));
		
		cell = row.createCell(19);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Roll No."));
		
		cell = row.createCell(20);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Password"));
		
		cell = row.createCell(21);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Photo"));
		
		cell = row.createCell(22);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Sign"));
		
		
		int count = 0;
		
		for (CandidateDetailsBean candidateDetailsBean : candidateDetailList) {
			row = sheet.createRow(count+1);
			
			HSSFCellStyle style = wb.createCellStyle();
			if (candidateDetailsBean!=null){
				row = sheet.createRow(count+1);
				cell = row.createCell(0);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOUM_USER_ID());
				
				
				cell = row.createCell(1);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue("");
				
				
				cell = row.createCell(2);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOCD_FIRST_NAME());
				
				cell = row.createCell(3);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOCD_LAST_NAME());
				
				cell = row.createCell(4);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOcd_phone_number());
				
				cell = row.createCell(5);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOum_mobile_no());
				
				cell = row.createCell(6);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOum_email_id());
				
				cell = row.createCell(7);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOTCM_CITY());
				
				
				cell = row.createCell(8);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOcd_date_of_birth());
				
				cell = row.createCell(9);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOcd_comm_address());
				
				cell = row.createCell(10);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateDetailsBean.getOcd_comm_pin_code());
				
				cell = row.createCell(11);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue("");
				
				cell = row.createCell(12);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue("");
				
				cell = row.createCell(13);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOSM_STATE_NAME()));
				
				cell = row.createCell(14);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOTCM_TEST_CENTRE_NAME()));
				
				cell = row.createCell(15);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue("");
				
				cell = row.createCell(16);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue("");
				
				cell = row.createCell(17);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOCD_TEST_FK()));
				
				
				cell = row.createCell(18);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOTCM_TEST_CENTRE_PK()));
				
				
				cell = row.createCell(19);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue("");
				
				
				cell = row.createCell(20);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue("");
				
				cell = row.createCell(21);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOUM_USER_ID()+".jpg"));
				
				cell = row.createCell(22);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(new HSSFRichTextString(candidateDetailsBean.getOUM_USER_ID()+".jpg"));
				
				count++;
			}
		}
		
		return wb;
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public HSSFWorkbook createWorkbookForOnlinePayment(List<CandidateMgmtBean> candidateDetailList)throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Payment Details Report");
		
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 7000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 7000);
		sheet.setColumnWidth(10, 7000);
		sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 7000);
		
		
		/**
		 * Style for the header cells.
		 */
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setWrapText(true);
		
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		headerCellStyle2.setFont(f);
		
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(boldFont);
		
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCell cellHeader = rowHeader.createCell(4);
		cellHeader.setCellStyle(headerCellStyle2);
		cellHeader.setCellValue(new HSSFRichTextString("Payment Details Report"));
		
		cellHeader = rowHeader.createCell(7);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(new HSSFRichTextString("Generated on"));

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		Date date = new Date();
		dateFormat.format(date);
		
		cellHeader = rowHeader.createCell(8);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(dateFormat.format(date));
		
		HSSFRow row = sheet.createRow(2);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Sr.No."));
		
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Registration ID"));
		
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Candidate Name"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Level Of Exam"));
		
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Category"));
		
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Payment Mode"));
		
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Applicable Fee"));
		
		
		cell = row.createCell(7);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Transaction Number"));
		
		cell = row.createCell(8);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Transaction Date"));
		
		cell = row.createCell(9);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Mobile Number"));
		
		cell = row.createCell(10);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Email ID"));
		
		cell = row.createCell(11);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Status Date"));
		
		cell = row.createCell(12);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Status"));
		
		
		int count = 0;
		
		for (CandidateMgmtBean candidateMgmtBean : candidateDetailList ) {
			row = sheet.createRow(count+3);
			
			HSSFCellStyle style = wb.createCellStyle();
			
			if (candidateMgmtBean!=null){
				
				cell = row.createCell(0); 
				cell.setCellValue(count+1);
				
				
				cell = row.createCell(1); 
				cell.setCellValue(candidateMgmtBean.getEnrollmentId());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(2);
				cell.setCellValue(candidateMgmtBean.getFirstName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(3);
				cell.setCellValue(candidateMgmtBean.getDisciplineType());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(4);
				cell.setCellValue(candidateMgmtBean.getCandidateCategory());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(5);
				cell.setCellValue(candidateMgmtBean.getPaymentMode());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(6);
				cell.setCellValue(Float.parseFloat(candidateMgmtBean.getApplicableFees()));
				cell.setCellStyle(headerCellStyle3);
				
				
				cell = row.createCell(7);
				cell.setCellValue(candidateMgmtBean.getOnlineTransactionNo());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(8);
				cell.setCellValue(candidateMgmtBean.getOnlineTransactionDate());
				
				cell = row.createCell(9);
				cell.setCellValue(Long.parseLong(candidateMgmtBean.getMobileNO()));
				
				cell = row.createCell(10);
				cell.setCellValue(candidateMgmtBean.getEmailAddress());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(11);
				cell.setCellValue(candidateMgmtBean.getPaymentSubmittedDate());
				
				cell = row.createCell(12);
				cell.setCellValue(candidateMgmtBean.getPaymentStatus());
				cell.setCellStyle(headerCellStyle3);
				count++;
			}
		}
		
		return wb;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	public HSSFWorkbook createWorkbookForDemandDraft(List<CandidateMgmtBean> candidateDetailList)throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Candidate Details");
	    
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 7000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 7000);
		sheet.setColumnWidth(10, 7000);
		sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 7000);
		sheet.setColumnWidth(13, 7000);
		sheet.setColumnWidth(14, 7000);
		
		
		
		/**
		 * Style for the header cells.
		 */
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setWrapText(true);
		
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		headerCellStyle2.setFont(f);
		
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(boldFont);


		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCell cellHeader = rowHeader.createCell(4);
		cellHeader.setCellStyle(headerCellStyle2);
		cellHeader.setCellValue(new HSSFRichTextString("Payment Details Report"));
		
		cellHeader = rowHeader.createCell(7);
		cellHeader.setCellStyle(headerCellStyle);
		HSSFRichTextString richString = new HSSFRichTextString("Generated on");
		cellHeader.setCellValue(richString);
		cellHeader.setCellStyle(headerCellStyle);



		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		Date date = new Date();
		dateFormat.format(date);
		cellHeader = rowHeader.createCell(8);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(dateFormat.format(date));
		
		HSSFRow row = sheet.createRow(2);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Sr.No."));
		
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Registration ID"));
		
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Candidate Name"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Level of Exam"));
		
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Category"));
		
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Payment Mode"));
		
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("DD Number"));
		
		
		cell = row.createCell(7);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("DD Date"));
		
		cell = row.createCell(8);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Applicable Fee"));
		
		cell = row.createCell(9);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Bank Name"));
		
		cell = row.createCell(10);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("City"));
		
		cell = row.createCell(11);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Mobile Number"));
		
		cell = row.createCell(12);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Email ID"));
		
		
		cell = row.createCell(13);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Status Date"));
		
		cell = row.createCell(14);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Status"));
		
		
		int count = 0;
		
		for (CandidateMgmtBean candidateMgmtBean : candidateDetailList ) {
			row = sheet.createRow(count+3);
			
			HSSFCellStyle style = wb.createCellStyle();
			
			if (candidateMgmtBean!=null){
				
				cell = row.createCell(0); 
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(count+1);
				
				
				cell = row.createCell(1); 
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getEnrollmentId());
				
				
				cell = row.createCell(2);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getFirstName());
				
				cell = row.createCell(3);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getDisciplineType());
				
				cell = row.createCell(4);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getCandidateCategory());
				
				cell = row.createCell(5);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getPaymentMode());
				
				cell = row.createCell(6);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getDdChallanReceiptNo());
				
				
				cell = row.createCell(7);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getDdChallanDate());
				
				cell = row.createCell(8);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(Float.parseFloat(candidateMgmtBean.getApplicableFees()));
				
				cell = row.createCell(9);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getDdBankName());
				
				cell = row.createCell(10);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getDdCityName());
				
				cell = row.createCell(11);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(Long.parseLong(candidateMgmtBean.getMobileNO()));
				
				cell = row.createCell(12);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getEmailAddress());
				
				cell = row.createCell(13);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getPaymentSubmittedDate());
				
				cell = row.createCell(14);
				cell.setCellStyle(headerCellStyle3);
				cell.setCellValue(candidateMgmtBean.getPaymentStatus());
				count++;
			}
		}
		
		return wb;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	public HSSFWorkbook createWorkbookForChallan(List<CandidateMgmtBean> candidateDetailList)throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Candidate Details");

		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 7000);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 7000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 7000);
		sheet.setColumnWidth(10, 7000);
		sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 7000);
		sheet.setColumnWidth(13, 7000);
		sheet.setColumnWidth(14, 7000);
		
		
		/**
		 * Style for the header cells.
		 */
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setWrapText(true);
		HSSFCellStyle dateCellStyle = wb.createCellStyle();
		DataFormat poiFormat = wb.createDataFormat();
		dateCellStyle.setDataFormat(poiFormat.getFormat("dd-MMM-yyyy"));
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		headerCellStyle2.setFont(f);
		
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(boldFont);
		//headerCellStyle3.setWrapText(true);
		
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCell cellHeader = rowHeader.createCell(4);
		cellHeader.setCellStyle(headerCellStyle2);
		cellHeader.setCellValue(new HSSFRichTextString("Payment Details Report"));
		
		//shekharc start
		//CellStyle cs = wb.createCellStyle();
	    //cs.setWrapText(true);
	  //shekharc start
	    
		cellHeader = rowHeader.createCell(7);
		cellHeader.setCellStyle(headerCellStyle);
		HSSFRichTextString richString = new HSSFRichTextString("Generated on");
		cellHeader.setCellValue(richString);
		cellHeader.setCellStyle(headerCellStyle);


		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		Date date = new Date();
		dateFormat.format(date);
		cellHeader = rowHeader.createCell(8);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(dateFormat.format(date));
		
		
		HSSFRow row = sheet.createRow(2);
		HSSFCell cell = row.createCell(0);
		
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Sr.No."));
		
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Registration ID"));
		
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Candidate Name"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Level of Exam"));
		
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Category"));
		
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Payment Mode"));
		
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Journal / Reference Number"));
		
		
		cell = row.createCell(7);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Payment Date"));
		
		cell = row.createCell(8);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Applicable Fee"));
		
		cell = row.createCell(9);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Branch Name"));
		
		cell = row.createCell(10);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Branch Code"));
		
		cell = row.createCell(11);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Mobile Number"));
		
		cell = row.createCell(12);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Email ID"));
		
		
		cell = row.createCell(13);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Status Date"));
		
		cell = row.createCell(14);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Status"));
		
		
		int count = 0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
		for (CandidateMgmtBean candidateMgmtBean : candidateDetailList ) {
			row = sheet.createRow(count+3);
			
			HSSFCellStyle style = wb.createCellStyle();
			
			if (candidateMgmtBean!=null){
				
				cell = row.createCell(0); 
				cell.setCellValue(count+1);
				
				
				cell = row.createCell(1); 
				cell.setCellValue(candidateMgmtBean.getEnrollmentId());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(2);
				cell.setCellValue(candidateMgmtBean.getFirstName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(3);
				cell.setCellValue(candidateMgmtBean.getDisciplineType());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(4);
				cell.setCellValue(candidateMgmtBean.getCandidateCategory());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(5);
				cell.setCellValue(candidateMgmtBean.getPaymentMode());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(6);
				cell.setCellValue(candidateMgmtBean.getDdChallanReceiptNo());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(7);
				cell.setCellStyle(dateCellStyle);
				String payDate = candidateMgmtBean.getOnlineTransactionDate();
				//Date payemntDate = simpledateformat.parse(payDate);
				cell.setCellValue(payDate);
				
				cell = row.createCell(8);
				cell.setCellValue(Float.parseFloat(candidateMgmtBean.getApplicableFees()));
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(9);
				cell.setCellValue(candidateMgmtBean.getChallanBranchName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(10);
				cell.setCellValue(candidateMgmtBean.getChallanBranchCode());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(11);
				cell.setCellValue(Long.parseLong(candidateMgmtBean.getMobileNO()));
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(12);
				cell.setCellValue(candidateMgmtBean.getEmailAddress());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(13);
				cell.setCellStyle(dateCellStyle);
				String paymentSubm = candidateMgmtBean.getPaymentSubmittedDate();
				Date payemntSubmDate = simpledateformat.parse(paymentSubm);
				cell.setCellValue(payemntSubmDate);
				
				cell = row.createCell(14);
				cell.setCellValue(candidateMgmtBean.getPaymentStatus());
				cell.setCellStyle(headerCellStyle3);
				count++;
			}
		}
		
		return wb;
	}
	
	
	
	
	
	
	
	
	
	
	public HSSFWorkbook createWorkbookForPaymentNotsubmitted(List<CandidateMgmtBean> candidateDetailList)throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Candidate Details");

		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 3500);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 7000);
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setWrapText(true);
		
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		headerCellStyle2.setFont(f);
		
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(boldFont);
		
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCell cellHeader = rowHeader.createCell(4);
		cellHeader.setCellStyle(headerCellStyle2);
		cellHeader.setCellValue(new HSSFRichTextString("Payment Details Report"));
		
		cellHeader = rowHeader.createCell(7);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(new HSSFRichTextString("Generated on"));

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		Date date = new Date();
		dateFormat.format(date);
		
		cellHeader = rowHeader.createCell(8);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(dateFormat.format(date));
		
		
		HSSFRow row = sheet.createRow(2);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Sr.No."));
		
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Registration ID"));
		
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Candidate Name"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Level of Exam"));
		
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Category"));
		
		
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Mobile Number"));
		
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Email ID"));
		
		
		cell = row.createCell(7);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Status"));
		
		
		int count = 0;
		
		for (CandidateMgmtBean candidateMgmtBean : candidateDetailList ) {
			row = sheet.createRow(count+3);
			
			HSSFCellStyle style = wb.createCellStyle();
			
			if (candidateMgmtBean!=null){
				
				cell = row.createCell(0); 
				cell.setCellValue(count+1);
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(1); 
				cell.setCellValue(candidateMgmtBean.getUserId());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(2);
				cell.setCellValue(candidateMgmtBean.getCandidateName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(3);
				cell.setCellValue(candidateMgmtBean.getDisciplineType());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(4);
				cell.setCellValue(candidateMgmtBean.getCandidateCategory());
				cell.setCellStyle(headerCellStyle3);
				
				
				cell = row.createCell(5);
				cell.setCellValue(candidateMgmtBean.getMobileNO());
				
				cell = row.createCell(6);
				cell.setCellValue(candidateMgmtBean.getEmailAddress());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(7);
				cell.setCellValue(candidateMgmtBean.getPaymentStatus());
				cell.setCellStyle(headerCellStyle3);
				count++;
			}
		}
		
		return wb;
	}
	
	public HSSFWorkbook createWorkbookForCandidateDetailsReport(
			List<CandidateBean> candidateDetailsList) throws ParseException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Candidate Detail Report");
		
		/**
		 * Setting the width of the first three columns.
		 */
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 7000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 7000);
		sheet.setColumnWidth(10, 7000);
		sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 7000);
		sheet.setColumnWidth(13, 7000);
		sheet.setColumnWidth(14, 7000);
		sheet.setColumnWidth(15, 7000);
		sheet.setColumnWidth(16, 7000);
		sheet.setColumnWidth(17, 7000);
		sheet.setColumnWidth(18, 7000);
		sheet.setColumnWidth(19, 7000);
		sheet.setColumnWidth(20, 7000);
		sheet.setColumnWidth(21, 7000);
		sheet.setColumnWidth(22, 7000);
		sheet.setColumnWidth(23, 7000);
		sheet.setColumnWidth(24, 7000);
		sheet.setColumnWidth(25, 7000);
		sheet.setColumnWidth(26, 7000);
		sheet.setColumnWidth(27, 10000);
		sheet.setColumnWidth(28, 7000);
		sheet.setColumnWidth(29, 7000);
		sheet.setColumnWidth(30, 7000);
		sheet.setColumnWidth(31, 7000);
		sheet.setColumnWidth(32, 7000);
		sheet.setColumnWidth(33, 7000);
		sheet.setColumnWidth(34, 7000);
		sheet.setColumnWidth(35, 7000);
		sheet.setColumnWidth(36, 7000);
		sheet.setColumnWidth(37, 7000);
		sheet.setColumnWidth(38, 7000);
		sheet.setColumnWidth(39, 7000);
		sheet.setColumnWidth(40, 7000);
		sheet.setColumnWidth(41, 7000);
		
		/**
		 * Style for the header cells.
		 */
		
		
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setWrapText(true);
		
		HSSFCellStyle dateCellStyle = wb.createCellStyle();
		DataFormat poiFormat = wb.createDataFormat();
		dateCellStyle.setDataFormat(poiFormat.getFormat("dd-MMM-yyyy"));

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		Date date = new Date();
		dateFormat.format(date);
		
		
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		headerCellStyle2.setFont(f);
		
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(boldFont);
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cellHeader = row.createCell(3);
		cellHeader.setCellStyle(headerCellStyle2);
		cellHeader.setCellValue(new HSSFRichTextString("Candidate Detail Report"));
		
		cellHeader = row.createCell(7);
		cellHeader.setCellStyle(headerCellStyle);

		HSSFRichTextString richString = new HSSFRichTextString("Generated on");
		cellHeader.setCellValue(richString);
		
		cellHeader = row.createCell(9);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(dateFormat.format(date));
		
		row = sheet.createRow(2);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Sr. No."));
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("User ID"));
				
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Post Applied for"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Applicant Name & Initial"));
		
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Gender"));
		
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Date of Birth"));
		
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Community"));
		
		cell = row.createCell(7);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Nativity"));
		
		cell = row.createCell(8);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Other Nativity"));
		
		cell = row.createCell(9);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Differently Abled (Yes / No)"));
		
		cell = row.createCell(10);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Whether Scribe Required"));
		
		cell = row.createCell(11);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Graduation Degree Name"));
		
		cell = row.createCell(12);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Graduation Main Subject"));
		
		cell = row.createCell(13);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Post Graduation Degree Name"));
		
		cell = row.createCell(14);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Post Graduation Main Subject"));
		
		
		cell = row.createCell(15);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Degree in Education Degree Name"));
		
		
		cell = row.createCell(16);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("City"));
		
		cell = row.createCell(17);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("State"));
		
		cell = row.createCell(18);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("E-Mail ID"));
		
		cell = row.createCell(19);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Mobile Number"));
				
		cell = row.createCell(20);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Preferred Test City 1"));
						
		cell = row.createCell(21);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Preferred Test City 2"));
								
		cell = row.createCell(22);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Preferred Test City 3"));
										
		cell = row.createCell(23);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Admit Card Downloaded (Yes / No)"));
				
		cell = row.createCell(24);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Date of Admit Card Download (Latest)"));
														
		cell = row.createCell(25);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Score Card Downloaded (Yes / No)"));
																
		cell = row.createCell(26);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Date of Download Score Card (Latest)"));
																	
		cell = row.createCell(27);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Call Letter Downloaded (Yes / No)"));
																				
		cell = row.createCell(28);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Date of Download Call Letter (Latest)"));
																					
		cell = row.createCell(29);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Payment Status"));
																								
		cell = row.createCell(30);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Payment Amount"));
				
		cell = row.createCell(31);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Basic Registration Date"));
																												
		cell = row.createCell(32);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Application Form Submission Date"));
				
		cell = row.createCell(33);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Successful Payment Date"));

		
		int count = 0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
		for (CandidateBean candidateDetailsBean : candidateDetailsList) {
			
			if (candidateDetailsBean!=null){
				row = sheet.createRow(count+3);
				cell = row.createCell(0);
				cell.setCellValue(count+1);
				
				
				cell = row.createCell(1);
				cell.setCellValue(candidateDetailsBean.getUserId());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(2);
				cell.setCellValue(candidateDetailsBean.getPostName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(3);
				cell.setCellValue(candidateDetailsBean.getFirstName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(4);
				cell.setCellValue(candidateDetailsBean.getPersonalDetailsBean().getGender());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(5);
				cell.setCellStyle(dateCellStyle);
				String DOB = candidateDetailsBean.getPersonalDetailsBean().getDateOfBirth();
				Date OCD_DATE_OF_BIRTH = simpledateformat.parse(DOB);
				cell.setCellValue(OCD_DATE_OF_BIRTH);
				
				cell = row.createCell(6);
				cell.setCellValue(candidateDetailsBean.getPersonalDetailsBean().getCategory());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(7);
				cell.setCellValue(candidateDetailsBean.getNativity());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(8);
				cell.setCellValue(candidateDetailsBean.getOtherNativity());
				cell.setCellStyle(headerCellStyle3);
				
				/*cell = row.createCell(9);
				cell.setCellValue(candidateDetailsBean.getPhysicalDisability());
				cell.setCellStyle(headerCellStyle3);*/
				
				cell = row.createCell(10);
				cell.setCellValue(candidateDetailsBean.getScribeRequired());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(11);
				cell.setCellValue(candidateDetailsBean.getEducationDetailsBean().getUgDegreeName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(12);
				cell.setCellValue(candidateDetailsBean.getEducationDetailsBean().getUgDegreeSubject());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(13);
				cell.setCellValue(candidateDetailsBean.getEducationDetailsBean().getPgDegreeName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(14);
				cell.setCellValue(candidateDetailsBean.getEducationDetailsBean().getPgDegreeSubject());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(15);
				cell.setCellValue(candidateDetailsBean.getEducationDetailsBean().getEduDegreeName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(16);
				cell.setCellValue(candidateDetailsBean.getCityName());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(17);
				cell.setCellValue(candidateDetailsBean.getStateValDesc());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(18);
				cell.setCellValue(candidateDetailsBean.getPersonalDetailsBean().getEmail());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(19);
				cell.setCellValue(candidateDetailsBean.getPersonalDetailsBean().getMobileNo());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(20);
				cell.setCellValue(candidateDetailsBean.getPreferredTestDate1());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(21);
				cell.setCellValue(candidateDetailsBean.getPreferredTestDate2());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(22);
				cell.setCellValue(candidateDetailsBean.getPreferredTestDate3());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(23);
				cell.setCellValue(candidateDetailsBean.getAdmitCardExist());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(24);
				cell.setCellStyle(dateCellStyle);
				String admitCardDate = candidateDetailsBean.getAdmitcardDownloadDate();
				if(admitCardDate != null) {
					Date AdmitCardDate = simpledateformat.parse(admitCardDate);
					cell.setCellValue(AdmitCardDate);
				}else {
					cell.setCellValue("");
				}
				
							
				cell = row.createCell(25);
				cell.setCellValue(candidateDetailsBean.getScoreCardExist());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(26);
				cell.setCellStyle(dateCellStyle);
				String scoreCardDate = candidateDetailsBean.getScoreCardDownloadDate();
				if(scoreCardDate != null) {
					Date scDate = simpledateformat.parse(scoreCardDate);
					cell.setCellValue(scDate);
				}else {
					cell.setCellValue("");
				}
				
				
				cell = row.createCell(27);
				cell.setCellValue(candidateDetailsBean.getCallLetterExist());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(28);
				cell.setCellStyle(dateCellStyle);
				String callLetterDate = candidateDetailsBean.getCallLetterDownloadDate();
				if(callLetterDate != null) {
					Date callDate = simpledateformat.parse(callLetterDate);
					cell.setCellValue(callDate);
				}else {
					cell.setCellValue("");
				}
				
				
				cell = row.createCell(29);
				cell.setCellValue(candidateDetailsBean.getPaymentStatus());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(30);
				cell.setCellValue(candidateDetailsBean.getReceiptAmount());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(31);
				cell.setCellValue(candidateDetailsBean.getRegStrtDate());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(32);
				cell.setCellValue(candidateDetailsBean.getRegFormSubmitedDate());
				cell.setCellStyle(headerCellStyle3);
				
				cell = row.createCell(33);
				cell.setCellValue(candidateDetailsBean.getPaymentTransactionDate());
				cell.setCellStyle(headerCellStyle3);
				
				
				count++;
			}
		}
		
		return wb;
	}
	
	//For export candidate Dump Excel by using XSSFWorkbook
	public HSSFWorkbook createWorkbookForViewCandidateDetails(List<List<String>> dumpData) throws Exception 
	{
		HSSFWorkbook wb= null;
		try{
			wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Candidate Detail Report");
			System.out.println(sheet.getClass().getProtectionDomain().getCodeSource());
			int sizeHeader=dumpData.get(1).size();
			int noOfRows=dumpData.size();
			System.out.println("no of columns= "+sizeHeader+" no of Rows= "+noOfRows);
			
			for(int i=0;i<sizeHeader-1;i++)
			sheet.setColumnWidth(i, 7500);
			
			HSSFCellStyle headerCellStyle = wb.createCellStyle();
			HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
			HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
			headerCellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			Font f = wb.createFont();
			f.setFontHeightInPoints((short) 16);
			headerCellStyle2.setFont(f);
			
			HSSFCellStyle wrapText = wb.createCellStyle();
			wrapText.setWrapText(true);
			
			HSSFFont boldFont = wb.createFont();
			boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			headerCellStyle3.setFont(boldFont);

			for(int i=0;i<noOfRows;i++)
			{
				HSSFRow row = sheet.createRow(i);
				HSSFCell cell =null;
				if(i==0)
				{
					for(int j=0;j<sizeHeader;j++)
					{
					cell = row.createCell(j);
					cell.setCellStyle(headerCellStyle3);
					cell.setCellValue(dumpData.get(i).get(j));
					}
				}
				else
				{	
					for(int j=0;j<sizeHeader;j++)
					{
					cell = row.createCell(j);
					cell.setCellValue(dumpData.get(i).get(j));
					}
				}
			}
			
		}
		catch(Exception e){
			System.out.println("Exception createWorkbookForViewCandidateDetails(): "+e.getMessage());
			e.printStackTrace();
		}
		return wb;
	}

	public HSSFWorkbook createWorkbookForReconcileReport(
			List<PaymentReportBean> paymentCollectionReportDetailList) {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle dateCellStyle = wb.createCellStyle();
		DataFormat poiFormat = wb.createDataFormat();
		dateCellStyle.setDataFormat(poiFormat.getFormat("dd-MMM-yyyy"));
		HSSFSheet sheet = wb.createSheet("Reconciliation Report");
		
		/**
		 * Setting the width of the first three columns.
		 */
		sheet.setColumnWidth(0, 3500);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 7500);
		sheet.setColumnWidth(4, 7500);
		
		/**
		 * Style for the header cells.
		 */
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setWrapText(true);
		
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle.setFont(boldFont);
		
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCell cellHeader = rowHeader.createCell(3);
		cellHeader.setCellStyle(headerCellStyle2);
		cellHeader.setCellValue(new HSSFRichTextString("Reconciliation Report"));
		
		cellHeader = rowHeader.createCell(7);
		cellHeader.setCellStyle(headerCellStyle);

		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		headerCellStyle2.setFont(f);
		HSSFRichTextString richString = new HSSFRichTextString("Generated on");
		cellHeader.setCellValue(richString);

	
         
         
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		
		Date date = new Date();
		dateFormat.format(date);
		
		cellHeader = rowHeader.createCell(10);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(dateFormat.format(date));
		
		HSSFRow row = sheet.createRow(2);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Sr No"));
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Candidate Name"));
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Application Number"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Transaction Serial"));
		
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(new HSSFRichTextString("Reconcilation Status Description"));
		
		for (int count = 0; count < paymentCollectionReportDetailList.size(); count++) {
			row = sheet.createRow(count+3);
			cell = row.createCell(0); 
			
			HSSFCellStyle style = wb.createCellStyle();
			
			PaymentReportBean paymentReportBean = paymentCollectionReportDetailList.get(count);
			
			HSSFRichTextString srNo = new HSSFRichTextString(paymentReportBean.getOUM_USER_ID());
			cell.setCellValue(srNo);
			cell.setCellStyle(style);
			cell = row.createCell(1);
			cell.setCellStyle(headerCellStyle3);
			
			//HSSFRichTextString paymentDate = new HSSFRichTextString(paymentReportBean.getOPD_CREATED_DATE());
			/*String paymentD = paymentReportBean.getOPD_CREATED_DATE();
			Date payamentDate = simpledateformat.parse(paymentD);
			cell.setCellValue(payamentDate);*/
			HSSFRichTextString candNmae = new HSSFRichTextString(paymentReportBean.getCandName());
			cell.setCellValue(candNmae);
			cell.setCellStyle(dateCellStyle);
			cell = row.createCell(2);
			cell.setCellStyle(headerCellStyle3);
			
			HSSFRichTextString appNo = new HSSFRichTextString(paymentReportBean.getAppNo());
			cell.setCellValue(appNo);
			cell.setCellStyle(dateCellStyle);
			cell = row.createCell(3);
			cell.setCellStyle(headerCellStyle3);
			
			HSSFRichTextString tranSer = new HSSFRichTextString(paymentReportBean.getTransSerial());
			cell.setCellValue(tranSer);
			cell.setCellStyle(dateCellStyle);
			cell = row.createCell(4);
			cell.setCellStyle(headerCellStyle3);
			
			HSSFRichTextString reason = new HSSFRichTextString(paymentReportBean.getReason());
			cell.setCellValue(reason);
			cell.setCellStyle(dateCellStyle);
			cell = row.createCell(5);
			cell.setCellStyle(headerCellStyle3);
			
			/*HSSFRichTextString paymentDesc = new HSSFRichTextString(paymentReportBean.getOPTM_PAYMENT_DESC());
			cell.setCellValue(paymentDesc);
			cell = row.createCell(3); 
			
			HSSFRichTextString amount = new HSSFRichTextString(String.valueOf(paymentReportBean.getOPD_AMOUNT()));
			if(!"null".equalsIgnoreCase(String.valueOf(paymentReportBean.getOPD_AMOUNT())) && !String.valueOf(paymentReportBean.getOPD_AMOUNT()).equalsIgnoreCase(""))
			cell.setCellValue(paymentReportBean.getOPD_AMOUNT());
			else{
				cell.setCellValue(0.0);
			}
			cell = row.createCell(4);
			cell.setCellStyle(headerCellStyle3);*/
			
		}
		
		return wb;
	}
	
	public HSSFWorkbook createWorkbookForTestCenterWiseReport(
			List<CandidateBean> candidateDetailsList) throws ParseException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Test City Wise Report");
		
		/**
		 * Setting the width of the first three columns.
		 */
		sheet.setColumnWidth(0, 10000);
		sheet.setColumnWidth(1, 4500);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 7000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 7000);
		sheet.setColumnWidth(10, 7000);
		/*sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 7000);
		sheet.setColumnWidth(13, 7000);
		sheet.setColumnWidth(14, 7000);
		sheet.setColumnWidth(15, 7000);
		sheet.setColumnWidth(16, 7000);
		sheet.setColumnWidth(17, 7000);
		sheet.setColumnWidth(18, 7000);
		sheet.setColumnWidth(19, 7000);
		sheet.setColumnWidth(20, 7000);
		sheet.setColumnWidth(21, 7000);
		sheet.setColumnWidth(22, 7000);
		sheet.setColumnWidth(23, 7000);
		sheet.setColumnWidth(24, 7000);
		sheet.setColumnWidth(25, 7000);
		sheet.setColumnWidth(26, 7000);
		sheet.setColumnWidth(27, 7000);
		sheet.setColumnWidth(28, 7000);
		sheet.setColumnWidth(29, 7000);
		sheet.setColumnWidth(30, 7000);
		sheet.setColumnWidth(31, 7000);
		sheet.setColumnWidth(32, 7000);
		
		sheet.setColumnWidth(33, 7000);
		sheet.setColumnWidth(34, 7000);
		sheet.setColumnWidth(35, 7000);
		sheet.setColumnWidth(36, 7000);
		*/
		/**
		 * Style for the header cells.
		 */
		
		
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle dateCellStyle = wb.createCellStyle();
		HSSFCellStyle wrapText = wb.createCellStyle();
		wrapText.setWrapText(true);
		
		DataFormat poiFormat = wb.createDataFormat();
		dateCellStyle.setDataFormat(poiFormat.getFormat("dd-MMM-yyyy"));

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		Date date = new Date();
		dateFormat.format(date);
		
		
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		headerCellStyle2.setFont(f);
		
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle3.setFont(boldFont);
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cellHeader = row.createCell(3);
		cellHeader.setCellStyle(headerCellStyle2);
		cellHeader.setCellValue(new HSSFRichTextString("Test City Wise Report"));
		
		cellHeader = row.createCell(7);
		cellHeader.setCellStyle(headerCellStyle);

		HSSFRichTextString richString = new HSSFRichTextString("Generated on");
		cellHeader.setCellValue(richString);
		
		cellHeader = row.createCell(9);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(dateFormat.format(date));
		
		row = sheet.createRow(2);
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("Name of City"));
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("Post Graduate Assistants"));
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("Physical Education Director Grade - 1"));
		
		//cell = row.createCell(3);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Assistant (Junior Assistant)"));
		
		/*cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("AM(SYS-DOT NET)"));
		
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("AM(SYS-NETWORKING)"));*/
		
//		cell = row.createCell(3);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Typist"));
		
		/*cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("MT(HR/ADMIN)"));
		
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("MT(F&A)"));*/
		
//		cell = row.createCell(7);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Chennai"));
	//	
//		cell = row.createCell(8);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Dehradun"));
	//	
//		cell = row.createCell(9);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Guwahati"));
	//	
//		cell = row.createCell(10);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Hyderabad"));
	//	
//		cell = row.createCell(11);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Imphal"));
	//	
//		cell = row.createCell(12);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Jaipur"));
	//	
//		cell = row.createCell(13);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Jamshedpur"));
	//	
//		cell = row.createCell(14);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Kolkata"));
	//	
	//	
//		cell = row.createCell(15);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Lucknow"));
	//	
//		cell = row.createCell(16);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Mumbai"));
	//	
//		cell = row.createCell(17);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("New Delhi"));
	//	
//		cell = row.createCell(18);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Patna"));
	//	
//		cell = row.createCell(19);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Pune"));
	//	
//		cell = row.createCell(20);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Raipur"));
	//
//		cell = row.createCell(21);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Ranchi"));
	//	
//		cell = row.createCell(22);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Shillong"));
	//	
//		cell = row.createCell(23);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Shimala"));
	//	
//		cell = row.createCell(24);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Srinagar"));
	//	
//		cell = row.createCell(25);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Trichy"));
	//	
//		cell = row.createCell(26);
//		cell.setCellStyle(headerCellStyle3);
//		cell.setCellValue(new HSSFRichTextString("Vizag"));
	//	
		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("Grand Total"));
		

				
		int count = 0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
	for (CandidateBean candidateDetailsBean : candidateDetailsList) {
			
			if (candidateDetailsBean!=null){
				row = sheet.createRow(count+3);
				cell = row.createCell(0);
				cell.setCellValue(candidateDetailsBean.getOtmPostName());
				cell.setCellStyle(wrapText);
				
			
				cell = row.createCell(1);
				cell.setCellValue(candidateDetailsBean.getAm_java());
				cell.setCellStyle(wrapText);
				
				cell = row.createCell(2);
				cell.setCellValue(candidateDetailsBean.getAm_dotnet());
				
				//cell = row.createCell(3);
				//cell.setCellValue(candidateDetailsBean.getAm_networking());
				
				//cell = row.createCell(4);
				//cell.setCellValue(candidateDetailsBean.getMt_adminhr());

				
				cell = row.createCell(3);
				cell.setCellValue(candidateDetailsBean.getTotal());
				
				/*cell = row.createCell(6);
				cell.setCellValue(candidateDetailsBean.getMt_fa());*/
				
//				cell = row.createCell(7);
//				cell.setCellValue(candidateDetailsBean.getChennai());
//				
	//
//				cell = row.createCell(8);
//				cell.setCellValue(candidateDetailsBean.getDehradun());
//				
	//
//				
//				cell = row.createCell(9);
//				cell.setCellValue(candidateDetailsBean.getGuwahati());
//				
	//
//				cell = row.createCell(10);
//				cell.setCellValue(candidateDetailsBean.getHyderabad());
//				
//				cell = row.createCell(11);
//				cell.setCellValue(candidateDetailsBean.getImphal());
//				
	//
//				cell = row.createCell(12);
//				cell.setCellValue(candidateDetailsBean.getJaipur());
//				
//				cell = row.createCell(13);
//				cell.setCellValue(candidateDetailsBean.getJamshedpur());
//				
//				cell = row.createCell(14);
//				cell.setCellValue(candidateDetailsBean.getKolkatta());
//				
//				cell = row.createCell(15);
//				cell.setCellValue(candidateDetailsBean.getLucknow());
//				
//				cell = row.createCell(16);
//				cell.setCellValue(candidateDetailsBean.getMumbai());
//				
//				cell = row.createCell(17);
//				cell.setCellValue(candidateDetailsBean.getNew_delhi());
//				
//				cell = row.createCell(18);
//				cell.setCellValue(candidateDetailsBean.getPatna());
//				
//				cell = row.createCell(19);
//				cell.setCellValue(candidateDetailsBean.getPune());
//				
//				cell = row.createCell(20);
//				cell.setCellValue(candidateDetailsBean.getRaipur());
//				
//				cell = row.createCell(21);
//				cell.setCellValue(candidateDetailsBean.getRanchi());
//				
//				cell = row.createCell(22);
//				cell.setCellValue(candidateDetailsBean.getShillong());
//				
//				cell = row.createCell(23);
//				cell.setCellValue(candidateDetailsBean.getShimla());
//				
//				cell = row.createCell(24);
//				cell.setCellValue(candidateDetailsBean.getSrinagar());
////				
//				cell = row.createCell(25);
//				cell.setCellValue(candidateDetailsBean.getTrichy());
//				
//				cell = row.createCell(26);
//				cell.setCellValue(candidateDetailsBean.getVizag());
//				
//			
				//cell = row.createCell(6);
				//cell.setCellValue(candidateDetailsBean.getTotal());
								
				count++;
			}
		}
		
		return wb;
	}


	public HSSFWorkbook createWorkbookForCategoryWiseReport(
			List<CandidateBean> candidateDetailsList) throws ParseException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Category Wise Report");
		
		/**
		 * Setting the width of the first three columns.
		 */
		sheet.setColumnWidth(0, 15000);
		sheet.setColumnWidth(1, 9000);
		sheet.setColumnWidth(2, 7000);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 7000);
		sheet.setColumnWidth(5, 7000);
		sheet.setColumnWidth(6, 7000);
		sheet.setColumnWidth(7, 7000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 7000);
		sheet.setColumnWidth(10, 7000);
		sheet.setColumnWidth(11, 7000);
		sheet.setColumnWidth(12, 7000);
		sheet.setColumnWidth(13, 9000);
		sheet.setColumnWidth(14, 9000);
		
		/**
		 * Style for the header cells.
		 */
		
		
		HSSFCellStyle headerCellStyle = wb.createCellStyle();
		HSSFCellStyle headerCellStyle2 = wb.createCellStyle();
		HSSFCellStyle headerCellStyle3 = wb.createCellStyle();
		headerCellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle dateCellStyle = wb.createCellStyle();
		HSSFCellStyle wrapText = wb.createCellStyle();
		wrapText.setWrapText(true);
		
		DataFormat poiFormat = wb.createDataFormat();
		dateCellStyle.setDataFormat(poiFormat.getFormat("dd-MMM-yyyy"));

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
		Date date = new Date();
		dateFormat.format(date);
		
		
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 16);
		headerCellStyle2.setFont(f);
		
		HSSFFont boldFont = wb.createFont();
		boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerCellStyle3.setFont(boldFont);
		
		HSSFRow row = sheet.createRow(0);
		HSSFCell cellHeader = row.createCell(2);
		cellHeader.setCellStyle(headerCellStyle2);
		cellHeader.setCellValue(new HSSFRichTextString("Category Wise Report"));
		
		cellHeader = row.createCell(6);
		cellHeader.setCellStyle(headerCellStyle);

		HSSFRichTextString richString = new HSSFRichTextString("Generated on");
		cellHeader.setCellValue(richString);
		
		cellHeader = row.createCell(8);
		cellHeader.setCellStyle(headerCellStyle);
		cellHeader.setCellValue(dateFormat.format(date));
		
		row = sheet.createRow(2);
		
		HSSFCell cell = row.createCell(0);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("Name of Post"));
		
		/*cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("General"));*/
		
		cell = row.createCell(1);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("OC"));
		
		cell = row.createCell(2);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("SC"));
		
		/*cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("SC (A)"));*/

		cell = row.createCell(3);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("ST"));
		
		cell = row.createCell(4);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("MBC / DNC"));
		
		cell = row.createCell(5);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("BC"));
		
		cell = row.createCell(6);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("BCM"));
		
		cell = row.createCell(7);
		cell.setCellStyle(headerCellStyle3);
		cell.setCellValue(new HSSFRichTextString("Total"));
		
				
		int count = 0;
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
		for (CandidateBean candidateDetailsBean : candidateDetailsList) {
			
			if (candidateDetailsBean!=null){
				row = sheet.createRow(count+3);
				cell = row.createCell(0);
				cell.setCellValue(candidateDetailsBean.getOtmPostName());
				
				/*cell = row.createCell(1);
				cell.setCellValue(candidateDetailsBean.getGeneral_unreserved());*/
				
				cell = row.createCell(1);
				cell.setCellValue(candidateDetailsBean.getObc());
				cell.setCellStyle(wrapText);
				
				cell = row.createCell(2);
				cell.setCellValue(candidateDetailsBean.getSc());
				
				/*cell = row.createCell(3);
				cell.setCellValue(candidateDetailsBean.getSca());*/
				
				cell = row.createCell(3);
				cell.setCellValue(candidateDetailsBean.getSt());

				cell = row.createCell(4);
				cell.setCellValue(candidateDetailsBean.getMbc());
				
				cell = row.createCell(5);
				cell.setCellValue(candidateDetailsBean.getBc());
				
				cell = row.createCell(6);
				cell.setCellValue(candidateDetailsBean.getBcm());
				
				cell = row.createCell(7);
				cell.setCellValue(candidateDetailsBean.getTotal());
				
				count++;
			}
		}
		
		return wb;
	}


	
}
