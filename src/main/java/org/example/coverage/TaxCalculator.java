package org.example.coverage;

public class TaxCalculator {
    public static long tax(long income) {
        long tax;
        long allowance = 10000;
        if (income > 25000) {
            long reduction = (income - 25000) / 2;
            allowance = Math.max(0, allowance - reduction);
        }
        long taxableIncome = Math.max(0, income - allowance);
        if (taxableIncome <= 100000) {
            tax = Math.round(taxableIncome * .25);
        } else {
            tax = Math.round(100000 * .25);
            long remaining = taxableIncome - 100000;
            tax = tax + Math.round(remaining * .35);
        }
        return tax;
    }
}
