package com.syd.java17.test;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PlayerThread extends Thread {
    private static final ClassLoader LOADER = PlayerThread.class.getClassLoader();
    private static Player player;

    public PlayerThread() {

    }

    public void setPlayer(Integer key) throws Exception {
        switch (key) {
            case 1:
                InputStream refrain = LOADER.getResourceAsStream("Refrain.mp3");
                if (refrain != null) {
                    player = new Player(refrain);
                    this.start();
                } else {
                    throw new FileNotFoundException("Refrain.mp3 not found");
                }
                break;
            case 2:
                InputStream sakura = LOADER.getResourceAsStream("sakura.mp3");
                if (sakura != null) {
                    player = new Player(sakura);
                    this.start();
                } else {
                    throw new FileNotFoundException("sakura.mp3 not found");
                }
                break;
            case 3:
                InputStream nextToYou = LOADER.getResourceAsStream("nextToYou.mp3");
                if (nextToYou != null) {
                    player = new Player(nextToYou);
                    this.start();
                } else {
                    throw new FileNotFoundException("nextToYou.mp3 not found");
                }
                break;

        }
    }

    @Override
    public void run() {
        super.run();
        try {
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
