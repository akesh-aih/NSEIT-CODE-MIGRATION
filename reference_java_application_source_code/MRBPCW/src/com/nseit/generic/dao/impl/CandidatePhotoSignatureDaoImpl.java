package com.nseit.generic.dao.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import org.springframework.jdbc.core.JdbcTemplate;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.CandidatePhotoSignatureDao;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;

public class CandidatePhotoSignatureDaoImpl extends BaseDAO implements CandidatePhotoSignatureDao {
	
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	@Override
	public boolean checkIfSignatureUploaded(Users users) throws Exception {
		int imageCount = 0;

		String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
		File documentFolder = new File(DocumentPath + File.separator + users.getUsername() + File.separator + users.getUsername() + "_sign.jpg");
		if (documentFolder != null && !documentFolder.equals("") && documentFolder.exists()) {
			imageCount = 1;
		}
		if (documentFolder != null) {
			BufferedImage originalImage = null;
			try {
				originalImage = ImageIO.read(documentFolder);
			} catch (Exception ex) {
				logger.info("IIOException in Upload Photo :" + ex.getMessage());
			}
			if (originalImage == null) {
				imageCount = 0;
			}
		}
		return imageCount > 0;
	}

	public boolean checkIfImageUploaded(Users users) throws Exception {

		int imageCount = 0;
		String DocumentPath = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.OES_DOCUMNETS_AND_PHOTO_PATH);
		File documentFolder = new File(DocumentPath + File.separator + users.getUsername() + File.separator + users.getUsername() + "_photo.jpg");
		if (documentFolder != null && !documentFolder.equals("") && documentFolder.exists()) {
			imageCount = 1;
		}
		if (documentFolder != null) {
			BufferedImage originalImage = null;
			try {
				originalImage = ImageIO.read(documentFolder);
			} catch (Exception ex) {
				logger.info("IIOException in Upload Photo :" + ex.getMessage());
			}

			if (originalImage == null) {
				imageCount = 0;
			}
		}

		return imageCount > 0;
	}

	public int insertCandidateSignature(CandidateBean candidateBean, final Users loggedInUser) throws Exception {
		int updateAndInsertCandidate = 0;
		int i = writeJdbcTemplate.queryForObject(CandidateQueries.CHECK_IMAGE_EXISTS_FOR_USERFK, new Object[] { loggedInUser.getUserId() }, Integer.class);
		if (i == 0) {
			updateAndInsertCandidate = writeJdbcTemplate.update(CandidateQueries.INSERT_CANDIDATE_SIGNATURE,
					new Object[] { loggedInUser.getUserId(), null, loggedInUser.getUsername(), candidateBean.getAttachmentSignatureFileName() });
		} else {
			updateAndInsertCandidate = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_SIGNATURE,
					new Object[] { null, loggedInUser.getUsername(), candidateBean.getAttachmentSignatureFileName(), loggedInUser.getUserId() });
		}
		return updateAndInsertCandidate;
	}

	public int insertCandidateImages(CandidateBean candidateBean, final Users loggedInUser) throws Exception {
		int updateAndInsertCandidate = 0;
		int i = writeJdbcTemplate.queryForObject(CandidateQueries.CHECK_IMAGE_EXISTS_FOR_USERFK, new Object[] { loggedInUser.getUserId() }, Integer.class);
		if (i == 0) {
			updateAndInsertCandidate = writeJdbcTemplate.update(CandidateQueries.INSERT_CANDIDATE_IMAGES,
					new Object[] { loggedInUser.getUserId(), null, loggedInUser.getUsername(), candidateBean.getAttachmentPhotoFileName() });
		} else {
			updateAndInsertCandidate = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_IMAGES,
					new Object[] { null, loggedInUser.getUsername(), candidateBean.getAttachmentPhotoFileName(), loggedInUser.getUserId() });
		}
		return updateAndInsertCandidate;
	}

	@Override
	public int updateCandidatePhotoStatus(CandidateBean candidateBean, Users loggedInUser, String flag) throws Exception {
		int updateUploadStatus = 0;
		try {
			if (flag.equals("Photo")) {
				updateUploadStatus = writeJdbcTemplate.update(CandidateQueries.UPDATE_UPLOAD_IMAGE_STATUS, new Object[] { loggedInUser.getUsername() });
			}
			if (flag.equals("Sign")) {
				updateUploadStatus = writeJdbcTemplate.update(CandidateQueries.UPDATE_UPLOAD_SIGN_STATUS, new Object[] { loggedInUser.getUsername() });
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateUploadStatus;
	}

}