package tasklet;


import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;

public abstract class TaskletEx<T> implements Tasklet {
    public static final String PARAM_KEY = "parameter_key";

    public T getParameter(ChunkContext chunkContext)
    {
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
        T o = (T)executionContext.get(PARAM_KEY);
        return o;
    }

    public <O> O getIfNotExistDefault(ChunkContext chunkContext,String key, O default_value )
    {
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
        boolean b = executionContext.containsKey(key);
        if(b==false)
        {
            executionContext.put(key,default_value);
        }
        O o = (O)executionContext.get(key);
        return o;
    }

    public <O> void putValue(ChunkContext chunkContext,String key, O value )
    {
        ExecutionContext executionContext = chunkContext.getStepContext().getStepExecution().getExecutionContext();
        executionContext.put(key,value);
    }
}
