package tech.wallet;

import java.util.concurrent.Executors;

public class Demo {
  public static void main(String[] args) {
    new Demo().runExecutor();
  }

  void runExecutor() {
    for (; ; ) {
      long start = System.currentTimeMillis();
      try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        for (int i = 0; i < 100_00; i++) {
          executor.submit(
              () -> {
                Thread.sleep(2000);
                return null;
              });
        }
      }
      long end = System.currentTimeMillis();
      System.out.println(end - start + " ms");
    }
  }
}
