package notepaded;

import java.io.Serializable;

public class Notepad implements Serializable{
	private static final long	serialVersionUID	= 1L;
	
	boolean isEncrypted;
	String data;
	
	public Notepad(){
		isEncrypted=false;
		data="";
	}
	
	public Notepad(boolean isEncrypted,String data){
		this.isEncrypted=isEncrypted;
		this.data=data;
	}
	
	public void setEncrypted(boolean isEncrypted){
		this.isEncrypted=isEncrypted;
	}
	
	public boolean isEncrypted(){
		return this.isEncrypted;
	}
	
	public void setData(String data){
		this.data=data;
	}
	
	public String getData(){
		return this.data;
	}
}
