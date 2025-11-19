package com.Niccolo.splitwise.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

@Getter
@EqualsAndHashCode
public final class Money {
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;

    private final BigDecimal amount;
    private final Currency currency;
    private final int defaultScale;

    public Money(BigDecimal amount, Currency currency) {
        if(amount == null || currency == null)
            throw new IllegalArgumentException("Amount and Currency must not be null");
        if(amount.signum() < 0){
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        this.currency = currency;

        int tempScale = currency.getDefaultFractionDigits();
        this.defaultScale = (tempScale < 0) ? 2 : tempScale; // Gestione valute speciali

        this.amount = amount.setScale(this.defaultScale, DEFAULT_ROUNDING);
    }

    // --- CORE ---
    public Money add(Money other){
        checkCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other){
        checkCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    /**
     * Divides amount by a factor, WITH LOSS
     * @param factor
     * @return new Money element
     */
    public Money divideByFactor(int factor){
        if(factor <= 0){
            throw new IllegalArgumentException("Cannot divide by a non-positive factor");
        }
        //Uso una scala più grande per arrotondare meglio
        BigDecimal dividedAmount = this.amount.divide(new BigDecimal(factor), defaultScale + 4, DEFAULT_ROUNDING).setScale(defaultScale, DEFAULT_ROUNDING);

        return new Money(dividedAmount, this.currency);
    }

    /**
     * Allocates the amount into 'n' parts randomly.
     * @param n Number of parts
     * @return list of amounts which total sum is equal to the original amount
     */
    public List<Money> allocate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Cannot allocate to <= 0 targets");
        }

        // 1. Base amount (Floor)
        int scale = this.amount.scale();
        BigDecimal baseAmount = this.amount.divide(new BigDecimal(n), scale, RoundingMode.DOWN);

        // 2. Remainder
        BigDecimal remainder = this.amount.subtract(baseAmount.multiply(new BigDecimal(n)));

        // 3. Calculate step (minimal unit, e.g. 0.01)
        BigDecimal step = BigDecimal.ONE.movePointLeft(scale);
        int remainderSteps = remainder.divide(step, RoundingMode.UNNECESSARY).intValue();

        // 4. Fill the list
        List<Money> results = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            if (i < remainderSteps) {
                // I primi 'remainderSteps' ricevono un centesimo in più
                results.add(new Money(baseAmount.add(step), this.currency));
            } else {
                // Gli altri ricevono la base
                results.add(new Money(baseAmount, this.currency));
            }
        }

        // 5. Shuffle to make it random (fair distribution of the extra cent)
        Collections.shuffle(results);

        return results;
    }

    // Helper privato per evitare duplicazione codice
    private void checkCurrency(Money other) {
        if(!this.currency.equals(other.currency)){
            throw new IllegalArgumentException("Currency mismatch: " + this.currency + " vs " + other.currency);
        }
    }

    // --- Object Methods ---
    @Override
    public String toString() {
        return currency.getSymbol() + " " + amount;
    }
}
