package tests;

import globalTesting.GlobalTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Question;
import domain.Answer;
import domain.Statistic;

import repositories.AnswerRepository;
import services.QuestionService;
import services.AnswerService;
import utilities.PopulateDatabase;

@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class CreateEditDeleteAnswer extends GlobalTest {

	@Autowired
	AnswerService answerService;

	@Autowired
	QuestionService questionService;

	// @Before
	// public void setUp() {
	// PopulateDatabase.main(null);
	// }

	@Test
	public void testCreateAnswerAdmin() {

		authenticate("admin");

		int sizeBefore = answerService.findAll().size();

		Answer answer = answerService.create();
		answer.setName("prueba");
		answer.setText("a");
		answer.setIsCorrect(true);
		answer.setPenalty(0.0);
		answer.setQuestion(questionService.findOne(9));
		answerService.save(answer);

		int sizeAfter = answerService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}

	@Test
	public void testCreateAnswerCustomer() {

		authenticate("customer");

		int sizeBefore = answerService.findAll().size();

		Answer answer = answerService.create();
		answer.setName("pruebacustomer");
		answer.setText("a");
		answer.setIsCorrect(true);
		answer.setPenalty(0.0);
		answer.setQuestion(questionService.findOne(9));
		answerService.save(answer);

		int sizeAfter = answerService.findAll().size();

		Assert.isTrue(sizeAfter > sizeBefore);
	}

	@Test
	public void testEditAnswer() {

		authenticate("admin");
		Answer answer = answerService.findOne(11);
		answer.setName("aeraera");
		answerService.save(answer);
		Answer again = answerService.findOne(11);
		Assert.isTrue(again.getName().equals("aeraera"));

	}

}