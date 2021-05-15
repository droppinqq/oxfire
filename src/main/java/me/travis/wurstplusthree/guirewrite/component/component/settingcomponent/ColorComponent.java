package me.travis.wurstplusthree.guirewrite.component.component.settingcomponent;

import me.travis.wurstplusthree.WurstplusThree;
import me.travis.wurstplusthree.guirewrite.WurstplusGuiNew;
import me.travis.wurstplusthree.guirewrite.component.Component;
import me.travis.wurstplusthree.guirewrite.component.component.HackButton;
import me.travis.wurstplusthree.hack.client.Gui;
import me.travis.wurstplusthree.setting.type.ColourSetting;
import me.travis.wurstplusthree.util.RenderUtil2D;
import org.lwjgl.input.Mouse;

import java.awt.*;

/**
 * @author Madmegsox1
 * @since 29/04/2021
 * @Author wallhacks0
 */

public class ColorComponent extends Component {
    private final ColourSetting set;
    private final HackButton parent;
    private Color finalColor;
    private int offset;
    private boolean isOpen;
    private boolean firstTimeOpen;

    // TODO : make color picker
    public ColorComponent(ColourSetting value, HackButton button, int offset) {
        this.set = value;
        this.parent = button;
        this.offset = offset;
        this.isOpen = false;
        this.firstTimeOpen = true;
    }

    @Override
    public void renderComponent(int mouseX, int mousY) {
        RenderUtil2D.drawRectMC(parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET, parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET, parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET + 95, parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET, Gui.INSTANCE.buttonColor.getValue().hashCode());
        RenderUtil2D.drawRectMC(parent.parent.getX() + parent.parent.getWidth() - WurstplusGuiNew.SETTING_OFFSET - 5, parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET, parent.parent.getX() + parent.parent.getWidth() - WurstplusGuiNew.SETTING_OFFSET, parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET, Gui.INSTANCE.buttonColor.getValue().hashCode());
        RenderUtil2D.drawRectMC(parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET + 95, parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET, parent.parent.getX() + parent.parent.getWidth() - WurstplusGuiNew.SETTING_OFFSET - 5, parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET + 3, Gui.INSTANCE.buttonColor.getValue().hashCode());
        RenderUtil2D.drawRectMC(parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET + 95, parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET, parent.parent.getX() + parent.parent.getWidth() - WurstplusGuiNew.SETTING_OFFSET - 5, parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET - 3, Gui.INSTANCE.buttonColor.getValue().hashCode());
        RenderUtil2D.drawBorderedRect(parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET + 95, parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET + 3, parent.parent.getX() + parent.parent.getWidth() - WurstplusGuiNew.SETTING_OFFSET - 5, parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET - 3, 1, this.set.getValue().hashCode(), WurstplusGuiNew.GUI_COLOR);
        WurstplusThree.GUI_FONT_MANAGER.drawStringWithShadow(set.getName(), parent.parent.getX() + WurstplusGuiNew.SUB_FONT_SIZE, parent.parent.getY() + offset + 3 + WurstplusGuiNew.MODULE_OFFSET, Gui.INSTANCE.fontColor.getValue().hashCode());
        if (this.isOpen) {
            this.drawPicker(set, parent.parent.getX() + 7, parent.parent.getY() + offset + 17, parent.parent.getX() + 100, parent.parent.getY() + offset + 17, parent.parent.getX() + 7, parent.parent.getY() + offset + 52 + 17, mouseX, mousY);
            set.setValue(finalColor);
        }
    }


    @Override
    public void setOff(int newOff) {
        offset = newOff;

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (isMouseOnButton(mouseX, mouseY) && parent.isOpen && button == 1) {
            for (Component comp : parent.parent.getComponents()) {
                if (comp instanceof HackButton) {
                    if (((HackButton) comp).isOpen) {
                        for (Component comp2 : ((HackButton) comp).getChildren()) {
                            if (comp2 instanceof ColorComponent) {
                                if (((ColorComponent) comp2).isOpen && comp2 != this) {
                                    ((ColorComponent) comp2).setOpen(false);
                                    this.parent.parent.refresh();
                                }
                            }
                        }
                    }
                }
            }
            setOpen(!isOpen);
            this.parent.parent.refresh();
        }
        if (!this.isOpen && !this.firstTimeOpen) {
            this.firstTimeOpen = true;
        }
        if (this.isOpen && firstTimeOpen) {
            boolean flag = false;
            for (Component component : this.parent.getChildren()) {
                if (!flag && component == this) {
                    flag = true;
                    continue;
                }
                if (flag) {
                    component.setOff(component.getOffset() + (WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET) * 5);
                }
            }
            this.firstTimeOpen = false;
        }

    }

    public void drawPicker(ColourSetting subColor, int pickerX, int pickerY, int hueSliderX, int hueSliderY, int alphaSliderX, int alphaSliderY, int mouseX, int mouseY) {
        float[] color = new float[] {
                0, 0, 0, 0
        };

        try {
            color = new float[] {
                    Color.RGBtoHSB(subColor.getColor().getRed(), subColor.getColor().getGreen(), subColor.getColor().getBlue(), null)[0], Color.RGBtoHSB(subColor.getColor().getRed(), subColor.getColor().getGreen(), subColor.getColor().getBlue(), null)[1], Color.RGBtoHSB(subColor.getColor().getRed(), subColor.getColor().getGreen(), subColor.getColor().getBlue(), null)[2], subColor.getColor().getAlpha() / 255f
            };
        } catch (Exception ignored) {

        }

        boolean pickingColor = false;
        boolean pickingHue = false;
        boolean pickingAlpha = false;

        int pickerWidth = 80;
        int pickerHeight = 50;
        int hueSliderWidth = 10;
        int hueSliderHeight = 50;
        int alphaSliderHeight = 10;
        int alphaSliderWidth = 100;


        if (Mouse.isButtonDown(0) && mouseOver(pickerX, pickerY, pickerX + pickerWidth, pickerY + pickerHeight, mouseX, mouseY))
            pickingColor = true;
        if (Mouse.isButtonDown(0) && mouseOver(hueSliderX, hueSliderY, hueSliderX + hueSliderWidth, hueSliderY + hueSliderHeight, mouseX, mouseY))
            pickingHue = true;
        if (Mouse.isButtonDown(0) && mouseOver(alphaSliderX, alphaSliderY, alphaSliderX + alphaSliderWidth, alphaSliderY + alphaSliderHeight, mouseX, mouseY))
            pickingAlpha = true;

        if (pickingHue) {
            float restrictedY = (float) Math.min(Math.max(hueSliderY, mouseY), hueSliderY + hueSliderHeight);
            color[0] = (restrictedY - (float) hueSliderY) / hueSliderHeight;
        }

        if (pickingAlpha) {
            float restrictedX = (float) Math.min(Math.max(alphaSliderX, mouseX), alphaSliderX + pickerWidth);
            color[3] = 1 - (restrictedX - (float) alphaSliderX) / pickerWidth;
        }

        if (pickingColor) {
            float restrictedX = (float) Math.min(Math.max(pickerX, mouseX), pickerX + pickerWidth);
            float restrictedY = (float) Math.min(Math.max(pickerY, mouseY), pickerY + pickerHeight);
            color[1] = (restrictedX - (float) pickerX) / pickerWidth;
            color[2] = 1 - (restrictedY - (float) pickerY) / pickerHeight;
        }

        int selectedColor = Color.HSBtoRGB(color[0], 1.0f, 1.0f);

        float selectedRed = (selectedColor >> 16 & 0xFF) / 255.0f;
        float selectedGreen = (selectedColor >> 8 & 0xFF) / 255.0f;
        float selectedBlue = (selectedColor & 0xFF) / 255.0f;

        RenderUtil2D.drawPickerBase(pickerX, pickerY, pickerWidth, pickerHeight, selectedRed, selectedGreen, selectedBlue, color[3]);

        drawHueSlider(hueSliderX, hueSliderY, hueSliderWidth, hueSliderHeight, color[0]);

        int cursorX = (int) (pickerX + color[1] * pickerWidth);
        int cursorY = (int) ((pickerY + pickerHeight) - color[2] * pickerHeight);

        RenderUtil2D.drawRectMC(cursorX - 2, cursorY - 2, cursorX + 2, cursorY + 2, -1);

        drawAlphaSlider(alphaSliderX, alphaSliderY, pickerWidth, alphaSliderHeight, selectedRed, selectedGreen, selectedBlue, color[3]);

        finalColor = alphaIntegrate(new Color(Color.HSBtoRGB(color[0], color[1], color[2])), color[3]);
    }

    public static Color alphaIntegrate(Color color, float alpha) {
        float red = (float) color.getRed() / 255;
        float green = (float) color.getGreen() / 255;
        float blue = (float) color.getBlue() / 255;
        return new Color(red, green, blue, alpha);
    }

    public void drawHueSlider(int x, int y, int width, int height, float hue) {
        int step = 0;

        if (height > width) {
            RenderUtil2D.drawRectMC(x, y, x + width, y + 4, 0xFFFF0000);
            y += 4;

            for (int colorIndex = 0; colorIndex < 6; colorIndex++) {
                int previousStep = Color.HSBtoRGB((float) step / 6, 1.0f, 1.0f);
                int nextStep = Color.HSBtoRGB((float) (step + 1) / 6, 1.0f, 1.0f);
                RenderUtil2D.drawGradientRect(x, y + step * (height / 6), x + width, y + (step + 1) * (height / 6), previousStep, nextStep);
                step++;
            }

            int sliderMinY = (int) (y + (height * hue)) - 4;
            RenderUtil2D.drawRectMC(x, sliderMinY - 1, x + width, sliderMinY + 1,-1);
        } else {
            for (int colorIndex = 0; colorIndex < 6; colorIndex++) {
                int previousStep = Color.HSBtoRGB((float) step / 6, 1.0f, 1.0f);
                int nextStep = Color.HSBtoRGB((float) (step + 1) / 6, 1.0f, 1.0f);
                RenderUtil2D.gradient(x + step * (width / 6), y, x + (step + 1) * (width / 6), y + height, previousStep, nextStep, true);
                step++;
            }

            int sliderMinX = (int) (x + (width * hue));
            RenderUtil2D.drawRectMC(sliderMinX - 1, y, sliderMinX + 1, y + height, -1);
        }
    }

    public void drawAlphaSlider(int x, int y, int width, int height, float red, float green, float blue, float alpha) {
        boolean left = true;
        int checkerBoardSquareSize = height / 2;

        for (int squareIndex = -checkerBoardSquareSize; squareIndex < width; squareIndex += checkerBoardSquareSize) {
            if (!left) {
                RenderUtil2D.drawRectMC(x + squareIndex, y, x + squareIndex + checkerBoardSquareSize, y + height, 0xFFFFFFFF);
                RenderUtil2D.drawRectMC(x + squareIndex, y + checkerBoardSquareSize, x + squareIndex + checkerBoardSquareSize, y + height, 0xFF909090);

                if (squareIndex < width - checkerBoardSquareSize) {
                    int minX = x + squareIndex + checkerBoardSquareSize;
                    int maxX = Math.min(x + width, x + squareIndex + checkerBoardSquareSize * 2);
                    RenderUtil2D.drawRectMC(minX, y, maxX, y + height, 0xFF909090);
                    RenderUtil2D.drawRectMC(minX, y + checkerBoardSquareSize, maxX, y + height,0xFFFFFFFF);
                }
            }

            left = !left;
        }

        RenderUtil2D.drawLeftGradientRect(x, y, x + width, y + height, new Color(red, green, blue, 1).getRGB(), 0);
        int sliderMinX = (int) (x + width - (width * alpha));
        RenderUtil2D.drawRectMC(sliderMinX - 1, y, sliderMinX + 1, y + height, -1);
    }


    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
    }

    public boolean isMouseOnButton(int x, int y) {
        return x > this.parent.parent.getX() + WurstplusGuiNew.SETTING_OFFSET && x < this.parent.parent.getX() + WurstplusGuiNew.WIDTH - WurstplusGuiNew.SETTING_OFFSET && y > this.parent.parent.getY() + offset + WurstplusGuiNew.MODULE_OFFSET && y < this.parent.parent.getY() + offset + WurstplusGuiNew.HEIGHT + WurstplusGuiNew.MODULE_OFFSET;
    }

    public void setOpen(boolean v) {
        this.isOpen = v;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public HackButton getParent() {
        return parent;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    public static boolean mouseOver(int minX, int minY, int maxX, int maxY, int mX, int mY) {
        return mX >= minX && mY >= minY && mX <= maxX && mY <= maxY;
    }
}
