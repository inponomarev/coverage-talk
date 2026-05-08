package org.example.coverage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Callable;

public class Dispatcher {
    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    public enum Value {
        A, B, C, D
    }

    void doA() {
        logger.info("A");
    }

    void doB() {
        logger.info("B");
    }

    void doC() {
        logger.info("C");
    }

    void doD() {
        logger.info("D");
    }

    public void process1(Value v) {
        switch (v) {
            case A -> doA();
            case B -> doB();
            case C -> doC();
            case D -> doD();
        }
    }

    public void process2(Value v) {
        Map.<Value, Runnable>of(
                Value.A, this::doA,
                Value.B, this::doB,
                Value.C, this::doC,
                Value.D, this::doD).get(v).run();
    }

}
