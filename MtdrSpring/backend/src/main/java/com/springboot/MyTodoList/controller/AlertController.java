package com.springboot.MyTodoList.controller;

import com.springboot.MyTodoList.model.Alert;
import com.springboot.MyTodoList.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/alerts") // Define the base URL for Alert-related endpoints
public class AlertController {

    @Autowired
    private AlertService alertService;

    // Get all alerts
    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAlertsByStatus("PENDING"); // Defaulting to PENDING for simplicity
    }

    // Get a specific alert by ID
    @GetMapping("/{id}")
    public ResponseEntity<Alert> getAlertById(@PathVariable Long id) {
        try {
            Alert alert = alertService.getAlertsByStatus("PENDING").stream()
                    .filter(a -> a.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Alert not found"));
            return new ResponseEntity<>(alert, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Create a new alert
    @PostMapping
    public ResponseEntity<Void> addAlert(@RequestBody Alert alert) throws Exception {
        Alert createdAlert = alertService.createAlert(alert.getMessage(), alert.getTaskId(),
                alert.getTask(), alert.getProjectId(), alert.getUserId(), alert.getPriority(),
                alert.getScheduledTime());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Location", "/alerts/" + createdAlert.getId());
        responseHeaders.set("Access-Control-Expose-Headers", "location");

        return ResponseEntity.created(URI.create("/alerts/" + createdAlert.getId()))
                .headers(responseHeaders)
                .build();
    }

    // Update an existing alert
    @PutMapping("/{id}")
    public ResponseEntity<Alert> updateAlert(@RequestBody Alert alert, @PathVariable Long id) {
        try {
            Alert updatedAlert = alertService.updateAlertStatus(id, alert.getStatus());
            return new ResponseEntity<>(updatedAlert, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Delete an alert
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAlert(@PathVariable("id") Long id) {
        Boolean flag = false;
        try {
            flag = alertService.deleteAlert(id);
            return new ResponseEntity<>(flag, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(flag, HttpStatus.NOT_FOUND);
        }
    }
}
