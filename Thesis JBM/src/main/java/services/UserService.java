package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.User;
import forms.CustomerForm;

@Transactional
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	private Md5PasswordEncoder encoder;

	// Constructors --------------------------

	public UserService() {
		super();
	}

	// Other business methods ----------------
	public boolean AmIMySelf(int userId) {
		return LoginService.getPrincipal().getId() == userId;
	}

	public boolean IAmAUser() {
		return checkRole(Authority.CUSTOMER);
	}

	public boolean IAmAnAdmin() {
		return checkRole(Authority.ADMIN);
	}

	// TODO
	public boolean AmIAGuest() {
		return false;
	}

	private boolean checkRole(String role) {
		Collection<Authority> authorities = LoginService.getPrincipal()
				.getAuthorities();

		boolean res = false;

		for (Authority auth : authorities)
			res = res || auth.getAuthority().toUpperCase().compareTo(role) == 0;

		return res;
	}

	public User findByPrincipal() {

		User result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public User findByUserAccount(UserAccount userAccount) {

		Assert.notNull(userAccount);
		User result;
		result = userRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	// Simple CRUD methods -----------------
	public User create() {
		User admin = new User();

		Authority auth = new Authority();
		Collection<Authority> lAuthorty = new ArrayList<Authority>();
		UserAccount usserA = new UserAccount();

		auth.setAuthority("CUSTOMER");
		lAuthorty.add(auth);
		usserA.setAuthorities(lAuthorty);
		admin.setUserAccount(usserA);

		return admin;
	}

	public Collection<User> findAll() {

		return userRepository.findAll();
	}

	public User findOne(int userId) {

		return userRepository.findOne(userId);
	}

	public User findOneEdit(int userId) {
		User now = findByPrincipal();

		User user = userRepository.findOne(userId);
		return user;

	}

	public void save(User user) {
		Assert.notNull(user);

		encoder = new Md5PasswordEncoder();

		String oldPassword = user.getUserAccount().getPassword();
		String newPassword = encoder.encodePassword(oldPassword, null);

		user.getUserAccount().setPassword(newPassword);

		userRepository.save(user);
	}

	public void saveUser(User user) {
		Assert.notNull(user);
		userRepository.save(user);
	}

	public void savePassword(User user, String password) {
		Assert.notNull(user);

		encoder = new Md5PasswordEncoder();

		String newPassword = encoder.encodePassword(password, null);

		user.getUserAccount().setPassword(newPassword);

		userRepository.save(user);
	}

	public User findByPrincipalComment() {

		User result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		result = findByUserAccountComment(userAccount);

		return result;
	}

	public User findByUserAccountComment(UserAccount userAccount) {

		User result;
		result = userRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public User findByUsername(String username) {

		User result;
		result = userRepository.findActorByUsername(username);

		return result;
	}

	// Reconstruct

	public User reconstruct(CustomerForm userForm) {
		User user = this.create();

		user.setEmail(userForm.getEmail());
		user.setId(userForm.getId());
		user.setName(userForm.getName());
		user.setVersion(userForm.getVersion());
		user.getUserAccount().setPassword(userForm.getPassword());
		user.getUserAccount().setUsername(userForm.getUsername());

		// Vemos si el nombre de usuario no está repetido
		// Assert.isTrue(this.findActorByUsername(userForm.getUsername()).isEmpty());
		// Vemos que las passwords sean iguales
		Assert.isTrue(
				user.getUserAccount().getPassword()
						.equals(userForm.getRepeatPassword()),
				"Passwords are different");
		// Vemos si ha aceptado los TOS
		return user;
	}

	public CustomerForm constructNew() {

		User user = this.create();

		CustomerForm userForm = new CustomerForm();

		userForm.setEmail(user.getEmail());
		userForm.setId(user.getId());
		userForm.setName(user.getName());
		
		userForm.setPassword(user.getUserAccount().getPassword());
		userForm.setUsername(user.getUserAccount().getUsername());

		return userForm;

	}

}
