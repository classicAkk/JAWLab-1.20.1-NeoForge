package net.classic_akk.ca_lab.Content.Items.custom.item.keycards;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KeyCard1 extends Item {
    public KeyCard1(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();

        if (!tag.contains("cLevel")) {
            tag.putInt("cLevel", 0);
        }
        if (!tag.contains("cNetwork")) {
            tag.putString("cNetwork", "none");
        }
        if (!tag.contains("cOwner")) {
            tag.putString("cOwner", "none");
        }
        if (!tag.contains("cUUID")) {
            tag.putString("cUUID", "none");
        }

        return InteractionResultHolder.success(stack);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.lab.key_card1.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}