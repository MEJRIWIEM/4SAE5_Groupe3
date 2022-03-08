package tn.esprit.spring.wecare.Entities.Collaborators;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Offer implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOffer;

	
	private String name;
	
	@Column(precision=2, scale=2, columnDefinition = "double default 0" )
	//@Type(type = "big_decimal") 
    //@Value("0.0")
	//@Column(precision=10, scale=2)
	private Double   ratingAvg ;
	
	@Column(columnDefinition = "integer default 0")
    private Integer countUser;
	
	@Enumerated(EnumType.STRING)
	
	private TypeOffer typeOffer ;
	
	private Float percent;
	@JsonIgnore
	@OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
	private Set<Rating> ratings;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "collaborator_id")
    private Collaborator collaborator;
	
	
}
