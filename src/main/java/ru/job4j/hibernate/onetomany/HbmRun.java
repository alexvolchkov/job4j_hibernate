package ru.job4j.hibernate.onetomany;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.onetomany.entity.CarBrand;
import ru.job4j.hibernate.onetomany.entity.Model;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            CarBrand opel = new CarBrand("Opel");
            opel.addModel(new Model("Opel Astra H"));
            opel.addModel(new Model("Opel Astra J"));
            opel.addModel(new Model("Opel Antara"));
            opel.addModel(new Model("Opel Zafira"));
            opel.addModel(new Model("Opel Corsa"));
            session.save(opel);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
