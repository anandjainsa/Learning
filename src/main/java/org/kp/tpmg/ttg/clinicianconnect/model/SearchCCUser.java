package org.kp.tpmg.ttg.clinicianconnect.model;

import java.io.Serializable;

public class SearchCCUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nuid;
	private String reason;
	private String loggedInUser;

	public String getNuid() {
		return nuid;
	}

	public void setNuid(String nuid) {
		this.nuid = nuid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(String loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	@Override
	public String toString() {
		return "SearchCCUser [nuid=" + nuid + ", reason=" + reason + ", loggedInUser=" + loggedInUser + "]";
	}
}
