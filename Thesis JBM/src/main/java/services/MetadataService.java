package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Metadata;
import domain.Question;

import repositories.MetadataRepository;


@Transactional
@Service
public class MetadataService {

	// Managed repository-----------------------

		@Autowired
		private MetadataRepository metadataRepository;
	
		
		
		// Constructors --------------------------
		public MetadataService() {
			super();
		}
		
		
		// Simple CRUD methods -----------------
		/**
		 * Constructor por defecto de MetadataService
		 * 
		 * @return Metadata metadata
		 */
		public Metadata create() {
			Metadata metadata = new Metadata();

			return metadata;
		}

		/**
		 * Devuelve una colección con todos los objetos de tipo Metadata
		 * 
		 * @return Collection<Metadata> metadatas
		 */
		public Collection<Metadata> findAll() {
			return metadataRepository.findAll();
		}

		/**
		 * Devuelve una instancia de un objetos de tipo Metadata En caso de no
		 * encontrarse, devuelve null
		 * 
		 * @return Metadata metadata
		 */
		public Metadata findOne(int metadataId) {
			return metadataRepository.findOne(metadataId);
		}

		/**
		 * Persiste (guarda o crea) el objeto de tipo Metadata en la base de datos a
		 * través del repositorio MetadataRepository
		 * 
		 * @return void
		 */
		public void save(Metadata metadata) {
			// TODO Restricciones de Save
			// Muscle muscle = metadata.getMuscle();
			//
			// muscle.getMetadatas().add(metadata);
			//
			// muscleService.save(muscle);
			//
			// MetadataGroup metadataGroup = metadata.getMetadataGroup();
			//
			// metadataGroup.getMetadatas().add(metadata);
			//
			// metadataGroupService.save(metadataGroup);

			metadataRepository.save(metadata);
		}

		/**
		 * Elimina el objeto de tipo Metadata de la base de datos a través del
		 * repositorio MetadataRepository
		 * 
		 * @return void
		 */
		public void delete(Metadata metadata) {
			Assert.notNull(metadata);
			// TODO Restricciones de Borrado

			for (Question q: metadata.getQuestions()){
				q.getMetadata().remove(metadata);				
			}
			metadata.setQuestions(new ArrayList<Question>());
			metadataRepository.delete(metadata);
		}


	
}
