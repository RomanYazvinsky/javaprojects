package by.edu.server.services.people;

import by.edu.server.beans.university.Chair;
import by.edu.server.beans.university.Faculty;
import by.edu.server.beans.university.Place;
import by.edu.server.dao.university.FacultyDao;
import by.edu.server.dao.university.PlaceDao;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class UniversityServiceImpl implements UniversityService {
    private static Logger logger = LogManager.getLogger(UniversityServiceImpl.class);
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private FacultyDao facultyDao;

    private void init(Faculty faculty) {
        if (faculty == null) {
            return;
        }
        List<Chair> chairs = faculty.getChairs();
    }

    @Override
    @Transactional
    public List<Faculty> getAllFaculties() {
        try {
            return facultyDao.findAll();
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public List<Place> getAllPlaces() {
        try {
            return placeDao.findAll();
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }


    @Override
    @Transactional
    public void addFaculty(Faculty faculty) {
        try {
            facultyDao.saveAndFlush(faculty);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Faculty getFaculty(Integer id, boolean toInit) {
        try {
            Faculty faculty = facultyDao.findById(id).get();
            if (toInit) {
                init(faculty);
            }
            return faculty;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void addPlace(Place place) {
        try {
            placeDao.saveAndFlush(place);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Place getPlace(Integer id) {
        try {
            return placeDao.findById(id).get();
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Place updatePlace(Place place) {
        try {
            Place toEdit = placeDao.getOne(place.getId());
            toEdit.setBuilding(place.getBuilding());
            toEdit.setCabinet(place.getCabinet());
            placeDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deletePlace(Integer id) {
        try {
            placeDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public Faculty updateFaculty(Faculty faculty) {
        try {
            Faculty toEdit = facultyDao.getOne(faculty.getId());
            toEdit.setName(faculty.getName());
            toEdit.getChairs().clear();
            toEdit.getChairs().addAll(faculty.getChairs());
            facultyDao.save(toEdit);
            return toEdit;
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteFaculty(Integer id) {
        try {
            facultyDao.deleteById(id);
        } catch (Exception e) {
            logger.log(Level.DEBUG, e.getMessage());
            throw e;
        }
    }
}
