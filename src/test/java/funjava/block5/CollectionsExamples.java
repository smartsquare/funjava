package funjava.block5;

import funjava.vavr.AccountVavr;
import funjava.vavr.PersonVavr;
import funjava.vavr.VavrData;
import io.vavr.collection.Array;
import io.vavr.collection.CharSeq;
import io.vavr.collection.*;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;
import io.vavr.collection.Set;
import io.vavr.collection.SortedMap;
import io.vavr.collection.Stream;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

import static io.vavr.API.*;

public class CollectionsExamples {
    @Test
    public void createCollectionsWithApiImport() {
        Array<Integer> intArray = Array(1, 2, 3);
        CharSeq string = CharSeq("Hello, World");
        Map<String, String> map = Map("key1", "value1", "key2", "value2");
        Queue<Integer> emptyQueue = Queue();
        List<String> stringList = List("Hello", "World");
        Set<String> stringSet = Set("Foo", "Bar", "Foo");
        //... und viele mehr!
    }

    @Test
    public void createCollectionsWithStaticMethods() {
        Array<Integer> intArray = Array.of(1, 2, 3);
        SortedMap<String, String> sortedMap = TreeMap.of("key1", "value1", "key2", "value2");
        Map<String, String> map = HashMap.of("key1", "value1", "key2", "value2");
        Set<String> stringSet = HashSet.of("Foo", "Bar", "Foo");
    }

    @Test
    public void createFromJavaCollection() {
        java.util.List<Integer> javaList = Arrays.asList(1, 2, 3);
        Array<Integer> intArray = Array.ofAll(javaList);
        List<Integer> intList = List.ofAll(javaList);

        java.util.Map<String, String> javaMap = new java.util.HashMap<>();
        javaMap.put("key1", "value1");
        javaMap.put("key2", "value2");
        Map<String, String> map = HashMap.ofAll(javaMap);
    }

    @Test
    public void convertToJavaCollection() {
        Integer[] array = List(1, 2, 3).toJavaArray(Integer.class);
        java.util.List<Integer> list = List(1, 2, 3).toJavaList();
        java.util.Map<String, String> map = Map("key1", "value1", "key2", "value2").toJavaMap();
    }

    @Test
    public void range() {
        Array<Double> values = Array.rangeClosedBy(-1, 1, 0.5);
        Assert.assertEquals(Array.of(-1.0, -0.5, 0.0, 0.5, 1.0), values);
    }


    @Test
    public void listAppend() {
        List<Integer> originalList = List(1, 2, 3);
        List<Integer> listWithNewElement = originalList.append(4);

        Assert.assertEquals(List(1, 2, 3), originalList);
        Assert.assertEquals(List(1, 2, 3, 4), listWithNewElement);
    }

    @Test
    public void toJavaStream() {
        int smallestNumber = List(17, 42, 23, 5)
                .toJavaStream()
                .sorted()
                .findFirst()
                .get();
    }

    @Test
    public void toVavrStream() {
        int smallestNumber = List(17, 42, 23, 5)
                .toStream()
                .sorted()
                .head();
    }

    @Test
    public void dotProduct() {
        Seq<Integer> vector1 = Vector(2, 4, -1);
        Seq<Integer> vector2 = Vector(3, -2, 2);

        Number product = vector1.zip(vector2)
                .map(pair -> pair._1 * pair._2)
                .sum();

        Assert.assertEquals(2 * 3 - 2 * 4 - 1 * 2, product.intValue());
    }

    @Test
    public void listAverage() {
        List<Integer> numbers = List(23, 7, 3, 42, 11);
        Double average = numbers.average().get();
        Assert.assertTrue(average > 17.1 && average < 17.3);
    }

    @Test
    public void fibonacci() {
        Stream<Integer> fibonacciStream = Stream(1, 1)
                .appendSelf(self -> self.zip(self.tail()).map(t -> t._1 + t._2));
        List<Integer> firstFiveFibs = fibonacciStream.take(5).toList();
        Assert.assertEquals(List(1, 1, 2, 3, 5), firstFiveFibs);
    }

    @Test
    public void getRichestPerson() {
        VavrData data = new VavrData();
        String nameOfRichestPerson = data.getAccounts()
                .toStream()
                .sorted(Comparator.comparing(AccountVavr::getBalance).reversed())
                .map(AccountVavr::getOwner)
                .map(PersonVavr::getName)
                .head();

        Assert.assertEquals("Carol", nameOfRichestPerson);
    }


    @Test
    public void intersperse() {
        List<String> row = List("Name", "Ort", "Straße");
        String csvRow = row
                .intersperse(",")
                .fold("", String::concat);
        Assert.assertEquals("Name,Ort,Straße", csvRow);

        Assert.assertEquals("Name,Ort,Straße", row.mkString(","));
    }

    @Test
    public void anagrams() {
        Set<CharSeq> dictionary = Set("coil", "coif", "comb", "late", "tale", "teal", "copy", "cool", "cook", "mesa", "same", "seam")
                .map(CharSeq::of);

        CharSeq inputWord = CharSeq("msae");

        Set<String> matches = inputWord.permutations()
                .filter(dictionary::contains)
                .map(CharSeq::toString)
                .toSet();

        Assert.assertEquals(Set("mesa", "same", "seam"), matches);
    }
}
