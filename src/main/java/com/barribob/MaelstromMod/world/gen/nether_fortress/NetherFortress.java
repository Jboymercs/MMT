package com.barribob.MaelstromMod.world.gen.nether_fortress;

import com.barribob.MaelstromMod.util.GenUtils;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.world.dimension.nether.ChunkGeneratorNether;
import com.google.common.collect.Lists;
import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

public class NetherFortress {

    private List<StructureComponent> components;

    private World world;

    private TemplateManager manager;

    private ChunkGeneratorNether provider;

    private static final int SIZE = 10;

    private int requiredGroundHeight = 60;

    private static final List<Tuple<Rotation, BlockPos>> CROSS_POS = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(0, 0, 0)),
            new Tuple(Rotation.CLOCKWISE_90, new BlockPos(13, 0, 0)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, 13)));


    public NetherFortress(World world, TemplateManager manager, ChunkGeneratorNether provider, List<StructureComponent> components) {
        this.components = components;
        this.world = world;
        this.manager = manager;
        this.provider = provider;
    }

    public void startFortress(BlockPos pos, Rotation rot) {
        FortressTemplateK template = new FortressTemplateK(manager, "nk_boss_room_north", pos, rot, 0, true);
        if (GenUtils.getGroundHeight(template, provider, rot) > requiredGroundHeight && ModUtils.chunksGenerated(template.getBoundingBox(), world)) {
            components.add(template);
            generateCross(template, BlockPos.ORIGIN, rot);
            FortressTemplateK.resetTemplateCount();
            System.out.println("First Structure spawned");
        }

    }

    public boolean generateCross(FortressTemplateK parent, BlockPos pos, Rotation rot) {
        String[] rooms = {"nk_intersection_1", "nk_intersection_2"};
        FortressTemplateK template = addAdjustmentPiece(parent, pos, ModRandom.choice(rooms), rot);

        if(template.isCollidingExcParent(manager, parent, components) || template.getDistance() > SIZE || ModUtils.chunksGenerated(template.getBoundingBox(), world)) {
            return false;
        }
        if(GenUtils.getGroundHeight(template, provider, rot) < requiredGroundHeight) {
            return this.generateEnd(parent, pos, rot); //Generate End
        }

        List<StructureComponent> structures = new ArrayList<>(components);

        components.add(template);

        int failedHalls = 0;
        for (Tuple<Rotation, BlockPos> tuple : CROSS_POS) {
            if(!generateHalls(template, tuple.getSecond(), rot.add(tuple.getFirst()))) {
                failedHalls++;
            }
        }
        if (failedHalls >= 3) {
            components.clear();
            components.addAll(structures);
            return this.generateEnd(parent, pos, rot);
        }
        return true;
    }

    private boolean generateHalls(FortressTemplateK parent, BlockPos pos, Rotation rotation) {
        String[] rooms = {"nk_hall_e_1", "nk_hall_e_2", "nk_hall_n_1", "nk_hall_n_2", "nk_room_north_south", "nk_room_west_east"};
        FortressTemplateK template1 = addAdjustmentPiece(parent, pos, ModRandom.choice(rooms), rotation);

        if(template1.isCollidingExcParent(manager, parent, components) || template1.getDistance() > SIZE || ModUtils.chunksGenerated(template1.getBoundingBox(), world)) {
            return false;
        }

        if(GenUtils.getGroundHeight(template1, provider, rotation) < requiredGroundHeight) {
            return this.generateEnd(parent, pos, rotation);
        }

        components.add(template1);

        int r = world.rand.nextInt(3);
        boolean genSuccess;

        if (r == 0) {
            genSuccess = generateHalls(template1, BlockPos.ORIGIN, rotation);
        }
        else {
            genSuccess = generateCross(template1, BlockPos.ORIGIN, rotation);
        }

        if(!genSuccess) {
            components.remove(template1);
            return this.generateEnd(parent, pos, rotation);
        }
        return true;

    }
    // Rooms InWork
    private boolean generateRooms(FortressTemplateK parent, BlockPos pos, Rotation rotation) {

        return false;
    }

    private boolean generateEnd(FortressTemplateK parent, BlockPos pos, Rotation rot) {
        FortressTemplateK template = addAdjustmentPiece(parent, pos, "nk_balcony_any", rot);
        if(template.isCollidingExcParent(manager, parent, components) || template.getDistance() > SIZE || ModUtils.chunksGenerated(template.getBoundingBox(), world)) {
            return false;

        }
        components.add(template);
        return true;
    }

    private FortressTemplateK addAdjustmentPiece(FortressTemplateK parent, BlockPos pos, String type, Rotation rot) {
        FortressTemplateK newTemplate = new FortressTemplateK(manager, type, parent.getTemplatePosition(), rot, parent.getDistance() + 1, true);
        BlockPos pos1 = parent.getTemplate().calculateConnectedPos(parent.getPlacementSettings(), pos, newTemplate.getPlacementSettings(), BlockPos.ORIGIN);
        newTemplate.offset(pos1.getX(), pos1.getY(), pos1.getZ());
        adjustmentCenter(parent, newTemplate, rot);
        return newTemplate;

    }
    private void adjustmentCenter(FortressTemplateK parent, FortressTemplateK child, Rotation rot) {
        BlockPos adjustmentPos = new BlockPos(parent.getTemplate().getSize().getX(), 0, (parent.getTemplate().getSize().getZ() - child.getTemplate().getSize().getZ()) / 2f)
                .rotate(rot);
        child.offset(adjustmentPos.getX(), adjustmentPos.getY(), adjustmentPos.getZ());
    }



}
