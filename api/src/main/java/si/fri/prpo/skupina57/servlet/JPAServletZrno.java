package si.fri.prpo.skupina57.servlet;

import si.fri.prpo.skupina57.katalog.entitete.GovorilnaUra;
import si.fri.prpo.skupina57.katalog.entitete.Profesor;
import si.fri.prpo.skupina57.katalog.entitete.Student;
import si.fri.prpo.skupina57.storitve.dtos.GovorilnaUraDto;
import si.fri.prpo.skupina57.storitve.dtos.PrijavaOdjavaDto;
import si.fri.prpo.skupina57.storitve.zrna.GovorilneUreZrno;
import si.fri.prpo.skupina57.storitve.zrna.ProfesorjiZrno;
import si.fri.prpo.skupina57.storitve.zrna.StudentiZrno;
import si.fri.prpo.skupina57.storitve.zrna.UpravljanjeGovorilnihUrZrno;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;
import java.util.List;


@WebServlet("/zrno")
public class JPAServletZrno extends HttpServlet {



    @Inject
    private UpravljanjeGovorilnihUrZrno upravljanjeGovorilnihUrZrno;

    @Inject
    private ProfesorjiZrno profesorjiZrno;

    @Inject
    private StudentiZrno studentiZrno;

    @Inject
    private GovorilneUreZrno govorilneUreZrno;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**

        List<Student> studenti = studentiZrno.getStudenti();

        // izpis uporabnikov na spletno stran
        PrintWriter writer = resp.getWriter();
        writer.append("Seznam obstojecih studentov:\n");
        studenti.stream().forEach(u -> writer.append(u.toString() + "\n"));


        List<Student> studentiC = studentiZrno.getStudenti();

        // izpis uporabnikov na spletno stran
        writer.append("Seznam obstojecih studentovC:\n");
        studentiC.stream().forEach(u -> writer.append(u.toString() + "\n"));
        */
        PrintWriter writer = resp.getWriter();

        Profesor profesor = new Profesor();

        profesor.setIme("Miha");
        profesor.setPriimek("Novak");
        profesor.setPredmet("PRPO");

        Profesor profesor1 = profesorjiZrno.dodajProfesorja(profesor);

        Student student = new Student();

        student.setIme("Tine");
        student.setPriimek("Hocevar");
        student.setLetnik(3);
        student.setVpisnaStevilka(63170000);

        Student student1 = studentiZrno.dodajStudenta(student);
        writer.append("Seznam govorilnih ur studenta: \n");
        student1.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));

        writer.append("Seznam govorilnih ur profesorja: \n");
        profesor1.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));

        GovorilnaUraDto govorilnaUraDto = new GovorilnaUraDto();

        govorilnaUraDto.setDatum(new Date());
        govorilnaUraDto.setKanal("Zoom");
        govorilnaUraDto.setProfesor_id(2);
        govorilnaUraDto.setKapaciteta(15);
        govorilnaUraDto.setUra("12:00");
        GovorilnaUra govorilnaUra = upravljanjeGovorilnihUrZrno.dodajGovorilneUre(govorilnaUraDto);


        writer.append("Seznam govorilnih ur profesorja: \n");
        profesor1.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));


        PrijavaOdjavaDto prijavaOdjavaDto =  new PrijavaOdjavaDto();
        prijavaOdjavaDto.setStudent_id(1);
        prijavaOdjavaDto.setGovorilna_ura_id(1);

        upravljanjeGovorilnihUrZrno.prijavaNaTermin(prijavaOdjavaDto);

        writer.append("Seznam govorilnih ur studenta: \n");
        student1.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));


        upravljanjeGovorilnihUrZrno.odjavaOdTermina(prijavaOdjavaDto);

        writer.append("Seznam govorilnih ur studenta: \n");
        student1.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));


    }
}
