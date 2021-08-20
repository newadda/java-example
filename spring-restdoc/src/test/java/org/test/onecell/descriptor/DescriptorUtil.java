package org.test.onecell.descriptor;

import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;

public class DescriptorUtil {
    public static Attributes.Attribute constraint(String constraint)
    {
        return  key("constraint").value(constraint);
    }
}
