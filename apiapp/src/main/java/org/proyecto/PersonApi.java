package org.proyecto;

import io.quarkus.panache.common.Parameters;
import org.proyecto.Entity.Person;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonApi {

    @POST
    @Transactional
    public void add(Person person) {
        Person.persist(person);
    }

    @GET
    public List<Person> getPeople(){
        return Person.listAll();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public void update(@PathParam("id") long id, Person person){
            Person.update("",Parameters.with("id","%"+id+"%"));
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public void deletePerson(@PathParam("id") long id){
        Person.deleteById(id);
    }
}
