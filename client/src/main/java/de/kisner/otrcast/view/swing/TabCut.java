package de.kisner.otrcast.view.swing;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabCut extends JPanel
{
	private static final long serialVersionUID = 1L;

	public TabCut(JTabbedPane parent)
	{
		JLabel filler = new JLabel("test");
	    filler.setHorizontalAlignment(JLabel.CENTER);
	    setLayout(new GridLayout(1, 1));
	    setPreferredSize(new Dimension(410, 50));
	    add(filler);
	    
	    parent.addTab("Tab 4", null, this,
        "Does nothing at all");
	    parent.setMnemonicAt(3, KeyEvent.VK_4);
    }
  
}
