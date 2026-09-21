package br.com.sistemabancariofix.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.sistemabancariofix.model.Usuario;

public class UsuarioDAO {
	
	public void cadastrar(Usuario usuario) {
		String sql = "INSERT INTO usuarios (nome, apelido, data_nascimento, cpf) VALUES (?, ?, ?, ?)";
		
		try (Connection conn = ConexaoBanco.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, usuario.getNome());
				stmt.setString(2, usuario.getApelido());
				// Converte o LocalDate do Java para o Date nativo do SQL
				stmt.setDate(3, Date.valueOf(usuario.getDataNascimento()));
				stmt.setString(4, usuario.getCPF());
				
				stmt.executeUpdate();
				
		} catch (SQLException e) {
			if (e.getSQLState().equals("23505")) {
				throw new IllegalArgumentException("[ERRO] CPF já cadastrado no banco de dados!");
			}
			throw new RuntimeException("Erro ao cadastrar usuário no banco de dados.", e);
		}
	}
	
	public Usuario buscarPorCpf (String cpf) {
		String sql = "SELECT * FROM usuarios WHERE cpf = ?";
		Usuario usuario = null;
		
		try (Connection conn = ConexaoBanco.conectar();
		PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, cpf);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				usuario = new Usuario(
	                    rs.getString("nome"),
	                    rs.getString("apelido"),
	                    rs.getDate("data_nascimento").toLocalDate(),
	                    rs.getString("cpf")
				);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar usuário por CPF.", e);
		}
		
		return usuario;
	}
	
}
