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

<form name="ghdhinput" action="ghdhscr2.cgi" method="GET">
<input type="hidden" name="p" value=$keys{p}>
<input type="hidden" name="a" value=$keys{a}>
<input type="hidden" name="b" value=$keys{b}>
<input type="hidden" name="Q" value=$keys{Q}>

<h1>Gong-Harn Diffie-Hellman Key Agreement </h1><p>

Select private key, x, for 0 &lt; x &lt; $keys{Q}: <input type=text
name="X"> <p>
Enter received public key pair: <br>
&nbsp sy = <input type=text name="sy"> <br>
s-y = <input type=text name="s_y"> <p>
<input type = "submit" value="Send">
<input type = "reset" value="Clear Form">

</form>
EOL

