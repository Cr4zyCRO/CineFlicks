package org.bg121788.cineflicks.controller;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Announcement;
import org.bg121788.cineflicks.service.AnnouncementService;
import org.bg121788.cineflicks.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final MovieService movieService;

    @GetMapping
    public ModelAndView announcementForm(){
        ModelAndView modelAndView = new ModelAndView("create-announcement");
        modelAndView.addObject("announcements",announcementService.getAllAnnouncements());
        return modelAndView;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAnnouncement(@RequestParam UUID movieId,
                                                     @RequestParam String expirationDate) {

        LocalDate parsedExpirationDate = LocalDate.parse(expirationDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDateTime expirationDateTime = parsedExpirationDate.atStartOfDay();

        announcementService.createAnnouncement(movieId, expirationDateTime);
        return new ResponseEntity<>("Announcement created successfully", HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        return new ResponseEntity<>(announcements, HttpStatus.OK);
    }
}
