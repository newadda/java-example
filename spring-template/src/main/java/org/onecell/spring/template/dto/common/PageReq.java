package org.onecell.spring.template.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Getter
@Setter
@NoArgsConstructor
public class PageReq {

    public int page_num=0;
    public int limit=5000;

    public Pageable toPageable()
    {
        PageRequest of = PageRequest.of(page_num, limit);
        return of;
    }
}
