package services;

import java.util.HashMap;
import utils.CLITheme;

public class CurrencyService {
    private HashMap<String, Double> rates= new HashMap<>();

    public CurrencyService(){
        rates.put("BHD", 1.0);
        rates.put("USD", 2.65);
        rates.put("GBP", 1.98);
        rates.put("EUR", 2.23);
        rates.put("SAR", 9.97);
    }

    public double convert(double amount, String fromCurrency, String toCurrency){
        if (amount < 0) {
            CLITheme.error("Amount cannot be negative.");
            return 0;
        }

        Double fromRate= rates.get(fromCurrency);
        Double toRate= rates.get(toCurrency);
        if (fromRate==null || toRate==null){
            CLITheme.error("Invalid currency.");
            return 0;
        }
        double defaultAmount= amount/fromRate;
        return defaultAmount*toRate;
    }
}
