<nav class="navbar navbar-expand-md navbar-light shadow-sm">
 <div class="container">
 <a href="{{ url('/') }}"> Home </a>
 <a href="{{ route('comments') }}"> Komentarze </a>

 <!-- Authentication Links -->
 @guest
 <a href="{{ route('login') }}">Login</a>
 @endguest

 </div>
</nav>
