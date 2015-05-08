package boni.jaded.shiny;

import net.minecraft.nbt.NBTTagCompound;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import tconstruct.library.event.ToolCraftEvent;
import tconstruct.tools.TinkerTools;

public class ToolEvents {

  @SubscribeEvent
  public void onToolCraft(ToolCraftEvent.NormalTool event) {
    NBTTagCompound toolTag = event.toolTag.getCompoundTag("InfiTool");

    // lumium, enderium, Manasteel
    int modifiers = toolTag.getInteger("Modifiers");
    modifiers += getPartCount(toolTag, JadedsShinies.ENDERIUM_ID);
    modifiers += getPartCount(toolTag, JadedsShinies.LUMIUM_ID);
    modifiers += getPartCount(toolTag, JadedsShinies.MANASTEEL_ID);

    modifiers += getPartCount(toolTag, JadedsShinies.SIGNALUM_ID)*2;

    // only 1 part counts for witheriron and terrasteel
    if(getPartCount(toolTag, JadedsShinies.TERRASTEEL_ID) > 0)
      modifiers += 3;
    if(getPartCount(toolTag, JadedsShinies.WITHER_ID) > 0)
      modifiers += 3;

    toolTag.setInteger("Modifiers", modifiers);
  }

  public static int getPartCount(NBTTagCompound toolTag, int materialID) {
    int parts = 0;
    if (toolTag.getInteger("Head") == materialID)
      parts++;
    if (toolTag.getInteger("Handle") == materialID)
      parts++;
    if (toolTag.getInteger("Accessory") == materialID)
      parts++;
    if (toolTag.getInteger("Extra") == materialID)
      parts++;

    return parts;
  }
}
