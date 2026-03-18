package com.pao.laboratory02.exercise4.service;

import com.pao.laboratory02.exercise4.model.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviciu Singleton care gestionează animalele din grădina zoologică.
 */
public class ZooService {

    private List<Animal> animals;

    private ZooService() {
        this.animals = new ArrayList<>();
    }

    private static class Holder {
        private static final ZooService INSTANCE = new ZooService();
    }

    public static ZooService getInstance() {
        return Holder.INSTANCE;
    }
    // === Sfârșit Singleton ===

    /**
     * Adaugă un animal în listă.
     */
    public void addAnimal(Animal a) {
        animals.add(a);
        System.out.println("Adăugat: " + a);
    }

    /**
     * Afișează toate animalele cu describe().
     */
    public void listAll() {
        if (animals.isEmpty()) {
            System.out.println("Grădina zoologică este goală.");
        } else {
            for (int i = 0; i < animals.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + animals.get(i).describe());
            }
        }
    }

    /**
     * Afișează doar animalele de un anumit tip.
     */
    public void listByType(String type) {
        boolean found = false;

        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equals(type)) {
                System.out.println("  - " + animal.describe());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Nu există animale de tipul: " + type);
        }
    }

    /**
     * Găsește și afișează cel mai bătrân animal.
     */
    public void findOldest() {
        if (animals.isEmpty()) {
            System.out.println("Grădina zoologică este goală.");
        } else {
            Animal oldest = animals.get(0);

            for (int i = 1; i < animals.size(); i++) {
                if (animals.get(i).getAge() > oldest.getAge()) {
                    oldest = animals.get(i);
                }
            }

            System.out.println("Cel mai bătrân animal: " + oldest.describe());
        }
    }
}