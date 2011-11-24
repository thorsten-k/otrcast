package net.sf.otrcutmp4.controller.batch.video;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import net.sf.otrcutmp4.AviToMp4;
import net.sf.otrcutmp4.controller.batch.AbstactBatchGenerator;
import net.sf.otrcutmp4.model.xml.cut.Cut;
import net.sf.otrcutmp4.model.xml.cut.CutList;
import net.sf.otrcutmp4.model.xml.cut.CutListsSelected;
import net.sf.otrcutmp4.util.OtrConfig;
import net.sf.otrcutmp4.util.OtrConfig.Dir;

import org.apache.commons.configuration.Configuration;

public class VideoCutter extends AbstactBatchGenerator
{	
	private static DecimalFormat df;
	
	public VideoCutter(OtrConfig otrConfig)
	{
		super(otrConfig);
	}
	
	public void applyCutList(CutListsSelected clSelected, String inVideo, AviToMp4.Profile profile)
	{
		txt.add("");
		int counter=1;
		for(CutList cl : clSelected.getCutList())
		{
			cutList(counter, cl, inVideo, profile);
			counter++;
		}
	}
	
	private void cutList(int index, CutList cl, String inVideo,AviToMp4.Profile profile)
	{
		int counter = 1;
		for(Cut cut : cl.getCut())
		{
			if(cut.isSetInclude())
			{
				switch(profile)
				{
					case P0:	txt.add(getP0cut(index, counter, cut, inVideo));break;
					case P1:	txt.add(getP1cut(index, counter, cut, inVideo));break;
				}
				
				counter++;
			}
		}
	}
	
	private String getP0cut(int index, int counter, Cut cut, String inVideo)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(cmdFfmpeg);
		sb.append(" -ss ").append(getSecond(cut.getStart()));
		sb.append(" -t ").append(getSecond(cut.getDuration()));
		sb.append(" -i "+inVideo);
		sb.append(" -vcodec copy");
		sb.append(" -acodec copy");
		sb.append(" ").append(rpf.relativate(new File(otrConfig.getDir(Dir.TMP), index+"-"+counter+".mp4")));
		return sb.toString();
	}
	
	private String getP1cut(int index, int counter, Cut cut, String inVideo)
	{		
		StringBuffer sb = new StringBuffer();
		sb.append(cmdFfmpeg);
		sb.append(" -ss ").append(getSecond(cut.getStart()));
		sb.append(" -t ").append(getSecond(cut.getDuration()));
		sb.append(" -fflags genpts -y");
		sb.append(" -i "+inVideo);
		sb.append(" -vcodec copy");
		sb.append(" -acodec libvo_aacenc");
		sb.append(" ").append(rpf.relativate(new File(otrConfig.getDir(Dir.TMP), index+"-"+counter+".mp4")));
		return sb.toString();
	}
	
	public static synchronized String getSecond(double d)
	{
		if(df==null)
		{
			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
			dfs.setDecimalSeparator('.');
			df = new DecimalFormat("0.00",dfs);
		}
		return df.format(d);
	}
}
