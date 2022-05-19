package io.github.andyroiiid.gurume

import io.github.andyroiiid.gurume.block.RiceCropBlock
import net.fabricmc.api.ModInitializer
import net.minecraft.block.*
import net.minecraft.item.*
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager

object Gurume : ModInitializer {
    private const val MOD_ID = "gurume"
    private val Logger = LogManager.getLogger(MOD_ID)

    val RICE_CROP_BLOCK = RiceCropBlock(
        AbstractBlock.Settings
            .of(Material.PLANT)
            .nonOpaque()
            .noCollision()
            .ticksRandomly()
            .breakInstantly()
            .sounds(BlockSoundGroup.CROP)
    )

    val RICE_SEEDS_ITEM = AliasedBlockItem(
        RICE_CROP_BLOCK,
        Item.Settings()
            .group(ItemGroup.MISC)
    )

    private val RICE_ITEM = Item(
        Item.Settings()
            .group(ItemGroup.MISC)
    )

    private val DEFAULT_FOOD_COMPONENT = FoodComponent.Builder().hunger(5).saturationModifier(0.6F).build()

    private val COOKED_RICE_ITEM = Item(
        Item.Settings()
            .food(DEFAULT_FOOD_COMPONENT)
            .group(ItemGroup.FOOD)
    )

    override fun onInitialize() {
        Logger.info("Registering blocks...")

        Registry.register(Registry.BLOCK, Identifier("gurume", "rice_crop"), RICE_CROP_BLOCK)

        Logger.info("Registering items...")

        Registry.register(Registry.ITEM, Identifier("gurume", "rice_seeds"), RICE_SEEDS_ITEM)
        Registry.register(Registry.ITEM, Identifier("gurume", "rice"), RICE_ITEM)
        Registry.register(Registry.ITEM, Identifier("gurume", "cooked_rice"), COOKED_RICE_ITEM)

        Logger.info("Registering compostable items...")

        registerCompostableItem(0.3f, RICE_SEEDS_ITEM)
        registerCompostableItem(0.65f, RICE_ITEM)
        registerCompostableItem(1.0f, COOKED_RICE_ITEM)
    }

    private fun registerCompostableItem(levelIncreaseChance: Float, item: Item) {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item, levelIncreaseChance)
    }
}