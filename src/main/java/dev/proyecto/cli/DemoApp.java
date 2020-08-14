//package dev.proyecto.cli;
//
//import io.quarkus.picocli.runtime.annotations.TopCommand;
//import picocli.CommandLine;
//import java.io.*;
//import java.lang.*;
//import java.io.File;
//
//@TopCommand
//@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {ExampleResources.Comando1.class, Comando2.class},
//        version= "version 1.0",
//        footerHeading = "2020 - Demo\n",
//        headerHeading = "Proyecto Framework Tool Set\n",
//        description = "App que hace varias cosas"
//)
//public class DemoApp {
//
//}
//
//@CommandLine.Command(name = "comando1", description = "Este comando ejecuta XYZ"
//)
//class Comando1 implements Runnable {
//    @CommandLine.Option(names = {"-n", "--nombre"},
//            description = "Nombre del usuario",
//            required = true
//    )
//    String nombre;
//
//    @CommandLine.Option(names = {"-s", "--sistema"}, description = "Sistema a configurar",
//            defaultValue = "S001"
//    )
//    String sistema;
//
//    @CommandLine.Option(names = {"-c", "--credencial"}, arity = "0..1",
//            description = "Credencial", interactive = true)
//    String credencial;
//
//    @CommandLine.Parameters(index = "0", description = "Archivo de configuración", paramLabel = "Archivo")
//    File archivo;
//
//    @Override
//    public void run() {
//        System.out.println("Mi primer comando [" + nombre + "] sistema [" + sistema + "] Path ["
//                + archivo.toPath() + "]");
//
//    }
//}
//
//
//@CommandLine.Command(name = "comando2", description = "Este comando ejecuta ABC")
//class Comando2 implements Runnable {
//
//    @CommandLine.Option(names = {"-n", "--nombre"},
//            description = "Nombre del usuario",
//            required = true
//    )
//    String nombre;
//
//    @CommandLine.Option(names = {"-att", "--attributo"},
//            description = "Nombre del atributo",
//            required = true)
//    String atributo1;
//
//    @CommandLine.Option(names = {"-tip", "--tipoatt"},
//            description = "Tipo del atributo",
//            required = true)
//    String tipo1;
//
//    @Override
//    public void run() {
//        System.out.println("Mi segundo comando");
//        try {
//            File myObj = new File("C:/Users/Edgar/Documents/DemoPicocli/src/main/java/dev/proyecto/cli/Entity/"+nombre+".java");
//            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//
//        try {
//            FileWriter myWriter = new FileWriter("C:/Users/Edgar/Documents/DemoPicocli/src/main/java/dev/proyecto/cli/Entity/"+nombre+".java");
//            myWriter.write("package dev.proyecto.cli.Entity;\n" +
//                            "\n" +
//                            "import io.quarkus.hibernate.orm.panache.PanacheEntity;\n" +
//                            "\n" +
//                            "import javax.persistence.Entity;\n" +
//                            "\n" +
//                            "@Entity\n" +
//                            "public class " + nombre + " extends PanacheEntity {\n" +
//                            "    public "+ tipo1 +" "+ atributo1 + ";\n"+ "    }\n" +
//                            "\n"
//            );
//            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//
//
//    }
//}
