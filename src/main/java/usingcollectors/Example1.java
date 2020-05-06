package usingcollectors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Example1 {
  public static void main(String[] args) {
    Map<Integer, String> map =
        Stream.of("short", "long", "advertise", "commuter",
        "banana", "fruit", "I", "am", "quite", "irrelevant")
        .collect(Collectors.groupingBy(
            s -> s.length(), // primary collector
            Collectors.joining(", "))); // "downstream" collector
    System.out.println(map);

  }
}
