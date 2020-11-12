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


        // NOVI PROFESOR
        writer.append("\n\nNOVI PROFESOR\n\n");

        Profesor profesor = new Profesor();

        profesor.setIme("Miha");
        profesor.setPriimek("Novak");
        profesor.setPredmet("PRPO");

        profesor = profesorjiZrno.dodajProfesorja(profesor);

        writer.append(profesor.toString() + "\n");

        writer.append("Seznam govorilnih ur profesorja:\n(prazen)\n");
        if (profesor.getGovorilneUre() != null) {
            profesor.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));
        }



        // NOVI STUDENT
        writer.append("\n\nNOVI STUDENT\n\n");

        Student student = new Student();

        student.setIme("Tine");
        student.setPriimek("Hocevar");
        student.setLetnik(3);
        student.setVpisnaStevilka(63170000);

        student = studentiZrno.dodajStudenta(student);

        writer.append(student.toString() + "\n");

        writer.append("Seznam govorilnih ur studenta:\n(prazen)\n");
        if (student.getGovorilneUre() != null) {
            student.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));
        }



        // DODAJ GOVORILNO URO
        writer.append("\n\nDODAJ GOVORILNO URO\n\n");

        GovorilnaUraDto govorilnaUraDto = new GovorilnaUraDto();

        govorilnaUraDto.setDatum(new Date());
        govorilnaUraDto.setKanal("Zoom");
        govorilnaUraDto.setProfesor_id(profesor.getId());
        govorilnaUraDto.setKapaciteta(15);
        govorilnaUraDto.setUra("12:00");

        GovorilnaUra govorilnaUra = upravljanjeGovorilnihUrZrno.dodajGovorilneUre(govorilnaUraDto);
        //updatej profesorja
        profesor = profesorjiZrno.pridobiProfesorja(profesor.getId());

        writer.append(govorilnaUra.toString() + "\n");
        writer.append(profesor.toString() + "\n");

        writer.append("Seznam govorilnih ur profesorja:\n");
        if (profesor.getGovorilneUre() != null) {
            profesor.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));
        }
        writer.append("Seznam studentov prijavlenih na govorilne ure:\n(prazen)\n");
        if (govorilnaUra.getStudenti() != null) {
            govorilnaUra.getStudenti().stream().forEach(st -> writer.append(st.toString()).append("\n"));
        }



        // PRIJAVA STUDENTA NA TERMIN
        writer.append("\n\nPRIJAVA STUDENTA NA TERMIN\n\n");

        PrijavaOdjavaDto prijavaOdjavaDto =  new PrijavaOdjavaDto();

        prijavaOdjavaDto.setStudent_id(student.getId());
        prijavaOdjavaDto.setGovorilna_ura_id(govorilnaUra.getId());

        govorilnaUra = upravljanjeGovorilnihUrZrno.prijavaNaTermin(prijavaOdjavaDto);
        //updatej studenta
        student = studentiZrno.pridobiStudenta(student.getId());

        writer.append(student.toString() + "\n");
        writer.append(govorilnaUra.toString() + "\n");

        writer.append("Seznam govorilnih ur studenta:\n");
        if (student.getGovorilneUre() != null) {
            student.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));
        }
        writer.append("Seznam studentov prijavlenih na govorilne ure:\n");
        if (govorilnaUra.getStudenti() != null) {
            govorilnaUra.getStudenti().stream().forEach(st -> writer.append(st.toString()).append("\n"));
        }



        // ODJAVA OD TERMINA
        writer.append("\n\nODJAVA OD TERMINA\n\n");
        if (!upravljanjeGovorilnihUrZrno.odjavaOdTermina(prijavaOdjavaDto)) {
            writer.append("Odjava studenta ni uspela ni studenta ali/in govorilne ure\n");
        } else {
            //updatej studenta
            student = studentiZrno.pridobiStudenta(student.getId());
            //updatej govorilno uro
            govorilnaUra = govorilneUreZrno.pridobiGovorilnoUro(govorilnaUra.getId());

            writer.append(govorilnaUra.toString() + "\n");
            writer.append(student.toString() + "\n");

            writer.append("Seznam govorilnih ur studenta:\n(prazen)\n");
            if (student.getGovorilneUre() != null) {
                student.getGovorilneUre().stream().forEach(gu -> writer.append(gu.toString()).append("\n"));
            }
            writer.append("Seznam studentov prijavlenih na govorilne ure:\n(prazen)\n");
            if (govorilnaUra.getStudenti() != null) {
                govorilnaUra.getStudenti().stream().forEach(st -> writer.append(st.toString()).append("\n"));
            }

        }

    }
}
