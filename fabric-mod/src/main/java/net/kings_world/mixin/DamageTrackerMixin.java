package net.kings_world.mixin;

import net.kings_world.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.kings_world.KingsWorld.modConfig;
import static net.kings_world.Utils.publishViaPlayer;
import static net.kings_world.Utils.HashReplacer;

@Mixin(DamageTracker.class)
public class DamageTrackerMixin {
    @Shadow
    @Final
    private LivingEntity entity;

    @Inject(at = @At(value = "RETURN"), method = "getDeathMessage")
    private void onGetDeathMessage(CallbackInfoReturnable<Text> cir) {
        if (!entity.isPlayer()) return;
        HashReplacer playerDeath = new HashReplacer();
        playerDeath.put("message", cir.getReturnValue().getString());

        publishViaPlayer(this.entity, modConfig.playerDeath, playerDeath);
    }
}