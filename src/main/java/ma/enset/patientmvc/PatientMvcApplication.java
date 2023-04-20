package ma.enset.patientmvc;

import ma.enset.patientmvc.entities.Patient;
import ma.enset.patientmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(PatientMvcApplication.class, args);
    }

        //@Bean
        CommandLineRunner commandLineRunner(PatientRepository patientRepository){
            return args -> {
                patientRepository.save
                        (new Patient(null, "Youssef",new java.util.Date(), false, 122));
                patientRepository.save
                        (new Patient(null, "Hiba", new java.util.Date(), true, 562));

                patientRepository.save
                        (new Patient(null, "Ayoub", new java.util.Date(), true, 745));

                patientRepository.save
                        (new Patient(null, "Anas", new java.util.Date(), false, 423));


                patientRepository.findAll().forEach(p-> {
                    System.out.println(p.getNom());
                });

            };
        }

}



















