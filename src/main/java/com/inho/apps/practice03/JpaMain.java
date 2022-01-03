package com.inho.apps.practice03;

import com.inho.entities.Member;

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
            //insert(em);
            condition_expression(em);
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


    }

    public static void condition_expression(EntityManager em)
    {
        em.flush();
        em.clear();

        // inner join
        String qlString = " select case " +
                "                   when m.age < 10 then '학생요금'" +
                "                   when m.age > 60 then '경로요금'" +
                "                   else '일반요금' " +
                "           end " +
                "           from Member m";

        TypedQuery<String> query = em.createQuery(qlString, String.class);
        List<String> members = query.getResultList();


    }



}
