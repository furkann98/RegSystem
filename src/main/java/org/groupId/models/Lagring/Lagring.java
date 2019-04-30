package org.groupId.models.Lagring;

import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;

import java.io.IOException;
import java.util.ArrayList;

public interface Lagring {

    void csvLagring(String kilde, Lokale lokale, Person person, Arrangement arrangement, Billett billett) throws IOException;

    void jobjLagring(String kilde) throws IOException;

}
