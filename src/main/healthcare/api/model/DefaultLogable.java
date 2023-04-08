package main.healthcare.api.model;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
public class DefaultLogable implements Serializable {

	private static final long serialVersionUID = -6415849527788577288L;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	public DefaultLogable() {
		// constructor with no parameters
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setLogging(String user) {
		this.createdBy = user;
		this.creationDate = new Date();
		this.lastUpdatedBy = user;
		this.lastUpdateDate = new Date();
	}

	public void updateLogging(String user) {
		this.lastUpdatedBy = user;
		this.lastUpdateDate = new Date();
	}
}

