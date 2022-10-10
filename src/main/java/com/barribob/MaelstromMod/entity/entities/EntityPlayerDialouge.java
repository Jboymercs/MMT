package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.gui.Guis;
import com.barribob.MaelstromMod.packets.MessageOpenDialog;
import com.barribob.MaelstromMod.world.IWorldHoverObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class EntityPlayerDialouge extends EntityPlayerBase implements IAnimatable {
    /**
     * Specific Base to handle Dialog Npcs
     *
     */

    public boolean talking;

    private UUID talkingPlayer;

    private static final DataParameter<Integer> NAME = EntityDataManager.<Integer>createKey(EntityPlayerDialouge.class, DataSerializers.VARINT);

    private EntityPlayerDialouge.NameSystem npcName;
    public EntityPlayerDialouge.NameSystem getNameDialog() {
        return this.getNameDialogID(this.dataManager.get(NAME));
    }
    public EntityPlayerDialouge(World worldIn) {
        super(worldIn);
        this.isImmuneToFire = true;
    }

    public void setName(EntityPlayerDialouge.NameSystem name) {
        this.npcName= name;
        this.dataManager.set(NAME, name.getID());
    }

    public static EntityPlayerDialouge.NameSystem getNameDialogID(int id) {
    EntityPlayerDialouge.NameSystem values[] = EntityPlayerDialouge.NameSystem.values();
        return values[id];
    }
    @Override
    public boolean canBeLeashedTo(EntityPlayer entityPlayer) {
        return false;
    }
    @Override
    public void onUpdate() {


        super.onUpdate();
    }
    public static enum NameSystem {
        TEST_DUMMY(0, 1);

        private final int id;
        private final int talkCount;

        private NameSystem(int id, int talkCount) {
            this.id = id;
            this.talkCount = talkCount;
        }

        public String getName() {
            return this.name();
        }

        public int getID() {
            return this.id;
        }

        public int getTalkCount() {
            return this.talkCount;
        }
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec3d, EnumHand hand) {
        this.faceEntity(player, 180F, 180F);
        this.motionX = 0.1;
        this.motionZ = 0.1;
        this.talking = true;
        ItemStack itemStack = player.getHeldItem(hand);

        if (this.opensGui()) {


               if (world.isRemote) Main.proxy.openGui(Guis.GuiNPCdialog);

        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(NAME, 0);

    }

    public EntityPlayerMP getTalkingPlayer() {
        if(this.talkingPlayer != null)
            return (EntityPlayerMP)this.world.getPlayerEntityByUUID(talkingPlayer);
        return null;
    }

    public boolean opensGui() {
        if (this.getNameDialog() == NameSystem.TEST_DUMMY) {
            return true;
        }

        return false;
    }



    //Kill me, I have so much more to add

}
