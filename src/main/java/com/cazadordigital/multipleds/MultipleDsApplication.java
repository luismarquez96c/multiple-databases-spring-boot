package com.cazadordigital.multipleds;

import com.cazadordigital.multipleds.persistence.mysql.entity.ClienteEntity;
import com.cazadordigital.multipleds.persistence.mysql.repository.ClienteRepository;
import com.cazadordigital.multipleds.persistence.postgresql.entity.BitacoraEntity;
import com.cazadordigital.multipleds.persistence.postgresql.repository.BitacoraRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SpringBootApplication
public class MultipleDsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MultipleDsApplication.class, args);
	}

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	@Qualifier("mysqlDatasource")
	DataSource mysqlDS;

	@Qualifier("mysqlEMF")
	@Autowired
	private EntityManager mysqlEM;

	@Autowired
	private BitacoraRepository bitacoraRepository;

	@Autowired
	@Qualifier("postgresqlEMF")
	private EntityManager postgresqlEM;

	@Autowired
	@Qualifier("postgresqlDatasource")
	private DataSource postgresqlDS;

	@Override


	public void run(String... args) throws Exception {

		postgresqlTest();

	}

	public void postgresqlTest() throws Exception{

		BitacoraEntity bitacoraEntity = new BitacoraEntity();
		bitacoraEntity.setNivel("WARNING");
		bitacoraEntity.setMensaje("Mensaje de advertencia de ejemplo # 4: generado con jpa puro");

//		postgresqlEM.persist(bitacoraEntity);

		Connection conn = postgresqlDS.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("select * from bitacora");
		ResultSet bitacoraRS = pstmt.executeQuery();

		while( bitacoraRS.next() ){
			System.out.println(bitacoraRS.getLong("id"));
			System.out.println(bitacoraRS.getString("mensaje"));
			System.out.println("-------------------");
		}

//		for (BitacoraEntity bitacora : bitacoraRepository.findAll()) {
//			System.out.println(bitacora.getId());
//			System.out.println(bitacora.getMensaje());
//			System.out.println("-------------------");
//		}


	}


	@Transactional("mysqlTrxManager")
	public void mysqlTest() throws Exception{
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setEmail("correo5@gmail.com");
		clienteEntity.setNombre("Cazador Digital 5");

		mysqlEM.persist(clienteEntity);

//		clienteRepository.save(clienteEntity);

//		for (ClienteEntity cliente: clienteRepository.findAll()) {
//			System.out.println(cliente.getId());
//			System.out.println(cliente.getNombre());
//			System.out.println(cliente.getEmail());
//		}


		Connection mysqlConn = mysqlDS.getConnection();
		PreparedStatement pSTS = mysqlConn.prepareStatement("select * from cliente");
		ResultSet rsClient = pSTS.executeQuery();

		for (ClienteEntity cliente: clienteRepository.findAll()) {
			System.out.println(cliente.getId());
			System.out.println(cliente.getNombre());
			System.out.println(cliente.getEmail());
			System.out.println("----------------");
		}
	}
}
