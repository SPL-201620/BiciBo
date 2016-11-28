package co.edu.uniandes.bicibo.aspectos;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import co.edu.uniandes.bicibo.domain.Log;

public aspect Security 
{
	pointcut security():
		call(* * (..));
	
	before():security()
	{		
		String strNombreMetodo = thisJoinPointStaticPart.getSignature().getName();
		String strParametros = thisJoinPoint.getArgs().toString();
		String strTarget = thisJoinPoint.getTarget().toString();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String strFecha = dateFormat.format(date);
		System.out.println("Metodo: " + strNombreMetodo + "\n" +
							"Parametros: " + strParametros + "\n" +
							"Objetivo: " + strTarget + "\n" +
							"Fecha: " + strFecha + "\n");
		
		try
        {
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA_Bicibo" );
			
			EntityManager entityManager;
        	entityManager = emfactory.createEntityManager( );
        	entityManager.getTransaction( ).begin( );
        	
        	Log log = new Log();
        	
        	log.setNombreMetodo(strNombreMetodo);
        	log.setParametros(strParametros);
        	log.setObjetivo(strTarget);
        	log.setFecha(strFecha);
        	
			entityManager.persist( log );
            entityManager.getTransaction( ).commit( );

            entityManager.close( );
            emfactory.close( );
        }
        catch (Exception e)
        {
        	System.out.println(e.getMessage());
        }    	
	}
	
	after()	returning(Object	r):	security()
	{
		System.out.println("Retorno: " + r);
	}
	
	after() throwing(Throwable e):security()
	{
		System.out.println("Excepcion: " + e);
	}
}
