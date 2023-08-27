package com.SpringBootS17G2.dependencyinjectionproject.rest;

import com.SpringBootS17G2.dependencyinjectionproject.mapping.DeveloperResponse;
import com.SpringBootS17G2.dependencyinjectionproject.model.*;
import com.SpringBootS17G2.dependencyinjectionproject.tax.Taxable;
import com.SpringBootS17G2.dependencyinjectionproject.validation.DeveloperValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
    public DeveloperController(@Qualifier("developerTax") Taxable taxable) {
        /*
        @Qualifier ilgili interfacei (Taxable) başka sınıf da implement etse idi onu da belirtmek için kullanılacakken
        burada DeveloperTax classı dışında başka sınıf implement etmediği için gerek olmamasına rağmen tercihen eklendi.
        Zorunlu DEĞİL!*/
        this.taxable = taxable;
    }

    @GetMapping("/")
    public List<Developer> getAllDevelopers() {
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse getById(@PathVariable int id) {
        // @GetMapping("/{id}")deki "id" ile (@PathVariable int "id")deki parametre isimleri aynı olmalı!
        // Mesela birisi developerId olursa diğeri de developerId olmaslı.
        if (!DeveloperValidation.isIdValid(id)) {
            return new DeveloperResponse(null, "Developer Id is not valid", 400);
        }
        if(!developers.containsKey(id)){
            return new DeveloperResponse(null,
                    "Developer with given id does not exist: ",400);
        }
        return new DeveloperResponse(developers.get(id), "Success", 200);
        //Dönüş tipi developer olması yerine DeveloperResponse yaparak extra; mesaj ve durum bilgilerine de yer verebildik!
    }

    @PostMapping("/")
    public DeveloperResponse addDeveloper(@RequestBody Developer developer) {
        Developer developerToAdd = DeveloperFactory.createDeveloper(developer, taxable);
        if (developerToAdd == null) {
            return new DeveloperResponse(null, "Developer with given experience isn't valid", 400);
        }
        if (developers.containsKey(developer.getId())) {
            return new DeveloperResponse(null, "Developer with given " + developer.getId() + " Id already exists", 400);
        }
        if (!DeveloperValidation.isDeveloperValid(developer)) {
            return new DeveloperResponse(null, "Developer credentials are not valid", 400);
        }
        developers.put(developer.getId(), developerToAdd);
        return new DeveloperResponse(developers.get(developer.getId()), "Success. Developer has added", 201);
    }

    @PutMapping("/{id}")
    public DeveloperResponse updateDeveloper(@PathVariable int id, @RequestBody Developer developer) {
        if (!developers.containsKey(id)) {
            return new DeveloperResponse(null, "Developer with given " + id + " Id doesn't exists", 400);
        }
        developer.setId(id);// JSON da id yazılmaz ise idyi "0"a eşitliyor o nedenle request idsi ile eşitlenmesi için yazıldı.
        Developer developerToUpdate = DeveloperFactory.createDeveloper(developer, taxable);

        if (developerToUpdate == null) {
            return new DeveloperResponse(null, "Developer with given experience isn't valid", 400);
        }

        if (!DeveloperValidation.isDeveloperValid(developer)) {
            return new DeveloperResponse(null, "Developer credentials are not valid", 400);
        }

        developers.put(id, developerToUpdate);
        return new DeveloperResponse(developers.get(id), "Success. Developer with Id "  + id + " updated", 200);
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse deleteDeveloper(@PathVariable int id) {
        if (!developers.containsKey(id)) {
            return new DeveloperResponse(null, "Developer with given Id " + id + " doesn't exists", 400);
        }
        Developer developerToDelete = developers.get(id);
        developers.remove(id);
        return new DeveloperResponse(developerToDelete, "Success. Developer has been deleted",200);
    }
}
