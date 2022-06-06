import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private final StringProperty id;
    private final SimpleStringProperty judul;
    private final SimpleStringProperty pengarang;
    private final SimpleStringProperty penerbit;
    private final SimpleStringProperty tahun;
    private final SimpleStringProperty jumlah;
     
    public Book() {
        id = new SimpleStringProperty(this, "id");
        judul = new SimpleStringProperty(this, "judul");
        pengarang = new SimpleStringProperty(this, "pengarang");
        penerbit = new SimpleStringProperty(this, "penerbit");
        tahun = new SimpleStringProperty(this, "tahun");
        jumlah = new SimpleStringProperty(this, "jumlah");

        
    }

    public StringProperty idProperty() { return id; }
    public String getId() { return id.get(); }
    public void setId(String newId) { id.set(newId); }

    public StringProperty judulProperty() { return judul; }
    public String getJudul() { return judul.get(); }
    public void setJudul(String newJudul) { judul.set(newJudul); }
    
    public StringProperty pengarangProperty() { return pengarang; }
    public String getPengarang() { return pengarang.get(); }
    public void setPengarang(String newPengarang) { pengarang.set(newPengarang); }

    public StringProperty penerbitProperty() { return penerbit; }
    public String getPenerbit() { return penerbit.get(); }
    public void setPenerbit(String newPenerbit) { penerbit.set(newPenerbit); }

    public StringProperty tahunProperty() { return tahun; }
    public String getTahun() { return tahun.get(); }
    public void setTahun(String newTahun) { tahun.set(newTahun); }

    public StringProperty jumlahProperty() { return jumlah; }
    public String getJumlah() { return jumlah.get(); }
    public void setJumlah(String newJumlah) { jumlah.set(newJumlah); 
    
    }
}