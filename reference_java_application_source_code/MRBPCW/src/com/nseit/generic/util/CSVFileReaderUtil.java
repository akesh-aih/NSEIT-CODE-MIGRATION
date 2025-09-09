package com.nseit.generic.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.csvreader.CsvReader;

public class CSVFileReaderUtil {

	public static List<HashMap<String, String>> readExcelFile(String fileName)
			throws IOException {
		File inputWorkbook = new File(fileName);
		Workbook w;
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			HashMap<String, String> cols = new HashMap<String, String>();
			for (int i = 0; i < sheet.getRows(); i++) {
				LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					String contents = cell.getContents();
					if (i == 0) {
						cols.put(j + "", contents);
					} else {
						row.put(cols.get(j + ""), contents);
					}
				}
				if (row.size() > 0) {
					list.add(row);
				}
			}
			// System.out.println(cols);
			// System.out.println(list);
			/*
			 * for (HashMap<String, String> row : list) {
			 * System.out.println(row); }
			 */
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static LinkedHashMap<String, String[]> readCSVFile(String fileName) throws IOException {
		LinkedHashMap<String, String[]> cols = new LinkedHashMap<String, String[]>();
		try {
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			csvReader.readHeaders();
			if(csvReader != null){
				while (csvReader.readRecord()) {
					String[] col = csvReader.getRawRecord().split(",");
					cols.put(csvReader.getCurrentRecord()+"", col);
				}
			}
			
/*			Set<String> setMarks = cols.keySet();
			for (String stringSetMarks : setMarks) {
				String[] valueLHM = cols.get(stringSetMarks);
				for (int i = 0; i < valueLHM.length; i++) {
					System.out.println("valueLHM["+i+"] --- "+valueLHM[i].toString());
				}
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return cols;
	}
	
	public static boolean isValidCSVFile(String fileName) throws IOException {
		boolean isValidCSV = true;
		try {
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			csvReader.readHeaders();
			if(csvReader != null){
				csvReader.readRecord();
				while (csvReader.readRecord()) {
					String postOfc = csvReader.get("POST OFFICE");
					if(postOfc==null || "".equals(postOfc)){
						isValidCSV = false;
						break;
					}
					String pincode = csvReader.get("PINCODE");
					if(pincode==null || "".equals(pincode)){
						isValidCSV = false;
						break;
					}
					String name = csvReader.get("NAME OF CANDIDATE");
					if(name==null || "".equals(name)){
						isValidCSV = false;
						break;
					}
					String appNo = csvReader.get("APPLICATION NO.");
					if(appNo==null || "".equals(appNo)){
						isValidCSV = false;
						break;
					}
					String advtNo = csvReader.get("ADVT. NO");
					if(advtNo==null || " ".equals(advtNo)){
						isValidCSV = false;
						break;
					}
					String accDt = csvReader.get("ACCOUNT DATE");
					if(accDt==null || "".equals(accDt)){
						isValidCSV = false;
						break;
					}
					String fee = csvReader.get("FEES");
					if(fee==null || "".equals(fee)){
						isValidCSV = false;
						break;
					}
					String receipt = csvReader.get("RECEIPT NUMBER");
					if(receipt==null || "".equals(receipt)){
						isValidCSV = false;
						break;
					}
					String tranDt = csvReader.get("TRANSACTION DATE");
					if(tranDt==null || "".equals(tranDt)){
						isValidCSV = false;
						break;
					}
					String tranSrl = csvReader.get("TRANS SERIAL");
					if(tranSrl==null || "".equals(tranSrl)){
						isValidCSV = false;
						break;
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return isValidCSV;
	}

	public static LinkedHashMap<String, String[]> readWrittenTestScoreCSVFile(String fileName) {
		LinkedHashMap<String, String[]> cols = new LinkedHashMap<String, String[]>();
		try
		{
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			boolean headers=false;
			
			String[] columns = {"Candidate ID","Enrollment ID","Test Center ID","Test Center Name","Module ID","Test Date","Test Time","Marks Obtain","Exam Status","Eligible For Field Test","Candidate Name"};
			
			if(csvReader != null && csvReader.readRecord())
			{
				String[] col = csvReader.getRawRecord().split(",");
				System.out.println("col :: "+csvReader.getRawRecord());
				for (int i = 0; i < columns.length; i++) {
					try{
					if(col[i]!=null && col[i].trim().equalsIgnoreCase(columns[i]))
					{
						headers=true;
					}
					else
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
						break;
					}
					}catch(ArrayIndexOutOfBoundsException ae)
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
					}
					
				}
			}
			if(csvReader != null && headers){
				while (csvReader.readRecord()) {
					String[] col = csvReader.getRawRecord().split(",");
					cols.put(csvReader.getCurrentRecord()+"", col);
					System.out.println("Record : "+csvReader.getRawRecord());
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return cols;
	}

	public static LinkedHashMap<String, String[]> readScheduleCSVFile(
			String fileName) {
		LinkedHashMap<String, String[]> cols = new LinkedHashMap<String, String[]>();
		
		try
		{
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			boolean headers=false;
			
			String[] columns = {"Client Code","Enrollment ID","Prefix","First Name","Father's Name","Last Name","Phone","Mobile No","Email","Test City","Date of Birth","Address/ Unit","Pincode","City","Other City","State","Exam Center","Exam Date","Exam Time","Module","Test Center ID","Roll No","Password","Photo","Sign","Gender","Education","Graduation Percentage","Client Roll No","Client Reg No","Room No","Grace Time Allowed","Grace Time","Category","Test City Zone","Exemption ID 1","Exemption ID 2","Candidate Status"};
			
			if(csvReader != null && csvReader.readRecord())
			{
				String[] col = csvReader.getRawRecord().split(",");
				System.out.println("col :: "+csvReader.getRawRecord());
				for (int i = 0; i < columns.length; i++) {
					try{
					if(col[i]!=null && col[i].trim().equalsIgnoreCase(columns[i]))
					{
						headers=true;
					}
					else
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
						break;
					}
					}catch(ArrayIndexOutOfBoundsException ae)
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
					}
					
				}
			}
			if(csvReader != null && headers){
				while (csvReader.readRecord()) {
					String[] col = csvReader.getRawRecord().split(",");
					cols.put(csvReader.getCurrentRecord()+"", col);
					System.out.println("Record : "+csvReader.getRawRecord());
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return cols;
	}
	
	public static LinkedHashMap<String, String[]> readFieldTestScheduleCSVFile(
			String fileName) {
		LinkedHashMap<String, String[]> cols = new LinkedHashMap<String, String[]>();
		
		try
		{
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			boolean headers=false;
			
			String[] columns = {"Client Code","Enrollment ID","Prefix","First Name","Father's Name","Last Name","Field Test City","Test City ID","Field Test Date","Field Test Reporting Time","Module"};
			
			if(csvReader != null && csvReader.readRecord())
			{
				String[] col = csvReader.getRawRecord().split(",");
				System.out.println("col :: "+csvReader.getRawRecord());
				for (int i = 0; i < columns.length; i++) {
					try{
					if(col[i]!=null && col[i].trim().equalsIgnoreCase(columns[i]))
					{
						headers=true;
					}
					else
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
						break;
					}
					}catch(ArrayIndexOutOfBoundsException ae)
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
					}
					
				}
			}
			if(csvReader != null && headers){
				while (csvReader.readRecord()) {
					String[] col = csvReader.getRawRecord().split(",");
					cols.put(csvReader.getCurrentRecord()+"", col);
					System.out.println("Record : "+csvReader.getRawRecord());
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return cols;
	}

	public static LinkedHashMap<String, String[]> readFieldTestScoreCSVFile(
			String fileName) {
		LinkedHashMap<String, String[]> cols = new LinkedHashMap<String, String[]>();
		try
		{
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			boolean headers=false;
			
			String[] columns = {"Candidate ID","Field Test City","Module ID","Field Test Date","Marks Obtain","Exam Status","Eligible For Interview"};
			
			if(csvReader != null && csvReader.readRecord())
			{
				String[] col = csvReader.getRawRecord().split(",");
				System.out.println("col :: "+csvReader.getRawRecord());
				for (int i = 0; i < columns.length; i++) {
					try{
					if(col[i]!=null && col[i].trim().equalsIgnoreCase(columns[i]))
					{
						headers=true;
					}
					else
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
						break;
					}
					}catch(ArrayIndexOutOfBoundsException ae)
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
					}
					
				}
			}
			if(csvReader != null && headers){
				while (csvReader.readRecord()) {
					String[] col = csvReader.getRawRecord().split(",");
					cols.put(csvReader.getCurrentRecord()+"", col);
					System.out.println("Record : "+csvReader.getRawRecord());
				}
			}
		}
		catch(Exception ex)
		{
		ex.printStackTrace();	
		}
		return cols;
	}

	public static LinkedHashMap<String, String[]> readInterviewcoreCSVFile(
			String fileName) {
		LinkedHashMap<String, String[]> cols = new LinkedHashMap<String, String[]>();
		try
		{
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			boolean headers=false;
			
			String[] columns = {"Candidate ID","Interview City","Module ID","Interview Date","Marks Obtain","Exam Status","Eligible For Medical"};
			
			if(csvReader != null && csvReader.readRecord())
			{
				String[] col = csvReader.getRawRecord().split(",");
				System.out.println("col :: "+csvReader.getRawRecord());
				for (int i = 0; i < columns.length; i++) {
					try{
					if(col[i]!=null && col[i].trim().equalsIgnoreCase(columns[i]))
					{
						headers=true;
					}
					else
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
						break;
					}
					}catch(ArrayIndexOutOfBoundsException ae)
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
					}
					
				}
			}
			if(csvReader != null && headers){
				while (csvReader.readRecord()) {
					String[] col = csvReader.getRawRecord().split(",");
					cols.put(csvReader.getCurrentRecord()+"", col);
					System.out.println("Record : "+csvReader.getRawRecord());
				}
			}
		}
		catch(Exception ex)
		{
		ex.printStackTrace();	
		}
		return cols;
	}

	public static LinkedHashMap<String, String[]> readMedicalScoreCSVFile(
			String fileName) {
		LinkedHashMap<String, String[]> cols = new LinkedHashMap<String, String[]>();
		try
		{
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			boolean headers=false;
			
			String[] columns = {"Candidate ID","Medical Test City","Module ID","Medical Test Date","Exam Status","Eligible For Final Result"};
			
			if(csvReader != null && csvReader.readRecord())
			{
				String[] col = csvReader.getRawRecord().split(",");
				System.out.println("col :: "+csvReader.getRawRecord());
				for (int i = 0; i < columns.length; i++) {
					try{
					if(col[i]!=null && col[i].trim().equalsIgnoreCase(columns[i]))
					{
						headers=true;
					}
					else
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
						break;
					}
					}catch(ArrayIndexOutOfBoundsException ae)
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
					}
					
				}
			}
			if(csvReader != null && headers){
				while (csvReader.readRecord()) {
					String[] col = csvReader.getRawRecord().split(",");
					cols.put(csvReader.getCurrentRecord()+"", col);
					System.out.println("Record : "+csvReader.getRawRecord());
				}
			}
		}
		catch(Exception ex)
		{
		ex.printStackTrace();	
		}
		return cols;
	}

	public static LinkedHashMap<String, String[]> readInterviewScheduleCSVFile(
			String fileName) {
		LinkedHashMap<String, String[]> cols = new LinkedHashMap<String, String[]>();
		try
		{
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			boolean headers=false;
			
			String[] columns = {"Client Code","Enrollment ID","Prefix","First Name","Father's Name","Last Name","Interview City","Test City ID","Interview Location","Interview Address","Interview Date","Interview Reporting Time","Module"};
			
			if(csvReader != null && csvReader.readRecord())
			{
				String[] col = csvReader.getRawRecord().split(",");
				System.out.println("col :: "+csvReader.getRawRecord());
				for (int i = 0; i < columns.length; i++) {
					try{
					if(col[i]!=null && col[i].trim().equalsIgnoreCase(columns[i]))
					{
						headers=true;
					}
					else
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
						break;
					}
					}catch(ArrayIndexOutOfBoundsException ae)
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
					}
					
				}
			}
			if(csvReader != null && headers){
				while (csvReader.readRecord()) {
					String[] col = csvReader.getRawRecord().split(",");
					cols.put(csvReader.getCurrentRecord()+"", col);
					System.out.println("Record : "+csvReader.getRawRecord());
				}
			}
		}
		catch(Exception ex)
		{
		ex.printStackTrace();	
		}
		return cols;
	}

	public static LinkedHashMap<String, String[]> readMedicalScheduleCSVFile(
			String fileName) {
		LinkedHashMap<String, String[]> cols = new LinkedHashMap<String, String[]>();
		try
		{
			CsvReader csvReader = null;
			csvReader = new CsvReader(fileName);
			boolean headers=false;
			
			String[] columns = {"Client Code","Enrollment ID","Prefix","First Name","Father's Name","Last Name","Medical Test City","Test City ID","Medical Test Location","Medical Test Address","Medical Test Date","Medical Test Reporting Time","Module"};
			
			if(csvReader != null && csvReader.readRecord())
			{
				String[] col = csvReader.getRawRecord().split(",");
				System.out.println("col :: "+csvReader.getRawRecord());
				for (int i = 0; i < columns.length; i++) {
					try{
					if(col[i]!=null && col[i].trim().equalsIgnoreCase(columns[i]))
					{
						headers=true;
					}
					else
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
						break;
					}
					}catch(ArrayIndexOutOfBoundsException ae)
					{
						headers=false;
						String[] col1={"Column Headers do not match the template"};
						cols.put("HEADER", col1);
					}
					
				}
			}
			if(csvReader != null && headers){
				while (csvReader.readRecord()) {
					String[] col = csvReader.getRawRecord().split(",");
					cols.put(csvReader.getCurrentRecord()+"", col);
					System.out.println("Record : "+csvReader.getRawRecord());
				}
			}
		}
		catch(Exception ex)
		{
		ex.printStackTrace();	
		}
		return cols;
	}
	
}
