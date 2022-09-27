package notepaded;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileOpenSave {

	public static void saveNotepad(Notepad np, File file){					 
			try {
				new ObjectOutputStream(new FileOutputStream(file)).writeObject(np);
			} catch (FileNotFoundException e) {			
				e.printStackTrace();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
	}
	
	public static Notepad openNotepad(File file){					 
		ObjectInputStream ois=null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			return (Notepad)ois.readObject();
		} catch (ClassNotFoundException e) {
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e1) {					

				}
			}
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {			
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e1) {					

				}
			}
			e.printStackTrace();
		} catch (IOException e) {			
			if(ois!=null){
				try {
					ois.close();
				} catch (IOException e1) {					

				}
			}
			e.printStackTrace();
		}
		
		return null;
		
	}
}
