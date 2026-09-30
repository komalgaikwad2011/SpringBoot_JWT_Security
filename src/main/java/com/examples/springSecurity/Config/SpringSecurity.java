package com.examples.springSecurity.Config;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.examples.springSecurity.Service.CustomUserDetailsService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;


@Configuration
@EnableWebSecurity
public class SpringSecurity {
	
	 @Bean
	 public PasswordEncoder passwordEncoder()
	 {
		 return new BCryptPasswordEncoder();
	 }
	
	 @Bean
	 public DaoAuthenticationProvider authenticationProvider(
			 CustomUserDetailsService userDetailsService,
			 PasswordEncoder passwordEncoder)
	 {
		 DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		 provider.setUserDetailsService(userDetailsService);
		 provider.setPasswordEncoder(passwordEncoder);
		 return provider;
	 }
	 @Bean
	 public SecurityFilterChain securityFilterChain(
			 HttpSecurity httpSecurity,
			 DaoAuthenticationProvider provider,
			 JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception 
	 {
		 httpSecurity.csrf(csrf->csrf.disable())
		 .authenticationProvider(provider)
		
		 
		 .authorizeHttpRequests(auth->
		 auth.requestMatchers("/api/users/register",
				 "/auth/login").permitAll()
		 .anyRequest().authenticated()
		 )
		 
		 .sessionManagement(session->
				 session.sessionCreationPolicy(
						 SessionCreationPolicy.STATELESS
						 )
				 )
		 .oauth2ResourceServer(oauth2->
				 oauth2.jwt(jwt->
						 jwt.jwtAuthenticationConverter(
								 jwtAuthenticationConverter
								 )
						 )
				 );
		 
		 
		 return httpSecurity.build();
	 }
	 
	 @Bean
	 public SecretKey jwtSecretKey(@Value("${jwt.secret}") String secret)
	 {
		 byte[] decodedKey=
				 Base64.getDecoder().decode(secret);
		 return new SecretKeySpec(
				 decodedKey,
				 "HmacSHA256"
				 );
	 }
	 
	 @Bean
	 public AuthenticationManager authenticationManager(
			 DaoAuthenticationProvider authenticationProvider)
	 {
		 return new ProviderManager(authenticationProvider);
	 }	 
	 
	 
	 @Bean
	 public JwtAuthenticationConverter jwtAuthenticationConverter()
	 {
		 JwtGrantedAuthoritiesConverter autheoritiesConverter=
				 new JwtGrantedAuthoritiesConverter();
		 
		 autheoritiesConverter.setAuthoritiesClaimName("authorities");
		 autheoritiesConverter.setAuthorityPrefix("");
		 
		 JwtAuthenticationConverter authenticationConverter=new JwtAuthenticationConverter();
		 
		 authenticationConverter.setJwtGrantedAuthoritiesConverter(autheoritiesConverter);
		 
		 return authenticationConverter;
	 }
	 
	// Sign token (Encoder)
	    @Bean
	    public JwtEncoder jwtEncoder(SecretKey secretKey) {
	        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
	    }
	 
	 @Bean
	    public JwtDecoder jwtDecoder(
	    			SecretKey secretKey, 
	    			@Value("${jwt.issuer}") String issuer) 
	    {
	        NimbusJwtDecoder decoder = NimbusJwtDecoder
	                .withSecretKey(secretKey)
	                .macAlgorithm(MacAlgorithm.HS256)
	                .build();

	        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuer));
	        return decoder;
	    }
}
