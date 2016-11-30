package assignment7;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Observer;

public class ClientObserver extends ObjectOutputStream implements Observer {
	public ClientObserver(OutputStream out) throws IOException {
		super(out);
	}
	@Override
	public void update(Observable o, Object arg) {
		//this.println(arg); //writer.println(arg);
		try {
			this.writeObject(arg);
			this.flush(); //writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
