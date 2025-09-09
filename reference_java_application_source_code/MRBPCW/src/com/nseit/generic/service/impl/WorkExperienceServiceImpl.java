package com.nseit.generic.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.dao.CandidatePersonalDetailsDAO;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.dao.WorkExperienceDao;
import com.nseit.generic.models.Users;
import com.nseit.generic.models.WorkExperienceBean;
import com.nseit.generic.models.WorkExperienceDetailsBean;
import com.nseit.generic.service.WorkExperienceService;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.LoggerHome;

public class WorkExperienceServiceImpl implements WorkExperienceService {
	private WorkExperienceDao workExperienceDao;
	private CandidatePersonalDetailsDAO candidatePersonalDetailsDAO;
	private CommonDao commonDao;

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	private static final long serialVersionUID = -7883478322664991439L;
	private Logger logger = LoggerHome.getLogger(getClass());

	private WorkExperienceBean workExperienceBean = new WorkExperienceBean();

	public void setCandidatePersonalDetailsDAO(CandidatePersonalDetailsDAO candidatePersonalDetailsDAO) {
		this.candidatePersonalDetailsDAO = candidatePersonalDetailsDAO;
	}

	public void setWorkExperienceDao(WorkExperienceDao workExperienceDao) {
		this.workExperienceDao = workExperienceDao;
	}

	@Override
	public List<WorkExperienceDetailsBean> getWorkExperienceDetailsForCandidate(Long usrFk) throws Exception {
		return workExperienceDao.getWorkExperienceDetailsForCandidate(usrFk);
	}

	@Override
	  public WorkExperienceBean getWorkExpAddDetails(Users users) throws Exception {
	  	return workExperienceDao.getWorkExpAddDetails(users);
	  }
	
	@Override
	public String saveWorkExperienceDetails(List<WorkExperienceDetailsBean> workExperienceDetailsList, Users loggedInUser, WorkExperienceBean workExperienceBean) throws Exception {
		try {
			int count = workExperienceDao.isUserExistInWorkExperiance(loggedInUser);
			int addDetailsCount = workExperienceDao.isUserExistInWorkExpAddDetails(loggedInUser);

			if (count > 0) {
				workExperienceDao.deleteWorkExpForCandidate(loggedInUser);
				workExperienceDao.insertWorkExperienceDetails(workExperienceDetailsList, loggedInUser, workExperienceBean);
			} else {
				workExperienceDao.insertWorkExperienceDetails(workExperienceDetailsList, loggedInUser, workExperienceBean);
			}
			if (addDetailsCount > 0) {
				workExperienceDao.deleteWorkExpAddDetails(loggedInUser);
				workExperienceDao.insertWorkExpAddDetails(loggedInUser, workExperienceBean);
			} else {
				workExperienceDao.insertWorkExpAddDetails(loggedInUser, workExperienceBean);
			}
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return GenericConstants.SAVE_SUCCESS;
	}

	@Override
	public int isUserExistInWorkExperiance(Users loggedInUser) throws Exception {
		return workExperienceDao.isUserExistInWorkExperiance(loggedInUser);
	}

	@Override
	public String getPoliceStationList(WorkExperienceBean workExperienceBean) throws Exception {
		Map<Integer, String> PoliceStationListMap = new LinkedHashMap<Integer, String>();
		String PresentPostingUnit = null;
		String PresentPostingUnit1 = "";

		PresentPostingUnit = workExperienceBean.getPresentPosting();

		if (PresentPostingUnit != null && !PresentPostingUnit.equals("") && !PresentPostingUnit.equals("null")) {

			PoliceStationListMap = ConfigurationConstants.getInstance().getPoliceStationMap1(Integer.parseInt(PresentPostingUnit));
		}

		if (PoliceStationListMap != null) {
			for (Map.Entry<Integer, String> entry : PoliceStationListMap.entrySet()) {
				if (entry != null) {
					if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null && !entry.getKey().equals("")) {
						PresentPostingUnit1 = PresentPostingUnit1 + (entry.getValue() + "#" + entry.getKey()) + ",";
					}
				}
			}
		}

		if (PresentPostingUnit1 != null && !PresentPostingUnit1.equals("") && PresentPostingUnit1.endsWith(",")) {
			PresentPostingUnit1 = PresentPostingUnit1.substring(0, PresentPostingUnit1.length() - 1);
		}

		return PresentPostingUnit1;

	}

	@Override
	public String getDistrict(WorkExperienceBean workExperienceBean) throws Exception {
		Map<Integer, String> unitListMap = new LinkedHashMap<Integer, String>();
		String PresentPostingUnit = null;
		String PresentPostingUnit1 = "";
		
		PresentPostingUnit = workExperienceBean.getPresentPosting();
		if (PresentPostingUnit != null && !PresentPostingUnit.equals("") && !PresentPostingUnit.equals("null")) {

			unitListMap = ConfigurationConstants.getInstance().getDistrictMap1(PresentPostingUnit);
		}

		if (unitListMap != null) {
			for (Map.Entry<Integer, String> entry : unitListMap.entrySet()) {
				if (entry != null) {
					if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null && !entry.getKey().equals("")) {
						PresentPostingUnit1 = PresentPostingUnit1 + (entry.getValue().replace(',', '@') + "#" + entry.getKey()) + ",";
					}
				}
			}
		}

		if (PresentPostingUnit1 != null && !PresentPostingUnit1.equals("") && PresentPostingUnit1.endsWith(",")) {
			PresentPostingUnit1 = PresentPostingUnit1.substring(0, PresentPostingUnit1.length() - 1);
		}
		return PresentPostingUnit1;
	}

	@Override
	public String getUnit(HttpServletRequest request, WorkExperienceBean workExperienceBean) throws Exception {

		Map<String, String> unitListMap = new LinkedHashMap<String, String>();

		String presentRank = null;
		String PresentPostingUnit = "";
		Map<String, String> treeMap = null;

		String remove = request.getParameter("remove");

		presentRank = workExperienceBean.getPresentRank();

		if (presentRank != null && !presentRank.equals("") && !presentRank.equals("null")) {

			unitListMap = ConfigurationConstants.getInstance().getSubUnitMap1(presentRank.trim());
			if (remove != null || !(remove == "")) {
				unitListMap.remove("YOUTH BRIGADE");
			}
			treeMap = new TreeMap<String, String>(unitListMap);
		}

		if (unitListMap != null) {
			for (Map.Entry<String, String> entry : unitListMap.entrySet()) {
				if (entry != null) {
					if (entry.getValue() != null && !entry.getValue().equals("") && entry.getKey() != null && !entry.getKey().equals("")) {
						PresentPostingUnit = PresentPostingUnit + (entry.getValue() + "#" + entry.getKey()) + ",";
					}
				}
			}
		}

		if (PresentPostingUnit != null && !PresentPostingUnit.equals("") && PresentPostingUnit.endsWith(",")) {
			PresentPostingUnit = PresentPostingUnit.substring(0, PresentPostingUnit.length() - 1);
		}
		return PresentPostingUnit;
	}

	@Override
	public List<WorkExperienceDetailsBean> getAddEduDetails(Users loggedInUser) throws Exception {
		return workExperienceDao.getAddEduDetails(loggedInUser);
	}

	@Override
	public WorkExperienceDetailsBean getMphilData(Users loggedInUser) throws Exception {
		return workExperienceDao.getMphilData(loggedInUser);
	}

	@Override
	public String getPostAppliedFor(Long usrId) throws Exception {
		return workExperienceDao.getPostAppliedFor(usrId);
	}
	
	@Override
	public WorkExperienceDetailsBean getHighestEduData(Users loggedInUser) throws Exception {
		return workExperienceDao.getHighestEduData(loggedInUser);
	}
}
