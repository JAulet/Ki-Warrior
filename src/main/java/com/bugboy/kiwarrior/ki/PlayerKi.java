package com.bugboy.kiwarrior.ki;

import net.minecraft.nbt.CompoundTag;

public class PlayerKi {
    private int ki;
    private int maxKi;
    private final int MIN_KI = 0;
    private int kiRegen;

    public int getKi() {
        return ki;
    }

    public void addKi(int add) {
        this.ki = Math.max(0, Math.min(maxKi, ki + add));
    }

    public void subKi(int sub){
        this.ki = Math.max(MIN_KI, ki - sub);
    }

    public void setKi(int ki) { this.ki = ki; }

    public int getMaxKi() {
        return maxKi;
    }

    public void addMaxKi(int add) {
        this.maxKi += add;
    }

    public void subMaxKi(int sub){
        this.ki = Math.min(ki, maxKi);
        this.maxKi = Math.max(MIN_KI, maxKi-sub);
    }

    public void setMaxKi(int maxKi) {
        this.maxKi = maxKi;
    }

    public int getKiRegen() {
        return kiRegen;
    }

    public void addKiRegen(int add) {
        this.kiRegen += add;
    }

    public void subKiRegen(int sub) { this.kiRegen -= sub; }

    public void setKiRegen(int kiRegen) {
        this.kiRegen = kiRegen;
    }

    public void copyFrom(PlayerKi source){
        this.ki = source.ki;
        this.maxKi = source.maxKi;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("ki", ki);
        nbt.putInt("maxKi", maxKi);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.ki = nbt.getInt("ki");
        this.maxKi = nbt.getInt("maxKi");
    }
}
