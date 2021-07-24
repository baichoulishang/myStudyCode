import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date 2020/4/7 21:37
 */
public class Event {
    public String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss yyyy-dd-MM")
    public Date eventDate;

    public Event(String name, Date eventDate) {
        this.name = name;
        this.eventDate = eventDate;
    }

    public static void main(String[] args) throws Exception {
        whenSerializingUsingJsonFormat_thenCorrect();
    }

    public static void whenSerializingUsingJsonFormat_thenCorrect() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date date = df.parse("20-12-2014 02:30:00");
        Event event = new Event("party", date);
        String result = new ObjectMapper().writeValueAsString(event);
        System.out.println(result);
    }
}
