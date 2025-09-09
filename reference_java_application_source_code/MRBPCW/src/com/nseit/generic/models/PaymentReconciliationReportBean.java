/*
 * ----------------------------------------------------
 * © 2012 NSEIT Limited
 * ----------------------------------------------------
 * Project :        Online Exams
 * File    :        PaymentReconciliationReportBean.java
 * ----------------------------------------------------
 * When             Who                Description
 * Dec 26, 2012			vijaya, Online Exams       Initial Version.
 * ----------------------------------------------------
 */

package com.nseit.generic.models;

import java.math.BigDecimal;

public class PaymentReconciliationReportBean
{

    private String discipline;
    
    private Integer noOfCandidates;
    
    private BigDecimal applicableFeesAmt;
    
    private BigDecimal totalApplicableFeesAmt;
    
    private String userID;
    
    private String candidateName;
    
    private String category;
    
    private String mobileNo;
    
    private String journalNo;
    
    private String challanDate;
    
    private String branchName;
    
    private String branchCode;
    
    private BigDecimal candidateFees;
    
    private BigDecimal candidateApplicableFees;
    
    private BigDecimal bankAmt;
    
    private String status;

    public String getDiscipline()
    {
        return discipline;
    }

    public void setDiscipline(String discipline)
    {
        this.discipline = discipline;
    }

    public Integer getNoOfCandidates()
    {
        return noOfCandidates;
    }

    public void setNoOfCandidates(Integer noOfCandidates)
    {
        this.noOfCandidates = noOfCandidates;
    }

    public BigDecimal getApplicableFeesAmt()
    {
        return applicableFeesAmt;
    }

    public void setApplicableFeesAmt(BigDecimal applicableFeesAmt)
    {
        this.applicableFeesAmt = applicableFeesAmt;
    }

    public BigDecimal getTotalApplicableFeesAmt()
    {
        return totalApplicableFeesAmt;
    }

    public void setTotalApplicableFeesAmt(BigDecimal totalApplicableFeesAmt)
    {
        this.totalApplicableFeesAmt = totalApplicableFeesAmt;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public String getCandidateName()
    {
        return candidateName;
    }

    public void setCandidateName(String candidateName)
    {
        this.candidateName = candidateName;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getMobileNo()
    {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }

    public String getJournalNo()
    {
        return journalNo;
    }

    public void setJournalNo(String journalNo)
    {
        this.journalNo = journalNo;
    }

    public String getChallanDate()
    {
        return challanDate;
    }

    public void setChallanDate(String challanDate)
    {
        this.challanDate = challanDate;
    }

    public String getBranchName()
    {
        return branchName;
    }

    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    public String getBranchCode()
    {
        return branchCode;
    }

    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }

    public BigDecimal getCandidateFees()
    {
        return candidateFees;
    }

    public void setCandidateFees(BigDecimal candidateFees)
    {
        this.candidateFees = candidateFees;
    }

    public BigDecimal getCandidateApplicableFees()
    {
        return candidateApplicableFees;
    }

    public void setCandidateApplicableFees(BigDecimal candidateApplicableFees)
    {
        this.candidateApplicableFees = candidateApplicableFees;
    }

    public BigDecimal getBankAmt()
    {
        return bankAmt;
    }

    public void setBankAmt(BigDecimal bankAmt)
    {
        this.bankAmt = bankAmt;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
    
}
