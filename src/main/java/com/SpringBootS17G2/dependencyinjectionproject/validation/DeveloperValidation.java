package com.SpringBootS17G2.dependencyinjectionproject.validation;

import com.SpringBootS17G2.dependencyinjectionproject.mapping.DeveloperResponse;
import com.SpringBootS17G2.dependencyinjectionproject.model.Developer;

public class DeveloperValidation {

    public static boolean isIdValid(int id){
        return id>0;
    }

    public static boolean isDeveloperValid(Developer developer){
        return isIdValid(developer.getId()) &&
                developer.getName() !=null && !developer.getName().isEmpty() &&
                developer.getSalary()>30000;
    }
}
