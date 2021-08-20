package org.test.onecell.restdoc;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class Test01 extends Init{

    //// 배열에 대해
    @Test
    public void 테스트01() throws Exception {
        mockMvc.perform(
                get("/test/test01")
                        .contentType(MediaType.APPLICATION_JSON)
        )  .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        responseFields(
                                fieldWithPath("datas").type("array").description("")
                                ,fieldWithPath("datas[]").type("Object").description("").optional()
                        ).andWithPrefix("datas[]."
                                , fieldWithPath("a").type("String").description("테스트")
                                , fieldWithPath("b").type("String").description("테스트")
                                , fieldWithPath("sub").type("Object").description("테스트").optional()
                                , fieldWithPath("sub.c").type("String").description("테스트").optional()

                )));

    }

    @Test
    public void 테스트02() throws Exception {
        mockMvc.perform(
                get("/test/test02")
                        .contentType(MediaType.APPLICATION_JSON)
        )  .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(

                        responseFields(
                                fieldWithPath("datas").type("array").description("").optional()
                        ).andWithPrefix("datas[]."
                                , fieldWithPath("a").type("String").description("테스트")
                                , fieldWithPath("b").type("String").description("테스트")
                                , fieldWithPath("sub").type("Object").description("테스트").optional()
                                , fieldWithPath("sub.c").type("String").description("테스트").optional()

                        )));

    }


    @Test
    public void 테스트03() throws Exception {
        mockMvc.perform(
                get("/test/test03")
                        .contentType(MediaType.APPLICATION_JSON)
        )  .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        responseFields(
                                fieldWithPath("datas").type("array").description("").optional()
                        ).andWithPrefix("datas[]."
                                , fieldWithPath("a").type("String").description("테스트")
                                , fieldWithPath("b").type("String").description("테스트")
                                , fieldWithPath("sub").type("Object").description("테스트").optional()
                                , fieldWithPath("sub.c").type("String").description("테스트").optional()

                        )));

    }


    @Test
    public void 테스트04() throws Exception {
        mockMvc.perform(
                get("/test/test04")
                        .contentType(MediaType.APPLICATION_JSON)
        )  .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        responseFields(
                                fieldWithPath("datas").type("array").description("").optional()
                        ).andWithPrefix("datas[]."
                                , fieldWithPath("a").type("String").description("테스트")
                                , fieldWithPath("b").type("String").description("테스트")
                                , fieldWithPath("sub").type("Object").description("테스트").optional()
                                , fieldWithPath("sub.c").type("String").description("테스트").optional()

                        )));

    }

    @Test
    public void 테스트05() throws Exception {

        mockMvc.perform(
                get("/test/test04")
                        .contentType(MediaType.APPLICATION_JSON)
        )  .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        // datas[] 필드 하위는 설명이 생략되고 testdto 란 이름이 포함된 .adoc 파일에 따라 하위 필드의 설명이 만들어 진다.
                        responseFields(beneathPath("datas[]").withSubsectionId("testdto")
                                , fieldWithPath("a").type("String").description("테스트")
                                , fieldWithPath("b").type("String").description("테스트")
                                , fieldWithPath("sub").type("Object").description("테스트").optional()
                                , fieldWithPath("sub.c").type("String").description("테스트").optional()
                        ))
                );

    }


    @Test
    public void 테스트06() throws Exception {

        mockMvc.perform(
                get("/test/test04")
                        .contentType(MediaType.APPLICATION_JSON)
        )  .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        responseFields(
                                // subsectionWithPath 는 하위를 확인하지 않고 넘어간다.
                                // 이것은 하위에 대해 설명하고 싶지 않거나, 하위가 가변적인 경우 사용하기에 적합하다.
                                // 하위에 대한 설명을 작성하면 안된다.
                                subsectionWithPath("datas").type("array").description("")
                        )));
    }


}
