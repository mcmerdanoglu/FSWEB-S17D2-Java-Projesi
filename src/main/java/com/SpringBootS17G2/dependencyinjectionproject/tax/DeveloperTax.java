package com.SpringBootS17G2.dependencyinjectionproject.tax;

public class DeveloperTax implements Taxable{
    @Override
    public double getSimpleTaxRate() {
        return 0.25;
    }

    @Override
    public double getMiddleTaxRate() {
        return 0.35;
    }

    @Override
    public double getUpperTaxRate() {
        return 0.45;
    }
}
