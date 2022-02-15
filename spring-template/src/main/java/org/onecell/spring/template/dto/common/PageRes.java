package org.onecell.spring.template.dto.common;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;

import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class PageRes<T> {
    @Setter
    @Getter
    public static class PageInfo{
        int data_size=0;
        long data_total=0;
        int page_num=0;
        int page_total=0;

        public void fromPageImpl(PageImpl pageImpl)
        {
            this.data_size = pageImpl.getNumberOfElements();
            this.data_total = pageImpl.getTotalElements();
            this.page_num = pageImpl.getNumber();
            this.page_total = pageImpl.getTotalPages();
        }

        public static PageInfo  createFromPageImpl(PageImpl pageImpl)
        {
            PageInfo pageInfo = new PageInfo();
            pageInfo.fromPageImpl(pageImpl);
            return pageInfo;
        }
    }

    public PageRes(PageInfo page_info)
    {
        this. page_info = page_info;
    }



    List<T> datas = new LinkedList<>();

    PageInfo page_info=new PageInfo();



}
