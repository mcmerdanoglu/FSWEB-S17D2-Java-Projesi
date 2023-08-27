package com.SpringBootS17G2.dependencyinjectionproject.model;

import com.SpringBootS17G2.dependencyinjectionproject.tax.Taxable;

public class DeveloperFactory {

    public static Developer createDeveloper(Developer developer, Taxable taxable) {
        Developer developerToAdd;
        if (developer.getExperience().name().equalsIgnoreCase("junior")) {//equals JUNIOR olarak da yazılabilir
            developerToAdd = new JuniorDeveloper(developer.getId(), developer.getName(),
                    developer.getSalary() - developer.getSalary() * taxable.getSimpleTaxRate(), developer.getExperience());
        } else if (developer.getExperience().name().equals("MID")) {
            developerToAdd = new MidDeveloper(developer.getId(), developer.getName(),
                    developer.getSalary() - developer.getSalary() * taxable.getMiddleTaxRate(), developer.getExperience());
        } else if (developer.getExperience().name().equalsIgnoreCase("senior")) {//equals SENIOR olarak da yazılabilir
            developerToAdd = new SeniorDeveloper(developer.getId(), developer.getName(),
                    developer.getSalary() - developer.getSalary() * taxable.getUpperTaxRate(), developer.getExperience());
        } else {
            developerToAdd = null;
        }
        return developerToAdd;
    }
}
