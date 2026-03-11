package net.classic_akk.jaw_lab.Content.Blocks.BlockEntities.Util;

import net.minecraft.util.StringRepresentable;

public enum DoorState implements StringRepresentable {
    OPENED,
    CLOSED,
    ERROR;

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }
}