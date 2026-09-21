package br.com.sistemabancariofix.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
	
	private static final String URL = "jdbc:postgresql://localhost:5432/sistemaBancario";
	private static final String USUARIO = "postgres";
	private static final String SENHA = "@CuscuzComBanana59Fifty";
	
	public static Connection conectar() {
		try {
			return DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (SQLException e) {
			throw new RuntimeException("Erro fatal: Não foi possível conectar ao banco de dados. Verifique suas credenciais e se o serviço do PostgreSQL está rodando.", e);
		}
	}
}
