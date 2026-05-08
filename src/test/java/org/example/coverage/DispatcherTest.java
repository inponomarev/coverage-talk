package org.example.coverage;

import org.junit.jupiter.api.Test;

class DispatcherTest {
    @Test
    void test1() {
       new Dispatcher().process1(Dispatcher.Value.A);
    }
    @Test
    void test2() {
        new Dispatcher().process2(Dispatcher.Value.A);
    }
}