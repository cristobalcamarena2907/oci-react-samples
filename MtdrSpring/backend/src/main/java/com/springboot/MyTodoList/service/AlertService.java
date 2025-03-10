package com.springboot.MyTodoList.service;

import com.springboot.MyTodoList.model.Alert;
import com.springboot.MyTodoList.repository.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    // Method to create an alert
    public Alert createAlert(String message, Long taskId, String task, Long projectId, String userId, String priority, OffsetDateTime scheduledTime) {
        Alert alert = new Alert();
        alert.setMessage(message);
        alert.setTaskId(taskId);
        alert.setTask(task);
        alert.setProjectId(projectId);
        alert.setUserId(userId);
        alert.setPriority(priority);
        alert.setScheduledTime(scheduledTime);
        alert.setStatus("PENDING");

        return alertRepository.save(alert);
    }

    // Method to find all alerts based on status (e.g., PENDING, SENT)
    public List<Alert> getAlertsByStatus(String status) {
        return alertRepository.findByStatus(status);
    }

    // Method to find alerts by user ID
    public List<Alert> getAlertsByUserId(String userId) {
        return alertRepository.findByUserId(userId);
    }

    // Method to find overdue alerts (scheduled before now but still PENDING)
    public List<Alert> getOverdueAlerts() {
        return alertRepository.findOverdueAlerts(OffsetDateTime.now());
    }

    // Method to send scheduled alerts periodically (every hour)
    @Scheduled(cron = "0 0 * * * ?")  // Run every hour
    public void sendScheduledAlerts() {
        List<Alert> alertsToSend = alertRepository.findByStatus("PENDING");
        for (Alert alert : alertsToSend) {
            if (alert.getScheduledTime().isBefore(OffsetDateTime.now())) {
                sendNotification(alert);  // Send the notification to the user
                alert.setStatus("SENT");
                alertRepository.save(alert);  // Update the alert status to "SENT"
            }
        }
    }

    // Method to update the alert status (e.g., SENT or CANCELLED)
    public Alert updateAlertStatus(Long id, String status) throws Exception {
        Alert alert = alertRepository.findById(id).orElseThrow(() -> new Exception("Alert not found"));
        alert.setStatus(status);
        return alertRepository.save(alert);
    }
    

    // Helper method to send the notification (this can be integrated with Telegram, Email, etc.)
    private void sendNotification(Alert alert) {
        // Logic to send the notification (e.g., via Telegram bot or email)
        System.out.println("Sending notification to user: " + alert.getUserId() + " with message: " + alert.getMessage());
        // You can replace the above with actual notification sending logic
    }

    public Boolean deleteAlert(Long id) {
        try {
            alertRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
}
