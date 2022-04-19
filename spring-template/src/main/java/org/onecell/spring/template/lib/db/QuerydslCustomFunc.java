package org.onecell.spring.template.lib.db;

import com.querydsl.core.types.dsl.DateTimeTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;

import java.time.LocalDateTime;

public class QuerydslCustomFunc {

    public static DateTimeTemplate<LocalDateTime> to_timestamp_seconds(StringExpression str)
    {

        DateTimeTemplate<LocalDateTime> template = Expressions.dateTimeTemplate(LocalDateTime.class, "TO_TIMESTAMP_SECOND({0})", str);
        return template;
    }

    public static DateTimeTemplate<LocalDateTime> to_timestamp_minute(StringExpression str)
    {

        DateTimeTemplate<LocalDateTime> template = Expressions.dateTimeTemplate(LocalDateTime.class, "TO_TIMESTAMP_MINUTE({0})", str);
        return template;
    }

    public static DateTimeTemplate<LocalDateTime> to_timestamp_hour(StringExpression str)
    {

        DateTimeTemplate<LocalDateTime> template = Expressions.dateTimeTemplate(LocalDateTime.class, "TO_TIMESTAMP_HOUR({0})", str);
        return template;
    }

}
