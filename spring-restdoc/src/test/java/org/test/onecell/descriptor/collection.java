package org.test.onecell.descriptor;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public final class collection {
    public  enum Type{
        String,
        Integer,
        Long,
        Double,
        Object

    }
    public static class Data{
        public static FieldDescriptor datas()
        {

            FieldDescriptor description = subsectionWithPath("datas").description("목록").optional();
            return description;
        }
        public static FieldDescriptor data()
        {

            FieldDescriptor description = subsectionWithPath("data").type("Object").description("목록").optional();
            return description;
        }
    }


    public static class Page{
        public static List<ParameterDescriptor> reqParam()
        {
            List<ParameterDescriptor> list = new LinkedList<>();
            list.add(parameterWithName("page_num").description("페이지 번호").optional());
            list.add(parameterWithName("limit")   .description("반환 최대 개수").optional());
            return list;
        }


        public static List<FieldDescriptor> resField()
        {
            List<FieldDescriptor> list = new LinkedList<>();
            list.add(fieldWithPath("data_size") .type(Type.Integer)  .description("반환 받은 요소 개수"));
            list.add(fieldWithPath("data_total") .type(Type.Long)  .description("총 요소 개수"));
            list.add(fieldWithPath("page_num") .type(Type.Integer)  .description("페이지 번호(0~)"));
            list.add(fieldWithPath("page_total") .type(Type.Integer)  .description("총 페이지 개수"));
            return list;
        }
    }

    public static class UserDto{
        public static List<FieldDescriptor> resField()
        {
            List<FieldDescriptor> list = new LinkedList<>();
            list.add(fieldWithPath("name") .type(Type.String)  .description("이름"));
            list.add(fieldWithPath("phone_number")  .type(Type.String)  .description("전화번호"));
            list.add(fieldWithPath("etc")   .type(Type.String)  .description("설명"));
            return list;
        }
    }


}
