package org.groupId.models.filhaandtering;



import org.groupId.models.Arrangement;
import org.groupId.models.Billett;
import org.groupId.models.Lokale;
import org.groupId.models.Person;

import java.io.IOException;

public interface Innlasting {

    void InnLasting(String kilde, Lokale lokale, Person person, Arrangement arrangement, Billett billett) throws IOException;

    //void jobjOpplasting(String kilde) throws IOException;
}
