package restdocs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.onecell.spring.template.WebApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestApi2  extends InitRestDocTest{

    @Test
    public void 로그인() throws Exception {
      /*
        AuthController.LoginDto loginDto = new AuthController.LoginDto();
        loginDto.setUsername("admin");
        loginDto.setPassword("admin");
        String s = mapper.writeValueAsString(loginDto);

        mockMvc.perform(
                post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                        requestFields(
                                fieldWithPath("username").type("String").description("아이디"),
                                fieldWithPath("password").type("String").description("비밀번호")
                        )

                ))
                .andDo(document.document(
                        responseFields(
                                fieldWithPath("jwt_token").type("String").description("JWT 토큰"),
                                fieldWithPath("roles[]").type("[String]").description("역할(권한) 목록")
                        )
                ));

                */
    }
}
