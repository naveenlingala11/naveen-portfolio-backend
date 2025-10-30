package com.naveen.portfolio.controller;

import com.naveen.portfolio.model.Project;
import com.naveen.portfolio.service.ProjectService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping
    public List<Project> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Project add(@RequestBody Project project) {
        return service.add(project);
    }

    @PutMapping("/{id}")
    public Project update(@PathVariable Long id, @RequestBody Project project) {
        return service.update(id, project);
    }

    /**
     * 🆕 Bulk add or update multiple projects via one POST call.
     * Example JSON payload:
     * [
     *   {
     *     "title": "Banking Microservices",
     *     "description": "Spring Boot REST APIs for banking apps",
     *     "techStack": ["Spring Boot", "PostgreSQL"],
     *     "githubUrl": "https://github.com/naveenlingala11",
     *     "demoUrl": "https://naveen-portfolio.netlify.app",
     *     "imageUrl": "assets/images/Banking.jpg"
     *   }
     * ]
     */
    @PostMapping("/bulk")
    public List<Project> addMultiple(@RequestBody List<Project> projects) {
        return service.saveAll(projects);
    }
    // ✅ DELETE endpoint
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "✅ Project with ID " + id + " deleted successfully.";
    }
}
