package tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.Map;

public class MyTasklet implements Tasklet {

    /* StepContribution contribution: 해당 step 의 총괄 정보(read/writed count등) 을 담당한다.
       step 을 파티셔닝 했을때 파티셔닝 된 step들을 종합 관리하는 총괄 ste
       p 의 상태이다.

       ChunkContext chunkContext : step 의 자신만의 상태이다. 파티셔닝 된 step 의 경우 파티셔닝 step 그 자체의 상태이다.
     */

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // job run 시 jobparameter 들이다. 이값은 바꾸는 것이 안된다. 읽기로만 사용 가능하다.
        // BATCH_JOB_EXECUTION_PARAMS 테이블에 저장된 값이다.
        Map<String, Object> jobParameters = chunkContext.getStepContext().getJobParameters();

        // BATCH_JOB_EXECUTION_CONTEXT 테이블에 저장할 수 있고 읽을 수도 있다.
        // 이곳에 넣은 값은 execute()함수 끝날시 db에 갱신한다. 저장과 읽을수 있기 때문에 step의 파라미터나 지속적으로 변경되는 값으로 사용하면 된다.
        // tasklet이 끝나는 시점을 알기 위해서도 사용 가능하다.
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();



        return null;
    }
}
