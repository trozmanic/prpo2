package si.fri.prpo.skupina57.storitve.dtos;

import java.util.Date;

public class GovorilnaUraDto {
    //private Integer id;

    private Date datum;

    private String ura;

    private Integer kapaciteta;

    private String kanal;

    private Integer profesor_id;
/*
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
*/
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

    public Integer getProfesor_id() {
        return profesor_id;
    }

    public void setProfesor_id(Integer profesor_id) {
        this.profesor_id = profesor_id;
    }

    @Override
    public String toString() {
        return "GovorilnaUraDto{" +
                "datum=" + datum +
                ", ura='" + ura + '\'' +
                ", kapaciteta=" + kapaciteta +
                ", kanal='" + kanal + '\'' +
                ", profesor_id=" + profesor_id +
                '}';
    }
}
