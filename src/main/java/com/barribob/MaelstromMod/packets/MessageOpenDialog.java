package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.gui.Guis;
import com.barribob.MaelstromMod.gui.dialog.NPCDialog;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenDialog implements IMessage {

    public int id;
    public MessageOpenDialog() {}

    public MessageOpenDialog(int id) {
        this.id = id;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();


    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.id);
    }

    public static class OpenDialogMessageHandler implements IMessageHandler<MessageOpenDialog, IMessage> {


        @Override
        public IMessage onMessage(MessageOpenDialog message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                NPCDialog.newInstance(message.id);
                Main.proxy.openGui(Guis.GuiNPCdialog);
            });

            return null;
        }
    }
}
