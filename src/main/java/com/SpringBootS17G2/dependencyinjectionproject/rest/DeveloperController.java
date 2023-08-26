package com.SpringBootS17G2.dependencyinjectionproject.rest;

import com.SpringBootS17G2.dependencyinjectionproject.model.Developer;
import com.SpringBootS17G2.dependencyinjectionproject.tax.Taxable;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private Map<Integer, Developer> developers;
    private Taxable taxable;

    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }

    @Autowired //Şuan için yazmak gerekmiyor ama best practice için iyi bir yöntem.
    public DeveloperController(@Qualifier("DeveloperTax") Taxable taxable) {
        /*
        @Qualifier ilgili interfacei (Taxable) başka sınıf da implement etse idi onu da belirtmek için kullanılacakken
        burada DeveloperTax classı dışında başka sınıf implement etmediği için gerek olmamasına rağmen tercihen eklendi.
        Zorunlu DEĞİL!*/
        this.taxable = taxable;
    }

    @GetMapping("/")
    public List<Developer> getAllDevelopers(){
        return developers.values().stream().toList();
    }
    @GetMapping("/{id}")
    public Developer getById(@PathVariable int id){
        // @GetMapping("/{id}")deki "id" ile (@PathVariable int "id")deki parametre isimleri aynı olmalı!
        // Mesela birisi developerId olursa diğeri de developerId olmaslı.
        return developers.get(id);
    }
}
