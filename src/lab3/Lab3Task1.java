package lab3;

import java.math.BigInteger;
import java.util.concurrent.*;
import java.util.stream.LongStream;

public class Lab3Task1
{
    private static final long MAX = 10_000_000_000L;

    private static CompletionService<BigInteger> getLongCompletionService(int threads, ExecutorService executor)
    {
        long chunkSize = MAX / threads;
        CompletionService<BigInteger> completionService = new ExecutorCompletionService<>(executor);

        for (int i = 0; i < threads; i++)
        {
            int threadIndex = i;
            completionService.submit(
                () -> {
                    long start = threadIndex * chunkSize + 1;
                    long end = (threadIndex == threads - 1) ? MAX : (threadIndex + 1) * chunkSize;
                    BigInteger sum = BigInteger.ZERO;
                    for (long j = start; j <= end; j++)
                    {
                        sum = sum.add(BigInteger.valueOf(j));
                    }
                    return sum;
                }
            );
        }
        return completionService;
    }

    private static BigInteger calculateSum(int threads) throws InterruptedException, ExecutionException
    {
        if (threads == 1)
        {
            BigInteger sum = BigInteger.ZERO;
            for (long i = 1; i <= MAX; i++)
            {
                sum = sum.add(BigInteger.valueOf(i));
            }
            return sum;
        }
        else
        {
            ExecutorService executor = Executors.newFixedThreadPool(threads);
            CompletionService<BigInteger> completionService = getLongCompletionService(threads, executor);

            BigInteger totalSum = BigInteger.ZERO;
            for (int i = 0; i < threads; i++)
            {
                totalSum = totalSum.add(completionService.take().get());
            }

            executor.shutdown();
            return totalSum;
        }
    }

    public static void run()
    {
        int[] threadsCount = {1, 2, 4, 8};

        for (int threads : threadsCount)
        {
            BigInteger sum;
            long startTime = System.currentTimeMillis();

            try
            {
                sum = calculateSum(threads);
            }
            catch (ExecutionException | InterruptedException e)
            {
                throw new RuntimeException(e);
            }

            long endTime = System.currentTimeMillis();
            System.out.printf("%d поток(ов): сумма = %d, время = %d мс%n",
                    threads, sum, endTime - startTime);
        }
    }
}
