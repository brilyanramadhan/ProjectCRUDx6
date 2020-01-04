
package db;
import base.*;
import java.sql.*;
/**
 *
 * @author brilyan
 */
public class DbTransaksi extends BaseTransaksi{
    private String nama_pegawai;
    
    public void setPegawai(String pgw){
        this.nama_pegawai = pgw;
    }
    public String getPegawai(){
        return this.nama_pegawai;
    }
    
    @Override
    public void newTransaksi(DbConnection db, String nama, String banyak) {
        Connection conn = db.dbCon;
        try {
            String sql1 = "insert into tb_transaksi(banyak) values (?)";
            PreparedStatement st1 = conn.prepareStatement(sql1);
            st1.setString(1, banyak);
            st1.executeUpdate();
            String sql2 = "select * from tb_barang where nama_barang ='"+nama+"'";
            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            rs2.next();
            String sql3 = "select * from tb_pegawai where nama_pegawai='"+getPegawai()+"'";
            Statement st3 = conn.createStatement();
            ResultSet rs3 = st3.executeQuery(sql3);
            rs3.next();
            PreparedStatement st4 = conn.prepareStatement("update tb_transaksi set id_barang=?, id_pegawai=?, total_harga=? where id_transaksi=LAST_INSERT_ID()");
            st4.setString(1, rs2.getString("id_barang"));
            st4.setString(2, rs3.getString("id_pegawai"));
            Integer hargaBarang = Integer.valueOf(rs2.getString("harga_barang")) * Integer.valueOf(banyak);
            st4.setString(3, hargaBarang.toString());
            st4.executeUpdate();
        } catch (Exception e) {
            System.out.println(e+"db");
        }
    }

    @Override
    public void del_transaksi(DbConnection db, String id) {
        Connection conn = db.dbCon;
        try {
            String sql = "delete from tb_transaksi where id_transaksi=?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, id);
            int row = st.executeUpdate();
            if (row > 0){
                System.out.println("Transaksi berhasil dihapus");
            }else{
                System.out.println("Penghapusan Transaksi gagal");
            }
        } catch (Exception e) {
        }
    }

}
