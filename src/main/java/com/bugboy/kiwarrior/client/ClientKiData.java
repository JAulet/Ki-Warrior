package com.bugboy.kiwarrior.client;

public class ClientKiData {
    private static int playerKi;
    private static int playerMaxKi;

    public static void set(int ki, int maxKi) {
        ClientKiData.playerKi = ki;
        ClientKiData.playerMaxKi = maxKi;
    }

    public static int getPlayerKi() {
        return playerKi;
    }

    public static int getPlayerMaxKi() {
        return playerMaxKi;
    }
}
