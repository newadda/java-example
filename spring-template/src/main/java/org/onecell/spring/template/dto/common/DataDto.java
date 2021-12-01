package org.onecell.spring.template.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DataDto <R>{
    R data;

    public DataDto(R data) {
        this.data = data;
    }
}
