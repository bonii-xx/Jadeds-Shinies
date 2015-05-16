package boni.jaded.shiny;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

import tconstruct.library.ActiveToolMod;
import tconstruct.library.tools.ToolCore;

public class JadedActiveToolMod extends ActiveToolMod {

  @Override
  public void afterBlockBreak(ToolCore tool, ItemStack stack, Block block, int x, int y, int z,
                              EntityLivingBase entity) {
    super.afterBlockBreak(tool, stack, block, x, y, z, entity);

    if (hasFeedback(stack)) {
      entity.attackEntityFrom(DamageSource.outOfWorld, 5);
    }
  }

  @Override
  public int attackDamage(int modDamage, int currentDamage, ToolCore tool, NBTTagCompound tags, NBTTagCompound toolTags,
                          ItemStack stack, EntityLivingBase player, Entity entity) {
    if (hasFeedback(stack)) {
      player.attackEntityFrom(DamageSource.outOfWorld, 5);
    }

    return super.attackDamage(modDamage, currentDamage, tool, tags, toolTags, stack, player, entity);
  }

  private boolean hasFeedback(ItemStack stack) {
    boolean feedback = false;

    NBTTagCompound tags = stack.getTagCompound().getCompoundTag("InfiTool");
    feedback = ToolEvents.getPartCount(tags, JadedsShinies.WITHER_ID) > 0;
    feedback &= ToolEvents.getPartCount(tags, JadedsShinies.TERRASTEEL_ID) > 0;

    return feedback;
  }
}
