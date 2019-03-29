package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class StudentsController {

	@RequestMapping(value = "/Students")
	public String listStudent(Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());

		return "studentsList";
	}

	@RequestMapping(value = "/AddStudent")
	public String displayAddStudentForm(Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());

		return "studentForm";
	}

	@RequestMapping(value = "/CreateStudent", method = RequestMethod.POST)
	public String createStudent(@RequestParam(value = "studentName", required = false) String name,
			@RequestParam(value = "studentSurname", required = false) String surname,
			@RequestParam(value = "studentPesel", required = false) String pesel,
			@RequestParam(value = "studentSchoolClass", required = false) String schoolClassId, Model model,
			HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		Student student = new Student();
		student.setName(name);
		student.setSurname(surname);
		student.setPesel(pesel);

		DatabaseConnector.getInstance().addStudent(student, schoolClassId);
		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
		model.addAttribute("message", "Dodano nowego ucznia");

		return "studentsList";
	}

	@RequestMapping(value = "/DeleteStudent", method = RequestMethod.POST)
	public String deleteStudent(@RequestParam(value = "studentId", required = false) String studentId, Model model,
			HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		DatabaseConnector.getInstance().deleteStudent(studentId);
		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
		model.addAttribute("message", "Uczen został usunięty");

		return "studentsList";
	}
	
	@RequestMapping(value = "/ModifyStudent")
	public String modifySTudent(@RequestParam(value = "studentId", required = true) String studentId, Model model,
			HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("student", DatabaseConnector.getInstance().getStudent(studentId));

		return "studentModifyForm";
	}

	@RequestMapping(value = "/UpdateStudent", method = RequestMethod.POST)
	public String updateSchoolClass(@RequestParam(value = "studentName", required = false) String name,
			@RequestParam(value = "studentSurname", required = false) String surname,
			@RequestParam(value = "studentPesel", required = false) String pesel,
			@RequestParam(value = "studentId", required = false) String studentId,
			@RequestParam(value = "studentSchoolClass", required = false) String schoolClassId, Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		DatabaseConnector.getInstance().updateStudent(studentId, name, surname, pesel);
		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
		model.addAttribute("message", "Informacje o studencie zostały zmodyfikowane");

		return "studentsList";
	}

}