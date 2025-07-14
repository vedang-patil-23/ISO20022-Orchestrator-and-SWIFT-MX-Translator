package com.iso20022.transform.mapper;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Mapper for amount and currency conversions between SWIFT and ISO 20022 formats
 */
@Component
@Named("AmountMapper")
public class AmountMapper {

    /**
     * Converts SWIFT amount string (e.g., "EUR1234,56") to BigDecimal
     */
    @Named("swiftAmountToBigDecimal")
    public BigDecimal swiftAmountToBigDecimal(String swiftAmount) {
        if (swiftAmount == null || swiftAmount.length() < 3) {
            return null;
        }
        
        try {
            String currency = swiftAmount.substring(0, 3);
            String amountStr = swiftAmount.substring(3).replace(",", ".");
            return new BigDecimal(amountStr).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid SWIFT amount format: " + swiftAmount, e);
        }
    }
    
    /**
     * Gets the number of decimal places for a given currency
     */
    @Named("getCurrencyDecimals")
    public int getCurrencyDecimals(String currencyCode) {
        try {
            Currency currency = Currency.getInstance(currencyCode);
            if (currency.getCurrencyCode().equals("JPY") || 
                currency.getCurrencyCode().equals("KRW") ||
                currency.getCurrencyCode().equals("ISK") ||
                currency.getCurrencyCode().equals("CLP") ||
                currency.getCurrencyCode().equals("PYG") ||
                currency.getCurrencyCode().equals("JPY")) {
                return 0;
            }
            return 2;
        } catch (Exception e) {
            // Default to 2 decimal places if currency is unknown
            return 2;
        }
    }
    
    /**
     * Converts amount to the correct scale based on currency
     */
    @Named("scaleAmount")
    public BigDecimal scaleAmount(BigDecimal amount, String currencyCode) {
        if (amount == null) {
            return null;
        }
        int decimals = getCurrencyDecimals(currencyCode);
        return amount.setScale(decimals, RoundingMode.HALF_UP);
    }
}
