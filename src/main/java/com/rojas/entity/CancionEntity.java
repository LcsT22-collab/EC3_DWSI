package com.rojas.entity;

import java.math.BigDecimal;
import java.util.List;

import com.rojas.enums.Estado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cancion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancionEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cancion")
	private Long idCancion;


	@Column(name="titulo", nullable = false)
	private String titulo;

	@Column(name = "duracion", precision = 4, scale = 2)
	private BigDecimal duracion;

	@Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

	@ManyToOne
    @JoinColumn(name = "id_banda")
	private BandaEntity banda;


}
