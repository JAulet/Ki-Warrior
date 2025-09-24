package com.bugboy.kiwarrior.networking.packets;

import com.bugboy.kiwarrior.client.ClientKiData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KiS2CSyncPacket {

    private final int ki;
    private final int maxKi;

    public KiS2CSyncPacket(int ki, int maxKi) {
        this.ki = ki;
        this.maxKi = maxKi;
    }

    public KiS2CSyncPacket(FriendlyByteBuf buf) {
        this.ki = buf.readInt();
        this.maxKi = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(ki);
        buf.writeInt(maxKi);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            ClientKiData.set(ki, maxKi);
        });
        return true;
    }
}
