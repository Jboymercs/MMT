package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.EntityVoidSpike;
import net.minecraft.entity.EntityLivingBase;

public class ActionShortRangeWave implements IAction{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        EntityVoidSpike spike = new EntityVoidSpike(actor.world);
        spike.setPosition(actor.posX + 1, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike);
        EntityVoidSpike spike2 = new EntityVoidSpike(actor.world);
        spike2.setPosition(actor.posX + 2, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike2);
        EntityVoidSpike spike3 = new EntityVoidSpike(actor.world);
        spike3.setPosition(actor.posX + 3, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike3);
        EntityVoidSpike spike4 = new EntityVoidSpike(actor.world);
        spike4.setPosition(actor.posX + 4, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike4);
        EntityVoidSpike spike5 = new EntityVoidSpike(actor.world);
        spike.setPosition(actor.posX + 1, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike5);
        EntityVoidSpike spike6 = new EntityVoidSpike(actor.world);
        spike6.setPosition(actor.posX - 1, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike6);
        EntityVoidSpike spike7 = new EntityVoidSpike(actor.world);
        spike7.setPosition(actor.posX -2, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike7);
        EntityVoidSpike spike8 = new EntityVoidSpike(actor.world);
        spike8.setPosition(actor.posX -3, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike8);
        EntityVoidSpike spike9 = new EntityVoidSpike(actor.world);
        spike9.setPosition(actor.posX -4, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike9);
        EntityVoidSpike spike10 = new EntityVoidSpike(actor.world);
        spike10.setPosition(actor.posX -5, actor.posY, actor.posZ);
        actor.world.spawnEntity(spike10);
        EntityVoidSpike spike11 = new EntityVoidSpike(actor.world);
        spike11.setPosition(actor.posX, actor.posY, actor.posZ + 1);
        actor.world.spawnEntity(spike11);
        EntityVoidSpike spike12 = new EntityVoidSpike(actor.world);
        spike12.setPosition(actor.posX, actor.posY, actor.posZ + 2);
        actor.world.spawnEntity(spike12);
        EntityVoidSpike spike13 = new EntityVoidSpike(actor.world);
        spike13.setPosition(actor.posX, actor.posY, actor.posZ + 3);
        actor.world.spawnEntity(spike13);
        EntityVoidSpike spike14 = new EntityVoidSpike(actor.world);
        spike14.setPosition(actor.posX, actor.posY, actor.posZ + 4);
        actor.world.spawnEntity(spike14);
        EntityVoidSpike spike15 = new EntityVoidSpike(actor.world);
        spike15.setPosition(actor.posX, actor.posY, actor.posZ + 5);
        actor.world.spawnEntity(spike15);
        EntityVoidSpike spike16 = new EntityVoidSpike(actor.world);
        spike16.setPosition(actor.posX, actor.posY, actor.posZ -1);
        actor.world.spawnEntity(spike16);
        EntityVoidSpike spike17 = new EntityVoidSpike(actor.world);
        spike17.setPosition(actor.posX, actor.posY, actor.posZ -2);
        actor.world.spawnEntity(spike17);
        EntityVoidSpike spike18 = new EntityVoidSpike(actor.world);
        spike18.setPosition(actor.posX, actor.posY, actor.posZ -3);
        actor.world.spawnEntity(spike18);
        EntityVoidSpike spike19 = new EntityVoidSpike(actor.world);
        spike19.setPosition(actor.posX, actor.posY, actor.posZ -4);
        actor.world.spawnEntity(spike19);
        EntityVoidSpike spike20 = new EntityVoidSpike(actor.world);
        spike20.setPosition(actor.posX, actor.posY, actor.posZ  -5);
        actor.world.spawnEntity(spike20);
        //Negative - Negative
        EntityVoidSpike spike21 = new EntityVoidSpike(actor.world);
        spike21.setPosition(actor.posX -1, actor.posY, actor.posZ  -1);
        actor.world.spawnEntity(spike21);
        EntityVoidSpike spike22 = new EntityVoidSpike(actor.world);
        spike22.setPosition(actor.posX -2, actor.posY, actor.posZ  -1);
        actor.world.spawnEntity(spike22);
        EntityVoidSpike spike23 = new EntityVoidSpike(actor.world);
        spike23.setPosition(actor.posX -3, actor.posY, actor.posZ  -1);
        actor.world.spawnEntity(spike23);
        EntityVoidSpike spike24 = new EntityVoidSpike(actor.world);
        spike24.setPosition(actor.posX -4, actor.posY, actor.posZ  -1);
        actor.world.spawnEntity(spike24);
        EntityVoidSpike spike25 = new EntityVoidSpike(actor.world);
        spike25.setPosition(actor.posX -1, actor.posY, actor.posZ  -2);
        actor.world.spawnEntity(spike25);
        EntityVoidSpike spike26 = new EntityVoidSpike(actor.world);
        spike26.setPosition(actor.posX -1, actor.posY, actor.posZ  -3);
        actor.world.spawnEntity(spike26);
        EntityVoidSpike spike27 = new EntityVoidSpike(actor.world);
        spike27.setPosition(actor.posX -1, actor.posY, actor.posZ  -4);
        actor.world.spawnEntity(spike27);
        EntityVoidSpike spike28 = new EntityVoidSpike(actor.world);
        spike28.setPosition(actor.posX -2, actor.posY, actor.posZ  -2);
        actor.world.spawnEntity(spike28);
        EntityVoidSpike spike29 = new EntityVoidSpike(actor.world);
        spike29.setPosition(actor.posX -3, actor.posY, actor.posZ  -2);
        actor.world.spawnEntity(spike29);
        EntityVoidSpike spike30 = new EntityVoidSpike(actor.world);
        spike30.setPosition(actor.posX -2, actor.posY, actor.posZ  -3);
        actor.world.spawnEntity(spike30);
        EntityVoidSpike spike31 = new EntityVoidSpike(actor.world);
        spike31.setPosition(actor.posX -3, actor.posY, actor.posZ  -3);
        actor.world.spawnEntity(spike31);
        //Positive - Positive
        EntityVoidSpike spike32 = new EntityVoidSpike(actor.world);
        spike32.setPosition(actor.posX + 1, actor.posY, actor.posZ +1);
        actor.world.spawnEntity(spike32);
        EntityVoidSpike spike33 = new EntityVoidSpike(actor.world);
        spike33.setPosition(actor.posX + 2, actor.posY, actor.posZ +1);
        actor.world.spawnEntity(spike33);
        EntityVoidSpike spike34 = new EntityVoidSpike(actor.world);
        spike34.setPosition(actor.posX + 3, actor.posY, actor.posZ +1);
        actor.world.spawnEntity(spike34);
        EntityVoidSpike spike35 = new EntityVoidSpike(actor.world);
        spike35.setPosition(actor.posX + 4, actor.posY, actor.posZ +1);
        actor.world.spawnEntity(spike35);
        EntityVoidSpike spike36 = new EntityVoidSpike(actor.world);
        spike36.setPosition(actor.posX + 1, actor.posY, actor.posZ +2);
        actor.world.spawnEntity(spike36);
        EntityVoidSpike spike37 = new EntityVoidSpike(actor.world);
        spike37.setPosition(actor.posX + 1, actor.posY, actor.posZ +3);
        actor.world.spawnEntity(spike37);
        EntityVoidSpike spike38 = new EntityVoidSpike(actor.world);
        spike38.setPosition(actor.posX + 1, actor.posY, actor.posZ +4);
        actor.world.spawnEntity(spike38);
        EntityVoidSpike spike39 = new EntityVoidSpike(actor.world);
        spike39.setPosition(actor.posX + 2, actor.posY, actor.posZ +2);
        actor.world.spawnEntity(spike39);
        EntityVoidSpike spike40 = new EntityVoidSpike(actor.world);
        spike40.setPosition(actor.posX + 3, actor.posY, actor.posZ +2);
        actor.world.spawnEntity(spike40);
        EntityVoidSpike spike41 = new EntityVoidSpike(actor.world);
        spike41.setPosition(actor.posX + 2, actor.posY, actor.posZ +3);
        actor.world.spawnEntity(spike41);
        EntityVoidSpike spike42 = new EntityVoidSpike(actor.world);
        spike42.setPosition(actor.posX + 3, actor.posY, actor.posZ +3);
        actor.world.spawnEntity(spike42);
        //Positive - Negative
        EntityVoidSpike spike43 = new EntityVoidSpike(actor.world);
        spike43.setPosition(actor.posX + 1, actor.posY, actor.posZ -1);
        actor.world.spawnEntity(spike43);
        EntityVoidSpike spike44 = new EntityVoidSpike(actor.world);
        spike44.setPosition(actor.posX + 2, actor.posY, actor.posZ -1);
        actor.world.spawnEntity(spike44);
        EntityVoidSpike spike45 = new EntityVoidSpike(actor.world);
        spike45.setPosition(actor.posX + 3, actor.posY, actor.posZ -1);
        actor.world.spawnEntity(spike45);
        EntityVoidSpike spike46 = new EntityVoidSpike(actor.world);
        spike46.setPosition(actor.posX + 4, actor.posY, actor.posZ -1);
        actor.world.spawnEntity(spike46);
        EntityVoidSpike spike47 = new EntityVoidSpike(actor.world);
        spike47.setPosition(actor.posX + 1, actor.posY, actor.posZ -2);
        actor.world.spawnEntity(spike47);
        EntityVoidSpike spike48 = new EntityVoidSpike(actor.world);
        spike48.setPosition(actor.posX + 1, actor.posY, actor.posZ -3);
        actor.world.spawnEntity(spike48);
        EntityVoidSpike spike49 = new EntityVoidSpike(actor.world);
        spike49.setPosition(actor.posX + 1, actor.posY, actor.posZ -4);
        actor.world.spawnEntity(spike49);
        EntityVoidSpike spike50 = new EntityVoidSpike(actor.world);
        spike50.setPosition(actor.posX + 2, actor.posY, actor.posZ -2);
        actor.world.spawnEntity(spike50);
        EntityVoidSpike spike51 = new EntityVoidSpike(actor.world);
        spike51.setPosition(actor.posX + 3, actor.posY, actor.posZ -2);
        actor.world.spawnEntity(spike51);
        EntityVoidSpike spike52 = new EntityVoidSpike(actor.world);
        spike52.setPosition(actor.posX + 2, actor.posY, actor.posZ -3);
        actor.world.spawnEntity(spike52);
        EntityVoidSpike spike53 = new EntityVoidSpike(actor.world);
        spike53.setPosition(actor.posX + 3, actor.posY, actor.posZ -3);
        actor.world.spawnEntity(spike53);
        //Negative - Positive
        EntityVoidSpike spike54 = new EntityVoidSpike(actor.world);
        spike54.setPosition(actor.posX -1, actor.posY, actor.posZ + 1);
        actor.world.spawnEntity(spike54);
        EntityVoidSpike spike55 = new EntityVoidSpike(actor.world);
        spike55.setPosition(actor.posX -2, actor.posY, actor.posZ + 1);
        actor.world.spawnEntity(spike55);
        EntityVoidSpike spike56 = new EntityVoidSpike(actor.world);
        spike56.setPosition(actor.posX -3, actor.posY, actor.posZ + 1);
        actor.world.spawnEntity(spike56);
        EntityVoidSpike spike57 = new EntityVoidSpike(actor.world);
        spike57.setPosition(actor.posX -4, actor.posY, actor.posZ + 1);
        actor.world.spawnEntity(spike57);
        EntityVoidSpike spike58 = new EntityVoidSpike(actor.world);
        spike58.setPosition(actor.posX -1, actor.posY, actor.posZ + 2);
        actor.world.spawnEntity(spike58);
        EntityVoidSpike spike59 = new EntityVoidSpike(actor.world);
        spike59.setPosition(actor.posX -1, actor.posY, actor.posZ + 3);
        actor.world.spawnEntity(spike59);
        EntityVoidSpike spike60 = new EntityVoidSpike(actor.world);
        spike60.setPosition(actor.posX -1, actor.posY, actor.posZ + 4);
        actor.world.spawnEntity(spike60);
        EntityVoidSpike spike61 = new EntityVoidSpike(actor.world);
        spike61.setPosition(actor.posX -2, actor.posY, actor.posZ + 2);
        actor.world.spawnEntity(spike61);
        EntityVoidSpike spike62 = new EntityVoidSpike(actor.world);
        spike62.setPosition(actor.posX -3, actor.posY, actor.posZ + 2);
        actor.world.spawnEntity(spike62);
        EntityVoidSpike spike63 = new EntityVoidSpike(actor.world);
        spike63.setPosition(actor.posX -2, actor.posY, actor.posZ + 3);
        actor.world.spawnEntity(spike63);
        EntityVoidSpike spike64 = new EntityVoidSpike(actor.world);
        spike64.setPosition(actor.posX -3, actor.posY, actor.posZ + 3);
        actor.world.spawnEntity(spike64);
    }
}