package com.damiatm94.shopapp.domain;


import javax.persistence.*;

import static com.damiatm94.shopapp.MainApp.getProductData;

/**
 * Created by damian on 26.09.16.
 */

@Entity
@Table(name="Warehouse")
//@Table(name = "Warehouse")
public class ProductToDB
{
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "Product_name")
    private String dbProductName;
    @Column(name = "Price")
    private double dbPrice;
    @Column(name = "Amount")
    private int dbAmount;
    @Column(name = "Minimal_amount")
    private int dbMinAmount;

    public static void persistProductsToDB()
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        for (int i = 0; i < getProductData().size(); i++)
        {
            ProductToDB productToDB = new ProductToDB();

            productToDB.setDbProductName(getProductData().get(i).getProductName());
            productToDB.setDbPrice(getProductData().get(i).getPrice());
            productToDB.setDbAmount(getProductData().get(i).getAmount());
            productToDB.setDbMinAmount(getProductData().get(i).getMinAmount());

//            System.out.println(productToDB.getDbProductName() + ", " + productToDB.getDbPrice() + ", " +
//                    productToDB.getDbAmount() + ", " + productToDB.getDbMinAmount());

            entityManager.persist(productToDB);
        }

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getDbProductName()
    {
        return dbProductName;
    }

    public void setDbProductName(String dbProductName)
    {
        this.dbProductName = dbProductName;
    }

    public double getDbPrice()
    {
        return dbPrice;
    }

    public void setDbPrice(double dbPrice)
    {
        this.dbPrice = dbPrice;
    }

    public int getDbAmount()
    {
        return dbAmount;
    }

    public void setDbAmount(int dbAmount)
    {
        this.dbAmount = dbAmount;
    }

    public int getDbMinAmount()
    {
        return dbMinAmount;
    }

    public void setDbMinAmount(int dbMinAmount)
    {
        this.dbMinAmount = dbMinAmount;
    }

}