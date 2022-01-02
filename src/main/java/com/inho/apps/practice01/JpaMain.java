package com.inho.apps.practice01;

import com.inho.entities.Member;
import com.inho.entities.Order;
import com.inho.entities.Product;
import com.inho.entities.Team;
import com.inho.entities.dto.UserDTO;
import com.inho.entities.valuetypes.Address;

import javax.persistence.*;
import java.util.List;

public class JpaMain
{
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2Db");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            insert(em);
            find(em);
            tx.commit();
        }
        catch(Exception e)
        {
            tx.rollback();
            e.printStackTrace();
        }
        finally
        {
            if (em != null && em.isOpen() ) em.close();
            if (emf != null && emf.isOpen() ) emf.close();
        }
    }

    public static void insert(EntityManager em)
    {
        Team teamA = Team.builder()
                .name("TeamA")
                .build();
        System.out.println("teamA.getId() = " + teamA.getId());
        System.out.println("teamA.getName() = " + teamA.getName());
        em.persist(teamA);

        Member memberA = Member.builder()
                .username("memberA")
                .age(18)
                .team(teamA)
                .build();
        em.persist(memberA);

        Product product = Product.builder()
                .name("상품A")
                .price(1000)
                .stockAmount(10).build();
        em.persist(product);

        Order order = Order.builder()
                .product(product)
                .orderAmount(1000)
                .address(Address.builder()
                        .city("서초구")
                        .street("서초동")
                        .zipcode("12356")
                        .build())
                .build();
        em.persist(order);

    }

    public static void find(EntityManager em)
    {
        em.flush();
        em.clear();

        // TypedQuery
        String qlString = "select o from Order as o";
        TypedQuery<Order> query = em.createQuery(qlString, Order.class);
        List<Order> orders = query.getResultList();
        for (Order order : orders) {
            System.out.println("order = " + order);
        }

        // Query
        qlString = "select o.orderAmount, o.address from Order as o";
        Query query1 = em.createQuery(qlString);
        List resultList = query1.getResultList();
        for (Object o : resultList) {
            Object[] fields = (Object[]) o;
            System.out.println("fields[0] = " + fields[0]);
            Address addr = (Address)fields[1];
            System.out.println("addr.getCity() =  " + addr.getCity());
            System.out.println("addr.getStreet() = " + addr.getStreet());
            System.out.println("addr.getZipcode() = " + addr.getZipcode());
        }

        // value type
        qlString = "select o.address from Order as o where id = :id";
        Address addr = em.createQuery(qlString, Address.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println("addr = " + addr);


        // entity type
        qlString = "select m.team from Member as m";
        TypedQuery<Team> query2 = em.createQuery(qlString, Team.class);

        List<Team> teams = query2.getResultList();

        System.out.println("==============================");
        // user DTO
        qlString = "select new com.inho.entities.dto.UserDTO( m.age) from Member as m";
        TypedQuery<UserDTO> query3 = em.createQuery(qlString, UserDTO.class);
        List<UserDTO> resultList1 = query3.getResultList();
        for (UserDTO userDTO : resultList1) {
            System.out.println("userDTO = " + userDTO);
            userDTO.setName("바뀌냐");
        }
        System.out.println("==============================");
        Team team = em.find(Team.class, 1L);
        team.setName("teamAv1.0");

    }
}
