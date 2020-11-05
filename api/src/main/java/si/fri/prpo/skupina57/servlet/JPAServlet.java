package si.fri.prpo.skupina57.servlet;

import si.fri.prpo.skupina57.katalog.entitete.Student;
import si.fri.prpo.skupina57.storitve.StudentiZrno;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {



    @Inject
    private StudentiZrno studentiZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Student> studenti = studentiZrno.getStudenti();

        // izpis uporabnikov na spletno stran
        PrintWriter writer = resp.getWriter();
        writer.append("Seznam obstojecih studentov:\n");
        studenti.stream().forEach(u -> writer.append(u.toString() + "\n"));


        List<Student> studentiC = studentiZrno.getStudenti();

        // izpis uporabnikov na spletno stran
        writer.append("Seznam obstojecih studentovC:\n");
        studentiC.stream().forEach(u -> writer.append(u.toString() + "\n"));

    }
}
