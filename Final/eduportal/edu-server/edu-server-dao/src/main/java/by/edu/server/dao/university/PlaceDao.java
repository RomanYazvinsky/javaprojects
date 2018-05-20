package by.edu.server.dao.university;

import by.edu.server.beans.university.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceDao extends JpaRepository<Place, Integer> {
}
