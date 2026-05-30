package com.pao.laboratory13.exercise1;

import java.util.Scanner;

public class Main {
    enum State { INIT, AUTH, OPEN, CLOSED }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextLine()) return;

        String firstLine = scanner.nextLine().trim();
        if (firstLine.isEmpty()) return;
        int q = Integer.parseInt(firstLine);

        State state = State.INIT;
        int historyCount = 0;
        int processed = 0;

        while (processed < q && scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }
            processed++;

            String[] tokens = line.trim().split("\\s+");
            String cmd = tokens[0];

            if (!cmd.equals("AUTH") && !cmd.equals("OPEN") && !cmd.equals("SEND") &&
                    !cmd.equals("BROADCAST") && !cmd.equals("HISTORY") && !cmd.equals("CLOSE")) {
                System.out.println("ERR E_PARSE UNKNOWN_COMMAND");
                continue;
            }

            if (cmd.equals("AUTH") && tokens.length < 2) {
                System.out.println("ERR E_PARSE AUTH");
                continue;
            }
            if (cmd.equals("OPEN") && tokens.length > 1) {
                System.out.println("ERR E_PARSE OPEN");
                continue;
            }
            if (cmd.equals("SEND") && tokens.length < 2) {
                System.out.println("ERR E_PARSE SEND");
                continue;
            }
            if (cmd.equals("BROADCAST") && tokens.length < 2) {
                System.out.println("ERR E_PARSE BROADCAST");
                continue;
            }
            if (cmd.equals("HISTORY") && tokens.length > 1) {
                System.out.println("ERR E_PARSE HISTORY");
                continue;
            }
            if (cmd.equals("CLOSE") && tokens.length > 1) {
                System.out.println("ERR E_PARSE CLOSE");
                continue;
            }

            if (state == State.CLOSED) {
                System.out.println("ERR E_STATE CLOSED");
                continue;
            }

            switch (cmd) {
                case "AUTH":
                    state = State.AUTH;
                    historyCount = 0;
                    System.out.println("OK AUTH user=" + tokens[1]);
                    break;

                case "OPEN":
                    if (state == State.OPEN) {
                        System.out.println("ERR E_STATE ALREADY_OPEN");
                    } else if (state == State.INIT) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else {
                        state = State.OPEN;
                        System.out.println("OK OPEN");
                    }
                    break;

                case "SEND":
                    if (state != State.OPEN) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else {
                        historyCount++;
                        System.out.println("OK OPEN sent");
                    }
                    break;

                case "BROADCAST":
                    if (state != State.OPEN) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else {
                        historyCount++;
                        System.out.println("OK OPEN broadcast");
                    }
                    break;

                case "HISTORY":
                    if (state != State.OPEN) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else {
                        System.out.println("OK OPEN history=" + historyCount);
                    }
                    break;

                case "CLOSE":
                    if (state != State.OPEN) {
                        System.out.println("ERR E_STATE NOT_OPEN");
                    } else {
                        state = State.CLOSED;
                        System.out.println("OK CLOSED");
                    }
                    break;
            }
        }
        scanner.close();
    }
}