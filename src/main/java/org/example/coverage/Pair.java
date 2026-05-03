package org.example.coverage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record Pair(int x, int y) {
    private static final Logger logger = LoggerFactory.getLogger(Pair.class);

    public int maxComponent() {
        if (x > y) {
            return x;
        } else {
            return x;
        }
    }

    //@formatter:off
    public int maxComponent2(){
        if (x > y) {
            return this
                    .
                    x;
        } else {
            return x;
        }
    }
    //@formatter:on

    public int maxComponent3() {
        logger.info("Calculating maxComponent2 for ({}, {})", x, y);
        if (x > y) {
            logger.info("x > y, so the maximum is x!");
            System.gc(); // because why not
            ohByTheWay();
            andMore();
            return x;
        } else {
            return x;
        }
    }

    private void andMore() {
        System.nanoTime();
    }

    private void ohByTheWay() {
        System.currentTimeMillis();
    }
}
