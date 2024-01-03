package me.Minestor.frogvasion.villager;

import com.google.common.collect.ImmutableSet;
import me.Minestor.frogvasion.Frogvasion;
import me.Minestor.frogvasion.blocks.ModBlocks;
import me.Minestor.frogvasion.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> HERPETOLOGIST_POI_KEY = registerPOIKey("herppoi");
    public static final PointOfInterestType HERPETOLOGIST_POI = registerPOI("herppoi", ModBlocks.CONVERSION_PEDESTAL);
    public static final VillagerProfession HERPETOLOGIST_PROFESSION = registerProfession("herpetologist", HERPETOLOGIST_POI_KEY);

    private static RegistryKey<PointOfInterestType> registerPOIKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(Frogvasion.MOD_ID, name));
    }
    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(Frogvasion.MOD_ID, name), 1,1, block);
    }
    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(Frogvasion.MOD_ID, name),
                new VillagerProfession(name, entry -> true, entry -> entry.matchesKey(type), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN));
    }

    public static void register() {
        registerTrades();
    }
    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(HERPETOLOGIST_PROFESSION,1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 1),
                            new ItemStack(ModItems.GRAPPLING_TONGUE, 1), 6, 2, 0.08f
                    ));
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.ENHANCED_MUTAGEN, 2),
                            new ItemStack(Items.EMERALD, 1), 4,2, 0.08f
                    )));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 3),
                            new ItemStack(ModItems.HERPETOLOGIST_JAR, 1), 6, 4, 0.02f
                    ));
                }
        );
        TradeOfferHelper.registerVillagerOffers(HERPETOLOGIST_PROFESSION,2,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 2),
                            new ItemStack(ModItems.FROGVASIUM_INGOT, 1), 6, 5, 0.06f
                    ));
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.FROG_HIDE, 4),
                            new ItemStack(Items.EMERALD, 1), 7,6, 0.1f
                    )));
                }
        );
        TradeOfferHelper.registerVillagerOffers(HERPETOLOGIST_PROFESSION,3,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10 + random.nextBetween(1, 10)),
                            new ItemStack(ModItems.FROG_STAFF, 1), 2, 10, 0.08f
                    ));
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10 + random.nextBetween(1, 10)),
                            new ItemStack(ModItems.JUMPY_TOTEM, 1), 3,7, 0.02f
                    )));
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 7 + random.nextBetween(1, 10)),
                            new ItemStack(ModItems.FROG_HELMET_ITEM, 1), 3,7, 0.05f
                    )));
                }
        );
        TradeOfferHelper.registerVillagerOffers(HERPETOLOGIST_PROFESSION,4,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.EMPTY_FROG_GHOST),
                            new ItemStack(ModItems.GROWING_FROG_GHOST), 1, 30, 0.08f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.EMPTY_FROG_GHOST),
                            new ItemStack(ModItems.SOLDIER_FROG_GHOST), 1, 30, 0.08f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.EMPTY_FROG_GHOST),
                            new ItemStack(ModItems.ARMED_FROG_GHOST), 1, 30, 0.08f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.EMPTY_FROG_GHOST),
                            new ItemStack(ModItems.GRAPPLING_FROG_GHOST), 1, 30, 0.08f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.EMPTY_FROG_GHOST),
                            new ItemStack(ModItems.ENDER_FROG_GHOST), 1, 30, 0.08f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.EMPTY_FROG_GHOST),
                            new ItemStack(ModItems.EXPLOSIVE_FROG_GHOST), 1, 30, 0.08f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 10),
                            new ItemStack(ModItems.EMPTY_FROG_GHOST),
                            new ItemStack(ModItems.ICE_FROG_GHOST), 1, 30, 0.08f
                    ));
                }
        );
        TradeOfferHelper.registerVillagerOffers(HERPETOLOGIST_PROFESSION,5,
                factories -> factories.add((entity, random) -> new TradeOffer(
                        new ItemStack(Items.EMERALD, 25),
                        new ItemStack(ModItems.EMPTY_FROG_GHOST),
                        new ItemStack(ModItems.BOSS_SOLDIER_FROG_GHOST), 1, 30, 0.08f
                ))
        );

    }
}
