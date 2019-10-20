package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Bebida;

public class BebidaRepository {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public BebidaRepository() {
		emf = Persistence.createEntityManagerFactory("boteco");
		em = emf.createEntityManager();
	}
	
	public Bebida obterPorId(int id) {
		em.getTransaction().begin();
		Bebida ingrediente = em.find(Bebida.class, id);
		em.getTransaction().commit();
//		emf.close();
		return ingrediente;
	}

	public void salvar(Bebida ingrediente) {
		em.getTransaction().begin();
		em.merge(ingrediente);
		em.getTransaction().commit();
//		emf.close();
	}
	
	public void remover(Bebida ingrediente) {
		em.getTransaction().begin();
		em.remove(ingrediente);
		em.getTransaction().commit();
//		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Bebida> listarTodos() {
		em.getTransaction().begin();;
		Query consulta = em.createQuery("select ingrediente from Bebida ingrediente");
		List<Bebida> ingredientes = consulta.getResultList();
		em.getTransaction().commit();
//		emf.close();
		return ingredientes;
	}
}
