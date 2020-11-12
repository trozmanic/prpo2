package si.fri.prpo.skupina57.katalog.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "student")
@NamedQueries(value =
        {
                @NamedQuery(name = "Student.getAll", query = "SELECT s FROM student s"),
                @NamedQuery(name = "Student.getStudent", query = "SELECT s FROM student s WHERE s.id = :id"),
                @NamedQuery(name = "Student.getGovorilneUre", query = "SELECT s.govorilneUre FROM  student s WHERE s.id = :id"),
                @NamedQuery(name = "Student.getLetnik", query = "SELECT s.letnik FROM student s WHERE s.id = :id")
        })
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vpisna_stevilka")
    private Integer vpisnaStevilka;

    private String ime;

    private String priimek;

    private Integer letnik;

    @ManyToMany(mappedBy = "studenti")
    private List<GovorilnaUra> govorilneUre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", vpisnaStevilka=" + vpisnaStevilka +
                ", ime='" + ime + '\'' +
                ", priimek='" + priimek + '\'' +
                ", letnik=" + letnik +
                ", govorilneUre=" + govorilneUre +
                '}';
    }
}