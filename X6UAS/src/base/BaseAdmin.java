package base;

/**
 *
 * @author brilyan
 */
public interface BaseAdmin {
    public void login(String userAdmin, String passAdmin);
    public void register(String userAdmin, String passAdmin, String namaPegawai,  String genderPegawai);
    
}
