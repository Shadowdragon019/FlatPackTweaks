package lol.roxxane.flat_pack_tweaks.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.simibubi.create.AllItems;
import lol.roxxane.flat_pack_tweaks.Fpt;
import lol.roxxane.flat_pack_tweaks.jei.InfiniDrillingRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static lol.roxxane.flat_pack_tweaks.FptUtils.new_config;
import static lol.roxxane.flat_pack_tweaks.config.FptConfigUtil.*;

@Mod.EventBusSubscriber(modid = Fpt.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FptConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final FptConfigValue<CommentedConfig, Map<Block, Item>> INFINI_DRILLING_RECIPES =
        new FptConfigValue<>(
            BUILDER.comment("Block to item").define("infini_drilling_recipes",
                new_config("minecraft:raw_iron_block", "minecraft:raw_iron"),
                o -> validate_entries(o, (k, v) ->
                    blocks_exists(k) && validate_string(v, FptConfigUtil::item_exists))),
            o -> parse_entries_to_map(o, FptConfigUtil::get_block, FptConfigUtil::get_item));
    public static final FptConfigValue<String, Item> SUPER_GLUE =
        new FptConfigValue<>(
            BUILDER.define("super_glue", "create:super_glue", FptConfigUtil::item_exists),
            FptConfigUtil::get_item);
    public static final BooleanValue REMOVE_TOOLBOX_RECIPES_FROM_JEI =
        BUILDER.define("remove_toolbox_recipes_from_jei", true);
    public static final BooleanValue WRENCH_CAN_PICKUP_ANYTHING_DESTRUCTIBLE =
        BUILDER.define("wrench_can_pickup_anything_destructible", true);
    public static final BooleanValue ALL_ITEMS_ARE_FIREPROOF =
        BUILDER.define("all_items_are_fireproof", true);
    public static final IntValue HAND_CRANK_ROTATION_SPEED =
        BUILDER.defineInRange("hand_crank_rotation_speed", 32, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final DoubleValue WATER_WHEEL_SPEED_FACTOR =
        BUILDER.comment("By default, water wheels have 8 speed & large water wheels have 4 speed")
            .defineInRange("water_wheel_speed_factor", 1, 0, Double.MAX_VALUE);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void on_load(final ModConfigEvent event) {
        if (SPEC.isLoaded())
            // Invalidate caches
            for (var value : FptConfigValue.VALUES)
                value.cached_value = null;
    }

    // Infini-Drilling
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean can_infini_drill(Block block) {
        return INFINI_DRILLING_RECIPES.get().containsKey(block);
    }
    public static @Nullable Item get_infini_drilling_result(Block block) {
        return INFINI_DRILLING_RECIPES.get().get(block);
    }
    public static List<InfiniDrillingRecipe> get_infini_drill_recipes() {
        return INFINI_DRILLING_RECIPES.get().entrySet().stream().collect(
            // Make a new array list to put the recipes into
	        ArrayList::new,
            // Put the recipe into the array
            (list, entry) ->
                list.add(new InfiniDrillingRecipe(entry.getKey(), entry.getValue())),
            // I don't really get what's going on here
	        ArrayList::addAll);
    }

    // Superglues
    public static Item is_superglue_return_item(Item item) {
        if (item == SUPER_GLUE.get())
            return AllItems.SUPER_GLUE.get(); // success
        else return Items.AIR; // failure
    }
}
