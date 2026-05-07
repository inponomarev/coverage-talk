package org.example.coverage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorTest {
    @ParameterizedTest
    @CsvSource({
            " 12_000,    500",
            "200_000, 60_000",
            " 30_000,  5_625",
            " 25_000,  3_750",
            "  9_000,      0",
            "      0,      0",
            "      1,      0",
            " 10_003,      1",
            "100_000, 25_000",
         })
    void taxTest(long income, long tax) {
        assertThat(TaxCalculator.tax(income))
                .isEqualTo(tax);
    }

}