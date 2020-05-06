package averages;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Averager2 {
  public static void main(String[] args) {
    long start = System.nanoTime();
    ThreadLocalRandom.current()
        .doubles(3_000_000_000L, -Math.PI, +Math.PI)
        .parallel()
//        .map(d -> Math.sin(d))
        .boxed()
        .reduce(new Avg(0, 0),
            (a, d) -> a.include(d),
            (a1, a2) -> a1.merge(a2))
        .get()
        .ifPresent(a -> System.out.println("Average is " + a));
    long time = System.nanoTime() - start;
    System.out.println("Time for computation " + (time / 1_000_000_000.0));
  }
}
