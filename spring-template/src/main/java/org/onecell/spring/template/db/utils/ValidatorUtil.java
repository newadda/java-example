package org.onecell.spring.template.db.utils;



import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

public final class ValidatorUtil {
    private <T> void  validateOrException(Validator validator,T dto)
    {
        Set<ConstraintViolation<T>> validate = validator.validate(dto);
        if(validate.isEmpty()!=true)
        {
            throw new ConstraintViolationException(validate);
        }
    }



}
