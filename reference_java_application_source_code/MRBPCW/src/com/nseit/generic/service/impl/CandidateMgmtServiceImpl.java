package com.nseit.generic.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;

import com.nseit.generic.dao.CandidateMgmtDAO;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.dao.ReportDao;
import com.nseit.generic.models.CandidateApproveRejectBean;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.EnrollmentBean;
import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentBean;
import com.nseit.generic.models.UserDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.service.CandidateMgmtService;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.GenericServiceException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.otbs.model.BatchDetailsBean;

@SuppressWarnings("unused")

public class CandidateMgmtServiceImpl implements CandidateMgmtService {

	private CandidateMgmtDAO candidateMgmtDAO;
	private CommonDao commonDao;
	private ReportDao reportDao;
	private Logger logger = LoggerHome.getLogger(getClass());

	public void setCandidateMgmtDAO(CandidateMgmtDAO candidateMgmtDAO) {
		this.candidateMgmtDAO = candidateMgmtDAO;
	}

	public void setCommonDao(CommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public void setReportDao(ReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public List<CandidateMgmtBean> getCandidateHallTicket(CandidateMgmtBean candidateMgmtBean, String stage, Integer attempt) throws Exception {

		List<CandidateMgmtBean> hallTicketDetails = null;
		try {
			hallTicketDetails = candidateMgmtDAO.getCandidateHallTicket(candidateMgmtBean, stage, attempt);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return hallTicketDetails;
	}

	public List<CandidateMgmtBean> getCandidateCandidateDetailsById(CandidateMgmtBean candidateMgmtBean) throws Exception {

		List<CandidateMgmtBean> candidateDetailsById = null;
		try {
			candidateDetailsById = candidateMgmtDAO.getCandidateDetailsByIdForAdmin(candidateMgmtBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return candidateDetailsById;
	}

	public Map<Integer, String> getTestCenterMasterDetails() throws Exception {

		Map<Integer, String> testCenterMasterDetails = null;
		try {
			testCenterMasterDetails = candidateMgmtDAO.getTestCenterMasterDetails();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return testCenterMasterDetails;
	}

	public List<String> getTestDates() throws Exception {

		List<String> testDates = null;
		try {
			testDates = candidateMgmtDAO.getTestDates();
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return testDates;
	}

	public List<String> getTestSlots(CandidateMgmtBean candidateMgmtBean) throws Exception {

		List<String> testSlots = null;
		try {
			testSlots = candidateMgmtDAO.getTestSlots(candidateMgmtBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return testSlots;
	}

	public List<CandidateMgmtBean> getCandidateDetailsForRegistration(CandidateMgmtBean candidateMgmtBean) throws Exception {

		List<CandidateMgmtBean> candidateDetailsForRegistration = null;
		try {
			candidateDetailsForRegistration = candidateMgmtDAO.getCandidateDetailsForRegistration(candidateMgmtBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return candidateDetailsForRegistration;
	}

	private List<EnrollmentBean> allocateSeatsPreferenceBasedPriorityWise(Map<Integer, Integer> testCenterWiseSeatsMap, List<EnrollmentBean> candidateDetails) throws Exception {
		List<EnrollmentBean> seatsAllocatedCandidates = new ArrayList<EnrollmentBean>();
		EnrollmentBean enrollmentBean = null;
		int totalRemainingSeats = 0;
		int testCentreId = 0;
		List<String> testDateList = null;
		List<String> testSlotList = null;
		for (int currCandidateIndex = 0, currTotalIndex = 0; currTotalIndex < candidateDetails.size() * 3; currCandidateIndex++, currTotalIndex++) {
			enrollmentBean = candidateDetails.get(currCandidateIndex);

			if (enrollmentBean.getAssignedTestCenter() == 0) {
				if (currTotalIndex >= 0 && currTotalIndex < candidateDetails.size()) {
					testCentreId = Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK());
				} else if (currTotalIndex >= candidateDetails.size() && currTotalIndex < candidateDetails.size() * 2) {
					testCentreId = Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK());
				} else if (currTotalIndex >= candidateDetails.size() * 2 && currTotalIndex < candidateDetails.size() * 3) {
					testCentreId = Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK());
				}

				if (testCenterWiseSeatsMap.get(testCentreId) != null && testCenterWiseSeatsMap.get(testCentreId) > 0) {
					enrollmentBean.setAssignedTestCenter(testCentreId);
					totalRemainingSeats = testCenterWiseSeatsMap.get(testCentreId) - 1;
					testCenterWiseSeatsMap.put(testCentreId, totalRemainingSeats);

					testDateList = reportDao.getTestDates(String.valueOf(testCentreId));
					// if (flag){
					if (testDateList != null && !testDateList.isEmpty()) {
						// if(flag){
						inner1: for (String testDate : testDateList) {
							if (testDate != null) {
								// logger.info(testDate);
								testSlotList = reportDao.getTestsSlots(testDate);
								inner2: for (String testSlot : testSlotList) {
									BatchDetailsBean batchDetailsBean = null;
									batchDetailsBean = candidateMgmtDAO.getbatchDetailsForSlot(String.valueOf(testCentreId), testDate, testSlot);
									if (batchDetailsBean != null) {
										if (Integer.parseInt(batchDetailsBean.getOBD_AVAILABLE_CAPACITY()) > 0) {
											enrollmentBean.setBatchPk(batchDetailsBean.getOBD_BATCH_PK());
											// logger.info("batch pk
											// "+batchDetailsBean.getOBD_BATCH_PK());
											break inner1;
										}
									}

								}
							}
						}
						// }

					}
					seatsAllocatedCandidates.add(enrollmentBean);
				}
			}

			if (currCandidateIndex == candidateDetails.size() - 1) {
				currCandidateIndex = -1;
			}
		}
		return seatsAllocatedCandidates;
	}

	// to do auto
	private List<EnrollmentBean> allocateSeatsTimeBasedPriorityWiseBatchWise(Map<Integer, Integer> testCenterWiseSeatsMap,
			Map<Integer, Map<Integer, Integer>> testCenterWiseBatchDetailsMapList, List<EnrollmentBean> candidateDetails) {
		List<EnrollmentBean> seatsAllocatedCandidates = new ArrayList<EnrollmentBean>();
		EnrollmentBean enrollmentBean = null;
		int totalRemainingSeats = 0;
		int testCentreId;
		List<String> testDateList = null;
		List<String> testSlotList = null;
		Map<Integer, Integer> batchDetailsMap = null;
		boolean flag = true;
		try {
			for (int currCandidateIndex = 0; currCandidateIndex < candidateDetails.size(); currCandidateIndex++) {
				enrollmentBean = candidateDetails.get(currCandidateIndex);
				if (enrollmentBean != null) {
					if (enrollmentBean.getPreferredTestCentre1FK() != null && !enrollmentBean.getPreferredTestCentre1FK().equals("")
							&& testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK())) != null
							&& testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK())) > 0) {
						batchDetailsMap = testCenterWiseBatchDetailsMapList.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()));

						if (batchDetailsMap != null && !batchDetailsMap.isEmpty()) {
							/*
							 * logger.info("Values  "+batchDetailsMap.values());
							 * logger.info("Keys  "+batchDetailsMap.keySet());
							 */

							mapLoop: for (Map.Entry<Integer, Integer> entry : batchDetailsMap.entrySet()) {

								if (entry.getValue() != null && entry.getValue() > 0) {
									enrollmentBean.setBatchPk(String.valueOf(entry.getKey()));
									enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()));
									batchDetailsMap.put(entry.getKey(), entry.getValue() - 1);
									testCenterWiseBatchDetailsMapList.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()), batchDetailsMap);
									seatsAllocatedCandidates.add(enrollmentBean);
									totalRemainingSeats = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK())) - 1;
									testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()), totalRemainingSeats);
									break mapLoop;
								}
							}
						}
					}

					else if (enrollmentBean.getPreferredTestCentre2FK() != null && !enrollmentBean.getPreferredTestCentre2FK().equals("")
							&& testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK())) != null
							&& testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK())) > 0) {
						batchDetailsMap = testCenterWiseBatchDetailsMapList.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()));

						if (batchDetailsMap != null && !batchDetailsMap.isEmpty()) {
							/*
							 * logger.info("Values  "+batchDetailsMap.values());
							 * logger.info("Keys  "+batchDetailsMap.keySet());
							 */

							mapLoop2: for (Map.Entry<Integer, Integer> entry : batchDetailsMap.entrySet()) {

								if (entry.getValue() != null && entry.getValue() > 0) {
									enrollmentBean.setBatchPk(String.valueOf(entry.getKey()));
									batchDetailsMap.put(entry.getKey(), entry.getValue() - 1);
									testCenterWiseBatchDetailsMapList.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()), batchDetailsMap);
									enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()));
									seatsAllocatedCandidates.add(enrollmentBean);
									totalRemainingSeats = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK())) - 1;
									testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()), totalRemainingSeats);
									break mapLoop2;
								}
							}
						}
					}

					else if (enrollmentBean.getPreferredTestCentre3FK() != null && !enrollmentBean.getPreferredTestCentre3FK().equals("")
							&& testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK())) != null
							&& testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK())) > 0) {
						batchDetailsMap = testCenterWiseBatchDetailsMapList.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()));

						if (batchDetailsMap != null && !batchDetailsMap.isEmpty()) {
							/*
							 * logger.info("Values  "+batchDetailsMap.values());
							 * logger.info("Keys  "+batchDetailsMap.keySet());
							 */

							mapLoop3: for (Map.Entry<Integer, Integer> entry : batchDetailsMap.entrySet()) {

								if (entry.getValue() != null && entry.getValue() > 0) {
									enrollmentBean.setBatchPk(String.valueOf(entry.getKey()));
									batchDetailsMap.put(entry.getKey(), entry.getValue() - 1);
									testCenterWiseBatchDetailsMapList.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()), batchDetailsMap);
									enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()));
									totalRemainingSeats = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK())) - 1;
									testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()), totalRemainingSeats);
									seatsAllocatedCandidates.add(enrollmentBean);
									break mapLoop3;
								}
							}
						}
					}

				} // bean is not null
			} // for loop for candidate

		} catch (Exception ex) {
			ex.printStackTrace();
			LoggerHome.getLogger(getClass()).fatal(ex);
			seatsAllocatedCandidates = new ArrayList<EnrollmentBean>();
		}

		return seatsAllocatedCandidates;
	}

	public UserDetailsBean getCandidateDisciplineForPayment(Users users) throws GenericException {
		UserDetailsBean userDetailsBean = null;
		try {
			userDetailsBean = candidateMgmtDAO.getCandidateDisciplineForPayment(users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return userDetailsBean;
	}

	public List<HallTicketBean> getCandidateDataForRollNumberGeneration() throws Exception {
		List<HallTicketBean> candidateDataForRollNumber = null;
		try {
			candidateDataForRollNumber = candidateMgmtDAO.getCandidateDataForRollNumberGeneration();
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return candidateDataForRollNumber;
	}

	public String getRollNumberSeq(Integer maxDigitsForRollNumber) throws Exception {
		String seqVal = null;
		try {
			seqVal = candidateMgmtDAO.getRollNumberSeq(maxDigitsForRollNumber);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return seqVal;
	}

	public int updateCandidateRollNumbers(final Users users, final List<HallTicketBean> candidateDataForRollNumber) throws GenericException {
		int update = 0;
		int updateCount[] = null;
		try {
			updateCount = candidateMgmtDAO.updateCandidateRollNumbers(users, candidateDataForRollNumber);

			if (updateCount != null && updateCount.length > 0) {
				for (int i : updateCount) {
					update = update + i;
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return update;
	}

	public List<HallTicketBean> getCandidateDataForAdmitCard(CandidateMgmtBean candidateMgmtBean) throws Exception {
		List<HallTicketBean> candidateDataForAdmitCardList = null;
		try {
			candidateDataForAdmitCardList = candidateMgmtDAO.getCandidateDataForAdmitCard(candidateMgmtBean);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return candidateDataForAdmitCardList;
	}

	public int getCandidateDataForAdmitCardForSearchCount(CandidateMgmtBean candidateMgmtBean) throws Exception {
		return candidateMgmtDAO.getCandidateDataForAdmitCardForSearchCount(candidateMgmtBean);
	}

	public List<HallTicketBean> getCandidateDataForAdmitCardForSearch(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<HallTicketBean> candidateDataForAdmitCardList = null;
		try {
			candidateDataForAdmitCardList = candidateMgmtDAO.getCandidateDataForAdmitCardForSearch(candidateMgmtBean, pagination);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return candidateDataForAdmitCardList;
	}

	public int[] updateGenerateHallticket(final List<String> enrollmentList, final Users users) throws GenericException {
		int[] updateGenerateHallticket = null;
		try {
			updateGenerateHallticket = candidateMgmtDAO.updateGenerateHallticket(enrollmentList, users);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateGenerateHallticket;
	}

	public String generateRollNumberForCandidates() throws Exception {
		String output = null;
		try {
			output = candidateMgmtDAO.generateRollNumberForCandidates();
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return output;
	}

	public UserDetailsBean getUserDetailsForEnrollmentId(String enrollmentPk) throws Exception {
		UserDetailsBean userDetailsBean = null;
		try {
			userDetailsBean = candidateMgmtDAO.getUserDetailsForEnrollmentId(enrollmentPk);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return userDetailsBean;
	}

	public int getRegisteredCandidateDetails(String userId, String fromDate, String toDate, Integer disciplineId, Integer candidateStatusId, int formSubmitStatus) {
		return candidateMgmtDAO.getRegisteredCandidateDetails(userId, fromDate, toDate, disciplineId, candidateStatusId, formSubmitStatus);
	}

	public List<CandidateApproveRejectBean> getRegisteredCandidateDetails(String userId, String fromDate, String toDate, Integer disciplineId, Integer candidateStatusId,
			int formStatusId, Pagination pagination) {
		return candidateMgmtDAO.getRegisteredCandidateDetails(userId, fromDate, toDate, disciplineId, candidateStatusId, formStatusId, pagination);
	}

	public int updateOesPaymentDetails(String challanNumber, Integer paymentModeId) throws Exception {
		return candidateMgmtDAO.updateOesPaymentDetails(challanNumber, paymentModeId);
	}

	public int updateOesPaymentDetailsWithPaymentReject(String challanNumber, Integer paymentModeId) throws Exception {
		return candidateMgmtDAO.updateOesPaymentDetailsWithPaymentReject(challanNumber, paymentModeId);
	}

	public int updateCandidateStatus(int candidateStatus, Integer userId) throws Exception {
		return candidateMgmtDAO.updateCandidateStatus(candidateStatus, userId);
	}

	private List<EnrollmentBean> allocateSeatsTimeBasedPriorityWise(Map<Integer, Integer> testCenterWiseSeatsMap, List<EnrollmentBean> candidateDetails) {
		List<EnrollmentBean> seatsAllocatedCandidates = new ArrayList<EnrollmentBean>();
		EnrollmentBean enrollmentBean = null;
		int totalRemainingSeats = 0;
		int testCentreId;
		List<String> testDateList = null;
		List<String> testSlotList = null;
		boolean flag = true;
		try {
			outer: for (int currCandidateIndex = 0; currCandidateIndex < candidateDetails.size(); currCandidateIndex++) {
				enrollmentBean = candidateDetails.get(currCandidateIndex);
				if (testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK())) != null
						&& testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK())) > 0) {
					testCentreId = Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK());
					enrollmentBean.setAssignedTestCenter(testCentreId);
					totalRemainingSeats = testCenterWiseSeatsMap.get(testCentreId) - 1;
					testCenterWiseSeatsMap.put(testCentreId, totalRemainingSeats);

					testDateList = reportDao.getTestDates(String.valueOf(testCentreId));
					// if (flag){
					if (testDateList != null && !testDateList.isEmpty()) {
						// if(flag){
						inner1: for (String testDate : testDateList) {
							if (testDate != null) {
								// logger.info(testDate);
								testSlotList = reportDao.getTestsSlots(testDate);
								inner2: for (String testSlot : testSlotList) {
									BatchDetailsBean batchDetailsBean = null;
									batchDetailsBean = candidateMgmtDAO.getbatchDetailsForSlot(String.valueOf(testCentreId), testDate, testSlot);
									if (batchDetailsBean != null) {
										if (Integer.parseInt(batchDetailsBean.getOBD_AVAILABLE_CAPACITY()) > 0) {
											enrollmentBean.setBatchPk(batchDetailsBean.getOBD_BATCH_PK());
											// logger.info("batch pk
											// "+batchDetailsBean.getOBD_BATCH_PK());
											break inner1;
										}
									}

								}
							}
						}
						// }

					}
					// }
					seatsAllocatedCandidates.add(enrollmentBean);
				} else if (testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK())) != null
						&& testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK())) > 0) {
					testCentreId = Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK());
					enrollmentBean.setAssignedTestCenter(testCentreId);
					totalRemainingSeats = testCenterWiseSeatsMap.get(testCentreId) - 1;
					testCenterWiseSeatsMap.put(testCentreId, totalRemainingSeats);

					testDateList = reportDao.getTestDates(String.valueOf(testCentreId));

					if (testDateList != null && !testDateList.isEmpty()) {
						inner3: for (String testDate : testDateList) {
							if (testDate != null) {
								// logger.info(testDate);
								testSlotList = reportDao.getTestsSlots(testDate);
								inner4: for (String testSlot : testSlotList) {
									BatchDetailsBean batchDetailsBean = null;
									batchDetailsBean = candidateMgmtDAO.getbatchDetailsForSlot(String.valueOf(testCentreId), testDate, testSlot);

									if (batchDetailsBean != null) {
										if (Integer.parseInt(batchDetailsBean.getOBD_AVAILABLE_CAPACITY()) > 0) {
											enrollmentBean.setBatchPk(batchDetailsBean.getOBD_BATCH_PK());
											// logger.info("batch pk
											// "+batchDetailsBean.getOBD_BATCH_PK());
											break inner3;
										}
									}
								}
							}
						}
					}
					seatsAllocatedCandidates.add(enrollmentBean);
				} else if (testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK())) != null
						&& testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK())) > 0) {
					testCentreId = Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK());
					enrollmentBean.setAssignedTestCenter(testCentreId);
					totalRemainingSeats = testCenterWiseSeatsMap.get(testCentreId) - 1;
					testCenterWiseSeatsMap.put(testCentreId, totalRemainingSeats);

					testDateList = reportDao.getTestDates(String.valueOf(testCentreId));

					if (testDateList != null && !testDateList.isEmpty()) {
						inner5: for (String testDate : testDateList) {
							if (testDate != null) {
								// logger.info(testDate);
								testSlotList = reportDao.getTestsSlots(testDate);
								inner6: for (String testSlot : testSlotList) {
									BatchDetailsBean batchDetailsBean = null;
									batchDetailsBean = candidateMgmtDAO.getbatchDetailsForSlot(String.valueOf(testCentreId), testDate, testSlot);

									if (batchDetailsBean != null) {
										if (Integer.parseInt(batchDetailsBean.getOBD_AVAILABLE_CAPACITY()) > 0) {
											enrollmentBean.setBatchPk(batchDetailsBean.getOBD_BATCH_PK());
											// logger.info("batch pk
											// "+batchDetailsBean.getOBD_BATCH_PK());
											break inner5;
										}
									}
								}
							}
						}
					}
					seatsAllocatedCandidates.add(enrollmentBean);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LoggerHome.getLogger(getClass()).fatal(ex);
			seatsAllocatedCandidates = new ArrayList<EnrollmentBean>();
		}

		return seatsAllocatedCandidates;
	}

	private List<EnrollmentBean> allocateSeatsTimeBasedPriorityWiseWithTestDate(Map<Integer, Integer> testCenterWiseSeatsMap, List<EnrollmentBean> candidateDetails,
			Map<String, List<BatchDetailsBean>> testDateBatchDetailsMap) {
		List<EnrollmentBean> seatsAllocatedCandidates = new ArrayList<EnrollmentBean>();
		EnrollmentBean enrollmentBean = null;
		int totalRemainingSeats = 0;
		int testCentreId;
		boolean flag = true;
		try {
			for (int currCandidateIndex = 0; currCandidateIndex < candidateDetails.size(); currCandidateIndex++) {
				enrollmentBean = candidateDetails.get(currCandidateIndex);
				mapLoop: if (enrollmentBean != null) {

					flag = true;
					// first test center
					if (enrollmentBean.getPreferredTestCentre1FK() != null && testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK())) > 0
							&& flag) {

						if (enrollmentBean.getPreferedTestDate1() != null && !enrollmentBean.getPreferedTestDate1().equals("") && flag) {// tc1
																																			// td1//tc1
																																			// td1
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(enrollmentBean.getPreferredTestCentre1FK()).append("#").append(enrollmentBean.getPreferedTestDate1());
							List<BatchDetailsBean> batchDetailsBean = testDateBatchDetailsMap.get(stringBuilder.toString().trim());

							Iterator<BatchDetailsBean> itr = batchDetailsBean.iterator();

							while (itr.hasNext() && flag) {
								BatchDetailsBean batchDetailsBeanTmp = (BatchDetailsBean) itr.next();
								BatchDetailsBean batchDetailsBeanTmp2 = new BatchDetailsBean();
								if (batchDetailsBeanTmp != null) {
									if (batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY() != null && Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) > 0) {
										enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()));
										enrollmentBean.setBatchPk(batchDetailsBeanTmp.getOBD_BATCH_PK());
										batchDetailsBeanTmp2 = batchDetailsBeanTmp;
										// batchDetailsBeanTmp2.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp2.getOBD_AVAILABLE_CAPACITY())-1));
										// itr.remove();
										batchDetailsBeanTmp.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) - 1));
										// batchDetailsBean.add(batchDetailsBeanTmp2);
										testDateBatchDetailsMap.put(stringBuilder.toString(), batchDetailsBean);
										seatsAllocatedCandidates.add(enrollmentBean);

										Integer actualCapcaity = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()));
										totalRemainingSeats = actualCapcaity - 1;

										// totalRemainingSeats =
										// testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()))-1;
										testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()), totalRemainingSeats);

										flag = false;
									}
								}
							}

						}
						if (enrollmentBean.getPreferedTestDate2() != null && !enrollmentBean.getPreferedTestDate2().equals("") && flag) {// tc1
																																			// td2
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(enrollmentBean.getPreferredTestCentre1FK()).append("#").append(enrollmentBean.getPreferedTestDate2());
							List<BatchDetailsBean> batchDetailsBean = testDateBatchDetailsMap.get(stringBuilder.toString().trim());

							Iterator<BatchDetailsBean> itr = batchDetailsBean.iterator();

							while (itr.hasNext() && flag) {
								BatchDetailsBean batchDetailsBeanTmp = (BatchDetailsBean) itr.next();
								BatchDetailsBean batchDetailsBeanTmp2 = new BatchDetailsBean();
								if (batchDetailsBeanTmp != null) {
									if (batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY() != null && Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) > 0) {
										enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()));
										enrollmentBean.setBatchPk(batchDetailsBeanTmp.getOBD_BATCH_PK());
										batchDetailsBeanTmp2 = batchDetailsBeanTmp;
										batchDetailsBeanTmp.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) - 1));

										// batchDetailsBeanTmp2.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp2.getOBD_AVAILABLE_CAPACITY())-1));
										// itr.remove();
										// batchDetailsBean.add(batchDetailsBeanTmp2);
										testDateBatchDetailsMap.put(stringBuilder.toString(), batchDetailsBean);
										seatsAllocatedCandidates.add(enrollmentBean);

										Integer actualCapcaity = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()));
										totalRemainingSeats = actualCapcaity - 1;

										// totalRemainingSeats =
										// testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()))-1;

										testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()), totalRemainingSeats);
										flag = false;
									}
								}
							}
						}
						if (enrollmentBean.getPreferedTestDate3() != null && !enrollmentBean.getPreferedTestDate3().equals("") && flag) {// tc1
																																			// td3
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(enrollmentBean.getPreferredTestCentre1FK()).append("#").append(enrollmentBean.getPreferedTestDate3());
							List<BatchDetailsBean> batchDetailsBean = testDateBatchDetailsMap.get(stringBuilder.toString().trim());

							Iterator<BatchDetailsBean> itr = batchDetailsBean.iterator();

							while (itr.hasNext() && flag) {
								BatchDetailsBean batchDetailsBeanTmp = (BatchDetailsBean) itr.next();
								BatchDetailsBean batchDetailsBeanTmp2 = new BatchDetailsBean();
								if (batchDetailsBeanTmp != null) {
									if (batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY() != null && Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) > 0) {
										enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()));
										enrollmentBean.setBatchPk(batchDetailsBeanTmp.getOBD_BATCH_PK());
										batchDetailsBeanTmp.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) - 1));
										// batchDetailsBeanTmp2 =
										// batchDetailsBeanTmp;
										// batchDetailsBeanTmp2.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp2.getOBD_AVAILABLE_CAPACITY())-1));
										// itr.remove();
										// batchDetailsBean.add(batchDetailsBeanTmp2);
										testDateBatchDetailsMap.put(stringBuilder.toString(), batchDetailsBean);
										seatsAllocatedCandidates.add(enrollmentBean);

										Integer actualCapcaity = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()));
										totalRemainingSeats = actualCapcaity - 1;

										// totalRemainingSeats =
										// testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()))-1;

										testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre1FK()), totalRemainingSeats);
										flag = false;
									}
								}
							}

						}

					}

					// second test center
					if (enrollmentBean.getPreferredTestCentre2FK() != null && testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK())) > 0
							&& flag) {
						flag = true;
						if (enrollmentBean.getPreferedTestDate1() != null && !enrollmentBean.getPreferedTestDate1().equals("") && flag) {// tc2
																																			// td1
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(enrollmentBean.getPreferredTestCentre2FK()).append("#").append(enrollmentBean.getPreferedTestDate1());
							List<BatchDetailsBean> batchDetailsBean = testDateBatchDetailsMap.get(stringBuilder.toString().trim());

							Iterator<BatchDetailsBean> itr = batchDetailsBean.iterator();

							while (itr.hasNext() && flag) {
								BatchDetailsBean batchDetailsBeanTmp = (BatchDetailsBean) itr.next();
								BatchDetailsBean batchDetailsBeanTmp2 = new BatchDetailsBean();
								if (batchDetailsBeanTmp != null) {
									if (batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY() != null && Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) > 0) {
										enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()));
										enrollmentBean.setBatchPk(batchDetailsBeanTmp.getOBD_BATCH_PK());
										batchDetailsBeanTmp2 = batchDetailsBeanTmp;
										batchDetailsBeanTmp.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) - 1));
										// itr.remove();
										// batchDetailsBean.add(batchDetailsBeanTmp2);
										testDateBatchDetailsMap.put(stringBuilder.toString(), batchDetailsBean);
										seatsAllocatedCandidates.add(enrollmentBean);

										Integer actualCapcaity = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()));
										totalRemainingSeats = actualCapcaity - 1;

										// totalRemainingSeats =
										// testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()))-1;
										testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()), totalRemainingSeats);
										// break mapLoop;
										flag = false;
									}
								}
							}

						}
						if (enrollmentBean.getPreferedTestDate2() != null && !enrollmentBean.getPreferedTestDate2().equals("") && flag) {// tc2
																																			// td2

							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(enrollmentBean.getPreferredTestCentre2FK()).append("#").append(enrollmentBean.getPreferedTestDate2());
							List<BatchDetailsBean> batchDetailsBean = testDateBatchDetailsMap.get(stringBuilder.toString().trim());

							Iterator<BatchDetailsBean> itr = batchDetailsBean.iterator();

							while (itr.hasNext() && flag) {
								BatchDetailsBean batchDetailsBeanTmp = (BatchDetailsBean) itr.next();
								BatchDetailsBean batchDetailsBeanTmp2 = new BatchDetailsBean();
								if (batchDetailsBeanTmp != null) {
									if (batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY() != null && Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) > 0) {
										enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()));
										enrollmentBean.setBatchPk(batchDetailsBeanTmp.getOBD_BATCH_PK());
										batchDetailsBeanTmp2 = batchDetailsBeanTmp;
										batchDetailsBeanTmp.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) - 1));
										// itr.remove();
										// batchDetailsBean.add(batchDetailsBeanTmp2);
										testDateBatchDetailsMap.put(stringBuilder.toString(), batchDetailsBean);
										seatsAllocatedCandidates.add(enrollmentBean);

										Integer actualCapcaity = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()));
										totalRemainingSeats = actualCapcaity - 1;
										// totalRemainingSeats =
										// testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()))-1;

										testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()), totalRemainingSeats);
										// break mapLoop;
										flag = false;
									}
								}
							}

						}
						if (enrollmentBean.getPreferedTestDate3() != null && !enrollmentBean.getPreferedTestDate3().equals("") && flag) {//// tc2
																																			//// td3

							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(enrollmentBean.getPreferredTestCentre2FK()).append("#").append(enrollmentBean.getPreferedTestDate3());
							List<BatchDetailsBean> batchDetailsBean = testDateBatchDetailsMap.get(stringBuilder.toString().trim());

							Iterator<BatchDetailsBean> itr = batchDetailsBean.iterator();

							while (itr.hasNext() && flag) {
								BatchDetailsBean batchDetailsBeanTmp = (BatchDetailsBean) itr.next();
								BatchDetailsBean batchDetailsBeanTmp2 = new BatchDetailsBean();
								if (batchDetailsBeanTmp != null) {
									if (batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY() != null && Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) > 0) {
										enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()));
										enrollmentBean.setBatchPk(batchDetailsBeanTmp.getOBD_BATCH_PK());
										batchDetailsBeanTmp2 = batchDetailsBeanTmp;
										batchDetailsBeanTmp.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) - 1));
										// itr.remove();
										// batchDetailsBean.add(batchDetailsBeanTmp2);
										testDateBatchDetailsMap.put(stringBuilder.toString(), batchDetailsBean);
										seatsAllocatedCandidates.add(enrollmentBean);

										Integer actualCapcaity = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()));
										totalRemainingSeats = actualCapcaity - 1;

										// totalRemainingSeats =
										// testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()))-1;
										testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre2FK()), totalRemainingSeats);
										// break mapLoop;
										flag = false;
									}
								}
							}

						}

					}
					// third test center
					if (enrollmentBean.getPreferredTestCentre3FK() != null && testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK())) > 0
							&& flag) {

						flag = true;
						if (enrollmentBean.getPreferedTestDate1() != null && !enrollmentBean.getPreferedTestDate1().equals("") && flag) {// tc3
																																			// td1

							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(enrollmentBean.getPreferredTestCentre3FK()).append("#").append(enrollmentBean.getPreferedTestDate1());
							List<BatchDetailsBean> batchDetailsBean = testDateBatchDetailsMap.get(stringBuilder.toString().trim());

							Iterator<BatchDetailsBean> itr = batchDetailsBean.iterator();

							while (itr.hasNext() && flag) {
								BatchDetailsBean batchDetailsBeanTmp = (BatchDetailsBean) itr.next();
								BatchDetailsBean batchDetailsBeanTmp2 = new BatchDetailsBean();
								if (batchDetailsBeanTmp != null) {
									if (batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY() != null && Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) > 0) {
										enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()));
										enrollmentBean.setBatchPk(batchDetailsBeanTmp.getOBD_BATCH_PK());
										batchDetailsBeanTmp2 = batchDetailsBeanTmp;
										batchDetailsBeanTmp.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) - 1));
										// itr.remove();
										// batchDetailsBean.add(batchDetailsBeanTmp2);
										testDateBatchDetailsMap.put(stringBuilder.toString(), batchDetailsBean);
										seatsAllocatedCandidates.add(enrollmentBean);

										Integer actualCapcaity = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()));
										totalRemainingSeats = actualCapcaity - 1;

										// totalRemainingSeats =
										// testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()))-1;

										testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()), totalRemainingSeats);
										// break mapLoop;
										flag = false;
									}
								}
							}

						}
						if (enrollmentBean.getPreferedTestDate2() != null && !enrollmentBean.getPreferedTestDate2().equals("") && flag) {// tc3
																																			// td2

							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(enrollmentBean.getPreferredTestCentre3FK()).append("#").append(enrollmentBean.getPreferedTestDate2());
							List<BatchDetailsBean> batchDetailsBean = testDateBatchDetailsMap.get(stringBuilder.toString().trim());

							Iterator<BatchDetailsBean> itr = batchDetailsBean.iterator();

							while (itr.hasNext() && flag) {
								BatchDetailsBean batchDetailsBeanTmp = (BatchDetailsBean) itr.next();
								BatchDetailsBean batchDetailsBeanTmp2 = new BatchDetailsBean();
								if (batchDetailsBeanTmp != null) {
									if (batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY() != null && Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) > 0) {
										enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()));
										enrollmentBean.setBatchPk(batchDetailsBeanTmp.getOBD_BATCH_PK());
										batchDetailsBeanTmp2 = batchDetailsBeanTmp;
										batchDetailsBeanTmp.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) - 1));
										// itr.remove();
										// batchDetailsBean.add(batchDetailsBeanTmp2);
										testDateBatchDetailsMap.put(stringBuilder.toString(), batchDetailsBean);

										Integer actualCapcaity = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()));
										totalRemainingSeats = actualCapcaity - 1;

										// totalRemainingSeats =
										// testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()))-1;

										testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()), totalRemainingSeats);
										// break mapLoop;
										flag = false;
									}
								}
							}

						}
						if (enrollmentBean.getPreferedTestDate3() != null && !enrollmentBean.getPreferedTestDate3().equals("") && flag) {// tc3
																																			// td3
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append(enrollmentBean.getPreferredTestCentre3FK()).append("#").append(enrollmentBean.getPreferedTestDate3());
							List<BatchDetailsBean> batchDetailsBean = testDateBatchDetailsMap.get(stringBuilder.toString().trim());

							Iterator<BatchDetailsBean> itr = batchDetailsBean.iterator();

							while (itr.hasNext() && flag) {
								BatchDetailsBean batchDetailsBeanTmp = (BatchDetailsBean) itr.next();
								BatchDetailsBean batchDetailsBeanTmp2 = new BatchDetailsBean();
								if (batchDetailsBeanTmp != null) {
									if (batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY() != null && Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) > 0) {
										enrollmentBean.setAssignedTestCenter(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()));
										enrollmentBean.setBatchPk(batchDetailsBeanTmp.getOBD_BATCH_PK());
										batchDetailsBeanTmp2 = batchDetailsBeanTmp;
										batchDetailsBeanTmp.setOBD_AVAILABLE_CAPACITY(String.valueOf(Integer.parseInt(batchDetailsBeanTmp.getOBD_AVAILABLE_CAPACITY()) - 1));
										// itr.remove();
										// batchDetailsBean.add(batchDetailsBeanTmp2);
										testDateBatchDetailsMap.put(stringBuilder.toString(), batchDetailsBean);
										seatsAllocatedCandidates.add(enrollmentBean);

										Integer actualCapcaity = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()));
										totalRemainingSeats = actualCapcaity - 1;

										totalRemainingSeats = testCenterWiseSeatsMap.get(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK())) - 1;

										testCenterWiseSeatsMap.put(Integer.parseInt(enrollmentBean.getPreferredTestCentre3FK()), totalRemainingSeats);
										// break mapLoop;
										flag = false;
									}
								}
							}
						}
					}
				} // bean is not null
			} // for loop for candidate

		} catch (Exception ex) {
			ex.printStackTrace();
			LoggerHome.getLogger(getClass()).fatal(ex);
			seatsAllocatedCandidates = new ArrayList<EnrollmentBean>();
		}

		return seatsAllocatedCandidates;
	}

	@Override
	public int getPaymentReportSearchResultCount(CandidateMgmtBean candidateMgmtBean) throws Exception {
		return candidateMgmtDAO.getPaymentReportSearchResultCount(candidateMgmtBean);
	}

	public List<CandidateMgmtBean> getPaymentReportSearchResult(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<CandidateMgmtBean> paymentReportResultBeanList = null;
		try {
			paymentReportResultBeanList = candidateMgmtDAO.getPaymentReportSearchResult(candidateMgmtBean, pagination);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return paymentReportResultBeanList;
	}

	public List<CandidateMgmtBean> getPaymentReportSearchResult(CandidateMgmtBean candidateMgmtBean) throws Exception {
		List<CandidateMgmtBean> paymentReportResultBeanList = null;
		try {
			paymentReportResultBeanList = candidateMgmtDAO.getPaymentReportSearchResult(candidateMgmtBean);
		} catch (Exception e) {
			throw new GenericServiceException(e);
		}

		return paymentReportResultBeanList;
	}

	public int getCandidateDetailsForPaymentNotSubmittedCount(CandidateMgmtBean candidateMgmtBean) throws Exception {
		return candidateMgmtDAO.getCandidateDetailsForPaymentNotSubmittedCount(candidateMgmtBean);
	}

	public List<CandidateMgmtBean> getCandidateDetailsForPaymentNotSubmitted(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		return candidateMgmtDAO.getCandidateDetailsForPaymentNotSubmitted(candidateMgmtBean, pagination);
	}

	public List<CandidateMgmtBean> getCandidateDetailsForPaymentNotSubmitted(CandidateMgmtBean candidateMgmtBean) throws Exception {
		return candidateMgmtDAO.getCandidateDetailsForPaymentNotSubmitted(candidateMgmtBean);
	}

	@Override
	public void insertEpostPaymentDetails(PaymentBean pb, String applicationNo, Integer paymentKeyByDesc, String tranId) throws Exception {
		candidateMgmtDAO.insertEpostPaymentDetails(pb, applicationNo, paymentKeyByDesc, tranId);
	}

}