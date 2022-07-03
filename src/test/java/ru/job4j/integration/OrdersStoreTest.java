package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql"))
        )) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void deleteTable() throws SQLException {
        pool.getConnection().prepareStatement("drop table orders").executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndFindById() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description2"));
        Order rsl = store.findById(1);
        assertThat(rsl.getDescription(), is("description2"));
        assertThat(rsl.getId(), is(1));
    }

    @Test
    public void whenUpdate() {
        OrdersStore store = new OrdersStore(pool);
        Order order = store.save(Order.of("name1", "description1"));
        order.setName("update");
        assertTrue(store.update(order));
        Order orderUpd = store.findById(order.getId());
        assertThat(orderUpd.getName(), is("update"));
    }

    @Test
    public void whenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        store.save(Order.of("name2", "description2"));
        store.save(Order.of("name1", "description3"));
        List<Order> all = store.findByName("name1");
        assertThat(all.size(), is(2));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(1).getDescription(), is("description3"));
    }
}