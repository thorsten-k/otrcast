package de.kisner.otrcast.model.ejb;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import de.kisner.otrcast.interfaces.model.Image;

@Entity
public class OtrImage implements Serializable,Image
{
	private static final long serialVersionUID = 1L;

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Override public void setId(long id) {this.id = id;}
	@Override public long getId() {return id;}
	
	private String fileType;
	@Override public String getFileType() {return fileType;}
	@Override public void setFileType(String fileType) {this.fileType = fileType;}

    private String url;
    @Override public String getUrl(){return url;}
    @Override public void setUrl(String url){this.url = url;}
	
	@Lob
	private byte[] data;
	@Override public byte[] getData() {return data;}
	@Override public void setData(byte[] data) {this.data = data;}

	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
			sb.append("[").append(id).append("]");
			sb.append(" fileType=").append(fileType);
			sb.append(" size=").append(data.length);
		return sb.toString();
	}
}