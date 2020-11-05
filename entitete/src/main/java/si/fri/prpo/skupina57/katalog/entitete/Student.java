package si.fri.prpo.skupina57.katalog.entitete;

import javax.persistence.*;

@Entity(name = "student")
@NamedQueries(value =
        {
                @NamedQuery(name = "Student.getAll", query = "SELECT s FROM student s")
        })
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer vpisnaStevilka;

    private String ime;

    private String priimek;

    private Integer letnik;

    public Integer getId() {
        return id;
    }


    public Integer getVpisnaStevilka() {
        return vpisnaStevilka;
    }

    public void setVpisnaStevilka(Integer vpisnaStevilka) {
        this.vpisnaStevilka = vpisnaStevilka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public Integer getLetnik() {
        return letnik;
    }

    public void setLetnik(Integer letnik) {
        this.letnik = letnik;
    }


}