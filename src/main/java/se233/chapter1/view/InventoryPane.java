package se233.chapter1.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import se233.chapter1.Launcher;
import se233.chapter1.controller.AllCustomHandler;
import se233.chapter1.model.item.BasedEquipment;

import java.util.ArrayList;

public class InventoryPane extends ScrollPane {
    private ArrayList<BasedEquipment> equipmentArray;
    public InventoryPane(){ }
    private Pane getDetailPane(){
        Pane inventoryinfoPane = new HBox(10);
        inventoryinfoPane.setBorder(null);
        inventoryinfoPane.setPadding(new Insets(25,25,25,25));
        if(equipmentArray != null){
            ImageView[] imageViewList = new ImageView[equipmentArray.size()];
            for(int i=0 ; i< equipmentArray.size() ; i++) {
                imageViewList[i] = new ImageView();
                imageViewList[i].setImage(new Image(Launcher.class.getResource(equipmentArray.get(i).getImgpath()).toString()));
                int finalI = i;
                imageViewList[i].setOnDragDetected(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        //TODO check again
                        AllCustomHandler.onDragDetected(event, equipmentArray.get(finalI),imageViewList[finalI]);
                    }
                });
                imageViewList[i].setOnDragDone(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        AllCustomHandler.onEquipDone(event);
                    }
                });
            }
            inventoryinfoPane.getChildren().addAll(imageViewList);
        }
        return inventoryinfoPane;

    }
    public void drawPane(ArrayList<BasedEquipment> equipmentArray) {
        this.equipmentArray = equipmentArray;
        Pane inventoryInfo = getDetailPane();
        this.setStyle("-fx-background-color:Red;");
        this.setContent(inventoryInfo);
    }
}
