package org.onecell.spring.template.exception;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ServiceException extends RuntimeException{
    private static final ReloadableResourceBundleMessageSource bundle ;
    private Locale locale = Locale.getDefault();
    private List<Object> arguments = new LinkedList<>();



    static {
        bundle = new ReloadableResourceBundleMessageSource();
        bundle.setBasenames("classpath:/ExceptionMessages");
        //bundle.setUseCodeAsDefaultMessage(true);  // true 이면 메세지 중간에 있는 Code, 예를 들어 @Min()의 min 같은 것을 그대로 표현하게 한다. false일때는 값으로 치환해서 사용한다.
        bundle.setFallbackToSystemLocale(true); // 예) 지정한 locale 언어가 없다면 system 기본 언어 또는 언어 지정없는 파일에서 메세지를 가져온다. 꼭 true하자.
        bundle.setDefaultEncoding("UTF-8");
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    protected void setArguments(Object... args)
    {
        arguments.clear();
        Arrays.stream(args).forEachOrdered(o -> arguments.add(o));

    }

    protected List<Object> getArguments()
    {
       return arguments;
    }

    @Override
    public String getMessage() {
        return bundle.getMessage(getClass().getName(),getArguments().toArray(),locale);
    }
}
