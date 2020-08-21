package dev.proyecto.cli;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;
import java.io.*;
import java.lang.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {Comando1.class, Comando2.class, test.class},
        version= "version 1.0",
        footerHeading = "2020 - Demo\n",
        headerHeading = "Proyecto Framework Tool Set\n",
        description = "App que hace varias cosas"
)
public class DemoApp {
    String path = System.getProperty("user.dir");

    public static String menu() {

        String selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("Que tipo de aplicacion desea hacer?");
        System.out.println("1 - Api-server con modelo usuario autenticacion");
        System.out.println("2 - Api-server sin ningun tipo de configuracion");
        System.out.println("Salir");
        selection = input.nextLine();
        return selection;
    }


    public static void modelaje(String nom){


        Scanner input = new Scanner(System.in);
        String sel;
        String nomb;
        String clase;
        String atributo;
        String tipo;
        String modelos = "";
        String getset = "";
        String entidad ="";
        String modelaje;



        do{
            System.out.println("Digite nombre del modelo: ");
            nomb = input.nextLine();

            System.out.println("Nombre del modelo: " + nomb + "\nConfirmar(Si/No)");
            sel = input.nextLine();
        }while(!sel.equalsIgnoreCase("Si"));

        clase = nomb.substring(0, 1).toUpperCase() + nomb.substring(1).toLowerCase();

        String claseminus = nomb.toLowerCase();

        do{

            System.out.println("Digite nombre del atributo: ");
            atributo = input.nextLine();


            System.out.println("Digite tipo del atributo \nTipos aceptados: boolean, byte, char, double, int, long, String\n" +
                    "Seleccion(Es case sensitive): ");
            tipo = input.nextLine();


            modelos = modelos +
                    "    public "+ tipo +" "+ atributo + ";\n" +
                    "\n";

            String aux;

            aux = atributo.substring(0, 1).toUpperCase() + atributo.substring(1).toLowerCase();

            getset = getset +
                    "    public "+ tipo +" get"+aux+"() {\n" +
                    "        return "+atributo.toLowerCase()+";\n" +
                    "    }\n" +
                    "\n" +
                    "    public void set" + aux + "("+ tipo +" "+ atributo.toLowerCase() +") {\n" +
                    "        this."+atributo.toLowerCase()+" = "+ atributo.toLowerCase() +";\n" +
                    "    }\n";


            entidad = entidad +
                    "        entity.set"+aux+"("+claseminus+".get"+aux+"());\n";

            System.out.println("Desea agregar otro atributo? \nRespuesta(Si/No)");
            sel = input.nextLine();

        }while(!sel.equalsIgnoreCase("No"));

        String path = System.getProperty("user.dir");
        String archivojava = "package org.proyecto.Entity;\n" +
                "\n" +
                "import io.quarkus.hibernate.orm.panache.PanacheEntity;\n" +
                "\n" +
                "import javax.persistence.Entity;\n" +
                "\n" +
                "@Entity\n" +
                "public class "+ clase +" extends PanacheEntity {\n" +

                modelos

                +

                getset

                +
                "}"
                ;


        try {
            File myObj = new File(path +"/" + nom + "/src/main/java/org/proyecto/Entity/"+clase+".java");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("Archivo ya existe.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(path +"/" + nom + "/src/main/java/org/proyecto/Entity/"+clase+".java");
            myWriter.write(archivojava
            );
            myWriter.close();
            System.out.println("Modelo generado");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



        String archivoapi =
        "package org.proyecto;\n" +
                "\n" +
                "import org.proyecto.Entity.*;\n" +
                "import javax.inject.Inject;\n" +
                "import javax.persistence.EntityManager;\n" +
                "import javax.transaction.Transactional;\n" +
                "import javax.ws.rs.*;\n" +
                "import javax.ws.rs.core.MediaType;\n" +
                "import java.util.List;\n" +
                "\n" +
                "@Path(\"/api/"+ nomb +"\")\n" +
                "@Produces(MediaType.APPLICATION_JSON)\n" +
                "@Consumes(MediaType.APPLICATION_JSON)\n" +
                "public class "+ clase +"Api {\n" +
                "\n" +
                "    @Inject\n" +
                "    EntityManager entityManager;\n" +
                "\n" +
                "\n" +
                "    @POST\n" +
                "    @Transactional\n" +
                "    public void add("+ clase +" "+ claseminus +") {\n" +
                "        "+clase+".persist("+claseminus+");\n" +
                "    }\n" +
                "\n" +
                "    @GET\n" +
                "    public List<"+clase+"> get"+clase+"(){\n" +
                "        return "+clase+".listAll();\n" +
                "    }\n" +
                "\n" +
                "    @PUT\n" +
                "    @Transactional\n" +
                "    @Path(\"/{id}\")\n" +
                "    public "+clase+" update(@PathParam(\"id\") long id, " + clase + " " + claseminus+"){\n" +
                "        if ("+claseminus+".findById(id) == null) {\n" +
                "            throw new WebApplicationException(\"Id no fue enviado en la petición.\", 422);\n" +
                "        }\n" +
                "\n" +
                "        "+clase+" entity = entityManager.find("+clase+".class,id);\n" +
                "\n" +
                "        if (entity == null) {\n" +
                "            throw new WebApplicationException(\" "+clase+" con el id: \" + id + \" no existe.\", 404);\n" +
                "        }\n" +
                "\n" +
                "\n" +
                entidad +
                "        return entity;\n" +
                "    }\n" +
                "\n" +
                "    @DELETE\n" +
                "    @Transactional\n" +
                "    @Path(\"/{id}\")\n" +
                "    public void delete"+clase+"(@PathParam(\"id\") long id){\n" +
                "        "+clase+".deleteById(id);\n" +
                "    }\n" +
                "}"
                ;



        try {
            File myObj = new File(path +"/" + nom + "/src/main/java/org/proyecto/"+clase+"Api.java");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("Archivo ya existe.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(path +"/" + nom + "/src/main/java/org/proyecto/"+clase+"Api.java");

            myWriter.write(archivoapi
            );
            myWriter.close();
            System.out.println("Clase api generado");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



        String custApp=
                "package org.proyecto;\n" +
                        "\n" +
                        "import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;\n" +
                        "import org.eclipse.microprofile.openapi.annotations.info.Contact;\n" +
                        "import org.eclipse.microprofile.openapi.annotations.info.Info;\n" +
                        "import org.eclipse.microprofile.openapi.annotations.info.License;\n" +
                        "\n" +
                        "import javax.ws.rs.core.Application;\n" +
                        "\n" +
                        "\n" +
                        "@OpenAPIDefinition( \n" +
                        "        info = @Info(\n" +
                        "                title=\"Java Framework\",\n" +
                        "                version = \"1.0.0 (Test)\",\n" +
                        "                contact = @Contact(\n" +
                        "                        name = \"API Explorer\",\n" +
                        "                        url = \"http://pucmm.edu.do/\",\n" +
                        "                        email = \"pucmmisc@example.com\"),\n" +
                        "                license = @License(\n" +
                        "                        name = \"Proyecto Final 1.0©\",\n" +
                        "                        url = \"http://www.apache.org/licenses/LICENSE-2.0.html\")))\n" +
                        "public class CustomApplication extends Application {\n" +
                        "}";

        try {
            File myObj = new File(path +"/" + nom + "/src/main/java/org/proyecto/CustomApplication.java");
            if (myObj.createNewFile()) {
                System.out.println("Archivo Creado: " + myObj.getName());
            } else {
                System.out.println("Archivo ya existe.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(path +"/" + nom + "/src/main/java/org/proyecto/CustomApplication.java");
            myWriter.write(custApp
            );
            myWriter.close();
            System.out.println("ApiSwagger Inyectado");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



    }

}

@CommandLine.Command(name = "comando1", description = "Este comando ejecuta XYZ"
)
class Comando1 implements Runnable {

    String selection;
    Scanner input = new Scanner(System.in);

    DemoApp dem;
    String sel;
    @CommandLine.Option(names = {"-n", "--nombre"},
            description = "Nombre del usuario",
            required = true
    )
    String nombre;

    @CommandLine.Option(names = {"-s", "--sistema"}, description = "Sistema a configurar",
            defaultValue = "S001"
    )
    String sistema;

    @CommandLine.Option(names = {"-c", "--credencial"}, arity = "0..1",
            description = "Credencial", interactive = true)
    String credencial;

    @CommandLine.Parameters(index = "0", description = "Archivo de configuración", paramLabel = "Archivo")
    File archivo;

    @Override
    public void run() {

        System.out.println("Mi primer comando [" + nombre + "] sistema [" + sistema + "] Path ["
                + archivo.toPath() + "]");

        System.out.println("Nombre de la app: " + nombre + "\nConfirmar(Si/No)");
        sel = input.next();

//        do{
//            System.out.println("Digite nuevo nombre: ");
//            nombre = input.nextLine();
//
//            System.out.println("Nombre de la app: " + nombre + "\nConfirmar(Si/No)");
//            sel = input.nextLine();
//
//            if(sel == "Si"){
//                break;
//            }else if (sel == "si")
//                break;
//        }
//        while(true);
//
//        do{
//            sel = dem.menu();
//        System.out.println("Su seleccion es: "+ sel);
//        }
//        while (sel!="Salir");

    }
}

@CommandLine.Command(name = "comando2", description = "Este comando ejecuta ABC")
class Comando2 implements Runnable {

    @CommandLine.Option(names = {"-n", "--nombre"},
            description = "Nombre del usuario",
            required = true
    )
    String nombre;

    @CommandLine.Option(names = {"-att", "--attributo"},
            description = "Nombre del atributo",
            required = true)
    String atributo1;

    @CommandLine.Option(names = {"-tip", "--tipoatt"},
            description = "Tipo del atributo",
            required = true)
    String tipo1;

    @Override
    public void run() {
        System.out.println("Mi segundo comando");
        try {
            File myObj = new File("./src/main/java/dev/proyecto/cli/Entity/"+nombre+".java");
            if (myObj.createNewFile()) {
                System.out.println("Archivo Creado: " + myObj.getName());
            } else {
                System.out.println("Archivo ya existe.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("./src/main/java/dev/proyecto/cli/Entity/"+nombre+".java");
            myWriter.write("package dev.proyecto.cli.Entity;\n" +
                            "\n" +
                            "import io.quarkus.hibernate.orm.panache.PanacheEntity;\n" +
                            "\n" +
                            "import javax.persistence.Entity;\n" +
                            "\n" +
                            "@Entity\n" +
                            "public class " + nombre + " extends PanacheEntity {\n" +
                            "    public "+ tipo1 +" "+ atributo1 + ";\n"+ "    }\n" +
                            "\n"
            );
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
}


@CommandLine.Command(name = "test", description = "Este comando ejecuta la inicializacion del api")
class test implements Runnable {

    Scanner input = new Scanner(System.in);
    String nombre;
    DemoApp dem;
    String sel;



    @Override
    public void run() {

        do{
            System.out.println("Digite nombre de la applicacion: ");
            nombre = input.nextLine();

            System.out.println("Nombre de la app: " + nombre + "\nConfirmar(Si/No)");
            sel = input.nextLine();
        }while(!sel.equalsIgnoreCase("Si"));


        String comandos, wnds_cm1,wnds_cm2,wnds_cm3,wnds_cm4;

        comandos =
                "mvn io.quarkus:quarkus-maven-plugin:1.6.1.Final:create -DprojectGroupId=org.proyecto " +
                "-DprojectArtifactId="+ nombre +
                " -DclassName=\"org.proyecto.Apiapp\" -Dpath=\"/hello\"\n" +
                "\n" +
                "cd "+ nombre +
                "\n" +
                "mvn quarkus:add-extension -Dextensions=\"agroal\" \n" +
                "\n" +
                "mvn quarkus:add-extension -Dextensions=\"quarkus-hibernate-orm-panache\" \n" +
                "\n" +
                "mvn quarkus:add-extension -Dextensions=\"jdbc-mysql\" \n" +
                "\n"+
                "mvn quarkus:add-extension -Dextensions=\"io.quarkus:quarkus-smallrye-openapi\"\n" +
                "\n" +
                "mvn quarkus:add-extension -Dextensions=\"io.quarkus:quarkus-spring-security\"\n" +
                "\n"+
                "mvn quarkus:add-extension -Dextensions=\"io.quarkus:quarkus-resteasy-jsonb\"\n" +
                 "\n"+
                "exit\n";
        wnds_cm1 = "mvn io.quarkus:quarkus-maven-plugin:1.6.1.Final:create -DprojectGroupId=org.proyecto " +
                "-DprojectArtifactId="+ nombre +
                " -DclassName=\"org.proyecto.Apiapp\" -Dpath=\"/hello\"\n" + "& \"mvn quarkus:add-extension -Dextensions=\\\"agroal\\\" \\n\"";

        //System.out.println(comandos);

        ProcessBuilder processBuilder = new ProcessBuilder();
    // -- Linux --

    // Run a shell command
     //   processBuilder.command("bash", "-c", comandos);

    // -- Windows --

    // Run a command
    processBuilder.command("cmd.exe", "/c", wnds_cm1);

        String path = System.getProperty("user.dir");
        String userHome = System.getProperty("user.home");
        File currentDir = new File(".");
        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("App creada!");
//                System.out.println(output);
//                System.exit(0);
//                System.out.println("Working Directory = " + path);
                System.out.println(userHome);
            } else {
                //abnormal...
            }
            process.destroyForcibly();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        do{

            try {
                File myObj = new File(path+"/"+nombre+"/src/main/resources/application.properties");
                if (myObj.createNewFile()) {
                    System.out.println("Archivo Creado: " + myObj.getName());
                } else {
                    System.out.println("Archivo ya existe.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            try {
                FileWriter myWriter = new FileWriter(path+"/"+ nombre +"/src/main/resources/application.properties");
                myWriter.write(
                        "#Datasource Config\n" +
                        "quarkus.datasource.db-kind=mysql\n" +
                        "quarkus.datasource.driver=com.mysql.cj.jdbc.Driver\n" +
                        "quarkus.datasource.username=root\n" +
                        "quarkus.datasource.password=12345678\n" +
                        "quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/prueba\n" +
                        "quarkus.hibernate-orm.log.sql=true\n" +
                        "# drop and create the database at startup (use `update` to only update the schema)\n" +
                        "quarkus.hibernate-orm.database.generation=drop-and-create\n" +
                        "quarkus.smallrye-openapi.path=/swagger\n" +
                        "quarkus.swagger-ui.always-include=true\n" +
                        "quarkus.swagger-ui.path=/explorer\n" +
                        "mp.openapi.extensions.smallrye.operationIdStrategy=METHOD"
                );
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }


            sel = dem.menu();
            System.out.println("Su seleccion es: "+ sel);

            if(sel.equalsIgnoreCase("1")) {
                System.out.println("Proyecto creado con opcion 1");

                try {

                    File theDir = new File(path+"/" + nombre + "/src/main/java/org/proyecto/Entity/");
                    if (!theDir.exists()) theDir.mkdirs();
                    File myObj = new File(path+"/" + nombre + "/src/main/java/org/proyecto/Entity/Usuario.java");
                    if (myObj.createNewFile()) {
                        System.out.println("Archivo Creado: " + myObj.getName());
                    } else {
                        System.out.println("Archivo ya existe.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {

                    File theDir = new File(path+"/" + nombre + "/src/main/java/org/proyecto/Entity/");
                    if (!theDir.exists()) theDir.mkdirs();
                    FileWriter myWriter = new FileWriter(path+"/" + nombre + "/src/main/java/org/proyecto/Entity/Usuario.java");
                    myWriter.write("" +
                            "package org.proyecto.Entity;\n" +
                            "\n" +
                            "import io.quarkus.hibernate.orm.panache.PanacheEntity;\n" +
                            "\n" +
                            "import javax.persistence.Entity;\n" +
                            "\n" +
                            "@Entity\n" +
                            "public class Usuario extends PanacheEntity {\n" +
                            "    public String username;\n" +
                            "    public String password;\n" +
                            "    public String email;\n" +
                            "    public String token;\n" +
                            "\n" +
                            "    public String getUsername() {\n" +
                            "        return username;\n" +
                            "    }\n" +
                            "\n" +
                            "    public void setUsername(String username) {\n" +
                            "        this.username = username;\n" +
                            "    }\n" +
                            "\n" +
                            "    public String getPassword() {\n" +
                            "        return password;\n" +
                            "    }\n" +
                            "\n" +
                            "    public void setPassword(String password) {\n" +
                            "        this.password = password;\n" +
                            "    }\n" +
                            "\n" +
                            "    public String getEmail() {\n" +
                            "        return email;\n" +
                            "    }\n" +
                            "\n" +
                            "    public void setEmail(String email) {\n" +
                            "        this.email = email;\n" +
                            "    }\n" +
                            "\n" +
                            "    public String getToken() {\n" +
                            "        return token;\n" +
                            "    }\n" +
                            "\n" +
                            "    public void setToken(String token) {\n" +
                            "        this.token = token;\n" +
                            "    }\n" +
                            "}"

                    );
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {
                    File myObj = new File(path+"/" + nombre + "/src/main/java/org/proyecto/UsuarioApi.java");
                    if (myObj.createNewFile()) {
                        System.out.println("Archivo Creado: " + myObj.getName());
                    } else {
                        System.out.println("Archivo ya existe.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {

                    File theDir = new File(path+"/" + nombre + "/src/main/java/org/proyecto/Entity/");
                    if (!theDir.exists()) theDir.mkdirs();
                    FileWriter myWriter = new FileWriter(path+"/" + nombre + "/src/main/java/org/proyecto/UsuarioApi.java");
                    myWriter.write(
                            "package org.proyecto;\n" +
                                    "\n" +
                                    "import org.proyecto.Entity.*;\n" +
                                    "import javax.inject.Inject;\n" +
                                    "import javax.persistence.EntityManager;\n" +
                                    "import javax.transaction.Transactional;\n" +
                                    "import javax.ws.rs.*;\n" +
                                    "import javax.ws.rs.core.MediaType;\n" +
                                    "import java.util.List;\n" +
                                    "\n" +
                                    "@Path(\"/api/usuario\")\n" +
                                    "@Produces(MediaType.APPLICATION_JSON)\n" +
                                    "@Consumes(MediaType.APPLICATION_JSON)\n" +
                                    "public class UsuarioApi {\n" +
                                    "\n" +
                                    "    @Inject\n" +
                                    "    EntityManager entityManager;\n" +
                                    "\n" +
                                    "\n" +
                                    "    @POST\n" +
                                    "    @Transactional\n" +
                                    "    public void add(Usuario usuario) {\n" +
                                    "        Usuario.persist(usuario);\n" +
                                    "    }\n" +
                                    "\n" +
                                    "    @GET\n" +
                                    "    public List<Usuario> getUsuario(){\n" +
                                    "        return Usuario.listAll();\n" +
                                    "    }\n" +
                                    "\n" +
                                    "    @PUT\n" +
                                    "    @Transactional\n" +
                                    "    @Path(\"/{id}\")\n" +
                                    "    public Usuario update(@PathParam(\"id\") long id, Usuario usuario){\n" +
                                    "        if (usuario.findById(id) == null) {\n" +
                                    "            throw new WebApplicationException(\"Id no fue enviado en la petición.\", 422);\n" +
                                    "        }\n" +
                                    "\n" +
                                    "        Usuario entity = entityManager.find(Usuario.class,id);\n" +
                                    "\n" +
                                    "        if (entity == null) {\n" +
                                    "            throw new WebApplicationException(\"Usuario con el id: \" + id + \" no existe.\", 404);\n" +
                                    "        }\n" +
                                    "\n" +
                                    "\n" +
                                    "        entity.setUsername(usuario.getUsername());\n" +
                                    "        entity.setPassword(usuario.getPassword());\n" +
                                    "        entity.setEmail(usuario.getEmail());\n" +
                                    "        return entity;\n" +
                                    "    }\n" +
                                    "\n" +
                                    "    @DELETE\n" +
                                    "    @Transactional\n" +
                                    "    @Path(\"/{id}\")\n" +
                                    "    public void deleteUsuario(@PathParam(\"id\") long id){\n" +
                                    "        Usuario.deleteById(id);\n" +
                                    "    }\n" +
                                    "}"

                    );
                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                do {
                    System.out.println("\n\nCreacion de modelo\n\n");
                    dem.modelaje(nombre);
                    System.out.println("Desea agregar otro modelo? (Si/No)");
                    sel = input.nextLine();
                    if(sel.equalsIgnoreCase("no")){
                        break;}

                }while(true);

                break;
            }

            if(sel.equalsIgnoreCase("2")){
                System.out.println("Proyecto creado con opcion 2");


                File theDir = new File(path+"/" + nombre + "/src/main/java/org/proyecto/Entity/");
                if (!theDir.exists()) theDir.mkdirs();
                do {
                    System.out.println("\n\nCreacion de modelo\n\n");
                    dem.modelaje(nombre);
                    System.out.println("Desea agregar otro modelo? (Si/No)");
                    sel = input.nextLine();
                    if(sel.equalsIgnoreCase("no")){
                        break;}

                }while(true);
                break;
            }

        }
        while (!sel.equalsIgnoreCase("Salir"));



//      mueve el trabajo realizado de target a la carpeta principal

        File theDir = new File(userHome+"/Documents/DemoPicocli/"+nombre);
        if (!theDir.exists()) theDir.mkdirs();
        File from = new File(path+"/" + nombre);
        File to = new File(userHome+"/Documents/DemoPicocli/"+nombre);

        try {
            Files.move(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Api creada con exito.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //System.exit(0);

    }
}


