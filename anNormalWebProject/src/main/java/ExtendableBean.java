import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * @date 2020/4/7 20:58
 */
@JsonPropertyOrder(alphabetic = true)
public class ExtendableBean {
    public String name;
    public Map<String, String> properties;

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }

    public ExtendableBean(String name) {
        this.name = name;
        properties = new HashMap<>();
    }

    public void add(String key, String value) {
        properties.put(key, value);
    }
}