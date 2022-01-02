package com.inho.apps.practice02;

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
            //pagings(em);
            join(em);
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
                .name("member90")
                .build();
        em.persist(teamA);

        for ( int i=0; i<100; i++)
        {
            Member memberA = Member.builder()
                    .username("member" + i)
                    .age( i % 100)
                    .team(teamA)
                    .build();
            em.persist(memberA);
        }
    }

    public static void pagings(EntityManager em)
    {
        em.flush();
        em.clear();

        String qlString = "select m from Member m order by m.id";
        final int PAGE_SIZE = 10;

        for ( int page = 1; page <= 10; page++)
        {
            int startPosition = ( page -1 ) * PAGE_SIZE;
            List<Member> list = em.createQuery(qlString, Member.class)
                    .setFirstResult(startPosition)
                    .setMaxResults(PAGE_SIZE)
                    .getResultList();

            System.out.println("[page] = " + page);
            System.out.println("page.firstItem.get(0).getUsername() = " + list.get(0).getUsername());
        }
    }

    public static void join(EntityManager em)
    {
        em.flush();
        em.clear();

        // inner join
        String qlString = " select m from Member m inner join Team t on m.username = t.name";
        TypedQuery<Member> query = em.createQuery(qlString, Member.class);
        List<Member> members = query
                                .getResultList();

        System.out.println("members = " + members.size());



    }



}
