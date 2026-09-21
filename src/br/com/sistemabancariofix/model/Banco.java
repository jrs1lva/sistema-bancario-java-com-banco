package br.com.sistemabancariofix.model;

import java.util.ArrayList;
import java.util.List;

import br.com.sistemabancariofix.dao.UsuarioDAO;

public class Banco {
	
	private final String NOME;
    private final String AGENCIA;

    private List<Conta> contas;
    private UsuarioDAO usuarioDAO;
    
	public Banco(String nome, String agencia) {
		this.NOME = nome;
		this.AGENCIA = agencia;
		this.contas = new ArrayList<Conta>();
		this.usuarioDAO = new UsuarioDAO();
	}
	
    public boolean cadastrarUsuario(Usuario usuario) {
    	usuarioDAO.cadastrar(usuario);
        return true;
    }
	
    public Conta criarConta(String cpf, Tipo tipo) {
    	Usuario usuario = buscarUsuario(cpf);
    	
    	if (usuario == null) {
    		throw new IllegalArgumentException("Usuário não encontrado.");
    	} 
    	
    	Conta conta;
    	
    	if (tipo == Tipo.CORRENTE) {
    		conta = new ContaCorrente(usuario);
    	} else {
    		conta = new ContaPoupanca(usuario);
    	}
    	
    	contas.add(conta);
    	
    	return conta;
    }
    
    // alterar metodos com lista utilizando comandos sql nas classes DAO
    
    public boolean existeUsuario(String cpf) {
		return usuarioDAO.buscarPorCpf(cpf) != null;
    }
    
    public Usuario buscarUsuario(String cpf) {
		return usuarioDAO.buscarPorCpf(cpf);
    }
    
    public Conta buscarConta(long id) {
    	for (Conta conta : contas) {
			if (conta.getId() == id) {
				return conta;
			}
		} return null;
    }
	
    public List<Conta> listarContas() {
    	return this.contas;
    	
//    	for (Conta conta : contas) {
//			System.out.println(conta.getUsuario().getNome());
//			System.out.println(conta.getId());
//			System.out.println(conta.getClass().getName());
//			System.out.println(conta.getSaldo());
//			System.out.println();
//		}
    }

	public String getNOME() {
		return NOME;
	}

	public String getAGENCIA() {
		return AGENCIA;
	}

	public String detalhesDoBanco() {
		return "Nome do Banco:" + NOME + "\n Agência:" + AGENCIA;
	}
    
}