package com.nseit.generic.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRelationBean {
	private Long relationPk;
	private Long userFk;
	private Long relationFk;
	private String firstName;
	private String middleName;
	private String lastName;
	private String relationDesc;
	private String mandatory;
	private String activeStatus;
}
