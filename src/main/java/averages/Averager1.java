package averages;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

class Avg {
  private double sum;
  private long count;

  public Avg(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public Avg include(double d) {
    return new Avg(this.sum + d, this.count + 1);
  }
  public Avg merge(Avg other) {
    return new Avg(this.sum + other.sum, this.count + other.count);
  }

  public Optional<Double> get() {
    if (count > 0) {
      return Optional.of(sum / count);
    } else {
      return Optional.empty();
    }
  }
}

public class Averager1 {
  public static void main(String[] args) {
    long start = System.nanoTime();
    ThreadLocalRandom.current()
        .doubles(200_000_000L, -Math.PI, +Math.PI)
//        .parallel()
        .map(d -> Math.sin(d))
        .mapToObj(d -> new Avg(d, 1))
        .reduce((a1, a2) -> a1.merge(a2))
        .ifPresent(a ->
            System.out.println("Average is "
                + a.get().map(v -> "" + v).orElse("unknown")));
    long time = System.nanoTime() - start;
    System.out.println("Time for computation " + (time / 1_000_000_000.0));
  }
}
