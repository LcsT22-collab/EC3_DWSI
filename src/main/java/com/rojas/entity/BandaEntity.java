package com.rojas.entity;

import java.util.List;

import com.rojas.enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="banda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandaEntity {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_banda" )
	private Long idBanda;

	@Column(name="nombre", nullable = false)
	private String nombre;

	@Column(name="genero", nullable = false)
	private String genero;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado", length = 10, nullable = false)
	private Estado estado;

	@OneToMany(mappedBy="banda")
	private List<CancionEntity> canciones;


}
