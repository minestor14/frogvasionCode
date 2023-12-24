package me.Minestor.frogvasion.util.items;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.items.custom.IceSpikeItemEntity;
import me.Minestor.frogvasion.items.custom.OrchidGrenadeItemEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModThrowables {
    public static final EntityType<IceSpikeItemEntity> ICE_SPIKE_ITEM_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Frogvasion.MOD_ID, "ice_spike"),
            FabricEntityTypeBuilder.<IceSpikeItemEntity>create(SpawnGroup.MISC, IceSpikeItemEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build());
    public static final EntityType<OrchidGrenadeItemEntity> ORCHID_GRENADE_ITEM_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Frogvasion.MOD_ID, "orchid_grenade"),
            FabricEntityTypeBuilder.<OrchidGrenadeItemEntity>create(SpawnGroup.MISC, OrchidGrenadeItemEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)).trackRangeBlocks(4).trackedUpdateRate(10).build());
    public static void registerThrowables() {}
}
