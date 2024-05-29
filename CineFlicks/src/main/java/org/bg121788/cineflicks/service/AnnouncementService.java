package org.bg121788.cineflicks.service;

import lombok.AllArgsConstructor;
import org.bg121788.cineflicks.entity.Announcement;
import org.bg121788.cineflicks.entity.Movie;
import org.bg121788.cineflicks.repository.AnnouncementRepository;
import org.bg121788.cineflicks.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final MovieRepository movieRepository;

    public void createAnnouncement(UUID movieId, LocalDateTime expirationDate) {
        Movie movie = movieRepository.getReferenceById(movieId);
        Announcement announcement = announcementRepository.findByMovie(movie);


        if (announcement == null){
            announcement = new Announcement();
            announcement.setMovie(movie);
        }

        announcement.setExpiration_date(expirationDate);
        announcementRepository.save(announcement);
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }
}
