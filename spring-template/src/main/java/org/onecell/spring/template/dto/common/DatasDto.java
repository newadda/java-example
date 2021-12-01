package org.onecell.spring.template.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DatasDto <R> {
    List<R> datas = new LinkedList<>();
}
