package db.entity;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name="BATCH_JOB_EXECUTION")
public class BatchJobExecutionEntity {


    @Id
    @Column(name = "JOB_EXECUTION_ID")
    private Long job_execution_id;

    @Column(name = "JOB_INSTANCE_ID")
    private Long job_instance_id;

    @Column(name = "CREATE_TIME")
    private ZonedDateTime create_time;

    @Column(name = "START_TIME")
    private ZonedDateTime start_time;

    @Column(name = "END_TIME")
    private ZonedDateTime end_time;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "LAST_UPDATED")
    private ZonedDateTime last_updated;

    @Fetch(FetchMode.JOIN)
    @OneToOne
    @JoinColumn(name="JOB_INSTANCE_ID",referencedColumnName = "JOB_INSTANCE_ID" ,insertable = false,updatable = false)
    private BatchJobInstanceEntity batchJobInstance;


    @Formula("(SELECT p.STRING_VAL FROM BATCH_JOB_EXECUTION_PARAMS p WHERE p.JOB_EXECUTION_ID=JOB_EXECUTION_ID AND p.TYPE_CD='STRING' AND p.KEY_NAME='description')")
    private String description;

    public String getDescription() {
        return description;
    }

    public Long getJob_execution_id() {
        return job_execution_id;
    }

    public Long getJob_instance_id() {
        return job_instance_id;
    }

    public ZonedDateTime getCreate_time() {
        return create_time;
    }

    public ZonedDateTime getStart_time() {
        return start_time;
    }

    public ZonedDateTime getEnd_time() {
        return end_time;
    }

    public String getStatus() {
        return status;
    }

    public ZonedDateTime getLast_updated() {
        return last_updated;
    }

    public BatchJobInstanceEntity getBatchJobInstance() {
        return batchJobInstance;
    }
}
