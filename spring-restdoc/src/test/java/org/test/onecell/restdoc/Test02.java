package org.test.onecell.restdoc;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.test.onecell.descriptor.DescriptorUtil.constraint;

public class Test02  extends Init{

    // link 테스트
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
                                , fieldWithPath("a").type("String").description("<<날짜형식,링크~>>")
                                , fieldWithPath("b").type("String").description("테스트")
                                , fieldWithPath("sub").type("Object").description("테스트").optional()
                                , fieldWithPath("sub.c").type("String").description("테스트").optional()

                        )));

    }



    // 추가 속성 constraint
    @Test
    public void 테스트02() throws Exception {
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
                                , fieldWithPath("a").type("String").description("<<날짜형식,링크~>>")
                                , fieldWithPath("b").type("String").description("테스트").attributes(constraint("제약조건이다~"))
                                , fieldWithPath("sub").type("Object").description("테스트").optional()
                                , fieldWithPath("sub.c").type("String").description("테스트").optional()

                        )));

    }
}
