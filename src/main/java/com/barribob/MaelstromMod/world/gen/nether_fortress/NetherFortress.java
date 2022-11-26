package com.barribob.MaelstromMod.world.gen.nether_fortress;

import com.google.common.collect.Lists;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGeneratorHell;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.List;

public class NetherFortress {

    private List<StructureComponent> components;

    private World world;

    private TemplateManager manager;

    private ChunkGeneratorHell provider;

    private static final int SIZE = 10;

    private int requiredGroundHeight = 60;

    private static final List<Tuple<Rotation, BlockPos>> CROSS_POS = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(0, 0, 0)),
            new Tuple(Rotation.CLOCKWISE_90, new BlockPos(30, 0, 0)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, 30)));


    public NetherFortress(World world, TemplateManager manager, ChunkGeneratorHell provider, List<StructureComponent> components) {
        this.components = components;
        this.world = world;
        this.manager = manager;
        this.provider = provider;
    }

    public void startFortress(BlockPos pos, Rotation rotation) {
        FortressTemplate template = new FortressTemplate(manager, "nk_boss_room_north", pos, rotation, 0, true);

    }


}
