package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.blocks.*;
import com.barribob.MaelstromMod.blocks.key_blocks.BlockKey;
import com.barribob.MaelstromMod.blocks.portal.*;
import com.barribob.MaelstromMod.entity.util.EntityAzurePortalSpawn;
import com.barribob.MaelstromMod.entity.util.EntityCliffPortalSpawn;
import com.barribob.MaelstromMod.entity.util.EntityCrimsonTowerSpawner;
import com.barribob.MaelstromMod.items.ItemBlockvoid;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenAzureTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenBigPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenPlumTree;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenSwampTree;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all of our new blocks
 */
public class ModBlocks {
    public static final List<Block> BLOCKS = new ArrayList<Block>();
    public static final float STONE_HARDNESS = 1.7f;
    public static final float STONE_RESISTANCE = 10f;
    public static final float BRICK_HARDNESS = 2.0f;
    public static final float WOOD_HARDNESS = 1.5f;
    public static final float WOOD_RESISTANCE = 5.0f;
    public static final float PLANTS_HARDNESS = 0.2f;
    public static final float PLANTS_RESISTANCE = 2.0f;
    public static final float ORE_HARDNESS = 3.0F;
    public static final float OBSIDIAN_HARDNESS = 50;
    public static final float OBSIDIAN_RESISTANCE = 2000;

    public static final Block MEGA_STRUCTURE_BLOCK = new BlockMegaStructure("mega_structure_block");
    public static final Block LIGHTING_UPDATER = new BlockLightingUpdater("lighting_updater", Material.AIR).setLightLevel(0.1f);
    public static final Block DISAPPEARING_SPAWNER = new BlockDisappearingSpawner("disappearing_spawner", Material.ROCK);
    public static final Block BOSS_SPAWNER = new BlockBossSpawner("nexus_herobrine_spawner");

    public static final Block MAELSTROM_CORE = new BlockMaelstromCore("maelstrom_core_block", Material.ROCK, ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, ModItems.MAELSTROM_FRAGMENT);
    public static final Block AZURE_MAELSTROM_CORE = new BlockMaelstromCore("azure_maelstrom_core", Material.ROCK, ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, ModItems.AZURE_MAELSTROM_FRAGMENT);
    public static final Block CLIFF_MAELSTROM_CORE = new BlockMaelstromCore("cliff_maelstrom_core", Material.ROCK, ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, ModItems.GOLDEN_MAELSTROM_FRAGMENT);
    public static final Block AZURE_MAELSTROM = new BlockMaelstrom("azure_maelstrom", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, 1).setLightLevel(0.5f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DECAYING_MAELSTROM = new BlockDecayingMaelstrom("azure_decaying_maelstrom", STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE, 1).setLightLevel(0.5f);
    public static final Block MAELSTROM_HEART = new BlockMaelstromHeart("maelstrom_heart", Material.ROCK, OBSIDIAN_HARDNESS, OBSIDIAN_RESISTANCE, SoundType.STONE).setLightLevel(0.5f).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block NEXUS_TELEPORTER = new BlockNexusTeleporter("nexus_teleporter", Material.ROCK, SoundType.STONE).setLightLevel(1.0f);
    public static final Block NEXUS_PORTAL = new BlockNexusPortal("nexus_portal").setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_NEXUS_PORTAL = new BlockDarkNexusPortal("dark_nexus_portal").setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_PORTAL = new BlockAzurePortal("azure_portal").setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CLIFF_PORTAL = new BlockCliffPortal("cliff_portal").setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRIMSON_PORTAL = new BlockCrimsonPortal("crimson_portal").setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block BLACK_SKY = new BlockBase("black_sky", Material.AIR).setBlockUnbreakable();

    /**
     * Stone and ore
     */

    public static final Block DARK_AZURE_STONE = new BlockBase("dark_azure_stone", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_1 = new BlockBase("azure_stone_1", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_2 = new BlockBase("azure_stone_2", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_3 = new BlockBase("azure_stone_3", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_4 = new BlockBase("azure_stone_4", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DARK_AZURE_STONE_5 = new BlockBase("azure_stone_5", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block LIGHT_AZURE_STONE = new BlockLamp("light_azure_stone", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS).setLightLevel(1.0f);
    public static final Block AZURE_COAL_ORE = new BlockAzureOre("azure_coal_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_DIAMOND_ORE = new BlockAzureOre("azure_diamond_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_EMERALD_ORE = new BlockAzureOre("azure_emerald_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_LAPIS_ORE = new BlockAzureOre("azure_lapis_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_IRON_ORE = new BlockAzureOre("azure_iron_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_GOLD_ORE = new BlockAzureOre("azure_gold_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_REDSTONE_ORE = new BlockAzureRedstoneOre("azure_redstone_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_LIT_REDSTONE_ORE = new BlockAzureRedstoneOre("azure_lit_redstone_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setLightLevel(0.3f);
    public static final Block CHASMIUM_ORE = new BlockAzureOre("chasmium_ore", ORE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CLIFF_STONE = new BlockBase("cliff_stone", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block RED_CLIFF_STONE = new BlockBase("red_cliff_stone", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);

    /**
     * Organic blocks
     */

    public static final Block AZURE_GRASS = new BlockAzureGrass("azure_grass", Material.GRASS, 0.5f, PLANTS_RESISTANCE, SoundType.GROUND).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_VINES_BLOCK = new BlockAzureVinesBlock("azure_vines_block", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_VINES = new BlockAzureVines("azure_vines", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BLUE_DAISY = new BlockAzureFlower("blue_daisy", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block RUBY_ORCHID = new BlockAzureFlower("ruby_orchid", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BROWNED_GRASS = new BlockModTallGrass("browned_grass", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block DOUBLE_BROWNED_GRASS = new BlockDoubleBrownedGrass("double_browned_grass", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block FIRE_GRASS = new BlockModTallGrass("fire_grass", Material.PLANTS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS).setLightLevel(0.5f);

    public static final Block AZURE_LOG = new BlockLogBase("azure_log", WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block PLUM_LOG = new BlockLogBase("plum_log", WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setLightLevel(0.3f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_LOG = new BlockLogBase("swamp_log", WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block FULL_SWAMP_LOG = new BlockFullLog("full_swamp_log", SWAMP_LOG).setHardness(WOOD_HARDNESS).setResistance(WOOD_RESISTANCE).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block AZURE_LEAVES = new BlockAzureLeaves("azure_leaves", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block PLUM_LEAVES = new BlockPlumLeaves("plum_leaves", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block PLUM_FILLED_LEAVES = new BlockPlumFilledLeaves("plum_filled_leaves", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_LEAVES = new BlockSwampLeaves("swamp_leaves", PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block AZURE_SAPLING = new BlockSaplingBase("azure_sapling", Blocks.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, new WorldGenAzureTree(true)).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block PLUM_SAPLING = new BlockSaplingBase("plum_sapling", Blocks.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, new WorldGenPlumTree(true, true)).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block LARGE_PLUM_SAPLING = new BlockSaplingBase("large_plum_sapling", Blocks.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, new WorldGenBigPlumTree(true)).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_SAPLING = new BlockSaplingBase("swamp_sapling", Blocks.GRASS, PLANTS_HARDNESS, PLANTS_RESISTANCE, SoundType.PLANT, new WorldGenSwampTree(true)).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block AZURE_PLANKS = new BlockBase("azure_planks", Material.WOOD, WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_FENCE = new BlockFenceBase("azure_fence", Material.WOOD, WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_PLANK_STAIRS = new BlockStairsBase("azure_plank_stairs", AZURE_PLANKS.getDefaultState(), WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_PLANKS = new BlockBase("swamp_planks", Material.WOOD, WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_PLANK_STAIRS = new BlockStairsBase("swamp_plank_stairs", SWAMP_PLANKS.getDefaultState(), WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_FENCE = new BlockFenceBase("swamp_fence", Material.WOOD, WOOD_HARDNESS, WOOD_RESISTANCE, SoundType.WOOD).setCreativeTab(ModCreativeTabs.BLOCKS);

    /**
     * Blocks for structures
     */

    public static final Block MAELSTROM_BRICKS = new BlockBase("maelstrom_bricks", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MAELSTROM_BRICK_FENCE = new BlockFenceBase("maelstrom_brick_fence", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MAELSTROM_BRICK_STAIRS = new BlockStairsBase("maelstrom_brick_stairs", MAELSTROM_BRICKS.getDefaultState(), BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block MAELSTROM_STONEBRICK = new BlockBase("maelstrom_stonebrick", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MAELSTROM_STONEBRICK_STAIRS = new BlockStairsBase("maelstrom_stonebrick_stairs", MAELSTROM_STONEBRICK.getDefaultState(), STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MAELSTROM_STONEBRICK_FENCE = new BlockFenceBase("maelstrom_stonebrick_fence", Material.ROCK, STONE_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block AZURE_STONEBRICK = new BlockBase("azure_stonebrick", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_STAIRS = new BlockStairsBase("azure_stonebrick_stairs", AZURE_STONEBRICK.getDefaultState(), BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_CRACKED = new BlockBase("azure_stonebrick_cracked", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_CARVED = new BlockBase("azure_stonebrick_carved", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_CARVED_2 = new BlockBase("azure_stonebrick_carved_2", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_CARVED_3 = new BlockBase("azure_stonebrick_carved_3", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_STONEBRICK_LIT = new BlockLamp("azure_stonebrick_lit", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.BLOCKS);

    public static final Block GOLD_STONE = new BlockBase("gold_stone", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRACKED_GOLD_STONE = new BlockBase("cracked_gold_stone", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block GOLD_STONE_FENCE = new BlockFenceBase("gold_stone_fence", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block GOLD_STONE_STAIRS = new BlockStairsBase("gold_stone_stairs", GOLD_STONE.getDefaultState(), BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BROWNED_PILLAR = new BlockBase("browned_pillar", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block BROWNED_BLOCK = new BlockBase("browned_block", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block GOLD_STONE_LAMP = new BlockLamp("gold_stone_lamp", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CHISELED_CLIFF_STONE = new BlockBase("chiseled_cliff_stone", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block SWAMP_BRICK = new BlockBase("swamp_brick", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRACKED_SWAMP_BRICK = new BlockBase("cracked_swamp_brick", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    //SLABS
    public static final Block GOLD_STONE_SLAB = new BlockSlabBase("gold_stone_slab", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRIMSON_MAELSTROM_BRICKS = new BlockBase("crimson_maelstrom_bricks", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRIMSON_MAELSTROM_BRICK_FENCE = new BlockFenceBase("crimson_maelstrom_brick_fence", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRIMSON_MAELSTROM_BRICK_STAIRS =
            new BlockStairsBase("crimson_maelstrom_brick_stairs", CRIMSON_MAELSTROM_BRICKS.getDefaultState(), BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRIMSON_LAMP = new BlockLamp("crimson_lamp", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE, false) {
        @Override
        @SideOnly(Side.CLIENT)
        public BlockRenderLayer getBlockLayer() {
            return BlockRenderLayer.CUTOUT;
        }
    }.setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CHAIN = new BlockChain("chain", Material.IRON, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.METAL, new AxisAlignedBB(0.425D, 0.0D, 0.425D, 0.575D, 1.0D, 0.575D)).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block MULTI_CHAIN = new BlockChain("multi_chain", Material.IRON, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.METAL, new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D)).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block LARGE_CHAIN = new BlockChain("large_chain", Material.IRON, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.METAL, new AxisAlignedBB(0.30D, 0.0D, 0.3D, 0.7D, 1.0D, 0.7D)).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block METAL_LAMP = new BlockLamp("metal_lamp", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE, false).setLightLevel(1.0f).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block FURNACE_BRICKS = new BlockBase("furnace_bricks", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block FURNACE_STAIRS = new BlockStairsBase("furnace_stairs", FURNACE_BRICKS.getDefaultState(), BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRACKED_FURNACE_BRICKS = new BlockBase("cracked_furnace_bricks", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block FURNACE_BRICKS_LIT = new BlockLamp("furnace_bricks_lit", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE, false).setLightLevel(1.0f);

    /*
     * Key blocks and nexus stuff
     */

    public static final Block CRACKED_QUARTZ = new BlockBase("cracked_quartz", Material.ROCK, 0.8f, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block AZURE_KEY_BLOCK = new BlockKey("azure_key_block", ModItems.AZURE_KEY, (world, pos) -> new EntityAzurePortalSpawn(world, pos.getX(), pos.getY(), pos.getZ()));
    public static final Block MAELSTROM_DUNGEON_KEY_BLOCK = new BlockKey("azure_dungeon_key_block");
    public static final Block BROWN_KEY_BLOCK = new BlockKey("brown_key_block", ModItems.BROWN_KEY, (world, pos) -> new EntityCliffPortalSpawn(world, pos.getX(), pos.getY(), pos.getZ()));
    public static final Block RED_DUNGEON_KEY_BLOCK = new BlockKey("red_dungeon_key_block", ModItems.RED_KEY, (world, pos) -> new EntityCrimsonTowerSpawner(world, pos.getX(), pos.getY(), pos.getZ()));
    public static final Block ICE_KEY_BLOCK = new BlockKey("ice_key_block");
    public static final Block ICE_DUNGEON_KEY_BLOCK = new BlockKey("ice_dungeon_key_block");
    public static final Block BLACK_DUNGEON_KEY_BLOCK = new BlockKey("black_dungeon_key_block");

    /*
     * Crimson dimension
     */

    public static final Block FURNACE_PILLAR = new BlockPillarBase("furnace_pillar", Material.ROCK).setHardness(BRICK_HARDNESS).setResistance(STONE_RESISTANCE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block REDSTONE_BRICK = new BlockRedstoneBrick("redstone_brick", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block CRACKED_REDSTONE_BRICK = new BlockRedstoneBrick("cracked_redstone_brick", Material.ROCK, BRICK_HARDNESS, STONE_RESISTANCE, SoundType.STONE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block IRON_GRATE = new BlockGrate("iron_grate", Material.IRON).setHardness(BRICK_HARDNESS).setResistance(STONE_RESISTANCE).setCreativeTab(ModCreativeTabs.BLOCKS);
    public static final Block FAN = new BlockFan("fan", Material.IRON, STONE_HARDNESS, STONE_RESISTANCE, SoundType.METAL);

    // There are technically not blocks, but are in here because they depend on two of the block defined above
    public static final Item STONEBRICK_BLOCKVOID = new ItemBlockvoid("stonebrick_blockvoid", Blocks.STONEBRICK, 30);
    public static final Item OBSIDIAN_BLOCKVOID = new ItemBlockvoid("obsidian_blockvoid", Blocks.OBSIDIAN, 1000);
    public static final Item FURNACE_BRICKS_BLOCKVOID = new ItemBlockvoid("furnace_bricks_blockvoid", ModBlocks.FURNACE_BRICKS, 30);
    public static final Item REDSTONE_BRICK_BLOCKVOID = new ItemBlockvoid("redstone_brick_blockvoid", ModBlocks.REDSTONE_BRICK, 30);
}
