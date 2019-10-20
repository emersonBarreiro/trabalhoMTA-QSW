package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Ingrediente;

public class IngredienteRepository {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public IngredienteRepository() {
		emf = Persistence.createEntityManagerFactory("boteco");
		em = emf.createEntityManager();
	}
	
	public Ingrediente obterPorId(int id) {
		em.getTransaction().begin();
		Ingrediente ingrediente = em.find(Ingrediente.class, id);
		em.getTransaction().commit();
//		emf.close();
		return ingrediente;
	}

	public void salvar(Ingrediente ingrediente) {
		em.getTransaction().begin();
		em.merge(ingrediente);
		em.getTransaction().commit();
//		emf.close();
	}
	
	public void remover(Ingrediente ingrediente) {
		em.getTransaction().begin();
		em.remove(ingrediente);
		em.getTransaction().commit();
//		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Ingrediente> listarTodos() {
		em.getTransaction().begin();;
		Query consulta = em.createQuery("select ingrediente from Ingrediente ingrediente");
		List<Ingrediente> ingredientes = consulta.getResultList();
		em.getTransaction().commit();
//		emf.close();
		return ingredientes;
	}
}
