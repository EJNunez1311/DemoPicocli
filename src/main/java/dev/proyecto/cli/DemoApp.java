package dev.proyecto.cli;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;
import java.io.*;
import java.lang.*;
import java.io.File;
import java.util.Scanner;

@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = {Comando1.class, Comando2.class, test.class},
        version= "version 1.0",
        footerHeading = "2020 - Demo\n",
        headerHeading = "Proyecto Framework Tool Set\n",
        description = "App que hace varias cosas"
)
public class DemoApp {


    public static String menu() {

        String selection;
        Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("Que tipo de aplicaicon desea hacer?");
        System.out.println("1 - Api-server con modelo usuario autenticacion");
        System.out.println("2 - Api-server sin ningun tipo de configuracion");
        System.out.println("Salir");
        selection = input.nextLine();
        return selection;
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

@CommandLine.Command(name = "test", description = "test"
)
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


        do{
            sel = dem.menu();
            System.out.println("Su seleccion es: "+ sel);

            if(sel.equalsIgnoreCase("1")){
                System.out.println("Opcion 1");
                break;
            }
            if(sel.equalsIgnoreCase("2")){
                System.out.println("Opcion 2");
                break;
            }

        }
        while (!sel.equalsIgnoreCase("Salir"));

        String comandos;

        comandos = "cd .. && mvn io.quarkus:quarkus-maven-plugin:1.6.1.Final:create -DprojectGroupId=org.proyecto " +
                "-DprojectArtifactId="+ nombre +
                " -DclassName=\"org.proyecto.Apiapp\" -Dpath=\"/hello\"\n" +
                "\n" + "cd "+ nombre +
                "\n" +
                "mvn quarkus:add-extension -Dextensions=\"agroal\" \n" +
                "\n" +
                "mvn quarkus:add-extension -Dextensions=\"quarkus-hibernate-orm-panache\" \n" +
                "\n" +
                "mvn quarkus:add-extension -Dextensions=\"jdbc-mysql\" ";

        System.out.println(comandos);

        ProcessBuilder processBuilder = new ProcessBuilder();

// -- Linux --

// Run a shell command
//        processBuilder.command("bash", "-c", comandos);

// Run a shell script
//processBuilder.command("path/to/hello.sh");

// -- Windows --

// Run a command
//processBuilder.command("cmd.exe", "/c", comandos);

// Run a bat file
//processBuilder.command("C:\\Users\\mkyong\\hello.bat");

        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



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
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
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
