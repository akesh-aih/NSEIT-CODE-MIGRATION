package com.nseit.generic.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.ReportDao;
import com.nseit.generic.models.HallTicketBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentReconciliationReportBean;
import com.nseit.generic.models.PaymentReportBean;
import com.nseit.generic.models.ReportBean;
import com.nseit.generic.models.ScheduleReportBean;
import com.nseit.generic.queries.ReportQueries;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;

public class ReportDaoImpl extends BaseDAO implements ReportDao {

	private Logger logger = LoggerHome.getLogger(getClass());
	private Connection connection = null;

	public Connection getDataSourceConnection() throws Exception {
		try {
			if (connection == null || (connection.isClosed())) {
				connection = getDataSource().getConnection();
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return connection;

	}

	public List<List> getSeatUtilizationReport() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		List<String> rowData = null;
		List<List> seatAllocationReport = new ArrayList<List>();
		List<String> slotList = new ArrayList<String>();
		ResultSet rs = null;
		ResultSet rs2 = null;

		/*
		 * String sqlQry =
		 * "select * from( select distinct otcm_TEST_CENTRE_NAME, otcm_city, Total_Capacity, "
		 * +
		 * "sum(batch1) over(partition by otcm_TEST_CENTRE_NAME,otcm_city,t_date order by t_date) as batch1, "
		 * +
		 * "sum(batch2) over(partition by otcm_TEST_CENTRE_NAME,otcm_city,t_date order by t_date) as batch2,"
		 * +
		 * "sum(batch3) over(partition by otcm_TEST_CENTRE_NAME,otcm_city,t_date order by t_date) as batch3, t_date from( "
		 * +
		 * " select otcm_TEST_CENTRE_NAME, otcm_city, nvl(otcm_TOTAL_CAPACITY,0) as Total_Capacity, "
		 * +
		 * " case when extract(hour from obd_TEST_START_TIME) =10 and extract(hour from obd_TEST_end_TIME) = 12 then obd_AVAILABLE_CAPACITY"
		 * +
		 * " else 0 end batch1, case when extract(hour from obd_TEST_START_TIME) =13 and extract(hour from obd_TEST_end_TIME) = 15 then obd_AVAILABLE_CAPACITY"
		 * +
		 * " else 0 end batch2, case when extract(hour from obd_TEST_START_TIME) =16 and extract(hour from obd_TEST_end_TIME) = 18 then obd_AVAILABLE_CAPACITY"
		 * +
		 * " else 0 end   batch3, trunc(obd_TEST_DATE) as t_date from gcet_test_center_master gtcm, gcet_batch_details gbd "
		 * + "where gtcm.otcm_TEST_CENTRE_PK = gbd.obd_TEST_CENTRE_FK  )) " +
		 * "order by otcm_TEST_CENTRE_NAME,otcm_city,t_date";
		 */

		String testDatesQuery = "SELECT DISTINCT to_char(obd_TEST_DATE, 'dd-Mon-yyyy') || '<br/>' || "
				+ " to_char(obd_TEST_START_TIME, 'HH24:mi') || '-' || to_char(obd_TEST_END_TIME, 'HH24:mi') as testSlot , obd_TEST_DATE, obd_TEST_START_TIME "
				+ " FROM oes_test_center_master INNER JOIN oes_batch_details ON obd_TEST_CENTRE_FK=otcm_TEST_CENTRE_PK " + " ORDER BY  obd_TEST_DATE, obd_TEST_START_TIME ";

		/*
		 * "SELECT DISTINCT to_char(obd_TEST_DATE, 'dd-Mon-yyyy') || '<br/>' ||  "
		 * +
		 * " to_char(obd_TEST_START_TIME, 'HH24:mi') || '-' || to_char(obd_TEST_END_TIME, 'HH24:mi') as testSlot , obd_TEST_DATE, obd_TEST_START_TIME"
		 * +
		 * " FROM gcet_test_center_master INNER JOIN gcet_batch_details ON obd_TEST_CENTRE_FK=otcm_TEST_CENTRE_PK "
		 * + " ORDER BY  obd_TEST_DATE, obd_TEST_START_TIME ";
		 */

		String tstSlots = "";

		String sqlQry = "SELECT * 	FROM ( SELECT otcm_TEST_CENTRE_NAME, otcm_city, otcm_TOTAL_CAPACITY, to_char(obd_TEST_DATE, 'dd-Mon-yyyy') || '<br/>' || "
				+ " to_char(obd_TEST_START_TIME, 'HH24:mi') || '-' || to_char(obd_TEST_END_TIME, 'HH24:mi') Date_Slot, otcm_TOTAL_CAPACITY-obd_AVAILABLE_CAPACITY OCCUPIED "
				+ " FROM oes_test_center_master INNER JOIN oes_batch_details ON obd_TEST_CENTRE_FK=otcm_TEST_CENTRE_PK) " + " PIVOT (MIN(nvl(OCCUPIED, 0)) "
				+ " FOR Date_Slot IN (%s))";

		/*
		 * "	SELECT * 	FROM ( SELECT otcm_TEST_CENTRE_NAME, otcm_city, otcm_TOTAL_CAPACITY, to_char(obd_TEST_DATE, 'dd-Mon-yyyy') || '<br/>' || "
		 * +
		 * "	to_char(obd_TEST_START_TIME, 'HH24:mi') || '-' || to_char(obd_TEST_END_TIME, 'HH24:mi') Date_Slot, otcm_TOTAL_CAPACITY-obd_AVAILABLE_CAPACITY OCCUPIED "
		 * +
		 * " FROM gcet_test_center_master INNER JOIN gcet_batch_details ON obd_TEST_CENTRE_FK=otcm_TEST_CENTRE_PK) "
		 * + " PIVOT (MIN(nvl(OCCUPIED, 0)) "+ " FOR Date_Slot IN (%s))";
		 */

		// tstSlots = tstSlots +"'";

		try {
			conn = getDataSourceConnection();
			ps = conn.prepareStatement(testDatesQuery);
			rs2 = ps.executeQuery();
			while (rs2.next()) {
				tstSlots = tstSlots + "'" + rs2.getString("testSlot") + "'" + ",";
				slotList.add(rs2.getString("testSlot"));
			}

			tstSlots = tstSlots.substring(0, tstSlots.length() - 1);

			ps = conn.prepareStatement(sqlQry.format(sqlQry, tstSlots));
			ps.executeQuery();
			rs = ps.getResultSet();
			ResultSetMetaData rsMetaData = rs.getMetaData();

			while (rs.next()) {
				rowData = new ArrayList<String>();
				for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {

					rowData.add(rs.getString(i));

				}
				seatAllocationReport.add(rowData);

			}
			seatAllocationReport.add(slotList);
			/*
			 * List<HashMap<String, String>> list = new
			 * ArrayList<HashMap<String, String>>(); HashMap<String, Integer>
			 * dayCountMap = new HashMap<String, Integer>(); HashMap<String,
			 * HashMap<String, String>> dayBatchMap = new HashMap<String,
			 * HashMap<String, String>>();
			 * 
			 * HashMap<String, TreeSet<String>> dayMap = new HashMap<String,
			 * TreeSet<String>>();
			 * 
			 * while (rs.next()) { Date testDate = rs.getDate("T_DATE"); String
			 * strTestDate = sdf.format(testDate);
			 * 
			 * HashMap<String, String> map = new HashMap<String, String>();
			 * String centerName = rs.getString("otcm_TEST_CENTRE_NAME");
			 * 
			 * map.put("CENTER", centerName); map.put("otcm_CITY",
			 * rs.getString("otcm_CITY")); map.put("TOTAL_CAPACITY",
			 * rs.getString("TOTAL_CAPACITY")); map.put("BATCH1",
			 * rs.getString("BATCH1")); map.put("BATCH2",
			 * rs.getString("BATCH2")); map.put("BATCH3",
			 * rs.getString("BATCH3")); TreeSet<String> hashSet = new
			 * TreeSet<String>(); hashSet.add(strTestDate); TreeSet<String> set
			 * = dayMap.put(centerName, hashSet); if (set != null) {
			 * set.add(strTestDate); dayMap.put(centerName, set); }
			 * 
			 * Integer oldVal = dayCountMap.put(centerName, 1); if (oldVal !=
			 * null) { dayCountMap.put(centerName, oldVal + 1); }
			 * 
			 * dayBatchMap.put(centerName + strTestDate, map); list.add(map); }
			 * 
			 * 
			 * Iterator<String> dayCntItr = dayCountMap.keySet().iterator(); int
			 * max = 0; int index = 0; while (dayCntItr.hasNext()) { String
			 * center = dayCntItr.next(); int cnt = dayCountMap.get(center); if
			 * (index == 0) { max = cnt; index++; } if (max < cnt) { max = cnt;
			 * } }
			 * 
			 * HashMap<String, Object> maxCount = new HashMap<String, Object>();
			 * maxCount.put("maxDay", max); returnList.add(maxCount);
			 * 
			 * Iterator<HashMap<String, String>> itr = list.iterator();
			 * HashSet<String> centerSet = new HashSet<String>(); while
			 * (itr.hasNext()) { HashMap<String, String> row = itr.next();
			 * 
			 * String testCenter = row.get("CENTER"); if
			 * (centerSet.add(testCenter)) { HashMap<String, Object> bean = new
			 * HashMap<String, Object>();
			 * 
			 * TreeSet<String> daySet = dayMap.get(testCenter);
			 * bean.put("center", testCenter); bean.put("city",
			 * row.get("otcm_CITY")); String strtotalCapacity =
			 * row.get("TOTAL_CAPACITY"); int totalCapacity = 0;
			 * if(strtotalCapacity!=null &&strtotalCapacity.trim().length()>0) {
			 * totalCapacity = Integer.parseInt(strtotalCapacity); }
			 * bean.put("capacity", totalCapacity+"");
			 * 
			 * Iterator<String> dayItr = daySet.iterator();
			 * 
			 * HashMap<String, HashMap<String, String>> daySeatMap = new
			 * HashMap<String, HashMap<String, String>>();
			 * 
			 * int daycount = 0;
			 * 
			 * while (dayItr.hasNext()) { String day = dayItr.next();
			 * HashMap<String, String> batch = dayBatchMap .get(testCenter +
			 * day); HashMap<String, String> batchDetail = new HashMap<String,
			 * String>(); String strbatch1 = batch.get("BATCH1"); String
			 * strbatch2 = batch.get("BATCH2"); String strbatch3 =
			 * batch.get("BATCH3"); int batch1 = 0; int batch2 = 0; int batch3 =
			 * 0; if(strbatch1!=null && strbatch1.trim().length()>0) { batch1 =
			 * Integer.parseInt(strbatch1); } if(strbatch2!=null &&
			 * strbatch2.trim().length()>0) { batch2 =
			 * Integer.parseInt(strbatch2); } if(strbatch3!=null &&
			 * strbatch3.trim().length()>0) { batch3 =
			 * Integer.parseInt(strbatch3); }
			 * 
			 * batchDetail.put("BATCH1", (totalCapacity-batch1)+"");
			 * batchDetail.put("BATCH2", (totalCapacity-batch2)+"");
			 * batchDetail.put("BATCH3", (totalCapacity-batch3)+"");
			 * 
			 * daySeatMap.put("DAY" + (++daycount), batchDetail); }
			 * bean.put("dayMap", daySeatMap); returnList.add(bean); } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (rs != null) {
					rs.close();
				}
				if (rs2 != null) {
					rs2.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {

			}
		}
		return seatAllocationReport;
	}

	@SuppressWarnings("unchecked")
	public List<String> getTestDates(String tstCntrPk) throws Exception {
		List<String> tstDateList = null;
		String testDates = null;

		try {
			tstDateList = getJdbcTemplate().query(ReportQueries.GET_TEST_DATES, new Object[] { Integer.parseInt(tstCntrPk) }, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowCount) throws SQLException {
					String tstDate = rs.getString("obd_test_date");
					return tstDate;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return tstDateList;
	}

	@SuppressWarnings("unchecked")
	public List<String> getTestsSlots(String testDate) throws Exception {
		List<String> tstSlotList = null;
		try {
			tstSlotList = getJdbcTemplate().query(ReportQueries.GET_TEST_SLOTS, new Object[] { testDate }, new RowMapper<String>() {
				public String mapRow(ResultSet rs, int rowCount) throws SQLException {
					String tstSlot = rs.getString("batch_slot");
					return tstSlot;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return tstSlotList;
	}

	public List<HallTicketBean> getBulkDownloadDataForHallTicket(ReportBean reportBean, boolean isRollNumGen) throws GenericException {
		List<HallTicketBean> hallTicketBulkList = null;

		String queryString = null;

		if (isRollNumGen) {
			queryString = ReportQueries.GET_HALLTICKET_DATA_FOR_BULK_DOWNLOAD_SORT_BY_ROLL_NUMBER;
		} else {
			queryString = ReportQueries.GET_HALLTICKET_DATA_FOR_BULK_DOWNLOAD_SORT_BY_ENROLLMENT_ID;
		}

		try {
			hallTicketBulkList = getJdbcTemplate().query(queryString,
					new Object[] { reportBean.getAttemptVal(), reportBean.getAttemptVal(), reportBean.getTestDate(), reportBean.getTestDate(), reportBean.getTestCenterVal(),
							reportBean.getTestCenterVal(), reportBean.getDisciplineVal(), reportBean.getDisciplineVal(), reportBean.getTestSlot(), reportBean.getTestSlot(), },
					new RowMapper<HallTicketBean>() {

						@Override
						public HallTicketBean mapRow(ResultSet rs, int rowCount) throws SQLException {

							HallTicketBean hallTicketBean = new HallTicketBean();

							hallTicketBean.setDisciplineType(rs.getString("OTM_TEST_NAME"));

							hallTicketBean.setUserId(rs.getString("OUM_USER_ID"));

							hallTicketBean.setEnrollmetPk(rs.getString("OED_ENROLLMENT_PK"));

							hallTicketBean.setUserName(rs.getString("OCD_NAME"));

							hallTicketBean.setTestCenterName(rs.getString("OTCM_TEST_CENTRE_NAME"));

							hallTicketBean.setTestCenterAddress(rs.getString("OTCM_ADDRESS"));

							hallTicketBean.setTestDate(rs.getString("TEST_DATE"));

							hallTicketBean.setReportingTime(rs.getString("REPORTING_TIME"));

							hallTicketBean.setTestStartTime(rs.getString("EXAM_START_TIME"));

							hallTicketBean.setBookingAttempt(rs.getInt("OED_ATTEMPT"));

							Integer attempt = rs.getInt("OED_ATTEMPT");
							if (attempt != null && attempt == 1) {
								hallTicketBean.setBookingAttemptVal("I");
							}

							if (attempt != null && attempt == 2) {
								hallTicketBean.setBookingAttemptVal("II");
							}

							String password = null;

							try {
								password = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rs.getString("OUM_PASSWORD"));
							} catch (Exception e1) {
								LoggerHome.getLogger(getClass()).fatal(e1, e1);
							}

							hallTicketBean.setUserPassword(password);

							hallTicketBean.setRollNumber(rs.getString("OED_ROLL_NO"));

							ByteArrayInputStream byteArrayInputStreamForImage = null;

							ByteArrayInputStream byteArrayInputStreamForSignature = null;
							try {
								if (rs.getBlob("OCI_PHOTO_IMAGE") != null) {
									byteArrayInputStreamForImage = new ByteArrayInputStream(
											rs.getBlob("OCI_PHOTO_IMAGE").getBytes(1, (int) (rs.getBlob("OCI_PHOTO_IMAGE").length())));
									hallTicketBean.setInputStreamForImage((InputStream) byteArrayInputStreamForImage);
								}

								if (rs.getBlob("OCI_SIGN_IMAGE") != null) {
									byteArrayInputStreamForSignature = new ByteArrayInputStream(
											rs.getBlob("OCI_SIGN_IMAGE").getBytes(1, (int) (rs.getBlob("OCI_SIGN_IMAGE").length())));
									hallTicketBean.setInputStreamForSignature((InputStream) byteArrayInputStreamForSignature);
								}

							} catch (SQLException e) {
								LoggerHome.getLogger(getClass()).fatal(e, e);
							}

							return hallTicketBean;
						}
					});
		} catch (DataAccessException e) {
			throw new GenericException(e);
		}

		if (hallTicketBulkList != null && !hallTicketBulkList.isEmpty()) {
			return hallTicketBulkList;
		} else {
			return null;
		}
	}

	/**
	 * @author shailendras
	 * @param reportBean
	 * @return
	 */
	public int getScheduleReportDetailsCount(ReportBean reportBean) {
		int scheduleCandidateReportDetailCount = 0;
		StringBuilder strBldr = new StringBuilder();
		try {
			strBldr.append(ReportQueries.GET_SCHEDULE_REPORT_COUNT);
			if (!ValidatorUtil.isEmpty(reportBean.getUserId())) {
				strBldr.append(" AND OUM_USER_ID = '" + reportBean.getUserId().trim() + "'");
			}
			if (!ValidatorUtil.isEmpty(reportBean.getDisciplineId())) {
				strBldr.append(" AND OTM_TEST_PK = " + reportBean.getDisciplineId());
			}
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				strBldr.append(" AND TRUNC(OBD_TEST_DATE) BETWEEN '" + reportBean.getFromDate() + "' AND '" + reportBean.getToDate() + "'");
			}
			if (!reportBean.getTestCenterId().equals(new Integer(0))) {
				strBldr.append(" AND OTCM_TEST_CENTRE_PK  = " + reportBean.getTestCenterId());
			}
			if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")) {
				if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("1")) {
					strBldr.append(" AND OED_GCPD_USER_FK IS NOT NULL");
				}
			} else {
				if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("1")) {
					strBldr.append(" AND OCD_VALIDATED_STATUS = 1 AND MAIN.OED_GCPD_USER_FK IS NOT NULL");
				}
			}

			if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")) {
				if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("2")) {
					strBldr.append(" AND OCD_USER_FK IN (SELECT opd_user_fk FROM oes_payment_details WHERE (opd_validated_status IS NULL OR opd_validated_status = 'R'");
					strBldr.append(" )) AND ocd.ocd_user_fk NOT IN (SELECT oed_gcpd_user_fk FROM oes_entrollment_details) ");
					strBldr.append("  AND ocd_validated_status = 1");
				}
			} else {
				if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("2")) {
					strBldr.append(" AND OCD_FORM_SUBMITED_DATE IS NOT NULL AND OCD_VALIDATED_STATUS IS NULL");
				}
			}

			if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")) {
				if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("3")) {
					strBldr.append(" AND OCD.OCD_USER_FK IN (SELECT OPD_USER_FK FROM OES_PAYMENT_DETAILS ");
					strBldr.append(" WHERE OPD_VALIDATED_STATUS = 'A' AND OPD_USER_FK NOT IN( SELECT OED_GCPD_USER_FK FROM OES_ENTROLLMENT_DETAILS))");
				}
			} else {
				if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("3")) {
					strBldr.append(" AND OCD_VALIDATED_STATUS = 1 AND MAIN.OED_GCPD_USER_FK is  null ");
				}
			}
			if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("4")) {
				strBldr.append(" AND OED_FLAG = 1 AND OED_ADMIT_CARD_DATE IS NOT NULL");
			}
			scheduleCandidateReportDetailCount = getJdbcTemplate().queryForObject(strBldr.toString(), new Object[] {}, Integer.class);
		} catch (Exception e) {
			scheduleCandidateReportDetailCount = 0;
		}
		return scheduleCandidateReportDetailCount;
	}

	/**
	 * @author shailendras
	 * @param reportBean
	 * @return
	 */

	public List<ScheduleReportBean> getScheduleReportDetail(ReportBean reportBean, Pagination pagination) {
		List<ScheduleReportBean> scheduleCandidateReportDetail = null;
		StringBuilder strBldr = new StringBuilder();
		try {
			strBldr.append(ReportQueries.GET_SCHEDULE_REPORT);
			if (!ValidatorUtil.isEmpty(reportBean.getUserId())) {
				strBldr.append(" AND OUM_USER_ID = '" + reportBean.getUserId().trim() + "'");
			} else {
				if (!ValidatorUtil.isEmpty(reportBean.getDisciplineId())) {
					strBldr.append(" AND OTM_TEST_PK = " + reportBean.getDisciplineId());
				}
				if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
					strBldr.append(" AND TRUNC (OBD_TEST_DATE) BETWEEN '" + reportBean.getFromDate() + "' AND '" + reportBean.getToDate() + "'");
				}
				if (!reportBean.getTestCenterId().equals(new Integer(0))) {
					strBldr.append(" AND OTCM_TEST_CENTRE_PK  = " + reportBean.getTestCenterId());
				}
				if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")) {
					if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("1")) {
						strBldr.append(" AND OED_GCPD_USER_FK IS NOT NULL");
					}
				} else {
					if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("1")) {
						strBldr.append(" AND OCD_VALIDATED_STATUS = 1 AND MAIN.OED_GCPD_USER_FK IS NOT NULL");
					}
				}

				if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")) {
					if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("2")) {
						strBldr.append(" AND OCD_USER_FK IN (SELECT opd_user_fk FROM oes_payment_details WHERE (opd_validated_status IS NULL OR opd_validated_status = 'R'");
						strBldr.append(" )) AND ocd.ocd_user_fk NOT IN (SELECT oed_gcpd_user_fk FROM oes_entrollment_details) ");
						strBldr.append("  AND ocd_validated_status = 1");
					}
				} else {
					if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("2")) {
						strBldr.append(" AND OCD_FORM_SUBMITED_DATE IS NOT NULL AND OCD_VALIDATED_STATUS IS NULL");
					}
				}

				if (ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.PAYMENT).equals("A")) {
					if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("3")) {
						strBldr.append(" AND OCD.OCD_USER_FK IN (SELECT OPD_USER_FK FROM OES_PAYMENT_DETAILS ");
						strBldr.append(" WHERE OPD_VALIDATED_STATUS = 'A' AND OPD_USER_FK NOT IN( SELECT OED_GCPD_USER_FK FROM OES_ENTROLLMENT_DETAILS))");
					}
				} else {
					if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("3")) {
						strBldr.append(" AND OCD_VALIDATED_STATUS = 1 AND MAIN.OED_GCPD_USER_FK is  null ");
					}
				}
				if (reportBean.getScheduleStatusId() != null && reportBean.getScheduleStatusId().equals("4")) {
					strBldr.append(" AND OED_FLAG = 1 AND OED_ADMIT_CARD_DATE IS NOT NULL");
				}
			}

			strBldr.append(" ORDER BY OUM_USER_ID )");
			strBldr.append(" WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd());
			scheduleCandidateReportDetail = getJdbcTemplate().query(strBldr.toString(), new Object[] {}, new BeanPropertyRowMapper(ScheduleReportBean.class));
		} catch (Exception e) {
			scheduleCandidateReportDetail = null;
		}
		return scheduleCandidateReportDetail;
	}

	@SuppressWarnings("unchecked")
	public List<PaymentReportBean> getPaymentReportDetail(ReportBean reportBean) {
		List<PaymentReportBean> paymentReportDetail = null;
		try {
			paymentReportDetail = getJdbcTemplate().query(ReportQueries.GET_PAYMENT_REPORT,
					new Object[] { reportBean.getPaymentStatusId(), reportBean.getPaymentStatusId(), reportBean.getDisciplineId(), reportBean.getDisciplineId(),
							reportBean.getPaymentModeId(), reportBean.getPaymentModeId(), reportBean.getFromDate(), reportBean.getFromDate(), reportBean.getToDate(),
							reportBean.getToDate(), reportBean.getUserId(), reportBean.getUserId() },
					new BeanPropertyRowMapper(PaymentReportBean.class));
		} catch (Exception e) {
			e.printStackTrace();
			paymentReportDetail = null;
		}
		return paymentReportDetail;
	}

	public int getPaymentCollectionReportDetailCount(ReportBean reportBean) {
		int paymentCollectionReportDetailCount = 0;
		try {
			if (reportBean.getFromDate() == null || reportBean.getFromDate().equals("")) {
				reportBean.setFromDate(" ");
			}
			if (reportBean.getToDate() == null || reportBean.getToDate().equals("")) {
				reportBean.setToDate(" ");
			}
			paymentCollectionReportDetailCount = getJdbcTemplate().queryForObject(ReportQueries.GET_PAYMENT_COLLECTION_REPORT_COUNT,
					new Object[] { reportBean.getPaymentModeId(), reportBean.getPaymentModeId(), reportBean.getFromDate().toUpperCase(), reportBean.getFromDate().toUpperCase(),
							reportBean.getToDate().toUpperCase(), reportBean.getToDate().toUpperCase(), reportBean.getPaymentModeId(), reportBean.getPaymentModeId(),
							reportBean.getFromDate().toUpperCase(), reportBean.getFromDate().toUpperCase(), reportBean.getToDate().toUpperCase(),
							reportBean.getToDate().toUpperCase() }, Integer.class);
			// logger.info("PAYMENT_COLLECTION_REPORT_COUNT\n"+ReportQueries.GET_PAYMENT_COLLECTION_REPORT_COUNT);
		} catch (Exception e) {
			paymentCollectionReportDetailCount = 0;
		}
		return paymentCollectionReportDetailCount;
	}

	@SuppressWarnings("unchecked")
	public List<PaymentReportBean> getPaymentCollectionReportDetail(ReportBean reportBean, Pagination pagination) {
		List<PaymentReportBean> paymentCollectionReportDetail = null;
		try {
			if (reportBean.getFromDate() == null || reportBean.getFromDate().equals("")) {
				reportBean.setFromDate(" ");
			}
			if (reportBean.getToDate() == null || reportBean.getToDate().equals("")) {
				reportBean.setToDate(" ");
			}
			paymentCollectionReportDetail = getJdbcTemplate().query(ReportQueries.GET_PAYMENT_COLLECTION_REPORT,
					new Object[] { reportBean.getPaymentModeId(), reportBean.getPaymentModeId(), reportBean.getFromDate().toUpperCase(), reportBean.getFromDate().toUpperCase(),
							reportBean.getToDate().toUpperCase(), reportBean.getToDate().toUpperCase(), reportBean.getPaymentModeId(), reportBean.getPaymentModeId(),
							reportBean.getFromDate().toUpperCase(), reportBean.getFromDate().toUpperCase(), reportBean.getToDate().toUpperCase(),
							reportBean.getToDate().toUpperCase(), pagination.getStart(), pagination.getEnd() },
					new BeanPropertyRowMapper(PaymentReportBean.class));
			// logger.info("PAYMENT_COLLECTION_REPORT_DETAILS\n"+ReportQueries.GET_PAYMENT_COLLECTION_REPORT_COUNT);
		} catch (Exception e) {
			e.printStackTrace();
			paymentCollectionReportDetail = null;
		}
		return paymentCollectionReportDetail;
	}

	public List<PaymentReportBean> getPaymentCollectionReportDetail(ReportBean reportBean) {
		List<PaymentReportBean> paymentCollectionReportDetail = null;
		try {
			if (reportBean.getFromDate() == null || reportBean.getFromDate().equals("")) {
				reportBean.setFromDate(" ");
			}
			if (reportBean.getToDate() == null || reportBean.getToDate().equals("")) {
				reportBean.setToDate(" ");
			}
			paymentCollectionReportDetail = getJdbcTemplate().query(ReportQueries.GET_PAYMENT_COLLECTION_REPORT_EXCEL,
					new Object[] { reportBean.getPaymentModeId(), reportBean.getPaymentModeId(), reportBean.getFromDate().toUpperCase(), reportBean.getFromDate().toUpperCase(),
							reportBean.getToDate().toUpperCase(), reportBean.getToDate().toUpperCase(), reportBean.getPaymentModeId(), reportBean.getPaymentModeId(),
							reportBean.getFromDate().toUpperCase(), reportBean.getFromDate().toUpperCase(), reportBean.getToDate().toUpperCase(),
							reportBean.getToDate().toUpperCase() },
					new BeanPropertyRowMapper(PaymentReportBean.class));
			// logger.info("PAYMENT_COLLECTION_REPORT_DETAILS in excell
			// query\n"+ReportQueries.GET_PAYMENT_COLLECTION_REPORT_COUNT);
		} catch (Exception e) {
			paymentCollectionReportDetail = null;
		}
		return paymentCollectionReportDetail;
	}

	@SuppressWarnings("unchecked")
	public List<PaymentReconciliationReportBean> getPaymentReconcilliationStatusResult(ReportBean reportBean) throws Exception {
		StringBuilder queryString = new StringBuilder();

		// Map<Integer, String> paymentModeTypeMap =
		// ConfigurationConstants.getInstance().getPaymentMasterMap();

		Map<String, Integer> paymentModeTypeMap = ConfigurationConstants.getInstance().getPaymentMasterReverseMap();

		Integer modeType = null;
		if (paymentModeTypeMap != null) {
			modeType = paymentModeTypeMap.get("Challan");
		}

		String disciplineVal = reportBean.getDisciplineVal();

		if (disciplineVal != null && disciplineVal.equals("")) {
			disciplineVal = "0";
		}

		if (reportBean.getPaymentReconcStatusVal() != null && (reportBean.getPaymentReconcStatusVal().equals("") || reportBean.getPaymentReconcStatusVal().equals("All"))) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_ALL_STATUS);

			if (disciplineVal.equals("0")) {
				queryString.append(" WHERE obcd.obcd_challan_no = opd_dd_challan_receipt_no(+)    GROUP BY otm_test_name");
			} else {
				queryString.append(" WHERE obcd.obcd_challan_no = opd_dd_challan_receipt_no    GROUP BY otm_test_name");
			}
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("0")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_RECONCILE_STATUS);

			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}

			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (OPD_TRANSACTION_DATE) >= '" + reportBean.getFromDate() + "'");
			}
			if (ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (OPD_TRANSACTION_DATE) <= '" + reportBean.getToDate() + "'");
			}

			queryString
					.append(" ), oes_bank_challan_details obcd where obcd.OBCD_CHALLAN_NO = OPD_DD_CHALLAN_RECEIPT_NO and obcd.OBCD_RECONCILE_FLAG = 'Y' group by OTM_TEST_NAME");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("1")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_NOT_RECONCILE_STATUS);

			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}

			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (OPD_TRANSACTION_DATE) >= '" + reportBean.getFromDate() + "'");
			}
			if (ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (OPD_TRANSACTION_DATE) <= '" + reportBean.getToDate() + "'");
			}

			if (disciplineVal.equals("0")) {
				queryString.append(
						" ) , oes_bank_challan_details obcd where obcd.OBCD_CHALLAN_NO = OPD_DD_CHALLAN_RECEIPT_NO (+) and obcd.OBCD_RECONCILE_FLAG = 'N' group by OTM_TEST_NAME");
			} else {
				queryString.append(
						" ) , oes_bank_challan_details obcd where obcd.OBCD_CHALLAN_NO = OPD_DD_CHALLAN_RECEIPT_NO and obcd.OBCD_RECONCILE_FLAG = 'N' group by OTM_TEST_NAME");
			}

		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("2")) {

			if (disciplineVal.equals("0")) {
				queryString.append(" WHERE obcd.obcd_challan_no = opd_dd_challan_receipt_no(+)    GROUP BY otm_test_name");
			} else {
				queryString.append(" WHERE obcd.obcd_challan_no = opd_dd_challan_receipt_no    GROUP BY otm_test_name");
			}

			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_PAYMENT_SUBMITTED_STATUS);

			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}
			// queryString.append(" ) , oes_bank_challan_details obcd where
			// obcd.OBCD_CHALLAN_NO(+) = OPD_DD_CHALLAN_RECEIPT_NO /*and
			// obcd.OBCD_CHALLAN_NO is null*/ group by OTM_TEST_NAME");
			queryString.append(" group by OTM_TEST_NAME");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("3")) {

			if (disciplineVal.equals("0")) {
				queryString.append(" WHERE obcd.obcd_challan_no = opd_dd_challan_receipt_no(+)    GROUP BY otm_test_name");
			} else {
				queryString.append(" WHERE obcd.obcd_challan_no = opd_dd_challan_receipt_no    GROUP BY otm_test_name");
			}
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_SUMMARY_RESULT_DATA_FOR_PAYMENT_NOT_SUBMITTED_STATUS);
			modeType = 1;
		}

		List<PaymentReconciliationReportBean> paymentreconciliateResultList = null;
		try {
			paymentreconciliateResultList = getJdbcTemplate().query(queryString.toString(), new Object[] { disciplineVal, disciplineVal },
					new RowMapper<PaymentReconciliationReportBean>() {

						public PaymentReconciliationReportBean mapRow(ResultSet rs, int rowNum) {
							PaymentReconciliationReportBean paymentReconcilliationMainResult = new PaymentReconciliationReportBean();
							try {

								// if(rs.getString("OTM_TEST_NAME") != null)
								// {
								paymentReconcilliationMainResult.setDiscipline(rs.getString("OTM_TEST_NAME"));
								paymentReconcilliationMainResult.setNoOfCandidates(rs.getInt("TOTAL_CANDIDATES"));
								paymentReconcilliationMainResult.setApplicableFeesAmt(rs.getBigDecimal("APPLICABLE_FEES_AMT"));

								// }
							} catch (SQLException e) {
								LoggerHome.getLogger(getClass()).fatal(e, e);
							}

							return paymentReconcilliationMainResult;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (paymentreconciliateResultList != null && !paymentreconciliateResultList.isEmpty()) {
			return paymentreconciliateResultList;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public PaymentReconciliationReportBean getPaymentReconcilliationDetailsForUserID(ReportBean reportBean) throws Exception {
		StringBuilder queryString = new StringBuilder();

		queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_DATA_FOR_USER_ID);

		Map<Integer, String> paymentModeTypeMap = ConfigurationConstants.getInstance().getPaymentMasterMap();

		String modeType = null;
		if (paymentModeTypeMap.containsValue("Challan")) {
			modeType = "Challan";
		}

		List<PaymentReconciliationReportBean> paymentreconciliateResultList = new ArrayList<PaymentReconciliationReportBean>();
		try {
			paymentreconciliateResultList = getJdbcTemplate().query(queryString.toString(), new Object[] { reportBean.getUserId().trim() },
					new RowMapper<PaymentReconciliationReportBean>() {

						public PaymentReconciliationReportBean mapRow(ResultSet rs, int rowNum) {
							PaymentReconciliationReportBean paymentReconcilliationResult = new PaymentReconciliationReportBean();
							try {

								paymentReconcilliationResult.setUserID(rs.getString("OUM_USER_ID"));
								paymentReconcilliationResult.setDiscipline(rs.getString("OTM_TEST_NAME"));
								paymentReconcilliationResult.setCandidateName(rs.getString("cand_name"));
								paymentReconcilliationResult.setCategory(rs.getString("OCTM_CATEGORY_CODE"));
								paymentReconcilliationResult.setMobileNo(rs.getString("OUM_MOBILE_NO"));
								paymentReconcilliationResult.setJournalNo(rs.getString("OPD_DD_CHALLAN_RECEIPT_NO"));
								paymentReconcilliationResult.setChallanDate(rs.getString("OPD_DD_DATE"));
								paymentReconcilliationResult.setBranchName(rs.getString("OPD_BRANCH_NAME"));
								paymentReconcilliationResult.setBranchCode(rs.getString("OPD_BRANCH_CODE"));
								paymentReconcilliationResult.setCandidateFees(rs.getBigDecimal("OPD_AMOUNT"));
								paymentReconcilliationResult.setApplicableFeesAmt(rs.getBigDecimal("fees"));
								paymentReconcilliationResult.setBankAmt(rs.getBigDecimal("OBCD_FEES"));

								if (rs.getString("OPD_RECONCILE_FLAG").equals("Y")) {
									paymentReconcilliationResult.setStatus("Reconciled");
								} else if (rs.getString("OPD_RECONCILE_FLAG").equals("N")) {
									paymentReconcilliationResult.setStatus("Not Reconciled");
								}

							} catch (SQLException e) {
								LoggerHome.getLogger(getClass()).fatal(e, e);
							}

							return paymentReconcilliationResult;
						}
					});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (paymentreconciliateResultList != null && !paymentreconciliateResultList.isEmpty()) {
			return paymentreconciliateResultList.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PaymentReconciliationReportBean> getPaymentReconcilliationCandidateWiseDetails(ReportBean reportBean) throws Exception {
		StringBuilder queryString = new StringBuilder();
		final String type = reportBean.getPaymentReconcStatusVal();
		Map<String, Integer> paymentModeTypeMap = ConfigurationConstants.getInstance().getPaymentMasterReverseMap();

		Integer modeType = null;
		if (paymentModeTypeMap != null) {
			modeType = paymentModeTypeMap.get("Challan");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("All")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_ALL_STATUS);
			queryString.append(" and nvl(otm_test_name,'Payment submission pending*') = '" + reportBean.getDisciplineVal() + "')");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("Reconciled")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_RECONCILE_STATUS);
			queryString.append(" AND OTM_TEST_NAME = '" + reportBean.getDisciplineVal() + "'");
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (OPD_TRANSACTION_DATE) >= '" + reportBean.getFromDate() + "'");
			}
			if (ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (OPD_TRANSACTION_DATE) <= '" + reportBean.getToDate() + "'");
			}

			queryString.append(" ) main, oes_bank_challan_details obcd where obcd.OBCD_CHALLAN_NO = OPD_DD_CHALLAN_RECEIPT_NO and obcd.OBCD_RECONCILE_FLAG = 'Y')");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("Not Reconciled")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_NOT_RECONCILE_STATUS);
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (ocd_created_date) >= '" + reportBean.getFromDate() + "'");
			}
			if (ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (ocd_created_date) <= '" + reportBean.getToDate() + "'");
			}

			queryString.append(
					" ) main, oes_bank_challan_details obcd where obcd.OBCD_CHALLAN_NO = OPD_DD_CHALLAN_RECEIPT_NO(+) and obcd.OBCD_RECONCILE_FLAG = 'N' and nvl(OTM_TEST_NAME,'Payment submission pending*')= '"
							+ reportBean.getDisciplineVal() + "')");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("Payment Submitted")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_PAYMENT_SUBMITTED_STATUS);
			queryString.append(" AND OTM_TEST_NAME = '" + reportBean.getDisciplineVal() + "'");
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}
			queryString.append(" ) main, oes_bank_challan_details obcd where obcd.OBCD_CHALLAN_NO(+) = OPD_DD_CHALLAN_RECEIPT_NO");
		}
		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("Payment Not Submitted")) {
			modeType = 1;
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_PAYMENT_NOT_SUBMITTED_STATUS);
			queryString.append(" AND OTM_TEST_NAME = '" + reportBean.getDisciplineVal() + "'");
			queryString.append(" AND opd.opd_user_fk IS NULL");
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}
		}
		List<PaymentReconciliationReportBean> paymentreconciliateResultList = null;
		try {
			paymentreconciliateResultList = getJdbcTemplate().query(queryString.toString(), new Object[] {}, new RowMapper<PaymentReconciliationReportBean>() {
				public PaymentReconciliationReportBean mapRow(ResultSet rs, int rowNum) {
					PaymentReconciliationReportBean paymentReconcilliationResult = new PaymentReconciliationReportBean();
					try {
						paymentReconcilliationResult.setUserID(rs.getString("OUM_USER_ID"));
						paymentReconcilliationResult.setDiscipline(rs.getString("OTM_TEST_NAME"));
						paymentReconcilliationResult.setCandidateName(rs.getString("cand_name"));
						paymentReconcilliationResult.setCategory(rs.getString("OCTM_CATEGORY_CODE"));
						paymentReconcilliationResult.setMobileNo(rs.getString("OUM_MOBILE_NO"));
						paymentReconcilliationResult.setChallanDate(rs.getString("OPD_DD_DATE"));
						paymentReconcilliationResult.setBranchName(rs.getString("OPD_BRANCH_NAME"));
						paymentReconcilliationResult.setBranchCode(rs.getString("OPD_BRANCH_CODE"));
						paymentReconcilliationResult.setCandidateFees(rs.getBigDecimal("OPD_AMOUNT"));
						paymentReconcilliationResult.setApplicableFeesAmt(rs.getBigDecimal("fees"));

						if (rs.getString("OBCD_RECONCILE_FLAG").equals("Y")) {
							paymentReconcilliationResult.setStatus("Reconciled");
						} else if (rs.getString("OBCD_RECONCILE_FLAG").equals("N")) {
							paymentReconcilliationResult.setStatus("Not Reconciled");
						}
						if (rs.getBigDecimal("OBCD_FEES") != null) {
							paymentReconcilliationResult.setBankAmt(rs.getBigDecimal("OBCD_FEES"));
						}

						if (rs.getString("OBCD_CHALLAN_NO") != null) {
							paymentReconcilliationResult.setJournalNo(rs.getString("OBCD_CHALLAN_NO"));
						}

					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e, e);
					}

					return paymentReconcilliationResult;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (paymentreconciliateResultList != null && !paymentreconciliateResultList.isEmpty()) {
			return paymentreconciliateResultList;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PaymentReconciliationReportBean> getPaymentReconcilliationCandidateWiseDetails(ReportBean reportBean, Pagination pagination) throws Exception {
		StringBuilder queryString = new StringBuilder();
		final String type = reportBean.getPaymentReconcStatusVal();
		Map<String, Integer> paymentModeTypeMap = ConfigurationConstants.getInstance().getPaymentMasterReverseMap();

		Integer modeType = null;
		if (paymentModeTypeMap != null) {
			modeType = paymentModeTypeMap.get("Challan");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("All")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_ALL_STATUS);
			queryString.append(" and nvl(otm_test_name,'Payment submission pending*') = '" + reportBean.getDisciplineVal() + "'");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("Reconciled")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_RECONCILE_STATUS);
			queryString.append(" AND OTM_TEST_NAME = '" + reportBean.getDisciplineVal() + "'");
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (OPD_TRANSACTION_DATE) >= '" + reportBean.getFromDate() + "'");
			}
			if (ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (OPD_TRANSACTION_DATE) <= '" + reportBean.getToDate() + "'");
			}

			queryString.append(" ) main, oes_bank_challan_details obcd where obcd.OBCD_CHALLAN_NO = OPD_DD_CHALLAN_RECEIPT_NO and obcd.OBCD_RECONCILE_FLAG = 'Y'");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("Not Reconciled")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_NOT_RECONCILE_STATUS);
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (ocd_created_date) >= '" + reportBean.getFromDate() + "'");
			}
			if (ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append("  AND TRUNC (ocd_created_date) <= '" + reportBean.getToDate() + "'");
			}

			queryString.append(
					" ) main, oes_bank_challan_details obcd where obcd.OBCD_CHALLAN_NO = OPD_DD_CHALLAN_RECEIPT_NO(+) and obcd.OBCD_RECONCILE_FLAG = 'N' and nvl(OTM_TEST_NAME,'Payment submission pending*')= '"
							+ reportBean.getDisciplineVal() + "'");
		}

		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("Payment Submitted")) {
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_PAYMENT_SUBMITTED_STATUS);
			queryString.append(" AND OTM_TEST_NAME = '" + reportBean.getDisciplineVal() + "'");
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}
			queryString.append(" ) main, oes_bank_challan_details obcd where obcd.OBCD_CHALLAN_NO(+) = OPD_DD_CHALLAN_RECEIPT_NO");
		}
		if (reportBean.getPaymentReconcStatusVal() != null && reportBean.getPaymentReconcStatusVal().equals("Payment Not Submitted")) {
			modeType = 1;
			queryString.append(ReportQueries.GET_PAYMENT_RECONCILLIATION_CANDIDATE_DATA_FOR_PAYMENT_NOT_SUBMITTED_STATUS);
			queryString.append(" AND OTM_TEST_NAME = '" + reportBean.getDisciplineVal() + "'");
			queryString.append(" AND opd.opd_user_fk IS NULL");
			if (!ValidatorUtil.isEmpty(reportBean.getFromDate()) && !ValidatorUtil.isEmpty(reportBean.getToDate())) {
				queryString.append(" and trunc(OPD_TRANSACTION_DATE) between '" + reportBean.getFromDate() + "' and '" + reportBean.getToDate() + "'");
			}
		}
		queryString.append(" )where num between ").append(pagination.getStart()).append(" and ").append(pagination.getEnd());
		List<PaymentReconciliationReportBean> paymentreconciliateResultList = null;
		try {
			paymentreconciliateResultList = getJdbcTemplate().query(queryString.toString(), new Object[] {}, new RowMapper<PaymentReconciliationReportBean>() {
				public PaymentReconciliationReportBean mapRow(ResultSet rs, int rowNum) {
					PaymentReconciliationReportBean paymentReconcilliationResult = new PaymentReconciliationReportBean();
					try {
						paymentReconcilliationResult.setUserID(rs.getString("OUM_USER_ID"));
						paymentReconcilliationResult.setDiscipline(rs.getString("OTM_TEST_NAME"));
						paymentReconcilliationResult.setCandidateName(rs.getString("cand_name"));
						paymentReconcilliationResult.setCategory(rs.getString("OCTM_CATEGORY_CODE"));
						paymentReconcilliationResult.setMobileNo(rs.getString("OUM_MOBILE_NO"));
						paymentReconcilliationResult.setChallanDate(rs.getString("OPD_DD_DATE"));
						paymentReconcilliationResult.setBranchName(rs.getString("OPD_BRANCH_NAME"));
						paymentReconcilliationResult.setBranchCode(rs.getString("OPD_BRANCH_CODE"));
						paymentReconcilliationResult.setCandidateFees(rs.getBigDecimal("OPD_AMOUNT"));
						paymentReconcilliationResult.setApplicableFeesAmt(rs.getBigDecimal("fees"));

						if (rs.getString("OBCD_RECONCILE_FLAG").equals("Y")) {
							paymentReconcilliationResult.setStatus("Reconciled");
						} else if (rs.getString("OBCD_RECONCILE_FLAG").equals("N")) {
							paymentReconcilliationResult.setStatus("Not Reconciled");
						}
						if (rs.getBigDecimal("OBCD_FEES") != null) {
							paymentReconcilliationResult.setBankAmt(rs.getBigDecimal("OBCD_FEES"));
						}

						if (rs.getString("OBCD_CHALLAN_NO") != null) {
							paymentReconcilliationResult.setJournalNo(rs.getString("OBCD_CHALLAN_NO"));
						}

					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e, e);
					}

					return paymentReconcilliationResult;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (paymentreconciliateResultList != null && !paymentreconciliateResultList.isEmpty()) {
			return paymentreconciliateResultList;
		} else {
			return null;
		}
	}

	public List<HallTicketBean> getBulkAdmitCard(String test) throws Exception {

		List<HallTicketBean> admitCardList = null;
		try {
			String query = "";
			if (test.equals("Written Test")) {
				query = ReportQueries.GET_BULK_ADMIT_CARD_WRITTEN_TEST + " and ocd_status_id_fk = "
						+ ConfigurationConstants.getInstance().getStatusKey(GenericConstants.SCHEDULED_FOR_WRITTEN_TEST);
			} else if (test.equals("Field Test")) {
				query = ReportQueries.GET_BULK_ADMIT_CARD_FIELD_TEST + " and ocd_status_id_fk = "
						+ ConfigurationConstants.getInstance().getStatusKey(GenericConstants.SCHEDULED_FOR_FIELD_TEST);
			} else if (test.equals("Interview")) {
				query = ReportQueries.GET_BULK_ADMIT_CARD_INTERVIEW + " and ocd_status_id_fk = "
						+ ConfigurationConstants.getInstance().getStatusKey(GenericConstants.SCHEDULED_FOR_INTERVIEW);
			} else {
				query = ReportQueries.GET_BULK_ADMIT_CARD_MEDICAL_TEST + " and ocd_status_id_fk = "
						+ ConfigurationConstants.getInstance().getStatusKey(GenericConstants.SCHEDULED_FOR_MEDICAL_TEST);
			}
			admitCardList = getJdbcTemplate().query(query, new RowMapper<HallTicketBean>() {

				public HallTicketBean mapRow(ResultSet rs, int rowNum) {
					HallTicketBean admitCardList = new HallTicketBean();
					try {

						// admitCardList.setDisciplineType(rs.getString("OTM_TEST_NAME"));
						admitCardList.setUserFK(rs.getLong("oum_user_pk"));
						admitCardList.setUserId(rs.getString("oum_user_id"));
						admitCardList.setReportingTime(rs.getString("oed_reporting_time"));
						admitCardList.setTestCenterAddress(rs.getString("otcm_address"));
						admitCardList.setTestCenterName(rs.getString("otcm_test_centre_name"));
						admitCardList.setTestDate(rs.getString("oed_test_date"));
						admitCardList.setTestStartTime(rs.getString("oed_test_time"));
						admitCardList.setCategory(rs.getString("octm_category_code"));
						admitCardList.setCandidateName(rs.getString("candidate_name"));
						admitCardList.setTestName(rs.getString("otm_test_name"));
						admitCardList.setCandidateAddress(rs.getString("ocd_comm_address"));
					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e, e);
					}

					return admitCardList;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return admitCardList;
	}
}
