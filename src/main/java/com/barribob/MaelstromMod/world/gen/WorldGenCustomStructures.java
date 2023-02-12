package com.barribob.MaelstromMod.world.gen;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.entities.*;
import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.BiomeInit;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.world.biome.BiomeCliffSwamp;
import com.barribob.MaelstromMod.world.dimension.crimson_kingdom.WorldGenCrimsonKingdomChunk;
import com.barribob.MaelstromMod.world.dimension.dark_nexus.WorldGenDarkNexus;
import com.barribob.MaelstromMod.world.dimension.nexus.DimensionNexus;
import com.barribob.MaelstromMod.world.gen.cliff.WorldGenCliffLedge;
import com.barribob.MaelstromMod.world.gen.cliff.WorldGenCliffStructureLedge;
import com.barribob.MaelstromMod.world.gen.cliff.WorldGenMaelstromCave;
import com.barribob.MaelstromMod.world.gen.cliff.WorldGenSmallLedge;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenCliffMushroom;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenCliffShrub;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenSwampVines;
import com.barribob.MaelstromMod.world.gen.foliage.WorldGenWaterfall;
import com.barribob.MaelstromMod.world.gen.maelstrom_castle.WorldGenMaelstromCastle;
import com.barribob.MaelstromMod.world.gen.nether_fortress.temple.WorldGenStartTemple;
import com.barribob.MaelstromMod.world.gen.nexus.WorldGenCrimsonTower;
import com.barribob.MaelstromMod.world.gen.nexus.WorldGenNexusIslands;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Keeps track of all the custom structures
 */
public class WorldGenCustomStructures implements IWorldGenerator {
    public static final WorldGenStructure MAELSTROM_CASTLE = new WorldGenMaelstromCastle("maelstrom_castle/maelstrom_castle");


    public static final WorldGenStructure AZURE_HOUSE_1 = new WorldGenStructure("azure/house_1") {
        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
            if (function.startsWith("chest")) {
                worldIn.setBlockToAir(pos);
                pos = pos.down();
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityChest) {
                    ((TileEntityChest) tileentity).setLootTable(LootTableList.CHESTS_VILLAGE_BLACKSMITH, rand.nextLong());
                }
            }
        }

        ;
    };

    public static final WorldGenStructure WITCH_HUT = new WorldGenStructure("cliff/maelstrom_witch_hut") {
        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
            if (function.startsWith("witch")) {
                worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(ModEntities.getID(EntityMaelstromWitch.class), 1, LevelHandler.CLIFF_OVERWORLD, 16);
                }
            }
        }

        ;
    };
    public static final WorldGenStructure CLIFF_TEMPLE = new WorldGenStructure("cliff/swamp_temple") {
        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
            if (function.startsWith("enemy")) {
                worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(ModEntities.getID(EntityCliffGolem.class), 1, LevelHandler.CLIFF_OVERWORLD, 16);
                }
            }
        }

        ;
    };
    private static final WorldGenStructure MAELSTROM_RUINS = new WorldGenStructure("cliff/maelstrom_ruins") {
        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
            if (function.startsWith("enemy")) {
                worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(
                            new MobSpawnData[]{
                                    new MobSpawnData(ModEntities.getID(EntityMaelstromMage.class), new Element[]{Element.NONE, Element.GOLDEN}, new int[]{4, 1}, 1),
                                    new MobSpawnData(ModEntities.getID(EntityShade.class), new Element[]{Element.NONE, Element.GOLDEN}, new int[]{4, 1}, 1),
                            },
                            new int[]{1, 1},
                            3,
                            LevelHandler.CLIFF_OVERWORLD,
                            16);
                }
            }
            if (function.startsWith("chest")) {
                worldIn.setBlockState(pos, Blocks.CHEST.getDefaultState());
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityChest) {
                    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.MAELSTROM_RUINS, rand.nextLong());
                }
            }
        }

        ;
    };
    public static final WorldGenStructure MAELSTROM_PIT = new WorldGenStructure("azure/maelstrom_pit") {
        @Override
        public boolean generate(World worldIn, Random rand, BlockPos position) {
            if (position.getY() < 12) {
                return false;
            }
            return super.generate(worldIn, rand, position.down(4));
        }

        ;

        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
            if (function.startsWith("boss")) {
                worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(ModEntities.getID(EntityIronShade.class), 1, LevelHandler.AZURE_ENDGAME, 16);
                }
            }
        }

        ;
    };
    public static final WorldGenStructure SMALL_LEDGE = new WorldGenCliffLedge("cliff/small_ledge", -5);
    public static final WorldGenStructure MEDIUM_LEDGE = new WorldGenCliffLedge("cliff/medium_ledge", -10);
    public static final WorldGenStructure MAELSTROM_LEDGE = new WorldGenCliffLedge("cliff/maelstrom_ledge", -15) {
        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
            if (function.startsWith("enemy")) {
                worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(new MobSpawnData(ModEntities.getID(EntityGoldenPillar.class), Element.GOLDEN), 2, LevelHandler.CLIFF_OVERWORLD, 16);
                }
            }
            if (function.startsWith("chest")) {
                worldIn.setBlockToAir(pos);
                pos = pos.down();
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityChest) {
                    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.MAELSTROM_RUINS, rand.nextLong());
                }
            }
        }

        ;
    };
    public static final WorldGenStructure CLIFF_RUIN_LEDGE = new WorldGenCliffLedge("cliff/xl_boardwalk", -5) {
        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
            if (function.startsWith("maelstrom")) {
                if (rand.nextInt(3) == 0) {
                    new WorldGenMaelstrom(ModBlocks.DECAYING_MAELSTROM, ModBlocks.CLIFF_MAELSTROM_CORE, (tileEntity) -> tileEntity.getSpawnerBaseLogic().setData(
                            new MobSpawnData[]{
                                    new MobSpawnData(ModEntities.getID(EntityMaelstromMage.class), new Element[]{Element.NONE, Element.GOLDEN}, new int[]{4, 1}, 1),
                                    new MobSpawnData(ModEntities.getID(EntityShade.class), new Element[]{Element.NONE, Element.GOLDEN}, new int[]{4, 1}, 1),
                                    new MobSpawnData(ModEntities.getID(EntityGoldenPillar.class), Element.GOLDEN, 2),
                            },
                            new int[]{1, 1, 1},
                            3,
                            LevelHandler.CLIFF_OVERWORLD,
                            16)).generate(worldIn, rand, pos);
                } else {
                    worldIn.setBlockToAir(pos);
                }
            }
            if (function.startsWith("chest")) {
                worldIn.setBlockToAir(pos);
                if (rand.nextInt(5) == 0) {
                    pos = pos.down();
                    TileEntity tileentity = worldIn.getTileEntity(pos);

                    if (tileentity instanceof TileEntityChest) {
                        ((TileEntityChest) tileentity).setLootTable(LootTableHandler.MAELSTROM_RUINS, rand.nextLong());
                    }
                } else {
                    worldIn.setBlockToAir(pos.down());
                }
            }
        }
    };

    public static final WorldGenStructure MAELSTROM_CAVE = new WorldGenMaelstromCave();

    public static final boolean canLedgeGenerate(World worldIn, BlockPos center) {
        if (center.getY() > 70 && center.getY() < 220 && worldIn.isAirBlock(center.up(5)) && worldIn.isAirBlock(center.up(40))
                && worldIn.getBlockState(center.down()).getBlock() == ModBlocks.CLIFF_STONE) {
            return true;
        }
        return false;
    }

    public static final WorldGenStructure NEXUS = new WorldGenNexusIslands();
    public static final WorldGenStructure DARK_NEXUS = new WorldGenDarkNexus();
    public static final WorldGenStructure CRIMSON_TOWER = new WorldGenCrimsonTower();

    public static class CliffMaelstromStructure extends WorldGenStructure {
        public CliffMaelstromStructure(String name) {
            super("cliff/" + name);
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
            if (function.startsWith("maelstrom")) {
                if (rand.nextInt(3) == 0) {
                    new WorldGenMaelstrom(ModBlocks.DECAYING_MAELSTROM, ModBlocks.CLIFF_MAELSTROM_CORE, (tileEntity) -> tileEntity.getSpawnerBaseLogic().setData(
                            new MobSpawnData[]{
                                    new MobSpawnData(ModEntities.getID(EntityMaelstromMage.class), new Element[]{Element.NONE, Element.GOLDEN}, new int[]{4, 1}, 1),
                                    new MobSpawnData(ModEntities.getID(EntityShade.class), new Element[]{Element.NONE, Element.GOLDEN}, new int[]{4, 1}, 1),
                                    new MobSpawnData(ModEntities.getID(EntityGoldenPillar.class), Element.NONE, 2),
                            },
                            new int[]{1, 1, 1},
                            3,
                            LevelHandler.CLIFF_OVERWORLD,
                            16)).generate(worldIn, rand, pos);
                } else {
                    worldIn.setBlockToAir(pos);
                }
            }
            if (function.startsWith("chest")) {
                worldIn.setBlockToAir(pos);
                if (rand.nextInt(3) == 0) {
                    pos = pos.down();
                    TileEntity tileentity = worldIn.getTileEntity(pos);

                    if (tileentity instanceof TileEntityChest) {
                        ((TileEntityChest) tileentity).setLootTable(LootTableHandler.MAELSTROM_RUINS, rand.nextLong());
                    }
                } else {
                    worldIn.setBlockToAir(pos.down());
                }
            }
        }
    }



    private static WorldGenStructure[] cliffSwampRuins = {WITCH_HUT, MAELSTROM_RUINS, CLIFF_TEMPLE, new CliffMaelstromStructure("brazier"),
            new CliffMaelstromStructure("gazebo"), new CliffMaelstromStructure("holy_tower"), new CliffMaelstromStructure("ruined_building"),
            new CliffMaelstromStructure("statue_of_nirvana"), new CliffMaelstromStructure("broken_arch"), new CliffMaelstromStructure("arch"),
            new CliffMaelstromStructure("ancient_houses")};
    private static double[] cliffRuinsWeights = {0.1, 0.1, 0.1, 0.1, 0.2, 0.1, 0.1, 0.05, 0.1, 0.1, 0.3};

    public static final WorldGenStructure invasionTower = new WorldGenStructure("invasion/invasion_tower") {
        @Override
        public boolean generate(World worldIn, Random rand, BlockPos position) {
            this.generateStructure(worldIn, position, Rotation.NONE);
            return true;
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand) {
            if (function.startsWith("boss")) {
                worldIn.setBlockToAir(pos);
                EntityMonolith entity = new EntityMonolith(worldIn);
                entity.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                worldIn.spawnEntity(entity);
            } else if (function.startsWith("scout")) {
                worldIn.setBlockState(pos, ModBlocks.BOSS_SPAWNER.getDefaultState(), 2);
                TileEntity tileentity = worldIn.getTileEntity(pos);

                if (tileentity instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(new MobSpawnData(ModEntities.getID(EntityShade.class), Element.NONE), 1, LevelHandler.INVASION, 8);
                }
            }
        }
    };



    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int x = chunkX * 16;
        int z = chunkZ * 16;

      if(world.provider.getDimension() == 0) {

        if(canStructureSpawn(chunkX, chunkZ, world, 80)) {
            Class<?> NETHER = Biomes.HELL.getBiomeClass();
            //Generate the NetherFortress, Reading Works
            System.out.println("Starting Dungeon");
            new WorldGenStartTemple(chunkX, chunkZ).generate(world, world.rand, new BlockPos(x, 90, z));


        }
      }


        if (world.provider.getDimension() == ModConfig.world.crimson_kingdom_dimension_id) {
            int chunkModX = Math.floorMod(chunkX, DimensionNexus.NexusStructureSpacing);
            int chunkModZ = Math.floorMod(chunkZ, DimensionNexus.NexusStructureSpacing);
            new WorldGenCrimsonKingdomChunk(chunkModX, chunkModZ).generate(world, world.rand, new BlockPos(x + 8, 0, z + 8));
        } else if (world.provider.getDimension() == ModConfig.world.cliff_dimension_id) {
            int i = 2;
            if (chunkX % i == 0 && chunkZ % i == 0 && world.rand.nextInt(4) == 0) {
                generateBiomeSpecificStructure(ModRandom.choice(cliffSwampRuins, random, cliffRuinsWeights).next(), world, random, x, z, BiomeInit.CLIFF_SWAMP.getClass());
            }

            // Generate more ledge features into the side of cliffs
            BlockPos pos = new BlockPos(x, 0, z);
            WorldGenStructure[] ledges = new WorldGenStructure[]{MEDIUM_LEDGE, MAELSTROM_LEDGE, SMALL_LEDGE, MAELSTROM_CAVE,};
            double[] ledgeWeights = {0.3, 0.1, 0.7, 0.1};
            WorldGenStructure cliffLedgeFeature = ModRandom.choice(ledges, random, ledgeWeights).next();
            cliffLedgeFeature.setMaxVariation(200);

            for (int j = 0; j < 20; j++) {
                if (generateBiomeSpecificStructure(cliffLedgeFeature, world, random, x + random.nextInt(8), z + random.nextInt(8), BiomeInit.HIGH_CLIFF.getClass(),
                        BiomeInit.CLIFF_SWAMP.getClass())) {
                    break;
                }
            }

            ledges = new WorldGenStructure[]{new WorldGenSmallLedge("cliff/boardwalk_pillars", 0), new WorldGenSmallLedge("cliff/boardwalk_totem", 0),
                    new WorldGenSmallLedge("cliff/large_boardwalk", 0), new WorldGenSmallLedge("cliff/boardwalk", 0), new WorldGenCliffStructureLedge(), CLIFF_RUIN_LEDGE};
            ledgeWeights = new double[]{0.1, 0.1, 0.2, 0.4, 0.1, 0.15};
            cliffLedgeFeature = ModRandom.choice(ledges, random, ledgeWeights).next();
            cliffLedgeFeature.setMaxVariation(200);

            for (int j = 0; j < 20; j++) {
                if (generateBiomeSpecificStructure(cliffLedgeFeature, world, random, x + random.nextInt(8), z + random.nextInt(8), BiomeInit.HIGH_CLIFF.getClass(),
                        BiomeInit.CLIFF_SWAMP.getClass())) {
                    break;
                }
            }

            // Generate smaller features like shrubs
            ModUtils.generateN(world, random, pos, 4, 40, 1, new WorldGenCliffMushroom(ModBlocks.CLIFF_STONE));
            ModUtils.generateN(world, random, pos, 35, 35, 1, new WorldGenCliffShrub(BiomeCliffSwamp.log, BiomeCliffSwamp.leaf));
            ModUtils.generateN(world, random, pos, 600, 30, 70, new WorldGenSwampVines());
            ModUtils.generateN(world, random, pos, 200, 100, 40, new WorldGenSwampVines());
            if (random.nextInt(15) == 0) {
                ModUtils.generateN(world, random, pos, 1, 200, 50, new WorldGenWaterfall(ModBlocks.CLIFF_STONE));
            }
        }

        if (world.provider.getDimension() == ModConfig.world.fracture_dimension_id) {
            WorldGenStructure[] azureStructures = {MAELSTROM_PIT, AZURE_HOUSE_1, new WorldGenStructure("azure/house_" + (random.nextInt(3) + 2)),
                    new WorldGenStructure("azure/pillar_" + (random.nextInt(6) + 1))};
            double[] azureWeights = {0.1, 0.1, 0.3, 0.5};
            int azure_structure_spacing = 3;
            if (chunkX % azure_structure_spacing == 0 && chunkZ % azure_structure_spacing == 0) {
                generateBiomeSpecificStructure(ModRandom.choice(azureStructures, random, azureWeights).next(), world, random, x, z, BiomeInit.AZURE.getClass(),
                        BiomeInit.AZURE_LIGHT.getClass());
            }
        }


    }

    public static final int STRUCTURE_SPACING_CHUNKS = 25;
    public static final int FORTRESS_NUMBER = 0;

    public static boolean canStructureSpawn(int chunkX, int chunkZ, World world, int frequency) {
        if (frequency <= 0) {
            return false;
        } else {
            int realFreq = 101 - frequency;
            int maxDistanceBetween = realFreq + 8;
            int i = chunkX;
            int j = chunkZ;
            if (chunkX < 0) {
                chunkX -= maxDistanceBetween - 1;
            }

            if (chunkZ < 0) {
                chunkZ -= maxDistanceBetween - 1;
            }

            int k = chunkX / maxDistanceBetween;
            int l = chunkZ / maxDistanceBetween;
            Random random = world.setRandomSeed(k, l, 14357617);
            k *= maxDistanceBetween;
            l *= maxDistanceBetween;
            k += random.nextInt(maxDistanceBetween - 8);
            l += random.nextInt(maxDistanceBetween - 8);
            return i == k && j == l;
        }

    }

    /**
     * Generates a structure in the chunk based if in the specific biome
     *
     * @param generator
     * @param world
     * @param rand
     * @param chunkX
     * @param chunkZ
     * @param classes
     * @return
     */
    private boolean generateBiomeSpecificStructure(WorldGenStructure generator, World world, Random rand, int x, int z, Class<?>... classes) {
        ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));

        x += 8;
        z += 8;
        int y = generator.getYGenHeight(world, x, z);
        BlockPos pos = new BlockPos(x, y, z);

        Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

        if (y > -1 && (world.getWorldType() != WorldType.FLAT || world.provider.getDimension() != 0)) {
            if (classesList.contains(biome)) {
                if (rand.nextFloat() > generator.getChanceToFail()) {
                    generator.generate(world, rand, pos);
                    return true;
                }
            }
        }
        return false;
    }


}
