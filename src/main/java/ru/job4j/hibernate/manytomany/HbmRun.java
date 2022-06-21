package ru.job4j.hibernate.manytomany;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.manytomany.entity.Author;
import ru.job4j.hibernate.manytomany.entity.Book;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Author author1 = Author.of("author1");
            Author author2 = Author.of("author2");
            Author author3 = Author.of("author3");
            Book book1 = Book.of("book1");
            book1.addAuthor(author1);
            Book book2 = Book.of("book2");
            book2.addAuthor(author1);
            book2.addAuthor(author2);
            Book book3 = Book.of("book3");
            book3.addAuthor(author1);
            book3.addAuthor(author3);
            session.persist(book1);
            session.persist(book2);
            session.persist(book3);
            session.remove(book2);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        }
    }
