package funjava.block3;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MutableMapKeyExamples {

    @Test
    @Ignore
    public void mutableMapKeyExample() {
        Map<Date, String> exampleMap = new HashMap<>();

        //Datum '1' soll auf 'value' abbilden:
        Date key = new Date(1);
        exampleMap.put(key, "value");
        Assert.assertEquals("value", exampleMap.get(new Date(1)));

        //Eine Nachfrage in der Map nach Datum '2' sollte null zurück geben:
        key.setTime(2);
        Assert.assertEquals(null, exampleMap.get(key));

        //Eine Nachfrage in der Map nach Datum '2' (mit einem neuen Dateo-object) sollte immer noch null zurück geben:
        Date newKey = new Date(2);
        Assert.assertEquals(null, exampleMap.get(key));

        //Eine Nachfrage in der Map nach Datum '1' sollte weiterhin 'value' zurückgeben...
        Assert.assertEquals("value", exampleMap.get(new Date(1)));
        //...tut es aber nicht!
    }
}
