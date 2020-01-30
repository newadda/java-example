package util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.batch.item.ExecutionContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TaskletParamTransformer {
    ObjectMapper objectMapper;
    public TaskletParamTransformer()
    {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
        simpleModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ISO_DATE));
        simpleModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        simpleModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        simpleModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        objectMapper.registerModule(simpleModule);
    }

    /**
     *   ExecutionContext executionContext = jobExecution.getExecutionContext();
     *   의 excutionContext 으로 객체를 Map 화 시키고 그리고 넣는다.
     *
     * @param context
     * @param params
     */
    public void toExecutionContext(ExecutionContext context, Object params)
    {
        Map<String, Object> map = objectMapper.convertValue(params, Map.class);

        for(String key : map.keySet())
        {
            Object o = map.get(key);
            context.put(key,o);

        }
    }

    /**
     * ExecutionContext 에서 파라미터를 객체화 시킨다.
     * @param clazz
     * @param context
     * @param <T>
     * @return
     */
    public <T> T  fromExecutionContext(Class<T>clazz,ExecutionContext context)
    {
        Set<Map.Entry<String, Object>> entries = context.entrySet();

        Map<String , Object> map = new HashMap<>();
        for(Map.Entry<String,Object> entry : entries)
        {
            map.put(entry.getKey(),entry.getValue());
        }

        return objectMapper.convertValue(map,clazz);
    }



}
