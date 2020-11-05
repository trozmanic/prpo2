package si.fri.prpo.skupina57.katalog.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "profesor")
@NamedQueries(value =
        {
                @NamedQuery(name = "Profesor.getAll", query = "SELECT p FROM profesor p"),
                @NamedQuery(name = "Profesor.getProfesor", query = "SELECT p FROM profesor p WHERE p.id = :id"),
                @NamedQuery(name = "Profesor.getGovorilneUre", query = "SELECT p.govorilneUre FROM  profesor p WHERE p.id = :id"),
                @NamedQuery(name = "Profesor.getPredmet", query = "SELECT p.predmet FROM profesor p WHERE p.id = :id")
        })
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private String priimek;

    private String predmet;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private List<GovorilnaUra> govorilneUre;

    public Integer getId() {
        return id;
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

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    @Override
    public String toString() {
        return "Profesor{" +
                "id=" + id +
                ", ime='" + ime + '\'' +
                ", priimek='" + priimek + '\'' +
                ", predmet='" + predmet + '\'' +
                ", govorilneUre=" + govorilneUre +
                '}';
    }
}
