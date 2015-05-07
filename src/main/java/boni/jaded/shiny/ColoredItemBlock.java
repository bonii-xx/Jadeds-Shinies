package boni.jaded.shiny;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ColoredItemBlock extends ItemBlock {

  public ColoredItemBlock(Block p_i45328_1_) {
    super(p_i45328_1_);
  }

  @Override
  public int getColorFromItemStack(ItemStack stack, int p_82790_2_) {
    if(stack.getItem() != null && stack.getItem() instanceof ItemBlock)
    {
      return ((ItemBlock)stack.getItem()).field_150939_a.getRenderColor(stack.getItemDamage());
    }
    return super.getColorFromItemStack(stack, p_82790_2_);
  }
}
