package org.test.onecell.restdoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.onecell.TestServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

/*
* Test 클래스에 아래의 설정을 매번 하게 되면 각 Test 클래스마다 Spring Context를 매번 구성하게 되어 테스트 속도가 느려진다.
*
* 이것을 상속 받아야 매번 Spring context 를 띄우지 않는다. Spring Context 구성은 딱 한번하게 된다.
*
*
* */
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest(classes = TestServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties") // 테스트를 위한 properties 파일 위치를 지정한다.  /src/test/resources 에서 찾는다.
@WithMockUser( roles = { "ADMIN","VISITOR"}) // Security 프로젝트의 테스트 권한 설정
public  class Init {
    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler document;

    protected ObjectMapper mapper = new ObjectMapper();

    public Init() {
        mapper.registerModule(new JavaTimeModule());
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
        simpleModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ISO_DATE));
        simpleModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        simpleModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        mapper.registerModule(simpleModule);
    }

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())

        );
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .operationPreprocessors().withRequestDefaults(
                                modifyUris().host("test.com").removePort(),prettyPrint()
                        ).withResponseDefaults(prettyPrint())
                )
                .alwaysDo(MockMvcResultHandlers.print())
                .alwaysDo(document)
                .addFilters(new CharacterEncodingFilter("UTF-8",true)) // 한글 깨짐 해결
                .build();
    }

}
