package enumerated;

import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @date 2020/1/20 20:46
 */
public class SpicinessTest {
    public static void main(String[] args) {
        String shit = "";
        List<String> result = new ArrayList<>();
        Arrays.stream(shit.split("\n")).filter(e -> !StringUtil.isNullOrEmpty(e)).forEach(e -> {
            if (e.indexOf(".") != -1) {
                result.add(e.substring(e.indexOf(".") + 1));
            } else {
                result.add(e);
            }
        });
        AtomicInteger code = new AtomicInteger(1);
        result.stream().sorted().forEach(e -> System.out.println("zuan," + (code.getAndIncrement()) + "=" + e));
    }
}
