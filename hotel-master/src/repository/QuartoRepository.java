package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Quarto;

public class QuartoRepository {

	EntityManagerFactory emf;
	EntityManager em;
	
	public QuartoRepository() {
		emf = Persistence.createEntityManagerFactory("poo");
		em = emf.createEntityManager();
	}
	
	public Quarto obterPorId(int id) {
		em.getTransaction().begin();
		Quarto quarto = em.find(Quarto.class, id);
		em.getTransaction().commit();
		emf.close();
		return quarto;
	}

	public void salvar(Quarto quarto) {
		em.getTransaction().begin();
		em.merge(quarto);
		em.getTransaction().commit();
		emf.close();
	}
	
	public void remover(Quarto quarto) {
		em.getTransaction().begin();
		em.remove(quarto);
		em.getTransaction().commit();
		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Quarto> listarTodos() {
		em.getTransaction().begin();;
		Query consulta = em.createQuery("select quarto from Quarto quarto");
		List<Quarto> quartos = consulta.getResultList();
		em.getTransaction().commit();
		emf.close();
		return quartos;
	}
}