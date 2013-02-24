package me.coldandtired.GUI_Creator;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.Widget;

public class GuiSliderMinus extends GenericButton
{
	Gui gui;
	
	public GuiSliderMinus(String text, Gui gui) 
	{
		super(text);
		this.gui = gui;
	}

	public void onButtonClick(ButtonClickEvent event) 
	{
		for (Widget w : this.getContainer().getChildren())
		{
			if (w instanceof GuiSlider)
			{
				GuiSlider slider = (GuiSlider)w;
				int i = ((int) (slider.getSliderPosition() * slider.max)) - 1;
				if (i >= 0)
				{
					slider.setSliderPosition((float)i / slider.max);
					slider.text = slider.get_full_text(i);
					if (!slider.skin_texture.equalsIgnoreCase("")) gui.update_texture(slider.skin_texture, slider.get_value(i));
					gui.replace_text();
				}
			}
		}		
	}
}
