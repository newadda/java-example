package restdocs.lib;

import lombok.Getter;
import lombok.Setter;
import org.springframework.restdocs.payload.FieldDescriptor;


import javax.validation.Constraint;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public class TransDtoToDescription {
    @Getter
    @Setter
    public static class Description {
        private String path;
        private Object type;
        private String description="";
        private List<String> constraints;

        public FieldDescriptor createField() {
            return fieldWithPath(path).type(type).description(description).attributes(key("constraint").value(String.join("\n", constraints)));
        }

    }

    public static List<FieldDescriptor> createFieldList(Map<String, Description> map) {
        List<FieldDescriptor> list = new LinkedList<>();
        map.forEach((s, description) ->
                list.add(description.createField())
        );
        return list;
    }

    public static Map<String, Description> trans(Class clazz) {
        try {
            return _trans( clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Description> _trans(Class clazz) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, Description> map = new LinkedHashMap<>();

        for (Field field : clazz.getDeclaredFields()) {
            Description item = new Description();
            field.setAccessible(true);
            String name = field.getName();

            item.setPath(name);
            Class<?> type = field.getType();
            String typeName = type.getSimpleName();

            item.setType(typeName);

            List<String> constraintList = new LinkedList<>();
            for (Annotation anno : field.getDeclaredAnnotations()) {
                Annotation[] annotations = anno.annotationType().getDeclaredAnnotations();

                Stream<Annotation> annotationStream = Arrays.stream(annotations).filter(
                        annotation -> annotation.annotationType().isAssignableFrom(Constraint.class));
                boolean present = annotationStream.findFirst().isPresent();
                if (present == true) {
                    constraintList.add(getConstraintString(anno));
                } else if (anno.annotationType().isAssignableFrom(Description.class)) {

                    item.setDescription(anno.annotationType().getDeclaredMethod("value").invoke(anno).toString());
                }
            }
            item.setConstraints(constraintList);
            map.put(item.getPath(), item);
        }

        return map;

    }




    private static String getConstraintString(Annotation annotation) {
        StringBuilder str = new StringBuilder();
        List<String> exclude = new LinkedList<String>() {{
            add("groups");
            add("payload");
            add("message");
        }};


        Method[] declaredMethods = annotation.annotationType().getDeclaredMethods();
        List<Method> collect = Arrays.stream(declaredMethods).filter(method ->
                !(exclude.stream().filter(item -> item.equals(method.getName())).findFirst().isPresent())
        ).collect(Collectors.toList());

        String simpleName = annotation.annotationType().getSimpleName();
        str.append(simpleName + "(");

        List<String> values = new LinkedList<>();
        collect.forEach(method -> {
            try {
                values.add(method.getName() + "=" + method.invoke(annotation));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        String join = String.join(",", values);
        str.append(join + ")");

        return str.toString();
    }

}
