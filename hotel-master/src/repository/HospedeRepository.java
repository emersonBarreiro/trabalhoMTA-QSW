package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Hospede;

public class HospedeRepository {
	
	EntityManagerFactory emf;
	EntityManager em;
	
	public HospedeRepository() {
		emf = Persistence.createEntityManagerFactory("poo");
		em = emf.createEntityManager();
	}
	
	public Hospede obterPorId(int id) {
		em.getTransaction().begin();
		Hospede hospede = em.find(Hospede.class, id);
		em.getTransaction().commit();
		emf.close();
		return hospede;
	}

	public void salvar(Hospede hospede) {
		em.getTransaction().begin();
		em.merge(hospede);
		em.getTransaction().commit();
		emf.close();
	}
	
	public void remover(Hospede hospede) {
		em.getTransaction().begin();
		em.remove(hospede);
		em.getTransaction().commit();
		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Hospede> listarTodos() {
		em.getTransaction().begin();;
		Query consulta = em.createQuery("select hospede from Hospede hospede");
		List<Hospede> hospedes = consulta.getResultList();
		em.getTransaction().commit();
		emf.close();
		return hospedes;
	}
}
