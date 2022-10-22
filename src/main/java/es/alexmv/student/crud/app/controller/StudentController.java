package es.alexmv.student.crud.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.alexmv.student.crud.app.entity.Student;
import es.alexmv.student.crud.app.service.IStudentService;
import es.alexmv.student.crud.app.service.StudentServiceImpl;

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private IStudentService studentService;

	@GetMapping("/all")
	public String getAllStudents(Model model) {

		model.addAttribute("students", studentService.getAllStudents());

		return "students";
	}

	@GetMapping("/add")
	public String addStudent(Model model) {

		Student student = new Student();
		model.addAttribute("student", student);

		return "create_student";
	}

	@PostMapping("/add")
	public String procesarAddStudent(@Valid @ModelAttribute Student student, BindingResult bindingResult,
			@RequestParam String repeatPassword, Model model) {

		if (!student.getPassword().equals(repeatPassword) || bindingResult.hasErrors()) {
			model.addAttribute("errorPassword", "Las passwords no coinciden");
			return "create_student";
		}

		studentService.saveStudent(student);

		return "redirect:/students/all";
	}

	@GetMapping("/edit/{id}")
	public String editStudentForm(@PathVariable Long id, Model model) {

		model.addAttribute("student", studentService.getStudentById(id));

		return "edit_student";
	}

	@PostMapping("/edit/{id}")
	public String procesarEditStudent(@PathVariable Long id, @Valid @ModelAttribute Student student,
			BindingResult bindingResult, @RequestParam String repeatPassword, @RequestParam String passwordActual,
			Model model) {

		if (!student.getPassword().equals(repeatPassword) || bindingResult.hasErrors()
				|| !passwordActual.equals(studentService.getStudentById(id).getPassword())) {

			if (!student.getPassword().equals(repeatPassword)) {
				model.addAttribute("errorPassword", "Las passwords no coinciden");
			}

			if (!passwordActual.equals(studentService.getStudentById(id).getPassword())) {
				model.addAttribute("passwordDiferenteAntigua", "La password antigua no es correcta");
			}

			return "edit_student";
		}

		Student studentOld = studentService.getStudentById(id);
		Student studentNew = student;

		studentOld.setId(studentNew.getId());
		studentOld.setNombre(studentNew.getNombre());
		studentOld.setApellido(studentNew.getApellido());
		studentOld.setEmail(studentNew.getEmail());
		studentOld.setPassword(studentNew.getPassword());

		studentService.saveStudent(studentOld);

		return "redirect:/students/all";
	}

	@GetMapping("/delete/{id}")
	public String deleteStudent(@PathVariable Long id) {

		studentService.deleteStudenById(id);

		return "redirect:/students/all";
	}
}
