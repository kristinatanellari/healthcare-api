package main.healthcare.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/v2/api-docs", "/swagger-ui.html", "/patient/register", "/doctor/register", "/pharmacist/register", "/login")
                .permitAll()
                .antMatchers("/publishArticle/**", "/get/allDoctors", "/get/allPatients", "/get/allMyPatients", "/doctor/**", "/setDiagnose/**", "/getMyPatientDiagnosis/**", "/prescription").hasAuthority("DOCTOR")
                .antMatchers("/patient/**", "/get/doctorsBySpecialization/**", "/get/checkMyAppointment/**", "/get/allDoctors", "/get/doctorInfo/**", "/getMyAnalysis/**","/getMyDiagnosis/**").hasAuthority("PATIENT")
                .antMatchers("/pharmacist/**", "/executePrescription/**", "/getPrescription/**").hasAuthority("PHARMACIST")
                .antMatchers("/readArticle/**").hasAnyAuthority("PATIENT", "DOCTOR", "PHARMACIST")
                .anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        ;
    }

    /*
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/v2/api-docs", "/swagger-ui.html", "/patient/register", "/doctor/register", "/pharmacist/register", "/login")
                .permitAll()
                .antMatchers("/publishArticle/**", "/get/allDoctors", "/get/allPatients", "/get/allMyPatients", "/doctor/**", "/doctor/setVacationDates", "/setDiagnose/**", "/getMyPatientDiagnosis/**", "/doctor/approveAppointment/**", "/prescription", "/doctor/getMyPrescriptions/**").hasAuthority("DOCTOR")
                .antMatchers("/patient/**", "/patient/deleteMyAccount", "/get/doctorsBySpecialization/**", "/get/checkMyAppointment/**", "/get/allDoctors", "/patient/scheduleAppointment", "/get/doctorInfo/**", "/getMyAnalysis/**", "/patient/updateData/**", "/patient/getMyPrescriptions/**", "/getMyDiagnosis/**").hasAuthority("PATIENT")
                .antMatchers("/pharmacist/**", "/executePrescription/**", "/getPrescription/**").hasAuthority("PHARMACIST")
                .antMatchers("/readArticle/**").hasAnyAuthority("PATIENT", "DOCTOR", "PHARMACIST")
                .anyRequest().authenticated()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        ;
    }
    */
}
