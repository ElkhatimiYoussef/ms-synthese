package ma.enset.patientmvc.web;



import lombok.AllArgsConstructor;
import ma.enset.patientmvc.entities.Patient;
import ma.enset.patientmvc.repositories.PatientRepository;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.boot.context.properties.bind.BindContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
     private PatientRepository patientRepository;
     @GetMapping(path = "/index")
     public String patients(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "5") int size,
                            @RequestParam(name = "keyword", defaultValue = "") String keyword
                            ){
         Page<Patient> pagePatients=patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
         model.addAttribute("listPatients",pagePatients.getContent());
         model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
         model.addAttribute("currentPage", page);
         model.addAttribute("keyword", keyword);
         return "patients";
     }

     @GetMapping("/delete")
     public String delete(Long id, String keyword, int page) {//Methode pour Supprimer a travers (-ID-)
         patientRepository.deleteById(id);
         return "redirect:/index?page="+page+"&keyword="+keyword; //Redireger vers la page
     }

     @GetMapping("/")
    public String home(){
         return "redirect:/index";
     }

     @GetMapping("/patients")
     @ResponseBody //Pour le Serializer dans le cors de la reponse
     public List<Patient> lisPatients(){
         return patientRepository.findAll();
     }

     @GetMapping("/formPatients")
     public String fromPatient(Model model){
         model.addAttribute("patient", new Patient());
         return "formPatients";
     }

     @PostMapping(path = "/save")//Persister User Dans La Base de donnes
     public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "") String keyword){
         if(bindingResult.hasErrors()) return "formPatients";
         patientRepository.save(patient);
         return "redirect:/index?page="+page+"&keyword="+keyword;
     }

    @GetMapping("/editPatient")
    public String editPatient(Model model, Long id, String keyword, int page){
         Patient patient=patientRepository.findById(id).orElse(null);
         if(patient==null) throw new RuntimeException("Patient introuvable");
         model.addAttribute("patient", patient);
         model.addAttribute("page", page);
         model.addAttribute("keyword", keyword);
        return "editPatient";
    }

}
