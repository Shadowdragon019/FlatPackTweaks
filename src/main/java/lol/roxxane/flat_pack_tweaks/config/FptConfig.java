package lol.roxxane.flat_pack_tweaks.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.google.common.collect.ImmutableMap;
import com.simibubi.create.AllItems;
import lol.roxxane.flat_pack_tweaks.Fpt;
import lol.roxxane.flat_pack_tweaks.FptUtils;
import lol.roxxane.flat_pack_tweaks.jei.InfiniDrillingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber(modid = Fpt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FptConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<CommentedConfig> INFINI_DRILLING_MAP_VALUE =
        BUILDER.comment("Block to item").define("drilling_map", FptUtils.new_config(
            "raw_iron_block", "raw_iron"
        ), o -> {
            if (o instanceof UnmodifiableConfig config) {
                for (var entry : config.entrySet())
                    if (!blocks_exists(entry.getKey()) ||
                        !(entry.getValue() instanceof String) || !item_exists(entry.getValue()))
                        return false;
                return true;
            }
            return false;
        });
    private static final ForgeConfigSpec.ConfigValue<String> SUPERGLUE_VALUE =
        BUILDER.define("superglue", "create:super_glue", FptConfig::item_exists);

    public static final ForgeConfigSpec.BooleanValue REMOVE_TOOLBOX_RECIPES_FROM_JEI =
        BUILDER.define("remove_toolbox_recipes_from_jei", true);
    public static final ForgeConfigSpec.BooleanValue WRENCH_CAN_PICKUP_ANYTHING_DESTRUCTIBLE =
        BUILDER.define("wrench_can_pickup_anything_destructible", true);
    public static final ForgeConfigSpec.BooleanValue ALL_ITEMS_ARE_FIREPROOF =
        BUILDER.define("all_items_are_fireproof", true);
    public static final ForgeConfigSpec.IntValue HAND_CRANK_ROTATION_SPEED =
        BUILDER.defineInRange("hand_crank_rotation_speed", 32, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue WATER_WHEEL_SPEED_FACTOR =
        BUILDER.comment("By default water wheels have 8 speed & large water wheels have 4 speed")
            .defineInRange("water_wheel_speed_factor", 1, 0, Double.MAX_VALUE);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    private static final HashMap<Block, Item> INFINI_DRILLING_MAP = new HashMap<>();
    private static Item superglue = Items.AIR;

    private static Item get_item(String string) {
        return ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(string));
    }
    private static boolean item_exists(Object object) {
        return object instanceof String string && ForgeRegistries.ITEMS.containsKey(ResourceLocation.parse(string));
    }
    private static Block get_block(String string) {
        return ForgeRegistries.BLOCKS.getValue(ResourceLocation.parse(string));
    }
    private static boolean blocks_exists(Object object) {
        return object instanceof String string && ForgeRegistries.BLOCKS.containsKey(ResourceLocation.parse(string));
    }

    @SubscribeEvent
    static void on_load(final ModConfigEvent event) {
        INFINI_DRILLING_MAP.clear();

        if (SPEC.isLoaded()) {
            INFINI_DRILLING_MAP_VALUE.get().valueMap().forEach((key, value) ->
                INFINI_DRILLING_MAP.put(get_block(key), get_item((String) value)));
            superglue = get_item(SUPERGLUE_VALUE.get());
        }
    }

    // Infini-Drilling
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean can_infini_drill(Block block) {
        return INFINI_DRILLING_MAP.containsKey(block);
    }
    public static @Nullable Item get_infini_drilling_result(Block block) {
        return INFINI_DRILLING_MAP.get(block);
    }
    public static ImmutableMap<Block, Item> get_infini_drilling_map() {
        return ImmutableMap.copyOf(INFINI_DRILLING_MAP);
    }
    public static List<InfiniDrillingRecipe> get_infini_drill_recipes() {
        return INFINI_DRILLING_MAP.entrySet().stream().collect(
            // Make a new array list to put the recipes into
	        ArrayList::new,
            // Put the recipe into the array
            (list, entry) -> list.add(new InfiniDrillingRecipe(entry.getKey(), entry.getValue())),
            // I don't really get what's going on here
	        ArrayList::addAll);
    }

    // Superglues
    public static Item get_superglue() {
        return superglue;
    }
    public static boolean is_superglue(Item item) {
        return get_superglue() == item;
    }
    public static Item is_superglue_return_item(Item item) {
        if (FptConfig.is_superglue(item))
            return AllItems.SUPER_GLUE.get(); // success
        else return Items.AIR; // failure
    }
}
