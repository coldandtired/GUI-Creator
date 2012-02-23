package me.coldandtired.GUI_Creator;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.Gradient;
import org.getspout.spoutapi.gui.RenderPriority;

public class GUI_confirmationbox extends GenericPopup
{
	GUI_confirmbutton confirm_button;
	GUI_closebutton cancel_button;
	
	GUI_confirmationbox(Main plugin, String command)
	{
		Gradient gg = new GenericGradient();
		gg.setX(100).setY(80).setHeight(80).setWidth(227);
		gg.setColor(new Color(0,0,0,130));
		gg.setPriority(RenderPriority.Highest);
		GenericLabel lb = new GenericLabel();
		lb.setX(105).setY(85).setHeight(10).setWidth(50);
		lb.setText("Confirm command!");
		lb.setTextColor(new Color(255,0,0));
		GenericLabel lb2 = new GenericLabel();
		lb2.setX(105).setY(110).setHeight(10).setWidth(127);
		lb2.setText(command);
		lb2.setTextColor(new Color(150,120,50));
		GenericLabel lb3 = new GenericLabel();
		lb3.setX(178).setY(144).setHeight(10).setWidth(127);
		lb3.setText("Are you sure?");
		lb3.setTextColor(new Color(255,0,0));
		confirm_button = new GUI_confirmbutton(command);
		confirm_button.setX(103).setY(138).setHeight(20).setWidth(50);
		confirm_button.setText("Yes");
		cancel_button = new GUI_closebutton();
		cancel_button.setX(274).setY(138).setHeight(20).setWidth(50);
		cancel_button.setText("No");
		attachWidgets(plugin, gg, lb, lb2, lb3, confirm_button, cancel_button);
	}
}