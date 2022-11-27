package com.barribob.MaelstromMod.world.gen.nether_fortress;

import com.barribob.MaelstromMod.world.dimension.nether.ChunkGeneratorNether;
import com.barribob.MaelstromMod.world.gen.MapGenModStructure;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

import java.util.Random;

public class MapGenNetherFortress extends MapGenModStructure {
        ChunkGeneratorNether provider;

    public MapGenNetherFortress(int spacing, int offset, int odds, ChunkGeneratorNether provider) {
        super(spacing, offset, odds);
        this.provider = provider;
        System.out.println("Loaded in MapGenFortress");
    }

    @Override
    public String getStructureName() {
        return "Nether Knight Fortress";
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new MapGenNetherFortress.Start(this.world, this.rand, chunkX, chunkZ, provider);
    }

    public static class Start extends StructureStart{
        ChunkGeneratorNether provider;

        public Start() {

        }

        public Start(World worldIn, Random rand, int chunkX, int chunkZ, ChunkGeneratorNether provider) {
            super(chunkX, chunkZ);
            this.provider = provider;
            this.create(worldIn, rand, chunkX, chunkZ);
            System.out.println("Starting Generation for Fortress");
        }

        public void create(World worldIn, Random rnd, int chunkX, int chunkZ) {
            Random random = new Random(chunkX + chunkZ * 10387313);
            int rand = random.nextInt(Rotation.values().length);

            for(int i = 0; i < 4; i++) {
                components.clear();
                Rotation rotation = Rotation.values()[(rand + i) % Rotation.values().length];
                int mod = (ChunkGeneratorNether.STRUCTURE_SPACING_CHUNKS + ChunkGeneratorNether.FORTRESS_NUMBER) * 2;
                boolean isXEven = ((((chunkX - ChunkGeneratorNether.FORTRESS_NUMBER) / ChunkGeneratorNether.STRUCTURE_SPACING_CHUNKS) % 2) & 1) == 0;
                boolean isZEven = ((((chunkZ - ChunkGeneratorNether.FORTRESS_NUMBER) / ChunkGeneratorNether.STRUCTURE_SPACING_CHUNKS) % 2) & 1) == 0;
                int y = isXEven ^ isZEven ? 50 : 70;
                BlockPos blockPos = new BlockPos(chunkX * 16 + 8, y, chunkZ * 16 + 8);
                NetherFortress stronghold = new NetherFortress(worldIn, worldIn.getSaveHandler().getStructureTemplateManager(), provider, components);
                stronghold.startFortress(blockPos, rotation);
                this.updateBoundingBox();
            }
        }
    }
}
