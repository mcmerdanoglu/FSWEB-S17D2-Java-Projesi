package com.SpringBootS17G2.dependencyinjectionproject.tax;

import org.springframework.stereotype.Component;

@Component
public class DeveloperTax implements Taxable{
    //@Component eklenmezse Spring bu sınıfı tanıyamıyor ve hata veriyor.
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
