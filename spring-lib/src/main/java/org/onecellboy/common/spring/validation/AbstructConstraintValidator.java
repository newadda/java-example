package org.onecellboy.common.spring.validation;

import javax.validation.ConstraintValidator;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;

public abstract class AbstructConstraintValidator<A extends Annotation, T> implements ConstraintValidator<A , T> {
    static {

    }

}
