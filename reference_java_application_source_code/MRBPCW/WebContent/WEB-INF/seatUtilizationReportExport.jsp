<%@ page trimDirectiveWhitespaces="true" %>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.nseit.generic.util.ExcelCreator"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import="java.io.OutputStream"%>
<%
	List<List> list = (List<List>) session
			.getAttribute("reportList");
	if (list != null && list.size() > 0) {
		HSSFWorkbook workbook = ExcelCreator
				.createSeatUitlizationREport(list);
		String fileName = "SeatUtilizationReport.xls";
	OutputStream osStream =null;
		try {
			response.setHeader("Content-Disposition",
					"attachment; filename=" + fileName);
			response.setContentType("application/vnd.ms-excel");
			osStream= response.getOutputStream();
			workbook.write(osStream);
			osStream.flush();
		} catch (Exception e) {
		}finally{
		osStream.close();
		}
	}
%>