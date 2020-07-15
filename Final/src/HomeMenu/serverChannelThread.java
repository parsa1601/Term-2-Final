package HomeMenu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class serverChannelThread extends Thread{
    String massage;
    public serverChannelThread(){}

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    @Override
    public void run() {
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Server Channel!");
        newWindow.setWidth(100);
        Pane layout = new Pane();
        layout.setPadding(new Insets(20,20,20,20));
        TextArea textArea = new TextArea();
        if(massage!=null) textArea.appendText(massage + "\n");
        layout.getChildren().add(textArea);
        Scene scene = new Scene(layout,100,200);
        newWindow.setScene(scene);
        newWindow.show();
    }
}
