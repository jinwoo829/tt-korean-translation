package vazkii.tinkerer.common.network.packet.kami;

import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.core.handler.kami.KamiArmorHandler;
import vazkii.tinkerer.common.network.IPacket;

/**
 * Created by Katrina on 28/02/14.
 */
public class PacketToggleArmor implements IPacket {
    private static final long serialVersionUID = -1247633508013055777L;
    public boolean armorStatus;
    public PacketToggleArmor(boolean status)
    {
        armorStatus=status;
    }
    @Override
    public void handle(INetworkManager manager, Player player) {
        if(player instanceof EntityPlayer)
        {

            EntityPlayer entityPlayer=(EntityPlayer)player;
            ThaumicTinkerer.proxy.setArmor(entityPlayer,armorStatus);

        }
    }
}
