package boni.jaded.shiny;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.LiquidCasting;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.util.IPattern;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.weaponry.TinkerWeaponry;

@Mod(modid = JadedsShinies.MODID, version = "${version}", dependencies = "required-after:TConstruct;")
public class JadedsShinies {

  public static final String MODID = "JadedsShinies";

  public static final int ONION_ID = 55;
  public static final int WITHER_ID = 656;
  public static final int PLASSTEEL_ID =578 ;

  public static final int MITHRIL_ID = 588;
  public static final int ENDERIUM_ID = 598;
  public static final int SIGNALUM_ID = 608;
  public static final int LUMIUM_ID = 618;

  // MFR
  public static final int PLASTIC_ID = 1000;

  // ExtraTiC
  public static final int MANASTEEL_ID = 145;
  public static final int TERRASTEEL_ID = 146;
  public static final int ELEMENTIUM_ID = 146;

  public static Fluid plassteelFluid;
  public static Fluid plasticFluid;
  public static Fluid witherironFluid;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    // Custom materials
    registerMaterial(ONION_ID, "materialOnion", 0, 10, 90, 6, 0.3f, 0, 0xFAF9C3, 0, 7.0f, 10, 0.1f);
    registerMaterial(WITHER_ID, "materialwitherIron", 8, 1500, 300, 9, 1.2f, 0, 0xDFC3FA, 2, 0.52f, 5, 3);
    registerMaterial(PLASSTEEL_ID, "materialPlassteel", 5, 900, 700, 8, 0.3f, 2, 0x91A5B3, 0, 5.0f, 7, 4.0f);

    // Thermal Foundation Materials
    registerMaterial(MITHRIL_ID, "materialMithril", 10, 2100, 1000, 14, 2.0f, 1, 0xC19BEB, 7, 8.0f, 10, 8.0f);
    registerMaterial(ENDERIUM_ID, "materialEnderium", 8, 1100, 2300, 16, 2.0f, 1, 0x148C99, 9, 0.2f, 6, 3.0f);
    registerMaterial(SIGNALUM_ID, "materialSignalum", 10, 800, 1300, 11, 1.0f, 0, 0xF78B3E, 3, 0.8f, 3, 7.0f);
    registerMaterial(LUMIUM_ID, "materialLumium", 10, 800, 1100, 6, 2.5f, 0, 0xFAFA9B, 2, 0.5f, 2, 8.0f);

    // Botania Materials
    //registerMaterial(619, "materialManaSteel", 6, 800, 400, 18, 2.5f, 0, 0x9BCFFA, 8, 20.0f, 2, 6.0f);
    //registerMaterial(620, "materialTerraSteel", 7, 900, 600, 22, 1.5f, 0, 0x9BFAB9, 8, 0.3f, 2, 8.0f);

    // The liquids not in Tinkers
    plassteelFluid = registerFluid("plassteel", 0x91A5B3);
    plasticFluid = registerFluid("plastic", 0xABABAB);
    witherironFluid = registerFluid("witheriron", 0xDFC3FA);

    FluidType.registerFluidType("PlasSteel", TinkerSmeltery.glueBlock, 0, 200, plassteelFluid, true);
    FluidType.registerFluidType("Plastic", TinkerSmeltery.glueBlock, 0, 200, plasticFluid, true);
    FluidType.registerFluidType("WitherIron", Blocks.iron_block, 0, 200, witherironFluid, true);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    // solid parts
    boolean registered = false;
    for(ItemStack onion : OreDictionary.getOres("cropOnion")) {
      if(!registered) {
        PatternBuilder.instance
            .registerFullMaterial(onion, 1, "materialOnion", new ItemStack(TinkerTools.toolShard, 1, 55),
                                  new ItemStack(TinkerTools.toolRod, 1, 55), 55);
        registered = true;
      }
      else {
        PatternBuilder.instance.registerMaterial(onion, 1, "materialOnion");
      }

      registerPartBuilding(55);
    }

    // smelting of plastic
    for(ItemStack plastic : OreDictionary.getOres("dustPlastic")) {
      Smeltery.addMelting(FluidType.getFluidType("Plastic"), plastic, 0, TConstruct.ingotLiquidValue / 4);
    }
    for(ItemStack plastic : OreDictionary.getOres("sheetPlastic")) {
      Smeltery.addMelting(FluidType.getFluidType("Plastic"), plastic, 0, TConstruct.ingotLiquidValue/4);
    }

    // wither-iron if present
    ItemStack witherIron = GameRegistry.findItemStack("progressiveautomation", "WitherIron", 1);
    if(witherIron != null)
      Smeltery.addMelting(FluidType.getFluidType("WitherIron"), witherIron, 0, TConstruct.ingotLiquidValue);

    // liquid parts
    registerCasting(witherironFluid, WITHER_ID);
    registerCasting(plassteelFluid, PLASSTEEL_ID);

    if(Loader.isModLoaded("MineFactoryReloaded"))
      registerCasting(plasticFluid, PLASTIC_ID);

    if(Loader.isModLoaded("ThermalFoundation")) {
      registerCasting(TinkerSmeltery.moltenMithrilFluid, MITHRIL_ID);
      registerCasting(TinkerSmeltery.moltenEnderiumFluid, ENDERIUM_ID);
      registerCasting(TinkerSmeltery.moltenSignalumFluid, SIGNALUM_ID);
      registerCasting(TinkerSmeltery.moltenLumiumFluid, LUMIUM_ID);
    }


    // alloying
    int amount = TConstruct.ingotLiquidValue;
    Smeltery.addAlloyMixing(new FluidStack(plassteelFluid, amount * 2),
                            new FluidStack(TinkerSmeltery.moltenSteelFluid, amount * 2),
                            new FluidStack(plasticFluid, amount));
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {

  }

  private void registerMaterial(int id, String name, int hlvl, int durability, int speed,
                                int attack, float handle, int reinforced, int color, int mass,
                                float breakchance, int drawspeed, float arrowspeed) {
    TConstructRegistry
        .addToolMaterial(id, name, hlvl, durability, speed, attack, handle, reinforced, 0,
                         EnumChatFormatting.LIGHT_PURPLE.toString(), color);
    TConstructRegistry.addArrowMaterial(id, mass, breakchance);
    TConstructRegistry.addBowMaterial(id, drawspeed, arrowspeed);
    TConstructRegistry.addDefaultShardMaterial(id);
    TConstructRegistry.addDefaultToolPartMaterial(id);
  }

  private Fluid registerFluid(String name, int color) {
    String fluidName = "molten." + name;
    String blockName = fluidName;
    // create the new fluid
    Fluid fluid = new ColoredFluid(fluidName, color).setDensity(100).setViscosity(50).setTemperature(300);
    fluid.setLuminosity(15);

    if(!FluidRegistry.registerFluid(fluid))
      fluid = FluidRegistry.getFluid(fluidName);

    LiquidBlock block = new LiquidBlock(fluid, Material.lava);
    block.setBlockName(blockName);
    GameRegistry.registerBlock(block, ColoredItemBlock.class,  blockName);

    return fluid;
  }

  private void registerPartBuilding(int materialID) {
    for (int meta = 0; meta < TinkerTools.patternOutputs.length; meta++) {
      if (TinkerTools.patternOutputs[meta] != null)
        TConstructRegistry.addPartMapping(TinkerTools.woodPattern, meta + 1, materialID, new ItemStack(TinkerTools.patternOutputs[meta], 1, materialID));
    }

    // weaponry
    for (int m = 0; m < TinkerWeaponry.patternOutputs.length; m++)
      TConstructRegistry.addPartMapping(TinkerWeaponry.woodPattern, m, materialID, new ItemStack(TinkerWeaponry.patternOutputs[m], 1, materialID));

    TConstructRegistry.addPartMapping(TinkerTools.woodPattern, 25, materialID, new ItemStack(TinkerWeaponry.arrowhead, 1, materialID));
  }

  private void registerCasting(Fluid fluid, int materialID) {
    if (fluid == null) {
      return;
    }
    LiquidCasting tableCasting = TConstructRegistry.getTableCasting();

    int fluidAmount;
    // regular tool stuff
    for (int iter = 0; iter < TinkerTools.patternOutputs.length; iter++) {
      if (TinkerTools.patternOutputs[iter] != null) {
        ItemStack cast = new ItemStack(TinkerSmeltery.metalPattern, 1, iter + 1);

        tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenAlubrassFluid,
                                                           TConstruct.ingotLiquidValue),
                                      new ItemStack(TinkerTools.patternOutputs[iter], 1,
                                                    Short.MAX_VALUE), false, 50);
        tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenGoldFluid,
                                                           TConstruct.ingotLiquidValue * 2),
                                      new ItemStack(TinkerTools.patternOutputs[iter], 1,
                                                    Short.MAX_VALUE), false, 50);

        fluidAmount =
            ((IPattern) TinkerSmeltery.metalPattern).getPatternCost(cast)
            * TConstruct.ingotLiquidValue / 2;
        ItemStack metalCast = new ItemStack(TinkerTools.patternOutputs[iter], 1, materialID);
        tableCasting.addCastingRecipe(metalCast, new FluidStack(fluid, fluidAmount), cast, 50);
        Smeltery.addMelting(FluidType.getFluidType(fluid), metalCast, 0, fluidAmount);
      }
    }

    // weaponry
    for (int i = 0; i < TinkerWeaponry.patternOutputs.length; i++) {
      ItemStack cast = new ItemStack(TinkerWeaponry.metalPattern, 1, i);

      tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenAlubrassFluid,
                                                         TConstruct.ingotLiquidValue),
                                    new ItemStack(TinkerWeaponry.patternOutputs[i], 1,
                                                  Short.MAX_VALUE), false, 50);
      tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenGoldFluid,
                                                         TConstruct.ingotLiquidValue * 2),
                                    new ItemStack(TinkerWeaponry.patternOutputs[i], 1,
                                                  Short.MAX_VALUE), false, 50);

      fluidAmount =
          TinkerWeaponry.metalPattern.getPatternCost(cast) * TConstruct.ingotLiquidValue / 2;
      ItemStack metalCast = new ItemStack(TinkerWeaponry.patternOutputs[i], 1, materialID);
      tableCasting.addCastingRecipe(metalCast, new FluidStack(fluid, fluidAmount), cast, 50);
      Smeltery.addMelting(FluidType.getFluidType(fluid), metalCast, 0, fluidAmount);
    }


    // arrowheads
    ItemStack cast = new ItemStack(TinkerSmeltery.metalPattern, 1, 25);
    tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue), new ItemStack(TinkerWeaponry.arrowhead, 1, Short.MAX_VALUE), false, 50);
    tableCasting.addCastingRecipe(cast, new FluidStack(TinkerSmeltery.moltenGoldFluid, TConstruct.ingotLiquidValue * 2), new ItemStack(TinkerWeaponry.arrowhead, 1, Short.MAX_VALUE), false, 50);

    fluidAmount = ((IPattern) TinkerSmeltery.metalPattern).getPatternCost(cast) * TConstruct.ingotLiquidValue / 2;
    ItemStack metalCast = new ItemStack(TinkerWeaponry.arrowhead, 1, materialID);
    tableCasting.addCastingRecipe(metalCast, new FluidStack(fluid, fluidAmount), cast, 50);
    Smeltery.addMelting(FluidType.getFluidType(fluid), metalCast, 0, fluidAmount);
  }
}
