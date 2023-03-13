package com.nighthawk.team_backend.mvc.database.club;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
This class has an instance of Java Persistence API (JPA)
-- @Autowired annotation. Allows Spring to resolve and inject collaborating beans into our bean.
-- Spring Data JPA will generate a proxy instance
-- Below are some CRUD methods that we can use with our database
*/
@Service
@Transactional
public class ClubDetailsService implements UserDetailsService { // "implements" ties ModelRepo to Spring Security
    // Encapsulate many object into a single Bean (Club, Roles, and Scrum)
    @Autowired // Inject ClubJpaRepository
    private ClubJpaRepository clubJpaRepository;
    @Autowired // Inject RoleJpaRepository
    private ClubRoleJpaRepository clubRoleJpaRepository;
    @Autowired // Inject PasswordEncoder
    private PasswordEncoder passwordEncoder;

    /*
     * UserDetailsService Overrides and maps Club & Roles POJO into Spring Security
     */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Club club = clubJpaRepository.findByEmail(email); // setting variable user equal to the method finding the
                                                          // username in the database
        if (club == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // club.getRoles().forEach(role -> { // loop through roles
        // authorities.add(new SimpleGrantedAuthority(role.getName())); // create a
        // SimpleGrantedAuthority by passed in
        // // role, adding it all to the authorities list,
        // // list of roles gets past in for spring
        // // security
        // });
        return new org.springframework.security.core.userdetails.User(club.getEmail(), club.getPassword(), authorities);
    }

    /* Club Section */

    public List<Club> listAll() {
        return clubJpaRepository.findAllByOrderByNameAsc();
    }

    // custom query to find match to name or email
    public List<Club> list(String name, String email) {
        return clubJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email);
    }

    // custom query to find anything containing term in name or email ignoring case
    public List<Club> listLike(String term) {
        return clubJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    // custom query to find anything containing term in name or email ignoring case
    public List<Club> listLikeNative(String term) {
        String like_term = String.format("%%%s%%", term); // Like required % rappers
        return clubJpaRepository.findByLikeTermNative(like_term);
    }

    public void save(Club club) {
        club.setPassword(passwordEncoder.encode(club.getPassword()));
        clubJpaRepository.save(club);
    }

    public Club get(long id) {
        return (clubJpaRepository.findById(id).isPresent())
                ? clubJpaRepository.findById(id).get()
                : null;
    }

    public Club getByEmail(String email) {
        return (clubJpaRepository.findByEmail(email));
    }

    public void delete(long id) {
        clubJpaRepository.deleteById(id);
    }

    public void defaults(String password, String roleName) {
        for (Club club : listAll()) {
            if (club.getPassword() == null || club.getPassword().isEmpty() || club.getPassword().isBlank()) {
                club.setPassword(passwordEncoder.encode(password));
            }
            // if (club.getRoles().isEmpty()) {
            // ClubRole role = clubRoleJpaRepository.findByName(roleName);
            // if (role != null) { // verify role
            // club.getRoles().add(role);
            // }
            // }
        }
    }

    /* Roles Section */

    public void saveRole(ClubRole role) {
        ClubRole roleObj = clubRoleJpaRepository.findByName(role.getName());
        if (roleObj == null) { // only add if it is not found
            clubRoleJpaRepository.save(role);
        }
    }

    public List<ClubRole> listAllRoles() {
        return clubRoleJpaRepository.findAll();
    }

    public ClubRole findRole(String roleName) {
        return clubRoleJpaRepository.findByName(roleName);
    }

    public void addRoleToClub(String email, String roleName) { // by passing in the two strings you are giving the user
                                                               // that certain role
        Club club = clubJpaRepository.findByEmail(email);
        if (club != null) { // verify club
            ClubRole role = clubRoleJpaRepository.findByName(roleName);
            // if (role != null) { // verify role
            // boolean addRole = true;
            // for (ClubRole roleObj : club.getRoles()) { // only add if user is missing
            // role
            // if (roleObj.getName().equals(roleName)) {
            // addRole = false;
            // break;
            // }
            // }
            // if (addRole)
            // club.getRoles().add(role); // everything is valid for adding role
            // }
        }
    }

}