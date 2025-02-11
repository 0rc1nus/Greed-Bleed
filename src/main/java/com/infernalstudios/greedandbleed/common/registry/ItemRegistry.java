package com.infernalstudios.greedandbleed.common.registry;

import com.infernalstudios.greedandbleed.GreedAndBleed;
import com.infernalstudios.greedandbleed.common.item.GBOnAStickItem;
import com.infernalstudios.greedandbleed.common.item.HoglinArmorItem;
import com.infernalstudios.greedandbleed.common.item.HoglinSaddleItem;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GreedAndBleed.MODID);

    public static final ItemGroup TAB_GREED_AND_BLEED = new ItemGroup( "greedAndBleed") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(HOGLIN_SADDLE.get());
        }
    };

    public static final RegistryObject<Item> CRIMSON_FUNGUS_ON_A_STICK = ITEMS.register(
            "crimson_fungus_on_a_stick", () ->
                    new GBOnAStickItem((
                            new Item.Properties())
                            .durability(100)
                            .tab(TAB_GREED_AND_BLEED),
                            EntityType.HOGLIN,
                            1)
    );
    public static final RegistryObject<Item> GOLDEN_HOGLIN_ARMOR = ITEMS.register(
            "golden_hoglin_armor", () ->
                    new HoglinArmorItem(7, "gold",
                            (new Item.Properties())
                            .stacksTo(1)
                            .tab(TAB_GREED_AND_BLEED))
    );
    public static final RegistryObject<Item> NETHERITE_HOGLIN_ARMOR = ITEMS.register(
            "netherite_hoglin_armor", () ->
                    new HoglinArmorItem(11, "netherite",
                            (new Item.Properties())
                            .stacksTo(1)
                            .tab(TAB_GREED_AND_BLEED))
    );

    public static final RegistryObject<Item> HOGLIN_SADDLE = ITEMS.register(
            "hoglin_saddle", () ->
                    new HoglinSaddleItem(
                            (new Item.Properties())
                                    .stacksTo(1)
                                    .tab(TAB_GREED_AND_BLEED))
    );

    // COPIED FROM INFERNAL EXPANSION
    public static final RegistryObject<Item> RAW_HOGCHOP = ITEMS.register("raw_hogchop", () -> new Item(new Item.Properties().tab(TAB_GREED_AND_BLEED)
            .food(new Food.Builder().nutrition(4).saturationMod(0.3F).meat().build())));

    public static final RegistryObject<Item> COOKED_HOGCHOP = ITEMS.register("cooked_hogchop", () -> new Item(new Item.Properties().tab(TAB_GREED_AND_BLEED)
            .food(new Food.Builder().nutrition(10).saturationMod(0.8F).meat().build())));
}
