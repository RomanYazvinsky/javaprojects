package by.edu.server.properties.resources;

public class BeanConstants {
    public final static String BEAN_ID = "id";

    public final static String TABLE_USER = "user";
    public final static String USER_PASSWORD = "password";
    public final static String USER_USERNAME = "username";
    public final static String USER_PERSON = "user_person_id";

    public final static String TABLE_PERSON = "person";
    public final static String PERSON_NAME = "name";
    public final static String PERSON_LAST_NAME = "last_name";
    public final static String PERSON_SECOND_NAME = "second_name";
    public final static String PERSON_PHOTO = "photo";
    public final static String PERSON_PERSONAL_INFO = "person_personal_info";
    public final static String PERSON_CONTACTS = "person_contacts";
    public final static String PERSON_ROLE = "role";
    public final static String PERSON_POSITION = "position";

    public final static String TABLE_PERSONAL_INFO = "personal_info";
    public final static String PERSONAL_INFO_NATIONALITY = "nationality";
    public final static String PERSONAL_INFO_BIRTH_DATE = "birthdate";
    public final static String PERSONAL_INFO_ADDRESS = "address";
    public final static String PERSONAL_INFO_REGISTRATION_DATE = "registration_date";

    public final static String TABLE_CONTACTS = "contacts";
    public final static String CONTACTS_PHONE = "phone";
    public final static String CONTACTS_EMAIL = "email";
    public final static String CONTACTS_ADDITIONAL = "additional";

    public final static String TABLE_PLACE = "place";
    public final static String PLACE_BUILDING = "building";
    public final static String PLACE_CABINET = "cabinet";

    public final static String TABLE_FACULTY = "faculty";
    public final static String FACULTY_NAME = "faculty_name";
    public final static String FACULTY_CHAIRS = "faculty_chairs";

    public final static String TABLE_CHAIR = "chair";
    public final static String CHAIR_NAME = "chair_name";
    public final static String CHAIR_FACULTY = "chair_faculty";
    public final static String CHAIR_LECTURERS = "chair_lecturers";
    public final static String CHAIR_SUBJECTS = "chair_subjets";

    public final static String TABLE_STUDENT = "student";
    public final static String STUDENT_PERSON = "student_person";
    public final static String STUDENT_SPECIALITY_GROUP = "student_speciality_group";
    public final static String STUDENT_COURSE_GROUPS = "student_course_groups";

    public final static String TABLE_LECTURER = "lecturer";
    public final static String LECTURER_PERSON = "lecturer_person";
    public final static String LECTURER_CHAIR = "lecturer_chair";
    public final static String LECTURER_CLASSES = "lecturer_classes";

    public final static String TABLE_SUBJECT = "subject";
    public final static String SUBJECT_NAME = "name";
    public final static String SUBJECT_CHAIR = "subject_chair";
    public final static String SUBJECT_TYPE = "subject_type";

    public final static String TABLE_SUBJECT_CLASS = "subject_class";
    public final static String SUBJECT_CLASS_SUBJECT = "subject_class_subject";
    public final static String SUBJECT_CLASS_PLACE = "subject_class_place";
    public final static String SUBJECT_CLASS_ADDITIONAL = "additional";
    public final static String SUBJECT_CLASS_BEGINNING_TIME = "beginning_time";
    public final static String SUBJECT_CLASS_LECTURERS = "subject_class_lecturer";
    public final static String SUBJECT_CLASS_COURSE = "subject_class_course";

    public final static String TABLE_JOURNAL_RECORD = "journal_record";
    public final static String JOURNAL_RECORD_SUBJECT_CLASS = "journal_record_subject_class";
    public final static String JOURNAL_RECORD_STUDENT = "journal_record_student";
    public final static String JOURNAL_RECORD_MARK = "mark";
    public final static String JOURNAL_RECORD_ABSENCE = "absence";

    public final static String TABLE_COURSE = "course";
    public final static String COURSE_COURSE_GROUP = "course_course_group";
    public final static String COURSE_SUBJECT_CLASSES = "course_subject_classes";

    public final static String TABLE_COURSE_GROUP = "course_group";
    public final static String COURSE_GROUP_NAME = "name";
    public final static String COURSE_GROUP_STUDENTS = "course_group_lecturer_chair";
    public final static String COURSE_GROUP_COURSE = "course_group_course";

    public final static String TABLE_SPECIALITY_GROUP = "speciality_group";
    public final static String SPECIALITY_GROUP_NAME = "name";
    public final static String SPECIALITY_GROUP_STUDENTS = "speciality_group_students";
    public final static String SPECIALITY_GROUP_CURATOR = "speciality_group_curator";
}
