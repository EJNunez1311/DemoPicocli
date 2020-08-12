package dev.proyecto.cli.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Person extends PanacheEntity {
    public String name;
    public String direccion;

    public static Person findByName(String name){
        return find("name", name).firstResult();
    }
}
