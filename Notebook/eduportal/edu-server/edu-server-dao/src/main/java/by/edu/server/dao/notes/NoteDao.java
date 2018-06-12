package by.edu.server.dao.notes;

import by.edu.server.beans.notes.Note;
import by.edu.server.beans.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteDao extends JpaRepository<Note, Integer> {
    @Query("select n from Note n left join n.owner where n.id = (:noteId)")
    Note findByIdJoinOwner(@Param("noteId") Integer id);
    @Query("select n from Note n left join n.owner where n.name = (:noteName)")
    Note findByNameJoinOwner(@Param("noteName") String name);
    Note getFirstByOwnerOrderByLastEditDateDesc(User owner);
    List<Note> findByOwner(User user);
}
