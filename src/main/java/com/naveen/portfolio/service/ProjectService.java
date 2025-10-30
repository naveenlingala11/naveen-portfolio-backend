package com.naveen.portfolio.service;

import com.naveen.portfolio.model.Project;
import com.naveen.portfolio.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    public List<Project> getAll() {
        return repo.findAll();
    }

    public Project add(Project project) {
        return repo.save(project);
    }

    public Project update(Long id, Project updatedProject) {
        updatedProject.setId(id);
        return repo.save(updatedProject);
    }

    public void delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new RuntimeException("Project with ID " + id + " not found.");
        }
    }

    // 🆕 Save multiple projects at once
    public List<Project> saveAll(List<Project> projects) {
        return repo.saveAll(projects);
    }
}
