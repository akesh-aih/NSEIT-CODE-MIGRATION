package com.nseit.generic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.ModuleDao;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.ModuleBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.TestGroup;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.ModuleQueries;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;

public class ModuleDaoImpl extends BaseDAO implements ModuleDao {
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	public List<CommonBean> getSyllabus() throws Exception {
		List<CommonBean> syllabusList = new ArrayList<CommonBean>();
		try {

			syllabusList = getJdbcTemplate().query(ModuleQueries.GET_SYLLABUS,
					new Object[] {}, new RowMapper<CommonBean>() {
						public CommonBean mapRow(ResultSet rs, int rowNum) {
							CommonBean syllabus = new CommonBean();
							try {
								syllabus
										.setLabelValue(rs.getString("SYLLABUS"));

							} catch (SQLException e) {
								LoggerHome.getLogger(getClass()).fatal(e);
							}
							return syllabus;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return syllabusList;
	}

	

	public ModuleBean getModuleEditDetails(ModuleBean moduleBean)
			throws Exception {
		System.out.println("calling getSectionDetails ");
		int updatecount = 0;
		ModuleBean ResultModuleBean = new ModuleBean();
		try {
			ResultModuleBean = (ModuleBean) getJdbcTemplate().queryForObject(
					ModuleQueries.GET_MODULE_EDIT_DETAILS,
					new Object[] { moduleBean.getModuleName() },
					new BeanPropertyRowMapper(ModuleBean.class));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ResultModuleBean;
	}

	public int deleteSectionDetails(ModuleBean moduleBean) throws Exception {
		System.out.println("calling deleteSectionDetails ");
		int updatecount = 0;
		try {
			updatecount = writeJdbcTemplate.update(
					ModuleQueries.DELETE_SECTION_DETAILS,
					new Object[] { moduleBean.getOEM_EXEMPTION_MASTER_PK()

					});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return updatecount;
	}

	public int isScheduleExistForModule(int moduleId) throws Exception {
		//logger.info("calling isScheduleExistForModule ");
		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(ModuleQueries.IS_SCHEDULE_EXIST_FOR_MODULE, new Object[] { moduleId }, Integer.class);
		} catch (Exception e) {
			count = 0;
			logger.info(e.getMessage());
		}
		return count;
	}
	
	

	public int getModulePK(String moduleName) throws Exception {
		System.out.println("calling getModulePK ");
		int modulePK = 0;
		try {
			modulePK = getJdbcTemplate().queryForObject(
					ModuleQueries.GET_MODULE_PK, new Object[] { moduleName }, Integer.class);
		} catch (Exception e) {
			modulePK = 0;
			System.out.println(e.getMessage());
		}
		return modulePK;
	}

	
	public int deleteSections(ModuleBean moduleBean) throws Exception {
		//logger.info("calling deleteSections ");
		int updatecount = 0;
		try {
			updatecount = writeJdbcTemplate.update(
					ModuleQueries.DELETE_SECTION_WITH_MODULE_ID,
					new Object[] { moduleBean.getModuleId()

					});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return updatecount;
	}

	@Override
	public List<ModuleBean> getSearchModuleDetails(ModuleBean moduleBean, Pagination pagination)
			throws Exception {
		List<ModuleBean> searchModuleList = new ArrayList<ModuleBean>();
		try
		{
			 StringBuffer strBld = new StringBuffer();
			String status = moduleBean.getStatus();
			
			strBld.append(ModuleQueries.GET_MODULE_SEARCH);
			 strBld.append(") as aliasTEST WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd()) ;
			 
			searchModuleList = getJdbcTemplate().query(strBld.toString(),
					new Object[] {moduleBean.getStatus(),moduleBean.getStatus()}, new RowMapper<ModuleBean>() {
						public ModuleBean mapRow(ResultSet rs, int rowNum) {
							ModuleBean module = new ModuleBean();
							try {
								module.setOTM_TEST_PK(rs.getString("OTM_TEST_PK"));
								module.setOTM_TEST_NAME(rs.getString("OTM_TEST_NAME"));
								module.setOTM_DESCRIPTION(rs.getString("OTM_DESCRIPTION"));
								module.setOTM_STATUS(rs.getString("OTM_STATUS"));
								module.setOTM_FROM_DATE(rs.getString("OTM_FROM_DATE"));
								module.setOTM_TO_DATE(rs.getString("OTM_TO_DATE"));
								module.setOTM_PAYMENT_START_DATE(rs.getString("OTM_PAYMENT_START_DATE"));
								module.setOTM_PAYMENT_END_DATE(rs.getString("OTM_PAYMENT_END_DATE"));
								module.setDateOfNotification(rs.getString("dateOfNotification"));
								module.setTestGroup(rs.getLong("testGroup"));
								module.setAdvertisementnumber(rs.getString("advertisementnumber"));
							} catch (SQLException e) {
								LoggerHome.getLogger(getClass()).fatal(e);
							}
							return module;
						}
					});
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return searchModuleList;
	}

	@Override
	public List<ModuleBean> getCategoryDetails() throws Exception {
		List<ModuleBean> searchCategoryList = new ArrayList<ModuleBean>();
		try
		{
			searchCategoryList = getJdbcTemplate().query(ModuleQueries.GET_CATEGORY_SEARCH,
					new Object[] {}, new RowMapper<ModuleBean>() {
						public ModuleBean mapRow(ResultSet rs, int rowNum) {
							ModuleBean module = new ModuleBean();
							try {
								module.setOCTM_CATEGORY_PK(rs.getString("OCTM_CATEGORY_PK"));
								module.setOCTM_CATEGORY_CODE(rs.getString("OCTM_CATEGORY_CODE"));
							} catch (SQLException e) {
								LoggerHome.getLogger(getClass()).fatal(e);
							}
							return module;
						}
					});
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return searchCategoryList;
	}

	@Override
	public int insertModuleDetails(ModuleBean moduleBean,
			List<ModuleBean> moduleCategoryList) throws Exception {
		System.out.println("calling insertModuleDetails ");
		int updatecount = 0;
		int moduleCount = 0;
		//String examCodeValue =null;
		/*if(!"-1".equals(moduleBean.getExamCodeValue())){
			examCodeValue = moduleBean.getExamCodeValue();
		}*/
		try {
			/*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String fdate = moduleBean.getFromDate().trim();
			Date fromDate = format.parse(fdate);
			
			String tdate = moduleBean.getToDate().trim();
			Date toDate = format.parse(tdate);*/
			
			updatecount = writeJdbcTemplate.update(
					ModuleQueries.INSERT_MODULE,
					new Object[] { moduleBean.getModuleName().trim(),
							moduleBean.getmoduleDescription().trim(),
							moduleBean.getStatus().trim(), 
							moduleBean.getFromDate().trim(),
							moduleBean.getToDate().trim(),
							moduleBean.getUsername(),
							moduleBean.getPaymentStartDate(),
							moduleBean.getPaymentEndDate(),
							moduleBean.getDateOfNotification(),
							moduleBean.getTestGroup(),
							moduleBean.getAdvertisementnumber()

					});
			
			if(updatecount > 0)
			{
				int testPk = getJdbcTemplate().queryForObject(ModuleQueries.GET_MODULE_PK, new Object[] { moduleBean.getModuleName() }, Integer.class);
				for (ModuleBean moduleBean2 : moduleCategoryList) {
				
				int categoryFk = getJdbcTemplate().queryForObject(ModuleQueries.GET_CATEGORY_PK, new Object[] { moduleBean2.getOCTM_CATEGORY_CODE() }, Integer.class);
				moduleCount = writeJdbcTemplate.update(
							ModuleQueries.INSERT_MODULE_FEES,
							new Object[] { testPk,
									categoryFk,
									Float.parseFloat(moduleBean2.getFees()),
									"A",
									moduleBean2.getFeesInWords(),
									moduleBean.getUsername()
							});
					
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return updatecount;
	}

	@Override
	public int updateModuleDetails(ModuleBean moduleBean,
			List<ModuleBean> moduleCategoryList) throws Exception {
		//logger.info("calling updateModuleDetails ");
		int updatecount = 0;
		int updateFees = 0;
		
		try {
			updatecount = writeJdbcTemplate.update(ModuleQueries.UPDATE_MODULE_DETAILS,
					new Object[] { moduleBean.getModuleName().trim(),
							moduleBean.getmoduleDescription().trim(),
							moduleBean.getStatus().trim(),
							moduleBean.getFromDate(),
							moduleBean.getToDate(),
							moduleBean.getUsername(),
							moduleBean.getPaymentStartDate(),
							moduleBean.getPaymentEndDate(),
							moduleBean.getAdvertisementnumber(),
							Integer.parseInt(moduleBean.getModuleId().trim())
							});
			
			for (ModuleBean moduleBean2 : moduleCategoryList) {
			int categoryFk = getJdbcTemplate().queryForObject(ModuleQueries.GET_CATEGORY_PK, new Object[] { moduleBean2.getOCTM_CATEGORY_CODE() }, Integer.class);			
			updateFees = writeJdbcTemplate.update(ModuleQueries.UPDATE_FEES_DETAILS,
					new Object[] { Float.parseFloat(moduleBean2.getFees()),
					moduleBean2.getFeesInWords(),
					moduleBean.getUsername(),
					Integer.parseInt(moduleBean.getModuleId().trim()),
					categoryFk
					});
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return updatecount;
	}

	@Override
	public ModuleBean getModuleFeesEdit(ModuleBean moduleBean,
			List<ModuleBean> categoryList) throws Exception {
		int updatecount = 0;
		ModuleBean ResultModuleBean = new ModuleBean();
		try {
			
			for (ModuleBean moduleBean2 : categoryList) {
			int categoryFk = getJdbcTemplate().queryForObject(ModuleQueries.GET_CATEGORY_PK, new Object[] { moduleBean2.getOCTM_CATEGORY_CODE() }, Integer.class);
			ResultModuleBean = (ModuleBean) getJdbcTemplate().queryForObject(
					ModuleQueries.GET_MODULE_FEES_DETAILS,
					new Object[] { Integer.parseInt(moduleBean.getModuleId()),
					categoryFk		
					},
					new BeanPropertyRowMapper(ModuleBean.class));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ResultModuleBean;
	}

	@Override
	public List<ModuleBean> getModuleFee(ModuleBean moduleBean,
			List<ModuleBean> categoryList) throws Exception {
		List<ModuleBean> moduleFees = new ArrayList<ModuleBean>();
		try
		{
			/*for (ModuleBean moduleBean2 : categoryList) {
			int categoryFk = getJdbcTemplate().queryForObject(ModuleQueries.GET_CATEGORY_PK, new Object[] { moduleBean2.getOCTM_CATEGORY_CODE() }, Integer.class);*/
			moduleFees = getJdbcTemplate().query(ModuleQueries.GET_MODULE_FEES_DETAILS,
					new Object[] {Integer.parseInt(moduleBean.getModuleId())
					}, new RowMapper<ModuleBean>() {
						public ModuleBean mapRow(ResultSet rs, int rowNum) {
							ModuleBean module = new ModuleBean();
							try {
								module.setFees(rs.getString("OFM_FEES"));
								module.setFeesInWords(rs.getString("OFM_FEES_IN_WORDS"));
								module.setOCTM_CATEGORY_CODE(rs.getString("OCTM_CATEGORY_CODE"));
								module.setOCTM_CATEGORY_PK(rs.getString("OCTM_CATEGORY_PK"));
							} catch (SQLException e) {
								LoggerHome.getLogger(getClass()).fatal(e);
							}
							return module;
						}
					});
//			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return moduleFees;
	}

	@Override
	public int getModuleCount(ModuleBean moduleBean) throws Exception {
		int count = 0;
		try
		{
		count = getJdbcTemplate().queryForObject(ModuleQueries.GET_MODULE_COUNT, new Object[] {moduleBean.getStatus(),moduleBean.getStatus()}, Integer.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


	@Override
	public int getModulePKCount(String moduleName) throws Exception {
		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(
					ModuleQueries.GET_MODULE_PK_COUNT, new Object[] { moduleName }, Integer.class);
		} catch (Exception e) {
			count = 0;
			System.out.println(e.getMessage());
		}
		return count;
	}



	
	@Override
	public Map<Integer, String> getUpdatedModules() throws Exception {
		Map<Integer, String> disciplineMap = new LinkedHashMap<Integer, String>();
		try{
			disciplineMap = (Map<Integer, String>) getJdbcTemplate().query(ModuleQueries.GET_DISCIPLINE_MAP, new ResultSetExtractor(){
			
			
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException{
				Map<Integer, String> disciplineMap = new LinkedHashMap<Integer, String>();
				
				while(rs.next()){
					disciplineMap.put(rs.getInt("OTM_TEST_PK"), rs.getString("OTM_TEST_NAME"));
				}
				return disciplineMap;
			}
		});
		}
		catch (Exception e) {
			throw new GenericException(e);
		}
		
		return disciplineMap;
	}



	@Override
	public List<TestGroup> getTestGroupList() throws Exception {
		List<TestGroup> testGroupList = new ArrayList<TestGroup>();
		try {

			testGroupList = getJdbcTemplate().query(ModuleQueries.GET_TESTGROUP,
					new Object[] {}, new RowMapper<TestGroup>() {
						public TestGroup mapRow(ResultSet rs, int rowNum) {
							TestGroup testGroup = new TestGroup();
							try {
								testGroup.setOtg_test_pk(rs.getLong("otg_test_pk"));
								testGroup.setOtg_test_name(rs.getString("otg_test_name"));
								testGroup.setOtm_description(rs.getString("otm_description"));

							} catch (SQLException e) {
								LoggerHome.getLogger(getClass()).fatal(e);
							}
							return testGroup;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return testGroupList;
		
	}
	

		
	

	
	
}