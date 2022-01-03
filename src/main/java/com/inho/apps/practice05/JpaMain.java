package com.inho.apps.practice05;

import javax.persistence.*;
import java.util.Collection;
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
            namedQuery(em);
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

    public static void namedQuery(EntityManager em)
    {
        em.flush();
        em.clear();

        List resultList = em.createNamedQuery("Member.findByUsername")
                .setParameter("username", "member0")
                .getResultList();
        for (Object o : resultList) {
            System.out.println("o = " + o);
        }


    }

    private static void printResult(Object result)
    {
        if ( result == null ){
            System.out.print("NULL");
        }
        else if ( result instanceof Object[] )
        {
            System.out.print("[");
            Object[] rows = (Object[]) result;
            for (Object row : rows) {
                printResult(row);
            }
            System.out.print("]");
        }
        else if ( result instanceof Collection){
            for (Object item : (Collection)result) {
                printResult(item);
            }
        }
        else if ( result instanceof Number || result instanceof  String)
        {
            System.out.print( result.getClass().getName() + ":" + result);
        }else {
            System.out.print(result);
        }
        System.out.println();
    }

}
