package sunsetsatellite.energyapi.mixin;

import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sunsetsatellite.energyapi.EnergyAPI;
import sunsetsatellite.energyapi.impl.ItemEnergyContainer;
import sunsetsatellite.energyapi.interfaces.mixins.IGuiContainer;
import sunsetsatellite.energyapi.template.items.ItemBattery;
import sunsetsatellite.energyapi.util.ICustomDescription;

import java.util.ArrayList;

@Debug( export = true )
@Mixin(
        value = GuiContainer.class,
        remap = false
)
public class GuiContainerMixin extends GuiScreen
    implements IGuiContainer
{

    @Shadow private static RenderItem itemRenderer;

    @Inject(
            method = "drawScreen",
            remap = false,
            at = @At(value = "INVOKE",target = "Lnet/minecraft/src/GuiContainer;formatDescription(Ljava/lang/String;I)Ljava/lang/String;", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void setDescription(int x, int y, float renderPartialTicks, CallbackInfo ci, int centerX, int centerY, Slot slot, InventoryPlayer inventoryplayer, StringTranslate trans, StringBuilder text, boolean multiLine, boolean control, boolean shift, boolean showDescription, boolean isCrafting, String itemName, String itemNick, boolean debug){
        ItemStack stack = slot.getStack();
        if(stack != null && stack.getItem() instanceof ItemBlock){
            Block block = Block.blocksList[stack.getItem().itemID];
            if(block instanceof ICustomDescription){
                text.append(((ICustomDescription) block).getDescription(stack)).append("\n");
            }
        }
        if(stack != null && stack.getItem() instanceof ICustomDescription){
            text.append(((ICustomDescription) stack.getItem()).getDescription(stack)).append("\n");
        }
    }

}
