package com.icosmic

public interface IEntity {
    public EntityStatus getStatus()
    public void setStatus(EntityStatus status)

    public Date getDateCreated()
    public void setDateCreated(Date dateCreated)

    public Date getLastUpdated()
    public void setLastUpdated(Date lastUpdated)

    public String getCreatedBy()
    public void setCreatedBy(String createdBy)

    public String getUpdatedBy()
    public void setUpdatedBy(String updatedBy)
}
