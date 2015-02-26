package joshie.harvestmoon.crops.icons;

import joshie.harvestmoon.api.crops.ICropIIconHandler;
import joshie.harvestmoon.core.lib.HMModInfo;
import joshie.harvestmoon.crops.Crop;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IIconHandlerDefault implements ICropIIconHandler {
    private IIcon[] stageIcons;
    private Crop crop;

    public IIconHandlerDefault(Crop crop) {
        this.crop = crop;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconForStage(int stage) {
        return stageIcons[Math.max(0, stage - 1)];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        stageIcons = new IIcon[crop.getStages()];
        for (int i = 0; i < crop.getStages(); i++) {
            stageIcons[i] = register.registerIcon(HMModInfo.CROPPATH + crop.getUnlocalizedName() + "/stage_" + i);
        }
    }
}
