package si.fri.prpo.skupina57.katalog.entitete;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "govorilna_ura")
@NamedQueries(value =
        {
                @NamedQuery(name = "GovorilnaUra.getAll", query = "SELECT gu FROM govorilna_ura gu"),
                @NamedQuery(name = "GovorilnaUra.getStudenti", query = "SELECT gu.studenti FROM govorilna_ura gu WHERE gu.id = :id"),
                @NamedQuery(name = "GovorilnaUra.getDatumUra", query = "SELECT gu.datum, gu.ura FROM  govorilna_ura gu WHERE gu.id = :id"),
                @NamedQuery(name = "GovorilnaUra.getProfesor", query = "SELECT gu.profesor FROM govorilna_ura gu WHERE gu.id = :id")
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

    @ManyToMany( cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "zbirka",
            joinColumns = @JoinColumn(name = "govorilne_ure_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")

    )
    private List<Student> studenti;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }

    @Override
    public String toString() {
        return "GovorilnaUra{" +
                "id=" + id +
                ", datum=" + datum +
                ", ura='" + ura + '\'' +
                ", kapaciteta=" + kapaciteta +
                ", kanal='" + kanal + '\'' +
                ", profesor=" + profesor +
                ", studenti=" + studenti +
                '}';
    }
}
