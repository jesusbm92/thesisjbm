package tests;

import globalTesting.GlobalTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Metadata;
import domain.Question;

import services.MetadataService;
import utilities.PopulateDatabase;


@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateEditDeleteMetadataTest extends GlobalTest {

	@Autowired
	MetadataService metadataService;
	

//	@Before
//	public void setUp() {
//		PopulateDatabase.main(null);
//	}

	@Test
	public void testCreateMetadataAdmin() {

		authenticate("admin");

		int sizeBefore = metadataService.findAll().size();

		Metadata metadata = metadataService.create();
		metadata.setName("prueba");
		metadataService.save(metadata);

		int sizeAfter = metadataService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}

	@Test
	public void testCreateMetadataCustomer() {

		authenticate("customer");

		int sizeBefore = metadataService.findAll().size();

		Metadata metadata = metadataService.create();
		metadata.setName("pruebacustomer");

		metadataService.save(metadata);

		int sizeAfter = metadataService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}
	
	@Test
	public void testEditMetadata() {

		authenticate("admin");
		Metadata metadata = metadataService.findOne(5);
		metadata.setName("aeraera");
		metadataService.save(metadata);
		Metadata again = metadataService.findOne(5);
		Assert.isTrue(again.getName().equals("aeraera"));

	}


}