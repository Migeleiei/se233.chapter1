package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import se233.chapter1.Launcher;
import se233.chapter1.model.DamageType;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

public class AllCustomHandler {
    public static class GenCharacterHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            setupEquip();
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.refreshPane();
        }
    }

    private static void setupEquip() {
        ArrayList<BasedEquipment> allEquipments = GenItemList.setUpItemList();
        // set chracter
        Weapon weapon = Launcher.getEquippedWeapon();
        Armor armor = Launcher.getEquippedArmor();

        BasedCharacter character = Launcher.getMainCharacter();

        if (weapon != null) {
            character.equipWeapon(weapon, true);
        }
        if (armor != null) {
            character.equipArmor(armor, true);
        }
        /// set armor

        Launcher.setEquippedArmor(null);
        Launcher.setEquippedWeapon(null);

        Launcher.setAllEquipments(allEquipments);

    }

    public static class RefreshAllEquipment implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            setupEquip();
            Launcher.refreshPane();
        }
    }

    //1.35
    public static void onDragDetected(MouseEvent event, BasedEquipment equipment,
                                      ImageView imgView) {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(equipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    //1.37
    public static void onDragOver(DragEvent event, String type) {
        Dragboard dragboard = event.getDragboard();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(
                BasedEquipment.DATA_FORMAT);

        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT) && retrievedEquipment.
                getClass().getSimpleName().equals(type))
            event.acceptTransferModes(TransferMode.MOVE);
    }

    private static boolean isDone = false;

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();
        //1.41
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        //1.41
        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(
                    BasedEquipment.DATA_FORMAT);
            //1.40
            BasedCharacter character = Launcher.getMainCharacter();
            //1.40


            if (retrievedEquipment.getClass().getSimpleName().equals("Weapon")) {

                Weapon weapon = (Weapon) retrievedEquipment;

                if (Launcher.getMainCharacter().getType() == weapon.getDamageType()
                        ||
                        ((Launcher.getMainCharacter().getType() == DamageType.physicalAndMagical)
                                && (weapon.getDamageType() == DamageType.physical || weapon.getDamageType() == DamageType.magical)
                        )
                ) {
                    if (Launcher.getEquippedWeapon() != null) {
                        allEquipments.add(Launcher.getEquippedWeapon());
                    }


                    //1.41
                    Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                    //1.40
                    character.equipWeapon((Weapon) retrievedEquipment, false);
                    isDone = true;
                    //1.40
                }

                //1.41


            } else if (retrievedEquipment.getClass().getSimpleName().equals("Armor")
                    &&
                    Launcher.getMainCharacter().getType() != DamageType.physicalAndMagical
            ) {


                //1.41
                if (Launcher.getEquippedArmor() != null)
                    allEquipments.add(Launcher.getEquippedArmor());
                //1.41
                Launcher.setEquippedArmor((Armor) retrievedEquipment);
                //1.40
                character.equipArmor((Armor) retrievedEquipment, false);
                isDone = true;
                //1.40
            }
            //1.40
            Launcher.setMainCharacter(character);
            //1.41
            Launcher.setAllEquipments(allEquipments);
            //1.41
            Launcher.refreshPane();
            //1.40
            ImageView imgView = new ImageView();
            if (imgGroup.getChildren().size() != 1) {
                imgGroup.getChildren().remove(1);
                Launcher.refreshPane();
            }
            lbl.setText(retrievedEquipment.getClass().getSimpleName() + ":\n" +
                    retrievedEquipment.getName());
            imgView.setImage(new Image(Launcher.class.getResource(retrievedEquipment.getImagepath()).toString()));
            imgGroup.getChildren().add(imgView);
            dragCompleted = true;

        }


        event.setDropCompleted(dragCompleted);
    }
    //1.37

    //1.41
    public static void onEquipDone(DragEvent event) {


        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment) dragboard.getContent(
                BasedEquipment.DATA_FORMAT);
        int pos = -1;
        for (int i = 0; i < allEquipments.size(); i++) {
            if (allEquipments.get(i).getName().equals(retrievedEquipment.getName())) {
                pos = i;
            }
        }
        if (pos != -1) {
            if (isDone) {
                allEquipments.remove(pos);
                isDone = false;
            }

        }
        Launcher.setAllEquipments(allEquipments);
        Launcher.refreshPane();
    }

    public static void onDroppingOut(DragEvent event) {
        Dragboard dragboard = event.getDragboard();

    }
    //1.41


}

