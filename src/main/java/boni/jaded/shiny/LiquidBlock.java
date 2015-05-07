package boni.jaded.shiny;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LiquidBlock extends BlockFluidClassic {
  public IIcon stillIcon;
  public IIcon flowIcon;

  public LiquidBlock(Fluid fluid, Material material) {
    super(fluid, material);
  }

  @Override
  public void registerBlockIcons (IIconRegister iconRegister)
  {
    stillIcon = iconRegister.registerIcon("jadedsshinies:liquid_gray");
    flowIcon = iconRegister.registerIcon("jadedsshinies:liquid_gray_flow");

    this.getFluid().setIcons(stillIcon, flowIcon);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public IIcon getIcon (int side, int meta)
  {
    if (side == 0 || side == 1)
      return stillIcon;
    return flowIcon;
  }

  @Override
  public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
    return definedFluid.getColor();
  }

  @Override
  public int getRenderColor(int p_149741_1_) {
    return definedFluid.getColor();
  }
}
