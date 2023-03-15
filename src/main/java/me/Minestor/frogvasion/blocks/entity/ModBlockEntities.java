package me.Minestor.frogvasion.blocks.entity;

import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<ConversionPedestalBlockEntity> CONVERSION_PEDESTAL_TYPE;
    public static BlockEntityType<FrogvasiumAttackerBlockEntity> FROGVASIUM_ATTACKER_TYPE;
    public static BlockEntityType<FrogvasiumDemolisherBlockEntity> FROGVASIUM_DEMOLISHER_TYPE;
    public static BlockEntityType<FrogvasiumGrapplerBlockEntity> FROGVASIUM_GRAPPLER_TYPE;
    public static BlockEntityType<FrogTrapBlockEntity> FROG_TRAP_TYPE;
    public static BlockEntityType<FrogCageBlockEntity> FROG_CAGE_TYPE;

    public static void registerBlockEntities() {
        CONVERSION_PEDESTAL_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "conversion_pedestal"),
                FabricBlockEntityTypeBuilder.create(ConversionPedestalBlockEntity::new, ModBlocks.CONVERSION_PEDESTAL).build(null));
        FROGVASIUM_ATTACKER_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "frogvasium_attacker"),
                FabricBlockEntityTypeBuilder.create(FrogvasiumAttackerBlockEntity::new, ModBlocks.FROGVASIUM_ATTACKER).build(null));
        FROGVASIUM_DEMOLISHER_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "frogvasium_demolisher"),
                FabricBlockEntityTypeBuilder.create(FrogvasiumDemolisherBlockEntity::new, ModBlocks.FROGVASIUM_DEMOLISHER).build(null));
        FROGVASIUM_GRAPPLER_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "frogvasium_grappler"),
                FabricBlockEntityTypeBuilder.create(FrogvasiumGrapplerBlockEntity::new, ModBlocks.FROGVASIUM_GRAPPLER).build(null));
        FROG_TRAP_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "frog_trap"),
                FabricBlockEntityTypeBuilder.create(FrogTrapBlockEntity::new, ModBlocks.FROG_TRAP).build(null));
        FROG_CAGE_TYPE = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(Frogvasion.MOD_ID, "frog_cage"),
                FabricBlockEntityTypeBuilder.create(FrogCageBlockEntity::new, ModBlocks.FROG_CAGE).build(null));
    }
}
