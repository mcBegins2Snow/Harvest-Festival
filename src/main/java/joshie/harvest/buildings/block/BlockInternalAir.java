package joshie.harvest.buildings.block;

import joshie.harvest.buildings.HFBuildings;
import joshie.harvest.buildings.item.ItemCheat.Cheat;
import joshie.harvest.core.HFTab;
import joshie.harvest.core.base.block.BlockHFBase;
import joshie.harvest.core.helpers.InventoryHelper;
import joshie.harvest.core.lib.CreativeSort;
import joshie.harvest.core.util.Text;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static joshie.harvest.core.helpers.InventoryHelper.ITEM_STACK;

public class BlockInternalAir extends BlockHFBase<BlockInternalAir> {
    public BlockInternalAir() {
        super(Material.GLASS, HFTab.TOWN);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return Text.localizeFully(getUnlocalizedName());
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return CreativeSort.LAST - 1;
    }

    public static void onPlaced(World world, BlockPos pos, EntityPlayer player) {
        MinecraftForge.EVENT_BUS.register(new RemoveIfHolding(world, pos, player));
    }

    private static class RemoveIfHolding {
        private final World world;
        private final BlockPos pos;
        private final EntityPlayer player;

        RemoveIfHolding (World world, BlockPos pos, EntityPlayer player) {
            this.world = world;
            this.pos = pos;
            this.player = player;
        }

        @SubscribeEvent
        public void onPlayerTick(PlayerTickEvent event) {
            if (event.player == player && event.phase == Phase.END) {
                if (InventoryHelper.getHandItemIsIn(event.player, ITEM_STACK, HFBuildings.CHEAT.getStackFromEnum(Cheat.AIR_REMOVER)) != null) {
                    world.setBlockToAir(pos);
                    try {
                        MinecraftForge.EVENT_BUS.unregister(this);
                    } catch (Exception e) {}
                }
            }
        }
    }
}
