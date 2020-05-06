package averages;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

class AvgMut {
  private double sum;
  private long count;

  public AvgMut(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public void include(double d) {
    this.sum += d;
    this.count++;
  }
  public void merge(AvgMut other) {
    this.sum += other.sum;
    this.count += other.count;
  }

  public Optional<Double> get() {
    if (count > 0) {
      return Optional.of(sum / count);
    } else {
      return Optional.empty();
    }
  }
}

public class Averager3 {
  public static void main(String[] args) {
    long start = System.nanoTime();
    ThreadLocalRandom.current()
        .doubles(5_000_000_000L, -Math.PI, +Math.PI)
        .parallel()
//        .map(d -> Math.sin(d))
        .collect(
            () -> new AvgMut(0, 0),
            (a1, a2) -> a1.include(a2),
            (a1, a2) -> a1.merge(a2))
        .get()
        .ifPresent(a -> System.out.println("Average is " + a));
    long time = System.nanoTime() - start;
    System.out.println("Time for computation " + (time / 1_000_000_000.0));
  }
}
