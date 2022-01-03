package com.inho.apps.practice04;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
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
            basic_function(em);
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

    public static void basic_function(EntityManager em)
    {
        em.flush();
        em.clear();

        // CONCAT
        String qlString = " select concat(m.username, '-', m.age, '-', m.id) from Member m";
        TypedQuery<String> query = em.createQuery(qlString, String.class);
        List<String> members = query.getResultList();
        printResult(members);

        // CURRENT_DATE
        qlString = " select CURRENT_DATE from Member m";
        List<Date> dateResult = em.createQuery(qlString, Date.class).getResultList();
        printResult(dateResult);

        // CURRENT_DATE
        qlString = " select CURRENT_TIME from Member m";
        List<Date> timeResult = em.createQuery(qlString, Date.class).getResultList();
        printResult(timeResult);

        // CURRENT_DATE
        qlString = " select CURRENT_TIMESTAMP from Member m";
        List<Date> timestampResult = em.createQuery(qlString, Date.class).getResultList();
        printResult(timestampResult);

        // LENGTH
        qlString = " select m.username, LENGTH(m.username) from Member m";
        List resultLength = em.createQuery(qlString).getResultList();
        printResult(resultLength);

        // LOWER
        qlString = " select m.username, LOWER(m.username) from Member m";
        List resultLower = em.createQuery(qlString).getResultList();
        printResult(resultLower);

        // UPPER, MOD
        qlString = " select m.username, UPPER(m.username), MOD(2, 3) from Member m";
        List resultUpper = em.createQuery(qlString).getResultList();
        printResult(resultUpper);


        // SUBSTRING, MOD
        qlString = " select m.username, SUBSTRING(m.username, 1, LENGTH(m.username)-2), TRIM( TRAILING FROM ' xxyy    ') from Member m";
        List resultSubstring = em.createQuery(qlString).getResultList();
        printResult(resultSubstring);

        // LOCATE
        qlString = " select m.username, LOCATE('er', m.username, 1) from Member m";
        List resultLocation = em.createQuery(qlString).getResultList();
        printResult(resultLocation);
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
