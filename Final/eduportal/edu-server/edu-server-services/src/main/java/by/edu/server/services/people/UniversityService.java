package by.edu.server.services.people;

import by.edu.server.beans.university.Faculty;
import by.edu.server.beans.university.Place;

import javax.transaction.Transactional;
import java.util.List;

public interface UniversityService {
    List<Faculty> getAllFaculties();

    List<Place> getAllPlaces();

    @Transactional
    void addFaculty(Faculty faculty);

    @Transactional
    Faculty getFaculty(Integer id, boolean toInit);

    @Transactional
    void addPlace(Place place);

    @Transactional
    Place getPlace(Integer id);

    @Transactional
    Place updatePlace(Place place);

    @Transactional
    void deletePlace(Integer id);

    @Transactional
    Faculty updateFaculty(Faculty faculty);

    @Transactional
    void deleteFaculty(Integer id);
}
