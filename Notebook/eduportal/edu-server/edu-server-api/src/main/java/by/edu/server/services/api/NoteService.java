package by.edu.server.services.api;

import by.edu.server.beans.notes.Note;
import by.edu.server.beans.users.User;

import java.util.List;

public interface NoteService {
    void addNote(Note note);

    Note getLastOne(User user);

    List<Note> getNotesOfUser(User user);

    Note getNote(Integer id);

    Note getNote(String name);

    void deleteNote(Integer id);

    Note updateNote(Note note);
}
