package com.bwin.ignite.test;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by dreddy on 12/13/2016.
 */
public class ServerHelper {
    List<Ignite> instances = Collections.synchronizedList(new ArrayList<>());

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void ensureClusterSize(int size) throws InterruptedException {
        if (instances.size() < size) {
            int diff = size - instances.size();
            CountDownLatch latch = new CountDownLatch(diff);
            for (int x = 0; x < diff; x++) {
                executorService.submit(new CreateIgniteInstance(latch));
            }
            latch.await(10, TimeUnit.SECONDS);
        } else if (instances.size() > size) {
            for (int x = instances.size() - 1; x >= size; x--) {
                Ignite instance = instances.remove(x);
                instance.close();
            }
        }
    }

    public final class CreateIgniteInstance implements Callable<Ignite> {
        private CountDownLatch latch;

        public CreateIgniteInstance(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public Ignite call() throws Exception {
            Ignite instance = Ignition.start("example-ignite.xml");
            instances.add(instance);
            if (latch != null) {
                latch.countDown();
            }
            return instance;
        }
    }

}
