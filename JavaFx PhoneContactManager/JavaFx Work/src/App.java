import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

//Sergei
public class App extends Application {
	ContactsManager cm;
	Controller controller;

	public static void main(String[] args) {
		App.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			cm = new ContactsManager("contacts.dat");
			controller = new Controller(cm);
			iView cmf = new ContactsManagerFrame();
			controller.addView(cmf);
			iView cmJFX = new ContactsManagerJavaFX(primaryStage);
			controller.addView(cmJFX);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
