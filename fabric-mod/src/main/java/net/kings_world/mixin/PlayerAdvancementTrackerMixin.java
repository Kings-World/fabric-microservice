package net.kings_world.mixin;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.kings_world.KingsWorld.modConfig;
import static net.kings_world.Utils.publishViaPlayer;
import static net.kings_world.Utils.HashReplacer;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {
    @Shadow
    public abstract AdvancementProgress getProgress(Advancement advancement);

    @Shadow
    private ServerPlayerEntity owner;

    @Inject(at = @At(value = "TAIL"), method = "grantCriterion")
    private void onGrantCriterion(Advancement advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
        AdvancementProgress progress = this.getProgress(advancement);
        AdvancementDisplay display = advancement.getDisplay();
        if (display != null && progress.isDone() && display.shouldAnnounceToChat()) {
            HashReplacer playerAdvancement = new HashReplacer();
            playerAdvancement.put("name", this.owner.getEntityName());
            playerAdvancement.put("title", display.getTitle().getString());

            publishViaPlayer(this.owner, modConfig.playerAdvancement, playerAdvancement);
        }
    }
}