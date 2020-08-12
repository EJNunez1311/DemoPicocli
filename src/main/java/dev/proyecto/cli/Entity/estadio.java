package dev.proyecto.cli.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class estadio extends PanacheEntity {
    public String nombre;
    }

