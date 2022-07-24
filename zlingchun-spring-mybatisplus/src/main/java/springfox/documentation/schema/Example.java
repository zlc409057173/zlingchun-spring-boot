package springfox.documentation.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import springfox.documentation.service.VendorExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@JsonInclude(Include.NON_EMPTY)
public class Example {
    private final String id;
    private final String summary;
    private final String description;
    private final Object value;
    private final String externalValue;
    private final String mediaType;
    private final List<VendorExtension> extensions = new ArrayList();

    /** @deprecated */
    @Deprecated
    public Example(Object value) {
        this.value = value;
        this.mediaType = null;
        this.externalValue = null;
        this.id = null;
        this.summary = null;
        this.description = null;
    }

    /** @deprecated */
    @Deprecated
    public Example(String mediaType, Object value) {
        this.mediaType = mediaType;
        this.value = value;
        this.externalValue = null;
        this.id = null;
        this.summary = null;
        this.description = null;
    }

    public Example(String id, String summary, String description, Object value, String externalValue, String mediaType) {
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.value = value;
        this.externalValue = externalValue;
        this.mediaType = mediaType;
    }

    public String getId() {
        return this.id;
    }

    public String getSummary() {
        return this.summary;
    }

    public String getDescription() {
        return this.description;
    }

    public String getExternalValue() {
        return this.externalValue;
    }

    public List<VendorExtension> getExtensions() {
        return this.extensions;
    }

    public Object getValue() {
        return this.value;
    }

    public Optional<String> getMediaType() {
        return Optional.ofNullable(this.mediaType);
    }

    public String toString() {
        return String.valueOf(this.value);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Example example = (Example)o;
            return Objects.equals(this.id, example.id) && Objects.equals(this.summary, example.summary) && Objects.equals(this.description, example.description) && this.value.equals(example.value) && Objects.equals(this.externalValue, example.externalValue) && Objects.equals(this.mediaType, example.mediaType) && this.extensions.equals(example.extensions);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.summary, this.description, this.value, this.externalValue, this.mediaType, this.extensions});
    }
}