package com.nseit.generic.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.RoleDao;
import com.nseit.generic.models.MenuBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.RoleBean;
import com.nseit.generic.queries.RoleQueries;
import com.nseit.generic.util.GenericException;

public class RoleDaoImpl extends BaseDAO implements RoleDao {
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}
	
	public List<RoleBean> getRoleSearch(
			RoleBean roleMasterBean,Pagination pagination) throws Exception {
		List<RoleBean> syllabusList = new ArrayList<RoleBean>();
		StringBuilder strBuilder = new StringBuilder();
		try {
			strBuilder.append(RoleQueries.GET_ROLE_SEARCH);
			strBuilder.append(" order by orm_role_desc");
			strBuilder.append(") as aliasTEST WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd()) ;			
			
			syllabusList = getJdbcTemplate().query(
					strBuilder.toString(),
					new Object[] { roleMasterBean.getStatus(),roleMasterBean.getStatus(),roleMasterBean.getRoleType() },
					new BeanPropertyRowMapper(RoleBean.class));
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return syllabusList;
	}
	
	public List<RoleBean> getRole(
			RoleBean roleMasterBean) throws Exception {
		List<RoleBean> syllabusList = new ArrayList<RoleBean>();
		StringBuilder strBuilder = new StringBuilder();
		try {
			strBuilder.append(RoleQueries.GET_ACTIVE_ROLE);
			
			strBuilder.append(" order by orm_role_desc");
			syllabusList = getJdbcTemplate().query(
					strBuilder.toString(),
					new Object[] { roleMasterBean.getRoleType() },
					new BeanPropertyRowMapper(RoleBean.class));
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return syllabusList;
	}

	public int insertRoleDetails(RoleBean roleBean) throws Exception {
		//logger.info("calling insertRoleDetails ");
		int updatecount = 0;
		
		try {
			updatecount = writeJdbcTemplate.update(
					RoleQueries.INSERT_ROLE,
					new Object[] { roleBean.getRoleCode().trim(),
							roleBean.getModuleName().trim(),
							roleBean.getStatus().trim(), roleBean.getUsername().trim(),roleBean.getRoleType()
					});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return updatecount;
	}
	
	public int[] insertAccessControlDetails(final List<String> menuDetails,final String role) throws Exception {
		//logger.info("calling insertAccessControlDetails ");
		int[] updateCounts = null;
		try {
			updateCounts = writeJdbcTemplate.batchUpdate(
					RoleQueries.INSERT_ACCESS_CONTROL,
					new BatchPreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement pStatement,
								int currBatchIndex) throws SQLException {
							pStatement.setInt(1, Integer.parseInt(menuDetails.get(currBatchIndex)));
							pStatement.setInt(2, Integer.parseInt(role));
						}

						@Override
						public int getBatchSize() {
							return menuDetails.size();
						}
					});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return updateCounts;
	}

	public int deleteAccessControl(String role)throws Exception{
		//logger.info("calling deleteAccessControl ");
		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(RoleQueries.UPDATE_ACCESS_CONTROL, new Object[] { Integer.parseInt(role) }, Integer.class);
		} catch (Exception e) {
			count = 0;
			//logger.info(e.getMessage());
		}
		return count;
	}
	
	public RoleBean getRoleEditDetails(RoleBean roleBean)
			throws Exception {
		//logger.info("calling getRoleEditDetails ");
		
		RoleBean ResultRoleBean = new RoleBean();
		try {
			ResultRoleBean = (RoleBean) getJdbcTemplate().queryForObject(
					RoleQueries.GET_ROLE_EDIT_DETAILS,
					new Object[] { Integer.parseInt(roleBean.getModuleId() )},
					new BeanPropertyRowMapper(RoleBean.class));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ResultRoleBean;
	}

	public int isScheduleExistForRole(int moduleId) throws Exception {
		//logger.info("calling isScheduleExistForRole ");
		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(RoleQueries.IS_ACCESS_EXIST_FOR_ROLE, new Object[] { moduleId }, Integer.class);
		} catch (Exception e) {
			count = 0;
			logger.info(e.getMessage());
		}
		return count;
	}
	
	public List<MenuBean> getRoleMenuDetails(String rolePk) throws Exception {
		//logger.info("calling getRoleMenuDetails ");
		List<MenuBean> menuBean = new ArrayList<MenuBean>();
		try {
			menuBean = getJdbcTemplate().query(RoleQueries.GET_ROLE_MENU_DETAILS, new Object[] { Integer.parseInt(rolePk) },new BeanPropertyRowMapper(MenuBean.class));
		} catch (Exception e) {
			
			logger.error(e);
		}
		return menuBean;
	}
	
	public int updateRoleDetails(RoleBean roleBean) throws Exception {
		//logger.info("calling updateRoleDetails ");
		int updatecount = 0;
		
		try {
			updatecount = writeJdbcTemplate.update(RoleQueries.UPDATE_ROLE_DETAILS,
					new Object[] { roleBean.getRoleCode().trim(),
					roleBean.getModuleName().trim(),
					roleBean.getStatus().trim(),roleBean.getRoleType(),Integer.parseInt(roleBean.getModuleId().trim())});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return updatecount;
	}

	public int getRolePK(String moduleCode) throws Exception {
		//logger.info("calling getRolePK ");
		int modulePK = 0;
		try {
			modulePK = getJdbcTemplate().queryForObject(
					RoleQueries.GET_ROLE_PK, new Object[] { moduleCode },Integer.class);
		} catch (Exception e) {
			modulePK = 0;
			System.out.println(e.getMessage());
		}
		return modulePK;
	}

	@Override
	public int getRoleCount(RoleBean roleMasterBean) throws Exception {
		int count = 0;
		try
		{
			StringBuffer strBld = new StringBuffer();
			
			strBld.append(RoleQueries.GET_ROLE_COUNT);
		count = getJdbcTemplate().queryForObject(strBld.toString(), new Object[] {roleMasterBean.getStatus(),roleMasterBean.getStatus(),roleMasterBean.getRoleType()},Integer.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
	
}