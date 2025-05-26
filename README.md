# Interface

## What is it

a project that started in a school task and evolved into a
CLI micro framework made in java

## Plans

 - Add a Http layer so its easier and always accesible from view
   without the java pain

 - Add a Login layer so a action like a_auth_redirect or even a_redirect(<class or obj>, AUTH.REQUIRE_LOGIN)
   means a login class to overwrite with custom login logic and just a redirect ux layer to redirect auto
   to a dev defined login view mask

 - Storage like web browser String key saves for dev defined data types
   read only or global, acces rights set by the author view etc

 - more timey wimey wibly wobly... stuff

## Changelog

- v0.1.1 (26.05.2025) PATCH
   - now posible to select a default view instad of hardcoded 
     v_select
   - removed debug line in Interface class i forgot UwU
   - now has acces to the terminal size via getter on every view

- v0.1.0 (25.05.2025) Made Interface its own module
	- manages views via View class
	- has Action classes and predefined funtionality: a_redirect, a_nop, a_exit
	- uses itself to show v_select to make a menue selection
	- v_select hardcoded may change in future
	- lots of known bugs but relative stable
	- v0.1 so still in alpha phase
