package ecare.dao.impl;

import ecare.dao.api.OptionDao;
import ecare.model.entity.Option;
import ecare.mq.MessageSender;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class OptionDaoImpl implements OptionDao {

    private final SessionFactory sessionFactory;

    final
    MessageSender messageSender;

    public OptionDaoImpl(SessionFactory sessionFactory, MessageSender messageSender) {
        this.sessionFactory = sessionFactory;
        this.messageSender = messageSender;
    }

    @Override
    public void save(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(option);
    }

    @Override
    public void update(Option option) {
        Session session = sessionFactory.getCurrentSession();
        session.update(option);
    }

    @Override
    public void delete(Option option) {
        Session session = sessionFactory.getCurrentSession();
        option.setActive(false);
        session.update(option);
    }

    @Override
    public List<Option> getOptionByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Option> query = session.createQuery(
                "select o " +
                        "from Option o " +
                        "where o.name = :nam", Option.class);
        query.setParameter("nam", name);
        return query.list();
    }

    @Override
    public List<Option> getOptionById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Option> query = session.createQuery("select o " +
                "from Option o " +
                "where o.option_id = :id", Option.class);
        query.setParameter("id", id);
        return query.list();
    }

    @Override
    public List<Option> searchForOptionByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Option> query = session.createQuery(
                "select o " +
                        "from Option o " +
                        "where o.name like:string", Option.class);
        query.setParameter("string", "%" + name + "%");
        return query.list();
    }

    @Override
    public List<Option> getActiveOptions() {
        Session session = sessionFactory.getCurrentSession();
        Query<Option> query = session.createQuery(
                "select o from Option o where o.isActive=true", Option.class);
        return query.list();
    }

    @Override
    public Set<Option> getParentObligatoryOptions(Long optionDTOid){
        Session session = sessionFactory.getCurrentSession();
        String sqlString = "SELECT option_id FROM obligatory_options oo WHERE  oo.obligatoryoption_id=" + optionDTOid;
        Query<BigInteger> query = session.createSQLQuery(sqlString);
        Set<BigInteger> setOfOptionIds = new HashSet<>(query.list());

        Set<Option> setOfOptions = new HashSet<>();
        for (BigInteger id: setOfOptionIds) {
            setOfOptions.add(getOptionById( id.longValue() ).get(0));
        }

        return setOfOptions;
    }

    @Override
    public Set<Option> getParentIncompatibleOptions(Long optionDTOid){
        Session session = sessionFactory.getCurrentSession();
        String sqlString = "SELECT option_id FROM incompatible_options io WHERE  io.incompatibleoption_id=" + optionDTOid;
        Set<BigInteger> setOfOptionIds = new HashSet<>(session.createSQLQuery(sqlString).list());
        Set<Option> setOfOptions = new HashSet<>();
        for (BigInteger id: setOfOptionIds) {
            setOfOptions.add(getOptionById( id.longValue() ).get(0));
        }
        return setOfOptions;
    }

    @Override
    public Set<Option> getAllParentDependencies(Long optionDTOid){
        Set<Option> setOfOptions = new HashSet<>(getParentObligatoryOptions(optionDTOid));
        setOfOptions.addAll(getParentIncompatibleOptions(optionDTOid));
        return setOfOptions;
    }

}
