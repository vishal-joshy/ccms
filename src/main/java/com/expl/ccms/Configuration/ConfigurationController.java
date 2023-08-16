package com.expl.ccms.Configuration;

import com.expl.ccms.groups.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/{group_id}/configurations")
public class ConfigurationController {
    private final ConfigurationService configService;

    @Autowired
    public ConfigurationController(ConfigurationService configService) {
        this.configService = configService;
    }

    @GetMapping()
    public ResponseEntity<List<Configuration>> getConfiguration(@PathVariable("group_id") Long groupId) {
        List<Configuration> configurations = configService.getConfigurations(groupId);
        if (configurations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(configurations, HttpStatus.OK);
    }

    @PostMapping()
    public void addConfiguration(@PathVariable("group_id") Long groupId, @RequestBody ConfigurationFormData configurationFormData) {
        Configuration configuration = new Configuration(configurationFormData.key, groupId, configurationFormData.name, configurationFormData.description, configurationFormData.version, configurationFormData.data);
        configService.addConfiguration(configuration);
    }

    @PutMapping(path = "{configuration_id}")
    public void updateConfiguration(@PathVariable("configuration_id") Long configurationId, @PathVariable("group-id") Long groupId, @RequestBody ConfigurationFormData configurationFormData) {
        Configuration configuration = new Configuration(configurationFormData.key, groupId, configurationFormData.name, configurationFormData.description, configurationFormData.version, configurationFormData.data);
        configService.updateConfiguration(configurationId, configuration);
    }

    @DeleteMapping(path = "{configuration_id}")
    public void deleteConfiguration(@PathVariable("configuration_id") Long configurationId) {
        configService.deleteConfiguration(configurationId);
    }
}
