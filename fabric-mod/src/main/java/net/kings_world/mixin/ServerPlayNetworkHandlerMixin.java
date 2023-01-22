package net.kings_world.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.kings_world.Utils.publishViaPlayer;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At("TAIL"), method = "onDisconnected")
    private void onPlayerLeave(Text reason, CallbackInfo info) {
        publishViaPlayer(player, String.format(
            ":arrow_left: %s has left!",
            player.getEntityName()
        ));
    }
}
