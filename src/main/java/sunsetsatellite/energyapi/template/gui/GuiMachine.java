package sunsetsatellite.energyapi.template.gui;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StringTranslate;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.template.containers.ContainerMachine;
import sunsetsatellite.energyapi.template.tiles.TileEntityMachine;
import sunsetsatellite.energyapi.util.Config;

public class GuiMachine extends GuiContainer {

    public String name = "Energy Machine";
    public TileEntityMachine tile;
    public GuiMachine(InventoryPlayer inventoryPlayer, TileEntityMachine tile) {
        super(new ContainerMachine(inventoryPlayer,tile));
        this.tile = tile;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f)
    {
        int i = mc.renderEngine.getTexture("assets/energyapi/gui/machine.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(i);
        int j = (width - xSize) / 2;
        int k = (height - ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, xSize, ySize);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        int color;
        //1 (red, empty) -> 0.65 (green, full)
        double color_mapped = EnergyAPI.map((float)tile.energy/(float)tile.capacity,0,1,1,0.65);
        double x_mapped = EnergyAPI.map((float)tile.energy/(float)tile.capacity, 0,1,0,15);
        Color c = new Color();
        c.fromHSB((float) color_mapped,1.0F,1.0F);
        color = c.getAlpha() << 24 | c.getRed() << 16 | c.getBlue() << 8 | c.getGreen();
        drawRectBetter(x+56,y+40, (int) x_mapped,7,color);
        GL11.glEnable(3553);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        j = (this.width - this.xSize) / 2;
        k = (this.height - this.ySize) / 2;
        if (this.tile.enoughEnergy()) {
            int i1 = this.tile.getCookProgressScaled(24);
            this.drawTexturedModalRect(j + 79, k + 34, 176, 14, i1 + 1, 16);
        }
    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTicks) {
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        super.drawScreen(x, y, renderPartialTicks);
        StringTranslate trans = StringTranslate.getInstance();
        StringBuilder text = new StringBuilder();
        if(x > i+56 && x < i+70){
            if(y > j+40 && y < j+46){
                text.append(Config.getFromConfig("energyName","Energy")).append(": ").append(tile.energy).append(" ").append(Config.getFromConfig("energySuffix","E")).append("/").append(tile.capacity).append(" ").append(Config.getFromConfig("energySuffix","E"));
                this.drawTooltip(text.toString(),x,y,8,-8,true);
            }
        }
    }

    protected void drawGuiContainerForegroundLayer()
    {
        super.drawGuiContainerForegroundLayer();
        fontRenderer.drawString(name, 50, 6, 0xFF404040);
    }


    public void initGui()
    {
        super.initGui();
    }
}
