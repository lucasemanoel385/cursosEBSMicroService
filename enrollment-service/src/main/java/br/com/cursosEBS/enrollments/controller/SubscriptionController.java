package br.com.cursosEBS.enrollments.controller;

import br.com.cursosEBS.enrollments.dto.RegisterSubscriptionDTO;
import br.com.cursosEBS.enrollments.dto.UpdateStatusSubscriptionDTO;
import br.com.cursosEBS.enrollments.entity.Subscription;
import br.com.cursosEBS.enrollments.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<RegisterSubscriptionDTO> registerSubscription(@RequestBody RegisterSubscriptionDTO dto, UriComponentsBuilder uriBuilder) {

        var subs = subscriptionService.registerSubscription(dto);
        System.out.println(subs.id() + " -- " + subs.userId());
        var uri = uriBuilder.path("/subscription/{id}").buildAndExpand(subs.id()).toUri();

        return ResponseEntity.created(uri).body(subs);
    }

    @PatchMapping("/status")
    public ResponseEntity updateStatus(UpdateStatusSubscriptionDTO dto) {
       subscriptionService.updateStatus(dto);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public Subscription getSubscriptionId(@PathVariable String id) {
        return subscriptionService.getSubscriptionId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSubscriptionId(@PathVariable String id) {

        subscriptionService.deleteSubscriptionId(id);
        return ResponseEntity.noContent().build();
    }
}
