package funjava.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CsvParser {

    public static final String CSV_SEPERATOR = ";";

    public static List<Map<String, String>> parseCsv(String csvFilename) {
        List<List<String>> lines;
        try {
            lines = readCsvLines(csvFilename);
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

        if (lines.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> header = lines.get(0);
        return lines
                .stream()
                .skip(1)
                .map(columns -> columnsToMap(header, columns))
                .collect(Collectors.toList());
    }

    private static Map<String, String> columnsToMap(List<String> header, List<String> columnValues) {
        if (columnValues.size() > header.size()) {
            throw new RuntimeException("Number of columns in header does not match number of data columns: " + columnValues);
        }

        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < columnValues.size(); i++) {
            if (!columnValues.get(i).isEmpty()){
                result.put(header.get(i), columnValues.get(i));
            }
        }

        return result;
    }

    private static List<List<String>> readCsvLines(String csvFilename) throws IOException, URISyntaxException {
        Path csvPath = Paths.get(CsvParser.class.getResource("/" + csvFilename).toURI());

        List<String> rawLines = Files.readAllLines(csvPath);

        return rawLines
                .stream()
                .map(CsvParser::removeUnicodeBOM)
                .map(line -> line.split(CSV_SEPERATOR))
                .map(Arrays::asList)
                .collect(Collectors.toList());
    }

    private static String removeUnicodeBOM(String line) {
        if (line.startsWith("\uFEFF")) {
            return line.substring(1);
        } else {
            return line;
        }
    }
}
