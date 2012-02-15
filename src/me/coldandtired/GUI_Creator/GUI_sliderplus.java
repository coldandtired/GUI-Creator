package me.coldandtired.GUI_Creator;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.Widget;

public class GUI_sliderplus extends GenericButton
{
	GUI gui;
	
	public GUI_sliderplus(String text, GUI gui) 
	{
		super(text);
		this.gui = gui;
	}

	public void onButtonClick(ButtonClickEvent event) 
	{
		for (Widget w : this.getContainer().getChildren())
		{
			if (w instanceof GUI_slider)
			{
				GUI_slider slider = (GUI_slider)w;
				int i = ((int) (slider.getSliderPosition() * slider.max)) + 1;
				if (i <= slider.max)
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