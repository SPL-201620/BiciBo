package co.edu.uniandes.bicibo.aspectos;

public aspect Security 
{
	pointcut security():
		call(* * (..));
	
	before():security()
	{		
		String strNombreMetodo = thisJoinPointStaticPart.getSignature().getName();
		String strParametros = thisJoinPoint.getArgs().toString();
		String strTarget = thisJoinPoint.getTarget().toString();
		System.out.println("Metodo: " + strNombreMetodo + "\n" +
							"Parametros: " + strParametros + "\n" +
							"Objetivo: " + strTarget + "\n");
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
