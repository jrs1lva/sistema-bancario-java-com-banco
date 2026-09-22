package br.com.sistemabancariofix.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.sistemabancariofix.model.Conta;
import br.com.sistemabancariofix.model.ContaCorrente;

public class ContaDAO {
	
	public void criarConta(Conta conta) {
		String sql = "INSERT INTO contas (tipo, saldo, usuario_id, limite) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = ConexaoBanco.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
				stmt.setString(1, conta.getTipo().name());
				stmt.setDouble(2, conta.getSaldo());
				stmt.setLong(3, conta.getUsuario().getId());
				
				if (conta instanceof ContaCorrente) {
					stmt.setDouble(4, ((ContaCorrente) conta).getLimite());
				} else {
					stmt.setDouble(4, 0);
				}
				
				stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao cadastrar conta no banco de dados.", e);
		}
	
	} 

}
