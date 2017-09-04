package funjava.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class CsvParserTest {
    @Test
    public void readCsvLines() throws Exception {
        List<Map<String, String>> users = CsvParser.parseCsv("people.csv");

        Assert.assertTrue(users.size() > 0);

        Map<String, String> maybeAlice = users.get(0);
        Assert.assertEquals("Alice", maybeAlice.get("Name"));
        Assert.assertEquals("DE", maybeAlice.get("Nationality"));
    }
}