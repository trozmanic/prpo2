package si.fri.prpo.skupina57.katalog.entitete;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "govorilna_ura")
@NamedQueries(value =
        {
                @NamedQuery(name = "GovorilnUra.getAll", query = "SELECT gu FROM govorilna_ura gu")
        })
public class GovorilnaUra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date datum;

    private String ura;

    private Integer kapaciteta;

    private String kanal;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    public Integer getId() {
        return id;
    }


    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getUra() {
        return ura;
    }

    public void setUra(String ura) {
        this.ura = ura;
    }

    public Integer getKapaciteta() {
        return kapaciteta;
    }

    public void setKapaciteta(Integer kapaciteta) {
        this.kapaciteta = kapaciteta;
    }

    public String getKanal() {
        return kanal;
    }

    public void setKanal(String kanal) {
        this.kanal = kanal;
    }
}
