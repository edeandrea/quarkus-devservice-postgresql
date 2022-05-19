package org.acme;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@Table(name = "fruits")
public class Fruit extends PanacheEntity {
	@Column(unique = true, nullable = false)
	public String name;
	public String description;
}
