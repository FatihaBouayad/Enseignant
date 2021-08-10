/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgsescuela.Enseignants;

import com.jfoenix.controls.JFXTextField;
import dgsescuela.DBConnection;
import static dgsescuela.Enseignants.FXMLEnseignantsController.StageEnseignant;
import static dgsescuela.LoginPackage.loginController.rootAccueil;
import static dgsescuela.LoginPackage.loginController.sceneAccueil;
import dgsescuela.Modele.ModeleEnseignantsStatic;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author hdegd
 */
public class FXMLModifierController implements Initializable {

    Connection conn;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
@FXML
    private JFXTextField fxIdEns;

    @FXML
    private DatePicker fxDateAjout;

    @FXML
    private JFXTextField fxNomEns;

    @FXML
    private JFXTextField fxPrenomEns;

    @FXML
    private JFXTextField fxTELEns;

    @FXML
    private JFXTextField fxAdresseEns;
     @FXML
    private JFXTextField fxEmailEns;
    
    @FXML
    private Label isEmpty;
    
    
    ModeleEnseignantsStatic CurrentObjetStatic = new ModeleEnseignantsStatic();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            // TODO
            conn= DBConnection.EtablirConnection();
            fxDateAjout.setValue(LocalDate.now());
            Init();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLModifierController.class.getName()).log(Level.SEVERE, null, ex);
        }
       

    }    
    
        @FXML
    void Clavier(KeyEvent event) throws ParseException, SQLException, IOException {
         if (event.getCode() == KeyCode.ENTER)
         {
             ModifierEnseignant();

         }
    }
    
    
    private void Init(){
        System.out.println("ccccccccccc "+CurrentObjetStatic.getIdEnseignant());
        if(CurrentObjetStatic==null || CurrentObjetStatic.getIdEnseignant().equals("")){
            
               Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur :   ");
                alert.setContentText("Veuillez Selectionner un enseignant d'abord!!!");
                alert.showAndWait();
                
        }else{
        fxIdEns.setText(CurrentObjetStatic.getIdEnseignant());
      //  fxDate.setText(CurrentObjetStatic.getDate());
         fxNomEns.setText(CurrentObjetStatic.getNomEnseignant());
        fxPrenomEns.setText(CurrentObjetStatic.getPrenomEnseignant());
        fxTELEns.setText(CurrentObjetStatic.getTelEnseignant());
        fxEmailEns.setText(CurrentObjetStatic.getEmailEnseignant());
        fxAdresseEns.setText(CurrentObjetStatic.getAdresseEnseignant());
        }
        
        
    }
    

    @FXML
    private void ModifierEnseignant() {
        try {
            if (isValidCondition()) {
           
                  String sql = "update enseignant set IdEns=?, Date_Ajout=?, NomEns=?, PrenomEns=?, Tel=?,Email=?, Adresse=? where IdEns='"+CurrentObjetStatic.getIdEnseignant()+"'";
                   //String sql = "";
                   pst = conn.prepareStatement(sql);

                    pst.setString(1, fxIdEns.getText());
                    pst.setString(2, fxDateAjout.getValue().toString());
                    pst.setString(3, fxNomEns.getText());
                    pst.setString(4, fxPrenomEns.getText());
                     pst.setString(5, fxTELEns.getText());
                      pst.setString(6, fxEmailEns.getText());
                    pst.setString(7, fxAdresseEns.getText());
                   
                    
     

                    pst.executeUpdate();
                    pst.close();
                    
                
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Sucess");
                    alert.setHeaderText("Sucess :   ");
                    alert.setContentText("l'enseignant :" + "  '" + fxIdEns.getText() + "' " + "a été Bien Modifier");
                    alert.showAndWait();
                    
                    StageEnseignant.close();
                    FenetreEnseignant();
                   
                
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
            Logger.getLogger(FXMLModifierController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLModifierController.class.getName()).log(Level.SEVERE, null, ex);
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
        if (fxIdEns.getText().trim().isEmpty()
                || fxNomEns.getText().trim().isEmpty()
                || fxPrenomEns.getText().trim().isEmpty()
                || fxTELEns.getText().trim().isEmpty()
                || fxEmailEns.getText().trim().isEmpty()
                || fxAdresseEns.getText().trim().isEmpty() ) {

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

        String sql = "select * from `enseignant` where IdEns='" + fxIdEns.getText() + "'";
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
    @FXML
    public void SelectImage(){
        
    }
    
           public void FenetreEnseignant() throws ParseException, IOException {

            rootAccueil = FXMLLoader.load(getClass().getResource("/dgsescuela/Enseignants/FXMLEnseignants.fxml"));
            sceneAccueil = new Scene(rootAccueil);
         
      


    }
}
