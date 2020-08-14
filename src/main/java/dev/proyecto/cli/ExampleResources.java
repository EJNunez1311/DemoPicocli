package dev.proyecto.cli;

import dev.proyecto.cli.Entity.Person;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class ExampleResources {
    @GetMapping("/hello")
    public String hello(){return "hello";}

    @PostMapping("/person")
    @Transactional
    public void addPerson(Person person)
    {
        Person.persist(person);
    }

    @GetMapping("/person")
    public List<Person> getPeople(){
        return Person.listAll();
    }

    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable("id") long id){
        return Person.findById(id);
    }

    @DeleteMapping("/person/{id}")
    @Transactional
    public void deletePerson(@PathVariable("id") long id){
        Person.delete("id",id);
    }
}
