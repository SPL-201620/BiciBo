package co.edu.uniandes.bicibo.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
 
/**
 * Clase para generar o ejecutar actualiaciones sobre las entidades del sistema Bicibo
 * @author Abimelec
 */
public class GenerarBD {
	public static void main(String[] args) 
	{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Eclipselink_JPA_Bicibo");
        EntityManager em = emf.createEntityManager();
        em.close();
    }
}
