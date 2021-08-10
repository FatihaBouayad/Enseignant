/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Enseignants;

import com.jfoenix.controls.JFXTextField;
import dgsescuela.DBConnection;

import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
import java.io.IOException;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hdegd
 */
public class FXMLAjouterController implements Initializable {

    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    @FXML
    private JFXTextField fxIdEnseignant;

    @FXML
    private DatePicker fxDateAjout;

    @FXML
    private JFXTextField fxNomEnseignant;

    @FXML
    private JFXTextField fxPrenomEnseignant;

    @FXML
    private JFXTextField fxTELEnseignant;
    @FXML
    private JFXTextField fxEmailEnseignant;

    @FXML
    private JFXTextField fxAdresseEnseignant;
    
    @FXML
    private Label isEmpty;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            conn= DBConnection.EtablirConnection();
            fxDateAjout.setValue(LocalDate.now());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }  
    @FXML
    public void SelectImage(){
        
    }
    

    @FXML
    private void AjouterEnseignant() {
        try {
            if (isValidCondition()) {
                if (isnewData()) {
                    String sql = "insert into enseignant values(?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(sql);
                    

                    pst.setString(1, fxIdEnseignant.getText());
                    pst.setString(2, fxDateAjout.getValue().toString());
                    pst.setString(3, fxNomEnseignant.getText());
                    pst.setString(4, fxPrenomEnseignant.getText());
                    pst.setString(5, fxTELEnseignant.getText());
                    pst.setString(6, fxEmailEnseignant.getText());
                    pst.setString(7, fxAdresseEnseignant.getText());
                    
     

                    pst.executeUpdate();
                    pst.close();
                    
                
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucess");
                    alert.setHeaderText("Sucess :   ");
                    alert.setContentText("l'enseignant :" + "  '" + fxIdEnseignant.getText() + "' " + "a été ajouté avec succès");
                    alert.showAndWait();
                    
                   // newStage.close();
                    FenetreEnseignant();
                   
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Erreur :   ");
                    alert.setContentText("cette données est deja existe!!!");
                    alert.showAndWait();
                }
          } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Vérifiez les données d'Article!!!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (ParseException ex) {
            Logger.getLogger(FXMLAjouterController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLAjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void Clavier(KeyEvent event) throws ParseException, SQLException, IOException {
         if (event.getCode() == KeyCode.ENTER)
         {
             AjouterEnseignant();

         }
    }
    
        //////////////////////  validé les condition du remplir les champ  //////////////

    private boolean isValidCondition() throws SQLException {
        isEmpty.setText("");
        boolean registration = false;
        if (isEmpty()) {
            System.out.println("Condition valid");
            registration = true;
            isEmpty.setText("done !!!");
            isEmpty.setStyle("-fx-text-fill:green;");

        } else {
            isEmpty.setText("SVP entrer tous les champs !!!");
            isEmpty.setStyle("-fx-text-fill:red;");

            System.out.println("Condition Invalid");
            registration = false;
        }

        return registration;

    }
    


    ///////////////    pour verifier les champ vide /////////////////
    private boolean isEmpty() {
        boolean isEmpty = false;
        if (fxIdEnseignant.getText().trim().isEmpty()
                || fxNomEnseignant.getText().trim().isEmpty()
                || fxPrenomEnseignant.getText().trim().isEmpty()
                || fxTELEnseignant.getText().trim().isEmpty()
                || fxEmailEnseignant.getText().trim().isEmpty()
                || fxAdresseEnseignant.getText().trim().isEmpty()) {

            //System.out.println("il y a un ou plusieur champs vide");
            isEmpty = false;
        } else {
                    
            System.out.println("il y a un ou plusieur champs vide");

            isEmpty = true;
        }
        return isEmpty;
    }

    /////////////////////   verifier si cette condidat est nouveau ou deja inscrit //////////
    private boolean isnewData() throws SQLException {

        String sql = "select * from `enseignant` where IdEns='" + fxIdEnseignant.getText() + "'";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();
        while (rs.next()) {
            pst.close();
            rs.close();
            System.out.println("isn't new data");
            return false;

        }
        pst.close();
        rs.close();
        System.out.println("is new data");
        return true;
    }
    
   public void FenetreEnseignant() throws ParseException, IOException {

            rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Enseignants/FXMLEnseignants.fxml"));
            sceneAccueil = new Scene(rootAccueil);
         


    }

}
