package org.groupId.models.exceptions;

public class LokaleOversiktException extends Exception {

    public LokaleOversiktException(String msg){
        super(msg);
    }

    @Override
    public String getMessage() {
        return "\"Det finnes ingen lokale, dermed er det ikke mulig å se oversikten. Vennligst lag et lokalet før du klikker videre. :)\" + \"\\n\" + \"TAMAM TAMAM\"";
    }
}
