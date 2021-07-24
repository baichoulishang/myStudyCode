import com.fasterxml.jackson.core.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2020/4/7 20:58
 */
public class JacksonTest {

    public static void main(String[] args) throws JsonProcessingException {
        whenSerializingUsingJsonAnyGetter_thenCorrect();
    }

    public void testJson6() throws Exception {
        Map<String, Object> map = new HashMap<>();
        String json = "{\"id\":\"abdae\",\"model\",\"nokia\"}";
    }

    private static void whenSerializingUsingJsonAnyGetter_thenCorrect() throws JsonProcessingException {
        ExtendableBean bean = new ExtendableBean("My bean");
        bean.add("attr1", "val1");
        bean.add("attr2", "val2");
        String result = null;
        try {
            result = new ObjectMapper().writeValueAsString(bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        String value = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(bean);
        System.out.println(value);
    }

}
