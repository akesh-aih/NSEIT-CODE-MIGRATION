<%@page import="com.nseit.generic.models.Pagination"%>
<%
					Pagination pagination = (Pagination) request.getAttribute("pagination");
					boolean previous = false;
					if(pagination.getPage_number() > 1){
						previous = true;
					}
					boolean next = true;
					if(pagination.getTotal_pages() == pagination.getPage_number()){
						next = false;
					}
				%>

		<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
		<table width="50%" align="right">
			<tr>
			  <td class="pagination-label" width="100%" nowrap="nowrap">
<%--			    <font color="red"><%=pagination.getPage_records() %></font> Candidates Found--%>
			  </td>
			  <td>
			  	<%if(previous){%>
			  	<a href="#" onclick="functionPagination(4,<%=pagination.getTotal_pages()%>);">
			  		<img src="images/left_end.gif" alt="Go to first page" width="15" height="19"/>
			  	</a>
			  	<%}else{ %>
			  		<img src="images/left_end_gray.gif" alt="Go to first page" width="15" height="19"/>
			  	<%} %>
			  </td>
			  <td>
			  	<%if(previous){%>
			  	<a href="#" onclick="functionPagination(3,<%=pagination.getTotal_pages()%>);">
			  		<img src="images/left.gif" alt="Go to first page" width="15" height="19"/>
			  	</a>
			  	<%}else{ %>
			  		<img src="images/left_gray.gif" alt="Go to first page" width="15" height="19"/>
			  	<%} %>
			  </td>
			  <td class="pagination-label" nowrap="nowrap">Page:</td>
			  <td>
			  	<input name="pagination.page_number" id="page_number" class="pagination-textbox" readonly="readonly"
			  	style="width: 10px;" maxlen="1" value="<%=pagination.getPage_number()%>" type="hidden"/><%=pagination.getPage_number()%></td>
			  <td class="pagination-label" nowrap="nowrap">of <%=pagination.getTotal_pages()%></td>
			  
			  <td>
			  	<% if(next){ %>
			  	<a href="#" onclick="functionPagination(1,<%=pagination.getTotal_pages()%>);">
			  		<img src="images/right.gif" alt="Go to next page" border="0" width="15" height="19"/>
			  	</a>
			  	<%}else{ %>
			  		<img src="images/right_gray.gif" alt="Go to next page" border="0" width="15" height="19"/>
			  	<%} %>
			  </td>
			  <td><% if(next){ %>
			  	<a href="#" onclick="functionPagination(2,<%=pagination.getTotal_pages()%>);">
			  		<img src="images/right_end.gif" alt="Go to next page" border="0" width="15" height="19"/>
			  	</a>
			  	<%}else{ %>
			  		<img src="images/right_end_gray.gif" alt="Go to next page" border="0" width="15" height="19"/>
			  	<%} %>
			  </td>
			  <td>&nbsp;</td>
			  <td class="pagination-label" nowrap="nowrap">Show:</td>
			  <td class="pagination-linkoff" style="" nowrap="nowrap">
			  	<s:select onchange="functionPagination(5,0);" list="#{'10':'10','25':'25','50':'50','100':'100'}" theme="simple" name="pagination.page_size" id="page_size" value="#request.pagination.page_size"/>
			  </td>
			</tr>
		</table>
