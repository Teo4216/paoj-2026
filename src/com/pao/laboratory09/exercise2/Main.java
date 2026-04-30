package com.pao.laboratory09.exercise2;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex2.bin";
    private static final int RECORD_SIZE = 32;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        File outFile = new File(OUTPUT_FILE);
        outFile.getParentFile().mkdirs();

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile))) {
            for (int i = 0; i < n; i++) {
                int id = scanner.nextInt();
                double suma = Double.parseDouble(scanner.next());
                String data = scanner.next();
                String tipStr = scanner.next();

                byte[] idBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array();
                dos.write(idBytes);

                byte[] sumaBytes = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(suma).array();
                dos.write(sumaBytes);

                String dataPadded = String.format("%-10s", data);
                dos.write(dataPadded.getBytes(StandardCharsets.US_ASCII));

                byte tip = (byte) (tipStr.equalsIgnoreCase("CREDIT") ? 0 : 1);
                dos.writeByte(tip);

                dos.writeByte(0);

                dos.write(new byte[8]);
            }
        }

        String[] statuses = {"PENDING", "PROCESSED", "REJECTED"};
        String[] types = {"CREDIT", "DEBIT"};

        try (RandomAccessFile raf = new RandomAccessFile(outFile, "rw")) {
            while (scanner.hasNext()) {
                String cmd = scanner.next();

                if (cmd.equals("READ")) {
                    int idx = scanner.nextInt();
                    printRecord(raf, idx, statuses, types);
                } else if (cmd.equals("UPDATE")) {
                    int idx = scanner.nextInt();
                    int st = scanner.nextInt();
                    raf.seek((long) idx * RECORD_SIZE + 23);
                    raf.writeByte(st);
                    System.out.println("Updated [" + idx + "]: " + statuses[st]);
                } else if (cmd.equals("PRINT_ALL")) {
                    long count = raf.length() / RECORD_SIZE;
                    for (int i = 0; i < count; i++) {
                        printRecord(raf, i, statuses, types);
                    }
                }
            }
        }
        scanner.close();
    }

    private static void printRecord(RandomAccessFile raf, int idx, String[] statuses, String[] types) throws Exception {
        if ((long) idx * RECORD_SIZE >= raf.length()) {
            return;
        }
        raf.seek((long) idx * RECORD_SIZE);

        byte[] idBytes = new byte[4];
        raf.readFully(idBytes);
        int id = ByteBuffer.wrap(idBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();

        byte[] sumaBytes = new byte[8];
        raf.readFully(sumaBytes);
        double suma = ByteBuffer.wrap(sumaBytes).order(ByteOrder.LITTLE_ENDIAN).getDouble();

        byte[] dataBytes = new byte[10];
        raf.readFully(dataBytes);
        String data = new String(dataBytes, StandardCharsets.US_ASCII).trim();

        byte tipIdx = raf.readByte();
        String tip = types[tipIdx];

        byte statusIdx = raf.readByte();
        String status = statuses[statusIdx];

        System.out.printf(Locale.US, "[%d] id=%d data=%s tip=%s suma=%.2f RON status=%s\n",
                idx, id, data, tip, suma, status);
    }
}