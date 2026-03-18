package com.pao.laboratory03.enums;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== Toate prioritățile ===");
        for (Priority p : Priority.values()) {
            System.out.println(p.getEmoji() + " " + p.name() +
                    " (level=" + p.getLevel() + ", color=" + p.getColor() + ")");
        }

        System.out.println("\n=== Switch pe prioritate ===");
        Priority myPriority = Priority.HIGH;

        switch (myPriority) {
            case LOW:
            case MEDIUM:
                System.out.println("Totul este sub control.");
                break;
            case HIGH:
                System.out.println("⚠️ Atenție! Prioritate ridicata!");
                break;
            case CRITICAL:
                System.out.println("Alarma! Situație critica!");
                break;
        }

        System.out.println("\n=== valueOf ===");
        Priority parsedPriority = Priority.valueOf("HIGH");
        System.out.println("Priority.valueOf(\"HIGH\") = " + parsedPriority);

        System.out.println("\n=== Comparare enum ===");
        System.out.println("HIGH == HIGH? " + (Priority.HIGH == Priority.HIGH));
        System.out.println("HIGH == LOW? " + (Priority.HIGH == Priority.LOW));

        System.out.println("\n=== name() și ordinal() ===");
        for (Priority p : Priority.values()) {
            System.out.println(p.name() + ": name=" + p.name() + ", ordinal=" + p.ordinal());
        }
    }
}