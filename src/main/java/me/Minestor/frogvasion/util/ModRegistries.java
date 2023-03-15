package me.Minestor.frogvasion.util;

import com.google.common.collect.Sets;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.world.biome.Biome;

import java.util.Set;


public class ModRegistries {

    public static class AnyOwnerBiome implements RegistryEntryOwner<Biome> {
        private final Set<RegistryEntryOwner<?>> owners = Sets.newIdentityHashSet();

        public AnyOwnerBiome() {
        }

        public boolean ownerEquals(RegistryEntryOwner<Biome> other) {
            return this.owners.contains(other);
        }

        public void addOwner(RegistryEntryOwner<?> owner) {
            this.owners.add(owner);
        }
    }
}
