package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import utilities.internal.CopyOfDatabaseUtil;
import domain.Answer;
import domain.Exam;
import domain.Exercise;
import domain.Metadata;
import domain.Question;
import domain.Statistic;

public class FileToObjectsConverter {

//	public static String prettyFormat(String input) {
//		
//	}


	public static void main(String[] args) {
		CopyOfDatabaseUtil databaseUtil;

		List<Answer> orderedAnswers = new ArrayList<Answer>();
		databaseUtil = null;

		BufferedReader br = null;

		Properties prop = new Properties();
		InputStream input = null;

		try {
			System.out.printf("PopulateDatabase 1.3%n");
			System.out.printf("--------------------%n%n");

			System.out.printf("Initialising persistence context `%s'...%n",
					DatabaseConfig.PersistenceUnit);
			databaseUtil = new CopyOfDatabaseUtil();

			System.out.printf("Creating database `%s' (%s)...%n",
					databaseUtil.getDatabaseName(),
					databaseUtil.getDatabaseDialectName());
			databaseUtil.recreateDatabase();

			String sCurrentLine;

			System.out.println("Accessing Properties File...");

			Path file;
			if(args[0] != "controller"){
			try {

				input = FileToObjectsConverter.class.getClassLoader()
						.getResourceAsStream("config.properties");

				// load a properties file
				prop.load(input);

			} catch (IOException ex) {
				ex.printStackTrace();
			}

			file = Paths.get(prop.getProperty("pathToExam"));
			}
			else{
				file = Paths.get(args[1]);
			}
			BasicFileAttributes attr = Files.readAttributes(file,
					BasicFileAttributes.class);
			Date creation = new Date(attr.creationTime().toMillis());

			if(args[0] != "controller"){
			br = new BufferedReader(new FileReader(
					prop.getProperty("pathToExam")));
			}
			else{
				br = new BufferedReader(new FileReader(args[1]));
			}

			Exam e = new Exam();
			e.setName(file.getFileName().toString().replace(".tst", ""));
			e.setDate(creation);
			System.out.println(e.getName());
			String xmlToExam = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<p:examen barajaEjercicios=\"false\" barajaPreguntas=\"false\" barajaRespuestas=\"true\" numEnunciados=\"9\" ordenEjercicios=\"10,3,5,ej1,2,4,6,7,8,9\" tipoNumeracionEjercicios=\"ordinal_literal\" tipoNumeracionPreguntas=\"decimal\" tipoNumeracionRespuestas=\"caracteres_minusculas\" xmlns:p=\"barajador_examen.modelo.jaxb.modeloexamen\" xmlns:xsi=\"ht//www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"barajador_examen.modelo.jaxb.modeloexamen modeloExamen.xsd \">\n <cabecera>\n <infoUniversidad>\n <nombre>Universidad Politécnica de Madrid</nombre>\n <siglas>UPM</siglas>\n </infoUniversidad>\n <infoFacultad>\n <nombre>Escuela Técnica Superior de Ingenieros Informáticos</nombre>\n <siglas>ETSIINF</siglas>\n </infoFacultad>\n <infoDepartamento>\n <nombre>Departamento de Lenguajes y Sistemas Informáticos e Ingeniería del Software</nombre>\n <siglas>DLSIIS</siglas>\n </infoDepartamento>\n <infoAsignatura>\n <nombre>Programación II</nombre>\n <tipo>Troncal</tipo>\n </infoAsignatura>\n <tipoExamen>Itinerario Flexible</tipoExamen>\n <fechaExamen>\n <fechaSeparadores tipo=\"separadores\">\n"
					+ e.getDate()
					+ "</fechaSeparadores>\n </fechaExamen>\n <tituloExamen>\n <nombreUniversidad>false</nombreUniversidad>\n <nombreFacultad>false</nombreFacultad>\n <nombreDepartamento>true</nombreDepartamento>\n <siglasUniversidad>true</siglasUniversidad>\n <siglasFacultad>true</siglasFacultad>\n <siglasDepartamento>false</siglasDepartamento>\n </tituloExamen>\n <subtituloExamen>\n <nombreAsignatura>true</nombreAsignatura>\n <siglasAsignatura>false</siglasAsignatura>\n <tipoAsignatura>false</tipoAsignatura>\n<tipoExamen>true</tipoExamen>\n <fechaExamen>true</fechaExamen>\n </subtituloExamen>\n <textoCaratulaExamen>\n <prefacioDelExamen>\n <elemento nombreElemento=\"Realización\">\n<![CDATA[El test se realizará en la hoja de respuesta. Es \textbf{importante} que no olvidéis rellenar vuestros datos personales y el código clave de vuestro enunciado. Se pueden utilizar hojas aparte en sucio. ]]>\n </elemento>\n <elemento nombreElemento=\"Duración\"> </elemento>\n <elemento nombreElemento=\"Puntuación\"> </elemento>\n <elemento nombreElemento=\"Calificaciones\"> </elemento>\n <elemento nombreElemento=\"Revisión\"> </elemento>\n </prefacioDelExamen>\n </textoCaratulaExamen>\n </cabecera> \n<ejercicios>\n";
			while ((sCurrentLine = br.readLine()) != null) {
				if (sCurrentLine.contains("EJERCICIO")
						&& !sCurrentLine.contains("INCLUIR")) {
					Exercise exercise = new Exercise();
					// Date
					e.getExercises().add(exercise);
					exercise.setExam(e);
					// Setting exercise name
					exercise.setName(sCurrentLine.substring(
							sCurrentLine.indexOf("E"),
							sCurrentLine.indexOf("<") - 1));
					// Next line for weight (For the question of that exercise)
					sCurrentLine = br.readLine();
					// sCurrentLine = sCurrentLine.replaceAll("[^\\d.]", "");
					Double currentWeight = null;
					while (currentWeight == null) {
						try {
							sCurrentLine = sCurrentLine.replaceAll("[^\\d.]",
									"");
							currentWeight = Double.valueOf(sCurrentLine);
						} catch (Exception ex) {
							sCurrentLine = br.readLine();
						}
					}
					// Next line is ENUNCIADO (text)
					sCurrentLine = br.readLine();
					// Next start the text of the exercise:
					Boolean textFinish = false;
					exercise.setText("");
					while ((sCurrentLine = br.readLine()) != null
							&& !textFinish) {
						// String variable to avoid modifying currentLine
						String copy = sCurrentLine;
						if (copy.replaceAll("\\s+", "").equals(":")) {
							textFinish = true;
						} else {
							exercise.setText(exercise.getText().concat(
									"\n" + sCurrentLine));
						}
					}
					// Text finished, now question
					Question question = new Question();
					exercise.getQuestions().add(question);
					question.setExercise(exercise);
					// Search for the first line "PREGUNTA 1XX"
					while (!(sCurrentLine.contains("PREGUNTA") && !sCurrentLine
							.contains("ESCOGE"))) {
						sCurrentLine = br.readLine();
					}

					question.setName(sCurrentLine.substring(
							sCurrentLine.indexOf("P"),
							sCurrentLine.indexOf("<") - 1));

					// Skipping the ENUNCIADO_TF line
						sCurrentLine = br.readLine();

					
					Boolean textQuestionFinish = false;
					question.setWeight(currentWeight);
					question.setText("");
					while ((sCurrentLine = br.readLine()) != null
							&& !textQuestionFinish) {
						// String variable to avoid modifying currentLine
						String copy = sCurrentLine;
						if (copy.replaceAll("\\s+", "").equals("::")) {
							textQuestionFinish = true;
						} else {
							question.setText(question.getText().concat(
									"\n" + sCurrentLine));
						}
					}
					// Question finished, now answers
					Collection<Answer> answers = new ArrayList<Answer>();
					// Now we look for the RESPUESTA line
					while (!sCurrentLine.contains("RESPUESTA")) {
						sCurrentLine = br.readLine();
					}
					// Now we loop each answer
					Boolean answerFinish = false;
					while (!answerFinish) {
						sCurrentLine = br.readLine();
						String copy = sCurrentLine;
						if (copy.replaceAll("\\s+", "").equals("::")) {
							answerFinish = true;
						}

						else {
							Answer a = new Answer();
							Pattern p = Pattern.compile("[^\\s\\\\]");
							Matcher m = p.matcher(copy);
							if (m.find()) {
								Integer indexStart = m.start();
								copy = copy.substring(indexStart);
								// System.out.println(copy);
								if(copy.contains("TRUE")||copy.contains("FALSE")){
								String[] splitted = copy.split("\\s+");
								a.setText("");
								for (int i = 0; i < splitted.length; i++) {
									if (i == 0) {
										a.setName(splitted[i]);
									} else if (i == 1) {
										if (splitted[i].toLowerCase().equals(
												"true")) {
											a.setIsCorrect(true);
										} else if (splitted[i].toLowerCase()
												.equals("false")) {
											a.setIsCorrect(false);
										}
									} else if (i == 2) {
										a.setText(a.getText() + splitted[i]);
									} else {
										a.setText(a.getText() + " "
												+ splitted[i]);
									}
								}
								// Setting the penalty to 0.0 because no info
								// about penalty is given in the tsts
								a.setPenalty(0.0);
								answers.add(a);
								orderedAnswers.add(a);
								}
								
								else{
									Answer aux = orderedAnswers.get(orderedAnswers.size()-1);
									aux.setText(aux.getText()+ " "+copy);
								}

							}

						}
					}
					// question.setAnswers(answers);
					// Statistic s = new Statistic();
					// s.setPercentage(0.0);
					// question.setStatistic(s);
					// s.setQuestion(question);
					// question.setDifficulty("");
					// question.setXml("");
					// e.setXml("");
					// e.setDifficulty("");
					// for (Answer a : answers) {
					// a.setQuestion(question);
					// }
					//
					// databaseUtil.openTransaction();
					//
					// databaseUtil.persist(question);
					// databaseUtil.persist(s);
					// for (Answer a : answers) {
					// databaseUtil.persist(a);
					// }
					// databaseUtil.persist(e);
					// databaseUtil.persist(exercise);
					//
					// databaseUtil.commitTransaction();
					BufferedReader brStatistic;
					if(args[0] != "controller"){
					brStatistic = new BufferedReader(
							new FileReader(prop.getProperty("pathToExam")
									.replace(".tst", ".sts")));

					}
					else{
						brStatistic = new BufferedReader(
								new FileReader(args[1]
										.replace(".tst", ".sts")));
					}
					Statistic s = new Statistic();
					while ((sCurrentLine = brStatistic.readLine()) != null) {
						// We reached percentages
						if (sCurrentLine.contains("Valores porcentuales")) {
							String copy = sCurrentLine;
							String namecode = question.getName().substring(9);
							//

							while (copy != null && sCurrentLine != null) {
								// We have here the line of the question we are
								// doing
								// TODO
								if (copy.length() > 3) {
									copy = copy.replaceAll("\\s+", "")
											.substring(0, 3);
									if (copy.equals(namecode)) {
										// We are in the line of the question
										// now
										String nospaces = sCurrentLine;
										nospaces = nospaces.replaceAll("\\s+",
												"");
										nospaces = nospaces.replaceAll(
												namecode, "");
										// Now we separate the percentage using
										// the . instead of substring(0,3) in
										// case we have elements like 4.2
										String[] percentsplit = nospaces
												.split("\\.");
										// We build the string for the
										// percentage
										String percentage = percentsplit[0]
												+ "."
												+ percentsplit[1].substring(0,
														1);

										s.setPercentage(new Double(percentage));
										s.setQuestion(question);
										// System.out.println(s.getPercentage());
									}

									else if (s.getPercentage() == null
											&& s.getQuestion() == null) {
										s.setPercentage(-1.0);
										s.setQuestion(question);
									}

								}

								sCurrentLine = brStatistic.readLine();
								// System.out.println(sCurrentLine);
								copy = sCurrentLine;
							}
						}
					}

					question.setAnswers(answers);
					question.setStatistic(s);
					question.setDifficulty(s.getPercentage().toString());
					xmlToExam = xmlToExam.concat("<ejercicio etiqueta=\""
							+ exercise.getName() + "\"> <enunciado>\n<![CDATA["
							+ exercise.getText()
							+ "]]></enunciado>\n <preguntas>\n");
					String xmlToQuestion = " <pregunta peso=\""
							+ question.getWeight()
							+ "\" codigo=\""
							+ question.getName()
							+ "\">\n <texto>"
							+ question.getText()
							+ "\n</texto>\n<respuestasUnica numColumnas=\"1\" numSoluciones=\"unica\" pesoRespCorrecta=\"2\" pesoRespIncorrecta=\""
							+ question.getWeightfail() + "\">\n";

					// Dificulty of exam:
					Double finalDif = 0.0;
					Integer count = 0;
					for (Exercise exer : e.getExercises()) {
						for (Question q : exer.getQuestions()) {
							if (q.getStatistic().getPercentage() > 0.0) {
								count++;
								finalDif = finalDif
										+ q.getStatistic().getPercentage();
							}
						}
					}

					finalDif = finalDif / count;
					e.setDifficulty(String.valueOf(finalDif));
					
					//COMMENT / UNCOMMENT TO GENERATE TEST METADATAS
//					Collection<Metadata> metadatas = new ArrayList<Metadata>();
//					Metadata m1 = new Metadata();
//					Metadata m2 = new Metadata();
//					m1.setName("MetadataTest1");
//					m2.setName("MetadataTest2");
//					metadatas.add(m1);
//					metadatas.add(m2);
//					Collection<Question> qsts = new ArrayList<Question>();
//					qsts.add(question);
//					m1.setQuestions(qsts);
//					m2.setQuestions(qsts);
//					question.setMetadata(metadatas);
					//COMMENT
					
					for (Answer a : answers) {
						xmlToQuestion = xmlToQuestion
								.concat("<respuesta correcta=\""
										+ a.getIsCorrect() + "\">\n<texto>"
										+ a.getText() + "</texto>\n</respuesta>\n");
						a.setQuestion(question);
					}
					// Concat XML endTags
					xmlToQuestion = xmlToQuestion
							.concat("</respuestasUnica>\n</pregunta>\n");

					// Añadir comprobacion si es ultimo para añadir al final
					// /ejercicios y /examen
					xmlToExam = xmlToExam
							.concat(xmlToQuestion
									+ "</preguntas>\n<metadatos></metadatos>\n</ejercicio>\n");

					question.setXml(xmlToQuestion);

					e.setXml(xmlToExam);
					// DATABASE INSERT
					databaseUtil.openTransaction();
					//COMMENT / UNCOMMENT TO GENERATE TEST METADATAS
//					for (Metadata m : metadatas) {
//						databaseUtil.persist(m);
//					}
					databaseUtil.persist(e);
					databaseUtil.persist(exercise);
					databaseUtil.persist(question);
					for (Answer a : answers) {
						databaseUtil.persist(a);
					}

					databaseUtil.commitTransaction();
				}

			}

			e.setXml(e.getXml().concat(" </ejercicios>\n</p:examen>"));
			databaseUtil.openTransaction();
			databaseUtil.persist(e);
			databaseUtil.commitTransaction();

		} catch (Throwable oops) {
			System.out.flush();
			System.err.printf("%n%s%n", oops.getLocalizedMessage());
			oops.printStackTrace(System.err);
		} finally {
			if (databaseUtil != null)
				databaseUtil.close();
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
