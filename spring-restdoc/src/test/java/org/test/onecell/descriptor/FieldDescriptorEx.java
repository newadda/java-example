package org.test.onecell.descriptor;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.IgnorableDescriptor;

import static org.springframework.restdocs.snippet.Attributes.key;

public class FieldDescriptorEx extends IgnorableDescriptor<FieldDescriptorEx> {
    private final String path;

    private Object type;

    private boolean optional;

    /**
     * Creates a new {@code FieldDescriptor} describing the field with the given
     * {@code path}.
     * @param path the path
     */
    protected FieldDescriptorEx(String path) {
        this.path = path;
    }

    /**
     * Specifies the type of the field. When documenting a JSON payload, the
     * {@link JsonFieldType} enumeration will typically be used.
     * @param type the type of the field
     * @return {@code this}
     * @see JsonFieldType
     */
    public final FieldDescriptorEx type(Object type) {
        this.type = type;
        return this;
    }

    /**
     * Marks the field as optional.
     * @return {@code this}
     */
    public final FieldDescriptorEx optional() {
        this.optional = true;
        return this;
    }

    /**
     * Returns the path of the field described by this descriptor.
     * @return the path
     */
    public final String getPath() {
        return this.path;
    }

    /**
     * Returns the type of the field described by this descriptor.
     * @return the type
     */
    public final Object getType() {
        return this.type;
    }

    /**
     * Returns {@code true} if the described field is optional, otherwise {@code false}.
     * @return {@code true} if the described field is optional, otherwise {@code false}
     */
    public final boolean isOptional() {
        return this.optional;
    }

    public FieldDescriptorEx constraint(String constraint)
    {
        this.attributes(key("constraint").value(constraint));
        return this;
    }


    public static FieldDescriptorEx fieldWithPathEx(String path) {
        return new FieldDescriptorEx(path);
    }





}
