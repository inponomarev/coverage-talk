package org.example.coverage;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PairTest {
    @Test
    void testMaxComponent(){
        assertThat(new Pair(3, 2).maxComponent()).isEqualTo(3);
    }

    @Test
    void testMaxComponent2(){
        Pair pair = new Pair(3, 2);
        assertThat(pair.maxComponent2()).isEqualTo(3);
    }

    @Test
    void testMaxComponent3(){
        Pair pair = new Pair(3, 2);
        assertThat(pair.maxComponent3()).isEqualTo(3);
    }
}