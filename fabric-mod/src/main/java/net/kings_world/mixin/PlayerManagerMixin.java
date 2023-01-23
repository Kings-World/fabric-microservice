package net.kings_world.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.kings_world.KingsWorld.modConfig;
import static net.kings_world.Utils.publishViaPlayer;
import static net.kings_world.Utils.HashReplacer;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Inject(at = @At("TAIL"), method = "onPlayerConnect")
    private void onPlayerJoin(ClientConnection connection, ServerPlayerEntity player, CallbackInfo info) {
        HashReplacer playerJoin = new HashReplacer();
        playerJoin.put("name", player.getEntityName());

        publishViaPlayer(player, modConfig.playerJoin, playerJoin);
    }
}
