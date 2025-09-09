package com.nseit.generic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademicDetailsBean
{
	private String sscExaminationName;
	private int sscExamination;
	private String sscYearOfPassing;
	private String sscUniversity;
	private short sscObtainedMarks;
	private short sscMaxMarks;
	private float sscPercentage;
	private int sscSPI;
	private int sscCPI;
	
	private String hscExaminationName;
	private int hscExamination;
	private String hscYearOfPassing;
	private String hscUniversity;
	private short hscObtainedMarks;
	private short hscMaxMarks;
	private float hscPercentage;
	private int hscSPI;
	private int hscCPI;
	
	private String beExaminationName;
	private int beExamination;
	private String beYearOfPassing;
	private String beUniversity;
	private short beObtainedMarks;
	private short beMaxMarks;
	private float bePercentage;
	private Integer beSPI;
	private Integer beCPI;
	
	private String baseSpiCpiInfo;
	
	// for cat
	private Long catSection1ObtdMrks;
	private Long catSection1MaxMrks;
	private Float catSection1Perc;
	
	private Long catSection2ObtdMrks;
	private Long catSection2MaxMrks;
	private Float catSection2Perc;
	
	private Long catSection3ObtdMrks;
	private Long catSection3MaxMrks;
	private Float catSection3Perc;
	
	private String catYear;
}

