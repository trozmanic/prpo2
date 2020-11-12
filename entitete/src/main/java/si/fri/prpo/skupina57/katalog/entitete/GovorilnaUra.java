package si.fri.prpo.skupina57.katalog.entitete;

import javax.persistence.*;
import java.lang.reflect.Type;
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

    //fetch = FetchType.EAGER NUJNO, ker default je LAZY, in nam je skos dalo IndirectList not instantiated
    @ManyToMany( cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
    },
    fetch = FetchType.EAGER)
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

        //to je drugacno od ostalih atributov zato,
        //ker pol profesor spet klilce govorilnaUra.toString(), ZATO, DA NI INFINITE LOOP,
        String pro = "'null'";
        if (profesor != null) {
            pro = profesor.getId().toString();
        }
        //to je drugacno od ostalih atributov zato,
        //ker pol student spet klilce govorilnaUra.toString(), ZATO, DA NI INFINITE LOOP,
        //3 opcije null, empty, seznam
        String stu;
        if (studenti == null) {
            stu = "null";
        } else if (studenti.isEmpty()) {
            stu = "[]";
        } else {
            stu = "[";
            for (int i = 0; i < studenti.size(); i++) {
                if (i == 0) {
                    stu += studenti.get(i).getIme();
                } else {
                    stu += ", " + studenti.get(i).getIme();
                }
            }
            stu += "]";
        }

        return "GovorilnaUra{" +
                "id=" + id +
                ", datum=" + "'" + datum + "'" +
                ", ura='" + ura + '\'' +
                ", kapaciteta=" + kapaciteta +
                ", kanal='" + kanal + '\'' +
                ", profesor=" + pro +
                ", studenti=" + stu  +
                '}';
    }

}
