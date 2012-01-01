package net.sf.otrcutmp4.controller.cutlist.chooser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsAvailable;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliCutlistChooserController implements ControllerCutlistChooser
{
	final static Logger logger = LoggerFactory.getLogger(CliCutlistChooserController.class);

	@Override
	public CutListsSelected select(CutListsAvailable clAvailable, boolean loadCutlist)
	{
		CutListsSelected clSelected = new CutListsSelected();
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		String[] tokens = line.split( "," );
		
		if(line.length()>0)
		{
			List<Integer> selectedIndexes = new ArrayList<Integer>();
			for(String token : tokens)
			{
				selectedIndexes.add(Integer.parseInt(token));
			}	
			
			if(loadCutlist)
			{
				for(Integer id : selectedIndexes)
				{
					CutList cl = new CutList();
					cl.setId(clAvailable.getCutList().get(id).getId());
					clSelected.getCutList().add(cl);
				}
				
//				vf.setCutListsSelected(loadCutlists(selectedIndexes, vf.getCutListsAvailable()));
			}
			else
			{
				if(selectedIndexes.size()==1)
				{
					clSelected.getCutList().add(clAvailable.getCutList().get(selectedIndexes.get(0)));
				}
				else
				{
					logger.warn("You have to chosse only one cutlist");
				}
			}
		}
		return clSelected;
	}
	
}