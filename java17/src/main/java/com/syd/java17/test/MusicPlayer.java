package com.syd.java17.test;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MusicPlayer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PlayerThread playerThread = null;
        while (scanner.hasNext()) {
            try {
                int key = scanner.nextInt();
                if (playerThread != null && playerThread.isAlive()) {
                    playerThread.stop();
                }
                if (key == 1 || key == 2 || key == 3) {
                    playerThread = new PlayerThread();
                    playerThread.setPlayer(key);
                }
            } catch (InputMismatchException e) {
                e.printStackTrace();
                scanner.next();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
