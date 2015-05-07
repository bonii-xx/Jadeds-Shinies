package boni.jaded.shiny;

import net.minecraftforge.fluids.Fluid;

public class ColoredFluid extends Fluid {
  public final int color;

  public ColoredFluid(String fluidName, int color) {
    super(fluidName);
    this.color = color;
  }

  @Override
  public int getColor() {
    return color;
  }

  @Override
  public String getLocalizedName() {
    return this.getBlock().getLocalizedName();
  }
}
