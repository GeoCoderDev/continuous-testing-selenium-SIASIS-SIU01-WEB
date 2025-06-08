    package org.example;

    import net.datafaker.Faker;

    public class Main {
        public static void main(String[] args) {
            System.out.println("Hello, World!");

            final var dataFaker = new Faker();

            final var nombreFaker = dataFaker.name().firstName();
            final var apellido = dataFaker.name().lastName();
            final var correo = dataFaker.internet().emailAddress();
            final var edad = dataFaker.number().numberBetween(20,50);
            final var peso = dataFaker.number().randomDouble(3,50,100);
            final var estadoCivil = dataFaker.bool().bool();
            final var frase = dataFaker.lorem().words(5);
            System.out.println(nombreFaker + " " + apellido + " "
                    + correo + " " + edad + " "+ peso + " " + estadoCivil + frase);
        }
    }