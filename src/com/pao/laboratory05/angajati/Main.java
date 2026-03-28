package com.pao.laboratory05.angajati;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AngajatService service = AngajatService.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Gestionare anagajati");
            System.out.println("1. Adauga angajat");
            System.out.println("2. Listare dupa salariu");
            System.out.println("3. Cauta dupa departament");
            System.out.println("0. Iesire");
            System.out.print("Optiune: ");

            int optiune = scanner.nextInt();
            scanner.nextLine(); 

            if (optiune == 0) {
                System.out.println("La revedere!");
                break;
            }

            switch (optiune) {
                case 1 -> {
                    System.out.print("Nume: ");
                    String nume = scanner.nextLine();
                    System.out.print("Departament (nume): ");
                    String numeDept = scanner.nextLine();
                    System.out.print("Departament (locatie): ");
                    String locatieDept = scanner.nextLine();
                    System.out.print("Salariu: ");
                    double salariu = scanner.nextDouble();
                    
                    service.addAngajat(new Angajat(nume, new Departament(numeDept, locatieDept), salariu));
                }
                case 2 -> service.listBySalary();
                case 3 -> {
                    System.out.print("Departament: ");
                    String dept = scanner.nextLine();
                    service.findByDepartament(dept);
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
        scanner.close();
    }
}