//Proyecto Final - Bases de Datos
//Integrantes:
//Jose Miguel García Reyes. 155871
//Juan Carlos Castelán Hernández. 153794
//Jordi Omar Ponce Bonfil. 155718

import java.sql.*;
import java.io.*;

public class FinalProjectMySQL {
	//Comando para correrlo -->   java -cp .;mysql-connector-java-8.0.13.jar FinalProjectMySQL
	Connection conn = null;
	Statement stmt = null;
	BufferedReader in = null;

	//static final String URL = "jdbc:mysql://localhost/";
	static final String URL = "jdbc:mysql://localhost/fptest?verifyServerCertificate=false&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//static final String BD = "fptest";
	static final String USER = "root";
	static final String PASSWD = "";

	public FinalProjectMySQL() throws SQLException, Exception {
		Class.forName( "com.mysql.jdbc.Driver" );
		System.out.print( "Connecting to the database... " );

		conn = DriverManager.getConnection( URL/*+BD*/, USER, PASSWD );
		System.out.println( "connected\n\n" );

		conn.setAutoCommit( false );
		stmt = conn.createStatement();
		in = new BufferedReader( new InputStreamReader(System.in) );

	}//end FinalProyectMySQL() - Constructor

	private void dumpResultSet( ResultSet rset ) throws SQLException {
		ResultSetMetaData rsetmd = rset.getMetaData();
		int i = rsetmd.getColumnCount();

		while( rset.next() ) {
			for( int j = 1; j <= i; j++ ) {
				System.out.print( rset.getString(j) + "\t" );
			}
			System.out.println();
		}

	}//end dumpResultSet()

	private void query( String statement ) throws SQLException {
		ResultSet rset = stmt.executeQuery( statement );

		//System.out.println( "Results:" );
		dumpResultSet( rset );
		System.out.println();
		rset.close();

	}//end query()

	private void close() throws SQLException {
		stmt.close();
		conn.close();

	}//end close()

	private boolean menu() throws SQLException, IOException {
		String statement;

		System.out.println( "\nNivel de aislamiento = " + conn.getTransactionIsolation() + "\n" );
		System.out.println( "(1) Lista completa de Cursos\n" );
		System.out.println( "(2) Lista con predicado de Cursos\n" );
		System.out.println( "(3) Lista completa de Horarios\n" );
		System.out.println( "(4) Lista con predicado de Horarios\n" );
		System.out.println( "(5) Lista completa de Periodos\n" );
		System.out.println( "(6) Lista con predicado de Periodos\n" );
		System.out.println( "(9) Lista completa de Reservaciones\n" );
		System.out.println( "(10) Lista con predicado de Reservaciones\n" );
		System.out.println( "(11) Lista completa de Salones\n" );
		System.out.println( "(12) Lista con predicado de Salones\n" );

		System.out.println( "(13) Hacer una Reservacion\n" );
		System.out.println( "(14) Cancelar una Reservacion\n" );
		System.out.println( "(15) Modificar una Reservacion\n" );

		System.out.println( "(16) Agregar Horario\n" );
		System.out.println( "(17) Borrar un Horario (Completo)\n" );
		System.out.println( "(18) Modificar un Horario\n" );

		System.out.println( "(19) Validar todas operaciones\n" );
		System.out.println( "(20) Abortar todas las operaciones\n" );
		System.out.println( "(21) Cambiar nivel de aislamiento\n" );
		System.out.println( "(22) Salir\n\n" );
		System.out.print( "Ingrese la opcion deseada: " );

		switch( Integer.parseInt( "0" + in.readLine() ) ) {

			case 1:	//Lista completa de Cursos
				System.out.println( "CLAVEC\tSECCION\tTITULO\tPROFESOR\n" );
				query( "select * from CURSO" );
			break;

			case 2:	//Lista con predicado de Cursos
				System.out.println( "\nPredicado?" );
				query( "select * from CURSO where " + in.readLine() );
			break;

			case 3:	//Lista completa de Horarios
				System.out.println( "\nCLAVEC\tSECCION\tDIASEM\tHORA\tMINUTO\tDURACION\tTITULOP\tIDSALON\n" );
				query( "select * from HORARIO" );
			break;

			case 4: //Lista con predicado de Horarios
				System.out.println( "\nPredicado?" );
				query( "select * from HORARIO where " + in.readLine() );
			break;

			case 5:	//Lista completa de Periodos
				System.out.println( "\nTITULOP\tFECHAINICIO\tFECHAFIN\n" );
				query( "select * from PERIODO" );
			break;

			case 6:	//Lista con predicado de Periodos
				System.out.println( "\nPredicado?" );
				query( "select * from PERIODO where " + in.readLine() );
			break;

			case 9:	//Lista completa de Reservaciones
				System.out.println( "\nIDSALON\tNOMBRE\tFECHA\tDIASEM\tHORA\tMINUTO\tDURACION\n" );
				query( "select * from RESERVACION" );
			break;

			case 10: //Lista con predicado de Reservaciones
				System.out.println( "\nPredicado?" );
				query( "select * from RESERVACION where " + in.readLine() );
			break;

			case 11: //Lista completa de Salones
				System.out.println( "\nIDSALON\tCAPACIDAD\tTIPO\n" );
				query( "select * from SALON" );
			break;

			case 12: //Lista con predicado de Salones
				System.out.println( "\nPredicado?" );
				query( "select * from SALON where " + in.readLine() );
			break;

			case 13: //Hacer una Reservacion
				statement = "insert into RESERVACION values ( ";

				System.out.println( "\nSalon?" );
				statement += "'" + in.readLine() + "', ";

				System.out.println( "ID?" );
				statement += in.readLine() + ", ";

				System.out.println( "Fecha (yyyy-mm-dd)?" );
				statement += "'" + in.readLine() + "', ";

				System.out.println( "Dia de la semana (1-7)?" );
				statement += in.readLine() + ", ";

				System.out.println( "Hora?" );
				statement += in.readLine() + ", ";

				System.out.println( "Minuto?" );
				statement += in.readLine() + ", ";

				System.out.println( "Duracion?" );
				statement += in.readLine() + ");";

				stmt.executeUpdate( statement );
			break;

			case 14: //Cancelar una Reservacion
				statement = "delete from RESERVACION where ";

				System.out.println( "\nSalón?" );
				statement += "IDSALON = '" + in.readLine() + "' AND ";

				System.out.println( "\nID Persona?" );
				statement += "ID = " + in.readLine() + " AND ";

				System.out.println( "\nFecha (yyyy-mm-dd)?" );
				statement += "FECHAHORA = '" + in.readLine() + "' AND ";

				System.out.println( "\nDia de la Semana (1-7)?" );
				statement += "DIASEM = " + in.readLine() + " AND ";

				System.out.println( "\nHora?" );
				statement += "HORA = " + in.readLine() + " ;";

				stmt.executeUpdate( statement );
			break;

			case 15: //Modificar una Reservacion
				statement = "update RESERVACION set ";

				System.out.println( "\n<Atributo> = <Valor>?" );
				statement += in.readLine();

				System.out.println( "Predicado?" );
				statement += " where " + in.readLine();

				stmt.executeUpdate( statement );
			break;

			case 16: //Agregar Horario
				statement = "insert into HORARIO values ( ";

				System.out.println( "\nClave?" );
				statement += "'" + in.readLine() + "', ";

				System.out.println( "Seccion?" );
				statement += in.readLine() + ", ";

				System.out.println( "Dia de la Semana (1-7)?" );
				statement += in.readLine() + ", ";

				System.out.println( "Hora?" );
				statement += in.readLine() + ", ";

				System.out.println( "Minuto?" );
				statement += in.readLine() + ", ";

				System.out.println( "Duración?" );
				statement += in.readLine() + ", ";

				System.out.println( "Periodo?" );
				statement += "'" + in.readLine() + "', ";

				System.out.println( "Año?" );
				statement += in.readLine() + ", ";

				System.out.println( "Salón?" );
				statement += "'" + in.readLine() + "' );";

				stmt.executeUpdate( statement );
			break;

			case 17: //Borrar un Horario (Completo)
				statement = "delete from HORARIO where ";

				System.out.println( "\nClave?" );
				statement += "CLAVE = '" + in.readLine() + "' AND ";

				System.out.println( "\nSección?" );
				statement += "SECCION = " + in.readLine() + " ;";

				stmt.executeUpdate( statement );
			break;

			case 18: //Modificar un Horario
				statement = "update HORARIO set ";

				System.out.println( "\n<Atributo> = <Valor>?" );
				statement += in.readLine();

				statement += " where ";

				System.out.println( "\nClave?" );
				statement += "CLAVE = '" + in.readLine() + "' AND ";

				System.out.println( "\nSeccion?" );
				statement += "SECCION = " + in.readLine() + " AND ";

				System.out.println( "\nDía de la semana (1-7)?" );
				statement += "DIASEM = " + in.readLine() + " AND ";

				System.out.println( "\nHora?" );
				statement += "HORA = " + in.readLine() + "; ";

				stmt.executeUpdate( statement );
			break;

			case 19: //Validar todas operaciones
				conn.commit();
			break;

			case 20: //Abortar todas las operaciones
				conn.rollback();
			break;

			case 21: //Cambiar nivel de aislamiento
				System.out.println();
				System.out.println( conn.TRANSACTION_NONE + " = TRANSACTION_NONE" );
				System.out.println( conn.TRANSACTION_READ_UNCOMMITTED + " = TRANSACTION_READ_UNCOMMITTED" );
				System.out.println( conn.TRANSACTION_READ_COMMITTED + " = TRANSACTION_READ_COMMITTED" );
				System.out.println( conn.TRANSACTION_REPEATABLE_READ + " = TRANSACTION_REPEATABLE_READ" );
				System.out.println( conn.TRANSACTION_SERIALIZABLE + " = TRANSACTION_SERIALIZABLE\n\n" );
				System.out.println( "Nivel?" );
				conn.setTransactionIsolation( Integer.parseInt( in.readLine() ) );
			break;

			case 22: //Salir
				return false;

			default: //En caso de no caer en ninguna de los anteriores
				System.out.println( "Error, ninguna opcion seleccionada" );
			break;
		}//end switch

		return true;

	}//end menu()

	public static void main( String arg[] ) throws SQLException, Exception {

		if( arg.length != 0 ) {
			System.err.println( "Use: java FinalProjectMySQL" );
			System.exit( 1 );
		}

		FinalProjectMySQL transaction = new FinalProjectMySQL();

		while( true ) 
			try {
				if( ! transaction.menu() )
					break;
			} catch( Exception e ) {
				System.err.println( "failed" );
				e.printStackTrace( System.err );
			}

		transaction.close();

	}//end main()

}//end class FinalProjectMySQL