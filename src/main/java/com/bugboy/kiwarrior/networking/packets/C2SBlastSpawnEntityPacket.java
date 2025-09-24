package com.bugboy.kiwarrior.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SBlastSpawnEntityPacket {
    private final BlockPos position;
    private final Vec3 angle;
    public C2SBlastSpawnEntityPacket(BlockPos position, Vec3 angle) {
        this.position = position;
        this.angle = angle;
    }
    public C2SBlastSpawnEntityPacket(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(),new Vec3(buffer.readVector3f()));
    }
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.position);
        buffer.writeVector3f(this.angle.toVector3f());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();
            LargeFireball largefireball = new LargeFireball(level, player, this.angle.x, this.angle.y, this.angle.z, 10);
            largefireball.setPos(position.getX(),position.getY(),position.getZ());
            level.addFreshEntity(largefireball);
        });


    }
}
