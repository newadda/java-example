package org.onecell.spring.template.dto.error;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRootDto {


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorDto {
        String code;
        String user_message;
    }

    ErrorDto error = new ErrorDto();


}
