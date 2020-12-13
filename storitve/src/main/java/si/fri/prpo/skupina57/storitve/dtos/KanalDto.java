package si.fri.prpo.skupina57.storitve.dtos;

public class KanalDto {
    private String naziv;

    public KanalDto(String naziv) {
        this.naziv = naziv;
    }

    public KanalDto() {
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
