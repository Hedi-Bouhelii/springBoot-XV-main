package pfe.agence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pfe.Assuré.Assure;
import pfe.Assuré.AssureRepository;
import pfe.Expert.Expert;
import pfe.Expert.ExpertRepository;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AgenceService  {
    private final AgenceRepository agenceRepository;
    private final AssureRepository assureRepository;
    private final ExpertRepository expertRepository;

    public List<Assure> getAllAssure(Integer id) {
        Agence agence = agenceRepository.findById(id).orElseThrow();
        return assureRepository.findByAssurance(agence);
    }
    public List<Expert> getAllExpert(Integer id) {
        Agence agence = agenceRepository.findById(id).orElseThrow();
        return expertRepository.findByAssurance(agence);
    }
    public String saveFile(MultipartFile file) throws IOException {
        String folder = "C:\\Users\\Hedi\\main-front-pfe\\main-front-pfe\\src\\assets\\files\\";
        byte[] bytes = file.getBytes();
        Path path = Paths.get(folder +file.getOriginalFilename());
        Files.write(path,bytes);
        return path.toString();
    }
}
