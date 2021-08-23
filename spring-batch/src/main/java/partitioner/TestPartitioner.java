package partitioner;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class TestPartitioner implements Partitioner {

    /// gridSize는 무시해도 되지만 사용자가 원하는 값이므로 왠만하면 이 값을 사용해서 파티션이 개수를 정하는 것이 좋다.
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {

        Map<String, ExecutionContext> result = new HashMap<>();
        ExecutionContext value = new ExecutionContext();
        value.putInt("minValue", 0);
        value.putInt("maxValue", 200);
        /// 첫 파라미터는 파티셔닝 step의 이름이다.
        // 두번째 파라미터는 해당 step의 실행 context 이고 여기에 step이 행할 작업의 파라미터를 전달하면
        // Tasklet 의 ChunkContext chunkContext 를 통해 접근가능하다. 그러면 해당 step 은 자신이 행할 작업의 범위를 알 수 있다.
        result.put("partition" + 1, value);



        value = new ExecutionContext();
        value.putInt("minValue", 0);
        value.putInt("maxValue", 200);
        result.put("partition" +2, value);

        // result 에 만들어진 파티셔닝 갯수의 파티셔닝 step이 생성된다.
        return result;


    }
}
