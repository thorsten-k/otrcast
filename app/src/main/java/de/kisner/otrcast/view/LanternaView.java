package de.kisner.otrcast.view;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import de.kisner.otrcast.interfaces.view.ViewSrcDirProcessor;
import de.kisner.otrcast.model.xml.cut.VideoFile;
import de.kisner.otrcast.model.xml.cut.VideoFiles;
import net.sf.exlp.util.xml.JaxbUtil;

public class LanternaView implements ViewSrcDirProcessor
{
	final static Logger logger = LoggerFactory.getLogger(LanternaView.class);

	Table<String> table;
	MultiWindowTextGUI gui;
	
	private SimpleDateFormat sdfDate,sdfTime;
	
	public LanternaView() throws IOException
	{
		sdfDate = new SimpleDateFormat("dd.MM.yyyy");
		sdfTime  = new SimpleDateFormat("HH:mm");
		
		Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        
        table = new Table<String>("Nr","Channel","Date","Time","Name");
        
        
        // Create window to hold the panel
        BasicWindow window = new BasicWindow();
        window.setComponent(table);

        // Create gui and start gui
        gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

        gui.addWindow(window);
	}
	
	@Override
	public void readFilesInDir(File srcDir)
	{
		logger.debug("Searching files in "+srcDir.getAbsolutePath());
	}

	@Override
	public void found(int i)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(i);
		sb.append(" ").append("file");
		if(i!=1){sb.append("s");}
		sb.append(" found.");
		logger.info(sb.toString());
	}

	@Override
	public void found(VideoFiles files)
	{
		logger.warn("Adding files");
		JaxbUtil.info(files);
		if(files.isSetVideoFile())
		{
			int i=1;
			for(VideoFile vf : files.getVideoFile())
			{
				table.getTableModel().addRow(i+"",
											vf.getOtrId().getTv().getChannel(),
											sdfDate.format(vf.getOtrId().getTv().getAirtime().toGregorianCalendar().getTime()), 
											sdfTime.format(vf.getOtrId().getTv().getAirtime().toGregorianCalendar().getTime()), 
											vf.getOtrId().getTv().getName());
				i++;
			}
		}
		logger.warn("gui.isPendingUpdate() "+gui.isPendingUpdate());
		try {
			gui.updateScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}