#!/.software/local/.admin/bins/bin/perl
use strict;
use Env;
use CGI;

print "Content-type:text/html\n\n";
my %keys;
my $cgi = new CGI;

foreach my $key ( $cgi->param() ) {
	$keys{$key} = $cgi->param($key);
}

print <<EOL;

<form name="ghsigninput" action="ghsignscr2.cgi" method="GET">
<input type="hidden" name="p" value=$keys{p}>
<input type="hidden" name="a" value=$keys{a}>
<input type="hidden" name="b" value=$keys{b}>
<input type="hidden" name="Q" value=$keys{Q}>

<h1> Sign a message </h1> <p>
Select a private key, x, for 0 &lt; x &lt; $keys{Q}: <input type=text
name="X"> <p>
Enter the message, m: <input type=text name="h"> <p>
Select a signing key, k, for 0 &lt; x &lt; $keys{Q}: <input type=text
name="K"> <p>


<input type = "submit" value="Send">
<input type = "reset" value="Clear Form">

</form>
EOL

