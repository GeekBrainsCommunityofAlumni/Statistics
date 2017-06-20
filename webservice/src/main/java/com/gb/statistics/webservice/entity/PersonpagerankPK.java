package com.gb.statistics.webservice.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PersonpagerankPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private int personid;

	@Column(insertable=false, updatable=false, unique=true, nullable=false)
	private int pageid;

	public PersonpagerankPK() {
	}
	public int getPersonid() {
		return this.personid;
	}
	public void setPersonid(int personid) {
		this.personid = personid;
	}
	public int getPageid() {
		return this.pageid;
	}
	public void setPageid(int pageid) {
		this.pageid = pageid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PersonpagerankPK)) {
			return false;
		}
		PersonpagerankPK castOther = (PersonpagerankPK)other;
		return 
			(this.personid == castOther.personid)
			&& (this.pageid == castOther.pageid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.personid;
		hash = hash * prime + this.pageid;
		
		return hash;
	}
}