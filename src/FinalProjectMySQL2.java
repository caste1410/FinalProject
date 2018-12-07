//Proyecto Final - Bases de Datos
//Integrantes:
//Jose Miguel García Reyes. 155871
//Juan Carlos Castelán Hernández. 153794
//Jordi Omar Ponce Bonfil. 155718

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.util.Date;

public class FinalProjectMySQL2 {
	//Comando para correrlo -->   java -cp .;mysql-connector-java-8.0.13.jar FinalProjectMySQL2
	Connection conn = null;
	Statement stmt = null;
	BufferedReader in = null;

	//static final String URL = "jdbc:mysql://localhost/";
	static final String URL = "jdbc:mysql://localhost/fptest?verifyServerCertificate=false&useSSL=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	//static final String BD = "fptest";
	static final String USER = "root";
	static final String PASSWD = "";

	public FinalProjectMySQL2() throws SQLException, Exception {
		Class.forName( "com.mysql.cj.jdbc.Driver" );
		System.out.print( "Connecting to the database... " );

		conn = DriverManager.getConnection( URL/*+BD*/, USER, PASSWD );
		System.out.println( "Connected\n\n" );

		conn.setAutoCommit( false );
		stmt = conn.createStatement();
		in = new BufferedReader( new InputStreamReader(System.in) );

	}//end FinalProyectMySQL2() - Constructor

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

		dumpResultSet( rset );
		System.out.println();
		rset.close();

	}//end query()

	private void close() throws SQLException {
		stmt.close();
		conn.close();

	}//end close()

	private boolean menu() throws SQLException, IOException {
		conn.setTransactionIsolation( Integer.parseInt( "4" ) );
		String statement;

		String hourString = "";
		String dateString = "";
		Date date = null;

		int dayOfWeek;
		
		Calendar calendar;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		boolean correct = false;
		int value = 0;

		System.out.println( "\nNivel de aislamiento = " + conn.getTransactionIsolation() + "\n" );
		System.out.println( "(1) Lista completa de Cursos" );
		System.out.println( "(2) Lista con predicado de Cursos" );
		System.out.println( "(3) Lista completa de Horarios" );
		System.out.println( "(4) Lista con predicado de Horarios" );
		System.out.println( "(5) Lista completa de Periodos" );
		System.out.println( "(6) Lista con predicado de Periodos" );
		System.out.println( "(7) Lista completa de Reservaciones" );
		System.out.println( "(8) Lista con predicado de Reservaciones" );
		System.out.println( "(9) Lista completa de Salones" );
		System.out.println( "(10) Lista con predicado de Salones\n" );

		System.out.println( "(11) Ver salones disponibles a reservar" );
		System.out.println( "(12) Hacer una Reservacion" );
		System.out.println( "(13) Cancelar una Reservacion" );
		System.out.println( "(14) Modificar una Reservacion\n" );

		System.out.println( "(15) Agregar Horario" );
		System.out.println( "(16) Borrar un Horario (Completo)" );
		System.out.println( "(17) Modificar un Horario\n" );
		
		System.out.println( "(18) Salir\n\n" );

		System.out.print( "Ingrese la opcion deseada: " );

		switch( Integer.parseInt( "0" + in.readLine() ) ) {
			case 1:	//Lista completa de Cursos
				System.out.println( "CLAVEC\t\tSECCION\tTITULO\tPROFESOR\n" );
				query( "select * from CURSO" );
			break;

			case 2:	//Lista con predicado de Cursos
				System.out.println( "\nPredicado?" );
				query( "select * from CURSO where " + in.readLine() );
			break;

			case 3:	//Lista completa de Horarios
				System.out.println( "\nCLAVEC\t\tSECCION\tDIASEM\tHORAMIN\t\tDURACION TITULOP\tIDSALON\n" );
				query( "select * from HORARIO" );
			break;

			case 4: //Lista con predicado de Horarios
				System.out.println( "\nPredicado?" );
				query( "select * from HORARIO where " + in.readLine() );
			break;

			case 5:	//Lista completa de Periodos
				System.out.println( "\nTITULOP\t\tFECHAINICIO\tFECHAFIN\n" );
				query( "select * from PERIODO" );
			break;

			case 6:	//Lista con predicado de Periodos
				System.out.println( "\nPredicado?" );
				query( "select * from PERIODO where " + in.readLine() );
			break;

			case 7:	//Lista completa de Reservaciones
				System.out.println( "\nIDSALON\tNOMBRE\tFECHA\t\tDIASEM\tHORAMIN\t\tDURACION\n" );
				query( "select * from RESERVACION" );
			break;

			case 8: //Lista con predicado de Reservaciones
				System.out.println( "\nPredicado?" );
				query( "select * from RESERVACION where " + in.readLine() );
			break;

			case 9: //Lista completa de Salones
				System.out.println( "\nIDSALON\tCAPACIDAD TIPO\n" );
				query( "select * from SALON" );
			break;

			case 10: //Lista con predicado de Salones
				System.out.println( "\nPredicado?" );
				query( "select * from SALON where " + in.readLine() );
			break;

			case 11: //Visualizar salones disponibles para reservar
				hourString = "";
				System.out.println( "Fecha (yyyy-mm-dd)?" );
				dateString = in.readLine();
				try {
  					date = format.parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar = Calendar.getInstance(TimeZone.getTimeZone("PST"));
    			calendar.setTime(date);
    			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				
				System.out.println( "Dia de la semana es " + dayOfWeek );

				System.out.println( "Hora y Minuto (HH:mm)? (Sin hora, se muestran los salones libres en todo el dia)" );
				hourString = in.readLine();

				System.out.println( "\nHorario - Salones desocupados a la hora " + hourString );
				System.out.println( "IDSALON" );
				if ( hourString == "" ) {
					query( "select IDSALON from SALON where IDSALON NOT IN (select IDSALON from HORARIO where DIASEM = " + dayOfWeek + " );" );
					System.out.println( "Reservacion - Salones desocupados a la hora " + hourString );
					System.out.println( "IDSALON" );
					query( "select IDSALON from SALON where IDSALON NOT IN (select IDSALON from RESERVACION where FECHA = '" + dateString + "' AND DIASEM = " + dayOfWeek + " );" );
				}
				else {
					query( "select IDSALON from SALON where IDSALON NOT IN (select IDSALON from HORARIO where DIASEM = " + dayOfWeek + " AND HORAMIN = '" + hourString + "' );" );
					System.out.println( "Reservacion - Salones desocupados a la hora " + hourString );
					System.out.println( "IDSALON" );
					query( "select IDSALON from SALON where IDSALON NOT IN (select IDSALON from RESERVACION where FECHA = '" + dateString + "' AND DIASEM = " + dayOfWeek + " AND HORAMIN = '" + hourString + "' );" );
				}
			break;

			case 12: //Hacer una Reservacion

				statement = "insert into RESERVACION values ( ";

				System.out.println( "\nSalon?" );
				statement += "'" + in.readLine() + "', ";

				System.out.println( "Nombre?" );
				statement += "'" + in.readLine() + "', ";

				System.out.println( "Fecha (yyyy-mm-dd)?" );
				dateString = in.readLine();
				statement += "'" + dateString + "', ";

				try {
  					date = format.parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar = Calendar.getInstance(TimeZone.getTimeZone("PST"));
    			calendar.setTime(date);
    			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				
				System.out.println( "Dia de la semana es " + dayOfWeek );
				statement += dayOfWeek + ", ";

				System.out.println( "Hora y Minuto (HH:mm)?" );
				statement += "'" + in.readLine() + "', ";

				correct = false;
				while (correct == false){
					System.out.println( "Duracion? (Solo multiplos de 60 minutos)" );
					value = Integer.parseInt(in.readLine());
					if (value % 60 == 0) {
						statement += value + " );"; correct = true;
					}
					else{
						System.out.println( "Solo se puede reservar por hora (multiplos de 60 minutos)\n" );
					}
				}

				stmt.executeUpdate( statement );
				conn.commit();
			break;

			case 13: //Cancelar una Reservacion
				statement = "delete from RESERVACION where ";

				System.out.println( "\nSalon?" );
				statement += "IDSALON = '" + in.readLine() + "' AND ";

				System.out.println( "\nNombre?" );
				statement += "NOMBRE = '" + in.readLine() + "' AND ";

				System.out.println( "\nFecha (yyyy-mm-dd)?" );
				dateString = in.readLine();
				statement += "FECHA = '" + dateString + "' AND ";

				try {
  					date = format.parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar = Calendar.getInstance(TimeZone.getTimeZone("PST"));
    			calendar.setTime(date);
    			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

				System.out.println( "\nDia de la Semana es " + dayOfWeek );
				statement += "DIASEM = " + dayOfWeek + " AND ";

				System.out.println( "\nHora y Minuto (HH:mm)?" );
				statement += "HORAMIN = '" + in.readLine() + "' ;";

				stmt.executeUpdate( statement );
				conn.commit();
			break;

			case 14: //Modificar una Reservacion
				statement = "update RESERVACION set ";

				System.out.println( "\n<Atributo a cambiar> = <Valor que se quiere asignar>?" );
				statement += in.readLine();

				statement += " where " 

				System.out.println( "Salon? " );
				statement += "IDSALON = '" + in.readLine() + "' AND ";

				System.out.println( "Nombre? " );
				statement += "NOMBRE = '" + in.readLine() + "' AND ";

				System.out.println( "Fecha (yyyy-MM-dd)? " );
				dateString = in.readLine();
				statement += "FECHA = '" + dateString + "' AND ";

				try {
  					date = format.parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar = Calendar.getInstance(TimeZone.getTimeZone("PST"));
    			calendar.setTime(date);
    			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

				System.out.println( "\nDia de la Semana es " + dayOfWeek );
				statement += "DIASEM = " + dayOfWeek + " AND ";

				System.out.println( "Hora y Minuto (HH:mm)? " );
				statement += "HORAMIN = '" + in.readLine() + "' ;";

				stmt.executeUpdate( statement );
				conn.commit();
			break;

			case 15: //Agregar Horario
				statement = "insert into HORARIO values ( ";

				System.out.println( "\nClave?" );
				statement += "'" + in.readLine() + "', ";

				System.out.println( "Seccion?" );
				statement += in.readLine() + ", ";

				correct = false;
				while (correct == false){
					System.out.println( "Dia de la Semana (1-7)?" );
					value = Integer.parseInt(in.readLine());
					if (value > 0 && value < 8) {
						statement += value + ", "; correct = true;
					}
					else{
						System.out.println( "Error, ingresa un valor entre 1 y 7\n" );
					}
				}

				System.out.println( "Hora y Minuto (HH:mm)?" );
				hourString = in.readLine() + ":00";
				statement += "'" + hourString + "', ";
				//statement += "'" + in.readLine() + "', ";	//Es el atributo final

				correct = false;
				while (correct == false){
					System.out.println( "Duracion? (Solo multiplos de 60 minutos)" );
					value = Integer.parseInt(in.readLine());
					if (value % 60 == 0) {
						statement += value + ", "; correct = true;
					}
					else{
						System.out.println( "Solo se puede crear un horario por hora (multiplos de 60 minutos)\n" );
					}
				}

				System.out.println( "Periodo?" );
				statement += "'" + in.readLine() + "', ";

				System.out.println( "Salon?" );
				statement += "'" + in.readLine() + "', ";	 //No es el atributo final
				//statement += "'" + in.readLine() + "' );"; //Es el atributo final

				DateFormat minFormat = new SimpleDateFormat("HH:mm:ss");
				try {
  					date = minFormat.parse(hourString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MINUTE, value);

				hourString = minFormat.format(cal.getTime());
				System.out.println( "Hora final es:  " + hourString );
				statement += "'" + hourString + "' );";

				stmt.executeUpdate( statement );
				conn.commit();
			break;

			case 16: //Borrar un Horario (Completo)
				statement = "delete from HORARIO where ";

				System.out.println( "\nClave?" );
				statement += "CLAVEC = '" + in.readLine() + "' AND ";

				System.out.println( "\nSeccion?" );
				statement += "SECCION = " + in.readLine() + " AND";

				correct = false;
				while (correct == false){
					System.out.println( "\nDia de la Semana (1-7)?" );
					value = Integer.parseInt(in.readLine());
					if (value > 0 && value < 8) {
						statement += "DIASEM = " + value + " AND"; correct = true;
					}
					else{
						System.out.println( "Error, ingresa un valor entre 1 y 7\n" );
					}
				}

				System.out.println( "\nHora y Minuto (HH:mm)?" );
				statement += "HORAMIN = '" + in.readLine() + "' ;";

				stmt.executeUpdate( statement );
				conn.commit();
			break;

			case 17: //Modificar un Horario
				statement = "update HORARIO set ";

				System.out.println( "\n<Atributo a cambiar> = <Valor que se quiere asignar>?" );
				statement += in.readLine();

				statement += " where ";

				System.out.println( "\nClave?" );
				statement += "CLAVEC = '" + in.readLine() + "' AND ";

				System.out.println( "\nSeccion?" );
				statement += "SECCION = " + in.readLine() + " AND ";

				correct = false;
				while (correct == false){
					System.out.println( "\nDia de la Semana (1-7)?" );
					value = Integer.parseInt(in.readLine());
					if (value > 0 && value < 8) {
						statement += "DIASEM = " + value + " AND "; correct = true;
					}
					else{
						System.out.println( "Error, ingresa un valor entre 1 y 7\n" );
					}
				}

				System.out.println( "\nHora y Minuto? (HH:mm)" );
				statement += "HORAMIN = '" + in.readLine() + "'; ";

				stmt.executeUpdate( statement );
				conn.commit();
			break;


			case 18: //Salir
				return false;

			default: //En caso de no caer en ninguna de los anteriores
				System.out.println( "Error, favor de seleccionar una opcion valida" );
			break;
		}//end switch

		return true;

	}//end menu()

	public static void main( String arg[] ) throws SQLException, Exception {

		if( arg.length != 0 ) {
			System.err.println( "Use: java FinalProjectMySQL2" );
			System.exit( 1 );
		}

		FinalProjectMySQL2 transaction = new FinalProjectMySQL2();

		while( true ) 
			try {
				if( ! transaction.menu() )
					break;
			} catch( Exception e ) {
				System.err.println( "Failed" );
				e.printStackTrace( System.err );
			}

		transaction.close();

	}//end main()

}//end class FinalProjectMySQL2