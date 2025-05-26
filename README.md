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

- v1.0.1 (26.05.2025) Better saftey and strucutre
   - added guard package to guard for security
      - PrintDog the cute k9 like hero to stop you from using    
        normal print... wil raise RuntimeException
   - split up View from their terminal methods and add action 
     factory methods
      - this means breaking changes of View
      - view has now 2 acces points
      1. terminal
      this is a fast way to acces terminal information for
      responsive layouts and co and also print adn fill
        - terminal.print
        - terminal.getWidth
        - terminal.getHeight
        - terminal.getCurrentRclCount
        - terminal.fill
      2. actions
      factory methods always acesible directly in view...
      all build in actions will be afaible like this in future
        - actions.nop
        - actions.redirect
        - actions.stop
        - actions.dd
   - added dump and die method similar to laravel
   - and much more fine bug fixes

- v0.0.1 (26.05.2025) First release
   - for better version control this is now v0.0.1
   - still wil leave the old log
   - chipi chipi chapa chapa UWU yey

- v0.1.1 (26.05.2025) PATCH
   - now posible to select a default view instad of hardcoded 
     v_select
   - removed debug line in Interface class i forgot UwU
   - now has acces to the terminal size via getter on every view
   - and also can acces current rcl (Render cycle line) count for better reactive menues

- v0.1.0 (25.05.2025) Made Interface its own module
	- manages views via View class
	- has Action classes and predefined funtionality: a_redirect, a_nop, a_exit
	- uses itself to show v_select to make a menue selection
	- v_select hardcoded may change in future
	- lots of known bugs but relative stable
	- v0.1 so still in alpha phase
