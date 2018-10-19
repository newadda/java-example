package org.onecellboy.common.spring.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneConstraintValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "{org.onecellboy.common.spring.validation.phone.message}"; // 기본적으로는 ValidationMessages.properties 에서 properties를 검색한다.

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
