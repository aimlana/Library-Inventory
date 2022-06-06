import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;

public class Controller implements Initializable{

    @FXML
    private Label label;

    @FXML
    private Button butHapus;

    @FXML
    private Button butPerbarui;

    @FXML
    private Button butTambah;

    @FXML
    private TableView<Book> tabelBuku;

    @FXML
    private TableColumn<Book, String> tabelId;

    @FXML
    private TableColumn<Book, String> tabelJudul;

    @FXML
    private TableColumn<Book, String> tabelPengarang;

    @FXML
    private TableColumn<Book, String> tabelPenerbit;

    @FXML
    private TableColumn<Book, String> tabelTahun;

    @FXML
    private TableColumn<Book, String> tabelJumlah;

    @FXML
    private TextField txtJudul;

    @FXML
    private TextField txtPengarang;

    @FXML
    private TextField txtPenerbit;

    @FXML
    private TextField txtTahun;

    @FXML
    private TextField txtJumlah;

    @FXML
    void add(ActionEvent event) {

        Connect();
        String judul = txtJudul.getText();
        String pengarang = txtPengarang.getText();
        String penerbit = txtPenerbit.getText();
        String tahun = txtTahun.getText();
        String jumlah = txtJumlah.getText();

        try {
            pst = con.prepareStatement("insert into tabelbuku(judul,pengarang,penerbit,tahun,jumlah)values(?,?,?,?,?)");
            pst.setString(1, judul);
            pst.setString(2, pengarang);
            pst.setString(3, penerbit);
            pst.setString(4, tahun);
            pst.setString(5, jumlah);
            pst.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library Inventory");
            alert.setHeaderText("Inventaris Buku");
            alert.setContentText("Buku berhasil ditambahkan");
            
            alert.showAndWait();
            tabelBuku();

            txtJudul.setText("");
            txtPengarang.setText("");
            txtPenerbit.setText("");
            txtTahun.setText("");
            txtJumlah.setText("");
        }
        catch(SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tabelBuku(){
        Connect();
          ObservableList<Book> books = FXCollections.observableArrayList();
       try 
       {
           pst = con.prepareStatement("select id,judul,pengarang,penerbit,tahun,jumlah from tabelbuku");  
           ResultSet rs = pst.executeQuery();
      {
        while (rs.next()) {
            Book bk = new Book();
            bk.setId(rs.getString("id"));
            bk.setJudul(rs.getString("judul"));
            bk.setPengarang(rs.getString("pengarang"));
            bk.setPenerbit(rs.getString("penerbit"));
            bk.setTahun(rs.getString("tahun"));
            bk.setJumlah(rs.getString("jumlah"));
            books.add(bk);
       }
    } 
            tabelBuku.setItems(books);
            tabelId.setCellValueFactory(f -> f.getValue().idProperty());
            tabelJudul.setCellValueFactory(f -> f.getValue().judulProperty());
            tabelPengarang.setCellValueFactory(f -> f.getValue().pengarangProperty());
            tabelPenerbit.setCellValueFactory(f -> f.getValue().penerbitProperty());
            tabelTahun.setCellValueFactory(f -> f.getValue().tahunProperty());
            tabelJumlah.setCellValueFactory(f -> f.getValue().jumlahProperty());
                
       }
       catch (SQLException ex) {
           Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       tabelBuku.setRowFactory( tv -> {
           TableRow<Book> myRow = new TableRow<>();
           myRow.setOnMouseClicked (event -> 
           {
               if (event.getClickCount() == 1 && (!myRow.isEmpty())) {
                   myIndex = tabelBuku.getSelectionModel().getSelectedIndex();
                   
                   id = Integer.parseInt(String.valueOf(tabelBuku.getItems().get(myIndex).getId()));
                   txtJudul.setText(tabelBuku.getItems().get(myIndex).getJudul());
		           txtPengarang.setText(tabelBuku.getItems().get(myIndex).getPengarang());
                   txtPenerbit.setText(tabelBuku.getItems().get(myIndex).getPenerbit());
                   txtTahun.setText(tabelBuku.getItems().get(myIndex).getTahun());
                   txtJumlah.setText(tabelBuku.getItems().get(myIndex).getJumlah());
                }
		    });
		        return myRow;
                   });   

    }

    @FXML
    void delete(ActionEvent event) {

        myIndex = tabelBuku.getSelectionModel().getSelectedIndex();
		 
        id = Integer.parseInt(String.valueOf(tabelBuku.getItems().get(myIndex).getId()));
                     

        try {
            pst = con.prepareStatement("delete from tabelbuku where id = ? ");
            pst.setInt(1, id);
            pst.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library Inventory");
            alert.setHeaderText("Inventaris Buku");
            alert.setContentText("Data berhasil dihapus");
            
            alert.showAndWait();
            tabelBuku();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void update(ActionEvent event) {

        String judul, pengarang, penerbit, tahun, jumlah;

        myIndex = tabelBuku.getSelectionModel().getSelectedIndex();
		 
        id = Integer.parseInt(String.valueOf(tabelBuku.getItems().get(myIndex).getId()));
           
            judul = txtJudul.getText();
            pengarang = txtPengarang.getText();
            penerbit = txtPenerbit.getText();
            tahun = txtTahun.getText();
            jumlah = txtJumlah.getText();

        try {
            pst = con.prepareStatement("update tabelbuku set judul = ?,pengarang = ?,penerbit = ?, tahun = ?, jumlah = ? where id = ? ");
            pst.setString(1, judul);
            pst.setString(2, pengarang);
            pst.setString(3, penerbit);
            pst.setString(4, tahun);
            pst.setString(5, jumlah);
            pst.setInt(6, id);
            pst.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library Inventory");
            alert.setHeaderText("Inventaris Buku");
            alert.setContentText("Data berhasil diperbarui");
            
            alert.showAndWait();
            tabelBuku();
        } 
        catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    Connection con;
    PreparedStatement pst;
    int myIndex;
    int id;

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/dbinventarisperpus","root","");
        } catch (ClassNotFoundException ex) {
          
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
    }

    @Override
        public void initialize(URL url, ResourceBundle rb) {
        Connect();
        tabelBuku();
    }

}
