package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.util.PlayerDataProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncDialogData implements IMessage {
    public String dialog;
    public boolean remove;



    public MessageSyncDialogData() {}

public MessageSyncDialogData(String dialog, boolean remove) {
        this.dialog = dialog;
        this.remove = remove;
}

    @Override
    public void fromBytes(ByteBuf buf) {
        this.dialog = ByteBufUtils.readUTF8String(buf);
        this.remove = buf.readBoolean();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.dialog);
        buf.writeBoolean(this.remove);

    }

    public static class SyncDialogDataMessageHandler implements IMessageHandler<MessageSyncDialogData, IMessage> {

        @Override
        public IMessage onMessage(MessageSyncDialogData message, MessageContext ctx) {
            EntityPlayerMP mp = ctx.getServerHandler().player;
            mp.getServer().addScheduledTask(() -> {
                if (message.remove) {
                    ctx.getServerHandler().player.getCapability(PlayerDataProvider.PLAYERDATA, null).removeString("dialog".concat(message.dialog));
                }
                else {
                    ctx.getServerHandler().player.getCapability(PlayerDataProvider.PLAYERDATA, null).addString("dialog".concat(message.dialog), false);

                }
            });
            return null;
        }
    }

}
