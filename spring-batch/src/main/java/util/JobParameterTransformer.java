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
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JobParameterTransformer {
    public static final ObjectMapper OBJECT_MAPPER;
    static{
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
        simpleModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ISO_DATE));
        simpleModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        simpleModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        simpleModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        OBJECT_MAPPER.registerModule(simpleModule);
    }

    /**
     * 객체의 멤버변수 이름으로 MAP 을 만들다.
     * @param from
     * @return
     */
    public static JobParameters toJobParameters(Object from){

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        Map<String, Object> map = OBJECT_MAPPER.convertValue(from, Map.class);
        for(String key : map.keySet())
        {
            Object o = map.get(key);
            if(o instanceof Long)
            {
                jobParametersBuilder.addLong(key,(Long)o);
            }else if(o instanceof Integer)
            {
                Integer value = (Integer)o;
                jobParametersBuilder.addLong(key,Long.valueOf(value));
            }else if(o instanceof String)
            {
                jobParametersBuilder.addString(key,(String)o);
            }else if(o instanceof Double)
            {
                jobParametersBuilder.addDouble(key,(Double)o);
            }else if(o instanceof Float)
            {
                BigDecimal bigDecimal = new BigDecimal((Float) o);
                jobParametersBuilder.addDouble(key,bigDecimal.doubleValue());
            }

        }
        return jobParametersBuilder.toJobParameters();
    }



    public static <T> T fromMap(Map<String,Object> map,Class<T> tClass){
        return OBJECT_MAPPER.convertValue(map, tClass);
    }


    /**
     * JobParameters 의 데이터를 클래스로 만든다. Jobparameter의 key는 T 클래스의 멤버변수 이름이다.
     * @param parameters
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T fromMap(JobParameters parameters,Class<T> tClass)
    {
        Map<String,Object> map = new HashMap<>();


        Map<String, JobParameter> parameters1 = parameters.getParameters();
        Set<String> kets = parameters1.keySet();
        for(String key : kets)
        {
            Object value = parameters1.get(key).getValue();
            map.put(key,value);
        }
       return fromMap(map,tClass);
    }
}
