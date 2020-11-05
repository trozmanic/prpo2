package si.fri.prpo.skupina57.katalog.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "profesor")
@NamedQueries(value =
        {
                @NamedQuery(name = "Profesor.getAll", query = "SELECT p FROM profesor p")
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

}
