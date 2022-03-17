package db.entity;


import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

// 안씀
//@Entity
//@IdClass(BatchJobExecutionParamsEntity.Params_PK.class)
//@Table(name="BATCH_JOB_EXECUTION_PARAMS")
public class BatchJobExecutionParamsEntity {

        public static class Params_PK implements Serializable{
        @Column(name = "JOB_EXECUTION_ID")
            private Long job_execution_id;
        @Column(name = "KEY_NAME")
            private String key_name;

        public Params_PK() {
        }

        public Params_PK(Long job_execution_id, String key_name) {
            this.job_execution_id = job_execution_id;
            this.key_name = key_name;
        }
        @Override
        public boolean equals(Object o) {
            return ((o instanceof Params_PK) && job_execution_id == ((Params_PK)o).getJob_execution_id() && key_name == ((Params_PK) o).getKey_name());
        }
        @Override
        public int hashCode() {
            return (int)(job_execution_id ^ key_name.hashCode());
        }

        public Long getJob_execution_id() {
            return job_execution_id;
        }

        public void setJob_execution_id(Long job_execution_id) {
            this.job_execution_id = job_execution_id;
        }

        public String getKey_name() {
            return key_name;
        }

        public void setKey_name(String key_name) {
            this.key_name = key_name;
        }

    }

    @Id
    @Column(name = "JOB_EXECUTION_ID")
    private Long job_execution_id;

    @Column(name = "TYPE_CD")
    private String type_cd;

    @Id
    @Column(name = "KEY_NAME")
    private String key_name;


    @Column(name = "STRING_VAL")
    private String string_val;


}
