package org.test.onecell.descriptor;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.applyPathPrefix;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class PageDesc {
    public static final String PAGE_NUM = "page_num";
    public static final String LIMIT = "limit";

    public static List<ParameterDescriptor> pageReqParameters()
    {
        return new LinkedList(){
            {
                add(parameterWithName(PAGE_NUM).description("페이지 번호"));
                add(parameterWithName(LIMIT).description("페이지당 아이템 최대갯수"));
            }
        };
    }

    public static List<FieldDescriptor> pageResFields()
    {
        return new LinkedList<FieldDescriptor>()
        {
            {
             add( fieldWithPath("page_info").type(Type.OBJECT).description("페이지 정보"));
             addAll(applyPathPrefix("page_info.",pageInfoFields()));
            }
        }        ;

    }


    public static List<FieldDescriptor> pageInfoFields()
    {
        return new LinkedList(){
            {
                add(fieldWithPath("data_size") .type(Type.NUMBER)  .description("반환된 데이터 갯수"));
                add(fieldWithPath("data_total") .type(Type.NUMBER)  .description("전체 데이터 갯수"));
                add(fieldWithPath("page_num") .type(Type.NUMBER)  .description("반환된 페이지 번호"));
                add(fieldWithPath("page_total") .type(Type.NUMBER)  .description("전체 페이지 갯수"));
            }
        };
    }

    /**
     * datas:[
     *  {
     *      "id":1,
     *      "username":""
     *  }
     * ]
     *
     * 이런 배열내의 Object 를 표현하기에 적합하다.
     *
     * @param data
     * @return
     */
    public static List<FieldDescriptor> datasFields(List<FieldDescriptor> data)
    {
        return new LinkedList<FieldDescriptor>()
        {
            {
                add( fieldWithPath("datas").type(Type.ARRAY).description(""));
                addAll(applyPathPrefix("datas[].",data));
            }
        }        ;
    }

}
