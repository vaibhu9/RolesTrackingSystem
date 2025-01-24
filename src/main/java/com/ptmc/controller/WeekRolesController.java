package com.ptmc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptmc.constant.WeekRolesResponseMessage;
import com.ptmc.entity.WeekRoles;
import com.ptmc.response.WeekRoleResponse;
import com.ptmc.service.WeekRolesService;

@RestController
@RequestMapping("/api/week-roles")
@CrossOrigin(origins = "*")
public class WeekRolesController {

    private static final Logger log = LoggerFactory.getLogger(WeekRolesController.class);

    private final WeekRolesService weekRolesService;

    public WeekRolesController(WeekRolesService weekRolesService) {
        this.weekRolesService = weekRolesService;
    }

    @PostMapping
    public ResponseEntity<WeekRoles> createWeekRoles(@RequestBody WeekRoles weekRoles) {
        log.info("Request received for new WeekRoles creation: {}", weekRoles);
        WeekRoles responseWeekRoles = weekRolesService.createWeekRoles(weekRoles);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseWeekRoles);
    }

    @GetMapping("/{title}")
    public ResponseEntity<WeekRoles> getWeekRoles(@PathVariable("title") String title) {
        log.info("Request received for get WeekRoles details by Title: {}", title);
        WeekRoles responseWeekRoles = weekRolesService.getWeekRoles(title);
        if (responseWeekRoles != null) {
            return ResponseEntity.ok(responseWeekRoles);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{title}")
    public ResponseEntity<WeekRoles> updateWeekRoles(@PathVariable("title") String title, @RequestBody WeekRoles weekRoles) {
        log.info("Request received for update WeekRoles with Title: {}", title);
        WeekRoles updatedWeekRoles = weekRolesService.updateWeekRoles(title, weekRoles);
        return ResponseEntity.status(HttpStatus.OK).body(updatedWeekRoles);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<String> deleteWeekRoles(@PathVariable("title") String title) {
        log.info("Request received for delete WeekRoles with Title: {}", title);
        weekRolesService.deleteWeekRoles(title);
        return ResponseEntity.ok(WeekRolesResponseMessage.WEEK_ROLES_DELETED_SUCCESSFULLY.getMessage(title));
    }

    @GetMapping
    public ResponseEntity<List<WeekRoleResponse>> getAllWeekRoles() {
        log.info("Request received for get list of WeekRoles");
        List<WeekRoleResponse> weekRolesList = weekRolesService.getAllWeekRoles();
        log.info("Data received {}",weekRolesList.getFirst().getRoleName());
        return ResponseEntity.status(HttpStatus.OK.value()).body(weekRolesList);
    }

    
    @GetMapping("/all")
    public ResponseEntity<List<WeekRoles>> getAllWeekRolesList() {
        log.info("Request received for get list of WeekRoles");
        List<WeekRoles> weekRolesList = weekRolesService.getAllWeekRolesList();
        log.info("Data received {}",weekRolesList.getFirst().getTitle());
        return ResponseEntity.status(HttpStatus.OK.value()).body(weekRolesList);
    }

    @GetMapping("/count/{role-count}")
    public ResponseEntity<Integer> getCountOfRoles()
    {
        Integer count = weekRolesService.getCountOfRoles();
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }
}
