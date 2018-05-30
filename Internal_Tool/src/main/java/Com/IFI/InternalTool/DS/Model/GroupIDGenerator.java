package Com.IFI.InternalTool.DS.Model;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class GroupIDGenerator implements IdentifierGenerator {
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {

    // define your IMEI, example IMEI1, IMEI2,...;
    return "IMEI"+ Random().nextInt().toString();
}