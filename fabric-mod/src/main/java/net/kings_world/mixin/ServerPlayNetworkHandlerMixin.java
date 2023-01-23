package net.kings_world.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.kings_world.KingsWorld.modConfig;
import static net.kings_world.Utils.publishViaPlayer;
import static net.kings_world.Utils.HashReplacer;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At("TAIL"), method = "onDisconnected")
    private void onPlayerLeave(Text reason, CallbackInfo info) {
        HashReplacer playerLeave = new HashReplacer();
        playerLeave.put("name", player.getEntityName());

        publishViaPlayer(player, modConfig.playerLeave, playerLeave);
    }
}
