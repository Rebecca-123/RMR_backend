package com.nighthawk.team_backend.mvc.database.team;

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
public class TeamDetailsService implements UserDetailsService { // "implements" ties ModelRepo to Spring Security
    // Encapsulate many object into a single Bean (Team, Roles, and Scrum)
    @Autowired // Inject TeamJpaRepository
    private TeamJpaRepository teamJpaRepository;
    @Autowired // Inject RoleJpaRepository
    private TeamRoleJpaRepository teamRoleJpaRepository;
    @Autowired // Inject PasswordEncoder
    private PasswordEncoder passwordEncoder;

    /*
     * UserDetailsService Overrides and maps Team & Roles POJO into Spring Security
     */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Team team = teamJpaRepository.findByEmail(email); // username in the database
        if (team == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // team.getRoles().forEach(role -> { // loop through roles
        // authorities.add(new SimpleGrantedAuthority(role.getName())); // create a
        // SimpleGrantedAuthority by passed in
        // // role, adding it all to the authorities list,
        // // list of roles gets past in for spring
        // // security
        // });
        return new org.springframework.security.core.userdetails.User(team.getEmail(), team.getPassword(), authorities);
    }

    /* Team Section */

    public List<Team> listAll() {
        return teamJpaRepository.findAllByOrderByNamesAsc();
    }

    // custom query to find match to name or email
    public List<Team> list(String bigteam, String email) {
        return teamJpaRepository.findByBigteamContainingIgnoreCaseOrEmailContainingIgnoreCase(bigteam, email);
    }

    // // custom query to find anything containing term in name or email ignoring
    // case
    // public List<Team> listLike(String term) {
    // return
    // teamJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term,
    // term);
    // }

    // custom query to find anything containing term in name or email ignoring case
    public List<Team> listLikeNative(String term) {
        String like_term = String.format("%%%s%%", term); // Like required % rappers
        return teamJpaRepository.findByLikeTermNative(like_term);
    }

    public void save(Team team) {
        team.setPassword(passwordEncoder.encode(team.getPassword()));
        teamJpaRepository.save(team);
    }

    public Team get(long id) {
        return (teamJpaRepository.findById(id).isPresent())
                ? teamJpaRepository.findById(id).get()
                : null;
    }

    public Team getByNames(String email) {
        return (teamJpaRepository.findByEmail(email));
    }

    public void delete(long id) {
        teamJpaRepository.deleteById(id);
    }

    public void defaults(String password, String roleName) {
        for (Team team : listAll()) {
            if (team.getPassword() == null || team.getPassword().isEmpty() || team.getPassword().isBlank()) {
                team.setPassword(passwordEncoder.encode(password));
            }
            // if (team.getRoles().isEmpty()) {
            // TeamRole role = teamRoleJpaRepository.findByName(roleName);
            // if (role != null) { // verify role
            // team.getRoles().add(role);
            // }
            // }
        }
    }

    /* Roles Section */

    public void saveRole(TeamRole role) {
        TeamRole roleObj = teamRoleJpaRepository.findByName(role.getName());
        if (roleObj == null) { // only add if it is not found
            teamRoleJpaRepository.save(role);
        }
    }

    public List<TeamRole> listAllRoles() {
        return teamRoleJpaRepository.findAll();
    }

    public TeamRole findRole(String roleName) {
        return teamRoleJpaRepository.findByName(roleName);
    }

    public void addRoleToTeam(String email, String roleName) { // by passing in the two strings you are giving the user
                                                               // that certain role
        Team team = teamJpaRepository.findByEmail(email);
        if (team != null) { // verify team
            TeamRole role = teamRoleJpaRepository.findByName(roleName);
            // if (role != null) { // verify role
            // boolean addRole = true;
            // for (TeamRole roleObj : team.getRoles()) { // only add if user is missing
            // role
            // if (roleObj.getName().equals(roleName)) {
            // addRole = false;
            // break;
            // }
            // }
            // if (addRole)
            // team.getRoles().add(role); // everything is valid for adding role
            // }
        }
    }

}
