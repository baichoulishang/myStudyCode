import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @date 2020/4/7 21:16
 */
public class RawBean {
    public String name;
    // @JsonRawValue
    public String attrs;

    public RawBean(String name, String attrs) {
        this.name = name;
        this.attrs = attrs;
    }

    public static void main(String[] args) throws JsonProcessingException {
        whenSerializingUsingJsonRawValue_thenCorrect();
    }

    public static void whenSerializingUsingJsonRawValue_thenCorrect()
            throws JsonProcessingException {
        RawBean bean = new RawBean("My bean", "{\"attr\":false}");
        String result = new ObjectMapper().writeValueAsString(bean);
        System.out.println(result);
    }

}