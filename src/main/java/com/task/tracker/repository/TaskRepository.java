package com.task.tracker.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.task.tracker.model.Task;

@Transactional(value = TxType.SUPPORTS)
public class TaskRepository {

	@PersistenceContext(unitName = "TaskPU")
	private EntityManager em;

	public Task find(Long id) {
		return em.find(Task.class, id);
	}

	@Transactional(value = TxType.REQUIRED)
	public Task create(Task task) {
		em.persist(task);
		return task;
	}

	@Transactional(value = TxType.REQUIRED)
	public void delete(Long id) {
		Task task = em.getReference(Task.class, id);
		em.remove(task);
	}

	public List<Task> findAll() {
		TypedQuery<Task> query = em.createQuery("SELECT t from Task t order by t.title", Task.class);
		return query.getResultList();
	}

	public Long countAll() {
		TypedQuery<Long> query = em.createQuery("SELECT count(t) from Task t", Long.class);
		return query.getSingleResult();
	}
}
