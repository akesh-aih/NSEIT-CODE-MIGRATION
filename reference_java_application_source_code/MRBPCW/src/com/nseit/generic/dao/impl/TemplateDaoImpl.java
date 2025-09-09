package com.nseit.generic.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.TemplateDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateDetailsBean;
import com.nseit.generic.models.CandidateDocBean;
import com.nseit.generic.models.TemplateDownloadBean;
import com.nseit.generic.queries.TemplateDownloadQueries;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;

public class TemplateDaoImpl extends BaseDAO implements TemplateDao{

	
	@SuppressWarnings("unchecked")
	public TemplateDownloadBean getTemplate(String templateDesc) throws GenericException {
		List<TemplateDownloadBean> templateList = null;
		
          try {
        	  templateList = getJdbcTemplate().query(TemplateDownloadQueries.GET_TEMPLATE,new Object[]{templateDesc},new RowMapper<TemplateDownloadBean>() {

  				@Override
  				public TemplateDownloadBean mapRow(ResultSet rs, int rowCount)throws SQLException{
  					TemplateDownloadBean templateDownloadBean= new TemplateDownloadBean();
  					templateDownloadBean.setTemplateDocumentByte(rs.getBytes("OTMP_TEMPLATE"));
  					return templateDownloadBean;
  				}
  			});
  		} catch (DataAccessException e) {
  			throw new GenericException(e);
  		}
  		
  		if (templateList!=null && !templateList.isEmpty()){
  			return templateList.get(0);
  		}else {
  			return null;
  		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CandidateBean>  getCandidateImage() throws Exception{
		List<CandidateBean> lstImageAndSignature;
		try{
				lstImageAndSignature =  getJdbcTemplate().query(
					TemplateDownloadQueries.GET_CANDIDATE_IMAGE,
					new Object[] {}, new RowMapper<CandidateBean>(){
						
		             public CandidateBean mapRow(ResultSet rs, int rowNum){   
		            		CandidateBean imageDetailBean = new CandidateBean();
		            		ByteArrayInputStream  byteArrayInputStreamForImage = null;
		            		try {
		            			if(rs.getBytes("OCI_PHOTO_IMAGE") != null){
		            				 byteArrayInputStreamForImage = new ByteArrayInputStream(rs.getBytes("OCI_PHOTO_IMAGE"),1, rs.getBytes("OCI_PHOTO_IMAGE").length);
				    				 imageDetailBean.setInputStreamForImage((InputStream)byteArrayInputStreamForImage);
			    				}
		            			if (rs.getBytes("OCI_PHOTO_IMAGE")!=null){
		            				imageDetailBean.setPhotoByte(rs.getBytes("OCI_PHOTO_IMAGE"));
		            			}
		            			if (rs.getString("OCI_CANDIDATE_IMAGE_PK")!=null && !rs.getString("OCI_CANDIDATE_IMAGE_PK").equals("")){
		            				imageDetailBean.setCandidateImagePK(rs.getString("OCI_CANDIDATE_IMAGE_PK"));
		            			}
		            			if (rs.getString("OUM_USER_ID")!=null && !rs.getString("OUM_USER_ID").equals("")){
		            				imageDetailBean.setUserId(rs.getString("OUM_USER_ID"));
		            			}
							}
		            		catch (SQLException e){
		            			LoggerHome.getLogger(getClass()).fatal(e, e);
							} catch (Throwable e) {
								LoggerHome.getLogger(getClass()).fatal(e, e);
							}
		            		
		            		return imageDetailBean;
		                 }   
		            });
		}catch (Exception e) {
			throw new GenericException(e);
		}
		
		
		if(lstImageAndSignature != null && !lstImageAndSignature.isEmpty()){
			return lstImageAndSignature;
		}
		else{
			return  null;
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CandidateBean>  getCandidateImageForScheduling() throws Exception{
		List<CandidateBean> lstImageAndSignature;
		try{
				lstImageAndSignature =  getJdbcTemplate().query(
					TemplateDownloadQueries.GET_CANDIDATE_IMAGE_FOR_SCHEDULING,
					new Object[] {}, new RowMapper<CandidateBean>(){
						
		             public CandidateBean mapRow(ResultSet rs, int rowNum){   
		            		CandidateBean imageDetailBean = new CandidateBean();
		            		ByteArrayInputStream  byteArrayInputStreamForImage = null;
		            		try {
		            			if(rs.getBytes("OCI_PHOTO_IMAGE") != null){
		            				 byteArrayInputStreamForImage = new ByteArrayInputStream(rs.getBytes("OCI_PHOTO_IMAGE"), rs.getBytes("OCI_PHOTO_IMAGE").length, 1);
				    				 imageDetailBean.setInputStreamForImage((InputStream)byteArrayInputStreamForImage);
			    				}
		            			if (rs.getBytes("OCI_PHOTO_IMAGE")!=null){
		            				imageDetailBean.setPhotoByte(rs.getBytes("OCI_PHOTO_IMAGE"));
		            			}
		            			if (rs.getString("OCI_CANDIDATE_IMAGE_PK")!=null && !rs.getString("OCI_CANDIDATE_IMAGE_PK").equals("")){
		            				imageDetailBean.setCandidateImagePK(rs.getString("OCI_CANDIDATE_IMAGE_PK"));
		            			}
		            			if (rs.getString("OUM_USER_ID")!=null && !rs.getString("OUM_USER_ID").equals("")){
		            				imageDetailBean.setUserId(rs.getString("OUM_USER_ID"));
		            			}
							}
		            		catch (SQLException e){
		            			LoggerHome.getLogger(getClass()).fatal(e, e);
							} catch (Throwable e) {
								LoggerHome.getLogger(getClass()).fatal(e, e);
							}
		            		
		            		return imageDetailBean;
		                 }   
		            });
		}catch (Exception e) {
			throw new GenericException(e);
		}
		
		
		if(lstImageAndSignature != null && !lstImageAndSignature.isEmpty()){
			return lstImageAndSignature;
		}
		else{
			return  null;
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CandidateBean>  getCandidateSignature() throws Exception{
		List<CandidateBean> lstImageAndSignature;
		try{
				lstImageAndSignature =  getJdbcTemplate().query(
					TemplateDownloadQueries.GET_CANDIDATE_SIGN,
					new Object[] {}, new RowMapper<CandidateBean>(){
						
		             public CandidateBean mapRow(ResultSet rs, int rowNum){   
		            		CandidateBean imageDetailBean = new CandidateBean();
		            		ByteArrayInputStream  byteArrayInputStreamForImage = null;
		            		try {
		            			if(rs.getBytes("OCI_SIGN_IMAGE") != null){
		            				 byteArrayInputStreamForImage = new ByteArrayInputStream(rs.getBytes("OCI_SIGN_IMAGE"), rs.getBytes("OCI_SIGN_IMAGE").length, 1);
				    				 imageDetailBean.setInputStreamForImage((InputStream)byteArrayInputStreamForImage);
			    				}
		            			if (rs.getBytes("OCI_SIGN_IMAGE")!=null){
		            				imageDetailBean.setSignByte(rs.getBytes("OCI_SIGN_IMAGE"));
		            			}
		            			if (rs.getString("OCI_CANDIDATE_IMAGE_PK")!=null && !rs.getString("OCI_CANDIDATE_IMAGE_PK").equals("")){
		            				imageDetailBean.setCandidateImagePK(rs.getString("OCI_CANDIDATE_IMAGE_PK"));
		            			}
		            			if (rs.getString("OUM_USER_ID")!=null && !rs.getString("OUM_USER_ID").equals("")){
		            				imageDetailBean.setUserId(rs.getString("OUM_USER_ID"));
		            			}
							}
		            		catch (SQLException e){
		            			LoggerHome.getLogger(getClass()).fatal(e, e);
							} catch (Throwable e) {
								LoggerHome.getLogger(getClass()).fatal(e, e);
							}
		            		return imageDetailBean;
		                 }   
		            });
		}catch (Exception e) {
			throw new GenericException(e);
		}
		
		
		if(lstImageAndSignature != null && !lstImageAndSignature.isEmpty()){
			return lstImageAndSignature;
		}
		else{
			return  null;
		}
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<CandidateBean>  getCandidateSignatureForScheduling() throws Exception{
		List<CandidateBean> lstImageAndSignature;
		try{
				lstImageAndSignature =  getJdbcTemplate().query(
					TemplateDownloadQueries.GET_CANDIDATE_SIGN_FOR_SCHEDULING,
					new Object[] {}, new RowMapper<CandidateBean>(){
						
		             public CandidateBean mapRow(ResultSet rs, int rowNum){   
		            		CandidateBean imageDetailBean = new CandidateBean();
		            		ByteArrayInputStream  byteArrayInputStreamForImage = null;
		            		try {
		            			if(rs.getBytes("OCI_SIGN_IMAGE") != null){
		            				 byteArrayInputStreamForImage = new ByteArrayInputStream(rs.getBytes("OCI_SIGN_IMAGE"), rs.getBytes("OCI_SIGN_IMAGE").length, 1);
				    				 imageDetailBean.setInputStreamForImage((InputStream)byteArrayInputStreamForImage);
			    				}
		            			if (rs.getBytes("OCI_SIGN_IMAGE")!=null){
		            				imageDetailBean.setSignByte(rs.getBytes("OCI_SIGN_IMAGE"));
		            			}
		            			if (rs.getString("OCI_CANDIDATE_IMAGE_PK")!=null && !rs.getString("OCI_CANDIDATE_IMAGE_PK").equals("")){
		            				imageDetailBean.setCandidateImagePK(rs.getString("OCI_CANDIDATE_IMAGE_PK"));
		            			}
		            			if (rs.getString("OUM_USER_ID")!=null && !rs.getString("OUM_USER_ID").equals("")){
		            				imageDetailBean.setUserId(rs.getString("OUM_USER_ID"));
		            			}
							}
		            		catch (SQLException e){
		            			LoggerHome.getLogger(getClass()).fatal(e, e);
							} catch (Throwable e) {
								LoggerHome.getLogger(getClass()).fatal(e, e);
							}
		            		return imageDetailBean;
		                 }   
		            });
		}catch (Exception e) {
			throw new GenericException(e);
		}
		
		
		if(lstImageAndSignature != null && !lstImageAndSignature.isEmpty()){
			return lstImageAndSignature;
		}
		else{
			return  null;
		}
		
	}
	
	
	public List<CandidateDetailsBean> getCandidateDetailsList()throws GenericException{
		//logger.info("Calling getCandidateDetailsList()");
		List<CandidateDetailsBean> candidateDetailsList = new ArrayList<CandidateDetailsBean>();
		try {
			candidateDetailsList = getJdbcTemplate().query(TemplateDownloadQueries.GET_CANDIDATE_DETAILS,new Object[]{}, new BeanPropertyRowMapper(CandidateDetailsBean.class));
		} catch (Exception e) {
			candidateDetailsList = null;
		}
		
		if (candidateDetailsList==null){
			candidateDetailsList = new ArrayList<CandidateDetailsBean>();
		}
		return candidateDetailsList;
	}
	
	public List<CandidateDetailsBean> getCandidateDetailsListWithScheduling()throws GenericException{
		//logger.info("Calling getCandidateDetailsList()");
		List<CandidateDetailsBean> candidateDetailsList = new ArrayList<CandidateDetailsBean>();
		try {
			String query = TemplateDownloadQueries.GET_CANDIDATE_DETAILS_WITH_SCHEDULING;
			candidateDetailsList = getJdbcTemplate().query(query,new BeanPropertyRowMapper(CandidateDetailsBean.class));
		} catch (Exception e) {
			candidateDetailsList = null;
		}
		if (candidateDetailsList==null){
			candidateDetailsList = new ArrayList<CandidateDetailsBean>();
		}
		return candidateDetailsList;
	}

	public Map<String, List<CandidateDocBean>> getCandidateDocumentMap()
			throws Exception {
		
		return (Map<String ,List<CandidateDocBean>>)getJdbcTemplate().query(TemplateDownloadQueries.GET_ALL_CANDIDATE_DOCUMENT, new ResultSetExtractor()
		{
			@Override
			public Map<String ,List<CandidateDocBean>> extractData(ResultSet rs) throws SQLException,
			DataAccessException
			{
				String currentUser="";
				List<CandidateDocBean> lCandidateDocList=null; 
				Map<String ,List<CandidateDocBean>> candidateDocMap = new LinkedHashMap<String ,List<CandidateDocBean>>();
				while(rs.next())
				{
					//System.out.println(rs.getString("OCD_USER_FK")+" "+rs.getString("OCD_FLAG"));
					CandidateDocBean lCandidateDocBean=new CandidateDocBean();
					if(rs.getString("OCD_USER_FK").equalsIgnoreCase(currentUser)){
							lCandidateDocBean.setOcad_document(rs.getBytes("OCD_DOCUMENT"));
							lCandidateDocBean.setOCD_USER_FK(rs.getString("OCD_USER_FK"));
							lCandidateDocBean.setOCD_DOC_FILE_NAME(rs.getString("OCD_DOC_FILE_NAME"));
							lCandidateDocBean.setOcd_created_by(rs.getString("oum_user_id"));
							lCandidateDocBean.setOCD_FLAG(rs.getString("OCD_FLAG"));
							lCandidateDocList.add(lCandidateDocBean);
					}else{
						if(currentUser.equalsIgnoreCase("")){
							currentUser=rs.getString("OCD_USER_FK");
							lCandidateDocList =new ArrayList<CandidateDocBean>();
							lCandidateDocBean.setOcad_document(rs.getBytes("OCD_DOCUMENT"));
							lCandidateDocBean.setOCD_DOC_FILE_NAME(rs.getString("OCD_DOC_FILE_NAME"));
							lCandidateDocBean.setOcd_created_by(rs.getString("oum_user_id"));
							lCandidateDocBean.setOCD_USER_FK(rs.getString("OCD_USER_FK"));
							lCandidateDocBean.setOCD_FLAG(rs.getString("OCD_FLAG"));
							lCandidateDocList.add(lCandidateDocBean);
						}else{
							candidateDocMap.put(currentUser, lCandidateDocList);
							currentUser=rs.getString("OCD_USER_FK");
							lCandidateDocList =new ArrayList<CandidateDocBean>();
							lCandidateDocBean.setOcad_document(rs.getBytes("OCD_DOCUMENT"));
							lCandidateDocBean.setOCD_USER_FK(rs.getString("OCD_USER_FK"));
							lCandidateDocBean.setOcd_created_by(rs.getString("oum_user_id"));
							lCandidateDocBean.setOCD_DOC_FILE_NAME(rs.getString("OCD_DOC_FILE_NAME"));
							lCandidateDocBean.setOCD_FLAG(rs.getString("OCD_FLAG"));
							lCandidateDocList.add(lCandidateDocBean);
							
						}
					}
					if(!rs.next())
					{
						candidateDocMap.put(currentUser, lCandidateDocList);
					}
				}
				return candidateDocMap;
			}
				
		});
	}
	
}
