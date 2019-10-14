package repository;

import model.Reserva;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ReservaRepository {

	EntityManagerFactory emf;
	EntityManager em;

	public ReservaRepository() {
		emf = Persistence.createEntityManagerFactory("poo");
		em = emf.createEntityManager();
	}
	
	public Reserva obterPorId(int id) {
		em.getTransaction().begin();
		Reserva reserva = em.find(Reserva.class, id);
		em.getTransaction().commit();
		emf.close();
		return reserva;
	}

	public void salvar(Reserva reserva) {
		em.getTransaction().begin();
		em.merge(reserva);
		em.getTransaction().commit();
		emf.close();
	}
	
	public void remover(Reserva reserva) {
		em.getTransaction().begin();
		em.remove(reserva);
		em.getTransaction().commit();
		emf.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Reserva> listarTodos() {
		em.getTransaction().begin();;
		Query consulta = em.createQuery("select reserva from Reserva reserva");
		List<Reserva> reservas = consulta.getResultList();
		em.getTransaction().commit();
		emf.close();
		return reservas;
	}
}
