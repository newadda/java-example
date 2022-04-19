package org.onecell.spring.template.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BATCH_JOB_INSTANCE")
public class BatchJobInstanceEntity {

    @Id
    @Column(name = "JOB_INSTANCE_ID")
    Long job_instance_id;

    @Column(name = "JOB_NAME")
    String job_name;

    public Long getJob_instance_id() {
        return job_instance_id;
    }

    public void setJob_instance_id(Long job_instance_id) {
        this.job_instance_id = job_instance_id;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }
}
