package pfe.agence;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import pfe.Expert.ExpertRegisterRequest;
import pfe.Expert.ExpertService;


import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@PreAuthorize("hasRole('AGENCE')")
@RequestMapping("/api/v1/management")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@Tag(name = "Management")
public class AgenceController {
    private final AgenceService service;
    private final ExpertService expertService;
    private final AgenceRepository agenceRepository;



    @Operation(
            description = "Get endpoint for agency management",
            summary = "This is a summary for agency get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Integer id) throws IOException {
        System.out.println("test *********");
        String filePath =  service.saveFile(file);
        Agence agence = agenceRepository.findById(id).orElseThrow();
        agence.setFile(String.valueOf(filePath));
        agence.setStatus("pending");
        agenceRepository.save(agence);
        return ResponseEntity.ok("File uploaded ");
    }




    @PostMapping("/add/expert")
    public ResponseEntity<?> addExpert(@RequestBody ExpertRegisterRequest request,@RequestParam("id") Integer id){
        return new ResponseEntity<>(expertService.saveExpert(request,id), HttpStatus.OK);
    }

    @GetMapping ("/users")
    public ResponseEntity<?> getUsers(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(service.getAllAssure(id), HttpStatus.OK);
    }

    @GetMapping ("/experts")
    public ResponseEntity<?> getExperts(@RequestParam("id") Integer id) {
        return new ResponseEntity<>(service.getAllExpert(id), HttpStatus.OK);
    }

}
