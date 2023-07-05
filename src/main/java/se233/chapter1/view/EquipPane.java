package se233.chapter1.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se233.chapter1.Launcher;
import se233.chapter1.controller.AllCustomHandler;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.Weapon;

import static se233.chapter1.controller.AllCustomHandler.onDragDropped;
import static se233.chapter1.controller.AllCustomHandler.onDragOver;


public class  EquipPane extends ScrollPane {
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    public EquipPane() { }
    private Pane getDetailsPane() {
        Pane equipmentInfoPane = new VBox(10);
        equipmentInfoPane.setBorder(null);
        ((VBox) equipmentInfoPane).setAlignment(Pos.CENTER);
        equipmentInfoPane.setPadding(new Insets(25, 25, 25, 25));
        Label weaponLbl,armorLbl;
        //1.38
        StackPane weaponImgGroup = new StackPane();
        StackPane armorImgGroup = new StackPane();
        ImageView bg1 = new ImageView();
        ImageView bg2 = new ImageView();

        //1.38
        ImageView weaponImg = new ImageView();
        ImageView armorImg = new ImageView();

        //1.38
        bg1.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        bg2.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));

        Button btnRefresh = new Button();
        btnRefresh.setText("Refresh");
        btnRefresh.setOnAction(new AllCustomHandler.RefreshAllEquipment());

        weaponImgGroup.getChildren().add(bg1);
        armorImgGroup.getChildren().add(bg2);
        //1.38
        if (equippedWeapon != null) {
            weaponLbl = new Label("Weapon:\n"+equippedWeapon.getName());
            weaponImg.setImage(new Image(Launcher.class.getResource(equippedWeapon.
                    getImagepath()).toString()));
            //1.38
            weaponImgGroup.getChildren().add(weaponImg);
            //1.38
        } else {
            weaponLbl = new Label("Weapon:");
            weaponImg.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        }
        if (equippedArmor != null) {
            armorLbl = new Label("Armor: \n"+equippedArmor.getName());
            armorImg.setImage(new Image(Launcher.class.getResource(equippedArmor.
                    getImagepath()).toString()));
            //1.38
            armorImgGroup.getChildren().add(armorImg);
            //1.38
        } else {
            armorLbl = new Label("Armor:");
            armorImg.setImage(new Image(Launcher.class.getResource("assets/blank.png"
            ).toString()));
        }

        //1.38
        weaponImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) { onDragOver(e,"Weapon"); }
        });
        armorImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) { onDragOver(e, "Armor"); }
        });
        weaponImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) { onDragDropped(e, weaponLbl, weaponImgGroup); }
        });
        armorImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent e) { onDragDropped(e, armorLbl, armorImgGroup); }
        });
        equipmentInfoPane.getChildren().addAll(weaponLbl,weaponImgGroup,armorLbl,
                armorImgGroup,btnRefresh);
        //1.38
//        equipmentInfoPane.getChildren().addAll(weaponLbl, weaponImg, armorLbl,
//                armorImg);


        return equipmentInfoPane;
    }
    public void drawPane(Weapon equippedWeapon, Armor equippedArmor) {
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        Pane equipmentInfo = getDetailsPane();
        this.setStyle("-fx-background-color:Red;");
        this.setContent(equipmentInfo);
    }
}

