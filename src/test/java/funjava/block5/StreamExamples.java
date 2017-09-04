package funjava.block5;

import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.Assert;
import org.junit.Test;

import static io.vavr.API.*;

public class StreamExamples {
    @Test
    public void mapStream() {
        Map<String, String> map = SortedMap("key1", "value1", "key2", "value2");

        List<String> strings = map.toStream()
                .map(keyValue -> keyValue._1 + " = " + keyValue._2)
                .toList();

        Assert.assertEquals(List.of("key1 = value1", "key2 = value2"), strings);
    }
}
